-- Source DDL: backend/de.metas.manufacturing/src/main/sql/postgresql/ddl/functions/computeCurrentBOMProductCost.sql
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
    v_ad_client_id      numeric;
    v_ad_org_id         numeric;
    v_acctschema_id     numeric;
    v_costelement_id    numeric;
    v_parent_product_id numeric;
    v_parent_uom_id     numeric;
    cost                numeric;
BEGIN
    -- Finished good (the BOM's own product) and its stock UOM, used to scale percentage
    -- lines from the finished good UOM into the BOM line UOM (see computeCurrentBOMLineProductCost).
    SELECT bom.AD_Client_ID, bom.AD_Org_ID, bom.M_Product_ID, parentProduct.C_UOM_ID
    INTO v_ad_client_id, v_ad_org_id, v_parent_product_id, v_parent_uom_id
    FROM PP_Product_BOM bom
    JOIN M_Product parentProduct ON parentProduct.M_Product_ID = bom.M_Product_ID
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

    -- Sum each direct BOM line: quantity for one finished good x component current cost.
    -- The component current cost is taken in the BOM line UOM (getCurrentCost converts from
    -- the component stock UOM), and a percentage line's quantity is scaled by the finished
    -- good -> line UOM conversion (see computeCurrentBOMLineProductCost).
    SELECT ROUND(SUM(
                         CASE WHEN bom.IsQtyPercentage = 'Y'
                                  THEN bom.Percentage / 100 * uomConvert(v_parent_product_id, v_parent_uom_id, bom.c_uom_id, 1) * bom.unit_cost
                                  ELSE bom.QtyBOM * bom.unit_cost
                         END
                 ), 6)
    INTO cost
    FROM (
             SELECT b.IsQtyPercentage,
                    b.QtyBOM,
                    b.Percentage,
                    b.c_uom_id,
                    COALESCE(getCurrentCost(
                                     b.m_product_id,
                                     b.c_uom_id,
                                     p_date,
                                     v_acctschema_id,
                                     v_costelement_id,
                                     v_ad_client_id,
                                     v_ad_org_id
                             ), 0) AS unit_cost
             FROM (SELECT * FROM pp_product_bom_recursive(p_pp_product_bom_id, NULL)) AS b
             WHERE b.depth = 2
         ) bom;

    RETURN cost;
END;
$$
;
