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
* `JsonExternalSystemRequest.parameters.BPartnerId`

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

1. Product - all `metasfresh-column` (without a table at the beginning) values refer to `M_Product`, `C_BPartner_Product` and `M_Product_TaxCategory` columns

| Pro Care Management           | metasfresh-column                                                          | mandatory in mf | metasfresh-json                                     | note                                                                                                                                                                                                        |
|-------------------------------|----------------------------------------------------------------------------|-----------------|-----------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ProductRow.bpartnerIdentifier | `S_ExternalReference.externalreference`                                    | Y               | JsonRequestBPartnerProductUpsert.bpartnerIdentifier | Identify the BPartner for which the `C_BPartner_Product` will be created, "ext-ProCareManagement-" + ProductRow.bpartnerIdentifier                                                                          |
| ProductRow.productIdentifier  | `S_ExternalReference.externalreference`                                    | Y               | JsonRequestProductUpsertItem.productIdentifier      | "ext-ProCareManagement-" + ProductRow.productIdentifier                                                                                                                                                     |
| ---                           | `S_ExternalReference.ExternalSystem_Config_ID`                             | N               | JsonRequestProductUpsertItem.externalSystemConfigId | always set to the ID of the ProCareManagement External System Config                                                                                                                                        |
| ProductRow.name               | `M_Product.Name`                                                           | Y               | JsonRequestProduct.name                             |                                                                                                                                                                                                             |
| ProductRow.value              | `M_Product.Value`                                                          | Y               | JsonRequestProduct.code                             |                                                                                                                                                                                                             |
| ProductRow.description        | `M_Product.Description`                                                    | N               | JsonRequestProduct.description                      |                                                                                                                                                                                                             |
| ----                          | `M_Product.C_UOM_ID`                                                       | Y               | JsonRequestProduct.uomCode                          | always set to `PCE`                                                                                                                                                                                         |
| ----                          | `M_Product.ProductType`                                                    | Y               | JsonRequestProduct.type                             | always set to `JsonRequestProduct.Type.ITEM`                                                                                                                                                                |
| ProductRow.bpartnerIdentifier | `S_ExternalReference.externalreference`/`C_BPartner_Product.C_BPartner_ID` | Y               | JsonRequestBPartnerProductUpsert.bpartnerIdentifier | Identify the BPartner for which the `C_BPartner_Product` will be created, "ext-ProCareManagement-" + ProductRow.bpartnerIdentifier                                                                          |
| ProductRow.value              | `C_BPartner_Product.productNo`                                             | N               | JsonRequestBPartnerProductUpsert.productNo          |                                                                                                                                                                                                             |
| ProductRow.description        | `C_BPartner_Product.Description`                                           | N               | JsonRequestBPartnerProductUpsert.description        |                                                                                                                                                                                                             |
| ProductRow.ean                | `C_BPartner_Product.EAN_CU`                                                | N               | JsonRequestBPartnerProductUpsert.cuEAN              |                                                                                                                                                                                                             |
| ----                          | `C_BPartner_Product.isCurrentVendor`                                       | Y               | JsonRequestBPartnerProductUpsert.currentVendor      | always set to true                                                                                                                                                                                          |
| ProductRow.taxRate            | `M_Product_TaxCategory.C_TaxCategory_ID`                                   | Y               | JsonRequestProductTaxCategoryUpsert.taxCategory     | Identify the `C_TaxCategory` based on the `TaxCategoryMappings` and `ProductRow.taxRate`. It will match the `C_TaxCategory` set in the `TaxCategoryMappings` for the rate specified in `ProductRow.taxRate` |
| ----                          | `M_Product_TaxCategory.C_Country_ID`                                       | N               | JsonRequestProductTaxCategoryUpsert.countryCode     | always set to `DE`, identifies `C_Country` with `CountryCode` = `DE`                                                                                                                                        |
| ----                          | `M_Product_TaxCategory.ValidFrom`                                          | Y               | JsonRequestProductTaxCategoryUpsert.validFrom       | always set to `import date - 365 DAYS`                                                                                                                                                                      |
| ----                          | `M_Product_TaxCategory.IsActive`                                           | Y               | JsonRequestProductTaxCategoryUpsert.active          | always set to true                                                                                                                                                                                          |

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

