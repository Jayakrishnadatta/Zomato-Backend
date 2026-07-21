# 💳 Payment API Complete Specification - Request & Response Details

## 📌 API Base URL
```
http://localhost:8080/api
```

---

## 🔗 Payment API Endpoints

### 1. Process Payment (Initiate Payment)
```
POST /api/payments/
```

#### 📤 Request Details
**Content-Type:** `application/json`

**Request Body (PaymentRequestDto):**
```json
{
  "orderId": 456,
  "paymentMethod": "RAZORPAY"
}
```

| Field | Type | Required | Description | Example |
|-------|------|----------|-------------|---------|
| **orderId** | Long | ✅ YES | Order ID from Order creation | `456` |
| **paymentMethod** | String | ✅ YES | Payment gateway type | `"RAZORPAY"` |

**Valid Payment Methods:**
- `RAZORPAY` - Razorpay payment gateway
- `CREDIT_CARD` - Credit card payment
- `DEBIT_CARD` - Debit card payment
- `UPI` - UPI payment method
- `WALLET` - Digital wallet

#### 📥 Response Details
**Status Code:** `200 OK`

**Response Body (PaymentResponse):**
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

| Field | Type | Description | Example |
|-------|------|-------------|---------|
| **paymentId** | Long | Unique payment ID in system | `101` |
| **transactionId** | String | Razorpay Order ID / Transaction ID | `"order_xyz123"` |
| **amount** | Double | Payment amount in INR | `1050.00` |
| **paymentStatus** | String | Current payment status | `"PENDING"` |
| **paidAt** | DateTime | Timestamp when payment was made | `null` (for PENDING) |
| **paymentGateway** | String | Gateway used | `"RAZORPAY"` |
| **gatewayOrderId** | String | Gateway's order reference | `"order_xyz123"` |

**Payment Status Values:**
- `PENDING` - Awaiting customer payment (for async gateways)
- `PAID` - Payment successful
- `SUCCESS` - Alternative success status
- `FAILED` - Payment failed
- `REFUNDED` - Payment refunded
- `CANCELLED` - Payment cancelled

#### ❌ Error Responses

**400 Bad Request:**
```json
{
  "error": "Order not found"
}
```

**400 Bad Request:**
```json
{
  "error": "Order is already paid"
}
```

**400 Bad Request:**
```json
{
  "error": "Cancelled order cannot be paid"
}
```

---

### 2. Verify Razorpay Payment (Webhook/Callback)
```
POST /api/payments/razorpay/verify
```

#### 📤 Request Details
**Content-Type:** `application/json`

**Request Body (RazorpayVerifyDto):**
```json
{
  "razorpayOrderId": "order_xyz123",
  "razorpayPaymentId": "pay_abc456",
  "razorpaySignature": "9ef4dffbfd84f1318f6739a3ce19f9d85851857ae648f114332d8401e0949a3d"
}
```

| Field | Type | Required | Description | Example |
|-------|------|----------|-------------|---------|
| **razorpayOrderId** | String | ✅ YES | Order ID from Razorpay response | `"order_xyz123"` |
| **razorpayPaymentId** | String | ✅ YES | Payment ID from Razorpay | `"pay_abc456"` |
| **razorpaySignature** | String | ✅ YES | HMAC signature from Razorpay | `"9ef4d..."` |

#### 📥 Response Details
**Status Code:** `200 OK`

**Response Body (PaymentResponse):**
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

| Field | Type | Description | Value After Success |
|-------|------|-------------|-----|
| **paymentId** | Long | Payment ID in system | `101` |
| **transactionId** | String | Razorpay Payment ID | `"pay_abc456"` |
| **amount** | Double | Confirmed amount | `1050.00` |
| **paymentStatus** | String | Status after verification | `"PAID"` |
| **paidAt** | DateTime | Actual payment timestamp | `"2026-07-19T11:24:35"` |
| **paymentGateway** | String | Gateway used | `"RAZORPAY"` |
| **gatewayOrderId** | String | Razorpay Order ID | `"order_xyz123"` |

#### ❌ Error Responses

**400 Bad Request - Invalid Signature:**
```json
{
  "error": "Invalid Razorpay signature"
}
```

**400 Bad Request - Missing Fields:**
```json
{
  "error": "Razorpay order id is required"
}
```

