# Status Enum Implementation - Visual Guide

## 📊 Enum Hierarchy

```
┌─────────────────────────────────────────────────────────────────┐
│                    STATUS ENUMS                                 │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────────────┐      ┌──────────────────────┐        │
│  │  PaymentStatus       │      │  OrderStatus         │        │
│  │  (6 values)          │      │  (8 values)          │        │
│  ├──────────────────────┤      ├──────────────────────┤        │
│  │ • PENDING            │      │ • PENDING            │        │
│  │ • SUCCESS            │      │ • PLACED             │        │
│  │ • FAILED             │      │ • CONFIRMED          │        │
│  │ • PAID (primary)     │      │ • SHIPPED            │        │
│  │ • REFUNDED           │      │ • DELIVERED          │        │
│  │ • CANCELLED          │      │ • CANCELLED          │        │
│  │                      │      │ • FAILED             │        │
│  │                      │      │ • RETURNED           │        │
│  └──────────────────────┘      └──────────────────────┘        │
│         ↓                              ↓                        │
│    Used in Payment                Used in Order                │
│    Entity                         Entity                       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🔄 Complete Order Lifecycle with Statuses

```
┌──────────────────────────────────────────────────────────────────────────┐
│                      ORDER LIFECYCLE FLOW                                │
└──────────────────────────────────────────────────────────────────────────┘

Step 1: ORDER CREATION
┌────────────────────────────────────────┐
│ POST /api/orders/{userId}              │
├────────────────────────────────────────┤
│ ✓ Cart items exist                     │
│ ✓ Address provided                     │
│ ✓ Payment method selected              │
└────────────────────────────────────────┘
                  ↓
        ┌─────────────────────┐
        │ Order Created       │
        │ OrderStatus: PLACED │
        │ PaymentStatus:      │
        │   PENDING           │
        └─────────────────────┘
                  ↓
Step 2: PAYMENT PROCESSING
┌────────────────────────────────────────┐
│ POST /api/payments/                    │
├────────────────────────────────────────┤
│ ✓ Order ID provided                    │
│ ✓ Payment method: RAZORPAY             │
│ ✓ Razorpay order created               │
└────────────────────────────────────────┘
                  ↓
        ┌─────────────────────┐
        │ Payment Created     │
        │ PaymentStatus:      │
        │   PENDING           │
        │ (Awaiting gateway)  │
        └─────────────────────┘
                  ↓
Step 3: USER PAYS ON RAZORPAY
        [User completes payment on Razorpay Gateway]
                  ↓
Step 4: PAYMENT VERIFICATION
┌────────────────────────────────────────┐
│ POST /api/payments/razorpay/verify     │
├────────────────────────────────────────┤
│ ✓ Razorpay Order ID                    │
│ ✓ Razorpay Payment ID                  │
│ ✓ Razorpay Signature verified          │
└────────────────────────────────────────┘
                  ↓
        ┌──────────────────────┐
        │ Order Confirmed      │
        │ OrderStatus:         │
        │   CONFIRMED          │
        │ PaymentStatus: PAID  │
        │ [Ready for dispatch] │
        └──────────────────────┘
                  ↓
Step 5: SHIPPING
┌────────────────────────────────────────┐
│ PATCH /api/orders/{orderId}/status/... │
├────────────────────────────────────────┤
│ Warehouse picks & packs order          │
│ Generates tracking number              │
│ Hands over to logistics                │
└────────────────────────────────────────┘
                  ↓
        ┌──────────────────────┐
        │ Order Shipped        │
        │ OrderStatus:         │
        │   SHIPPED            │
        │ PaymentStatus: PAID  │
        │ [In transit]         │
        └──────────────────────┘
                  ↓
Step 6: DELIVERY
        [Logistics delivers to customer]
                  ↓
        ┌──────────────────────┐
        │ Order Delivered      │
        │ OrderStatus:         │
        │   DELIVERED          │
        │ PaymentStatus: PAID  │
        │ [SUCCESS]            │
        └──────────────────────┘
                  ↓
        ✅ PROCESS COMPLETE
```

---

## ❌ Alternative Flows

### Scenario A: Payment Failed

```
Order Created (PLACED/PENDING)
            ↓
Process Payment → FAILS
            ↓
        ┌─────────────────────┐
        │ Payment Failed      │
        │ PaymentStatus:      │
        │   FAILED            │
        │ OrderStatus:        │
        │   PLACED (no change)│
        └─────────────────────┘
            ↓
    User must retry payment
    OR
    Order gets auto-cancelled after timeout
```

### Scenario B: User Cancels Order

```
Order Created (PLACED/PENDING)
            ↓
    User requests cancellation
            ↓
        ┌─────────────────────┐
        │ Order Cancelled     │
        │ OrderStatus:        │
        │   CANCELLED         │
        │ PaymentStatus:      │
        │   PENDING/REFUNDED  │
        └─────────────────────┘
            ↓
    ✓ Stock restored
    ✓ Cart cleared
```

### Scenario C: Return After Delivery

```
Order Delivered (DELIVERED/PAID)
            ↓
    Customer initiates return
            ↓
        ┌─────────────────────┐
        │ Order Returned      │
        │ OrderStatus:        │
        │   RETURNED          │
        │ PaymentStatus:      │
        │   REFUNDED          │
        └─────────────────────┘
            ↓
    ✓ Refund processed
    ✓ Stock restored
