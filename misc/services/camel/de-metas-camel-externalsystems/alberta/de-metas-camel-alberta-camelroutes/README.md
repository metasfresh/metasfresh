# Alberta -> Metasfresh Mappings
note: I've marked with `?` the alberta fields we didn't map yet

`Patient`

Metasfresh|  Alberta
--- | --- | 
C_BPartner.externalId | Id |
C_BPartner.Name | firstName + " " + lastName |
C_BPartner.isCustomer | always true |
????? | comment |
--- | --- | 
Contact.externalId | Patient.Id|
Contact.firstName | Patient.firstName |
Contact.lastName | Patient.lastName |
Contact.email | Patient.email |
Contact.Fax | Patient.fax |
Contact.Phone| Patient.phone |
Contact.MobilePhone| Patient.mobilePhone |
Contact.DefaultContact | always true |
????? | birthday |
????? | gender |
????? | title |
--- | --- |
BPLocation.externalId | Patient.Id |
BPLocation.address1 | Patient.address |
BPLocation.address2 | Patient.additionalAddress |
BPLocation.address3 | Patient.additionalAddress2 |
BPLocation.postal | Patient.postalCode |
BPLocation.City | Patient.city |
BPLocation.CountryCode | always DE |
BPLocation.billToDefault | true, if Patient.billingAddress == null |
BPLocation.billTo | true, if Patient.billingAddress == null |
BPLocation.shipToDefault | true, if Patient.deliveryAddress == null |
BPLocation.shipTo | true, if Patient.deliveryAddress == null |

`Patient.billingAddress` mappings

Metasfresh | Alberta |
-- | -- |
BPLocation.externalId | 'billingAddress_' + Patient.Id |
BPLocation.address1 | Patient.billingAddress.address |
BPLocation.postal | Patient.billingAddress.postalCode |
BPLocation.City | Patient.billingAddress.city |
BPLocation.CountryCode | always DE |
BPLocation.bPartnerName | Patient.billingAddress.name |
BPLocation.billToDefault | true |
BPLocation.billTo | true |
???? | Patient.billingAddress.title |

`Patient.deliveryAddress` mappings

Metasfresh | Alberta |
-- | -- |
BPLocation.externalId | 'shippingAddress_' + Patient.Id |
BPLocation.address1 | Patient.deliveryAddress.address |
BPLocation.postal | Patient.deliveryAddress.postalCode |
BPLocation.City | Patient.deliveryAddress.city |
BPLocation.CountryCode | always DE |
BPLocation.bPartnerName | Patient.deliveryAddress.name |
BPLocation.shipToDefault | true |
BPLocation.shipTo | true |
???? | Patient.deliveryAddress.title |

`Care Giver`

Metasfresh | Alberta |
-- | -- |
C_BPartner.externalId | Id |
C_BPartner.Name | firstName + " " + lastName |
C_BPartner.phone | phone |
C_BPartner.isCustomer | always true |
-- | -- |
Contact.externalId | Id  (careGiver.id) |
Contact.firstName | firstName |
Contact.lastName | lastName |
Contact.email | email |
Contact.fax| fax |
Contact.phone | phone |
Contact.mobilePhone | mobilePhone |
Contact.defaultContact | always true |
 ??? | title |
 ??? | gender |
 ??? | type |
 ??? | isLegalCarer |
-- | -- |
BPLocation.externalId | Id (careGiver.id) |
BPLocation.address1 | address |
BPLocation.city | city |
BPLocation.postal | postalCode |
BPLocation.countryCode | always DE |


`Hospital`

Metasfresh | Alberta |
-- | -- |
C_BPartner.externalId | Id |
C_BPartner.companyName | companyName |
C_BPartner.Name | name |
C_BPartner.Name2  | company |
C_BPartner.Name3 | addittionalCompanyName |
C_BPartner.phone | phone |
C_BPartner.isCustomer | always true |
????? | email |
????? | fax |
????? | website |
-- | -- |
BPLocation.externalId | Id (hospital.id) |
BPLocation.address1 | address |
BPLocation.city | city |
BPLocation.postal | postalCode |
BPLocation.countryCode | always DE |
BPLocation.billToDefault | true |
BPLocation.billTo | true |
BPLocation.shipToDefault | true |
BPLocation.shipTo | true |
-- | -- |

`NursingService`

Metasfresh | Alberta |
-- | -- |
C_BPartner.externalId | Id |
C_BPartner.Name | name |
C_BPartner.phone | phone|
C_BPartner.isCustomer | always true |
????? | email |
????? | fax |
????? | website |
-- | -- |
BPLocation.externalId | Id (NursingService.id) |
BPLocation.address1 | address |
BPLocation.city | city |
BPLocation.postal | postalCode |
BPLocation.countryCode | always DE |
BPLocation.billToDefault | true |
BPLocation.billTo | true |
BPLocation.shipToDefault | true |
BPLocation.shipTo | true |


`NursingHome`

Metasfresh | Alberta |
-- | -- |
C_BPartner.externalId | Id |
C_BPartner.Name | name |
C_BPartner.phone | phone|
C_BPartner.isCustomer | always true |
????? | email |
????? | fax |
-- | -- |
BPLocation.externalId | Id (NursingHome.id) |
BPLocation.address1 | address |
BPLocation.city | city |
BPLocation.postal | postalCode |
BPLocation.countryCode | always DE |
BPLocation.billToDefault | true |
BPLocation.billTo | true |
BPLocation.shipToDefault | true |
BPLocation.shipTo | true |

`Doctor`

Metasfresh | Alberta |
-- | -- |
C_BPartner.externalId | Id |
C_BPartner.Name | firstName + " " + lastName |
C_BPartner.phone | phone|
C_BPartner.isCustomer | always true |
???? | title |
???? | titleShort |
-- | -- |
Contact.externalId | Id (Doctor.Id) |
Contact.firstName | firstName |
Contact.lastName | lastName |
Contact.Fax | fax |
Contact.Phone | phone |
--| -- |
BPLocation.externalId | Id (Doctor.id) |
BPLocation.address1 | address |
BPLocation.city | city |
BPLocation.postal | postalCode |
BPLocation.countryCode | always DE |
BPLocation.billToDefault | true |
BPLocation.billTo | true |
BPLocation.shipToDefault | true |
BPLocation.shipTo | true |

`Payer`

Metasfresh | Alberta |
-- | -- |
C_BPartner.externalId | Id |
C_BPartner.Name | name |
C_BPartner.code| iKNumber |
C_BPartner.isCustomer | always true |
--| -- |
BPLocation.externalId | Id (Payer.id) |
BPLocation.countryCode | always DE |
BPLocation.billToDefault | true |
BPLocation.billTo | true |
BPLocation.shipToDefault | true |
BPLocation.shipTo | true |

`Pharmacy`

Metasfresh | Alberta |
-- | -- |
C_BPartner.ExternalId | Id |
C_BPartner.Name | Name |
C_BPartner.Phone | Phone |
C_BPartner.isCustomer | true |
??? | website |
??? | email |
??? | fax |
--| -- |
BPLocation.ExternalId | Id (Pharmacy.Id) |
BPLocation.City | City |
BPLocation.Postal | PostalCode |
BPLocation.Address1 | Address |
BPLocation.BillTo | true |
BPLocation.BillToDefault | true |
BPLocation.ShipTo | true |
BPLocation.ShipToDefault | true |





 