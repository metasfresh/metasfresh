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
                keydate             date,
                product             varchar,
                productCategory     varchar,
                costelement         varchar,
                cost                numeric,
                param_organization  varchar,
                param_product       varchar,
                param_product_categ varchar,
                param_acctSchema    varchar
            )
AS
$$
SELECT p_date::date                                                                                        AS keydate,
       p.value || '_' || p.name                                                                            AS product,
       pc.name                                                                                             AS productCategory,
       ce.name                                                                                             AS costelement,
       getcurrentcost(p_m_product_id := p.m_product_id,
                      p_c_uom_id := p.c_uom_id,
                      p_date := p_date::date,
                      p_acctschema_id := p_acctschema_id,
                      p_m_costelement_id := ce.m_costelement_id,
                      p_ad_client_id := ac.ad_client_id,
                      p_ad_org_id := p_ad_org_id)                                                          AS cost,
       (SELECT o.name FROM ad_org o WHERE o.ad_org_id = p_ad_org_id)                                       AS param_organization,
       (SELECT name FROM M_product pr WHERE pr.m_product_id = p_m_product_id)                              AS param_product,
       (SELECT name FROM M_product_category pcr WHERE pcr.M_product_category_ID = p_M_Product_Category_ID) AS param_product_categ,
       (SELECT name FROM c_acctschema a WHERE a.c_acctschema_id = p_acctschema_id)                         AS param_acctSchema
FROM (M_product p
         JOIN ad_org o ON p.ad_org_id = o.ad_org_id
         JOIN M_product_category pc ON p.m_product_category_id = pc.m_product_category_id)
   , c_acctschema ac
         JOIN m_costelement ce ON ce.costingmethod = ac.costingmethod
WHERE p.isstocked = 'Y'
  AND p.isactive = 'Y'
  AND (CASE WHEN p_m_product_id IS NULL THEN TRUE ELSE p.m_product_id = p_m_product_id END)
  AND (CASE WHEN p_M_Product_Category_ID IS NULL THEN TRUE ELSE p.m_product_category_id = p_M_Product_Category_ID END)

  AND ac.c_acctschema_id = p_acctschema_id
  AND p.ad_org_id = p_ad_org_id
ORDER BY p.value, p.name;

$$
    LANGUAGE sql VOLATILE
;
