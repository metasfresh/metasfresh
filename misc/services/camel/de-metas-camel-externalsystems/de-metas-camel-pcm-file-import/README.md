## Current data mapping

****

## Values pulled from Pro Care Management

* `Product`
* `BPartner`
* `Warehouse`
* `PurchaseCandidate`

## Values computed in metasfresh

### Local File

* `JsonExternalSystemRequest.parameters.LocalFileRootLocation`
* `JsonExternalSystemRequest.parameters.LocalFileProductFileNamePattern`
* `JsonExternalSystemRequest.parameters.LocalFilePartnerFileNamePattern`
* `JsonExternalSystemRequest.parameters.LocalFileWarehouseFileNamePattern`
* `JsonExternalSystemRequest.parameters.LocalFilePurchaseOrderFileNamePattern`
* `JsonExternalSystemRequest.parameters.LocalFileProcessedDirectory`
* `JsonExternalSystemRequest.parameters.LocalFileErroredDirectory`
* `JsonExternalSystemRequest.parameters.LocalFilePollingFrequencyInMs`
* `JsonExternalSystemRequest.parameters.TaxCategoryMappings` - a Map between `C_TaxCategory.InternalName` and the rates set in the `Tax Category Mapping` sub-tab. The tax rates have to be separated by `,`.
* `JsonExternalSystemRequest.parameters.BPartnerId` - the partner to use when upserting warehouse-locations

## **Pro Care Management => metasfresh product**

* `Product` - pulled via a local file camel route

First, the local file consumer must be configured using `ExternalSystem_Config_ProCareManagement_LocalFile` and started by invoking the `ProCareManagement-startProductSyncLocalFile` dedicated route. In order to stop the consumer, the `ProCareManagement-stopProductSyncLocalFile` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `ExternalSystem_Config_ProCareManagement_LocalFile`:

