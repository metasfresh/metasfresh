# ExtendedContact

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**companyName** | **String** | The company name associated with the buyer or eBay shipping partner. This field is only returned if defined/applicable to the buyer or eBay shipping partner. |  [optional]
**contactAddress** | [**Address**](Address.md) |  |  [optional]
**email** | **String** | This field shows the email address of the buyer. The email address of a buyer will be masked 14 days after order creation. This field will still be returned for the order, but it will not contain the buyer&#x27;s email address, but instead, something like &#x27;Invalid Request&#x27;. Note: This field always contains the email address of the buyer even with a Global Shipping Program shipment. |  [optional]
**fullName** | **String** | The full name of the buyer or eBay shipping partner. |  [optional]
**primaryPhone** | [**PhoneNumber**](PhoneNumber.md) |  |  [optional]
