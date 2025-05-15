SELECT db_drop_functions('de_metas_acct.report_InventoryValue')
;

-- Used for Lagerwert (Excel)
CREATE OR REPLACE FUNCTION de_metas_acct.report_InventoryValue(
    IN p_DateAcct               timestamp WITH TIME ZONE,
    IN p_M_Product_ID           numeric(10, 0) = 0,
    IN p_M_Warehouse_ID         numeric(10, 0) = 0,
    IN p_AD_Language            character varying(6) = 'de_DE',
    IN p_Exclude_GL_Journal_IDs numeric[] = NULL
)
    RETURNS TABLE
            (
                combination                     character varying,
                description                     character varying,
                OrgName                         character varying,
                ActivityName                    character varying,
                WarehouseName                   character varying,
                ProductCategoryName             character varying,
                ProductValue                    character varying,
                ProductName                     character varying,
                Qty                             numeric,
                UOMSymbol                       character varying,
                --
                Acct_CostPrice                  numeric,
                Acct_ExpectedAmt                numeric,
                Acct_ErrorAmt                   numeric,
                --
                Costing_CostPrice               numeric,
                Costing_ExpectedAmt             numeric,
                Costing_ErrorAmt                numeric,
                --
                InventoryValueAcctAmt           numeric,
                InventoryValueAcctAmt_YearStart numeric
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_start_time    TIMESTAMP := CLOCK_TIMESTAMP();
    v_affected_rows integer;
BEGIN
    --
    -- Create tmp_inventory_valuation work table
    -- IMPORTANT: before changing the name of tmp_inventory_valuation table or removing/renaming some columns, please check which other functions are using it
    BEGIN
        RAISE NOTICE 'Preparing tmp_inventory_valuation...';
        v_start_time := CLOCK_TIMESTAMP();

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
               COALESCE(pc_trl.Name, pc.Name)                                                                    AS ProductCategoryName,
               COALESCE(p.value, '?')                                                                            AS ProductValue,
               COALESCE(p_trl.Name, p.Name, '?')                                                                 AS ProductName,
               fa.m_product_id                                                                                   AS M_Product_ID,
               COALESCE(SUM(fa.qty), 0)                                                                          AS Qty,
               COALESCE(uom_trl.UOMSymbol, uom.UOMSymbol)                                                        AS UOMSymbol,
               uom.C_UOM_ID                                                                                      AS C_UOM_ID,
               currency.iso_code                                                                                 AS Currency,
               currency.stdprecision                                                                             AS StdPrecision,
               currency.costingprecision                                                                         AS CostingPrecision,
               --
               (SELECT COALESCE(pca.costinglevel, 'O')
                FROM m_product_category_acct pca
                WHERE pca.m_product_category_id = p.m_product_category_id
                  AND pca.c_acctschema_id = fa.c_acctschema_id)                                                  AS CostingLevel,
               NULL::text                                                                                        AS CostingAggKey,
               NULL::numeric                                                                                     AS Costing_Org_ID,
               NULL::numeric                                                                                     AS M_CostElement_ID,
               --
               NULL::numeric                                                                                     AS Acct_CostPrice,
               NULL::numeric                                                                                     AS Acct_InventoryValue,
               NULL::numeric                                                                                     AS Acct_ErrorAmt,    -- Expected(InventoryValue) minus AmtAcctBalance
               --
               NULL::numeric                                                                                     AS Costing_CostPrice,
               NULL::numeric                                                                                     AS Costing_InventoryValue,
               NULL::numeric                                                                                     AS Costing_ErrorAmt, -- Expected(InventoryValue) minus AmtAcctBalance
               --
               SUM(fa.amtacctdr - fa.amtacctcr)                                                                  AS AmtAcctBalance,
               SUM((CASE WHEN fa.dateacct < TRUNC(p_DateAcct, 'Y') THEN fa.amtacctdr - fa.amtacctcr ELSE 0 END)) AS AmtAcctBalance_YearStart,
               --
               COUNT(1)                                                                                          AS count_trxs
        --
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
                 LEFT OUTER JOIN m_product_category pc ON (pc.m_product_category_id = p.m_product_category_id)
                 LEFT OUTER JOIN m_product_category_trl pc_trl ON (pc_trl.m_product_category_id = pc.m_product_category_id AND pc_trl.ad_language = p_AD_Language)
                 LEFT OUTER JOIN c_uom uom ON (uom.c_uom_id = COALESCE(fa.c_uom_id, p.c_uom_id))
                 LEFT OUTER JOIN c_uom_trl uom_trl ON (uom_trl.c_uom_id = uom.c_uom_id AND uom_trl.ad_language = p_AD_Language)
        WHERE TRUE
          AND fa.c_acctschema_id = getC_AcctSchema_ID(fa.ad_client_id, fa.ad_org_id)
          AND fa.accountconceptualname = 'P_Asset_Acct'
          AND (COALESCE(p_M_Product_ID, 0) <= 0 OR fa.m_product_id = p_M_Product_ID)
          -- NOTE: consider all warehouses for the moment, we might need them to calculate the Inventory Value and Acct_CostPrice
          -- AND (COALESCE(p_M_Warehouse_ID, 0) <= 0 OR loc.M_Warehouse_ID = p_M_Warehouse_ID)
          AND fa.DateAcct <= p_DateAcct
          AND (p_Exclude_GL_Journal_IDs IS NULL OR NOT (fa.ad_table_id = get_table_id('GL_Journal') AND fa.record_id = ANY (p_Exclude_GL_Journal_IDs)))
        GROUP BY ev.C_ElementValue_ID, ev.value, ev.name,
                 wh.name, loc.m_warehouse_id, activity.Name,
                 pc.name, pc_trl.name, p.m_product_category_id,
                 p.value, p.name, p_trl.name, fa.m_product_id,
                 uom.c_uom_id, uom.UOMSymbol, uom_trl.UOMSymbol,
                 fa.c_acctschema_id,
                 fa.ad_client_id, fa.ad_org_id, org.name,
                 acctSchema.costingmethod,
                 currency.stdprecision, currency.costingprecision, currency.iso_code;
        GET DIAGNOSTICS v_affected_rows = ROW_COUNT;
        CREATE UNIQUE INDEX ON tmp_inventory_valuation (id);
        CREATE UNIQUE INDEX ON tmp_inventory_valuation (M_Warehouse_ID, M_Product_ID, C_UOM_ID, C_ElementValue_ID);

        --
        -- Update M_CostElement_ID
        -- noinspection SqlWithoutWhere
        UPDATE tmp_inventory_valuation t SET M_CostElement_ID=de_metas_acct.getAcct_CostElement_ID(t.C_AcctSchema_ID);
        ALTER TABLE tmp_inventory_valuation
            ALTER COLUMN M_CostElement_ID SET NOT NULL;

        --
        -- Compute Costing Aggregation Key
        UPDATE tmp_inventory_valuation
        SET Costing_Org_ID=(CASE WHEN CostingLevel = 'O' THEN AD_Org_ID END),
            CostingAggKey=(
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
        RAISE NOTICE 'Created tmp_inventory_valuation with % rows (took %)', v_affected_rows, (CLOCK_TIMESTAMP() - v_start_time);
    END;


    --
    -- Compute the aggregated costs at the costing level
    BEGIN
        v_start_time := CLOCK_TIMESTAMP();

        DROP TABLE IF EXISTS tmp_aggregated_costs;
        CREATE TEMPORARY TABLE tmp_aggregated_costs AS
        SELECT t.CostingAggKey,
               --
               t.M_Product_ID                                                                                            AS M_Product_ID,
               MIN(t.C_UOM_ID)                                                                                           AS C_UOM_ID,
               t.C_AcctSchema_ID                                                                                         AS C_AcctSchema_ID,
               t.M_CostElement_ID                                                                                        AS M_CostElement_ID,
               t.AD_Client_ID                                                                                            AS Costing_Client_ID,
               t.Costing_Org_ID                                                                                          AS Costing_Org_ID,
               --
               SUM(t.AmtAcctBalance)                                                                                     AS AmtAcctBalance,
               SUM(t.Qty)                                                                                                AS Qty,
               (CASE WHEN SUM(t.Qty) != 0 THEN ROUND(SUM(t.AmtAcctBalance) / SUM(t.Qty), t.CostingPrecision) ELSE 0 END) AS Acct_CostPrice,
               NULL::numeric                                                                                             AS Costing_CostPrice
        FROM tmp_inventory_valuation t
        GROUP BY t.CostingAggKey,
                 --
                 t.M_Product_ID,
                 t.C_AcctSchema_ID,
                 t.M_CostElement_ID,
                 t.AD_Client_ID,
                 t.Costing_Org_ID,
                 --
                 t.CostingPrecision;
        GET DIAGNOSTICS v_affected_rows = ROW_COUNT;
        CREATE UNIQUE INDEX ON tmp_aggregated_costs (CostingAggKey);
        --
        RAISE NOTICE 'Created tmp_aggregated_costs with % rows (took %)', v_affected_rows, (CLOCK_TIMESTAMP() - v_start_time);


        v_start_time := CLOCK_TIMESTAMP();
        -- noinspection SqlWithoutWhere
        UPDATE tmp_aggregated_costs agg
        SET Costing_CostPrice=getCurrentCost(
                p_m_product_id =>agg.M_Product_ID,
                p_c_uom_id =>agg.C_UOM_ID,
                p_date =>p_DateAcct::date,
                p_acctschema_id =>agg.C_AcctSchema_ID,
                p_m_costelement_id =>agg.M_CostElement_ID,
                p_ad_client_id =>agg.Costing_Client_ID,
                p_ad_org_id =>agg.Costing_Org_ID);
        --
        RAISE NOTICE 'Computed tmp_aggregated_costs.Costing_CostPrice (took %)', (CLOCK_TIMESTAMP() - v_start_time);
    END;

    --
    -- Compute Cost Price, InventoryValue, ErrorAmt (Accounting and Costing)
    BEGIN
        v_start_time := CLOCK_TIMESTAMP();

        -- noinspection SqlWithoutWhere
        UPDATE tmp_inventory_valuation t
        SET Acct_CostPrice=agg.Acct_CostPrice,
            Costing_CostPrice=agg.Costing_CostPrice
        FROM tmp_aggregated_costs agg
        WHERE agg.CostingAggKey = t.CostingAggKey;
        -- noinspection SqlWithoutWhere
        UPDATE tmp_inventory_valuation t
        SET Acct_InventoryValue=ROUND(t.Acct_CostPrice * t.Qty, t.StdPrecision),
            Costing_InventoryValue=ROUND(t.Costing_CostPrice * t.Qty, t.StdPrecision);
        -- noinspection SqlWithoutWhere
        UPDATE tmp_inventory_valuation t
        SET Acct_ErrorAmt=t.Acct_InventoryValue - t.AmtAcctBalance,
            Costing_ErrorAmt=t.Costing_InventoryValue - t.AmtAcctBalance;

        RAISE NOTICE 'Computed Cost Price, InventoryValue, ErrorAmt (Accounting and Costing) (took %)', (CLOCK_TIMESTAMP() - v_start_time);
    END;


    RAISE NOTICE 'Returning table result';
    RETURN QUERY (
        --
        SELECT t.AccountValue             AS Combination,
               t.AccountName              AS Description,
               t.OrgName                  AS OrgName,
               t.ActivityName,
               t.WarehouseName,
               t.ProductCategoryName,
               t.ProductValue,
               t.ProductName,
               t.Qty,
               t.UOMSymbol,
               --
               t.Acct_CostPrice           AS Acct_CostPrice,
               t.Acct_InventoryValue      AS Acct_ExpectedAmt,
               t.Acct_ErrorAmt            AS Acct_ErrorAmt,
               --
               t.Costing_CostPrice        AS Costing_CostPrice,
               t.Costing_InventoryValue   AS Costing_ExpectedAmt,
               t.Costing_ErrorAmt         AS Costing_ErrorAmt,
               --
               t.AmtAcctBalance           AS InventoryValueAcctAmt,
               t.AmtAcctBalance_YearStart AS InventoryValueAcctAmt_YearStart
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
