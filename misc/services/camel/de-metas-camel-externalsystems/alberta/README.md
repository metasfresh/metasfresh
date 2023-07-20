# Mappings
note: I've marked with `?????` the alberta fields we didn't map yet



## Patients and Instances (Alberta) => BPartners (metasfresh)

### Meanings within the "Metasfresh" columns

* `bpartner` means `requestItems/bpartnerComposite/bpartner` (i.e. `de.metas.common.bpartner.v2.request.JsonRequestBPartner`)
* `contact` means `requestItems/bpartnerComposite/contacts` (i.e. `de.metas.common.bpartner.v2.request.JsonRequestContactUpsert`)
* `location` means `requestItems/bpartnerComposite/locations` (i.e. `de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert`)
* `contact/albertaInfo` means the new alberta-info object that is backed by table `AD_User_Alberta`
* `<type>`-ESR means an `S_ExternalReference` record with `Type=<type>`

:zap: **NEW**: add a new ExternalReferenceType `Contact`. Although both contacts and users are stored in `AD_User` there is a big different between them

### Patient

| Metasfresh                                      | Alberta                            | Note                                                                                                         |
|-------------------------------------------------|------------------------------------|--------------------------------------------------------------------------------------------------------------|
| `BPartner`-ESR                                  | `_id`                              |                                                                                                              |
| `Contact`-ESR                                   | `_id`                              |                                                                                                              |
| `BPartnerLocation`-ESR                          | `_id`                              |                                                                                                              |
| ?????                                           | `customerId`                       | TODO :question: to IT-Labs                                                                                   |
| ?????                                           | `customerContactId`                | TODO :question: to IT-Labs                                                                                   |
| `contact/albertaContact/gender`                 | `gender`                           | :zap: **NEW**: needs new table `AD_User_Alberta` and according API-extension                                 |
| `bpartner/albertaContact/title`                 | `title`                            | :zap: **NEW**: needs new table `AD_User_Alberta` and according API-extension                                 |
| `contact/firstName`                             | `firstName`                        |                                                                                                              |
| `contact/lastName`                              | `lastName`                         |                                                                                                              |
| `bpartner/name`                                 | `firstName` + " " + `lastName`     |                                                                                                              |
| `contact/birthday`                              | `birthday`                         | :zap: **NEW**: column `AD_User.BirthDay` exists, but API needs to be extended                                |
| `location/address1`                             | `address`                          |                                                                                                              |
| `location/address2`                             | `additionalAddress`                |                                                                                                              |
| `location/address3`                             | `additionalAddress2`               |                                                                                                              |
| `location/postal`                               | `postalCode`                       |                                                                                                              |
| `location/city`                                 | `city`                             |                                                                                                              |
| `contact/phone`                                 | `phone`                            |                                                                                                              |
| `contact/fax`                                   | `fax`                              |                                                                                                              |
| `contact/mobilePhone`                           | `mobilePhone`                      |                                                                                                              |
| `contact/email`                                 | `patient/email`                    |                                                                                                              |
| (lookup, sync and connect via `C_BP_Relation`)  | `primaryDoctorId`                  | also see `doctor` mappings                                                                                   |
| (lookup, sync and connect via `C_BP_Relation`)  | `nursingHomeId`                    | also see `nursingHome` mappings                                                                              |
| (lookup, sync and connect via `C_BP_Relation`)  | `nursingServiceId`                 | also see `nursingServiceId` mappings                                                                         |
| (lookup, sync and connect via `C_BP_Relation`)  | `hostpital/hostpitalId`            | also see `hostpital` mappings                                                                                |
| `bpartner/albertaPatient/hospitalId`            | `hostpital/hospitalId`             | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/hospitalDischargeDate` | `hostpital/dischargeDate`          | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| (lookup, sync and connect via `C_BP_Relation`)  | `payer/payerId`                    | also see `payer` mappings                                                                                    |
| `bpartner/albertaPatient/payerId`               | `hostpital/payerId`                | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/payerType`             | `payer/payerType`                  | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/insuranceNumber`       | `payer/numberOfInsured`            | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/copaymentFrom`         | `payer/copaymentFromDate`          | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/copaymentTo`           | `payer/copaymentToDate`            | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/transferPatient`       | `changeInSupplier`                 | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/ivTherapy`             | `ivTherapy`                        | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| (lookup, sync and connect via `C_BP_Relation`)  | `pharmacyId`                       | also see `pharmacy` mappings                                                                                 |
| `bpartner/memo`                                 | `comment`                          | :zap: **NEW**: column `bpartner/Memo` exists, but API needs to be extended                                   |
| (additional address)                            | `billingAddress`                   | see `patient/billingAddress` mappings                                                                        |
| (additional address)                            | `deliveryAddress`                  | see `patient/deliveryAddress` mappings                                                                       |
| ?????                                           | `regionId`                         | column `C_BPartner_Location.C_Region_ID` exists, but don't map it now                                        |
| `bpartner/albertaPatient/fieldNurseIdentifier`  | `fieldNurseId`                     | also see `user` mappings ?                                                                                   |
| `bpartner/albertaPatient/deactivationReason`    | `deactivationReason`               | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/deactivationDate`      | `deactivationDate`                 | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/deactivationComment`   | `deactivationComment`              | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| (lookup, sync and connect via `C_BP_Relation`)  | `careGivers`                       | see `careGivers` mappings                                                                                    |
| `bpartner/albertaCaregivers/identifier`         | `careGivers/_id`                   | :zap: **NEW**: needs new table `C_BPartner_AlbertaCareGiver` and according API-extension                     |
| `bpartner/albertaCaregivers/type`               | `careGivers/type`                  | :zap: **NEW**: needs new table `C_BPartner_AlbertaCareGiver` and according API-extension                     |
| `bpartner/albertaCaregivers/legalCarer`         | `careGivers/isLegalCarer`          | :zap: **NEW**: needs new table `C_BPartner_AlbertaCareGiver` and according API-extension                     |
| `bpartner/albertaBPartner/archived`             | `archived`                         | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension                              |
| `bpartner/albertaPatient/createdAt`             | `createdAt`                        | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/createdByIndentifier`  | `createdBy`                        | see `user` mappings;  :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension |
| `bpartner/albertaPatient/updatedAt`             | `updatedAt`                        | :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension                       |
| `bpartner/albertaPatient/updatedByIdentifier`   | `updatedBy`                        | see `user` mappings; :zap: **NEW**: needs new table `C_BPartner_AlbertaPatient` and according API-extension  |
| `bpartner/albertaBPartner/timestamp`            | `timestamp`                        | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension                              |
| `bpartner/albertaBPartner/role`                 | always `Patient`                   | :zap: **NEW**: needs new table `C_BPartner_AlbertaRole` and according API-extension                          |
| `location/countryCode`                          | (always `DE`)                      |                                                                                                              |
| `location/billToDefault`                        | true, if `billingAddress == null`  |                                                                                                              |
| `location/billTo`                               | true, if `billingAddress == null`  |                                                                                                              |
| `location/shipToDefault`                        | true, if `deliveryAddress == null` |                                                                                                              |
| `location/shipTo`                               | true, if `deliveryAddress == null` |                                                                                                              |
| `contact/DefaultContact`                        | always true                        |                                                                                                              |
| `bpartner/isCustomer`                           | always true                        |                                                                                                              |

