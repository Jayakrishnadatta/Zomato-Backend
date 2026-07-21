# Payment & Order Status Enums - Quick Reference Guide

## 📌 Enum Values at a Glance

### PaymentStatus Enum
| Constant | Value | Meaning | When Used |
|----------|-------|---------|-----------|
| PENDING | PENDING | Awaiting payment confirmation | After Razorpay order creation |
| SUCCESS | SUCCESS | Payment completed successfully | Alternative to PAID |
| FAILED | FAILED | Payment transaction failed | When gateway rejects |
| PAID | PAID | Payment has been received | After successful verification |
| REFUNDED | REFUNDED | Payment has been refunded | After refund process |
| CANCELLED | CANCELLED | Payment was cancelled | User cancelled payment |

**Recommended Primary Flow:** PENDING → PAID

---

### OrderStatus Enum
| Constant | Value | Meaning | When Used |
|----------|-------|---------|-----------|
| PENDING | PENDING | Order awaiting payment | Before payment attempt |
| PLACED | PLACED | Order has been placed | Immediately after order creation |
| CONFIRMED | CONFIRMED | Order is confirmed | After payment is verified |
| SHIPPED | SHIPPED | Order is on the way | After dispatch from warehouse |
| DELIVERED | DELIVERED | Order has been delivered | After successful delivery |
| CANCELLED | CANCELLED | Order has been cancelled | User or system cancellation |
| FAILED | FAILED | Order creation failed | Transaction/processing failure |
| RETURNED | RETURNED | Order has been returned | Post-delivery return request |

**Recommended Primary Flow:** PLACED → CONFIRMED → SHIPPED → DELIVERED

---

## 🔗 Status Correlations

### Order Payment Status During Payment Process

| OrderStatus | PaymentStatus | State Description |
|----------|----------|-------------------|
| PLACED | PENDING | Awaiting customer payment |
| PLACED | PAID | Customer has paid |
| CONFIRMED | PAID | Order confirmed after payment |
| SHIPPED | PAID | Order dispatched, payment collected |
| DELIVERED | PAID | Order delivered, payment finalized |
| CANCELLED | PENDING | Order cancelled before payment |
| CANCELLED | REFUNDED | Order cancelled, refund issued |

---

## 🎯 Common Status Combinations

### Scenario 1: Successful Payment Flow
```
OrderStatus: PLACED → CONFIRMED → SHIPPED → DELIVERED
PaymentStatus: PENDING → PAID → PAID → PAID
Timeline: Order → Payment → Dispatch → Delivery
```

### Scenario 2: Failed Payment
```
OrderStatus: PLACED → PLACED (or FAILED)
PaymentStatus: PENDING → FAILED
Result: Order remains unconfirmed, no stock deduction
```

### Scenario 3: Order Cancellation
```
OrderStatus: PLACED → CANCELLED
PaymentStatus: PENDING (no refund) or REFUNDED (if paid)
Result: Stock restored, inventory adjusted
```

### Scenario 4: Return After Delivery
```
OrderStatus: DELIVERED → RETURNED
PaymentStatus: PAID → REFUNDED
Timeline: Delivery → Return Request → Refund Processed
```

---

## 💻 Code Usage Examples

### Using PaymentStatus

```java
// Setting status
payment.setPaymentStatus(PaymentStatus.PENDING);
payment.setPaymentStatus(PaymentStatus.PAID);

// Getting status (returns enum)
PaymentStatus status = payment.getPaymentStatus();

// Checking status
if (payment.getPaymentStatus() == PaymentStatus.PAID) {
    // Process accordingly
}

// Converting to string (for logging/display)
String statusString = payment.getPaymentStatus().toString(); // "PAID"

// Getting description
String desc = payment.getPaymentStatus().getDescription();
// Output: "Paid - Payment has been received"
```

### Using OrderStatus

```java
// Setting status
order.setOrderStatus(OrderStatus.PLACED);
order.setOrderStatus(OrderStatus.CONFIRMED);

// Getting status
OrderStatus status = order.getOrderStatus();

// Checking status
if (order.getOrderStatus() == OrderStatus.CONFIRMED) {
    // Process shipping
}

// Converting from string (from API request)
try {
    OrderStatus status = OrderStatus.valueOf("SHIPPED");
    order.setOrderStatus(status);
} catch (IllegalArgumentException e) {
    // Invalid status provided
}
```

---

## 📊 API Request/Response Examples

### Request: Update Order Status
```
PATCH /api/orders/456/status/SHIPPED

Valid values: PENDING, PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED, FAILED, RETURNED
```

