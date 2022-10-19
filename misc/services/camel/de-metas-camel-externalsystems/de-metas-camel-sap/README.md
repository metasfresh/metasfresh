## Current data mapping

****

## Values pulled from SAP

* `Product` - pulled via an SFTP route

## Values computed in metasfresh

* `JsonExternalSystemRequest.parameters.SFTP_HostName`
* `JsonExternalSystemRequest.parameters.SFTP_Port`
* `JsonExternalSystemRequest.parameters.SFTP_Username`
* `JsonExternalSystemRequest.parameters.SFTP_Password`
* `JsonExternalSystemRequest.parameters.SFTP_Target_Directory`
* `JsonExternalSystemRequest.parameters.ProcessedDirectory`
* `JsonExternalSystemRequest.parameters.ErroredDirectory`
* `JsonExternalSystemRequest.parameters.PollingFrequency`

## **SAP => metasfresh product**

* `Product` - pulled via an SFTP camel route

First, the SFTP consumer must be configured using `Externalsystem_Config_SAP` and started by invoking the `SAP-startProductsSync` dedicated route.

Configs available in `Externalsystem_Config_SAP`:

| Column name         | Accepted values | Description                                                                  |
|---------------------|-----------------|------------------------------------------------------------------------------|
| Hostname            | string          | sftp server hostname                                                         |
| Port                | number          | sftp server port                                                             |
| Username            | string          | sftp server authentication username                                          |
| Password            | string          | sftp server authentication password                                          | 
| Target Directory    | string          | the location used to pull from the sftp server                               | 
| Processed Directory | string          | the location where the processed files will be moved                         | 
| Errored Directory   | string          | the location where the files will be moved in case of error while processing |
| Polling Frequency   | number          | the frequency with which the files are polled from the sftp server           |

1. Product - all `metasfresh-column` values refer to `M_Product` columns

SAP | metasfresh-column                     | mandatory in mf | metasfresh-json                                | note                                                               |
---- |---------------------------------------|-----------------|------------------------------------------------|--------------------------------------------------------------------|
ProductRow.materialCode + "_" + ProductRow.name | `value`                               | Y               | JsonRequestProduct.code                        |                                                                    |
ProductRow.uom | `c_uom_id`                            | Y               | JsonRequestProduct.uomCode                     |                                                                    |
ProductRow.name | `name`                                | Y               | JsonRequestProduct.name                        |                                                                    |
ProductRow.sectionCode | `m_sectioncode_id`                    | N               | JsonRequestProduct.sectionCode                 |                                                                    |
---- | `isstocked`                           | Y               | JsonRequestProduct.stocked                     | always set to `true`                                               |
---- | `discontinued`                        | N               | JsonRequestProduct.discontinued                | always set to `false`                                              |
ProductRow.materialGroup | `producttype`                         | Y               | JsonRequestProduct.type                        | alwasys set to JsonRequestProduct.Type.ITEM                        |
ProductRow.materialCategory | `m_product_category_id`               | Y               | JsonRequestProduct.productCategoryIdentifier   | never set, but will be STANDARD due to default value in metasfresh |
--- | ----                                  | N               | JsonRequestProductUpsert.syncAdvise            | default value CREATE_OR_MERGE                                      |
ProductRow.materialCode | `S_ExternalReference.externalReference` | Y               | JsonRequestProductUpsertItem.productIdentifier | ext-SAP-MaterialCode                                               |

