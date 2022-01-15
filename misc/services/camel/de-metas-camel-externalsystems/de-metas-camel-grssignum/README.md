## Current data mapping

****

* There is a camel HTTP rest endpoint that can be configured using `ExternalSystem_Config_GRSSignum.BaseURL` && `ExternalSystem_Config_GRSSignum.CamelHttpResourceAuthKey`.

* Currently, the `GRSSignum` rest route can handle 3 different entity types: (routing by `request.FLAG`)
  * `FLAG` = `100` translates to `PushBPartners` route;
  * `FLAG`= `200` translates to `PushRawMaterials` route;
  * `FLAG`= `300` translates to `PushBOMs` route;


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
---- | `AD_Org_ID` | Y |  JsonRequestComposite.orgCode | `AD_Org_ID` computed from `login` orgCode |
MKREDID | `S_ExternalReference`.`externalReference` -> `C_BPartner_ID` | Y | JsonRequestBPartnerUpsertItem.bpartnerIdentifier | `MKREDID` -> in format: `ext-GRSSignum-[MKREDID]`|
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

* if provided then upsert a `C_BPartner_Product` for the given `M_Product_Id` following the below mappings:

GRSSignum | metasfresh-column | mandatory in mf | metasfresh-json | note | 
---- | ---- | ---- | ---- | ---- |
KRED.MKREDID | `C_BPartner_ID` | Y | JsonRequestBPartnerProductUpsert.bpartnerIdentifier | `MKREDID` -> in format: `ext-GRSSignum-[MKREDID]` |
---- | `UsedForVendor` | Y | JsonRequestBPartnerProductUpsert.usedForVendor | manually set to `true` |
KRED.STDKRED | `IsCurrentVendor` | Y | JsonRequestBPartnerProductUpsert.currentVendor | `KRED.STDKRED` == `1` => `true`; `KRED.STDKRED` == `0` => `false` | 

**GRSSignum => metasfresh BOMs**

* `JsonBOM` - payload sent to the camel http endpoint
  * see: `de.metas.camel.externalsystems.grssignum.api.model.JsonBOM`
  * note: during the push `JsonBOM` call there will be also an product upsert; how it works?
    * first we need to invoke the endpoint `api/v2/products/orgCode` with `JsonRequestProductUpsert` as payload to upsert the product ( making sure we have the latest version of that product in our system )
    * then we need to invoke the endpoint `api/v2/bom/version/{orgCode}` with `JsonBOMCreateRequest` as payload to push boms

1.`JsonBOM` - all `metasfresh-column` values refer to `M_Product` columns

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

2.If there is no `PP_Product_BOMVersions` for the given `M_Product_Id`, we create one (metasfresh side)

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

