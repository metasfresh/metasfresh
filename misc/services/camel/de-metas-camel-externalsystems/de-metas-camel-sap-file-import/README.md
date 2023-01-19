## Current data mapping

****

## Values pulled from SAP

* `Product`
* `BPartner`
* `CreditLimit`

## Values computed in metasfresh

### SFTP

* `JsonExternalSystemRequest.parameters.SFTP_HostName`
* `JsonExternalSystemRequest.parameters.SFTP_Port`
* `JsonExternalSystemRequest.parameters.SFTP_Username`
* `JsonExternalSystemRequest.parameters.SFTP_Password`
* `JsonExternalSystemRequest.parameters.SFTP_Product_Target_Directory`
* `JsonExternalSystemRequest.parameters.SFTPProductFileNamePattern`
* `JsonExternalSystemRequest.parameters.SFTP_BPartner_Target_Directory`
* `JsonExternalSystemRequest.parameters.SFTPBPartnerFileNamePattern`
* `JsonExternalSystemRequest.parameters.SFTP_CreditLimit_Target_Directory`
* `JsonExternalSystemRequest.parameters.SFTPCreditLimitFileNamePattern`
* `JsonExternalSystemRequest.parameters.SFTPProcessedDirectory`
* `JsonExternalSystemRequest.parameters.SFTPErroredDirectory`
* `JsonExternalSystemRequest.parameters.SFTPPollingFrequencyInMs`
* `JsonExternalSystemRequest.parameters.CheckDescriptionForMaterialType` - If set, the process also checks the product description when trying to lookup material type.

### Local File

* `JsonExternalSystemRequest.parameters.LocalFileRootLocation`
* `JsonExternalSystemRequest.parameters.LocalFile_Product_Target_Directory`
* `JsonExternalSystemRequest.parameters.LocalFileProductFileNamePattern`
* `JsonExternalSystemRequest.parameters.LocalFile_BPartner_Target_Directory`
* `JsonExternalSystemRequest.parameters.LocalFileBPartnerFileNamePattern`
* `JsonExternalSystemRequest.parameters.LocalFile_CreditLimit_Target_Directory`
* `JsonExternalSystemRequest.parameters.LocalFileCreditLimitFileNamePattern`
* `JsonExternalSystemRequest.parameters.LocalFileProcessedDirectory`
* `JsonExternalSystemRequest.parameters.LocalFileErroredDirectory`
* `JsonExternalSystemRequest.parameters.LocalFilePollingFrequencyInMs`
* `JsonExternalSystemRequest.parameters.CheckDescriptionForMaterialType` - If set, the process also checks the product description when trying to lookup material type.

## **SAP => metasfresh product**

* `Product` - pulled via an SFTP camel route

First, the SFTP consumer must be configured using `Externalsystem_Config_SAP_SFTP` and started by invoking the `SAP-startProductsSyncSFTP` dedicated route. In order to stop the consumer, the `SAP-stopProductSyncSFTP` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `Externalsystem_Config_SAP_SFTP`:

| Column name                       | Accepted values | Description                                                                                                                 |
|-----------------------------------|-----------------|-----------------------------------------------------------------------------------------------------------------------------|
| Hostname                          | string          | sftp server hostname                                                                                                        |
| Port                              | number          | sftp server port                                                                                                            |
| Username                          | string          | sftp server authentication username                                                                                         |
| Password                          | string          | sftp server authentication password                                                                                         | 
| Product Target Directory          | string          | the location used to pull products from the sftp server                                                                     |
| Product Filename Pattern          | string          | regex used to identify product-containing files (useful if multiple type of files are placed in the same source folder)     |
| Processed Directory               | string          | the location where the processed files will be moved                                                                        | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                                |
| Polling Frequency In Milliseconds | number          | the frequency used to poll files from the sftp server (in milliseconds)                                                     |

* `Product` - pulled via a local file camel route

