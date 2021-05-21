Updated Mappings for the overhaul of out existing external-system components


# Tables, Columns and Fields

## Tables to extends

`AD_User`
* `Gender` 
  * :speaking_head: name `Gender` => `Geschlecht`
  * 


# Mappings
note: I've marked with `?????` the alberta fields we didn't map yet


Checking current version of API-Spec 

## Patients and Instances (Alberta) => BPartners (metasfresh)

### Meanings withing the "Metasfresh" columns

* `bpartner` means `requestItems/bpartnerComposite/bpartner` (i.e. `de.metas.common.bpartner.v2.request.JsonRequestBPartner`)
* `contact` means `requestItems/bpartnerComposite/contacts` (i.e. `de.metas.common.bpartner.v2.request.JsonRequestContactUpsert`)
* `location` means `requestItems/bpartnerComposite/locations` (i.e. `de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert`)
* `contact/albertaInfo` means the new alberta-info object that is backed by table `AD_User_Alberta`
* `<type>`-ESR means an `S_ExternalReference` record with `Type=<type>`

### Patient

Metasfresh | Alberta | Note
--- | --- | ---
`BPartner`-ESR | `_id` |
`UserID`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
????? | `customerId` |
????? | `customerContactId` |
contact/gender | `gender` | :exclamation: need new column `AD_User.Gender` and extend the API accordingly
????? | `title` |
contact/firstName | `firstName` |
contact/lastName | `lastName` |
bpartner/name | `firstName` + " " + `lastName` |
contact/birthday | `birthday` | :exclamation: column `AD_User.BirthDay` exists, but API needs to be extended
location/address1 | `address` |
location/address2 | `additionalAddress` |
location/address3 | `additionalAddress2` |
location/postal | `postalCode` |
location/City | `city` |
location/CountryCode | (always `DE`) |
location/billToDefault | true, if `billingAddress == null` |
location/billTo | true, if `billingAddress == null` |
location/shipToDefault | true, if `deliveryAddress == null` |
location/shipTo | true, if `deliveryAddress == null` |
contact/Phone| `phone` |
contact/Fax | `fax` |
contact/MobilePhone| `mobilePhone` |
contact/email | `patient/email` |
contact/DefaultContact | always true |
C_BPartner.isCustomer | always true |
(lookup, sync and connect via `C_BP_Relation`) | `primaryDoctorId` | also see `doctor` mappings
(lookup, sync and connect via `C_BP_Relation`) | `nursingHomeId` | also see `nursingHome` mappings
(lookup, sync and connect via `C_BP_Relation`) | `nursingServiceId` | also see `nursingServiceId` mappings
(lookup, sync and connect via `C_BP_Relation`) | `hostpital/hostpitalId` | also see `hostpital` mappings
????? | `hostpital/dischargeDate` |
(lookup, sync and connect via `C_BP_Relation`) | `payer/payerId` | also see `payer` mappings
????? | `payer/payerType` |
????? | `payer/numberOfInsured` | new
????? | `payer/copaymentFromDate` | new
????? | `payer/copaymentToDate` | new
????? | `changeInSupplier` | new
????? | `ivTherapy` | new
(lookup, sync and connect via `C_BP_Relation`) | `pharmacyId` | also see `pharmacy` mappings
????? | `comment` |
 | `billingAddress` | see `patient/billingAddress` mappings
 | `deliveryAddress` | see `patient/deliveryAddress` mappings
????? | `regionId` |   also see `region` mappings
????? | `fieldNurseId` | also see `user` mappings ?
????? | `deactivationReason` | 
????? | `deactivationReason` | 
????? | `deactivationComment` |
 | `careGivers` | see `careGivers` mappings
????? | `archived` |
????? | `createdAt` |
????? | `createdBy` | see `user` mappings
????? | `updatedAt` |
????? | `updatedBy` | see `user` mappings
????? | `timestamp` |

### `doctor` mappings

Metasfresh | Alberta |
-- | -- |
`BPartner`-ESR | `_id` |
`UserID`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
????? | `gender` |
???? | `titleShort` |
???? | `title` |
contact/firstName | `firstName` |
contact/lastName | `lastName` |
C_BPartner.Name | `firstName` + " " + `lastName` |
location/address1 | `address` |
location/postal | `postalCode` |
location/city | `city` |
C_BPartner.phone | `phone`|
contact/Phone | `phone` |
contact/Fax | `fax` |
????? | `timestamp` |
C_BPartner.isCustomer | always true |
location/countryCode | always DE |
location/billToDefault | true |
location/billTo | true |
location/shipToDefault | true |
location/shipTo | true |

### `nursingHome` mappings

