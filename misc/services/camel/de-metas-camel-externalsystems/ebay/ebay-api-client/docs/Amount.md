# Amount

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**convertedFromCurrency** | **String** | A three-letter ISO 4217 code that indicates the currency of the amount in the convertedFromValue field. This value is required or returned only if currency conversion/localization is required, and represents the pre-conversion currency. For implementation help, refer to &lt;a href&#x3D;&#x27;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:CurrencyCodeEnum&#x27;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**convertedFromValue** | **String** | The monetary amount before any conversion is performed, in the currency specified by the convertedFromCurrency field. This value is required or returned only if currency conversion/localization is required. The value field contains the converted amount of this value, in the currency specified by the currency field. |  [optional]
**currency** | **String** | A three-letter ISO 4217 code that indicates the currency of the amount in the value field. If currency conversion/localization is required, this is the post-conversion currency of the amount in the value field. Default: The default currency of the eBay marketplace that hosts the listing. For implementation help, refer to &lt;a href&#x3D;&#x27;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:CurrencyCodeEnum&#x27;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**value** | **String** | The monetary amount, in the currency specified by the currency field. If currency conversion/localization is required, this value is the converted amount, and the convertedFromValue field contains the amount in the original currency. Required in the amount type. |  [optional]
