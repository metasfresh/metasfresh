DROP FUNCTION IF EXISTS computeCurrentBOMLineProductCost(
    p_pp_product_bomline_id numeric,
    p_date                  date
);

CREATE OR REPLACE FUNCTION computeCurrentBOMLineProductCost(
    p_pp_product_bomline_id numeric,
    p_date                  date
)
    RETURNS numeric
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_PP_Product_BOM_ID  numeric;
    v_M_Product_ID       numeric;
    v_ad_client_id       numeric;
    v_ad_org_id          numeric;
    v_acctschema_id      numeric;
    v_costelement_id     numeric;
    v_cost               numeric;
    v_sub_bom_cost       numeric;
    v_IsQtyPercentage    varchar;
    v_QtyBatch           numeric;
    v_QtyBOM             numeric;
BEGIN

    SELECT
        bomLine.M_Product_ID
         ,   bomLine.AD_Client_ID
         ,   bomLine.AD_Org_ID
         ,   bomLine.IsQtyPercentage
         ,   bomLine.QtyBatch
         ,   bomLine.QtyBOM
    INTO
        v_M_Product_ID
        ,   v_ad_client_id
        ,   v_ad_org_id
        ,   v_IsQtyPercentage
        ,   v_QtyBatch
        ,   v_QtyBOM
    FROM PP_Product_BOMLine bomLine
    WHERE bomLine.PP_Product_BOMLine_ID = p_pp_product_bomline_id
    ;

    -- Find if the component itself has a BOM (sub-assembly)
    SELECT
        bom.PP_Product_BOM_ID
    INTO v_PP_Product_BOM_ID
    FROM PP_Product_BOM bom
    WHERE bom.M_Product_ID = v_M_Product_ID
      AND bom.IsActive = 'Y'
      AND (bom.validto >= p_date OR bom.validto IS NULL)
      AND (bom.validfrom <= p_date OR bom.validfrom IS NULL)
    ORDER BY
        bom.validfrom DESC
           ,   bom.PP_Product_BOM_ID DESC
    LIMIT 1
    ;

    SELECT
        ci.C_AcctSchema1_ID
    INTO v_acctschema_id
    FROM AD_ClientInfo ci
    WHERE ci.AD_Client_ID = v_ad_client_id
    ;

    SELECT
        ce.M_CostElement_ID
    INTO v_costelement_id
    FROM M_CostElement ce
    WHERE ce.IsActive = 'Y'
      AND ce.CostElementType = 'M'
      AND EXISTS
        (
            SELECT 1
            FROM C_AcctSchema cas
            WHERE cas.CostingMethod = ce.CostingMethod
              AND cas.C_AcctSchema_ID = v_acctschema_id
        )
    LIMIT 1
    ;

    -- Determine the unit cost: either from sub-BOM or from cost element
    IF v_PP_Product_BOM_ID IS NOT NULL THEN
        -- Sub-assembly: get the cost of the sub-BOM (cost per 1 unit)
        v_sub_bom_cost := computeCurrentBOMProductCost(v_PP_Product_BOM_ID, p_date);
    ELSE
        -- Leaf component: get current cost per unit
        v_sub_bom_cost := COALESCE(getCurrentCost(
                                           v_M_Product_ID,
                                           NULL,  -- use default UOM
                                           p_date,
                                           v_acctschema_id,
                                           v_costelement_id,
                                           v_ad_client_id,
                                           v_ad_org_id
                                   ), 0);
    END IF;

    -- Apply qty / percentage from the BOM line
    v_cost := ROUND(
            CASE
                WHEN v_IsQtyPercentage = 'Y'
                    THEN v_QtyBatch / 100 * v_sub_bom_cost
                    ELSE
                    v_QtyBOM * v_sub_bom_cost
            END
        , 6);

    RETURN v_cost;

END;
$$;



DROP FUNCTION IF EXISTS computeCurrentBOMProductCost(p_pp_product_bom_id numeric,
                                                    p_date              date)
;

CREATE OR REPLACE FUNCTION computeCurrentBOMProductCost(
    p_pp_product_bom_id numeric,
    p_date              date
)
    RETURNS numeric
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_ad_client_id   numeric;
    v_ad_org_id      numeric;
    v_acctschema_id  numeric;
    v_costelement_id numeric;
    cost             numeric;
