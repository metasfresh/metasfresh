DROP VIEW IF EXISTS purchase_order_highestprice_per_day_v
;

CREATE OR REPLACE VIEW purchase_order_highestprice_per_day_v AS
WITH target_currency (c_currency_id) AS (
    (SELECT c_currency_id
     FROM c_currency
     WHERE iso_code = get_sysconfig_value('de.metas.ui.web.material.cockpit.field.HighestPurchasePrice_AtDate.CurrencyCode', 'CHF')))
SELECT ol.m_product_id,
       TRUNC(o.dateordered)::date AS date,
       MAX(
               COALESCE(
                       currencyconvert(
                               priceuomconvert(
                                       ol.m_product_id,
                                       ol.priceactual,
                                       ol.price_uom_id,
                                       p.c_uom_id
                               ),
                               o.c_currency_id,
                               target_currency.c_currency_id,
                               TRUNC(o.dateordered),
                               C_ConversionType_ID,
                               o.ad_client_id,
                               o.ad_org_id
                       ),
                       0
               )
       )                          AS max_price,
       -- array_agg(distinct ol.priceactual) as prices,
       -- count(1) as cnt,
       p.c_uom_id,
       target_currency.c_currency_id
FROM c_order o
         INNER JOIN c_orderline ol ON ol.c_order_id = o.c_order_id
         INNER JOIN m_product p ON p.m_product_id = ol.m_product_id
   , target_currency
WHERE TRUE
  AND o.issotrx = 'N'
  AND o.docstatus IN ('CO', 'CL')
GROUP BY ol.m_product_id, TRUNC(o.dateordered), p.c_uom_id, target_currency.c_currency_id
ORDER BY ol.m_product_id, TRUNC(o.dateordered)
;
