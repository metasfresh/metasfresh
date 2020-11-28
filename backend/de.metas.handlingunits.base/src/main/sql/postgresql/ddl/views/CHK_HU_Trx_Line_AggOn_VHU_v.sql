
drop view if exists "de.metas.handlingunits".CHK_HU_Trx_Line_AggOn_VHU_v;

-- View
create or replace view "de.metas.handlingunits".CHK_HU_Trx_Line_AggOn_VHU_v
as
	select 
		vhu_s.M_HU_ID
		, tl.M_Locator_ID
		, tl.M_Product_ID
		, tl.C_UOM_ID
		, sum(tl.Qty) as Qty
		, min(tl.Updated) as Min_Updated
		, max(tl.Updated) as Max_Updated
		, count(1) as Count_TrxLines
	from M_HU_Trx_Line tl
		inner join M_HU_Item vhui on (vhui.M_HU_Item_ID=tl.VHU_Item_ID)
		inner join M_HU_Storage vhu_s on (vhu_s.M_HU_ID=vhui.M_HU_ID)
	where true
		AND tl.huStatus IN ('A', 'S') -- only display transactions if status is stocked, A = Active, S = Picked
	group by
		vhu_s.M_HU_ID
		, tl.M_Locator_ID
		, tl.M_Product_ID
		, tl.C_UOM_ID
;

COMMENT ON VIEW "de.metas.handlingunits".CHK_HU_Trx_Line_AggOn_VHU_v IS 'Aggregated the M_HU_Trx_Lines for a given VHU. Typical use:

-- Materialize
drop table if exists "de.metas.handlingunits".CHK_HU_Trx_Line_AggOn_VHU;
create table "de.metas.handlingunits".CHK_HU_Trx_Line_AggOn_VHU as select * from "de.metas.handlingunits".CHK_HU_Trx_Line_AggOn_VHU_v;

-- Indexes for fast search
drop index if exists "de.metas.handlingunits".CHK_HU_Trx_Line_AggOn_VHU_HU;
create index CHK_HU_Trx_Line_AggOn_VHU_HU on "de.metas.handlingunits".CHK_HU_Trx_Line_AggOn_VHU (M_HU_ID, M_Product_ID, M_Locator_ID);
';