**400 Bad Request - Payment Not Found:**
```json
{
  "error": "Payment not found for given Razorpay order id"
}
```

---

### 3. Get Payment by ID
```
GET /api/payments/{paymentId}
```

#### 📤 Request Details
**URL Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| **paymentId** | Long | ✅ YES | Payment ID to fetch | `101` |

**Example Request:**
```
GET /api/payments/101
```

#### 📥 Response Details
**Status Code:** `200 OK`

**Response Body (PaymentResponse):**
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

#### ❌ Error Responses

**400 Bad Request:**
```json
{
  "error": "Payment not found"
}
```

---

### 4. Get Payments by User
```
GET /api/payments/user/{userId}
```

#### 📤 Request Details
**URL Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| **userId** | Long | ✅ YES | User ID to fetch payments | `123` |

**Example Request:**
```
GET /api/payments/user/123
```

#### 📥 Response Details
**Status Code:** `200 OK`

**Response Body (List of PaymentResponse):**
```json
[
  {
    "paymentId": 101,
    "transactionId": "pay_abc456",
    "amount": 1050.00,
    "paymentStatus": "PAID",
    "paidAt": "2026-07-19T11:24:35",
    "paymentGateway": "RAZORPAY",
    "gatewayOrderId": "order_xyz123"
  },
  {
    "paymentId": 102,
    "transactionId": "pay_xyz789",
    "amount": 500.00,
    "paymentStatus": "PAID",
    "paidAt": "2026-07-18T10:15:20",
    "paymentGateway": "RAZORPAY",
    "gatewayOrderId": "order_abc789"
  }
]
```

---

### 5. Refund Payment
```
PATCH /api/payments/{paymentId}/refund
```

#### 📤 Request Details
**URL Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| **paymentId** | Long | ✅ YES | Payment ID to refund | `101` |

**Example Request:**
```
PATCH /api/payments/101/refund
```

**Request Body:** Empty (no body required)

#### 📥 Response Details
**Status Code:** `204 No Content` (No body returned)

OR

**Status Code:** `200 OK`
```json
{
  "paymentId": 101,
  "transactionId": "pay_abc456",
  "amount": 1050.00,
  "paymentStatus": "REFUNDED",
  "paidAt": "2026-07-19T11:24:35",
  "paymentGateway": "RAZORPAY",
  "gatewayOrderId": "order_xyz123"
}
```

#### ❌ Error Responses

**400 Bad Request:**
```json
{
  "error": "Payment not found"
}
```

---

## 🛒 Order API Endpoints (Related to Payment)

### 1. Place Order
```
POST /api/orders/{userId}
```

#### 📤 Request Details
**URL Parameters:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| **userId** | Long | ✅ YES | User placing order | `123` |

**Content-Type:** `application/json`

**Request Body (OrderRequestDto):**
```json
{
  "addressId": 789,
  "paymentMethod": "RAZORPAY"
}
```

| Field | Type | Required | Description | Example |
|-------|------|----------|-------------|---------|
| **addressId** | Long | ✅ YES | Shipping address ID | `789` |
| **paymentMethod** | String | ✅ YES | Payment method | `"RAZORPAY"` |

#### 📥 Response Details
**Status Code:** `200 OK`

**Response Body (List of OrderResponseDto):**
```json
[
  {
    "orderId": 456,
    "productId": 100,
    "productName": "iPhone 15 Pro",
    "quantity": 1,
    "price": 999.99,
    "totalPrice": 999.99,
    "orderStatus": "PLACED",
    "paymentStatus": "PENDING",
    "orderedAt": "2026-07-19T11:24:35"
  },
  {
    "orderId": 456,
    "productId": 101,
    "productName": "Apple Watch",
    "quantity": 1,
    "price": 399.99,
    "totalPrice": 399.99,
    "orderStatus": "PLACED",
    "paymentStatus": "PENDING",
    "orderedAt": "2026-07-19T11:24:35"
  }
]
```

| Field | Type | Description | Example |
|-------|------|-------------|---------|
| **orderId** | Long | Order ID (same for all items in order) | `456` |
| **productId** | Long | Product ID | `100` |
| **productName** | String | Product name | `"iPhone 15 Pro"` |
| **quantity** | Integer | Quantity ordered | `1` |
| **price** | Double | Unit price | `999.99` |
| **totalPrice** | Double | Total for this item (qty × price) | `999.99` |
| **orderStatus** | String | Order status | `"PLACED"` |
| **paymentStatus** | String | Payment status | `"PENDING"` |
| **orderedAt** | DateTime | Order creation time | `"2026-07-19T11:24:35"` |

