

# ReturnAddress

This type is used by the payment dispute methods, and is relevant if the buyer will be returning the item to the seller.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**addressLine1** | **String** | The first line of the street address. |  [optional]
**addressLine2** | **String** | The second line of the street address. This line is not always necessarily, but is often used for apartment number or suite number, or other relevant information that can not fit on the first line. |  [optional]
**city** | **String** | The city of the return address. |  [optional]
**country** | **String** | The country&#39;s two-digt, ISO 3166-1 country code. See the enumeration type for a country&#39;s value. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:CountryCodeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**county** | **String** | The county of the return address. Counties are not applicable to all countries. |  [optional]
**fullName** | **String** | The full name of return address owner. |  [optional]
**postalCode** | **String** | The postal code of the return address. |  [optional]
**primaryPhone** | [**Phone**](Phone.md) |  |  [optional]
**stateOrProvince** | **String** | The state or province of the return address. |  [optional]



