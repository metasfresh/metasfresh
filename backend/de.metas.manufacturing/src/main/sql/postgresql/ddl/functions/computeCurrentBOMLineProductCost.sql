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
    v_M_Product_ID       numeric;
    v_ad_client_id       numeric;
    v_ad_org_id          numeric;
    v_acctschema_id      numeric;
    v_costelement_id     numeric;
    v_cost               numeric;
    v_unit_cost          numeric;
    v_IsQtyPercentage    varchar;
    v_QtyBatch           numeric;
    v_QtyBOM             numeric;
    v_parent_bom_id      numeric;
    v_bomline_uom_id     numeric;
    v_parent_product_id  numeric;
    v_parent_uom_id      numeric;
    v_uom_multiplier     numeric;
BEGIN

    SELECT
        bomLine.M_Product_ID
         ,   bomLine.AD_Client_ID
         ,   bomLine.AD_Org_ID
         ,   bomLine.IsQtyPercentage
         ,   bomLine.QtyBatch
         ,   bomLine.QtyBOM
         ,   bomLine.PP_Product_BOM_ID
         ,   bomLine.C_UOM_ID
    INTO
        v_M_Product_ID
        ,   v_ad_client_id
        ,   v_ad_org_id
        ,   v_IsQtyPercentage
        ,   v_QtyBatch
        ,   v_QtyBOM
        ,   v_parent_bom_id
        ,   v_bomline_uom_id
    FROM PP_Product_BOMLine bomLine
    WHERE bomLine.PP_Product_BOMLine_ID = p_pp_product_bomline_id
    ;

    -- Finished good (the BOM's own product) and its stock UOM, used to scale a
    -- percentage line: the percentage is relative to one finished good, so it must be
    -- converted from the finished good UOM into the BOM line UOM (mirrors the qty
    -- explosion in org.eevolution.api.impl.ProductBOMBL.computeQtyMultiplier).
    SELECT
        parentBom.M_Product_ID
         ,   parentProduct.C_UOM_ID
    INTO
        v_parent_product_id
        ,   v_parent_uom_id
    FROM PP_Product_BOM parentBom
    JOIN M_Product parentProduct ON parentProduct.M_Product_ID = parentBom.M_Product_ID
    WHERE parentBom.PP_Product_BOM_ID = v_parent_bom_id
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

    -- Component current cost, expressed in the BOM line UOM. getCurrentCost converts
    -- from the component's stock UOM into the line UOM (e.g. 494.28 / Laib -> 14.54 / kg),
    -- so the cost basis matches the quantity basis used below.
    v_unit_cost := COALESCE(getCurrentCost(
                                    v_M_Product_ID,
                                    v_bomline_uom_id,
                                    p_date,
                                    v_acctschema_id,
                                    v_costelement_id,
                                    v_ad_client_id,
                                    v_ad_org_id
                            ), 0);

    -- Apply qty / percentage from the BOM line.
    -- For a percentage line the quantity for one finished good is
    --   (QtyBatch / 100) * uomConvert(finishedGood, finishedGoodStockUOM -> lineUOM, 1).
    -- uomConvert returns 1 when both UOMs match (no change), and NULL when no conversion
    -- is defined for a cross-UOM line (the cost is then NULL, surfacing the missing conversion).
    IF v_IsQtyPercentage = 'Y' THEN
        v_uom_multiplier := uomConvert(v_parent_product_id, v_parent_uom_id, v_bomline_uom_id, 1);
        v_cost := ROUND(v_QtyBatch / 100 * v_uom_multiplier * v_unit_cost, 6);
    ELSE
        v_cost := ROUND(v_QtyBOM * v_unit_cost, 6);
    END IF;

    RETURN v_cost;

END;
$$;
