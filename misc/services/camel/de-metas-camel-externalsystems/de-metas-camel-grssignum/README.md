## Current data mapping

****

**1. GRSSignum to metasfresh**
---

* There is a HTTP rest endpoint (`/camel/grs`) that can be secured using `ExternalSystem_Config_GRSSignum.CamelHttpResourceAuthKey`.
  * one can find those settings within `External system config` window (i.e. `windowId` = `541024`)

* Currently, the `GRSSignum` rest route can handle 3 different entity types: (routing by `request.FLAG`)
  * `FLAG` = `100` translates to `PushBPartners` route;
  * `FLAG`= `200` translates to `PushRawMaterials` route;
  * `FLAG`= `300` translates to `PushBOMs` route;
  * `FLAG`= `999` translates to `UpdateHU` route;

**GRSSignum JsonBPartner => metasfresh C_Bpartner**
---

* `JsonBPartner` - payload sent to the camel rest endpoint
  * see: `de.metas.camel.externalsystems.grssignum.api.model.JsonBPartner`

1. `JsonBPartner` - all `metasfresh-column` values refer to `C_BPartner` columns

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
   ---- | ---- | ---- | ---- | ---- |
KURZBEZEICHNUNG | `Name` | Y | JsonRequestBPartner.name | ---- |
KURZBEZEICHNUNG | `CompanyName ` | Y | JsonRequestBPartner.companyName | ---- |
INAKTIV | `IsActive` | Y |  JsonRequestBPartner.isActive | `INAKTIV` == `1` => `isActive` == `false`; `INAKTIV` == `0` => `isActive` == `true`|
---- | `IsVendor` | Y |  JsonRequestBPartner.vendor | manually set `true` for this flow |
MKREDID | `Value` | Y | JsonRequestBPartner.code | ---- |
---- | `AD_Org_ID` | Y |  JsonRequestComposite.orgCode | `AD_Org_ID` computed from `login` orgCode |
METASFRESHID | `C_BPartner_ID` | Y | JsonRequestBPartnerUpsertItem.bpartnerIdentifier| |
---- | ---- | ---- | syncAdvice | manually set as `CREATE_OR_MERGE` | 

**GRSSignum => metasfresh RawMaterials**

* `JsonProduct` - payload sent to the camel rest endpoint
  * see `de.metas.camel.externalsystems.grssignum.api.model.JsonProduct`

1. `JsonProduct` - all `metasfresh-column` values refer to `M_Product` columns

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note | 
---- | ---- | ---- | ---- | ---- |
---- |  `AD_Org_ID`  |  Y   |  ----  | `AD_Org_ID` computed from `login` orgCode |
ARTNR  | `Value` | Y |  JsonRequestProduct.code | - |
TEXT + " " + TEXT2  | `Name` | Y |  JsonRequestProduct.name | - |
INAKTIV  | `IsActive` | Y |  JsonRequestProduct.isActive |`INAKTIV` == `1` => `isActive` == `false`; `INAKTIV` == `0` => `isActive` == `true` |
-----  | `ProductType` | Y |  JsonRequestProduct.type | manually set to `ITEM` |
-----  | `C_UOM_ID` | Y |  JsonRequestProduct.uomCode | `C_UOM_ID` corresponding to `KGM` uomCode which is manually set |
ARTNRID  | `S_ExternalReference`.`externalReference` -> `M_Product_ID` | Y |  JsonRequestProductUpsertItem.productIdentifier | `ARTNRID` -> in format: `ext-GRSSignum-[ARTNRID]` |
KRED  | ----- | ---- |  JsonRequestProduct.bpartnerProductItem List | see details below |
---- | ---- | ---- | syncAdvise | manually set as `CREATE_OR_MERGE` | 

2. `JsonBPartnerProduct` - all `metasfresh-column` values refer to `C_BPartner_Product` columns

