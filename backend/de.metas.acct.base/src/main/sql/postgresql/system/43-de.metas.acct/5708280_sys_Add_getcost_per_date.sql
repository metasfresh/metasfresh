DROP FUNCTION IF EXISTS getCurrentCost(numeric,
                                       date,
                                       numeric,
                                       numeric,
                                       numeric,
                                       numeric)
;



DROP FUNCTION IF EXISTS getCurrentCost(numeric,
                                       numeric,
                                       date,
                                       numeric,
                                       numeric,
                                       numeric)
;

CREATE OR REPLACE FUNCTION getCurrentCost(p_M_Product_ID     numeric,
                                          p_C_UOM_ID         numeric,
                                          p_Date             date,
                                          p_AcctSchema_ID    numeric,
                                          p_M_CostElement_Id numeric,
                                          p_AD_Client_ID     numeric,
                                          p_AD_Org_ID        numeric)
    RETURNS numeric
AS
$BODY$
WITH x AS
         (
             SELECT m_costdetail_ID,
                    cd.prev_currentcostprice,
                    cd.M_Product_ID,
                    COALESCE(mi.dateAcct,
                             mpo.dateAcct,
                             pp.dateAcct,
                             inv.movementDate,
                             m.MovementDate,
                             io.dateAcct,
                             NULL) AS dateAcct

             FROM m_costdetail cd

                      JOIN AD_Org org ON cd.ad_org_ID = org.ad_org_id


                      LEFT JOIN M_MatchInv mi ON mi.m_matchinv_id = cd.m_matchinv_id
                      LEFT JOIN M_MatchPO mpo ON mpo.M_MatchPO_ID = cd.m_MatchPO_ID
                      LEFT JOIN PP_Cost_Collector pp ON pp.PP_Cost_Collector_ID = cd.pp_cost_collector_id
                      LEFT JOIN m_inventoryline il ON il.m_inventoryline_id = cd.m_inventoryline_id
                      LEFT JOIN M_Inventory inv ON inv.m_inventory_id = il.m_inventory_id
                      LEFT JOIN M_MovementLine ml ON ml.m_movementline_id = cd.m_movementline_id
                      LEFT JOIN M_Movement m ON m.m_movement_id = ml.m_movement_ID
                      LEFT JOIN m_inoutline iol ON iol.m_inoutline_id = cd.m_inoutline_id
                      LEFT JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID

             WHERE cd.ischangingcosts = 'Y'
               AND cd.c_acctschema_id = p_acctSchema_ID
               AND cd.m_costelement_id = p_M_CostElement_Id
               AND cd.M_Product_ID = p_M_Product_ID
               AND cd.ad_client_id = p_AD_Client_ID
               AND cd.ad_org_ID = p_AD_Org_ID
         )


SELECT priceuomconvert(p.M_Product_ID,
                       COALESCE((
                                    SELECT prev_currentcostprice
                                    FROM x
                                    WHERE dateAcct > p_Date
                                    ORDER BY dateAcct
                                    LIMIT 1), cost.currentCostPrice),
                       p.c_uom_id,
                       p_c_uom_id,
                       currency.costingprecision:: integer)
           AS currentCostPrice

FROM M_Cost cost
         JOIN M_Product p ON cost.M_Product_ID = p.M_Product_ID
         JOIN C_AcctSchema sch ON cost.c_acctschema_id = sch.c_acctschema_id
         JOIN C_Currency currency ON sch.c_currency_id = currency.c_currency_id


WHERE cost.m_product_id = p_M_Product_ID
  AND cost.c_acctschema_id = p_acctSchema_ID
  AND cost.m_costelement_id = p_M_CostElement_Id
  AND cost.ad_client_id = p_AD_Client_ID
  AND cost.AD_Org_Id = p_AD_Org_ID;


$BODY$
    LANGUAGE SQL
    STABLE
;

DROP FUNCTION IF EXISTS report.getCostsPerDate(p_date                  timestamp WITH TIME ZONE,
                                        p_acctschema_id         numeric,
                                        p_ad_org_id             numeric,
                                        p_m_product_id          numeric,
                                        p_M_Product_Category_ID numeric
)
;

CREATE FUNCTION report.getCostsPerDate(p_date                  timestamp WITH TIME ZONE,
                                p_acctschema_id         numeric,
                                p_ad_org_id             numeric,
                                p_m_product_id          numeric = NULL,
                                p_M_Product_Category_ID numeric = NULL
)
    RETURNS TABLE
            (
                date                date,
                product             varchar,
                productCategory     varchar,
                costelement         varchar,
                cost                numeric,
                param_organization  varchar,
                param_product       varchar,
                param_product_categ varchar,
                param_acctSchema    varchar
            )
    LANGUAGE plpgsql
    VOLATILE
AS
$BODY$
DECLARE
    v_acctSchemaInfo record;
BEGIN

    --
    -- Fetch accounting info
    SELECT ac.ad_client_id,
           ce.m_costelement_id,
           ce.name AS CostElementName
    INTO v_acctSchemaInfo
    FROM c_acctschema ac
             JOIN m_costelement ce ON ce.costingmethod = ac.costingmethod
    WHERE ac.c_acctschema_id = p_acctschema_id;


    RETURN QUERY SELECT p_date::date                                                                                        AS keydate,
                        (p.value || '_' || p.name)::varchar                                                                 AS product,
                        pc.name                                                                                             AS productCategory,
                        v_acctSchemaInfo.CostElementName                                                                    AS costelement,
                        coalesce(getcurrentcost(p_m_product_id := p.m_product_id,
                                       p_c_uom_id := p.c_uom_id,
                                       p_date := p_date::date,
                                       p_acctschema_id := p_acctschema_id,
                                       p_m_costelement_id := v_acctSchemaInfo.m_costelement_id,
                                       p_ad_client_id := p.ad_client_id,
                                       p_ad_org_id := p_ad_org_id)   , 0)                                                       AS cost,
                        --
                        (SELECT o.name FROM ad_org o WHERE o.ad_org_id = p_ad_org_id)                                       AS param_organization,
                        (SELECT name FROM M_product pr WHERE pr.m_product_id = p_m_product_id)                              AS param_product,
                        (SELECT name FROM M_product_category pcr WHERE pcr.M_product_category_ID = p_M_Product_Category_ID) AS param_product_categ,
                        (SELECT name FROM c_acctschema a WHERE a.c_acctschema_id = p_acctschema_id)                         AS param_acctSchema
                 FROM M_product p
                          JOIN ad_org o ON p.ad_org_id = o.ad_org_id
                          JOIN M_product_category pc ON p.m_product_category_id = pc.m_product_category_id
                 WHERE p.isstocked = 'Y'
                   AND p.isactive = 'Y'
                   AND p.ad_client_id = v_acctSchemaInfo.ad_client_id
                   AND p.ad_org_id IN (0, p_ad_org_id)
                   AND (p_m_product_id IS NULL OR p.m_product_id = p_m_product_id)
                   AND (p_M_Product_Category_ID IS NULL OR p.m_product_category_id = p_M_Product_Category_ID)
                 ORDER BY p.value, p.name, p.m_product_id;
END;
$BODY$

;


/*
SELECT *
FROM report.getCostsPerDate(p_date :='01.01.2022'::date,
                     p_acctschema_id := 1000000,
                     p_ad_org_id := 1000000,
                     p_m_product_id := 2009018,
                     p_m_product_category_id := NULL)
;
*/