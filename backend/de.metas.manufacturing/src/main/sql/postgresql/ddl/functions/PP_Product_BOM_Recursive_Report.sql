DROP FUNCTION IF EXISTS PP_Product_BOM_Recursive_Report(numeric)
;

CREATE OR REPLACE FUNCTION PP_Product_BOM_Recursive_Report(p_PP_Product_BOM_ID numeric)
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
               (CASE
                    WHEN t.pp_product_bom_id > 0 THEN
                        computeCurentBOMProductCost(
                                p_pp_product_bom_id => t.PP_Product_BOM_ID,
                                p_date => NOW()::date)
                                                 ELSE
                        (
                            ROUND(
                                    CASE
                                        WHEN t.IsQtyPercentage = 'Y'
                                            THEN t.Percentage / 100 * COALESCE(getCurrentCost(
                                                                                       t.m_product_id,
                                                                                       t.c_uom_id,
                                                                                       NOW()::date,
                                                                                       v_acctschema_id,
                                                                                       v_costelement_id,
                                                                                       v_ad_client_id,
                                                                                       v_ad_org_id
                                                                               ), 0)
                                            ELSE t.QtyBOM * COALESCE(getCurrentCost(
                                                                             t.m_product_id,
                                                                             t.c_uom_id,
                                                                             NOW()::date,
                                                                             v_acctschema_id,
                                                                             v_costelement_id,
                                                                             v_ad_client_id,
                                                                             v_ad_org_id
                                                                     ), 0)
                                    END
                                , 2)
                            )

                END
                   ) AS cost
        FROM PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID, NULL) t
        ORDER BY t.path;
END;
$$
;