

# CancelRequest

This type contains information about a buyer request to cancel an order.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cancelCompletedDate** | **String** | The date and time that the order cancellation was completed, if applicable. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. This field is not returned until the cancellation request has actually been approved by the seller. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**cancelInitiator** | **String** | This string value indicates the party who made the initial cancellation request. Typically, either the &#39;Buyer&#39; or &#39;Seller&#39;. If a cancellation request has been made, this field should be returned. |  [optional]
**cancelReason** | **String** | The reason why the cancelInitiator initiated the cancellation request. Cancellation reasons for a buyer might include &#39;order placed by mistake&#39; or &#39;order won&#39;t arrive in time&#39;. For a seller, a typical cancellation reason is &#39;out of stock&#39;. If a cancellation request has been made, this field should be returned. |  [optional]
**cancelRequestedDate** | **String** | The date and time that the order cancellation was requested. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. This field is returned for each cancellation request. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**cancelRequestId** | **String** | The unique identifier of the order cancellation request. This field is returned for each cancellation request. |  [optional]
**cancelRequestState** | **String** | The current stage or condition of the cancellation request. This field is returned for each cancellation request. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:CancelRequestStateEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]



