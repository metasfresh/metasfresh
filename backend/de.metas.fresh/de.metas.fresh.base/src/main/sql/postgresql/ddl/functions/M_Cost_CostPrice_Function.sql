DROP FUNCTION IF EXISTS report.M_Cost_CostPrice_Function(
    IN p_DateAcct       timestamp WITH TIME ZONE,
    IN p_M_Product_ID   numeric(10, 0),
    IN p_M_Warehouse_ID numeric(10, 0),
    IN p_ad_language    character varying(6)
)
;


-- Used for Lagerwert (Excel)
CREATE OR REPLACE FUNCTION report.M_Cost_CostPrice_Function(
    IN p_DateAcct       timestamp WITH TIME ZONE,
    IN p_M_Product_ID   numeric(10, 0),
    IN p_M_Warehouse_ID numeric(10, 0),
    IN p_ad_language    character varying(6)
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
SELECT vc.combination                                     AS combination,
       vc.description                                     AS description,
       activity.name                                      AS ActivityName,
       wh.name                                            AS WarehouseName,
       p.value                                            AS ProductValue,
       COALESCE(p_trl.Name, p.Name)                       AS ProductName,
       SUM(cd.qty)                                        AS Qty,
       COALESCE(uom_trl.UOMSymbol, uom.UOMSymbol)         AS UOMSymbol,
       -- (CASE WHEN SUM(cd.qty) != 0 THEN ROUND(SUM(cd.amt) / SUM(cd.qty), currency.costingprecision) END) AS CostPrice,
       COALESCE(getCurrentCost(cd.M_Product_ID,
                               cd.C_UOM_ID,
                               '2024-12-11'::date,
                               cd.c_acctschema_id,
                               cd.m_costelement_id,
                               wh.ad_client_id,
                               wh.ad_org_id), 0::numeric) AS CostPrice,
       SUM(cd.amt)                                        AS TotalAmt
FROM m_costdetail_v cd
         INNER JOIN m_warehouse wh ON (wh.m_warehouse_id = cd.m_warehouse_id)
         INNER JOIN ad_clientinfo clientInfo ON clientInfo.ad_client_id = wh.ad_client_id
         INNER JOIN c_acctschema acctSchema ON clientInfo.c_acctschema1_id = acctSchema.c_acctschema_id
    -- INNER JOIN C_Currency currency ON acctSchema.c_currency_id = currency.c_currency_id
    --
         LEFT OUTER JOIN m_product_acct pa ON (pa.m_product_id = cd.m_product_id AND pa.c_acctschema_id = acctSchema.c_acctschema_id)
         LEFT OUTER JOIN c_validcombination vc ON (vc.c_validcombination_id = pa.p_asset_acct)
         LEFT OUTER JOIN c_activity activity ON (activity.c_activity_id = wh.c_activity_id)
         LEFT OUTER JOIN m_product p ON (p.m_product_id = cd.m_product_id)
         LEFT OUTER JOIN m_product_trl p_trl ON (p_trl.m_product_id = cd.m_product_id AND p_trl.ad_language = p_ad_language)
         LEFT OUTER JOIN c_uom uom ON (uom.c_uom_id = cd.c_uom_id)
         LEFT OUTER JOIN c_uom_trl uom_trl ON (uom_trl.c_uom_id = cd.c_uom_id AND uom_trl.ad_language = p_ad_language)
--
WHERE TRUE
  AND cd.c_acctschema_id = acctSchema.c_acctschema_id
  AND cd.m_costelement_id = (SELECT ce.m_costelement_id FROM m_costelement ce WHERE ce.costingmethod = acctSchema.costingmethod AND ce.isactive = 'Y')
  AND COALESCE(cd.m_inoutline_id, cd.m_movementline_id, cd.m_inventoryline_id) IS NOT NULL
  --
  AND (COALESCE(p_M_Product_ID, 0) <= 0 OR cd.m_product_id = p_M_Product_ID)
  AND (COALESCE(p_M_Warehouse_ID, 0) <= 0 OR cd.m_warehouse_id = p_M_Warehouse_ID)
  AND cd.dateacct <= p_DateAcct
--
GROUP BY vc.combination,
         vc.description,
         activity.name,
         wh.name,
         p.value,
         COALESCE(p_trl.Name, p.Name),
         COALESCE(uom_trl.UOMSymbol, uom.UOMSymbol),
         -- currency.costingprecision,
         cd.m_product_id, cd.c_uom_id, cd.c_acctschema_id, cd.m_costelement_id, wh.ad_client_id, wh.ad_org_id
--
ORDER BY vc.combination,
         vc.description,
         activity.name,
         wh.name,
         p.value
    ;
$$
    LANGUAGE sql STABLE
;


--
-- Test:
/*
SELECT r.*, 
    (case when r.qty !=0 then round(r.TotalAmt/r.qty, 4) end) as costPrice_calc
FROM report.M_Cost_CostPrice_Function(
        p_keydate := '2024-12-11',
        p_M_Product_ID := (SELECT m_product_id FROM m_product WHERE value = '104031'),
        p_M_Warehouse_ID := 540008,
        p_showDetails := 'Y',
        p_ad_language := 'de_DE',
        p_ad_client_id := 1000000,
        p_ad_org_id := 1000000
     ) r
;
*/
