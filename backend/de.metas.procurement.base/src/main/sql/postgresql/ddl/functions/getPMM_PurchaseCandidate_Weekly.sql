drop function if exists de_metas_procurement.getPMM_PurchaseCandidate_Weekly(p_candidate PMM_PurchaseCandidate, p_WeekOffset integer);

create or replace function de_metas_procurement.getPMM_PurchaseCandidate_Weekly(p_candidate PMM_PurchaseCandidate, p_WeekOffset integer default 0)
returns PMM_PurchaseCandidate_Weekly
as
$BODY$
	select w.*
	from PMM_PurchaseCandidate_Weekly w
	where true
	and w.C_BPartner_ID=($1).C_BPartner_ID
	and w.M_Product_ID=($1).M_Product_ID
	and w.M_AttributeSetInstance_ID IS NOT DISTINCT FROM ($1).M_AttributeSetInstance_ID
	-- and w.M_HU_PI_Item_Product_ID IS NOT DISTINCT FROM ($1).M_HU_PI_Item_Product_ID
	and w.C_Flatrate_DataEntry_ID IS NOT DISTINCT FROM ($1).C_Flatrate_DataEntry_ID
	and w.WeekDate=date_trunc('week', ($1).DatePromised) + $2 * interval '1 week'
	limit 1
	;
$BODY$
LANGUAGE sql STABLE;