| Pro Care Management            | metasfresh-column                              | mandatory in mf | metasfresh-json                                      | note                                                                 |
|--------------------------------|------------------------------------------------|-----------------|------------------------------------------------------|----------------------------------------------------------------------|
| BPartnerRow.value              | `C_BPartner.value`                             | Y               | JsonRequestBPartner.code                             |                                                                      |
| BPartnerRow.name               | `C_BPartner.Name`                              | Y               | JsonRequestBPartner.name                             |                                                                      |
| BPartnerRow.name               | `C_BPartner.CompanyName`                       | N               | JsonRequestBPartner.companyName                      |                                                                      |
| BPartnerRow.bpartnerIdentifier | `S_ExternalReference.externalreference`        | Y               | JsonRequestBPartnerUpsertItem.bpartnerIdentifier     | "ext-ProCareManagement-" + BPartnerRow.bpartnerIdentifier            |

2. BPartner_Location - all `metasfresh-column` values refer to `C_BPartner_Location` and `C_Location` columns

    - There will be one BPartner_Location created for each bpartner row.

| Pro Care Management            | metasfresh-column                       | mandatory in mf | metasfresh-json                                  | note                                                      |
|--------------------------------|-----------------------------------------|-----------------|--------------------------------------------------|-----------------------------------------------------------|
| ---                            | `C_Location.c_country_id`               | Y               | JsonRequestLocation.countryCode                  | always set to `DE`                                        |
| BPartnerRow.city               | `C_Location.city`                       | N               | JsonRequestLocation.city                         |                                                           |
| BPartnerRow.address1           | `C_Location.address1`                   | N               | JsonRequestLocation.address1                     |                                                           |
| BPartnerRow.postalCode         | `C_Location.postal`                     | N               | JsonRequestLocation.postal                       |                                                           |
| ---                            | `C_BPartner_Location.isshiptodefault`   | N               | ---                                              | always set to `true`                                      |
| ---                            | `C_BPartner_Location.isbilltodefault`   | N               | ---                                              | always set to `true`                                      |
| BPartnerRow.bpartnerIdentifier | `S_ExternalReference.externalreference` | Y               | JsonRequestLocationUpsertItem.bpartnerIdentifier | "ext-ProCareManagement-" + BPartnerRow.bpartnerIdentifier |

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

| Pro Care Management              | metasfresh-column                       | mandatory in mf | metasfresh-json                                   | note                                                        |
|----------------------------------|-----------------------------------------|-----------------|---------------------------------------------------|-------------------------------------------------------------|
| ---                              | `C_BPartner.C_BPartner_ID`              | Y               | JsonRequestBPartnerUpsertItem.bpartnerIdentifier  | `JsonExternalSystemRequest.parameters.BPartnerId`           |
| WarehouseRow.name                | `C_BPartner_Location.Name`              | Y               | JsonRequestLocation.name                          |                                                             |
| WarehouseRow.address1            | `C_Location.address1`                   | N               | JsonRequestLocation.address1                      |                                                             |
| WarehouseRow.postal              | `C_Location.postal`                     | N               | JsonRequestLocation.postal                        |                                                             |
| WarehouseRow.city                | `C_Location.city`                       | N               | JsonRequestLocation.city                          |                                                             |
| WarehouseRow.gln                 | `C_BPartner_Location.GLN`               | N               | JsonRequestLocation.gln                           |                                                             |
| ---                              | `C_Location.c_country_id`               | Y               | JsonRequestLocation.countryCode                   | always set to `DE`                                          |
| WarehouseRow.warehouseIdentifier | `S_ExternalReference.externalreference` | Y               | JsonRequestLocationUpsertItem.warehouseIdentifier | "ext-ProCareManagement-" + WarehouseRow.warehouseIdentifier |

2. Warehouse - all `metasfresh-column` values refer to `M_Warehouse` columns

| Pro Care Management              | metasfresh-column                       | mandatory in mf | metasfresh-json                                    | note                                                        |
|----------------------------------|-----------------------------------------|-----------------|----------------------------------------------------|-------------------------------------------------------------|
| WarehouseRow.warehouseIdentifier | `S_ExternalReference.externalreference` | Y               | JsonRequestWarehouseUpsertItem.warehouseIdentifier | "ext-ProCareManagement-" + WarehouseRow.warehouseIdentifier |
| WarehouseRow.warehouseValue      | `M_Warehouse.Value`                     | Y               | JsonRequestWarehouse.code                          |                                                             |
| WarehouseRow.name                | `M_Warehouse.Name`                      | Y               | JsonRequestWarehouse.name                          |                                                             |
| WarehouseRow.warehouseIdentifier | `S_ExternalReference.externalreference` | Y               | JsonRequestWarehouse.bpartnerLocationIdentifier    | "ext-ProCareManagement-" + WarehouseRow.warehouseIdentifier |
| ---                              | `M_Warehouse.isActive`                  | Y               | JsonRequestWarehouse.active                        | always set to true                                          |

