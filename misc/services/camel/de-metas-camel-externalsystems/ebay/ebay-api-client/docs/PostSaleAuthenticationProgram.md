# PostSaleAuthenticationProgram

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**outcomeReason** | **String** | This field indicates the result of the authenticity verification inspection on an order line item. This field is not returned when the status value of the order line item is PENDING or PASSED. The possible values returned here are NOT_AUTHENTIC, NOT_AS_DESCRIBED, CUSTOMIZED, MISCATEGORIZED, or NOT_AUTHENTIC_NO_RETURN. For implementation help, refer to &lt;a href&#x3D;&#x27;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:AuthenticityVerificationReasonEnum&#x27;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**status** | **String** | The value in this field indicates whether the order line item has passed or failed the authenticity verification inspection, or if the inspection and/or results are still pending. The possible values returned here are PENDING, PASSED, FAILED, or PASSED_WITH_EXCEPTION. For implementation help, refer to &lt;a href&#x3D;&#x27;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:AuthenticityVerificationStatusEnum&#x27;&gt;eBay API documentation&lt;/a&gt; |  [optional]
