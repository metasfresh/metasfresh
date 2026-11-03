drop view if exists M_HU_BestBefore_V;
create or replace view M_HU_BestBefore_V as
select
	t.M_HU_ID
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
	t.M_HU_ID
	, t.HU_BestBeforeDate
	, t.HU_Expired
	, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy
;