### `doctor` mappings

Metasfresh | Alberta | Note
-- | -- | --
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
`Contact`-ESR | `_id` |
`contact/locationIdentifier` | `_id` | :zap: **NEW**: needs an API extension so that we can set `AD_User.C_BPartner_Location_ID`
`contact/albertaContact/gender` | `gender` | :zap: **NEW**: needs new table `AD_User_Alberta` and according API-extension
`bpartner/albertaBPartner/title` | `title` | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`bpartner/albertaBPartner/titleShort` | `titleShort` | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`contact/firstName` | `firstName` |
`contact/lastName` | `lastName` |
`bpartner/name` | `firstName` + " " + `lastName` |
`location/address1` | `address` |
`location/postal` | `postalCode` |
`location/city` | `city` |
`bpartner/phone` | `phone`|
`contact/phone` | `phone` |
`contact/fax` | `fax` |
`bpartner/albertaBPartner/timestamp` | `timestamp` | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`bpartner/albertaBPartner/role` | always `Doctor` | :zap: **NEW**: needs new table `C_BPartner_AlbertaRole` and according API-extension
`bpartner/isCustomer` | always true |
`location/countryCode` | always DE |
`location/billToDefault` | true |
`location/billTo` | true |
`location/shipToDefault` | true |
`location/shipTo` | true |

