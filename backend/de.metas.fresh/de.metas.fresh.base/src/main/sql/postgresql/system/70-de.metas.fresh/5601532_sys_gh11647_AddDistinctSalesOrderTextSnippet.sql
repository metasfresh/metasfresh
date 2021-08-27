DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getOrderTextBoilerPlate(numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getOrderTextBoilerPlate(IN p_C_Order_ID numeric)
    RETURNS TABLE
            (
                Subject           character varying,
                textsnippet       text,
                ad_boilerplate_id numeric
            )
AS
$$

SELECT distinct subject, textsnippet, ad_boilerplate_id
FROM (
-- textsnippet for products
         SELECT subject, textsnippet, ad_boilerplate_id
         FROM (
                  SELECT DISTINCT b.subject, b.textsnippet, b.ad_boilerplate_id, cb.SeqNo
                  FROM C_OrderLine ol
                           JOIN M_Product p on ol.m_product_id = p.m_product_id
                           JOIN ad_product_category_boilerplate cb on p.M_Product_Category_ID = cb.M_Product_Category_ID
                           JOIN ad_boilerplate b on cb.ad_boilerplate_id = b.ad_boilerplate_id
                  WHERE ol.C_Order_ID = p_C_Order_ID
                  ORDER BY cb.SeqNo
              ) as A
         UNION ALL

-- textsnippet for document
         SELECT subject, textsnippet, ad_boilerplate_id
         FROM (
                  SELECT DISTINCT b.subject, b.textsnippet, b.ad_boilerplate_id, db.SeqNo
                  FROM C_Order o
                           JOIN ad_doctype_boilerplate db on db.C_doctype_ID = o.C_doctype_ID
                           JOIN ad_boilerplate b on db.ad_boilerplate_id = b.ad_boilerplate_id
                  WHERE o.C_Order_ID = p_C_Order_ID
                  ORDER BY db.SeqNo
              ) AS B
     ) AS textsnippet
$$
    LANGUAGE sql STABLE
;
