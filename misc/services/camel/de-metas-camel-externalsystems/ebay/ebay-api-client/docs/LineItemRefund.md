

# LineItemRefund

This type contains refund information for a line item.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**amount** | [**Amount**](Amount.md) |  |  [optional]
**refundDate** | **String** | The date and time that the refund was issued for the line item. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. This field is not returned until the refund has been issued. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**refundId** | **String** | Unique identifier of a refund that was initiated for an order&#39;s line item through the issueRefund method. If the issueRefund method was used to issue a refund at the order level, this identifier is returned at the order level instead (paymentSummary.refunds.refundId field). A refundId value is returned in the response of the issueRefund method, and this same value will be returned in the getOrder and getOrders responses for pending and completed refunds. The issueRefund method can only be used for eBay managed payment orders. |  [optional]
**refundReferenceId** | **String** | This field is reserved for internal or future use. |  [optional]



