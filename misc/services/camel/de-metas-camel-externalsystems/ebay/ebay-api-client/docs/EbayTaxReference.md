

# EbayTaxReference

This type describes the VAT tax details. The eBay VAT tax type and the eBay VAT identifier number will be returned if a VAT tax is applicable for the order. Note: On January 31, 2022, the orders.fulfillmentStartInstructions.shippingStep.shipTo.contactAddress.addressLine2 will stop being used to return VAT information, so developers should make sure they integrate with the new fields before that time.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | This field value is returned to indicate the VAT tax type, which will vary by country/region. This string value will be one of the following: ABN: if this string is returned, the ID in the value field is an Australia tax ID IOSS: if this string is returned, the ID in the value field is an eBay EU or UK IOSS number IRD: if this string is returned, the ID in the value field is an eBay New Zealand tax ID OSS: if this string is returned, the ID in the value field is an eBay Germany VAT ID VOEC: if this string is returned, the ID in the value field is an eBay Norway tax ID |  [optional]
**value** | **String** | The value returned in this field is the VAT identifier number (VATIN), which will vary by country/region. This field will be returned if VAT tax is applicable for the order. The name field indicates the VAT tax type, which will vary by country/region: ABN: eBay AU tax ID IOSS: eBay EU IOSS number / eBay UK IOSS number IRD: eBay NZ tax ID OSS: eBay DE VAT ID VOEC: eBay NO number |  [optional]