BEGIN
    SELECT bom.AD_Client_ID, bom.AD_Org_ID
    INTO v_ad_client_id, v_ad_org_id
    FROM PP_Product_BOM bom
    WHERE bom.PP_Product_BOM_ID = p_pp_product_bom_id;

    SELECT ci.C_AcctSchema1_ID
    INTO v_acctschema_id
    FROM AD_ClientInfo ci
    WHERE ci.AD_Client_ID = v_ad_client_id;

    SELECT ce.M_CostElement_ID
    INTO v_costelement_id
    FROM M_CostElement ce
    WHERE ce.IsActive = 'Y'
      AND ce.CostElementType = 'M'
      AND EXISTS (SELECT 1
                  FROM C_AcctSchema cas
                  WHERE cas.CostingMethod = ce.CostingMethod
                    AND cas.C_AcctSchema_ID = v_acctschema_id)
    LIMIT 1;

    SELECT ROUND(SUM(
                         CASE WHEN bom.IsQtyPercentage = 'Y'
                                  THEN bom.Percentage / 100 * bom.unit_cost
                                  ELSE bom.QtyBOM * bom.unit_cost
                         END
                 ), 6)
    INTO cost
    FROM (
             SELECT b.IsQtyPercentage,
                    b.QtyBOM,
                    b.Percentage,
                    CASE
                        WHEN b.PP_Product_BOM_ID IS NOT NULL
                            THEN COALESCE(computeCurrentBOMProductCost(b.PP_Product_BOM_ID, p_date), 0)
                        ELSE COALESCE(getCurrentCost(
                                              b.m_product_id,
                                              b.c_uom_id,
                                              p_date,
                                              v_acctschema_id,
                                              v_costelement_id,
                                              v_ad_client_id,
                                              v_ad_org_id
                                      ), 0)
                        END AS unit_cost
             FROM (SELECT * FROM pp_product_bom_recursive(p_pp_product_bom_id, NULL)) AS b
             WHERE b.depth = 2
         ) bom;

    RETURN cost;
END;
$$
;

DROP FUNCTION IF EXISTS PP_Product_BOM_Recursive_Report(numeric)
;
DROP FUNCTION IF EXISTS PP_Product_BOM_Recursive_Report(numeric, date)
;

CREATE OR REPLACE FUNCTION PP_Product_BOM_Recursive_Report(
    p_PP_Product_BOM_ID numeric,
    p_date              date DEFAULT NOW()::date
)
    RETURNS table
            (
                Line         text,
                ProductValue varchar,
                ProductName  varchar,
                QtyBOM       numeric,
                Percentage   numeric,
                UOMSymbol    varchar,
                Cost         numeric
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_ad_client_id   numeric;
    v_ad_org_id      numeric;
    v_acctschema_id  numeric;
    v_costelement_id numeric;
BEGIN

    SELECT bom.AD_Client_ID, bom.AD_Org_ID
    INTO v_ad_client_id, v_ad_org_id
    FROM PP_Product_BOM bom
    WHERE bom.PP_Product_BOM_ID = p_pp_product_bom_id;

    SELECT ci.C_AcctSchema1_ID
    INTO v_acctschema_id
    FROM AD_ClientInfo ci
    WHERE ci.AD_Client_ID = v_ad_client_id;

    SELECT ce.M_CostElement_ID
    INTO v_costelement_id
    FROM M_CostElement ce
    WHERE ce.IsActive = 'Y'
      AND ce.CostElementType = 'M'
      AND EXISTS (SELECT 1
                  FROM C_AcctSchema cas
                  WHERE cas.CostingMethod = ce.CostingMethod
                    AND cas.C_AcctSchema_ID = v_acctschema_id)
    LIMIT 1;

    RETURN QUERY
        SELECT t.Line,
               t.ProductValue,
               t.ProductName,
               t.QtyBOM,
               t.Percentage,
               t.UOMSymbol,
               ROUND(
                       t.cumulative_qty
                       *
                       CASE WHEN t.PP_Product_BOM_ID IS NOT NULL
                                THEN COALESCE(computeCurrentBOMProductCost(t.PP_Product_BOM_ID, p_date), 0)
                                ELSE COALESCE(getCurrentCost(
                                                      t.m_product_id,
                                                      t.c_uom_id,
                                                      p_date,
                                                      v_acctschema_id,
                                                      v_costelement_id,
                                                      v_ad_client_id,
                                                      v_ad_org_id
                                              ), 0)
                           END
                   , 6) AS cost
        FROM PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID, NULL) t
        ORDER BY t.path;
END;
$$
;
