

# PaymentDisputeOutcomeDetail

This type is used by the resolution container that is returned for payment disputes that have been resolved.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**fees** | [**SimpleAmount**](SimpleAmount.md) |  |  [optional]
**protectedAmount** | [**SimpleAmount**](SimpleAmount.md) |  |  [optional]
**protectionStatus** | **String** | This enumeration value indicates if the seller is fully protected, partially protected, or not protected by eBay for the payment dispute. This field is always returned once the payment dispute is resolved. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/api:ProtectionStatusEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**reasonForClosure** | **String** | The enumeration value returned in this field indicates the outcome of the payment dispute for the seller. This field is always returned once the payment dispute is resolved. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/api:OutcomeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**recoupAmount** | [**SimpleAmount**](SimpleAmount.md) |  |  [optional]
**totalFeeCredit** | [**SimpleAmount**](SimpleAmount.md) |  |  [optional]



