DROP FUNCTION IF EXISTS computeProductBOMCost(numeric, date, numeric, numeric, numeric, numeric);

CREATE OR REPLACE FUNCTION computeProductBOMCost(
    p_PP_Product_BOM_ID  numeric,
    p_date               date,
    p_acctschema_id      numeric,
    p_m_costelement_id   numeric,
    p_ad_client_id       numeric,
    p_ad_org_id          numeric
)
RETURNS numeric
LANGUAGE sql STABLE
COST 100
AS
$func$
SELECT COALESCE(SUM(
    CASE
        WHEN bom.IsQtyPercentage = 'Y' THEN bom.Percentage / 100 * cost.unit_cost
        ELSE bom.QtyBOM * cost.unit_cost
    END
), 0)
FROM PP_Product_BOM_Recursive(p_PP_Product_BOM_ID, NULL) bom
CROSS JOIN LATERAL (
    SELECT COALESCE(
        getCurrentCost(
            p_m_product_id     := bom.M_Product_ID,
            p_c_uom_id         := bom.C_UOM_ID,
            p_date             := p_date,
            p_acctschema_id    := p_acctschema_id,
            p_m_costelement_id := p_m_costelement_id,
            p_ad_client_id     := p_ad_client_id,
            p_ad_org_id        := p_ad_org_id
        ), 0) AS unit_cost
) cost
WHERE bom.depth >= 2
$func$;
