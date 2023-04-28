DROP VIEW RV_CostDetail
;

CREATE OR REPLACE VIEW RV_CostDetail AS
SELECT c.ad_client_id,
       c.ad_org_id,
       c.isactive,
       c.created,
       c.createdby,
       c.updated,
       c.updatedby,
       p.m_product_id,
       p.value,
       p.name,
       p.upc,
       p.isbom,
       p.producttype,
       p.m_product_category_id,
       c.m_inoutline_id,
       c.c_invoiceline_id,
       asi.m_attributesetinstance_id,
       asi.m_attributeset_id,
       acct.c_acctschema_id,
       acct.c_currency_id,
       c.amt,
       c.qty,
       c.description,
       c.processed
FROM m_costdetail c
         JOIN m_product p ON c.m_product_id = p.m_product_id
         JOIN c_acctschema acct ON c.c_acctschema_id = acct.c_acctschema_id
         JOIN m_attributesetinstance asi ON c.m_attributesetinstance_id = asi.m_attributesetinstance_id;