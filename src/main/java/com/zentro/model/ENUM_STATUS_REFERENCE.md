# Payment & Order Status Enums - Complete Reference

## 📋 Overview
Both `Payment` and `Order` models now use type-safe enums for status management instead of String values. This provides:
- ✅ Type safety at compile time
- ✅ Prevents invalid status values
- ✅ Database constraints (VARCHAR(20))
- ✅ Better IDE autocomplete support

---

## 1. PaymentStatus Enum

### Location
`src/main/java/com/zentro/model/PaymentStatus.java`

### Enum Values

| Value | Description | Use Case |
|-------|-------------|----------|
| **PENDING** | Awaiting payment confirmation | Initial state when Razorpay order is created |
| **SUCCESS** | Payment completed successfully | Alternative to PAID |
| **FAILED** | Payment failed | When payment gateway rejects transaction |
| **PAID** | Payment has been received | Successful payment completion |
| **REFUNDED** | Payment has been refunded | After refund is processed |
| **CANCELLED** | Payment was cancelled | User cancelled payment |

### Usage Examples

```java
// In Payment model
@Enumerated(EnumType.STRING)
@Column(columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
private PaymentStatus paymentStatus;

// Setting status
payment.setPaymentStatus(PaymentStatus.PENDING);
payment.setPaymentStatus(PaymentStatus.PAID);

// Getting status (returns enum)
PaymentStatus status = payment.getPaymentStatus();

// For API responses (automatically converts to String)
String statusString = payment.getPaymentStatus().toString(); // "PENDING"
```

### Payment Lifecycle

```
Order Created
    ↓
Process Payment → PaymentStatus.PENDING (Razorpay order created)
    ↓
Razorpay Verification → PaymentStatus.PAID (Payment confirmed)
    ↓
(Optional) Refund Request → PaymentStatus.REFUNDED
```

---

## 2. OrderStatus Enum

### Location
`src/main/java/com/zentro/model/OrderStatus.java`

### Enum Values

| Value | Description | Use Case |
|-------|-------------|----------|
| **PENDING** | Order awaiting payment | Initial state before payment |
| **PLACED** | Order has been placed | After order creation |
| **CONFIRMED** | Order is confirmed | After payment succeeds |
| **SHIPPED** | Order is on the way | When dispatch happens |
| **DELIVERED** | Order has been delivered | Final successful state |
| **CANCELLED** | Order has been cancelled | User or system cancellation |
| **FAILED** | Order creation failed | Transaction failure |
| **RETURNED** | Order has been returned | Post-delivery return |

### Usage Examples

```java
// In Order model
@Enumerated(EnumType.STRING)
@Column(columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
private OrderStatus orderStatus;

@Enumerated(EnumType.STRING)
@Column(columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
private PaymentStatus paymentStatus;

// Setting status
order.setOrderStatus(OrderStatus.PLACED);
order.setPaymentStatus(PaymentStatus.PENDING);

// Getting status
String status = order.getOrderStatus(); // Automatically converts to String via toString()
```

### Order Lifecycle

```
Cart Items Selected
    ↓
Place Order → OrderStatus.PLACED, PaymentStatus.PENDING
    ↓
Payment Processing → OrderStatus.PLACED, PaymentStatus.PENDING
    ↓
Payment Verified → OrderStatus.CONFIRMED, PaymentStatus.PAID
    ↓
Order Dispatched → OrderStatus.SHIPPED
    ↓
Order Delivered → OrderStatus.DELIVERED
```

---

## 3. Complete Flow: Payment + Order

```
Frontend: POST /api/orders/{userId}
├─ Input: { "addressId": 123, "paymentMethod": "razorpay" }
├─ Creates: Order with OrderStatus.PLACED, PaymentStatus.PENDING
└─ Returns: OrderResponseDto

Frontend: POST /api/payments/
├─ Input: { "orderId": 456, "paymentMethod": "razorpay" }
├─ Creates: Payment with PaymentStatus.PENDING
├─ Calls: Razorpay API
├─ Returns: PaymentResponse (with gatewayOrderId)
└─ Status: PaymentStatus.PENDING

User Completes Razorpay Payment
    ↓

Frontend: POST /api/payments/razorpay/verify
├─ Input: { "razorpayOrderId": "...", "razorpayPaymentId": "...", "razorpaySignature": "..." }
├─ Verifies: Signature with backend
├─ Updates: Payment.paymentStatus = PaymentStatus.PAID
├─ Updates: Order.paymentStatus = PaymentStatus.PAID
├─ Updates: Order.orderStatus = OrderStatus.CONFIRMED
└─ Returns: PaymentResponse (with PaymentStatus.PAID)
```

---

## 4. Database Schema

### Payment Table
```sql
CREATE TABLE payment (
    payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    payment_method VARCHAR(50),
    transaction_id VARCHAR(100),
    payment_gateway VARCHAR(50),
    paid_amount DOUBLE,
    payment_status VARCHAR(20) DEFAULT 'PENDING',  -- Enum stored as String
    paid_at TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);
```

