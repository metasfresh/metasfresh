## Current data mapping

****

## Values pulled from SAP

* `Product` - pulled via an SFTP route
* `BPartner` - pulled via an SFTP route

## Values computed in metasfresh

* `JsonExternalSystemRequest.parameters.SFTP_HostName`
* `JsonExternalSystemRequest.parameters.SFTP_Port`
* `JsonExternalSystemRequest.parameters.SFTP_Username`
* `JsonExternalSystemRequest.parameters.SFTP_Password`
* `JsonExternalSystemRequest.parameters.SFTP_Product_Target_Directory`
* `JsonExternalSystemRequest.parameters.SFTP_BPartner_Target_Directory`
* `JsonExternalSystemRequest.parameters.SFTPProcessedDirectory`
* `JsonExternalSystemRequest.parameters.SFTPErroredDirectory`
* `JsonExternalSystemRequest.parameters.SFTPPollingFrequencyInMs`

## **SAP => metasfresh product**

* `Product` - pulled via an SFTP camel route

First, the SFTP consumer must be configured using `Externalsystem_Config_SAP` and started by invoking the `SAP-startProductsSync` dedicated route.

Configs available in `Externalsystem_Config_SAP`:

| Column name                       | Accepted values | Description                                                                          |
|-----------------------------------|-----------------|--------------------------------------------------------------------------------------|
| Hostname                          | string          | sftp server hostname                                                                 |
| Port                              | number          | sftp server port                                                                     |
| Username                          | string          | sftp server authentication username                                                  |
| Password                          | string          | sftp server authentication password                                                  | 
| Product Target Directory          | string          | the location used to pull products from the sftp server                              |
| Processed Directory               | string          | the location where the processed files will be moved                                 | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing         |
| Polling Frequency In Milliseconds | number          | the frequency with which the files are polled from the sftp server (in milliseconds) |

1. Product - all `metasfresh-column` values refer to `M_Product` columns

SAP | metasfresh-column                     | mandatory in mf | metasfresh-json                                | note                                                               |
---- |---------------------------------------|-----------------|------------------------------------------------|--------------------------------------------------------------------|
ProductRow.materialCode + "_" + ProductRow.name | `value`                               | Y               | JsonRequestProduct.code                        |                                                                    |
ProductRow.uom | `c_uom_id`                            | Y               | JsonRequestProduct.uomCode                     |                                                                    |
ProductRow.name | `name`                                | Y               | JsonRequestProduct.name                        |                                                                    |
ProductRow.sectionCode | `m_sectioncode_id`                    | N               | JsonRequestProduct.sectionCode                 |                                                                    |
---- | `isstocked`                           | Y               | JsonRequestProduct.stocked                     | always set to `true`                                               |
---- | `discontinued`                        | N               | JsonRequestProduct.discontinued                | always set to `false`                                              |
ProductRow.materialGroup | `producttype`                         | Y               | JsonRequestProduct.type                        | always set to JsonRequestProduct.Type.ITEM                         |
ProductRow.materialCategory | `m_product_category_id`               | Y               | JsonRequestProduct.productCategoryIdentifier   | never set, but will be STANDARD due to default value in metasfresh |
--- | ----                                  | N               | JsonRequestProductUpsert.syncAdvise            | default value CREATE_OR_MERGE                                      |
ProductRow.materialCode | `S_ExternalReference.externalReference` | Y               | JsonRequestProductUpsertItem.productIdentifier | ext-SAP-MaterialCode                                               |
--- | `S_ExternalReference.isreadonlyinmetasfresh` | Y               | JsonRequestBPartnerUpsertItem.isReadOnlyInMetasfresh                                                                        | always set to `true`                                               |
--- | `S_ExternalReference.externalsystem_config_id` | N               | JsonRequestBPartnerUpsertItem.externalSystemConfigId                                                                        | always set to the ID of the SAP External System Config             |

## **SAP => metasfresh bpartner**

* `BPartner` - pulled via an SFTP camel route

First, the SFTP consumer must be configured using `Externalsystem_Config_SAP` and started by invoking the `SAP-startBPartnerSync` dedicated route.

Configs available in `Externalsystem_Config_SAP`:

| Column name                       | Accepted values | Description                                                                  |
|-----------------------------------|-----------------|------------------------------------------------------------------------------|
| Hostname                          | string          | sftp server hostname                                                         |
| Port                              | number          | sftp server port                                                             |
| Username                          | string          | sftp server authentication username                                          |
| Password                          | string          | sftp server authentication password                                          |
| BPartner Target Directory         | string          | the location used to pull business partners from the sftp server             | 
| Processed Directory               | string          | the location where the processed files will be moved                         | 
| Errored Directory                 | string          | the location where the files will be moved in case of error while processing |
| Polling Frequency In Milliseconds | number          | the frequency with which the files are polled from the sftp server (in milliseconds)          |

1. Section Group Partner - all `metasfresh-column` values refer to `C_BPartner` columns

