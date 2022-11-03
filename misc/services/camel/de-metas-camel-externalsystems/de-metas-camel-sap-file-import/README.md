## Current data mapping

****

## Values pulled from SAP via an SFTP route

* `Product`
* `CreditLimit`

## Values computed in metasfresh

* `JsonExternalSystemRequest.parameters.SFTP_HostName`
* `JsonExternalSystemRequest.parameters.SFTP_Port`
* `JsonExternalSystemRequest.parameters.SFTP_Username`
* `JsonExternalSystemRequest.parameters.SFTP_Password`
* `JsonExternalSystemRequest.parameters.SFTP_Target_Directory`
* `JsonExternalSystemRequest.parameters.ProcessedDirectory`
* `JsonExternalSystemRequest.parameters.ErroredDirectory`
* `JsonExternalSystemRequest.parameters.PollingFrequency`

Configs available in `Externalsystem_Config_SAP`:

| Column name         | Accepted values | Description                                                                  |
|---------------------|-----------------|------------------------------------------------------------------------------|
| Hostname            | string          | sftp server hostname                                                         |
| Port                | number          | sftp server port                                                             |
| Username            | string          | sftp server authentication username                                          |
| Password            | string          | sftp server authentication password                                          | 
| Product Target Directory    | string          | the location used to pull products from the sftp server                               | 
| CreditLimit Target Directory    | string          | the location used to pull credit limits from the sftp server                 | 
| Processed Directory | string          | the location where the processed files will be moved                         | 
| Errored Directory   | string          | the location where the files will be moved in case of error while processing |
| Polling Frequency   | number          | the frequency with which the files are polled from the sftp server           |

## **SAP => metasfresh product**

* `Product` - pulled via an SFTP camel route

First, the SFTP consumer must be configured using `Externalsystem_Config_SAP` and started by invoking the `SAP-startProductsSync` dedicated route.

1. Product - all `metasfresh-column` values refer to `M_Product` columns

SAP | metasfresh-column                     | mandatory in mf | metasfresh-json                                | note                                                               |
---- |---------------------------------------|-----------------|------------------------------------------------|--------------------------------------------------------------------|
ProductRow.materialCode + "_" + ProductRow.name | `value`                               | Y               | JsonRequestProduct.code                        |                                                                    |
ProductRow.uom | `c_uom_id`                            | Y                                                | JsonRequestProduct.uomCode                     |                                                                    |
ProductRow.name | `name`                                | Y                                               | JsonRequestProduct.name                        |                                                                    |
ProductRow.sectionCode | `m_sectioncode_id`                    | N                                        | JsonRequestProduct.sectionCode                 |                                                                    |
---- | `isstocked`                           | Y                                                          | JsonRequestProduct.stocked                     | always set to `true`                                               |
---- | `discontinued`                        | N                                                          | JsonRequestProduct.discontinued                | always set to `false`                                              |
ProductRow.materialGroup | `producttype`                                                 | Y              | JsonRequestProduct.type                        | alwasys set to JsonRequestProduct.Type.ITEM                        |
ProductRow.materialCategory | `m_product_category_id`               | Y                                   | JsonRequestProduct.productCategoryIdentifier   | never set, but will be STANDARD due to default value in metasfresh |
--- | ----                                  | N                                                           | JsonRequestProductUpsert.syncAdvise            | default value CREATE_OR_MERGE                                      |
ProductRow.materialCode | `S_ExternalReference.externalReference` | Y                                     | JsonRequestProductUpsertItem.productIdentifier | ext-SAP-MaterialCode                                               |

## **SAP => metasfresh bpartner_creditlimit**

* `CreditLimit` - pulled via an SFTP camel route

First, the SFTP consumer must be configured using `Externalsystem_Config_SAP` and started by invoking the `SAP-startCreditLimitsSync` dedicated route.

### CreditLimit - all `metasfresh-column` values refer to `C_BPartner_CreditLimit` columns
| SAP                              | metasfresh-column                       | mandatory in mf   | metasfresh-json                                        | note                                                                                                                                                                                                              |
| ----                             | ------------------------                | ----------------- | --------------------------------------------------     | --------------------------------------------------------------------                                                                                                                                              |
| ----                             | `S_ExternalReference.ExternalReference` | Y                 | JsonRequestCreditLimitUpsertItem.creditLimitIdentifier | -> in format: `ext-SAP-[creditLimitIdentifier]` where `creditLimitIdentifier` = `CreditLimitRow.CreditAccount` + "_" + type + "_" + CreditLimitRow.EffectiveDateFrom                                              |
| CreditLimitRow.CreditAccount     | `C_BPartner_ID`                         | Y                 | JsonRequestBPartnerUpsertItem.bpartnerIdentifier       | -> in format: `ext-SAP-[partnerIdentifier]`                                                                                                                                                                       |
| CreditLimitRow.CreditControlArea | ??`M_SectionCode_ID`                    | N                 | JsonRequestCreditLimitUpsertItem.sectionCode           | should we have `M_SectionCode_ID` on BPartner/CreditLimit ?                                                                                                                                                       |
| CreditLimitRow.EffectiveDateFrom | `DateFrom`                              | N                 | JsonRequestCreditLimitUpsertItem.dateFrom              |                                                                                                                                                                                                                   |
| CreditLimitRow.EffectiveDateTo   | `IsActive`                              | Y                 | JsonRequestCreditLimitUpsertItem.isActive              | if `CreditLimitRow.DeleteFlag` is not set, check if `currentDate` is between `CreditLimitRow.EffectiveDateFrom` & `CreditLimitRow.EffectiveDateTo` => `isActive` = `true`, else `isActive` = `false`              |
| CreditLimitRow.CurrencyCode      | `C_Currency_ID`                         | N                 | JsonRequestCreditLimitUpsertItem.currencyCode          |                                                                                                                                                                                                                   |
| CreditLimitRow.CreditLine        | `Amount`                                | Y                 | JsonRequestCreditLimitUpsertItem.amount                |                                                                                                                                                                                                                   |
| ----                             | `C_CreditLimit_Type_ID`                 | Y                 | JsonRequestCreditLimitUpsertItem.type                  | always set to `Insurance`                                                                                                                                                                                         |
| ----                             | `AD_Org_ID`                             | Y                 | JsonRequestCreditLimitUpsertItem.orgCode               | always set to `ExternalSystem_Config.AD_Org_ID` from `JsonExternalSystemRequest`                                                                                                                                  |
| ----                             | ----                                    | N                 | JsonRequestCreditLimitUpsert.syncAdvise                | default value `CREATE_OR_MERGE`                                                                                                                                                                                   |
| CreditLimitRow.DeleteFlag        | `IsActive`                              | Y                 | ----                                                   | if `CreditLimitRow.DeleteFlag` = `Y` => `isActive` = `N` ELSE check `CreditLimitRow.EffectiveDateFrom` and `CreditLimitRow.EffectiveDateTo`                                                                       |
| CreditLimitRow.CreditType        | ----                                    | N                 | ----                                                   | ----                                                                                                                                                                                                              |
| CreditLimitRow.CreditLineFlag    | ----                                    | N                 | ----                                                   | hoq should be used ??                                                                                                                                                                                             |