### Order Table
```sql
CREATE TABLE orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    order_status VARCHAR(20) DEFAULT 'PENDING',    -- Enum stored as String
    payment_status VARCHAR(20) DEFAULT 'PENDING',  -- Enum stored as String
    total_amount DOUBLE,
    tax_amount DOUBLE,
    shipping_charge DOUBLE,
    discount_amount DOUBLE,
    final_amount DOUBLE,
    order_tracking_number VARCHAR(100),
    ordered_at TIMESTAMP,
    delivered_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## 5. API Response Examples

### Order Response
```json
{
  "orderId": 456,
  "productId": 789,
  "productName": "Product Name",
  "quantity": 2,
  "price": 500.00,
  "totalPrice": 1000.00,
  "orderStatus": "PLACED",           // String representation of OrderStatus enum
  "paymentStatus": "PENDING",        // String representation of PaymentStatus enum
  "orderedAt": "2026-07-19T11:15:43"
}
```

### Payment Response
```json
{
  "paymentId": 101,
  "transactionId": "txn_xxxxx",
  "amount": 1000.00,
  "paymentStatus": "PENDING",        // String representation of PaymentStatus enum
  "paidAt": "2026-07-19T11:20:00",
  "paymentGateway": "RAZORPAY",
  "gatewayOrderId": "order_xxxxx"
}
```

---

## 6. Validation Rules

### PaymentStatus Transitions
```
✅ PENDING → PAID      (Successful payment)
✅ PENDING → FAILED    (Payment failed)
✅ PENDING → CANCELLED (User cancelled)
✅ PAID → REFUNDED     (Refund requested)
❌ FAILED → PAID       (Cannot recover)
❌ REFUNDED → PAID     (Cannot revert)
```

### OrderStatus Transitions
```
✅ PENDING → PLACED     (Order placed)
✅ PLACED → CONFIRMED   (Payment received)
✅ CONFIRMED → SHIPPED  (Dispatched)
✅ SHIPPED → DELIVERED  (Completed)
✅ PLACED → CANCELLED   (Before confirmation)
✅ DELIVERED → RETURNED (Post-delivery)
❌ DELIVERED → SHIPPED  (Cannot go backward)
❌ CANCELLED → PLACED   (Cannot revert)
```

---

## 7. Updated Service Methods

### PaymentServiceImple
```java
// Razorpay integration
payment.setPaymentStatus(PaymentStatus.PENDING);

// Verification
payment.setPaymentStatus(PaymentStatus.PAID);
order.setPaymentStatus(PaymentStatus.PAID);

// Refund
payment.setPaymentStatus(PaymentStatus.REFUNDED);
order.setPaymentStatus(PaymentStatus.REFUNDED);
```

### OrderServiceImple
```java
// Place Order
order.setOrderStatus(OrderStatus.PLACED);
order.setPaymentStatus(PaymentStatus.PENDING);

// Cancel Order
order.setOrderStatus(OrderStatus.CANCELLED);

// Update Status (with validation)
OrderStatus status = OrderStatus.valueOf(statusString);
order.setOrderStatus(status);
```

---

## 8. Frontend Integration Notes

### When Receiving API Responses
- Status values are returned as **Strings** (e.g., "PENDING", "PAID")
- Map these strings to your frontend enum/constants for type safety
- Example: `const PaymentStatus = { PENDING: 'PENDING', PAID: 'PAID', ... }`

### When Sending Status Updates
- Convert your frontend status to uppercase string
- Backend automatically converts to enum
- Example: `PATCH /api/orders/456/status/SHIPPED`

### Valid Status Values to Send
```
OrderStatus:    PENDING, PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED, FAILED, RETURNED
PaymentStatus:  PENDING, SUCCESS, FAILED, PAID, REFUNDED, CANCELLED
```

---

## 9. Query Methods

### Check Payment Status
```java
if (payment.getPaymentStatus() == PaymentStatus.PAID) {
    // Process refund
}

// Or compare with string from API
if (payment.getPaymentStatus().toString().equals("PAID")) {
    // Same as above
}
```

### Check Order Status
```java
if (order.getOrderStatus() == OrderStatus.CONFIRMED) {
    // Ready to ship
}

OrderStatus status = OrderStatus.valueOf("SHIPPED");
order.setOrderStatus(status);
```

---

## 10. Migration Notes

✅ **All string literals replaced with enums**
- Old: `payment.setPaymentStatus("PAID")`
- New: `payment.setPaymentStatus(PaymentStatus.PAID)`

✅ **Database compatibility maintained**
- Enums stored as VARCHAR(20) in database
- Existing string data remains compatible

✅ **API response format unchanged**
- Frontend receives enum names as strings
- No breaking changes to API contracts

✅ **Type safety improved**
- Compile-time validation
- IDE autocomplete support
- Prevents typos in status values
