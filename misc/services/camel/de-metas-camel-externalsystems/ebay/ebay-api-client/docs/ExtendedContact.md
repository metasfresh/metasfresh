

# ExtendedContact

This type contains shipping and contact information for a buyer or an eBay shipping partner.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**companyName** | **String** | The company name associated with the buyer or eBay shipping partner. This field is only returned if defined/applicable to the buyer or eBay shipping partner. |  [optional]
**contactAddress** | [**Address**](Address.md) |  |  [optional]
**email** | **String** | This field contains the email address of the buyer. This address will be returned for up to 14 days from order creation. If an order is more than 14 days old, no address is returned. Note: If returned, this field contains the email address of the buyer, even for Global Shipping Program shipments. |  [optional]
**fullName** | **String** | The full name of the buyer or eBay shipping partner. |  [optional]
**primaryPhone** | [**PhoneNumber**](PhoneNumber.md) |  |  [optional]



