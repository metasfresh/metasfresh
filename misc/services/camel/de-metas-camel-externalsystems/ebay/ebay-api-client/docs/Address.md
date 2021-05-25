

# Address

This type contains the details of a geographical address.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**addressLine1** | **String** | The first line of the street address. |  [optional]
**addressLine2** | **String** | The second line of the street address. This field can be used for additional address information, such as a suite or apartment number. This field will be returned if defined for the shipping address. |  [optional]
**city** | **String** | The city of the shipping destination. |  [optional]
**country** | **String** | The country of the shipping destination, represented as a two-letter ISO 3166-1 alpha-2 country code. For example, US represents the United States, and DE represents Germany. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:CountryCodeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**county** | **String** | The county of the shipping destination. Counties typically, but not always, contain multiple cities or towns. This field is returned if known/available. |  [optional]
**postalCode** | **String** | The postal code of the shipping destination. Usually referred to as Zip codes in the US. Most countries have postal codes, but not all. The postal code will be returned if applicable. |  [optional]
**stateOrProvince** | **String** | The state or province of the shipping destination. Most countries have states or provinces, but not all. The state or province will be returned if applicable. |  [optional]



