DROP FUNCTION IF EXISTS de_metas_acct.report_InventoryValue(
    IN p_DateAcct       timestamp WITH TIME ZONE,
    IN p_M_Product_ID   numeric(10, 0),
    IN p_M_Warehouse_ID numeric(10, 0),
    IN p_AD_Language    character varying(6)
)
;


-- Used for Lagerwert (Excel)
CREATE OR REPLACE FUNCTION de_metas_acct.report_InventoryValue(
    IN p_DateAcct       timestamp WITH TIME ZONE,
    IN p_M_Product_ID   numeric(10, 0) = 0,
    IN p_M_Warehouse_ID numeric(10, 0) = 0,
    IN p_AD_Language    character varying(6) = 'de_DE'
)
    RETURNS TABLE
            (
                combination   character varying,
                description   character varying,
                ActivityName  character varying,
                WarehouseName character varying,
                ProductValue  character varying,
                ProductName   character varying,
                Qty           numeric,
                UOMSymbol     character varying,
                CostPrice     numeric,
                TotalAmt      numeric
            )
AS
$$
SELECT ev.value                                    AS Combination,
       ev.name                                     AS Description,
       activity.name                               AS ActivityName,
       wh.name                                     AS WarehouseName,
       -- loc.m_warehouse_id,
       p.value                                     AS ProductValue,
       COALESCE(p_trl.Name, p.Name)                AS ProductName,
       -- fa.m_product_id,
       SUM(fa.qty)                                 AS Qty,
       COALESCE(uom_trl.UOMSymbol, uom.UOMSymbol)  AS UOMSymbol,
       -- fa.c_uom_id,
       --
       -- (CASE WHEN SUM(fa.qty) != 0 THEN ROUND(SUM(fa.amtacctdr - fa.amtacctcr) / SUM(fa.qty), currency.costingprecision) END) AS CostPrice_calc,
       getCurrentCost(p_M_Product_ID => fa.M_Product_ID,
                      p_C_UOM_ID => fa.C_UOM_ID,
                      p_Date => p_DateAcct::date,
                      p_AcctSchema_ID => fa.c_acctschema_id,
                      p_M_CostElement_ID => (SELECT ce.m_costelement_id FROM m_costelement ce WHERE ce.costingmethod = acctSchema.costingmethod AND ce.isactive = 'Y'),
                      p_AD_Client_ID => fa.ad_client_id,
                      p_AD_Org_ID => fa.ad_org_id) AS CostPrice,
       SUM(fa.amtacctdr - fa.amtacctcr)            AS TotalAmt
FROM fact_acct fa
         INNER JOIN c_acctschema acctSchema ON acctSchema.c_acctschema_id = fa.c_acctschema_id
    -- INNER JOIN C_Currency currency ON acctSchema.c_currency_id = currency.c_currency_id
         INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
         LEFT OUTER JOIN m_locator loc ON loc.m_locator_id = fa.m_locator_id
         LEFT OUTER JOIN m_warehouse wh ON wh.m_warehouse_id = loc.m_warehouse_id
         LEFT OUTER JOIN c_activity activity ON (activity.c_activity_id = wh.c_activity_id)
         LEFT OUTER JOIN m_product p ON (p.m_product_id = fa.m_product_id)
         LEFT OUTER JOIN m_product_trl p_trl ON (p_trl.m_product_id = fa.m_product_id AND p_trl.ad_language = p_AD_Language)
         LEFT OUTER JOIN c_uom uom ON (uom.c_uom_id = fa.c_uom_id)
         LEFT OUTER JOIN c_uom_trl uom_trl ON (uom_trl.c_uom_id = fa.c_uom_id AND uom_trl.ad_language = p_AD_Language)
WHERE TRUE
  AND fa.c_acctschema_id = getC_AcctSchema_ID(fa.ad_client_id, fa.ad_org_id)
  AND fa.accountconceptualname = 'P_Asset_Acct'
  AND (COALESCE(p_M_Product_ID, 0) <= 0 OR fa.m_product_id = p_M_Product_ID)
  AND (COALESCE(p_M_Warehouse_ID, 0) <= 0 OR loc.M_Warehouse_ID = p_M_Warehouse_ID)
  AND fa.DateAcct <= p_DateAcct
GROUP BY ev.value, ev.name,
         wh.name, loc.m_warehouse_id, activity.Name,
         p.value, p.name, p_trl.name, fa.m_product_id,
         fa.c_uom_id, uom.UOMSymbol, uom_trl.UOMSymbol,
         fa.c_acctschema_id, fa.ad_client_id, fa.ad_org_id,
         acctSchema.costingmethod
    -- ,currency.costingprecision
    ;
$$
    LANGUAGE sql STABLE
;



--
-- Test:
/*
SELECT r.*, 
    (case when r.qty !=0 then round(r.TotalAmt/r.qty, 4) end) as costPrice_calc
FROM de_metas_acct.report_InventoryValue(
        p_DateAcct => '2024-12-11',
        p_M_Product_ID => (SELECT m_product_id FROM m_product WHERE value = '104031'),
        p_M_Warehouse_ID => 540008,
        p_ad_language => 'de_DE'
     ) r
;
*/
