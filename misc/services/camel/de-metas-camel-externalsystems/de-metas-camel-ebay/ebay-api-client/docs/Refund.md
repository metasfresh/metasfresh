

# Refund

This is the base type of the issueRefund response payload. As long as the issueRefund method does not trigger an error, a response payload will be returned.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**refundId** | **String** | The unique identifier of the order refund. This value is returned unless the refund operation fails (refundStatus value shows FAILED). This identifier can be used to track the status of the refund through a getOrder or getOrders call. For order-level refunds, check the paymentSummary.refunds.refundId field in the getOrder/getOrders response, and for line item level refunds, check the lineItems.refunds.refundId field(s) in the getOrder/getOrders response. |  [optional]
**refundStatus** | **String** | The value returned in this field indicates the success or failure of the refund operation. A successful issueRefund operation should result in a value of PENDING. A failed issueRefund operation should result in a value of FAILED, and an HTTP status code and/or and API error code may also get returned to possibly indicate the issue. The refunds issued through this method are processed asynchronously, so the refund will not show as &#39;Refunded&#39; right away. A seller will have to make a subsequent getOrder call to check the status of the refund. The status of an order refund can be found in the paymentSummary.refunds.refundStatus field of the getOrder response. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:RefundStatusEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]