Metasfresh | Alberta |
-- | -- |
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
C_BPartner.Name | `name` |
location/address1 | `address` |
location/postal | `postalCode` |
location/city | `city` |
C_BPartner.phone | `phone` |
????? | `fax` |
????? | `email` |
????? | `timestamp` |
C_BPartner.isCustomer | always true |
location/countryCode | always DE |
location/billToDefault | true |
location/billTo | true |
location/shipToDefault | true |
location/shipTo | true |


### `nursingService` mappings

Metasfresh | Alberta |
-- | -- |
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
C_BPartner.Name | `name` |
location/address1 | `address` |
location/postal | `postalCode` |
location/city | `city` |
C_BPartner.phone | `phone`|
????? | `fax` |
????? | `email` |
????? | `website` |
????? | `timestamp` |
C_BPartner.isCustomer | always true |
location/countryCode | always DE |
location/billToDefault | true |
location/billTo | true |
location/shipToDefault | true |
location/shipTo | true |


### `hospital` mappings

Metasfresh | Alberta |
-- | -- |
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
C_BPartner.Name2  | `company` |
C_BPartner.Name | `name` |
C_BPartner.companyName | `companyName` |
C_BPartner.Name3 | `additionalCompanyName` |
location/address1 | `address` |
location/postal | `postalCode` |
location/city | `city` |
C_BPartner.phone | `phone` |
????? | `fax` |
????? | `email` |
????? | `website` |
????? | `timestamp` |
C_BPartner.isCustomer | always true |
location/countryCode | always DE |
location/billToDefault | true |
location/billTo | true |
location/shipToDefault | true |
location/shipTo | true |

### `payer` mappings

Metasfresh | Alberta |
-- | -- |
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
C_BPartner.Name | `name` |
????? | `type` |
C_BPartner.code| `iKNumber` |
????? | `timestamp` |
C_BPartner.isCustomer | always true |
location/countryCode | always DE |
location/billToDefault | true |
location/billTo | true |
location/shipToDefault | true |
location/shipTo | true |

### `pharmacy` mappings

Metasfresh | Alberta |
-- | -- |
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
C_BPartner.Name | `name` |
location/address1 | `address` |
location/postal | `postalCode` |
location/city | `city` |
C_BPartner.phone | `phone` |
????? | `fax` |
????? | `email` |
????? | `website` |
????? | `timestamp` |
C_BPartner.isCustomer | always true |
location/countryCode | always DE |
location/billToDefault | true |
location/billTo | true |
location/shipToDefault | true |
location/shipTo | true |

### `patient/billingAddress` mappings

Metasfresh | Alberta |
-- | -- |
`BPartnerLocation`-ESR | 'billingAddress_' + Patient.Id |
????? | `gender` | 
????? | `title` | 
location/bPartnerName | `name` | 
location/address1 | `address` |
location/postal | `postalCode` |
location/City | `city` |
location/CountryCode | always DE |
location/billToDefault | true |
location/billTo | true |

### `patient/deliveryAddress` mappings

Metasfresh | Alberta |
-- | -- |
`BPartnerLocation`-ESR | 'shippingAddress_' + Patient.Id |
location/address1 | Patient.deliveryAddress.address |
location/postal | Patient.deliveryAddress.postalCode |
location/City | Patient.deliveryAddress.city |
location/CountryCode | always DE |
location/bPartnerName | Patient.deliveryAddress.name |
location/shipToDefault | true |
location/shipTo | true |
???? | Patient.deliveryAddress.title |

### `region` mappings

Metasfresh | Alberta |
-- | -- |
????? | `_id` |
????? | `label` |
????? | `parent` |
????? | `timestamp` |

### `careGivers` mappings

Metasfresh | Alberta |
-- | -- |
`BPartner`-ESR | `_id` |
`UserID`-ESR | `_id` |
`BPartnerLocation`-ESR | _id` |
????? | `customerId` |
????? | `type` |
????? | `isLegalCarer` |
????? | gender |
????? | title |
contact/firstName | firstName |
contact/lastName | lastName |
C_BPartner.Name | firstName + " " + lastName |
location/address1 | address |
location/postal | postalCode |
location/city | city |
location/countryCode | always DE |
C_BPartner.phone | phone |
contact/phone | phone |
contact/mobilePhone | mobilePhone |
contact/fax| fax |
contact/email | email |
????? | archived |
????? | timestamp |
C_BPartner.isCustomer | always true |
contact/defaultContact | always true |

❗ ❓  I didn't set the isBillToDefault and isShippingToDefault flags here, should I do it know?

### `user` mappings

Metasfresh | Alberta |
-- | -- |
`UserID`-ESR | `_id` |
contact/firstName | `firstName` |
contact/lastName | `lastName` |
contact/email | `email` |
contact/albertaInfo/lastUpdated | `timestamp` |

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
| `_id` | `C_OLCand.ExternalHeaderId`, also used for `C_OLCand.POReference` if salesId is empty | Y | |
| `salesId` | `C_OLCand.POReference` | Y | |
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
