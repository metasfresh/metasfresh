DROP VIEW IF EXISTS purchase_prices_in_stock_uom_plv_v
;

CREATE VIEW purchase_prices_in_stock_uom_plv_v AS
WITH cte (c_currency_id) AS (
    (SELECT c_currency_id
     FROM c_currency
     WHERE iso_code = (SELECT value
                       FROM ad_sysconfig
                       WHERE name = 'de.metas.ui.web.material.cockpit.field.HighestPurchasePrice_AtDate.CurrencyCode')))
SELECT pl.m_pricelist_id,
       cte.c_currency_id,
       plv.m_pricelist_version_id,
       plv.validfrom,
       COALESCE(plv_next.validfrom, '9999-12-31') AS validto,
       pp.m_product_id,
       pp.m_productprice_id,
       COALESCE(
               currencyconvert(
                       priceuomconvert(
                               pp.m_product_id,
                               pp.pricestd,
                               pp.c_uom_id,
                               (SELECT p.c_uom_id FROM m_product p WHERE pp.m_product_id = p.m_product_id)
                           ),
                       pl.c_currency_id,
                       cte.c_currency_id,
                       NOW(),
                       0,
                       pp.ad_client_id,
                       pp.ad_org_id
                   ),
               0
           ) AS ProductPriceInStockUOM,
       (SELECT p.c_uom_id FROM m_product p WHERE pp.m_product_id = p.m_product_id) AS C_UOM_ID
FROM m_productprice pp
         JOIN m_pricelist_version plv ON plv.m_pricelist_version_id = pp.m_pricelist_version_id
         JOIN m_pricelist pl ON plv.m_pricelist_id = pl.m_pricelist_id AND pl.issopricelist = 'N'
         LEFT JOIN m_pricelist_version plv_next ON pl.m_pricelist_id = plv_next.m_pricelist_id AND plv_next.validfrom > plv.validfrom
         LEFT JOIN m_pricelist_version plv_between ON pl.m_pricelist_id = plv_between.m_pricelist_id AND plv_between.validfrom > plv.validfrom AND plv_between.validfrom < plv_next.validfrom
         LEFT JOIN cte ON TRUE
WHERE plv_between.m_pricelist_version_id IS NULL
;