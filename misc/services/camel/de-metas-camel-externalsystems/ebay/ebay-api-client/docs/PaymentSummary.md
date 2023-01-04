

# PaymentSummary

This type contains information about the various monetary exchanges that apply to the net balance due for the order.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**payments** | [**List&lt;Payment&gt;**](Payment.md) | This array consists of payment information for the order, including payment status, payment method, payment amount, and payment date. This array is always returned, although some of the fields under this container will not be returned until payment has been made. |  [optional]
**refunds** | [**List&lt;OrderRefund&gt;**](OrderRefund.md) | This array is always returned, but is returned as an empty array unless the seller has submitted a partial or full refund to the buyer for the order. If a refund has occurred, the refund amount and refund date will be shown for each refund. |  [optional]
**totalDueSeller** | [**Amount**](Amount.md) |  |  [optional]



