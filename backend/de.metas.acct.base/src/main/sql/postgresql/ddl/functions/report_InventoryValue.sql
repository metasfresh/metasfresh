DROP FUNCTION IF EXISTS de_metas_acct.report_InventoryValue(
    IN p_DateAcct       timestamp WITH TIME ZONE,
    IN p_M_Product_ID   numeric(10, 0),
    IN p_M_Warehouse_ID numeric(10, 0),
    IN p_AD_Language    character varying(6)
)
;
DROP FUNCTION IF EXISTS de_metas_acct.report_InventoryValue_NEW(
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
                combination              character varying,
                description              character varying,
                OrgName                  character varying,
                ActivityName             character varying,
                WarehouseName            character varying,
                ProductValue             character varying,
                ProductName              character varying,
                Qty                      numeric,
                UOMSymbol                character varying,
                CostPrice                numeric,
                TotalAmt                 numeric,
                AmtAcctBalance           numeric,
                AmtAcctBalance_YearStart numeric
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    DROP TABLE IF EXISTS tmp_inventory_valuation;
    CREATE TEMPORARY TABLE tmp_inventory_valuation AS
    SELECT ROW_NUMBER() OVER ()                                                                              AS id,
           fa.C_AcctSchema_ID                                                                                AS C_AcctSchema_ID,
           ev.C_ElementValue_ID                                                                              AS C_ElementValue_ID,
           ev.Value                                                                                          AS AccountValue,
           ev.Name                                                                                           AS AccountName,
           activity.Name                                                                                     AS ActivityName,
           COALESCE(wh.Name, '?')                                                                            AS WarehouseName,
           loc.m_warehouse_id                                                                                AS M_Warehouse_ID,
           fa.AD_Org_ID                                                                                      AS AD_Org_ID,
           org.Name                                                                                          AS OrgName,
           fa.AD_Client_ID                                                                                   AS AD_Client_ID,
           COALESCE(p.value, '?')                                                                            AS ProductValue,
           COALESCE(p_trl.Name, p.Name, '?')                                                                 AS ProductName,
           fa.m_product_id                                                                                   AS M_Product_ID,
           COALESCE(SUM(fa.qty), 0)                                                                          AS Qty,
           COALESCE(uom_trl.UOMSymbol, uom.UOMSymbol)                                                        AS UOMSymbol,
           uom.C_UOM_ID                                                                                      AS C_UOM_ID,
           SUM(fa.amtacctdr - fa.amtacctcr)                                                                  AS AmtAcctBalance,
           SUM((CASE WHEN fa.dateacct < TRUNC(p_DateAcct, 'Y') THEN fa.amtacctdr - fa.amtacctcr ELSE 0 END)) AS AmtAcctBalance_YearStart,
           currency.iso_code                                                                                 AS Currency,
           currency.stdprecision                                                                             AS StdPrecision,
           currency.costingprecision                                                                         AS CostingPrecision,
           NULL::numeric                                                                                     AS CostPrice,
           NULL::numeric                                                                                     AS InventoryValue,
           NULL::integer                                                                                     AS InventoryValue_Rank,
           (SELECT COALESCE(pca.costinglevel, 'O')
            FROM m_product_category_acct pca
            WHERE pca.m_product_category_id = p.m_product_category_id
              AND pca.c_acctschema_id = fa.c_acctschema_id)                                                  AS CostingLevel,
           NULL::text                                                                                           CostingAggKey
    FROM fact_acct fa
             INNER JOIN c_acctschema acctSchema ON acctSchema.c_acctschema_id = fa.c_acctschema_id
             INNER JOIN c_currency currency ON (currency.c_currency_id = acctSchema.c_currency_id)
             INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
             INNER JOIN ad_org org ON org.ad_org_id = fa.ad_org_id
             LEFT OUTER JOIN m_locator loc ON loc.m_locator_id = fa.m_locator_id
             LEFT OUTER JOIN m_warehouse wh ON wh.m_warehouse_id = loc.m_warehouse_id
             LEFT OUTER JOIN c_activity activity ON (activity.c_activity_id = wh.c_activity_id)
             LEFT OUTER JOIN m_product p ON (p.m_product_id = fa.m_product_id)
             LEFT OUTER JOIN m_product_trl p_trl ON (p_trl.m_product_id = fa.m_product_id AND p_trl.ad_language = p_AD_Language)
             LEFT OUTER JOIN c_uom uom ON (uom.c_uom_id = COALESCE(fa.c_uom_id, p.c_uom_id))
             LEFT OUTER JOIN c_uom_trl uom_trl ON (uom_trl.c_uom_id = uom.c_uom_id AND uom_trl.ad_language = p_AD_Language)
    WHERE TRUE
      AND fa.c_acctschema_id = getC_AcctSchema_ID(fa.ad_client_id, fa.ad_org_id)
      AND fa.accountconceptualname = 'P_Asset_Acct'
      AND (COALESCE(p_M_Product_ID, 0) <= 0 OR fa.m_product_id = p_M_Product_ID)
      -- NOTE: consider all warehouses for the moment, we might need them to calculate the Inventory Value
      -- AND (COALESCE(p_M_Warehouse_ID, 0) <= 0 OR loc.M_Warehouse_ID = p_M_Warehouse_ID)
      AND fa.DateAcct <= p_DateAcct
    GROUP BY ev.C_ElementValue_ID, ev.value, ev.name,
             wh.name, loc.m_warehouse_id, activity.Name,
             p.value, p.name, p_trl.name, fa.m_product_id, p.m_product_category_id,
             uom.c_uom_id, uom.UOMSymbol, uom_trl.UOMSymbol,
             fa.c_acctschema_id,
             fa.ad_client_id, fa.ad_org_id, org.name,
             acctSchema.costingmethod,
             currency.stdprecision, currency.costingprecision, currency.iso_code;
    CREATE INDEX ON tmp_inventory_valuation (id);

    --
    -- Compute Costing Aggregation Key
    UPDATE tmp_inventory_valuation
    SET CostingAggKey=(
                          CASE
                              WHEN CostingLevel = 'C' THEN 'C=' || AD_Client_ID
                              WHEN CostingLevel = 'O' THEN 'O=' || AD_Org_ID
                                                      ELSE 'W=' || COALESCE(M_Warehouse_ID, 0) -- shall not happen, consider it to our most detailed level of aggregation
                          END)
                          || ',P=' || COALESCE(M_Product_ID, 0)
                          || ',UOM=' || COALESCE(C_UOM_ID, 0)
                          || ',ACCT=' || C_ElementValue_ID
                          || ',ACCTSCHEMA=' || C_AcctSchema_ID;
    CREATE INDEX ON tmp_inventory_valuation (CostingAggKey);


    --
    -- Compute the aggregated costs at the costing level 
    DROP TABLE IF EXISTS tmp_aggregated_costs;
    CREATE TEMPORARY TABLE tmp_aggregated_costs AS
    SELECT t.CostingAggKey,
           SUM(t.AmtAcctBalance)                                                                                     AS AmtAcctBalance,
           SUM(t.Qty)                                                                                                AS Qty,
           (CASE WHEN SUM(t.Qty) != 0 THEN ROUND(SUM(t.AmtAcctBalance) / SUM(t.Qty), t.CostingPrecision) ELSE 0 END) AS CostPrice
    FROM tmp_inventory_valuation t
    GROUP BY t.CostingAggKey, t.CostingPrecision;
    CREATE UNIQUE INDEX ON tmp_aggregated_costs (CostingAggKey);

    --
    -- Compute Current Cost Price
    UPDATE tmp_inventory_valuation t
    SET CostPrice=(SELECT agg.CostPrice FROM tmp_aggregated_costs agg WHERE agg.CostingAggKey = t.CostingAggKey);

    --
    -- Compute Inventory Value
    UPDATE tmp_inventory_valuation t SET InventoryValue=ROUND(t.CostPrice * t.Qty, t.StdPrecision);

    --
    -- Compute Inventory Value Rank.
    -- Will be used to do InventoryValue adjustments to get rid of rounding errors
    WITH ranked_data AS (SELECT t.id,
                                RANK() OVER (PARTITION BY t.CostingAggKey ORDER BY ABS(t.InventoryValue) DESC, ABS(t.AmtAcctBalance) DESC, t.M_Warehouse_ID) AS InventoryValue_Rank
                         FROM tmp_inventory_valuation t)
    UPDATE tmp_inventory_valuation t
    SET InventoryValue_Rank=ranked_data.InventoryValue_Rank
    FROM ranked_data
    WHERE t.id = ranked_data.id;

    --
    -- Adjust Inventory Value, getting rid of rounding errors
    UPDATE tmp_inventory_valuation t
    SET InventoryValue=(SELECT agg.AmtAcctBalance FROM tmp_aggregated_costs agg WHERE agg.CostingAggKey = t.CostingAggKey)
        - (SELECT COALESCE(SUM(InventoryValue), 0) FROM tmp_inventory_valuation others WHERE others.CostingAggKey = t.CostingAggKey AND others.id != t.id)
    WHERE t.InventoryValue_Rank = 1;


    RETURN QUERY (
        --
        SELECT t.AccountValue   AS Combination,
               t.AccountName    AS Description,
               t.OrgName        AS OrgName,
               t.ActivityName,
               t.WarehouseName,
               t.ProductValue,
               t.ProductName,
               t.Qty,
               t.UOMSymbol,
               t.CostPrice,
               t.InventoryValue AS TotalAmt,
               t.AmtAcctBalance,
               t.AmtAcctBalance_YearStart
        FROM tmp_inventory_valuation t
        WHERE TRUE
          AND (COALESCE(p_M_Warehouse_ID, 0) <= 0 OR t.M_Warehouse_ID = p_M_Warehouse_ID)
        ORDER BY t.ProductValue, t.WarehouseName
        --
    );
END;
$$
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
