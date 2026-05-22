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
                 ), 2)
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
