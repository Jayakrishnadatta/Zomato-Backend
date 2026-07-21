# 🎨 Frontend Payment Integration Guide

## 🚀 Quick Start for Frontend Team

### What Frontend Needs to Send

#### **Step 1: When User Clicks "Checkout"**
```javascript
// Frontend collects from user
const checkoutData = {
  userId: 123,              // User ID (from login)
  addressId: 789,           // Selected shipping address
  paymentMethod: "RAZORPAY" // Selected payment gateway
};
```

#### **Step 2: Create Order (First API Call)**
```javascript
POST http://localhost:8080/api/orders/123

BODY: {
  "addressId": 789,
  "paymentMethod": "RAZORPAY"
}

RESPONSE: [
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

**What to Extract:**
- `orderId = 456` (Use for next step)

---

#### **Step 3: Process Payment (Second API Call)**
```javascript
POST http://localhost:8080/api/payments/

BODY: {
  "orderId": 456,
  "paymentMethod": "RAZORPAY"
}

RESPONSE: {
  "paymentId": 101,
  "transactionId": "order_xyz123",
  "amount": 1050.00,
  "paymentStatus": "PENDING",
  "paidAt": null,
  "paymentGateway": "RAZORPAY",
  "gatewayOrderId": "order_xyz123"
}
```

**What to Extract:**
- `gatewayOrderId = "order_xyz123"` (Use for Razorpay)
- `paymentId = 101` (Store for reference)

---

#### **Step 4: Show Razorpay Checkout Modal**
```javascript
// Use gatewayOrderId from previous response
const options = {
  key: "YOUR_RAZORPAY_PUBLIC_KEY", // From environment
  order_id: "order_xyz123",         // From API response
  amount: 105000,                   // amount * 100 (paise)
  currency: "INR",
  handler: handlePaymentSuccess,
  prefill: {
    name: "Customer Name",
    email: "customer@email.com",
    contact: "9876543210"
  }
};

const rzp = new Razorpay(options);
rzp.open();
```

---

#### **Step 5: Verify Payment After Success (Third API Call)**
```javascript
// When customer completes payment on Razorpay

POST http://localhost:8080/api/payments/razorpay/verify

BODY: {
  "razorpayOrderId": "order_xyz123",      // From Razorpay response
  "razorpayPaymentId": "pay_abc456",      // From Razorpay response
  "razorpaySignature": "9ef4d..."         // From Razorpay response
}

RESPONSE: {
  "paymentId": 101,
  "transactionId": "pay_abc456",
  "amount": 1050.00,
  "paymentStatus": "PAID",               // SUCCESS!
  "paidAt": "2026-07-19T11:24:35",
  "paymentGateway": "RAZORPAY",
  "gatewayOrderId": "order_xyz123"
}
```

**Check:** If `paymentStatus === "PAID"` → Show success message

---

## 📝 Request/Response Field Reference

### Payment Request Fields (Sent to Backend)
```json
{
  "orderId": "Long - Order ID from order creation",
  "paymentMethod": "String - Payment gateway (RAZORPAY, CREDIT_CARD, etc.)"
}
```

### Payment Response Fields (From Backend)
```json
{
  "paymentId": "Long - Unique payment ID in system",
  "transactionId": "String - Razorpay Order ID or Payment ID",
  "amount": "Double - Payment amount (e.g., 1050.00)",
  "paymentStatus": "String - Status: PENDING, PAID, FAILED, REFUNDED, CANCELLED",
  "paidAt": "DateTime - When payment was made (null if PENDING)",
  "paymentGateway": "String - Gateway used (RAZORPAY)",
  "gatewayOrderId": "String - Razorpay order reference"
}
```

### Order Request Fields
```json
{
  "addressId": "Long - Shipping address ID",
  "paymentMethod": "String - Payment method (RAZORPAY)"
}
```

### Order Response Fields (In Array)
```json
{
  "orderId": "Long - Order ID",
  "productId": "Long - Product ID",
  "productName": "String - Product name",
  "quantity": "Integer - Quantity ordered",
  "price": "Double - Unit price",
  "totalPrice": "Double - Total price for item",
  "orderStatus": "String - Status: PLACED, CONFIRMED, SHIPPED, DELIVERED",
  "paymentStatus": "String - Status: PENDING, PAID",
  "orderedAt": "DateTime - Order creation time"
}
```

---

## 🎯 Complete Integration Example

### HTML Template
```html
<div class="checkout">
  <!-- Shipping Address Selection -->
  <select id="addressSelect" required>
    <option value="">Select Address</option>
    <option value="789">Home - 123 Main St</option>
    <option value="790">Office - 456 Work Ave</option>
  </select>

  <!-- Payment Method Selection -->
  <select id="paymentMethod" required>
    <option value="RAZORPAY">Razorpay</option>
    <option value="CREDIT_CARD">Credit Card</option>
    <option value="DEBIT_CARD">Debit Card</option>
    <option value="UPI">UPI</option>
  </select>

  <!-- Order Summary -->
  <div id="orderSummary">
    <p>Total Amount: <span id="totalAmount">₹ 0</span></p>
  </div>

  <!-- Checkout Button -->
  <button onclick="initiateCheckout()">Pay Now</button>