## **Pro Care Management => metasfresh purchase candidates**

* `Purchase Candidate` - pulled via a local file camel route

First, the local file consumer must be configured using `ExternalSystem_Config_ProCareManagement_LocalFile` and started by invoking the `ProCareManagement-startPurchaseOrderSyncLocalFile` dedicated route. In order to stop the consumer, the `ProCareManagement-stopPurchaseOrderSyncLocalFile` route must be invoked.

In order to update the consumer configuration, you have to firstly stop the consumer, then reconfigure it and then restart the consumer. (You can't reconfigure the consumer while it's running.)

Configs available in `ExternalSystem_Config_ProCareManagement_LocalFile`:

| Column name                       | Accepted values | Description                                                                                                                              |
|-----------------------------------|-----------------|------------------------------------------------------------------------------------------------------------------------------------------|
| Local Root Location               | string          | the local root location (equivalent to the SFTP server root location)                                                                    |
| Purchase Order Filename Pattern   | string          | ant-pattern used to identify Purchase Candidate-containing files (useful as multiple type of files are placed in the same source folder) |
| Processed Directory               | string          | the location where the processed files will be moved                                                                                     | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing                                                             |
| Polling Frequency In Milliseconds | number          | the frequency used to poll files from the local machine (in milliseconds)                                                                |

1. Purchase Candidate - all `metasfresh-column` values refer to `C_PurchaseCandidate`

| Pro Care Management                  | metasfresh-column                                                                     | mandatory in mf | metasfresh-json                                           | note                                                                    |
|--------------------------------------|---------------------------------------------------------------------------------------|-----------------|-----------------------------------------------------------|-------------------------------------------------------------------------|
| PurchaseOrderRow.productIdentifier   | `S_ExternalReference.externalreference`                                               | Y               | JsonPurchaseCandidateCreateItem.productIdentifier         | "ext-ProCareManagement-" + PurchaseOrderRow.productIdentifier           |
| PurchaseOrderRow.warehouseIdentifier | `S_ExternalReference.externalreference`                                               | Y               | JsonPurchaseCandidateCreateItem.warehouseIdentifier       | "ext-ProCareManagement-" + PurchaseOrderRow.warehouseIdentifier         |
| PurchaseOrderRow.bpartnerIdentifier  | `S_ExternalReference.externalreference`                                               | Y               | JsonPurchaseCandidateCreateItem.vendor.bpartnerIdentifier | "ext-ProCareManagement-" + PurchaseOrderRow.bpartnerIdentifier          |
| PurchaseOrderRow.externalHeaderId    | `C_PurchaseCandidate.externalHeaderId`                                                | N               | JsonPurchaseCandidateCreateItem.externalHeaderId          |                                                                         |
| PurchaseOrderRow.externalLineId      | `C_PurchaseCandidate.externalLineId`                                                  | N               | JsonPurchaseCandidateCreateItem.externalLineId            |                                                                         |
| PurchaseOrderRow.poReference         | `C_PurchaseCandidate.poReference`                                                     | N               | JsonPurchaseCandidateCreateItem.poReference               |                                                                         |
| PurchaseOrderRow.qty                 | `C_PurchaseCandidate.QtyToPurchase`/`C_PurchaseCandidate.C_UOM_ID`                    | Y               | JsonPurchaseCandidateCreateItem.qty                       | `C_UOM_ID` always set to `PCE`                                          |
| ---                                  | `C_PurchaseCandidate.IsManualPrice`                                                   | Y               | JsonPurchaseCandidateCreateItem.isManualPrice             | always set to `true`                                                    |
| PurchaseOrderRow.price               | `C_PurchaseCandidate.PriceEntered`/`C_PurchaseCandidate.Price_UOM_ID`/`C_Currency_ID` | N               | JsonPurchaseCandidateCreateItem.price                     | `Price_UOM_ID` always set to `PCE`/`C_Currency_ID` alwayse set to `EUR` |
| PurchaseOrderRow.dateOrdered         | `C_PurchaseCandidate.PurchaseDateOrdered`                                             | Y               | JsonPurchaseCandidateCreateItem.purchaseDateOrdered       |                                                                         |
| PurchaseOrderRow.datePromised        | `C_PurchaseCandidate.PurchaseDatePromised`                                            | Y               | JsonPurchaseCandidateCreateItem.purchaseDatePromised      |                                                                         |
