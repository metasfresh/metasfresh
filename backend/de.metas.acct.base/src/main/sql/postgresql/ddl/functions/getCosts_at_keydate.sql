DROP FUNCTION IF EXISTS getCosts_at_keydate(p_m_product_id          numeric,
                                            p_keydate               timestamp WITH TIME ZONE,
                                            p_acctschema_id         numeric,
                                            p_ad_org_id             numeric,
                                            p_M_Product_Category_ID numeric
)
;

CREATE FUNCTION getCosts_at_keydate(p_m_product_id          numeric,
                                    p_keydate               timestamp WITH TIME ZONE,
                                    p_acctschema_id         numeric,
                                    p_ad_org_id             numeric,
                                    p_M_Product_Category_ID numeric = NULL
)
    RETURNS TABLE
            (
                keydate         date,
                product         varchar,
                productCategory varchar,
                costelement     varchar,
                cost            numeric,
                organization    varchar
            )
AS
$$
SELECT p_keydate::date                            AS keydate,
       p.value || '_' || p.name                   AS product,
       pc.name                                    AS productCategory,
       ce.name                                    AS costelement,
       getcurrentcost(p_m_product_id := p_m_product_id,
                      p_c_uom_id := p.c_uom_id,
                      p_date := p_keydate::date,
                      p_acctschema_id := p_acctschema_id,
                      p_m_costelement_id := ce.m_costelement_id,
                      p_ad_client_id := ac.ad_client_id,
                      p_ad_org_id := p_ad_org_id) AS cost,
       o.name                                     AS organization
FROM (M_product p
         JOIN ad_org o ON p.ad_org_id = o.ad_org_id
         JOIN M_product_category pc ON p.m_product_category_id = pc.m_product_category_id)
   , c_acctschema ac
         JOIN m_costelement ce ON ce.costingmethod = ac.costingmethod
WHERE p.isstocked = 'Y'
  AND (p.m_product_id = p_m_product_id OR p.m_product_category_id = p_M_Product_Category_ID)
  AND ac.c_acctschema_id = p_acctschema_id
ORDER BY p.value, p.name;

$$
    LANGUAGE sql VOLATILE
;
