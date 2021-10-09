drop view if exists M_DiscountSchemaBreak_V;

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
      (m_product_id*3+m_discountschema_id) as m_discountschemabreak_v_id
from (
         select distinct ad_client_id,
                         ad_org_id,
                         isactive,
                         m_product_id,
                         m_discountschema_id
         from m_discountschemabreak
		 where isactive='Y'
     ) as d
order by m_discountschema_id;
