

# SimpleAmount

This type defines the monetary value of the payment dispute, and the currency used.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**currency** | **String** | A three-letter ISO 4217 code (such as USD for US site) that indicates the currency of the amount in the value field. Both the value and currency fields are always returned with the amount container. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:CurrencyCodeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**value** | **String** | The monetary amount of the payment dispute. Both the value and currency fields are always returned with the amount container. |  [optional]



