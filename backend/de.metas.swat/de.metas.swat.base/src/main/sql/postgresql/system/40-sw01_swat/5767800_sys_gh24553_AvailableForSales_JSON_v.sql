DROP VIEW IF EXISTS available_for_sales_json_v
;

CREATE OR REPLACE VIEW available_for_sales_json_v AS

SELECT COALESCE(externalReference.externalreference, '') AS "ProductExternalReference",
       product.m_product_id                              AS "Product_ID",
       availableForSales.qtyonhandstock                  AS "QtyOnHandStock",
       availableForSales.qtytobeshipped                  AS "QtyToBeShipped",
       availableForSales.storageattributeskey            AS "StorageAttributesKey",
       COALESCE(externalSystemConfig.name, '')           AS "ExternalSystem"

FROM MD_Available_For_Sales availableForSales
         LEFT JOIN m_product product ON availableForSales.m_product_id = product.m_product_id
         LEFT JOIN s_externalreference externalReference ON externalreference.record_id = product.m_product_id AND externalreference.referenced_ad_table_id = get_table_id('M_Product')
    AND externalreference.type = 'Product'
         LEFT JOIN externalsystem_config externalSystemConfig ON externalSystemConfig.name ILIKE externalreference.externalsystem
ORDER BY availableForSales.m_product_id
;
