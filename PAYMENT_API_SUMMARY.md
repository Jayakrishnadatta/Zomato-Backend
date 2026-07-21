# 📋 Payment API Review - Executive Summary for Frontend

## ✅ API Overview

| Aspect | Details |
|--------|---------|
| **Base URL** | `http://localhost:8080/api` |
| **Content-Type** | `application/json` |
| **Authentication** | User ID from URL |
| **Payment Gateway** | Razorpay (Primary) |

---

## 🔗 Main Endpoints

### 1. Place Order
```
POST /api/orders/{userId}
```
**Needed from Frontend:** addressId, paymentMethod  
**Returns:** Order details with orderId

### 2. Process Payment (Razorpay)
```
POST /api/payments/
```
**Needed from Frontend:** orderId, paymentMethod  
**Returns:** gatewayOrderId (use for Razorpay checkout)

### 3. Verify Payment
```
POST /api/payments/razorpay/verify
```
**Needed from Frontend:** razorpayOrderId, razorpayPaymentId, razorpaySignature  
**Returns:** Confirmation with paymentStatus = "PAID"

---

## 📤 What Frontend Sends

### Request 1: Create Order
```json
{
  "addressId": 789,
  "paymentMethod": "RAZORPAY"
}
```

| Field | Type | Required | Note |
|-------|------|----------|------|
| addressId | Long | ✅ | Shipping address ID |
| paymentMethod | String | ✅ | Must be "RAZORPAY" |

### Request 2: Process Payment
```json
{
  "orderId": 456,
  "paymentMethod": "RAZORPAY"
}
```

| Field | Type | Required | Note |
|-------|------|----------|------|
| orderId | Long | ✅ | From order creation |
| paymentMethod | String | ✅ | Must be "RAZORPAY" |

### Request 3: Verify Payment
```json
{
  "razorpayOrderId": "order_xyz123",
  "razorpayPaymentId": "pay_abc456",
  "razorpaySignature": "9ef4d..."
}
```

| Field | Type | Required | Note |
|-------|------|----------|------|
| razorpayOrderId | String | ✅ | From Razorpay response |
| razorpayPaymentId | String | ✅ | From Razorpay response |
| razorpaySignature | String | ✅ | HMAC signature for security |

---

## 📥 What Frontend Receives

### Response 1: Order Created
```json
[
  {
    "orderId": 456,
    "productId": 100,
    "productName": "iPhone 15",
    "quantity": 1,
    "price": 999.99,
    "totalPrice": 999.99,
    "orderStatus": "PLACED",
    "paymentStatus": "PENDING",
    "orderedAt": "2026-07-19T11:24:35"
  }
]
```

**Extract:** `orderId` (for next step)

---

### Response 2: Payment Ready
```json
{
  "paymentId": 101,
  "transactionId": "order_xyz123",
  "amount": 1050.00,
  "paymentStatus": "PENDING",
  "paidAt": null,
  "paymentGateway": "RAZORPAY",
  "gatewayOrderId": "order_xyz123"
}
```

**Extract:** `gatewayOrderId` (for Razorpay)

---

### Response 3: Payment Verified
```json
{
  "paymentId": 101,
  "transactionId": "pay_abc456",
  "amount": 1050.00,
  "paymentStatus": "PAID",
  "paidAt": "2026-07-19T11:24:35",
  "paymentGateway": "RAZORPAY",
  "gatewayOrderId": "order_xyz123"
}
```

**Check:** `paymentStatus === "PAID"` → Show success

---

## 🎯 Integration Flow Chart

```
┌─────────────────────────────────────────────────────────────┐
│                   PAYMENT FLOW                              │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Frontend                    Backend           Razorpay    │
│  ────────                    ───────           ────────    │
│     │                                                      │
│     │  1. POST /orders/{userId}                           │
│     │────────────────────────→                            │
│     │  {addressId, paymentMethod}                         │
│     │                                                      │
│     │                  orderId ←─────────                 │
│     │←────────────────────────                            │
│     │                                                      │
│     │  2. POST /payments/                                 │
│     │────────────────────────→                            │
│     │  {orderId, paymentMethod}                           │
│     │                                                      │
│     │            gatewayOrderId ←──────────              │
│     │←────────────────────────                            │
│     │                                                      │
│     │  3. Show Razorpay Modal                             │
│     │────────────────────────────────────────────────────→│
│     │                                              modal    │
│     │  4. Customer Completes Payment                      │
│     │                                     ← payment data ──│
│     │                                                      │
│     │  5. POST /payments/razorpay/verify                  │
│     │────────────────────────→                            │
│     │  {razorpayOrderId, razorpayPaymentId, signature}    │
│     │                                                      │
│     │              verification result ←──────────        │
│     │←────────────────────────                            │
│     │                                                      │
│     │  6. Show Success Message                            │
│     │                                                      │
│     │  7. Redirect to Order Tracking                      │
│     │                                                      │
└─────────────────────────────────────────────────────────────┘
```

