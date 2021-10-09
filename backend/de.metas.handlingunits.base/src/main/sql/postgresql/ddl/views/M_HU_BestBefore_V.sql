drop view if exists M_HU_BestBefore_V;
create or replace view M_HU_BestBefore_V as
select
    hua_Expired_M_HU_Attribute_ID as M_HU_BestBefore_V_ID
     , t.M_HU_ID
     , t.HU_BestBeforeDate
     , max(t.GuaranteeDaysMin) as GuaranteeDaysMin
     , t.HU_BestBeforeDate - max(t.GuaranteeDaysMin) as HU_ExpiredWarnDate
     , t.HU_Expired
     --
     , AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy
from (
         select
             hua.M_HU_ID
              , hua.ValueDate as HU_BestBeforeDate
              , (case
                     when p.GuaranteeDaysMin > 0 then p.GuaranteeDaysMin
                     when pc.GuaranteeDaysMin > 0 then pc.GuaranteeDaysMin
                     else null
                 end) as GuaranteeDaysMin
              , hua_Expired.Value as HU_Expired
              , hua_Expired.M_HU_Attribute_ID as hua_Expired_M_HU_Attribute_ID
              -- , hus.M_Product_ID, p.M_Product_Category_ID, hua.M_HU_Attribute_ID, hua.M_Attribute_ID
              --
              , hu.AD_Client_ID, hu.AD_Org_ID, hu.IsActive, hu.Created, hu.CreatedBy, hu.Updated, hu.UpdatedBy
         from M_HU_Attribute hua
                  inner join M_HU hu on (hu.M_HU_ID=hua.M_HU_ID)
                  inner join M_HU_Storage hus on (hus.M_HU_ID=hua.M_HU_ID)
                  inner join M_Product p on (p.M_Product_ID=hus.M_Product_ID)
                  inner join M_Product_Category pc on (pc.M_Product_Category_ID=p.M_Product_Category_ID)
                  inner join M_HU_Attribute hua_Expired on (
                     hua_Expired.M_HU_ID=hua.M_HU_ID
                 and hua_Expired.M_Attribute_ID=(select a.M_Attribute_ID from M_Attribute a where a.Value='HU_Expired')
             -- and (hua_Expired.Value is null or hua_Expired.Value<>'expired')
             )
         where true
           and hua.M_Attribute_ID=(select a.M_Attribute_ID from M_Attribute a where a.Value='HU_BestBeforeDate')
           and hua.ValueDate is not null
           and hu.HUStatus='A' -- Active
     ) t
where t.GuaranteeDaysMin is not null
group by
    hua_Expired_M_HU_Attribute_ID
       , t.M_HU_ID
       , t.HU_BestBeforeDate
       , t.HU_Expired
       , AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy
;


/*
alter table M_Product_Category drop column GuaranteeDaysMin;
SELECT public.db_alter_table('M_Product_Category','ALTER TABLE public.M_Product_Category ADD COLUMN GuaranteeDaysMin NUMERIC(10)') ;

update M_Product_Category set GuaranteeDaysMin=3 where M_Product_Category_ID=2001580;
update M_Product_Category set GuaranteeDaysMin=null where M_Product_Category_ID=2001580;
update M_Product set GuaranteeDaysMin=5 where M_Product_ID=2005572;
update M_Product set GuaranteeDaysMin=null where M_Product_ID=2005572;
*/
/*
select
	"de.metas.handlingunits".huInfo(t.M_HU_ID) as huInfo
	, trunc(now(), 'DD') as now
	-- , (select p.Value||'_'||p.name from M_product p where p.M_Product_ID=t.M_Product_ID) as product
	-- , (select p.Value||'_'||p.name from M_product_Category p where p.M_product_Category_ID=t.M_product_Category_ID) as productCategory
	, t.*
from M_HU_BestBefore_V t
;
*/
