Mappings:

BPartner
## Current data mapping

Metasfresh |  Shopware
--- | --- | 
JsonRequestBPartner.ExternalId | Order.OrderCustomer.CustomerId |
JsonRequestBPartner.Name | Order.OrderCustomer.FirstName + " " + Order.OrderCustomer.LastName |
JsonRequestBPartner.CompanyName | Order.OrderCustomer.Company |
JsonRequestBPartner.Code | Order.OrderCustomer.CustomerNumber |
JsonRequestBPartner.isCustomer | always true |
 ??? | Order.OrderCustomer.Email |
 ??? | Order.OrderCustomer.title |
 ??? | Order.OrderCustomer.salutation |
 
 Contact
 Metasfresh |  Shopware
--- | --- | 
JsonRequestContact.ExternalId | Order.OrderCustomer.CustomerId |
JsonRequestContact.FirstName | Order.OrderCustomer.FirstName |
JsonRequestContact.LastName | Order.OrderCustomer.LastName |
JsonRequestContact.Email | Order.OrderCustomer.Email |

BPartnerLocation
 Metasfresh |  Shopware
--- | --- | 
JsonRequestLocation.CountryCode | OrderAddress.countryId -> CountryInfo -> ISO |
JsonRequestLocation.Address1 | OrderAddress.Street |
JsonRequestLocation.Address2 |  OrderAddress.AdditionalAddressLine1 |
JsonRequestLocation.Address3 |  OrderAddress.AdditionalAddressLine2 |
JsonRequestLocation.City | OrderAddress.City |
JsonRequestLocation.Postal | OrderAddress.ZipCode |
JsonRequestLocation.shipTo | true, if the OrderAddress is coming from Deliveries |
JsonRequestLocation.billTo | true, if the OrderAddress is coming from `Order.BillingAddressId` |
 ?? | OrderAddress.PhoneNumber |
 