* if provided, then upserts a `C_BPartner_Product` for the given `M_Product_Id` following the below mappings:

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note | 
---- | ---- | ---- | ---- | ---- |
KRED.MKREDID | `C_BPartner.Value` | N | - | |
KRED.METASFRESHID | `C_BPartner_ID` | Y | JsonRequestBPartnerProductUpsert.bpartnerIdentifier | |
---- | `UsedForVendor` | Y | JsonRequestBPartnerProductUpsert.usedForVendor | manually set to `true` |
KRED.STDKRED | `IsCurrentVendor` | Y | JsonRequestBPartnerProductUpsert.currentVendor | `KRED.STDKRED` == `1` => `true`; `KRED.STDKRED` == `0` => `false` | 
KRED.LIEFERANTENFREIGABE | `IsExcludedFromPurchase` | N | JsonRequestBPartnerProductUpsert.excludedFromPurchase | `KRED.LIEFERANTENFREIGABE` == `1` => `excludedFromPurchase = false`; `KRED.LIEFERANTENFREIGABE` == `0` => `excludedFromPurchase = true`, default value `KRED.LIEFERANTENFREIGABE` == `0`| 
---- | `ExclusionFromPurchaseReason` | N | JsonRequestBPartnerProductUpsert.exclusionFromPurchaseReason | `JsonRequestBPartnerProductUpsert.excludedFromPurchase` == `true` => `exclusionFromPurchaseReason` has value `Imported setting`, otherwise is `null`| 
KRED.INAKTIV | `IsActive` | N | JsonRequestBPartnerProductUpsert.active | `KRED.INAKTIV` == `1` => `IsActive = false`; `KRED.INAKTIV` == `0` => `IsActive = true`; default value is `KRED.INAKTIV` == `0` => `IsActive = true`| 

**GRSSignum => metasfresh BOMs**
---

* `JsonBOM` - payload sent to the camel http endpoint
  * see: `de.metas.camel.externalsystems.grssignum.api.model.JsonBOM`
  * note: during the push `JsonBOM` call there will be also a product upsert; how it all works:
    * first the process will invoke `api/v2/products/orgCode` with `JsonRequestProductUpsert` as payload to upsert the product ( making sure the latest version of that product is present in metasfresh)
    * then it invokes the endpoint `api/v2/bom/version/{orgCode}` with `JsonBOMCreateRequest` as payload to push the actual bom formula

1.`JsonBOM` - all `metasfresh-column` values refer to `M_Product` columns except the last one that refers to `C_BPartner_Product`

* metasfresh-json => `JsonRequestProductUpsert`

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
---- | `AD_Org_ID` | Y | ---- | `AD_Org_ID` computed from `login` orgCode |
ARTNR  | `Value` | Y |  JsonRequestProduct.code | - |
TEXT + " " +TEXT2  | `Name` | Y |  JsonRequestProduct.name | - |
INAKTIV  | `IsActive` | Y |  JsonRequestProduct.isActive | `INAKTIV` == `1` => `isActive` == `false`; `INAKTIV` == `0` => `isActive` == `true` |
-----  | `ProductType` | Y |  JsonRequestProduct.type | manually set to `ITEM` |
-----  | `C_UOM_ID` | Y |  JsonRequestProduct.uomCode | `C_UOM_ID` corresponding to `KGM` uomCode which is manually set |
ARTNRID  | `S_ExternalReference`.`externalReference`  -> `M_Product_ID` | Y |  JsonRequestProductUpsertItem.productIdentifier | `ARTNRID` -> in format: `ext-GRSSignum-[ARTNRID]` |
GTIN | `gtin` | N | `gtin` |  | 
---- | ---- | ---- | syncAdvise | manually set as `CREATE_OR_MERGE` | 
METASFRESHID | `C_BPartner_Product.C_BPartner_ID` | Y | JsonRequestBPartnerProductUpsert.bpartnerIdentifier | ---- |

2.If there is no `PP_Product_BOMVersions` for the given `M_Product_Id`, the process will create one (metasfresh side)

* all `metasfresh-column` values refer to `PP_Product_BOMVersions` columns

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
---- | `S_ExternalReference`.`externalReference` -> `M_Product_ID` | Y | ---- | computed `productId` from `JsonBOM.ARTNRID` |
---- | `Name` | Y | ---- | `JsonBOM.TEXT` + " " + `JsonBOM.TEXT2` |

3.`JsonBOM` - all `metasfresh-column` values refer to `PP_Product_BOM` columns

* metasfresh-json => `JsonBOMCreateRequest `

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note | 
---- | ---- | ---- | ---- | ---- |
---- | `AD_Org_ID` | Y |  ----  | `AD_Org_ID` computed from `login` orgCode |
ARTNRID  | `S_ExternalReference`.`externalReference` -> `M_Product_ID` | Y |  productIdentifier | `ARTNRID` -> in format: `ext-GRSSignum-[ARTNRID]` |
--- | `Value` | Y |  productCode | matching `M_Product`.`value` via `ARTNRID`   |
TEXT + " " + TEXT2  | `Name` | Y |  name | - |
INAKTIV  | `IsActive` | Y |  isActive | `INAKTIV` == `1` => `isActive` == `false`;`INAKTIV` == `0` => `isActive` == `true` |
-----  | `C_UOM_ID` | Y |  uomCode |  `C_UOM_ID` corresponding to `KGM` uomCode which is manually set |
---- | `ValidFrom` | Y | ---- | manually set to current time of the execution |
---- | `PP_Product_BOMVersions_ID` | Y | ---- | determined `PP_Product_BOMVersions_ID` based on incoming `M_Product_ID`|
DETAIL | ----- | ---- | lines | see below: `JsonBOMLine` |