### Response: Get Order
```json
{
  "orderId": 456,
  "orderStatus": "CONFIRMED",      // Enum as string
  "paymentStatus": "PAID",          // Enum as string
  "totalAmount": 1000.00,
  "finalAmount": 1050.00,
  "orderedAt": "2026-07-19T11:15:43"
}
```

### Response: Get Payment
```json
{
  "paymentId": 101,
  "amount": 1050.00,
  "paymentStatus": "PAID",         // Enum as string
  "transactionId": "pay_xxxxx",
  "paymentGateway": "RAZORPAY",
  "paidAt": "2026-07-19T11:20:00"
}
```

---

## ⚡ Frontend Integration Checklist

- [ ] Update TypeScript/JavaScript enum definitions to match backend
- [ ] Create status display mappings for UI labels
- [ ] Handle all enum values in status change handlers
- [ ] Validate status before sending to API
- [ ] Add error handling for invalid status values
- [ ] Update order tracking UI with new statuses
- [ ] Update payment status display pages
- [ ] Add transitions/animations for status changes
- [ ] Test all status transitions
- [ ] Update documentation/user guides

---

## 🔒 Validation Rules

### Cannot transition directly (invalid):
```
❌ DELIVERED → CONFIRMED (going backward)
❌ CANCELLED → PLACED (cannot revert cancellation)
❌ FAILED → CONFIRMED (cannot recover from failure)
❌ REFUNDED → PAID (cannot unreverse)
```

### Allowed transitions (valid):
```
✅ PLACED → CONFIRMED (after payment)
✅ CONFIRMED → SHIPPED (dispatch)
✅ SHIPPED → DELIVERED (delivery)
✅ PLACED → CANCELLED (before confirmation)
✅ DELIVERED → RETURNED (post-delivery)
✅ PAID → REFUNDED (refund request)
```

---

## 📝 Database Column Details

### Payment Table
```sql
payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
```
Allowed values: `PENDING`, `SUCCESS`, `FAILED`, `PAID`, `REFUNDED`, `CANCELLED`

### Orders Table
```sql
order_status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
```
Allowed values:
- `order_status`: `PENDING`, `PLACED`, `CONFIRMED`, `SHIPPED`, `DELIVERED`, `CANCELLED`, `FAILED`, `RETURNED`
- `payment_status`: `PENDING`, `SUCCESS`, `FAILED`, `PAID`, `REFUNDED`, `CANCELLED`

---

## 🚀 Implementation Status

✅ **Completed:**
1. Created `PaymentStatus` enum (6 values)
2. Created `OrderStatus` enum (8 values)
3. Updated `Payment` model with enum
4. Updated `Order` model with enum
5. Updated `PaymentServiceImple` to use enums
6. Updated `OrderServiceImple` to use enums
7. Added proper imports and annotations
8. Added validation for status transitions

⏳ **Still Needed:**
- [ ] Update DTOs if needed
- [ ] Update repository queries if using string filters
- [ ] Add validation layer for status transitions
- [ ] Update front-end to handle new values
- [ ] Add unit tests for enum transitions
- [ ] Database schema verification

---

## 🔍 Where to Find Enums

| File | Location | Purpose |
|------|----------|---------|
| `PaymentStatus.java` | `src/main/java/com/zentro/model/` | Payment status enum |
| `OrderStatus.java` | `src/main/java/com/zentro/model/` | Order status enum |
| `Payment.java` | `src/main/java/com/zentro/model/` | Uses PaymentStatus |
| `Order.java` | `src/main/java/com/zentro/model/` | Uses OrderStatus & PaymentStatus |
| `PaymentServiceImple.java` | `src/main/java/com/zentro/serviceImple/` | Manages PaymentStatus |
| `OrderServiceImple.java` | `src/main/java/com/zentro/serviceImple/` | Manages OrderStatus |

---

## 📞 Support Reference

### Common Issues & Solutions

**Issue:** "Invalid order status" error
```
Solution: Use valid enum values only (PENDING, PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED, FAILED, RETURNED)
```

**Issue:** Status not updating
```
Solution: Make sure to use PaymentStatus.PAID not "PAID" in code
```

**Issue:** Database query fails
```
Solution: Enums are stored as strings in DB, queries still use enum names
```

---

## 📚 Reference Links

- Payment Flow: See `ENUM_STATUS_REFERENCE.md` - Section 3
- Order Lifecycle: See `ENUM_STATUS_REFERENCE.md` - Section 2
- Complete Flow: See `ENUM_STATUS_REFERENCE.md` - Section 3
- Files Modified: See `ENUM_IMPLEMENTATION_SUMMARY.md` - Files Modified section
