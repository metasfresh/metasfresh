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
    CASE WHEN bl.IsQtyPercentage = 'Y'
        THEN bl.QtyBatch / 100 * cost.unit_cost
        ELSE bl.QtyBOM * cost.unit_cost
    END
), 0)
FROM PP_Product_BOMLine bl
CROSS JOIN LATERAL (
    SELECT COALESCE(
        getCurrentCost(
            bl.M_Product_ID,
            bl.C_UOM_ID,
            p_date,
            p_acctschema_id,
            p_m_costelement_id,
            p_ad_client_id,
            p_ad_org_id
        ), 0) AS unit_cost
) cost
WHERE bl.PP_Product_BOM_ID = p_PP_Product_BOM_ID
  AND bl.IsActive = 'Y'
$func$;