### `nursingHome` mappings

Metasfresh | Alberta | Note
-- | -- | --
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
`Contact`-ESR | `_id` | :zap: **NEW**: need to create a default-contact
`contact/locationIdentifier` | `_id` | :zap: **NEW**: needs an API extension so that we can set `AD_User.C_BPartner_Location_ID`
`bpartner/name` | `name` |
`location/address1` | `address` |
`location/postal` | `postalCode` |
`location/city` | `city` |
`bpartner/phone` | `phone` |
`contact/fax` | `fax` | :zap: **NEW**: need to create a default-contact
`contact/email` | `email` | :zap: **NEW**: need to create a default-contact
`bpartner/albertaBPartner/timestamp` | `timestamp` | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`bpartner/albertaBPartner/role` | always `NursingHome` | :zap: **NEW**: needs new table `C_BPartner_AlbertaRole` and according API-extension
`bpartner/isCustomer` | always true |
`location/countryCode` | always DE |
`location/billToDefaul` | true |
`location/billTo` | true |
`location/shipToDefault` | true |
`location/shipTo` | true |


### `nursingService` mappings

Metasfresh | Alberta | Note
-- | -- | --
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
`Contact`-ESR | `_id` | :zap: **NEW**: need to create a default-contact
`contact/locationIdentifier` | `_id` | :zap: **NEW**: needs an API extension so that we can set `AD_User.C_BPartner_Location_ID`
`bpartner/name` | `name` |
`location/address1` | `address` |
`location/postal` | `postalCode` |
`location/city` | `city` |
`bpartner/phone` | `phone`|
`contact/fax` | `fax` | :zap: **NEW**: need to create a default-contact
`contact/email` | `email` | :zap: **NEW**: need to create a default-contact
`bpartner/url` | `website` |  :zap: **NEW**: column `C_BPartner.URL` exists, but API needs to be extended
`bpartner/albertaBPartner/timestamp` | `timestamp` | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`bpartner/albertaBPartner/role` | always `NursingService` | :zap: **NEW**: needs new table `C_BPartner_AlbertaRole` and according API-extension
`bpartner/isCustomer` | always true |
`location/countryCode` | always DE |
`location/billToDefault` | true |
`location/billTo` | true |
`location/shipToDefault` | true |
`location/shipTo` | true |


### `hospital` mappings

Metasfresh | Alberta | Note
-- | -- | --
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
`Contact`-ESR | `_id` | :zap: **NEW**: need to create a default-contact
`contact/locationIdentifier` | `_id` | :zap: **NEW**: needs an API extension so that we can set `AD_User.C_BPartner_Location_ID`
`bpartner/name2`  | `company` |
`bpartner/name` | `name` |
`bpartner/companyName` | `companyName` |
`bpartner/name3` | `additionalCompanyName` |
`location/address1` | `address` |
`location/postal` | `postalCode` |
`location/city` | `city` |
`bpartner/phone` | `phone` |
`contact/fax` | `fax` | :zap: **NEW**: need to create a default-contact
`contact/email` | `email` | :zap: **NEW**: need to create a default-contact
`bpartner/url` | `website` |  :zap: **NEW**: column `C_BPartner.URL` exists, but API needs to be extended
`bpartner/albertaBPartner/timestamp` | `timestamp` | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`bpartner/albertaBPartner/role` | always `Hostpital` | :zap: **NEW**: needs new table `C_BPartner_AlbertaRole` and according API-extension
`bpartner/isCustomer` | always true |
`location/countryCode` | always DE |
`location/billToDefault` | true |
`location/billTo` | true |
`location/shipToDefault` | true |
`location/shipTo` | true |

