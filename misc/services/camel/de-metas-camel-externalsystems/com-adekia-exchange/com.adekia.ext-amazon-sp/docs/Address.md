# Address

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | The name. | 
**addressLine1** | **String** | The street address. |  [optional]
**addressLine2** | **String** | Additional street address information, if required. |  [optional]
**addressLine3** | **String** | Additional street address information, if required. |  [optional]
**city** | **String** | The city  |  [optional]
**county** | **String** | The county. |  [optional]
**district** | **String** | The district. |  [optional]
**stateOrRegion** | **String** | The state or region. |  [optional]
**municipality** | **String** | The municipality. |  [optional]
**postalCode** | **String** | The postal code. |  [optional]
**countryCode** | **String** | The country code. A two-character country code, in ISO 3166-1 alpha-2 format. |  [optional]
**phone** | **String** | The phone number. Not returned for Fulfillment by Amazon (FBA) orders. |  [optional]
**addressType** | [**AddressTypeEnum**](#AddressTypeEnum) | The address type of the shipping address. |  [optional]

<a name="AddressTypeEnum"></a>
## Enum: AddressTypeEnum
Name | Value
---- | -----
RESIDENTIAL | &quot;Residential&quot;
COMMERCIAL | &quot;Commercial&quot;
