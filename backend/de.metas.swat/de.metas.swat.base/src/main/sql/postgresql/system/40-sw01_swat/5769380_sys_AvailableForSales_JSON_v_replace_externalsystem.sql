DROP VIEW IF EXISTS available_for_sales_json_v
;

CREATE OR REPLACE VIEW available_for_sales_json_v AS

SELECT COALESCE(externalReference.externalreference, '') AS "ProductExternalReference",
       product.m_product_id                              AS "Product_ID",
       availableForSales.qtyonhandstock                  AS "QtyOnHandStock",
       availableForSales.qtytobeshipped                  AS "QtyToBeShipped",
       availableForSales.storageattributeskey            AS "StorageAttributesKey",
       COALESCE(externalreference.externalsystem, '')    AS "ExternalSystem"

FROM MD_Available_For_Sales availableForSales
         INNER JOIN m_product product ON availableForSales.m_product_id = product.m_product_id
         LEFT JOIN (SELECT e.record_id,
                           CASE WHEN COUNT(*) = 1 THEN MAX(e.externalreference) END  AS externalreference,
                           CASE WHEN COUNT(*) = 1 THEN MAX(externalsystem.value) END AS externalsystem
                    FROM s_externalreference e
                             JOIN externalsystem ON externalsystem.externalsystem_id = e.externalsystem_id
                    WHERE e.referenced_ad_table_id = get_table_id('M_Product')
                      AND e.type = 'Product'
                    GROUP BY e.record_id) externalReference ON externalReference.record_id = product.m_product_id
ORDER BY availableForSales.m_product_id
;

