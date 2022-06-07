

# MonetaryTransaction

This type is used to provide details about one or more monetary transactions that occur as part of a payment dispute.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**date** | **String** | This timestamp indicates when the monetary transaction occurred. A date is returned for all monetary transactions. The following format is used: YYYY-MM-DDTHH:MM:SS.SSSZ. For example, 2015-08-04T19:09:02.768Z. |  [optional]
**type** | **String** | This enumeration value indicates whether the monetary transaction is a charge or a credit to the seller. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/api:MonetaryTransactionTypeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**reason** | **String** | This enumeration value indicates the reason for the monetary transaction. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/api:MonetaryTransactionReasonEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**amount** | [**DisputeAmount**](DisputeAmount.md) |  |  [optional]