First, the local file consumer must be configured using `Externalsystem_Config_SAP_LocalFile` and started by invoking the `SAP-startProductsSyncLocalFile` dedicated route. In order to stop the consumer, the `SAP-stopProductSyncLocalFile` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `Externalsystem_Config_SAP_LocalFile`:

| Column name                       | Accepted values | Description                                                                                                                 |
|-----------------------------------|-----------------|-----------------------------------------------------------------------------------------------------------------------------|
| Local Root Location               | string          | the local root location (equivalent to the SFTP server root location)                                                       |
| Credit-Limit Target Directory     | string          | the location used to pull credit-limit files from the local machine                                                         |
| Credit-Limit Filename Pattern     | string          | regex used to identify CreditLimit-containing files (useful if multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                        | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                                |
| Polling Frequency In Milliseconds | number          | the frequency used to poll files from the local machine (in milliseconds)                                                   |

1. Product - all `metasfresh-column` values refer to `M_Product` columns

SAP | metasfresh-column                     | mandatory in mf | metasfresh-json                                | note                                                               |
---- |--------------------------------------|-----------------|------------------------------------------------|--------------------------------------------------------------------|
ProductRow.materialCode + " (" + ProductRow.sectionCode + ")" | `value`                                        | Y               | JsonRequestProduct.code                        |                                                                    |
ProductRow.uom | `c_uom_id`                            | Y               | JsonRequestProduct.uomCode                     |                                                                    |
ProductRow.name | `name`                                | Y               | JsonRequestProduct.name                        |                                                                    |
ProductRow.sectionCode | `m_sectioncode_id`                    | N               | JsonRequestProduct.sectionCode                 |                                                                    |
---- | `isstocked`                           | Y               | JsonRequestProduct.stocked                     | always set to `true`                                               |
---- | `discontinued`                        | N               | JsonRequestProduct.discontinued                | always set to `false`                                              |
ProductRow.materialGroup | `producttype`                         | Y               | JsonRequestProduct.type                        | always set to JsonRequestProduct.Type.ITEM                         |
ProductRow.materialType | `m_product_category_id`               | Y               | JsonRequestProduct.productCategoryIdentifier   | resolved from `Product category mapping` or using `ProductRow.description` when`CheckDescriptionForMaterialType` is enabled. If none is provided then default value `Standard` is set. |
ProductRow.productHierarchy | `SAP_ProductHierarchy`               | Y               | JsonRequestProduct.sapProductHierarchy   |  |
--- | ----                                  | N               | JsonRequestProductUpsert.syncAdvise            | default value CREATE_OR_MERGE                                      |
ProductRow.materialCode | `S_ExternalReference.externalReference` | Y               | JsonRequestProductUpsertItem.productIdentifier | ext-SAP-MaterialCode                                               |
--- | `S_ExternalReference.isreadonlyinmetasfresh` | Y               | JsonRequestBPartnerUpsertItem.isReadOnlyInMetasfresh                                                                        | always set to `true`                                               |
--- | `S_ExternalReference.externalsystem_config_id` | N               | JsonRequestBPartnerUpsertItem.externalSystemConfigId                                                                        | always set to the ID of the SAP External System Config             |

## **SAP => metasfresh bpartner**

* `BPartner` - pulled via an SFTP camel route

First, the SFTP consumer must be configured using `Externalsystem_Config_SAP_SFTP` and started by invoking the `SAP-startBPartnerSyncSFTP` dedicated route. In order to stop the consumer, the `SAP-stopBPartnerSyncSFTP` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `Externalsystem_Config_SAP_SFTP`:

| Column name                       | Accepted values | Description                                                                                                             |
|-----------------------------------|-----------------|-------------------------------------------------------------------------------------------------------------------------|
| Hostname                          | string          | sftp server hostname                                                                                                    |
| Port                              | number          | sftp server port                                                                                                        |
| Username                          | string          | sftp server authentication username                                                                                     |
| Password                          | string          | sftp server authentication password                                                                                     |
| BPartner Target Directory         | string          | the location used to pull business partners from the sftp server                                                        |
| BPartner Filename Pattern         | string          | regex used to identify partner-containing files (useful if multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                    | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                            |
| Polling Frequency In Milliseconds | number          | the frequency with which the files are polled from the sftp server (in milliseconds)                                    |

* `BPartner` - pulled via a local file camel route

First, the local file consumer must be configured using `Externalsystem_Config_SAP_LocalFile` and started by invoking the `SAP-startBPartnerSyncLocalFile` dedicated route. In order to stop the consumer, the `SAP-stopBPartnerSyncLocalFile` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `Externalsystem_Config_SAP_LocalFile`:

| Column name                       | Accepted values | Description                                                                                                             |
|-----------------------------------|-----------------|-------------------------------------------------------------------------------------------------------------------------|
| Local Root Location               | string          | the local root location (equivalent to the SFTP server root location)                                                   |
| BPartner Target Directory         | string          | the location used to pull business partners from the local machine                                                      |
| BPartner Filename Pattern         | string          | regex used to identify partner-containing files (useful if multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                    | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                            |
| Polling Frequency In Milliseconds | number          | the frequency with which the files are polled from the local machine (in milliseconds)                                  |

1. Section Group Partner - all `metasfresh-column` values refer to `C_BPartner` columns

SAP | metasfresh-column                                | mandatory in mf | metasfresh-json                                                                                      | note                                                                       |
---- |--------------------------------------------------|-----------------|------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------|
BPartnerRow.partnerCode | `C_BPartner.value`                               | Y               | JsonRequestBPartner.code                                                                             | BPartnerRow.partnerCode(without the last 2 digits) + "00"                  |
BPartnerRow.name1 | `C_BPartner.companyname`                         | N               | JsonRequestBPartner.companyName                                                                      |                                                                            |
BPartnerRow.name2 | `C_BPartner.name`                                | Y               | JsonRequestBPartner.name                                                                             |                                                                            |
--- | `C_BPartner.IsProspect`                          | N               | JsonRequestBPartner.prospect                                                                      | always set to `false`                                                          |
--- | ----                                             | N               | JsonRequestBPartnerUpsert.syncAdvise                                                                  | default value CREATE_OR_MERGE                                              |
BPartnerRow.partnerCode | `S_ExternalReference.externalreference`          | Y               | JsonRequestBPartnerUpsertItem.bpartnerIdentifier | "ext-SAP-" + BPartnerRow.partnerCode(without the last 2 characters) + "00" |
--- | `S_ExternalReference.isreadonlyinmetasfresh`     | Y               | JsonRequestBPartnerUpsertItem.isReadOnlyInMetasfresh                                                 | always set to `true`                                                       |
--- | `S_ExternalReference.externalsystem_config_id`   | N               | JsonRequestBPartnerUpsertItem.externalSystemConfigId                                                 | always set to the ID of the SAP External System Config                     |

2. Section Partner - all `metasfresh-column` values refer to `C_BPartner` columns

SAP | metasfresh-column                                | mandatory in mf | metasfresh-json                                      | note                                                                                                                            |
---- |--------------------------------------------------|-----------------|------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
BPartnerRow.partnerCode + BPartnerRow.section | `C_BPartner.value`   | Y               | JsonRequestBPartner.code                             | BPartnerRow.partnerCode(without the last 2 digits) + "00" + " (" + BPartnerRow.section  + ")"                                    |
BPartnerRow.name1 | `C_BPartner.companyname`                         | N               | JsonRequestBPartner.companyName                      |                                                                                                                                 |
BPartnerRow.name2 | `C_BPartner.name`                                | Y               | JsonRequestBPartner.name                             |                                                                                                                                 |
BPartnerRow.section | `C_BPartner.m_sectioncode_id`                    | N               | JsonRequestBPartner.sectionCode                      |                                                                                                                                 |
BPartnerRow.salesPaymentTerms | `C_BPartner.c_paymentterm_id`                    | N               | JsonRequestBPartner.salesPaymentTermIdentifier       |                                                                                                                                 |
BPartnerRow.purchasePaymentTerms | `C_BPartner.po_paymentterm_id`                   | N               | JsonRequestBPartner.purchasePaymentTermIdentifier    |                                                                                                                                 |
--- | `C_BPartner.deliveryrule`                        | N               | JsonRequestBPartner.deliveryRule                     | always set to JsonDeliveryRule.Availability                                                                                     |
--- | `C_BPartner.deliveryviarule`                     | N               | JsonRequestBPartner.deliveryViaRule                  | always set to JsonDeliveryViaRule.Shipper                                                                                       |
--- | `C_BPartner.paymentrule`                         | Y               | JsonRequestBPartner.paymentRule                      | always set to JsonPaymentRule.OnCredit                                                                                          |
--- | `C_BPartner.paymentrulepo`                       | Y               | JsonRequestBPartner.paymentRulePO                    | always set to JsonPaymentRule.OnCredit                                                             |
--- | `C_BPartner.iscustomer`                          | Y               | JsonRequestBPartner.customer                         | set to `false` if BPartnerRow.partnerCategory is equal to 4, else always set to `true` which is the default value in metasfresh |
--- | `C_BPartner.isvendor`                            | Y               | JsonRequestBPartner.vendor                           | always set to `true`                                                                                                            |
--- | `C_BPartner.isstoragewarehouse`                  | Y               | JsonRequestBPartner.storageWarehouse                 | set to `true` if BPartnerRow.partnerCategory is equal to 4, else set always to `false` which is the default value in metasfresh |
--- | `C_BPartner.bpartner_parent_id`                  | N               | JsonRequestBPartner.parentIdentifier                 | set to the associated Section Group Partner -> BPartnerRow.partnerCode(without the last 2 digits) + "00"                        |
--- | `C_BPartner.IsProspect`                          | N               | JsonRequestBPartner.prospect                         | always set to `false`                                                                         |
--- | ----                                             | N               | JsonRequestBPartnerUpsert.syncAdvise                 | default value CREATE_OR_MERGE                                                                                                   |
BPartnerRow.partnerCode + "_" + BPartnerRow.section  | `S_ExternalReference.externalreference`          | Y               | JsonRequestBPartnerUpsertItem.bpartnerIdentifier     | "ext-SAP-" + BPartnerRow.partnerCode(without the last 2 digits) + "00" + "_" + BPartnerRow.section                              |
--- | `S_ExternalReference.isreadonlyinmetasfresh`     | Y               | JsonRequestBPartnerUpsertItem.isReadOnlyInMetasfresh | always set to `true`                                                                                                            |
--- | `S_ExternalReference.externalsystem_config_id`   | N               | JsonRequestBPartnerUpsertItem.externalSystemConfigId | always set to the ID of the SAP External System Config                                                                          |

3. Section Partner - all `metasfresh-column` values refer to `C_BPartner_Location` and `C_Location` columns

    - There will be one BPartner_Location created for each bpartner row matching on PartnerCode (without last 2 digits) and section.

SAP | metasfresh-column                                | mandatory in mf | metasfresh-json                                                                                       | note                                                                                                                                                        |
---- |--------------------------------------------------|-----------------|-------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
BPartnerRow.countryKey | `C_Location.c_country_id`                        | N               | JsonRequestLocation.countryCode                                                                              |                                                                                                                                                             |
BPartnerRow.city | `C_Location.city`                                | N               | JsonRequestLocation.city                                                                              |                                                                                                                                                             |
BPartnerRow.street | `C_Location.address1`                            | N               | JsonRequestLocation.address1                                                                          |                                                                                                                                                             |
BPartnerRow.street2 | `C_Location.address2`                            | N               | JsonRequestLocation.address2                                                                          |                                                                                                                                                             |
BPartnerRow.street3 | `C_Location.address3`                            | N               | JsonRequestLocation.address3                                                                          |                                                                                                                                                             |
BPartnerRow.street4 + "," + BPartnerRow.street5 | `C_Location.address4`                            | N               | JsonRequestLocation.address4                                                                          | if the value of "BPartnerRow.street5", then it is concatenated to "BPartnerRow.street4" and set to "JsonRequestLocation.address4" (with a "," between them) |
BPartnerRow.postal | `C_Location.postal`                              | N               | JsonRequestLocation.postal                                                                            |                                                                                                                                                             |
--- | `C_BPartner_Location.ishandoverlocation`         | Y               | JsonRequestLocation.handoverLocation                                                                  | always set to `true`                                                                                                                                        |
--- | `C_BPartner_Location.isshiptodefault`            | N               | ---                                                                                                   | always set to `false`                                                                                                                                       |
--- | `C_BPartner_Location.isbilltodefault`            | N               | ---                                                                                                   | always set to `false`                                                                                                                                       |
--- | `C_BPartner_Location.isshipto`                   | Y               | ---                                                                                                   | always set to `true`                                                                                                                                        |
--- | `C_BPartner_Location.isbillto`                   | Y               | ---                                                                                                   | always set to `true`                                                                                                                                        |
--- | `C_BPartner_Location.isremitto`                  | Y               | ---                                                                                                   | always set to `false`                                                                                                                                       |
--- | `C_BPartner_Location.isreplicationlookupdefault` | Y               | ---                                                                                                   | always set to `false`                                                                                                                                       |
--- | `C_BPartner_Location.visitorsaddress`            | Y               | ---                                                                                                   | always set to `false`                                                                                                                                       |
Office Address | ?                                                | ?               | ?                                                                                                     |                                                                                                                                                             |
Warehouse Address | ?                                                | ?               | ?                                                                                                     |                                                                                                                                                             |
Production Site | ?                                                | ?               | ?                                                                                                     |                                                                                                                                                             |
BPartnerRow.vatRegNo | `C_BPartner_Location.VATaxId`   | N              | JsonRequestLocation.vatId                                                                              |                                                                                                                                                             |
BPartnerRow.paymentType | `C_BPartner_Location.SAP_PaymentMethod`              | N               | JsonRequestLocation.sapPaymentMethod                                                       |                                                                                                                |
--- | ----                                             | N               | JsonRequestBPartnerUpsert.syncAdvise                                                                  | default value CREATE_OR_MERGE                                                                                                                               |
BPartnerRow.partnerCode + "_" + BPartnerRow.section  | `S_ExternalReference.externalreference`          | Y               | JsonRequestLocationUpsertItem.locationIdentifier | "ext-SAP-" + BPartnerRow.PartnerCode + "_" + BPartnerRow.SectionCode (the PartnerCode is not truncated)                                                     |
--- | `S_ExternalReference.isreadonlyinmetasfresh`     | Y               | JsonRequestBPartnerUpsertItem.isReadOnlyInMetasfresh                                                  | always set to `true`                                                                                                                                        |
--- | `S_ExternalReference.externalsystem_config_id`   | N               | JsonRequestBPartnerUpsertItem.externalSystemConfigId                                                  | always set to the ID of the SAP External System Config                                                                                                      |

## **SAP => metasfresh bpartner_creditlimit**

* `CreditLimit` - pulled via an SFTP camel route

First, the SFTP consumer must be configured using `Externalsystem_Config_SAP_SFTP` and started by invoking the `SAP-startCreditLimitsSyncSFTP` dedicated route. In order to stop the consumer, the `SAP-stopCreditLimitsSyncSFTP` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `Externalsystem_Config_SAP_SFTP`:

| Column name                       | Accepted values | Description                                                                                                                 |
|-----------------------------------|-----------------|-----------------------------------------------------------------------------------------------------------------------------|
| Hostname                          | string          | sftp server hostname                                                                                                        |
| Port                              | number          | sftp server port                                                                                                            |
| Username                          | string          | sftp server authentication username                                                                                         |
| Password                          | string          | sftp server authentication password                                                                                         | 
| Credit-Limit Target Directory     | string          | the location used to pull cedit-limit files from the sftp server                                                            |
| Credit-Limit Filename Pattern     | string          | regex used to identify CreditLimit-containing files (useful if multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                        | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                                |
| Polling Frequency In Milliseconds | number          | the frequency used to poll files from the sftp server (in milliseconds)                                                     |

* `CreditLimit` - pulled via a local file camel route

First, the local file consumer must be configured using `Externalsystem_Config_SAP_LocalFile` and started by invoking the `SAP-startCreditLimitsSyncLocalFile` dedicated route. In order to stop the consumer, the `SAP-stopCreditLimitsSyncLocalFile` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `Externalsystem_Config_SAP_LocalFile`:

| Column name                       | Accepted values | Description                                                                                                                 |
|-----------------------------------|-----------------|-----------------------------------------------------------------------------------------------------------------------------|
| Local Root Location               | string          | the local root location (equivalent to the SFTP server root location)                                                       |
| Credit-Limit Target Directory     | string          | the location used to pull credit-limit files from the local machine                                                         |
| Credit-Limit Filename Pattern     | string          | regex used to identify CreditLimit-containing files (useful if multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                        | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                                |
| Polling Frequency In Milliseconds | number          | the frequency used to poll files from the local machine (in milliseconds)                                                   |


1. CreditLimit - all `metasfresh-column` values refer to `C_BPartner_CreditLimit` columns

| SAP                              | metasfresh-column                       | mandatory in mf   | metasfresh-json                                        | note                                                                                                                                                                                                              |
| ----                             | ------------------------                | ----------------- | --------------------------------------------------     | --------------------------------------------------------------------                                                                                                                                              |
| CreditLimitRow.CreditAccount     | `C_BPartner_ID`                         | Y                 | JsonRequestBPartnerUpsertItem.bpartnerIdentifier       | -> in format: `ext-SAP-[partnerIdentifier_sectionCode]`                                                                                                                                                           |
| CreditLimitRow.CreditControlArea | `M_SectionCode_ID`                      | N                 | JsonRequestCreditLimitUpsertItem.sectionCode           |                                                                                                                                                                                                                   |
| CreditLimitRow.EffectiveDateFrom | `DateFrom`                              | N                 | JsonRequestCreditLimitUpsertItem.dateFrom              |                                                                                                                                                                                                                   |
| CreditLimitRow.EffectiveDateTo   | `IsActive`                              | Y                 | JsonRequestCreditLimitUpsertItem.isActive              | if `CreditLimitRow.DeleteFlag` is not set, check if `currentDate` is before `CreditLimitRow.EffectiveDateTo` => `isActive` = `true`, else `isActive` = `false`                                                    |
| CreditLimitRow.CurrencyCode      | `C_Currency_ID`                         | N                 | JsonRequestCreditLimitUpsertItem.currencyCode          | used to convert the amount to ORG currency                                                                                                                                                                        |
| CreditLimitRow.CreditLine        | `Amount`                                | Y                 | JsonRequestCreditLimitUpsertItem.amount                |                                                                                                                                                                                                                   |
| ----                             | `C_CreditLimit_Type_ID`                 | Y                 | JsonRequestCreditLimitUpsertItem.type                  | always set to `Insurance`                                                                                                                                                                                         |
| ----                             | `AD_Org_ID`                             | Y                 | JsonRequestCreditLimitUpsertItem.orgCode               | always set to `ExternalSystem_Config.AD_Org_ID` from `JsonExternalSystemRequest`                                                                                                                                  |
| ----                             | ----                                    | N                 | JsonRequestCreditLimitUpsert.syncAdvise                | default value `CREATE_OR_MERGE`                                                                                                                                                                                   |
| CreditLimitRow.DeleteFlag        | `IsActive`                              | Y                 | ----                                                   | if `CreditLimitRow.DeleteFlag` = `Y` => `isActive` = `N` ELSE check `CreditLimitRow.EffectiveDateFrom` and `CreditLimitRow.EffectiveDateTo`                                                                       |
| CreditLimitRow.CreditType        | ----                                    | N                 | ----                                                   | ----                                                                                                                                                                                                              |
