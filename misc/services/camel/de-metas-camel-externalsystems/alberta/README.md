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