**Order Status Values:**
- `PENDING` - Initial order state
- `PLACED` - Order placed successfully
- `CONFIRMED` - Payment confirmed
- `SHIPPED` - Order dispatched
- `DELIVERED` - Order delivered
- `CANCELLED` - Order cancelled
- `FAILED` - Order failed
- `RETURNED` - Order returned

#### ❌ Error Responses

**400 Bad Request - Empty Cart:**
```json
{
  "error": "Cart is empty"
}
```

**400 Bad Request - Invalid Address:**
```json
{
  "error": "Address not found"
}
```

---

## 🔄 Complete Payment Flow - Request/Response Sequence

### Flow: Customer Places Order → Pays via Razorpay

#### Step 1: Place Order
```
POST /api/orders/123
Content-Type: application/json

{
  "addressId": 789,
  "paymentMethod": "RAZORPAY"
}
```

**Response:**
```json
{
  "orderId": 456,
  "orderStatus": "PLACED",
  "paymentStatus": "PENDING",
  "totalPrice": 1050.00
}
```

#### Step 2: Process Payment (Initiate Razorpay)
```
POST /api/payments/
Content-Type: application/json

{
  "orderId": 456,
  "paymentMethod": "RAZORPAY"
}
```

**Response:** (Contains Razorpay Order ID)
```json
{
  "paymentId": 101,
  "transactionId": "order_xyz123",
  "amount": 1050.00,
  "paymentStatus": "PENDING",
  "paymentGateway": "RAZORPAY",
  "gatewayOrderId": "order_xyz123"
}
```

#### Step 3: Frontend - Display Razorpay Checkout
Frontend uses `gatewayOrderId` to display Razorpay checkout modal

#### Step 4: Customer Completes Payment on Razorpay
(Customer enters card details, OTP, etc.)

#### Step 5: Verify Payment with Backend
```
POST /api/payments/razorpay/verify
Content-Type: application/json

{
  "razorpayOrderId": "order_xyz123",
  "razorpayPaymentId": "pay_abc456",
  "razorpaySignature": "9ef4dffbfd84f1318f6739a3ce19f9d85851857ae648f114332d8401e0949a3d"
}
```

**Response:** (Payment Confirmed)
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

#### Step 6: Frontend - Show Success Message
Display order confirmation, payment details, tracking info

---

## 📊 Field Mapping Table

### PaymentRequestDto → PaymentResponse
```
INPUT (PaymentRequestDto)           OUTPUT (PaymentResponse)
┌─────────────────────────┐        ┌──────────────────────────┐
│ orderId      → 456      │        │ paymentId    → 101       │
│ paymentMethod→ RAZORPAY │   →    │ transactionId→ order_... │
│                         │        │ amount       → 1050.00   │
│                         │        │ paymentStatus→ PENDING   │
│                         │        │ paymentGateway→ RAZORPAY │
│                         │        │ gatewayOrderId→ order_...│
└─────────────────────────┘        └──────────────────────────┘
```

### OrderRequestDto → OrderResponseDto
```
INPUT (OrderRequestDto)             OUTPUT (OrderResponseDto)
┌──────────────────────┐           ┌─────────────────────────┐
│ userId   → 123       │           │ orderId      → 456      │
│ addressId→ 789       │      →    │ productId    → 100      │
│ paymentMethod→ RAZORPAY           │ productName  → iPhone...│
│                      │           │ quantity     → 1        │
│                      │           │ price        → 999.99   │
│                      │           │ orderStatus  → PLACED   │
│                      │           │ paymentStatus→ PENDING  │
└──────────────────────┘           └─────────────────────────┘
```

---

## 🎯 Frontend Integration Checklist

### Before Payment
- [ ] Cart has items
- [ ] User is logged in
- [ ] Address selected (has addressId)
- [ ] Payment method selected (RAZORPAY, etc.)
- [ ] Total amount calculated
- [ ] Prepare OrderRequestDto

### Payment Initiation
- [ ] Call: `POST /api/orders/{userId}` → Get orderId
- [ ] Call: `POST /api/payments/` → Get gatewayOrderId (for Razorpay)
- [ ] Extract `gatewayOrderId` from response
- [ ] Show Razorpay checkout modal

