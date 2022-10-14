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

## **SAP => metasfresh product**

* `Product` - pulled via an SFTP camel route

we need to invoke the sftp server configured for SAP external system (`Externalsystem_Config_SAP`)

1. Product - all `metasfresh-column` values refer to `M_Product` columns

SAP | metasfresh-column           | mandatory in mf | metasfresh-json                     | note                                      |
---- |-----------------------------|-----------------|-------------------------------------|-------------------------------------------|
ProductRow.materialCode + "_" + ProductRow.name | `value`                     | Y               | JsonRequestProduct.code             |                                           |
ProductRow.uom | `c_uom_id`                  | Y               | JsonRequestProduct.uomCode          |                                           |
ProductRow.name | `name`                      | Y               | JsonRequestProduct.name             |                                           |
ProductRow.sectionCode | `m_sectioncode_id`          | N               | JsonRequestProduct.sectionCode      |                                           |
---- | `isstocked`                 | Y               | JsonRequestProduct.stocked          |                                           |
---- | `discontinued`              | N               | JsonRequestProduct.discontinued     |                                           |
ProductRow.materialGroup | `producttype` - ?           | Y               | ?                                   | default value STANDARD                    |
ProductRow.materialCategory | `m_product_category_id` - ? | Y               | ?                                   | deault value JsonRequestProduct.Type.ITEM |
--- | ----                        | N               | JsonRequestProductUpsert.syncAdvise | default value CREATE_OR_MERGE             |