SAP | metasfresh-column                                | mandatory in mf | metasfresh-json                                                                                      | note                                                                                                                                                       |
---- |--------------------------------------------------|-----------------|------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|
BPartnerRow.partnerCode | `C_BPartner.value`                               | Y               | JsonRequestBPartner.code                                                                             |                                                                                                                                                            |
BPartnerRow.name1 | `C_BPartner.companyname`                         | N               | JsonRequestBPartner.companyName                                                                      |                                                                                                                                                            |
BPartnerRow.name2 | `C_BPartner.name`                                | Y               | JsonRequestBPartner.name                                                                             |                                                                                                                                                            |
BPartnerRow.searchTerm | `C_BPartner.description`                         | N               | JsonRequestBPartner.description                                                                      |                                                                                                                                                            |
--- | ----                                             | N               | JsonRequestBPartner.syncAdvise                                                                       | default value CREATE_OR_MERGE                                                                                                                              |
--- | ----                                             | N               | JsonRequestComposite.syncAdvise                                                                      | default value CREATE_OR_MERGE                                                                                                                              |
--- | ----                                             | N               | JsonRequestBPartnerUpsert.syncAdvise                                                                 | default value CREATE_OR_MERGE                                                                                                                              |
BPartnerRow.partnerCode | `S_ExternalReference.externalreference`          | Y               | JsonRequestBPartnerUpsertItem.bpartnerIdentifier | ext-SAP-PartnerCode                                                                                                                            |
--- | `S_ExternalReference.isreadonlyinmetasfresh`     | Y               | JsonRequestBPartnerUpsertItem.isReadOnlyInMetasfresh                                                 | always set to `true`                                                                                                                                       |
--- | `S_ExternalReference.externalsystem_config_id`   | N               | JsonRequestBPartnerUpsertItem.externalSystemConfigId                                                 | always set to the ID of the SAP External System Config                                                                                                     |

2. Section Partner - all `metasfresh-column` values refer to `C_BPartner` columns

SAP | metasfresh-column                                | mandatory in mf | metasfresh-json                                      | note                                                                                                                            |
---- |--------------------------------------------------|-----------------|------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
BPartnerRow.partnerCode | `C_BPartner.value`                               | Y               | JsonRequestBPartner.code                             |                                                                                                                                 |
BPartnerRow.name1 | `C_BPartner.companyname`                         | N               | JsonRequestBPartner.companyName                      |                                                                                                                                 |
BPartnerRow.name2 | `C_BPartner.name`                                | Y               | JsonRequestBPartner.name                             |                                                                                                                                 |
BPartnerRow.searchTerm | `C_BPartner.description`                         | N               | JsonRequestBPartner.description                      |                                                                                                                                 |
BPartnerRow.section | `C_BPartner.m_sectioncode_id`                    | N               | JsonRequestBPartner.sectionCode                      |                                                                                                                                 |
BPartnerRow.salesIncoterms | `C_BPartner.c_incoterms_customer_id`             | N               | JsonRequestBPartner.incotermsCustomer                |                                                                                                                                 |
BPartnerRow.purchaseIncoterms | `C_BPartner.c_incoterms_vendor_id`               | N               | JsonRequestBPartner.incotermsVendor                  |                                                                                                                                 |
BPartnerRow.salesPaymentTerms | `C_BPartner.c_paymentterm_id`                    | N               | JsonRequestBPartner.salesPaymentTermIdentifier       |                                                                                                                                 |
BPartnerRow.purchasePaymentTerms | `C_BPartner.po_paymentterm_id`                   | N               | JsonRequestBPartner.purchasePaymentTermIdentifier    |                                                                                                                                 |
--- | `C_BPartner.deliveryrule`                        | N               | JsonRequestBPartner.deliveryRule                     | always set to JsonRequestBPartner.DeliveryRule.AVAILABILITY                                                                     |
--- | `C_BPartner.deliveryviarule`                     | N               | JsonRequestBPartner.deliveryViaRule                  | always set to JsonRequestBPartner.DeliveryViaRule.SHIPPER                                                                       |
--- | `C_BPartner.paymentrule`                         | Y               | ---                                                  | never set, but will be ON CREDIT due to default value in metasfresh                                                             |
--- | `C_BPartner.paymentrulepo`                       | Y               | ---                                                  | never set, but will be ON CREDIT due to default value in metasfresh                                                             |
--- | `C_BPartner.iscustomer`                          | Y               | JsonRequestBPartner.customer                         | set to `false` if BPartnerRow.partnerCategory is equal to 4, else always set to `true` which is the default value in metasfresh |
--- | `C_BPartner.isvendor`                            | Y               | JsonRequestBPartner.vendor                           | always set to `true`                                                                                                            |
--- | `C_BPartner.isstoragewarehouse`                  | Y               | JsonRequestBPartner.storageWarehouse                 | set to `true` if BPartnerRow.partnerCategory is equal to 4, else set always to `false` which is the default value in metasfresh |
--- | `C_BPartner.bpartner_parent_id`                  | N               | JsonRequestBPartner.parentIdentifier                 | set to the associated Section Group Partner                                                                                                          |
--- | ----                                             | N               | JsonRequestBPartnerUpsert.syncAdvise                 | default value CREATE_OR_MERGE                                                                                                   |
BPartnerRow.partnerCode + "_" + BPartnerRow.section  | `S_ExternalReference.externalreference`          | Y               | JsonRequestBPartnerUpsertItem.bpartnerIdentifier     | ext-SAP-PartnerCode_SectionCode                                                                                                 |
--- | `S_ExternalReference.isreadonlyinmetasfresh`     | Y               | JsonRequestBPartnerUpsertItem.isReadOnlyInMetasfresh | always set to `true`                                                                                                            |
--- | `S_ExternalReference.externalsystem_config_id`   | N               | JsonRequestBPartnerUpsertItem.externalSystemConfigId | always set to the ID of the SAP External System Config                                                                          |

