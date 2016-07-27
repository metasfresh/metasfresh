drop function if exists de_metas_procurement.getMonthlyQtyPromised
(
	p_MonthDate timestamp -- 1
	, p_C_BPartner_ID numeric -- 2
	, p_M_Product_ID numeric -- 3
	, p_M_AttributeSetInstance_ID numeric -- 4
	, p_C_Flatrate_DataEntry_ID numeric -- 5
);

create or replace function de_metas_procurement.getMonthlyQtyPromised
(
	p_MonthDate timestamp -- 1
	, p_C_BPartner_ID numeric -- 2
	, p_M_Product_ID numeric -- 3
	, p_M_AttributeSetInstance_ID numeric -- 4
	, p_C_Flatrate_DataEntry_ID numeric -- 5
)
returns numeric
as
$BODY$
	select COALESCE(SUM(qtypromised), 0)
	from PMM_Balance b
	where true
	and b.C_BPartner_ID=$2
	and b.M_Product_ID=$3
	and ($4 is null or b.M_AttributeSetInstance_ID=$4)
	and b.MonthDate=date_trunc('month', $1)
	and b.WeekDate is null
	and (b.C_Flatrate_DataEntry_ID IS NULL OR b.C_Flatrate_DataEntry_ID = $5)
	limit 1

$BODY$
LANGUAGE sql STABLE;



/* TEST:
select * from (
	select
		b.MonthDate
		, b.WeekDate
		,b.C_BPartner_ID
		,b.M_Product_ID
		,b.M_AttributeSetInstance_ID
		,b.C_Flatrate_DataEntry_ID
		, b.qtypromised
		, de_metas_procurement.getMonthlyQtyPromised(
			p_MonthDate := b.MonthDate
			, p_C_BPartner_ID := b.C_BPartner_ID
			, p_M_Product_ID := b.M_Product_ID
			, p_M_AttributeSetInstance_ID := b.M_AttributeSetInstance_ID
			, p_C_Flatrate_DataEntry_ID := b.C_Flatrate_DataEntry_ID
		) as QtyPromised_Calc
	from PMM_Balance b
	where true
	and b.WeekDate is null
) t
where true
and t.qtypromised is distinct from t.QtyPromised_Calc
;
*/