---

## 📊 Field Details Reference

### Payment Status Values
| Status | Meaning | Shown at |
|--------|---------|----------|
| PENDING | Awaiting payment | After step 2 |
| PAID | Payment successful | After verification (step 5) |
| FAILED | Payment failed | On error |
| REFUNDED | Money refunded | On refund request |
| CANCELLED | Payment cancelled | If user cancels |

### Order Status Values
| Status | Meaning |
|--------|---------|
| PLACED | Order just created |
| CONFIRMED | After payment confirmed |
| SHIPPED | Order dispatched |
| DELIVERED | Delivered to customer |
| CANCELLED | Order cancelled |

---

## 💡 Key Points for Frontend

✅ **Must Do:**
- Send proper Content-Type: `application/json`
- Multiply amount by 100 when sending to Razorpay (paise)
- Extract `gatewayOrderId` from payment response
- Use `razorpayOrderId`, `razorpayPaymentId`, `razorpaySignature` for verification
- Check `paymentStatus === "PAID"` after verification
- Clear cart only after successful payment

❌ **Must NOT Do:**
- Never expose Razorpay secret key
- Never skip payment verification
- Never trust just frontend payment status
- Never hardcode API URLs or keys

---

## 🔧 Complete 3-Step Integration

### Step 1: Create Order
```javascript
const response = await fetch(`/api/orders/${userId}`, {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    addressId: 789,
    paymentMethod: 'RAZORPAY'
  })
});
const order = await response.json();
const orderId = order[0].orderId;
```

### Step 2: Initiate Payment
```javascript
const response = await fetch('/api/payments/', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    orderId: orderId,
    paymentMethod: 'RAZORPAY'
  })
});
const payment = await response.json();
const gatewayOrderId = payment.gatewayOrderId;
```

### Step 3: Verify Payment
```javascript
const response = await fetch('/api/payments/razorpay/verify', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    razorpayOrderId: razorpayResponse.razorpay_order_id,
    razorpayPaymentId: razorpayResponse.razorpay_payment_id,
    razorpaySignature: razorpayResponse.razorpay_signature
  })
});
const result = await response.json();
if (result.paymentStatus === 'PAID') {
  console.log('Payment successful!');
}
```

---

## 📱 UI Components to Build

1. **Address Selector Dropdown**
   - List all user addresses
   - Allow selection of shipping address

2. **Payment Method Selector**
   - Show options: RAZORPAY, CREDIT_CARD, DEBIT_CARD, UPI
   - Default to RAZORPAY

3. **Order Summary Display**
   - Show items with prices
   - Calculate and display total
   - Show all charges (tax, shipping)

4. **Razorpay Checkout Modal**
   - Trigger Razorpay with order details
   - Handle success and error callbacks

5. **Payment Status Display**
   - Show PENDING while waiting
   - Show PAID when successful
   - Show FAILED if payment fails

6. **Order Confirmation Page**
   - Display order details
   - Show payment confirmation
   - Provide order tracking link

---

## ❌ Error Handling

### Most Common Errors

**"Order not found"**
- Cause: Invalid orderId
- Fix: Verify orderId from order creation

**"Order is already paid"**
- Cause: Attempting to pay twice
- Fix: Check order paymentStatus before payment

**"Cart is empty"**
- Cause: No items in cart
- Fix: User must add items first

**"Invalid Razorpay signature"**
- Cause: Wrong credentials or tampered data
- Fix: Verify Razorpay keys are correct

**"Address not found"**
- Cause: Invalid addressId
- Fix: Let user select valid address

---

## 🚀 Ready to Build?

Use **FRONTEND_PAYMENT_INTEGRATION_GUIDE.md** for complete implementation example!

All API details provided above. Start integrating! 🎉

---

## 📚 Documentation Files Available

- **PAYMENT_API_SPECIFICATION.md** - Complete API spec (this file content)
- **FRONTEND_PAYMENT_INTEGRATION_GUIDE.md** - Full implementation guide with code
- **ENUM_QUICK_REFERENCE.md** - Status values reference

---

**Happy Coding! 🚀**
