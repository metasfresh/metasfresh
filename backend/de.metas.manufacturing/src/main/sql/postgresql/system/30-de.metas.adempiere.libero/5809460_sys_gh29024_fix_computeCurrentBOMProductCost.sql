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

    -- Sum each direct BOM line (depth=2; depth=1 is the finished good itself): quantity for
    -- one finished good x component current cost. The component current cost (bom.unit_cost) is
    -- taken in the BOM line UOM (getCurrentCost converts from the component stock UOM, and for a
    -- sub-assembly component returns its own already-rolled-up current cost). A percentage line's
    -- quantity is scaled by bom.uom_mult = the finished good -> line UOM conversion. bom.Percentage
    -- is PP_Product_BOMLine.QtyBatch as exposed by pp_product_bom_recursive for percentage lines.
    --
    -- A percentage line whose finished good / line UOMs cannot be converted has uom_mult = NULL;
    -- that is a configuration gap, so the whole BOM cost is returned NULL to surface it (mirrors
    -- computeCurrentBOMLineProductCost, which returns NULL for that line). The guard targets only
    -- percentage lines, so a line that is legitimately NULL for other reasons (e.g. a by-product
    -- with no QtyBOM) is still skipped by SUM without nulling the whole total.
    SELECT CASE
               WHEN bool_or(bom.IsQtyPercentage = 'Y' AND bom.uom_mult IS NULL) THEN NULL
               ELSE ROUND(SUM(
                                  CASE WHEN bom.IsQtyPercentage = 'Y'
                                           THEN bom.Percentage / 100 * bom.uom_mult * bom.unit_cost
                                           ELSE bom.QtyBOM * bom.unit_cost
                                  END
                          ), 6)
           END
    INTO cost
    FROM (
             SELECT b.IsQtyPercentage,
                    b.QtyBOM,
                    b.Percentage,
                    uomConvert(v_parent_product_id, v_parent_uom_id, b.c_uom_id, 1) AS uom_mult,
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
