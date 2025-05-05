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