### `payer` mappings

Metasfresh | Alberta | Note
-- | -- | --
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
`bpartner/name` | `name` |
(nothing) | `type` | we don't sync this to the payer-`C_BPartner`, because it would be a new Alberta-table just for this field, and also we have this info in the patient window already.
`bpartner/code`| `iKNumber` |
`bpartner/albertaBPartner/timestamp` | `timestamp` | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`bpartner/albertaBPartner/role` | always `Payer` | :zap: **NEW**: needs new table `C_BPartner_AlbertaRole` and according API-extension
`bpartner/isCustomer` | always true |
`location/countryCode` | always DE |
`location/billToDefault` | true |
`location/billTo` | true |
`location/shipToDefault` | true |
`location/shipTo` | true |

### `pharmacy` mappings

Metasfresh | Alberta | Note
-- | -- | --
`BPartner`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
`Contact`-ESR | `_id` | :zap: **NEW**: need to create a default-contact
`contact/locationIdentifier` | `_id` | :zap: **NEW**: needs an API extension so that we can set `AD_User.C_BPartner_Location_ID`
`bpartner/mame` | `name` |
`location/address1` | `address` |
`location/postal` | `postalCode` |
`location/city` | `city` |
`bpartner/phone` | `phone` |
`contact/fax` | `fax` | :zap: **NEW**: need to create a default-contact
`contact/email` | `email` | :zap: **NEW**: need to create a default-contact
`bpartner/url` | `website` |  :zap: **NEW**: column `C_BPartner.URL` exists, but API needs to be extended
`bpartner/albertaBPartner/timestamp` | `timestamp` | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`bpartner/albertaBPartner/role` | always `Pharmacy` | :zap: **NEW**: needs new table `C_BPartner_AlbertaRole` and according API-extension
`bpartner/isCustomer` | always true |
`location/countryCode` | always DE |
`location/billToDefault` | true |
`location/billTo` | true |
`location/shipToDefault` | true |
`location/shipTo` | true |

### `patient/billingAddress` mappings

Metasfresh | Alberta | Note
-- | -- | --
`BPartnerLocation`-ESR | `billingAddress_` + `/patient/_id` |
`Contact`-ESR | `billingAddress_` + `/patient/_id` |
`contact/locationIdentifier` | `billingAddress_` + `/patient/_id` | :zap: **NEW**: needs an API extension so that we can set `AD_User.C_BPartner_Location_ID`
`contact/albertaContact/gender` | `gender` | :zap: **NEW**: needs new table `AD_User_Alberta` and according API-extension
`contact/albertaContact/title` | `title` | :zap: **NEW**: needs new table `AD_User_Alberta` and according API-extension
`location/bPartnerName` | `name` |
`location/address1` | `address` |
`location/postal` | `postalCode` |
`location/city` | `city` |
`location/countryCode` | always DE |
`location/billToDefault` | true |
`location/billTo` | true |

### `patient/deliveryAddress` mappings

Metasfresh | Alberta | Note
-- | -- | --
`BPartnerLocation`-ESR | `shippingAddress_` + `/patient/_id` |
`Contact`-ESR | `shippingAddress_` + `/patient/_id` |
`contact/locationIdentifier` | `shippingAddress_` + `/patient/_id` | :zap: **NEW**: needs an API extension so that we can set `AD_User.C_BPartner_Location_ID`
`contact/albertaContact/gender` | `gender` | :zap: **NEW**: needs new table `AD_User_Alberta` and according API-extension
`contact/albertaContact/title` | `title` | :zap: **NEW**: needs new table `AD_User_Alberta` and according API-extension
`location/address1` | Patient.deliveryAddress.address |
`location/postal` | Patient.deliveryAddress.postalCode |
`location/city` | Patient.deliveryAddress.city |
`location/countryCode` | always DE |
`location/bPartnerName` | Patient.deliveryAddress.name |
`location/shipToDefault` | true |
`location/shipTo` | true |

### `region` mappings

Not sure: shall we add a dedicated EP for regions??
I believe that would be the cleanest solution..