### During Payment
- [ ] Customer completes payment in Razorpay modal
- [ ] Razorpay returns: razorpayOrderId, razorpayPaymentId, razorpaySignature
- [ ] Send verification request to backend

### After Payment
- [ ] Call: `POST /api/payments/razorpay/verify` → Verify signature
- [ ] Check response: paymentStatus should be "PAID"
- [ ] Show success message
- [ ] Display order confirmation details
- [ ] Redirect to order tracking page
- [ ] Clear cart on frontend

### Error Handling
- [ ] Handle 400 Bad Request errors
- [ ] Display user-friendly error messages
- [ ] Provide retry option for failed payments
- [ ] Log errors for debugging

---

## 🔐 Security Notes

### For Frontend Developers
- **Never expose Razorpay Secret Key** in frontend
- Always verify payment on backend using signature
- Use HTTPS for all API calls
- Store paymentId safely (don't expose to user)
- Validate all responses from backend

### For Backend Verification
- Signature verification happens here (not frontend)
- Backend validates: `razorpayOrderId`, `razorpayPaymentId`, `razorpaySignature`
- Only after verification, payment status is updated to PAID
- Frontend trusts backend verification

---

## 📱 Example Frontend Implementation (React/JavaScript)

### Step 1: Place Order
```javascript
const response = await fetch(`/api/orders/${userId}`, {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    addressId: 789,
    paymentMethod: 'RAZORPAY'
  })
});
const orderData = await response.json();
const orderId = orderData[0].orderId; // First item's order ID
```

### Step 2: Process Payment
```javascript
const paymentResponse = await fetch('/api/payments/', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    orderId: orderId,
    paymentMethod: 'RAZORPAY'
  })
});
const payment = await paymentResponse.json();
const gatewayOrderId = payment.gatewayOrderId;
```

### Step 3: Show Razorpay Checkout
```javascript
const options = {
  key: 'YOUR_RAZORPAY_KEY', // From .env
  order_id: gatewayOrderId,
  amount: payment.amount * 100, // Razorpay expects paise
  currency: 'INR',
  handler: function(response) {
    // Step 5: Verify Payment
    verifyPayment(response);
  }
};
const rzp = new Razorpay(options);
rzp.open();
```

### Step 4: Verify Payment
```javascript
async function verifyPayment(response) {
  const verifyResponse = await fetch('/api/payments/razorpay/verify', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      razorpayOrderId: response.razorpay_order_id,
      razorpayPaymentId: response.razorpay_payment_id,
      razorpaySignature: response.razorpay_signature
    })
  });
  const result = await verifyResponse.json();
  
  if (result.paymentStatus === 'PAID') {
    console.log('Payment successful!');
    // Show success message
  }
}
```

---

## ⚠️ Important Notes

### Status Values (Enums)
As of the latest update, all status values are now **type-safe enums**:

**PaymentStatus:**
- PENDING, SUCCESS, FAILED, PAID, REFUNDED, CANCELLED

**OrderStatus:**
- PENDING, PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED, FAILED, RETURNED

### Request/Response Format
- All requests should send status as **STRING** (e.g., `"RAZORPAY"`)
- All responses return status as **STRING** (e.g., `"PAID"`)
- Backend automatically handles String ↔ Enum conversion

### Amount Handling
- **API returns:** Amount in INR (e.g., 1050.00)
- **Razorpay expects:** Amount in Paise (multiply by 100)
- **Frontend should:** Multiply amount by 100 before sending to Razorpay

---

## 📞 Quick Reference

| Endpoint | Method | Use Case | Frontend Needs |
|----------|--------|----------|---|
| `/api/orders/{userId}` | POST | Place order | userId, addressId, paymentMethod |
| `/api/payments/` | POST | Initiate payment | orderId, paymentMethod |
| `/api/payments/razorpay/verify` | POST | Verify Razorpay | razorpayOrderId, razorpayPaymentId, razorpaySignature |
| `/api/payments/{paymentId}` | GET | Get payment details | paymentId |
| `/api/payments/user/{userId}` | GET | Get user's payments | userId |
| `/api/payments/{paymentId}/refund` | PATCH | Refund payment | paymentId |

---

**Ready to integrate? Use this spec for frontend development!**