</div>

<!-- Razorpay Script -->
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
```

### JavaScript Implementation
```javascript
const userId = 123; // From login
let orderId = null;
let paymentId = null;

async function initiateCheckout() {
  try {
    const addressId = document.getElementById('addressSelect').value;
    const paymentMethod = document.getElementById('paymentMethod').value;

    if (!addressId || !paymentMethod) {
      alert('Please select address and payment method');
      return;
    }

    // Step 1: Create Order
    const orderResponse = await fetch(`/api/orders/${userId}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        addressId: parseInt(addressId),
        paymentMethod: paymentMethod
      })
    });

    if (!orderResponse.ok) throw new Error('Order creation failed');
    const orderData = await orderResponse.json();
    orderId = orderData[0].orderId;
    const totalAmount = orderData[0].totalPrice;
    document.getElementById('totalAmount').textContent = `₹ ${totalAmount}`;

    // Step 2: Process Payment
    const paymentResponse = await fetch('/api/payments/', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        orderId: orderId,
        paymentMethod: paymentMethod
      })
    });

    if (!paymentResponse.ok) throw new Error('Payment processing failed');
    const paymentData = await paymentResponse.json();
    paymentId = paymentData.paymentId;

    // Step 3: Initiate Razorpay
    if (paymentMethod === 'RAZORPAY') {
      showRazorpayCheckout(paymentData, totalAmount);
    }

  } catch (error) {
    console.error('Error:', error);
    alert('Failed to initiate payment: ' + error.message);
  }
}

function showRazorpayCheckout(paymentData, amount) {
  const options = {
    key: 'YOUR_RAZORPAY_PUBLIC_KEY', // Store in .env
    order_id: paymentData.gatewayOrderId,
    amount: amount * 100, // Convert to paise
    currency: 'INR',
    name: 'Zentro Store',
    description: 'Order Payment',
    handler: async function(response) {
      await verifyPayment(response);
    },
    prefill: {
      name: 'Customer Name',
      email: 'customer@example.com'
    },
    theme: {
      color: '#3399cc'
    }
  };

  const rzp = new Razorpay(options);
  rzp.open();
}

async function verifyPayment(razorpayResponse) {
  try {
    // Step 4: Verify Payment
    const verifyResponse = await fetch('/api/payments/razorpay/verify', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        razorpayOrderId: razorpayResponse.razorpay_order_id,
        razorpayPaymentId: razorpayResponse.razorpay_payment_id,
        razorpaySignature: razorpayResponse.razorpay_signature
      })
    });

    if (!verifyResponse.ok) throw new Error('Payment verification failed');
    
    const result = await verifyResponse.json();

    if (result.paymentStatus === 'PAID') {
      showSuccessMessage(result);
      clearCart();
      redirectToOrderTracking(orderId);
    } else {
      showErrorMessage('Payment failed');
    }

  } catch (error) {
    console.error('Verification error:', error);
    showErrorMessage('Failed to verify payment');
  }
}

function showSuccessMessage(paymentData) {
  alert(`✅ Payment Successful!
Order ID: ${orderId}
Payment ID: ${paymentData.paymentId}
Amount: ₹${paymentData.amount}`);
}

function showErrorMessage(message) {
  alert(`❌ ${message}`);
}

function clearCart() {
  // Clear cart from frontend state/localStorage
  localStorage.removeItem('cart');
  // Or dispatch Redux action, etc.
}

function redirectToOrderTracking(orderId) {
  window.location.href = `/orders/${orderId}`;
}
```

---

## 🎨 UI/UX Components Needed

### 1. Address Selection Dropdown
```javascript
// Format: [{ id, label }, ...]
const addresses = [
  { id: 789, label: '📍 Home - 123 Main St, City' },
  { id: 790, label: '💼 Office - 456 Work Ave, City' }
];
```

### 2. Payment Method Selection
```javascript
const paymentMethods = [
  { id: 'RAZORPAY', label: 'Razorpay', icon: '💳' },
  { id: 'CREDIT_CARD', label: 'Credit Card', icon: '💰' },
  { id: 'DEBIT_CARD', label: 'Debit Card', icon: '💰' },
  { id: 'UPI', label: 'UPI', icon: '📱' }
];
```

### 3. Order Summary Display
```
Order Summary:
├─ iPhone 15 Pro x1        ₹ 999.99
├─ Apple Watch x1          ₹ 399.99
├─ Subtotal                ₹ 1,399.98
├─ Tax (0%)                ₹ 0.00
├─ Shipping                ₹ 0.00
└─ Total                   ₹ 1,399.98
```

### 4. Payment Status Indicators
```javascript
const statusColors = {
  'PENDING': 'orange',  // Awaiting payment
  'PAID': 'green',      // Payment successful
  'FAILED': 'red',      // Payment failed
  'REFUNDED': 'blue',   // Money refunded
  'CANCELLED': 'grey'   // Payment cancelled
};
```

---

## ⚠️ Error Handling Guide

### Common Errors & Solutions

| Error | Cause | Solution |
|-------|-------|----------|
| "Order not found" | Invalid orderId | Create order first |
| "Order is already paid" | Duplicate payment attempt | Check order status first |
| "Cart is empty" | No items in cart | Add items to cart |
| "Invalid Razorpay signature" | Wrong credentials | Verify API keys |
| "Address not found" | Invalid addressId | Select valid address |
| "Payment not found" | Invalid paymentId | Create payment first |

### Error Handling Code
```javascript
async function handleApiCall(apiUrl, options) {
  try {
    const response = await fetch(apiUrl, options);
    
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.error || 'API Error');
    }
    
    return await response.json();
  } catch (error) {
    console.error('API Error:', error);
    showUserFriendlyError(error.message);
    throw error;
  }
}

function showUserFriendlyError(error) {
  const messages = {
    'Order not found': '❌ Order could not be found. Please try again.',
    'Order is already paid': '⚠️ This order has already been paid.',
    'Cart is empty': '❌ Please add items to cart before checkout.',
    'Invalid Razorpay signature': '❌ Payment verification failed. Please retry.',
    'Address not found': '❌ Selected address is invalid. Please select another.',
    'default': '❌ An error occurred. Please try again.'
  };
  
  alert(messages[error] || messages['default']);
}
```

---

## 🔒 Security Best Practices

### Do's ✅
- [x] Store Razorpay public key in `.env` file
- [x] Always verify payment on backend
- [x] Use HTTPS for all API calls
- [x] Never log sensitive data (signatures, keys)
- [x] Validate user input before sending to API
- [x] Store paymentId for reference (not sensitive)

### Don'ts ❌
- [ ] Never expose Razorpay secret key in frontend
- [ ] Never skip payment verification
- [ ] Never trust frontend validation alone
- [ ] Never store payment details in localStorage
- [ ] Never hardcode API credentials
- [ ] Never expose API keys in version control

---

## 📱 Environment Configuration

### `.env` File (Frontend)
```bash
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_RAZORPAY_KEY=YOUR_PUBLIC_KEY_HERE
REACT_APP_RAZORPAY_SECRET=DO_NOT_PUT_SECRET_HERE
```

### Usage in Code
```javascript
const API_BASE = process.env.REACT_APP_API_BASE_URL;
const RAZORPAY_KEY = process.env.REACT_APP_RAZORPAY_KEY;

// Example API call
fetch(`${API_BASE}/payments/`)
```

---

## 🧪 Testing Checklist

### Payment Flow Testing
- [ ] Place order with valid address
- [ ] Verify orderId returned
- [ ] Process payment with orderId
- [ ] Verify paymentId and gatewayOrderId returned
- [ ] Complete Razorpay payment (test card)
- [ ] Verify payment on backend
- [ ] Check payment status is "PAID"
- [ ] Verify order cleared from cart

### Error Scenario Testing
- [ ] Place order without address
- [ ] Place order without payment method
- [ ] Process payment with invalid orderId
- [ ] Verify payment with wrong signature
- [ ] Handle network timeouts
- [ ] Handle API 500 errors

### Test Razorpay Cards
```
Card: 4111 1111 1111 1111
Expiry: Any future date
CVV: 123
```

---

## 📞 Support & Debugging

### Enable Debug Logging
```javascript
// Add to payment functions
console.log('Step 1 - Order Response:', orderData);
console.log('Step 2 - Payment Response:', paymentData);
console.log('Step 3 - Razorpay Response:', razorpayResponse);
console.log('Step 4 - Verify Response:', verifyResult);
```

### Network Inspection (DevTools)
1. Open Chrome DevTools → Network tab
2. Perform payment flow
3. Check each request:
   - `POST /api/orders/{userId}` → Status 200
   - `POST /api/payments/` → Status 200
   - `POST /api/payments/razorpay/verify` → Status 200
4. Check response bodies for expected fields

---

## 🎊 Summary

**Frontend needs to:**
1. Collect: userId, addressId, paymentMethod
2. Call: `POST /api/orders/{userId}` → Get orderId
3. Call: `POST /api/payments/` → Get gatewayOrderId
4. Show: Razorpay checkout modal
5. Call: `POST /api/payments/razorpay/verify` → Confirm payment
6. Check: paymentStatus === "PAID"
7. Display: Success message & redirect

**That's it! Follow the example code above for complete implementation.**