Metasfresh | Alberta | Note
-- | -- | --
????? | `_id` |
????? | `label` |
????? | `parent` |
????? | `timestamp` |

### `careGivers` mappings

Metasfresh | Alberta | Note
-- | -- | --
`BPartner`-ESR | `_id` |
`Contact`-ESR | `_id` |
`BPartnerLocation`-ESR | `_id` |
`contact/locationIdentifier` | `_id` | :zap: **NEW**: needs an API extension so that we can set `AD_User.C_BPartner_Location_ID`
????? | `customerId` | TODO :question: to IT-Labs
(nothing) | `type` | :zap: **NEW**: see `patient` mappings (`careGivers/type`)
(nothing) | `isLegalCarer` | :zap: **NEW**: see `patient` mappings (`careGivers/isLegalCarer`)
`contact/albertaContact/gender` | `gender` | :zap: **NEW**: needs new table `AD_User_Alberta` and according API-extension
`contact/albertaContact/title` | `title` | :zap: **NEW**: needs new table `AD_User_Alberta` and according API-extension
`contact/firstName` | firstName |
`contact/lastName` | lastName |
`bpartner/name` | firstName + " " + lastName |
`location/address1` | address |
`location/postal` | postalCode |
`location/city` | city |
`location/countryCode` | always DE |
`bpartner/phone` | phone |
`contact/phone` | phone |
`contact/mobilePhone` | mobilePhone |
`contact/fax` | fax |
`contact/email` | email |
`bpartner/albertaBPartner/archived` | `archived`  | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`bpartner/albertaBPartner/timestamp` | `timestamp` | :zap: **NEW**: needs new table `C_BPartner_Alberta` and according API-extension
`bpartner/isCustomer` | always true |
`contact/defaultContact` | always true |
`location/countryCode` | always DE |
`location/billToDefault` | true |
`location/billTo` | true |
`location/shipToDefault` | true |
`location/shipTo` | true |


### `user` mappings

* :zap: **NEW**: needs new alberta-config parameter
  * Create an alberta-external-systems-parameter for the C_BPartner_ID under which these a contacts shall be synched
* :zap: **NEW**: needs an additional Alberta-API-Call AFAIK
  
Metasfresh | Alberta | Note
-- | -- | --
`UserID`-ESR | `_id` |
`contact/firstName` | `firstName` |
`contact/lastName` | `lastName` |
`contact/email` | `email` |
`contact/albertaContact/timestamp` | `timestamp` |

## Products (metasfresh) => Articles (Alberta)

| Alberta                  | metasfresh                                           | mandatory | note                                                                                                                                                                                                                                                                                              |
|--------------------------|------------------------------------------------------|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| customerNumber           | M_Product.Value                                      | YES       | value is unique *per org*; if we push an article to alberta, it is per tenant, not globally; i think we can assume that two different metasfresh-AD_Orgs will never have overlapping M_Product.Value and also have the same alberta tenant (and if they do we can still add a  prefix on the fly) |
| name                     | M_Product.Name                                       | YES       |                                                                                                                                                                                                                                                                                                   |
| description              | M_Product.Description                                | NO        |                                                                                                                                                                                                                                                                                                   |
| additionalDescription    | M_Product_AlbertaArticle.AdditionalDescription       | NO        |                                                                                                                                                                                                                                                                                                   |
| manufacturer             | M_Product.Manufacturer_ID                            | NO        | column points to C_BPartner_ID. C_Partner needs to be flagged as manufacturer                                                                                                                                                                                                                     |
| manufacturerNumber       | M_Product.~~Manufacturer~~ ManufacturerArticleNumber | NO        | new column in M_Product                                                                                                                                                                                                                                                                           |
| therapyIds               | M_Product_AlbertaTherapy                             | NO        | label-field                                                                                                                                                                                                                                                                                       |
| billableTherapies        | M_Product_AlbertaBillableTherapy                     | NO        | label-field                                                                                                                                                                                                                                                                                       |
| productGroupId           | S_ExternalReference.ExternalReference                | NO        | Produkt groups themselves are **not** synched to Alberta. To make metasfresh send a productGroupId, one needs to create a metasfresh external reference record with System=ALBERTA and type=ProductCategory for each category                                                                     |
| size                     | M_Product_AlbertaArticle.Size                        | NO        |
| inventoryType            | M_Product_AlbertaArticle.InventoryType               | NO        | Ref-List as in the API definition                                                                                                                                                                                                                                                                 |
| status                   | M_Product_AlbertaArticle.Status                      | NO        | Ref-List as in the API definition                                                                                                                                                                                                                                                                 |
| medicalAidPositionNumber | M_Product_AlbertaArticle.MedicalAidPositionNumber    | NO        |
| stars                    | M_Product_AlbertaData.Stars                          | NO        |
| assortmentType           | M_Product_AlbertaArticle.AssortmentType              | NO        |
| pharmacyPrice            | MProductPrice.PriceStd aus AEP-Preisliste            | NO        | Price-List can be set in in alberta-external-system-config, with a preset default value                                                                                                                                                                                                           |
| fixedPrice               | MProductPrice.PriceList                              | NO        | aus AEP-Preisliste (same as pharmacyPrice) :exclamation: in the alberta-webui we have also a sales price and `n` health-insurance-prices; pricing is not 100% clear yet                                                                                                                           |
| purchaseRating           | M_Product_AlbertaArticle.PurchaseRating              | NO        | Ref-List as in the API definition                                                                                                                                                                                                                                                                 | 
|                          |                                                      | NO        | :exclamation: in the API the also have attributes; i'll ask them to update the API-docs                                                                                                                                                                                                           |

