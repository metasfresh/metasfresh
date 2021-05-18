select distinct M_DiscountSchemaBreak_V_id from M_DiscountSchemaBreak_V;

create or replace view M_DiscountSchemaBreak_V AS
select now() as created,
       now() as updated,
       99    as createdby,
       99    as updatedby,
       ad_client_id,
       ad_org_id,
       isactive,
       m_product_id,
       m_discountschema_id,
       m_discountschemabreak_v_id
from (
         select distinct ad_client_id,
                         ad_org_id,
                         isactive,
                         m_product_id,
                         m_discountschema_id,
                         max(m_discountschemabreak_id) as m_discountschemabreak_v_id
         from m_discountschemabreak
         where isactive = 'Y'
         group by ad_client_id,
                  ad_org_id,
                  isactive,
                  m_product_id,
                  m_discountschema_id
     ) as d
order by m_discountschema_id;
