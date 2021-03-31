# Mappings
note: I've marked with `?????` the alberta fields we didn't map yet

## Patients and Instances (Alberta) => BPartners (metasfresh)

Patient

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

Patient.billingAddress mappings
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

Patient.deliveryAddress mappings
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

Care Giver
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
❗ ❓  I didn't set the isBillToDefault and isShippingToDefault flags here, should I do it know?

Hospital
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


## Products (metasfresh) => Articles (Alberta)
 
| Alberta | metasfresh | mandatory | note |
| --- | --- | --- | --- |
| customerNumber | M_Product.Value | YES | value is unique *per org*; if we push an article to alberta, it is per tenant, not globally; i think we can assume that two different metasfresh-AD_Orgs will never have overlapping M_Product.Value and also have the same alberta tenant (and if they do we can still add a  prefix on the fly)|
| name | M_Product.Name | YES | |
| description | M_Product.Description | NO | |
| additionalDescription | M_Product_AlbertaArticle.AdditionalDescription | NO| |
| manufacturer| M_Product.Manufacturer_ID | NO| column points to C_BPartner_ID. C_Partner needs to be flagged as manufacturer |
| manufacturerNumber | M_Product.~~Manufacturer~~ ManufacturerArticleNumber | NO| new column in M_Product|
| therapyIds | M_Product_AlbertaTherapy | NO| label-field |
| billableTherapies | M_Product_AlbertaBillableTherapy | NO| label-field |
| productGroupId | S_ExternalReference.ExternalReference | NO| we need externalreference-support for M_ProductCategory..and then export the M_ProductCategory's external reference; :exclamation: looks like this not yet done|
| size |M_Product_AlbertaArticle.Size | NO|
| inventoryType | M_Product_AlbertaArticle.InventoryType | NO| Ref-List as in the API definition |
| status | M_Product_AlbertaArticle.Status | NO| Ref-List as in the API definition |
| medicalAidPositionNumber | M_Product_AlbertaArticle.MedicalAidPositionNumber|  NO |
| stars |M_Product_AlbertaData.Stars| NO|
| assortmentType | M_Product_AlbertaArticle.AssortmentType | NO|
| pharmacyPrice |  MProductPrice.PriceStd aus AEP-Preisliste | NO| Price-List can be set in in alberta-external-system-config, with a preset default value |
| fixedPrice | MProductPrice.PriceList | NO| aus AEP-Preisliste (same as pharmacyPrice) :exclamation: in the alberta-webui we have also a sales price and `n` health-insurance-prices; pricing is not 100% clear yet |
| purchaseRating | M_Product_AlbertaArticle.PurchaseRating | NO| Ref-List as in the API definition | 
| |  | NO | :exclamation: in the API the also have attributes; i'll ask them to update the API-docs |

**Article.packagingUnits**

| Alberta | metasfresh | mandatory | note |
| --- | --- | --- | --- |
|  | M_Product_AlbertaPackagingUnit.M_HU_PI_Item_Product_ID | NO | optional; if set then we can look it up later when recediving C_OLCands
| unit | M_Product_AlbertaPackagingUnit.Unit | YES | Ref-List with values `Stk` and `Ktn`
| quantity | M_Product_AlbertaPackagingUnit.Quantity | YES | |
| pcn | M_Product_AlbertaPackagingUnit.PCN | YES | :exclamation: did not create the column yet - needs to be clarified |
| defaultPackagingUnit | M_Product_AlbertaPackagingUnit.IsDefaultPackagingUnit | YES | allow just one to be the default; :exclamation: this is not in the webui, so i didn't create it yet
| archived | M_Product_AlbertaPackagingUnit.IsArchived | NO | :exclamation: this is not in the webui, so i didn't create it yet



## Orders (Alberta) => Sales Order Candiates (metasfresh)


### Order