**Article.packagingUnits**

| Alberta              | metasfresh                                             | mandatory | note                                                                                                |
|----------------------|--------------------------------------------------------|-----------|-----------------------------------------------------------------------------------------------------|
|                      | M_Product_AlbertaPackagingUnit.M_HU_PI_Item_Product_ID | NO        | optional; if set then we can look it up later when recediving C_OLCands                             |
| unit                 | M_Product_AlbertaPackagingUnit.Unit                    | YES       | Ref-List with values `Stk` and `Ktn`                                                                |
| quantity             | M_Product_AlbertaPackagingUnit.Quantity                | YES       |                                                                                                     |
| pcn                  | M_Product_AlbertaPackagingUnit.PCN                     | YES       | :exclamation: did not create the column yet - needs to be clarified                                 |
| defaultPackagingUnit | M_Product_AlbertaPackagingUnit.IsDefaultPackagingUnit  | YES       | allow just one to be the default; :exclamation: this is not in the webui, so i didn't create it yet |
| archived             | M_Product_AlbertaPackagingUnit.IsArchived              | NO        | :exclamation: this is not in the webui, so i didn't create it yet                                   |

## Orders (Alberta) => Sales Order Candiates (metasfresh)


### Order

| Alberta | metasfresh | mandatory in mf | note |
| --- | --- | --- | --- |
| `_id` | `C_OLCand.ExternalHeaderId`, also used for `C_OLCand.POReference` if salesId is empty | Y | |
| `_id` | Alberta_Order.ExternalId | Y |
| `salesId` | `C_OLCand.POReference` | Y | |
| patientId | C_OLCand.C_BPartner_ID | Y |  we have `ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI` to retrieve patient data `ext-ALBERTA-patientId` from mf. it sets the metasfresh id for patient `C_BPartner_ID` |
| rootId | Alberta_Order.RootId | |
| creationDate | Alberta_Order.CreationDate | |
| deliveryDate | C_OLCand.DatePromised | |
| startDate | Alberta_Order.StartDate | |
| endDate | Alberta_Order.EndDate | |
| dayOfDelivery | Alberta_Order.DayOfDelivery | |
| nextDelivery | Alberta_Order.NextDelivery | |
| deliveryAddress | C_OLCand.C_BPartnerLocation_ID |  Y | note that we already have `de.metas.camel.alberta.BPartnerUpsertRequestProducer#getShippingAddress` | 
| doctorId | Alberta_Order.C_Doctor_BPartner_ID | ..via external reference lookup |
| pharmacyId | Alberta_Order.C_Pharmacy_BPartner_ID | ..via external reference lookup |
| therapyId |  C_OLCand_AlbertaTherapy | |
| therapyTypeIds | C_OLCand_AlbertaTherapyType | eine andere enum als therapy, mit mehr Werten |
| isInitialCare | Alberta_Order.IsInitialCare | |
| orderedArticleLines | | |
| createdBy | | Alberta-User => checken..|
| status | | |
| isSeriesOrder | Alberta_Order.IsSeriesOrder | |
| annotation | Alberta_Order.Annotation | Notiz für den Innendienst |
| archived | Alberta_Order.IsArchived | |
| updated | Alberta_Order.ExternallyUpdatedAt | |
| deliveryInformation | Alberta_Order.DeliveryInfo | |
| deliveryNote | Alberta_Order.DeliveryNote | |
| pharmacyId | C_OLCand.DropShip_Location_ID | N | send pharmacyId on dropship; if present, metasfresh will resolve it's respective location as `pharmacyId -> C_BPartner_Location.IsShipTo = 'Y'`|
| -- | C_OLCand.Bill_Location_ID | N | we have `ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI` to retrieve patient data from mf. it sets the metasfresh id for patient billing location `C_BPartner_Location.IsBillTo = 'Y'`|