4.`JsonBOMLine` - all `metasfresh-column` values refer to `PP_Product_BOMLine` columns

* metasfresh-json => `JsonCreateBOMLine`

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- | 
ARTNRID | `S_ExternalReference`.`externalReference` -> `M_Product_ID` | Y | productIdentifier | `ARTNRID` -> in format: `ext-GRSSignum-[ARTNRID]` | 
ANTEIL | `QtyBOM` | N | JsonQuantity.qty | ---- | 
UOM | `C_UOM_ID` | N | JsonQuantity.uomCode | `C_UOM_ID` computed from the incoming `uomCode` |
POS | `Line` | Y | line | ---- |
----| `IsQtyPercentage` | N | isQtyPercentage | manually set to `true` | 
---- | `Scrap` | N | scrap | JsonBOM.VERLUST value |

**GRSSignum => metasfresh HU Attribute**
---

* `JsonHUUpdate` - payload sent to camel http endpoint
  * see: `de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonHUUpdate`
  * metasfresh-json => `JsonHUAttributesRequest`

1. `JsonHUUpdate` - the columns refers to `M_HU`

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- | 
ID | `M_HU_ID` | Y | JsonHUAttributesRequest.id  | metasfresh id for the hu in question | 
ATTRIBUTES | --- | Y | JsonHUAttributesRequest.attributes  | see details below | 

2. `ATTRIBUTES`

- metasfresh-json => `JsonHUAttributes`
- defined as a <key, value> pair for a hu attribute `Map<String, Object>`

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- | 
ATTRIBUTES.key | `M_Attribute.Name` | Y | JsonHUAttributes.key  | name for a specific M_Attribute from metasfresh | 
ATTRIBUTES.value | `M_HU_Attribute.Value` | Y | JsonHUAttributes.value  | attribute value mapped based on M_Attribute_ID identified from ATTRIBUTES.key and M_HU_ID | 

**2. metasfresh to GRSSignum**
---

* There are exports performed from metasfresh to GRSSignum using `ExternalSystem_Config_GRSSignum.BaseURL` && `ExternalSystem_Config_GRSSignum.AuthToken`.

* Currently, the `GRSSignum` export routes are
  * `GRSSignum-exportBPartner`;
  * `GRSSignum-exportHU`;

**metasfresh C_Bpartner => GRSSignum JsonBPartner**
---

1. `C_Bpartner` -> exported as `JsonBPartner`

- metasfresh json `JsonResponseComposite` to `JsonBPartner`

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
   ---- | ---- | ---- | ---- | ---- |
FLAG | ---- | Y | ---- | GRSSignum route flag |
MKREDID | `C_Bpartner.Value` | Y | JsonResponseBPartner.code | ---- |
KURZBEZEICHNUNG | `C_Bpartner.Name ` | Y | JsonResponseBPartner.name + JsonResponseBPartner.name2 + JsonResponseBPartner.name3 | ---- |
INAKTIV | `C_Bpartner.IsActive` | Y |  JsonRequestBPartner.isActive | `isActive` == `false` => `INAKTIV` == `1`; `isActive` == `true` => `INAKTIV` == `0`|
MID | `ExternalSystem_Config_GRSSignum.TenantId` | N |  JsonExternalSystemRequest.parameters.TenantId | ---- |
NAMENSZUSATZ | `C_Bpartner.Name2` | N |  JsonRequestBPartner.name2 | ---- |
METASFRESHID | `C_Bpartner.C_BPartner_ID` | Y | JsonResponseBPartner.JsonMetasfreshId | ---- |
KREDITORENNR | `C_Bpartner.CreditorID` | N | JsonResponseBPartner.creditorId | ---- |
DEBITORRENNR | `C_Bpartner.DebtorID` | N | JsonResponseBPartner.debtorId | ---- |
ANHANGORDNER | ---- | N | ---- | `basePath` for `C_BPartner` directory, computed from `externalsystem_config_grssignum.basepathforexportdirectories` + `c_bpartner.value` |
METASFRESHURL | ---- | N | JsonResponseBPartner.metasfreshUrl | `baseUrl/window/{specificBPartnerWindowId}/{C_BPartner_ID}` |
ADRESSE 1 | `C_BPartner_Location.C_Location.Address1` | N |  JsonResponseLocation.address1 | ---- |
ADRESSE 2 | `C_BPartner_Location.C_Location.Address2` | N |  JsonResponseLocation.address2 | ---- |
ADRESSE 3 | `C_BPartner_Location.C_Location.Address3` | N |  JsonResponseLocation.address3 | ---- |
ADRESSE 4 | `C_BPartner_Location.C_Location.Address4` | N |  JsonResponseLocation.address4 | ---- |
PLZ | `C_BPartner_Location.C_Location.postal` | N |  JsonResponseLocation.postal | ---- |
ORT | `C_BPartner_Location.C_Location.City` | N |  JsonResponseLocation.city | ---- |
LANDESCODE | `C_BPartner_Location.C_Location.C_Country.CountryCode` | N |  JsonResponseLocation.countryCode | ---- |
GLN | `C_BPartner_Location.gln` | N |  JsonResponseLocation.gln | ---- |
KONTAKTE | `Ad_User` | N |  JsonResponseContact | details below |