```

---

## 🎨 State Machine Diagram

```
PAYMENT STATUS STATE MACHINE:

              ┌──────────────────────────────┐
              │                              │
              ↓                              │
    ┌─────────────────┐                     │
    │    PENDING      │ ←────────────────────┘ (retry)
    └────┬────────────┘
         │ (success)
         ↓
    ┌─────────────────┐     ┌──────────────┐
    │     PAID        │────→│  REFUNDED    │
    └─────────────────┘     └──────────────┘
         ↑ (success)
         │ (alternative)
    ┌────────────┐
    │  SUCCESS   │
    └────────────┘

         │ (failure)
         ↓
    ┌─────────────────┐
    │     FAILED      │ (terminal)
    └─────────────────┘

    ┌─────────────────┐
    │   CANCELLED     │ (terminal)
    └─────────────────┘


ORDER STATUS STATE MACHINE:

    ┌──────────────┐
    │   PENDING    │ (alternative initial)
    └────┬─────────┘
         │ (or place directly)
         ↓
    ┌──────────────┐
    │   PLACED     │ ←──────────────────┐
    └────┬─────────┘                    │ (retry)
         │ (payment successful)         │
         ↓                              │
    ┌──────────────┐                    │
    │ CONFIRMED    │────────────────────┘
    └────┬─────────┘
         │ (dispatch)
         ↓
    ┌──────────────┐
    │   SHIPPED    │
    └────┬─────────┘
         │ (delivery)
         ↓
    ┌──────────────┐
    │ DELIVERED    │ (main success flow)
    └────┬─────────┘
         │ (return request)
         ↓
    ┌──────────────┐
    │  RETURNED    │ (terminal)
    └──────────────┘

    ┌──────────────┐
    │ CANCELLED    │ (terminal)
    └──────────────┘

    ┌──────────────┐
    │   FAILED     │ (terminal)
    └──────────────┘
```

---

## 📱 Frontend Status Display

### Payment Status Labels

```javascript
const PaymentStatusDisplay = {
    'PENDING': {
        label: 'Pending',
        color: 'orange',
        icon: 'clock',
        message: 'Awaiting payment confirmation'
    },
    'SUCCESS': {
        label: 'Success',
        color: 'green',
        icon: 'check',
        message: 'Payment successful'
    },
    'PAID': {
        label: 'Paid',
        color: 'green',
        icon: 'check',
        message: 'Payment confirmed'
    },
    'FAILED': {
        label: 'Failed',
        color: 'red',
        icon: 'x',
        message: 'Payment failed, please retry'
    },
    'REFUNDED': {
        label: 'Refunded',
        color: 'blue',
        icon: 'undo',
        message: 'Payment refunded'
    },
    'CANCELLED': {
        label: 'Cancelled',
        color: 'grey',
        icon: 'ban',
        message: 'Payment cancelled'
    }
};
```

### Order Status Labels

```javascript
const OrderStatusDisplay = {
    'PENDING': {
        label: 'Pending',
        color: 'grey',
        icon: 'hourglass',
        message: 'Order awaiting payment'
    },
    'PLACED': {
        label: 'Placed',
        color: 'blue',
        icon: 'shopping-cart',
        message: 'Order placed successfully'
    },
    'CONFIRMED': {
        label: 'Confirmed',
        color: 'green',
        icon: 'check',
        message: 'Order confirmed'
    },
    'SHIPPED': {
        label: 'Shipped',
        color: 'purple',
        icon: 'truck',
        message: 'Order is on the way'
    },
    'DELIVERED': {
        label: 'Delivered',
        color: 'green',
        icon: 'home',
        message: 'Order delivered'
    },
    'CANCELLED': {
        label: 'Cancelled',
        color: 'red',
        icon: 'x',
        message: 'Order cancelled'
    },
    'FAILED': {
        label: 'Failed',
        color: 'red',
        icon: 'alert',
        message: 'Order creation failed'
    },
    'RETURNED': {
        label: 'Returned',
        color: 'orange',
        icon: 'undo',
        message: 'Order returned'
    }
};
```

---

## 🔑 Key Points Summary

| Aspect | Details |
|--------|---------|
| **PaymentStatus values** | PENDING, SUCCESS, FAILED, PAID, REFUNDED, CANCELLED |
| **OrderStatus values** | PENDING, PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED, FAILED, RETURNED |
| **Database storage** | VARCHAR(20) as string |
| **API response** | Enum name as string (e.g., "PAID") |
| **Java type** | Type-safe enum |
| **Primary flow** | PLACED → CONFIRMED → SHIPPED → DELIVERED |
| **Payment flow** | PENDING → PAID (or FAILED/CANCELLED) |
| **Backward compatible** | Yes, enums stored as strings |
| **Compile-time safety** | Yes, prevents invalid values |

---

## 🚀 Deployment Checklist

- [ ] Enums created and in place
- [ ] Models updated with @Enumerated annotations
- [ ] Service implementations updated
- [ ] Imports added to all files
- [ ] Validation logic verified
- [ ] Tests written for state transitions
- [ ] Database schema verified (no changes needed)
- [ ] API documentation updated
- [ ] Frontend enum definitions created
- [ ] Frontend status display components updated
- [ ] Razorpay integration verified
- [ ] Error messages updated to use enum names
- [ ] Logging updated to use enums
- [ ] Code compiled without errors
- [ ] Existing data tested for compatibility