### OrderedArticleLines

| Alberta | metasfresh | mandatory in mf | note |
| --- | --- | --- | --- |
| _id | C_OLCand.ExternalLineId | Y | |
| _id | Alberta_OrderedArticleLine.ExternalId | Y | |
| salesLineId | Alberta_OrderedArticleLine.SalesLineId | | |
| articleId | C_OLCand.M_Product_ID | Y | ..via external reference lookup |
| articleCustomerNumber |  | (ignore) | this is basically the M_Product.Value that we had send to Alberta..but who knows, maybe it was meanwhile edited in metasfresh | 
| quantity | C_OLCand.QtyEntered | Y | |
| unit | Alberta_OrderedArticleLine.Unit | | Ref-List with values Stk and Ktn |
| duration | | |
| isRentalEquipment | Alberta_OrderedArticleLine.IsRentalEquipment | |
| isPrivateSale | Alberta_OrderedArticleLine.IsPrivateSale | |
| updated | Alberta_OrderedArticleLine.ExternallyUpdatedAt | |
| duration.amount | Alberta_OrderedArticleLine.DurationAmount | |
| duration.timePeriod | Alberta_OrderedArticleLine.TimePeriod | |

### Duration

| Alberta | metasfresh | mandatory in mf | note |
| --- | --- | --- | --- |
| description | C_OLCand_AlbertaOrder.DurationDescription | | |
| amount | C_OLCand_AlbertaOrder.DurationAmount | | |
| timePeriod | C_OLCand_AlbertaOrder.DurationAmount | | Ref-List with `0=Unbekannt`, `1=Minute`, `2=Stunde`, `3=Tag`, `4=Woche`, `5=Monat`, `6=Quartal`, `7=Halbjahr`, `8=Jahr` |

### Additional C_OLCand-Columns

| Alberta | metasfresh                     | mandatory in mf | note                                                                                                       |
|---------|--------------------------------|-----------------|------------------------------------------------------------------------------------------------------------|
|         | C_OLCand.IsManualPrice         | Y               | const `N`                                                                                                  |
|         | C_OLCand.IsManualDiscount      | Y               | const `N`                                                                                                  |
|         | C_OLCand.IsImportedWitIssues   | Y               | const `Y`? we will have small REST-EP to clear them, after all were created                                |
|         | C_OLCand.DeliveryViaRule       | Y               | const 'D'                                                                                                  |
|         | C_OLCand.DelvieryRule          | Y               | const 'A'                                                                                                  |
|         | C_OLCand.DateCandidate         | Y               | :question:                                                                                                 |
|         | C_OLCand.AD_InputDataSource_ID | Y               | new hardcoded "Alberta" datasource                                                                         |
|         | C_OLCand.AD_DataDestination_ID | Y               | check the code..it *should* be automatically set to the one with internalname=DEST.de.metas.ordercandidate |