* `KONTAKTE` - all the `metasfresh-column` are from `Ad_User` table

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
  ---- | ---- | ---- | ---- | ---- |
METASFRESHID | `AD_User_ID` | Y | JsonResponseContact.metasfreshId | `AD_User_ID` record id from metasfresh|
NACHNAME | `Lastname` | N | JsonResponseContact.lastName |---- |
VORNAME | `FirstName` | N | JsonResponseContact.firstName |---- |
ANREDE | `C_Greeting.Greeting` | N | JsonResponseContact.greeting |---- |
TITEL | `Title` | N | JsonResponseContact.title |---- |
POSITION | `C_Job.Name` | N | JsonResponseContact.JsonResponseContactPosition.name |---- |
EMAIL | `Email` | N | JsonResponseContact.email |---- |
TELEFON | `Phone` | N | JsonResponseContact.phone |---- |
MOBIL | `Phone2` | N | JsonResponseContact.phone2 |---- |
FAX | `Fax` | N | JsonResponseContact.fax |---- |
ROLLEN | `C_User_Assigned_Role.C_User_Role.Name` | N | JsonResponseContact.JsonResponseContactRole.name |array of all the user rolles assigned |

**metasfresh M_HU => GRSSignum**
---

* for HU export there is no custom GRSSignum json, the process exports directly the metasfresh object `JsonHU`
* all the `metasfresh-json` are from `JsonHU`

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
  ---- | ---- | ---- | ---- | ---- |
---- | `M_HU.M_HU_ID` | Y | id | ---- |
---- | `M_HU.HUStatus` | Y | huStatus | ---- |
---- | `M_HU.M_HU_PI_Version.HU_UnitType` | Y | jsonHUType | ---- |
---- | `M_Warehouse.Value` | N | warehouseValue | retrieve warehouse value based on M_HU.M_Locator.M_Warehouse_ID |
---- | `M_Locator.Value` | N | locatorValue | retrieve locator value based on M_HU.M_Locator |
---- | `M_HU.M_HU_PI_Item_Product.M_Product` | Y | products | explained below as `JsonHUProduct` |
---- | `M_HU.M_HU_Attribute` | Y | attributes | explained below as `JsonHUAttributes` |
---- | `List<JsonHU>` | Y | includedHUs | a list of all the included HUs if any |

* `JsonHUProduct`

  GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
    ---- | ---- | ---- | ---- | ---- |
  ---- | `M_Product_ID.Value` | Y | productValue | ---- |
  ---- | `M_Product_ID.Name` | Y | productName | ---- |
  ---- | `M_HU_Storage.Qty` | Y | qty | ---- |
  ---- | `M_HU_Storage.C_UOM.X12DE355` | Y | uom | ---- |

* `JsonHUAttributes`

  - defined as a <key, value> pair for a hu attribute `Map<String, Object>`
  - a `M_HU_Attribute` is identified based on a `M_HU_ID`

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- | 
---- | `M_Attribute.Name` | Y | key  | name for a specific M_Attribute from metasfresh | 
---- | `M_HU_Attribute.Value` | Y | value  | attribute value mapped based on M_Attribute_ID identified from ATTRIBUTES.key.M_Attribute_ID and M_HU_ID | 

