drop function if exists de_metas_procurement.getPMM_PurchaseCandidate_Weekly(p_candidate PMM_PurchaseCandidate, p_WeekOffset integer);
drop view if exists PMM_PurchaseCandidate_Weekly;

create or replace view PMM_PurchaseCandidate_Weekly as
select
	t.*
	, (select PMM_Trend from PMM_Week w
		where w.C_BPartner_ID=t.C_BPartner_ID
			and w.M_Product_ID=t.M_Product_ID
			and w.M_HU_PI_Item_Product_ID is not distinct from t.M_HU_PI_Item_Product_ID
			and w.WeekDate=t.WeekDate
		) as PMM_Trend
from (
	select
		b.C_BPartner_ID
		, b.M_Product_ID
		, b.M_HU_PI_Item_Product_ID
		, b.C_Flatrate_DataEntry_ID
		--
		, b.MonthDate
		, b.WeekDate
		--
		, b.QtyPromised
		, b.QtyPromised_TU
		, b.QtyOrdered
		, b.QtyOrdered_TU
		, b.QtyDelivered
		--
		, b.AD_Client_ID
		, b.AD_Org_ID
		, b.Created
		, b.CreatedBy
		, b.Updated
		, b.UpdatedBy
		, b.IsActive
	from PMM_Balance b
	where true
		and b.WeekDate is not null
) t
;

-- select * from PMM_PurchaseCandidate_Weekly







-- drop function if exists de_metas_procurement.getPMM_PurchaseCandidate_Weekly(p_candidate PMM_PurchaseCandidate, p_WeekOffset integer);
create or replace function de_metas_procurement.getPMM_PurchaseCandidate_Weekly(p_candidate PMM_PurchaseCandidate, p_WeekOffset integer default 0)
returns PMM_PurchaseCandidate_Weekly
as
$BODY$
	select w.*
	from PMM_PurchaseCandidate_Weekly w
	where true
	and w.C_BPartner_ID=($1).C_BPartner_ID
	and w.M_Product_ID=($1).M_Product_ID
	and w.M_HU_PI_Item_Product_ID IS NOT DISTINCT FROM ($1).M_HU_PI_Item_Product_ID
	and w.C_Flatrate_DataEntry_ID IS NOT DISTINCT FROM ($1).C_Flatrate_DataEntry_ID
	and w.WeekDate=date_trunc('week', ($1).DatePromised) + $2 * interval '1 week'
	limit 1
	;
$BODY$
LANGUAGE sql STABLE;