| Alberta | metasfresh | mandatory in mf | note |
| --- | --- | --- | --- |
| _id | C_OLCand.ExternalHeaderId | Y | |
| salesId | C_OLCand.POReference | | |
| patientId | C_OLCand.C_BPartner_ID | Y | ..via external reference lookup |
| rootId | C_OLCand_AlbertaOrder.RootId | |
| creationDate | C_OLCand_AlbertaOrder.CreationDate | |
| deliveryDate | C_OLCand.DatePromised | |
| startDate | C_OLCand_AlbertaOrder.StartDate | |
| endDate | C_OLCand_AlbertaOrder.EndDate | |
| dayOfDelivery | C_OLCand_AlbertaOrder.DayOfDelivery | |
| nextDelivery | C_OLCand_AlbertaOrder.NextDelivery | |
| deliveryAddress | C_OLCand.C_BPartnerLocation_ID |  Y | note that we already have `de.metas.camel.alberta.BPartnerUpsertRequestProducer#getShippingAddress` | 
| doctorId | C_OLCand_AlbertaOrder.C_Doctor_BPartner_ID | ..via external reference lookup |
| pharmacyId | C_OLCand_AlbertaOrder.C_Pharmacy_BPartner_ID | ..via external reference lookup |
| therapyId |  C_OLCand_AlbertaTherapy | |
| therapyTypeIds | C_OLCand_AlbertaTherapyType | eine andere enum als therapy, mit mehr Werten |
| isInitialCare | C_OLCand_AlbertaOrder.IsInitialCare | |
| orderedArticleLines | | |
| createdBy | | Alberta-User => checken..|
| status | | |
| isSeriesOrder | C_OLCand_AlbertaOrder.IsSeriesOrder | |
| annotation | C_OLCand_AlbertaOrder.Annotation | Notiz für den Innendienst |
| archived | C_OLCand_AlbertaOrder.IsArchived | |
| updated | C_OLCand_AlbertaOrder.Updated | |

### OrderedArticleLines

| Alberta | metasfresh | mandatory in mf | note |
| --- | --- | --- | --- |
| _id | C_OLCand.ExternalLineId | Y | |
| salesLineId | C_OLCand_AlbertaOrder.SalesLineId | | |
| articleId | C_OLCand.M_Product_ID | Y | ..via external reference lookup |
| articleCustomerNumber |  | (ignore) | this is basically the M_Product.Value that we had send to Alberta..but who knows, maybe it was meanwhile edited in metasfresh | 
| quantity | C_OLCand.QtyEntered | Y | |
| unit | C_OLCand_AlbertaOrder.Unit | | Ref-List with values Stk and Ktn |
| duration | | |
| isRentalEquipment | C_OLCand_AlbertaOrder.IsRentalEquipment | |
| isPrivateSale | C_OLCand_AlbertaOrder.IsPrivateSale | |
| updated | C_OLCand_AlbertaOrder.Updated | |

### Duration

| Alberta | metasfresh | mandatory in mf | note |
| --- | --- | --- | --- |
| description | C_OLCand_AlbertaOrder.DurationDescription | | |
| amount | C_OLCand_AlbertaOrder.DurationAmount | | |
| timePeriod | C_OLCand_AlbertaOrder.DurationAmount | | Ref-List with `0=Unbekannt`, `1=Minute`, `2=Stunde`, `3=Tag`, `4=Woche`, `5=Monat`, `6=Quartal`, `7=Halbjahr`, `8=Jahr` |

### Additional C_OLCand-Columns

| Alberta | metasfresh | mandatory in mf | note |
| --- | --- | --- | --- |
|     | C_OLCand.IsManualPrice | Y | const `N` |
|     | C_OLCand.IsManualDiscount | Y | const `N` |
|     | C_OLCand.IsImportedWitIssues | Y | const `Y`? we will have small REST-EP to clear them, after all were created |
|     | C_OLCand.DeliveryViaRule | Y | const 'D' |
|     | C_OLCand.DelvieryRule | Y | const 'A' |
|     | C_OLCand.DateCandidate | Y | :question: |
|     | C_OLCand.AD_InputDataSource_ID | Y | new hardcoded "Alberta" datasource |
|     | C_OLCand.AD_DataDestination_ID | Y | check the code..it *should* be automatically set to the one with internalname=DEST.de.metas.ordercandidate