3. Section Partner - all `metasfresh-column` values refer to `C_BPartner_Location` and `C_Location` columns

SAP | metasfresh-column                                | mandatory in mf | metasfresh-json                                                                                       | note                                                                                                                                                       |
---- |--------------------------------------------------|-----------------|-------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|
BPartnerRow.city | `C_Location.city`                                | N               | JsonRequestLocation.city                                                                              |                                                                                                                                                            |
BPartnerRow.street | `C_Location.address1`                            | N               | JsonRequestLocation.address1                                                                          |                                                                                                                                                            |
BPartnerRow.street2 | `C_Location.address2`                            | N               | JsonRequestLocation.address2                                                                          |                                                                                                                                                            |
BPartnerRow.street3 | `C_Location.address3`                            | N               | JsonRequestLocation.address3                                                                          |                                                                                                                                                            |
BPartnerRow.street4 + "," + BPartnerRow.street5 | `C_Location.address4`                            | N               | JsonRequestLocation.address4                                                                          | if the value of "BPartnerRow.street5", then it is concatenated to "BPartnerRow.street4" and set to "JsonRequestLocation.address4" (with a "," between them) |
BPartnerRow.postal | `C_Location.postal`                              | N               | JsonRequestLocation.postal                                                                            |                                                                                                                                                            |
--- | `C_BPartner_Location.ishandoverlocation`         | Y               | JsonRequestLocation.handoverLocation                                                                  | always set to `true`                                                                                                                                       |
--- | `C_BPartner_Location.isshiptodefault`            | N               | ---                                                                                                   | never set, but will be `false` due to default value in metasfresh                                                                                          |
--- | `C_BPartner_Location.isbilltodefault`            | N               | ---                                                                                                   | never set, but will be `false` due to default value in metasfresh                                                                                          |
--- | `C_BPartner_Location.isshipto`                   | Y               | ---                                                                                                   | never set, but will be `true` due to default value in metasfresh                                                                                           |
--- | `C_BPartner_Location.isbillto`                   | Y               | ---                                                                                                   | never set, but will be `true` due to default value in metasfresh                                                                                           |
--- | `C_BPartner_Location.isremitto`                  | Y               | ---                                                                                                   | never set, but will be `false` due to default value in metasfresh                                                                                          |
--- | `C_BPartner_Location.isreplicationlookupdefault` | Y               | ---                                                                                                   | never set, but will be `false` due to default value in metasfresh                                                                                          |
--- | `C_BPartner_Location.visitorsaddress`            | Y               | ---                                                                                                   | never set, but will be `false` due to default value in metasfresh                                                                                          |
Office Address | ?                                                | ?               | ?                                                                                                     |                                                                                                                                                            |
Warehouse Address | ?                                                | ?               | ?                                                                                                     |                                                                                                                                                            |
Production Site | ?                                                | ?               | ?                                                                                                     |                                                                                                                                                            |
BPartnerRow.vatRegNo | ?                                                | ?               | ?                                                                                                     |                                                                                                                                                            |
--- | ----                                             | N               | JsonRequestBPartner.syncAdvise                                                                        | default value CREATE_OR_MERGE                                                                                                                              |
--- | ----                                             | N               | JsonRequestLocationUpsert.syncAdvise                                                                  | default value CREATE_OR_MERGE                                                                                                                              |
--- | ----                                             | N               | JsonRequestComposite.syncAdvise                                                                       | default value CREATE_OR_MERGE                                                                                                                              |
--- | ----                                             | N               | JsonRequestBPartnerUpsert.syncAdvise                                                                  | default value CREATE_OR_MERGE                                                                                                                              |
BPartnerRow.partnerCode + "_" + BPartnerRow.section  | `S_ExternalReference.externalreference`          | Y               | JsonRequestLocationUpsertItem.locationIdentifier | ext-SAP-PartnerCode_SectionCode                                                                                                                            |
--- | `S_ExternalReference.isreadonlyinmetasfresh`     | Y               | JsonRequestBPartnerUpsertItem.isReadOnlyInMetasfresh                                                  | always set to `true`                                                                                                                                       |
--- | `S_ExternalReference.externalsystem_config_id`   | N               | JsonRequestBPartnerUpsertItem.externalSystemConfigId                                                  | always set to the ID of the SAP External System Config                                                                                                     |
