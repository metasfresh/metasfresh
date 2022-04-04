

# DisputeAmount

This type defines the monetary value of an amount. It can provide the amount in both the currency used on the eBay site where an item is being offered and the conversion of that value into another currency, if applicable.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**convertedFromCurrency** | **String** | The three-letter ISO 4217 code representing the currency of the amount in the convertedFromValue field. This value is the pre-conversion currency. This field is only returned if/when currency conversion was applied by eBay. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:CurrencyCodeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**convertedFromValue** | **String** | The monetary amount before any conversion is performed, in the currency specified by the convertedFromCurrency field. This value is the pre-conversion amount. The value field contains the converted amount of this value, in the currency specified by the currency field. This field is only returned if/when currency conversion was applied by eBay. |  [optional]
**currency** | **String** | A three-letter ISO 4217 code that indicates the currency of the amount in the value field. This field is always returned with any container using Amount type. Default: The currency of the authenticated user&#39;s country. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:CurrencyCodeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**exchangeRate** | **String** | The exchange rate used for the monetary conversion. This field shows the exchange rate used to convert the dollar value in the value field from the dollar value in the convertedFromValue field. This field is only returned if/when currency conversion was applied by eBay. |  [optional]
**value** | **String** | The monetary amount, in the currency specified by the currency field. This field is always returned with any container using Amount type. |  [optional]