| Column name                       | Accepted values | Description                                                                                                                   |
|-----------------------------------|-----------------|-------------------------------------------------------------------------------------------------------------------------------|
| Local Root Location               | string          | the local root location (equivalent to the SFTP server root location)                                                         |
| Product Filename Pattern          | string          | ant-pattern used to identify Product-containing files (useful as multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                          | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                                  |
| Polling Frequency In Milliseconds | number          | the frequency used to poll files from the local machine (in milliseconds)                                                     |

1. Product - all `MF-column` (without a table at the beginning) values refer to `M_Product`, `C_BPartner_Product` and `M_Product_TaxCategory` columns

| MF CSV-Column name (ProductRow)  | MF-column                                                                  | mandatory in MF | MF-json                                             | note                                                                                                                                                                                                        |
|----------------------------------|----------------------------------------------------------------------------|-----------------|-----------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| bpartnerIdentifier               | `S_ExternalReference.externalreference`                                    | Y               | JsonRequestBPartnerProductUpsert.bpartnerIdentifier | Identify the BPartner for which the `C_BPartner_Product` will be created, "ext-ProCareManagement-" + ProductRow.bpartnerIdentifier                                                                          |
| productIdentifier                | `S_ExternalReference.externalreference`                                    | Y               | JsonRequestProductUpsertItem.productIdentifier      | "ext-ProCareManagement-" + ProductRow.productIdentifier                                                                                                                                                     |
| ---                              | `S_ExternalReference.ExternalSystem_Config_ID`                             | N               | JsonRequestProductUpsertItem.externalSystemConfigId | always set to the ID of the ProCareManagement External System Config                                                                                                                                        |
| name                             | `M_Product.Name`                                                           | Y               | JsonRequestProduct.name                             |                                                                                                                                                                                                             |
| value + "_" + bpartnerIdentifier | `M_Product.Value`                                                          | Y               | JsonRequestProduct.code                             |                                                                                                                                                                                                             |
| description                      | `M_Product.Description`                                                    | N               | JsonRequestProduct.description                      |                                                                                                                                                                                                             |
| ----                             | `M_Product.C_UOM_ID`                                                       | Y               | JsonRequestProduct.uomCode                          | always set to `PCE`                                                                                                                                                                                         |
| ----                             | `M_Product.ProductType`                                                    | Y               | JsonRequestProduct.type                             | always set to `JsonRequestProduct.Type.ITEM`                                                                                                                                                                |
| bpartnerIdentifier               | `S_ExternalReference.externalreference`/`C_BPartner_Product.C_BPartner_ID` | Y               | JsonRequestBPartnerProductUpsert.bpartnerIdentifier | Identify the BPartner for which the `C_BPartner_Product` will be created, "ext-ProCareManagement-" + ProductRow.bpartnerIdentifier                                                                          |
| value                            | `C_BPartner_Product.productNo`                                             | N               | JsonRequestBPartnerProductUpsert.productNo          |                                                                                                                                                                                                             |
| description                      | `C_BPartner_Product.Description`                                           | N               | JsonRequestBPartnerProductUpsert.description        |                                                                                                                                                                                                             |
| ean                              | `C_BPartner_Product.EAN_CU`                                                | N               | JsonRequestBPartnerProductUpsert.cuEAN              |                                                                                                                                                                                                             |
| ----                             | `C_BPartner_Product.isCurrentVendor`                                       | Y               | JsonRequestBPartnerProductUpsert.currentVendor      | always set to true                                                                                                                                                                                          |
| taxRate                          | `M_Product_TaxCategory.C_TaxCategory_ID`                                   | Y               | JsonRequestProductTaxCategoryUpsert.taxCategory     | Identify the `C_TaxCategory` based on the `TaxCategoryMappings` and `ProductRow.taxRate`. It will match the `C_TaxCategory` set in the `TaxCategoryMappings` for the rate specified in `ProductRow.taxRate` |
| ----                             | `M_Product_TaxCategory.C_Country_ID`                                       | N               | JsonRequestProductTaxCategoryUpsert.countryCode     | always set to `DE`, identifies `C_Country` with `CountryCode` = `DE`                                                                                                                                        |
| ----                             | `M_Product_TaxCategory.ValidFrom`                                          | Y               | JsonRequestProductTaxCategoryUpsert.validFrom       | always set to `import date - 365 DAYS`                                                                                                                                                                      |
| ----                             | `M_Product_TaxCategory.IsActive`                                           | Y               | JsonRequestProductTaxCategoryUpsert.active          | always set to true                                                                                                                                                                                          |

## **Pro Care Management => metasfresh bpartner**

* `BPartner` - pulled via a local file camel route

First, the local file consumer must be configured using `ExternalSystem_Config_ProCareManagement_LocalFile` and started by invoking the `ProCareManagement-startBPartnerSyncLocalFile` dedicated route. In order to stop the consumer, the `ProCareManagement-stopBPartnerSyncLocalFile` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `ExternalSystem_Config_ProCareManagement_LocalFile`:

| Column name                       | Accepted values | Description                                                                                                                    |
|-----------------------------------|-----------------|--------------------------------------------------------------------------------------------------------------------------------|
| Local Root Location               | string          | the local root location (equivalent to the SFTP server root location)                                                          |
| Business Partner Filename Pattern | string          | ant-pattern used to identify BPartner-containing files (useful as multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                           | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                                   |
| Polling Frequency In Milliseconds | number          | the frequency used to poll files from the local machine (in milliseconds)                                                      |

1. BPartner - all `metasfresh-column` values refer to `C_BPartner` columns

| MF CSV-Column name (BPartnerRow) | MF-column                               | mandatory in MF | MF-json                                          | note                                                      |
|----------------------------------|-----------------------------------------|-----------------|--------------------------------------------------|-----------------------------------------------------------|
| value                            | `C_BPartner.value`                      | Y               | JsonRequestBPartner.code                         |                                                           |
| name                             | `C_BPartner.Name`                       | Y               | JsonRequestBPartner.name                         |                                                           |
| name                             | `C_BPartner.CompanyName`                | N               | JsonRequestBPartner.companyName                  |                                                           |
| bpartnerIdentifier               | `S_ExternalReference.externalreference` | Y               | JsonRequestBPartnerUpsertItem.bpartnerIdentifier | "ext-ProCareManagement-" + BPartnerRow.bpartnerIdentifier |

2. BPartner_Location - all `metasfresh-column` values refer to `C_BPartner_Location` and `C_Location` columns

    - There will be one BPartner_Location created for each bpartner row.

| MF CSV-Column name (BPartnerRow) | MF-column                               | mandatory in MF | MF-json                                          | note                                                      |
|----------------------------------|-----------------------------------------|-----------------|--------------------------------------------------|-----------------------------------------------------------|
| ---                              | `C_Location.c_country_id`               | Y               | JsonRequestLocation.countryCode                  | always set to `DE`                                        |
| city                             | `C_Location.city`                       | N               | JsonRequestLocation.city                         |                                                           |
| address1                         | `C_Location.address1`                   | N               | JsonRequestLocation.address1                     |                                                           |
| postalCode                       | `C_Location.postal`                     | N               | JsonRequestLocation.postal                       |                                                           |
| ---                              | `C_BPartner_Location.isshiptodefault`   | N               | ---                                              | always set to `true`                                      |
| ---                              | `C_BPartner_Location.isbilltodefault`   | N               | ---                                              | always set to `true`                                      |
| bpartnerIdentifier               | `S_ExternalReference.externalreference` | Y               | JsonRequestLocationUpsertItem.bpartnerIdentifier | "ext-ProCareManagement-" + BPartnerRow.bpartnerIdentifier |

## **Pro Care Management => metasfresh warehouse**

* `Warehouse` - pulled via a local file camel route

First, the local file consumer must be configured using `ExternalSystem_Config_ProCareManagement_LocalFile` and started by invoking the `ProCareManagement-startWarehouseSyncLocalFile` dedicated route. In order to stop the consumer, the `ProCareManagement-stopWarehouseSyncLocalFile` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `ExternalSystem_Config_ProCareManagement_LocalFile`:

| Column name                       | Accepted values | Description                                                                                                                     |
|-----------------------------------|-----------------|---------------------------------------------------------------------------------------------------------------------------------|
| Local Root Location               | string          | the local root location (equivalent to the SFTP server root location)                                                           |
| Warehouse Filename Pattern        | string          | ant-pattern used to identify Warehouse-containing files (useful as multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                            | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                                    |
| Polling Frequency In Milliseconds | number          | the frequency used to poll files from the local machine (in milliseconds)                                                       |

1. BPartner - all `metasfresh-column` values refer to `C_BPartner`, `C_BPartner_Location` and `C_Location` columns

| MF CSV-Column name (WarehouseRow) | MF-column                               | mandatory in MF | MF-json                                           | note                                                        |
|-----------------------------------|-----------------------------------------|-----------------|---------------------------------------------------|-------------------------------------------------------------|
| ---                               | `C_BPartner.C_BPartner_ID`              | Y               | JsonRequestBPartnerUpsertItem.bpartnerIdentifier  | `JsonExternalSystemRequest.parameters.BPartnerId`           |
| name                              | `C_BPartner_Location.Name`              | Y               | JsonRequestLocation.name                          |                                                             |
| address1                          | `C_Location.address1`                   | N               | JsonRequestLocation.address1                      |                                                             |
| postal                            | `C_Location.postal`                     | N               | JsonRequestLocation.postal                        |                                                             |
| city                              | `C_Location.city`                       | N               | JsonRequestLocation.city                          |                                                             |
| gln                               | `C_BPartner_Location.GLN`               | N               | JsonRequestLocation.gln                           |                                                             |
| ---                               | `C_Location.c_country_id`               | Y               | JsonRequestLocation.countryCode                   | always set to `DE`                                          |
| warehouseIdentifier               | `S_ExternalReference.externalreference` | Y               | JsonRequestLocationUpsertItem.warehouseIdentifier | "ext-ProCareManagement-" + WarehouseRow.warehouseIdentifier |

2. Warehouse - all `metasfresh-column` values refer to `M_Warehouse` columns

| MF CSV-Column name (WarehouseRow) | MF-column                               | mandatory in MF | MF-json                                            | note                                                        |
|-----------------------------------|-----------------------------------------|-----------------|----------------------------------------------------|-------------------------------------------------------------|
| warehouseIdentifier               | `S_ExternalReference.externalreference` | Y               | JsonRequestWarehouseUpsertItem.warehouseIdentifier | "ext-ProCareManagement-" + WarehouseRow.warehouseIdentifier |
| warehouseValue                    | `M_Warehouse.Value`                     | Y               | JsonRequestWarehouse.code                          |                                                             |
| name                              | `M_Warehouse.Name`                      | Y               | JsonRequestWarehouse.name                          |                                                             |
| warehouseIdentifier               | `S_ExternalReference.externalreference` | Y               | JsonRequestWarehouse.bpartnerLocationIdentifier    | "ext-ProCareManagement-" + WarehouseRow.warehouseIdentifier |
| ---                               | `M_Warehouse.isActive`                  | Y               | JsonRequestWarehouse.active                        | always set to true                                          |

## **Pro Care Management => metasfresh purchase candidates**

* `Purchase Candidate` - pulled via a local file camel route

First, the local file consumer must be configured using `ExternalSystem_Config_ProCareManagement_LocalFile` and started by invoking the `ProCareManagement-startPurchaseOrderSyncLocalFile` dedicated route. In order to stop the consumer, the `ProCareManagement-stopPurchaseOrderSyncLocalFile` route must be invoked.

- 💡 In order to update the consumer configuration, you have to first stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)
- 💡 Purchase-Candidate files are only processed if there are no masterdata files in the root folder.
  In other words: if the root folder contains files with match any of `ProductFileNamePattern`, `PartnerFileNamePattern` or `WarehouseFileNamePattern`, then the files matched by `PurchaseOrderFileNamePattern` are ignored.
- 💡 After a CSV-file is processed, the created purchase candidates are processed, i.e. purchase orders are created, **unless**
    - if a CSV-row could not be processed into a purchase order candidate, then the rows with the same `externalHeaderId` are **not** processed. If the `externalHeaderId` can'T be parsed from the CSV-file, then none of the purchase candidate is processed.

Configs available in `ExternalSystem_Config_ProCareManagement_LocalFile`:

| Column name                       | Accepted values | Description                                                                                                                              |
|-----------------------------------|-----------------|------------------------------------------------------------------------------------------------------------------------------------------|
| Local Root Location               | string          | the local root location (equivalent to the SFTP server root location)                                                                    |
| Purchase Order Filename Pattern   | string          | ant-pattern used to identify Purchase Candidate-containing files (useful as multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                                     | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                                             |
| Polling Frequency In Milliseconds | number          | the frequency used to poll files from the local machine (in milliseconds)                                                                |

1. Purchase Candidate - all `metasfresh-column` values refer to `C_PurchaseCandidate`

| MF CSV-Column name (PurchaseOrderRow) | MF-column                                                                             | mandatory in MF | MF-json                                                   | note                                                                    |
|---------------------------------------|---------------------------------------------------------------------------------------|-----------------|-----------------------------------------------------------|-------------------------------------------------------------------------|
| productIdentifier                     | `S_ExternalReference.externalreference`                                               | Y               | JsonPurchaseCandidateCreateItem.productIdentifier         | "ext-ProCareManagement-" + PurchaseOrderRow.productIdentifier           |
| warehouseIdentifier                   | `S_ExternalReference.externalreference`                                               | Y               | JsonPurchaseCandidateCreateItem.warehouseIdentifier       | "ext-ProCareManagement-" + PurchaseOrderRow.warehouseIdentifier         |
| bpartnerIdentifier                    | `S_ExternalReference.externalreference`                                               | Y               | JsonPurchaseCandidateCreateItem.vendor.bpartnerIdentifier | "ext-ProCareManagement-" + PurchaseOrderRow.bpartnerIdentifier          |
| externalHeaderId                      | `C_PurchaseCandidate.externalHeaderId`                                                | N               | JsonPurchaseCandidateCreateItem.externalHeaderId          |                                                                         |
| externalLineId                        | `C_PurchaseCandidate.externalLineId`                                                  | N               | JsonPurchaseCandidateCreateItem.externalLineId            |                                                                         |
| poReference                           | `C_PurchaseCandidate.poReference`                                                     | N               | JsonPurchaseCandidateCreateItem.poReference               |                                                                         |
| qty                                   | `C_PurchaseCandidate.QtyToPurchase`/`C_PurchaseCandidate.C_UOM_ID`                    | Y               | JsonPurchaseCandidateCreateItem.qty                       | `C_UOM_ID` always set to `PCE`                                          |
| ---                                   | `C_PurchaseCandidate.IsManualPrice`                                                   | Y               | JsonPurchaseCandidateCreateItem.isManualPrice             | always set to `true`                                                    |
| price                                 | `C_PurchaseCandidate.PriceEntered`/`C_PurchaseCandidate.Price_UOM_ID`/`C_Currency_ID` | N               | JsonPurchaseCandidateCreateItem.price                     | `Price_UOM_ID` always set to `PCE`/`C_Currency_ID` alwayse set to `EUR` |
| dateOrdered                           | `C_PurchaseCandidate.PurchaseDateOrdered`                                             | Y               | JsonPurchaseCandidateCreateItem.purchaseDateOrdered       |                                                                         |
| datePromised                          | `C_PurchaseCandidate.PurchaseDatePromised`                                            | Y               | JsonPurchaseCandidateCreateItem.purchaseDatePromised      |                                                                         |
