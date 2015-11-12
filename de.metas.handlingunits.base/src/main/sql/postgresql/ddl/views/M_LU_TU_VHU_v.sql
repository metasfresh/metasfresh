
--ts: commented out the levels that never occur in our system
--as long as they are there, PostgreSQL will consider them in the plan
--and therefore e.g. do a seq scan instead of using an index

drop view if exists "de.metas.handlingunits".M_LU_TU_VHU_v;
create or replace view "de.metas.handlingunits".M_LU_TU_VHU_v
as
select
/**
 * View is fetching LU/TU/VirtualHU on the same ROW.
 */
	-- LU
	(case
		when p1_piv.HU_UnitType='LU' then p1_hu.M_HU_ID
		when p2_piv.HU_UnitType='LU' then p2_hu.M_HU_ID
		/*
		when p3_piv.HU_UnitType='LU' then p3_hu.M_HU_ID
		when p4_piv.HU_UnitType='LU' then p4_hu.M_HU_ID
		when p5_piv.HU_UnitType='LU' then p5_hu.M_HU_ID
		when p6_piv.HU_UnitType='LU' then p6_hu.M_HU_ID
		*/
		else null
	end) as M_LU_HU_ID
	-- TU
	, (case
		when p1_piv.HU_UnitType='TU' then p1_hu.M_HU_ID
		when p2_piv.HU_UnitType='TU' then p2_hu.M_HU_ID
		/*
		when p3_piv.HU_UnitType='TU' then p3_hu.M_HU_ID
		when p4_piv.HU_UnitType='TU' then p4_hu.M_HU_ID
		when p5_piv.HU_UnitType='TU' then p5_hu.M_HU_ID
		when p6_piv.HU_UnitType='TU' then p6_hu.M_HU_ID
		*/
		else null
	end) as M_TU_HU_ID
	-- Virtual HU
	, vhu.M_HU_ID as VHU_ID
	-- Top Level HU
	, COALESCE(/*p6_hu.M_HU_ID, p5_hu.M_HU_ID, p4_hu.M_HU_ID, p3_hu.M_HU_ID, */p2_hu.M_HU_ID, p1_hu.M_HU_ID, vhu.M_HU_ID) as TopLevel_HU_ID
	--
from M_HU vhu
inner join M_HU_PI_Version vhu_piv on (vhu_piv.M_HU_PI_Version_ID=vhu.M_HU_PI_Version_ID)
--
left outer join M_HU_Item p1_hui on (p1_hui.M_HU_Item_ID=vhu.M_HU_Item_Parent_ID)
left outer join M_HU p1_hu on (p1_hu.M_HU_ID=p1_hui.M_HU_ID)
left outer join M_HU_PI_Version p1_piv on (p1_piv.M_HU_PI_Version_ID=p1_hu.M_HU_PI_Version_ID)
--
left outer join M_HU_Item p2_hui on (p2_hui.M_HU_Item_ID=p1_hu.M_HU_Item_Parent_ID)
left outer join M_HU p2_hu on (p2_hu.M_HU_ID=p2_hui.M_HU_ID)
left outer join M_HU_PI_Version p2_piv on (p2_piv.M_HU_PI_Version_ID=p2_hu.M_HU_PI_Version_ID)
/*
left outer join M_HU_Item p3_hui on (p3_hui.M_HU_Item_ID=p2_hu.M_HU_Item_Parent_ID)
left outer join M_HU p3_hu on (p3_hu.M_HU_ID=p3_hui.M_HU_ID)
left outer join M_HU_PI_Version p3_piv on (p3_piv.M_HU_PI_Version_ID=p3_hu.M_HU_PI_Version_ID)
--
left outer join M_HU_Item p4_hui on (p4_hui.M_HU_Item_ID=p3_hu.M_HU_Item_Parent_ID)
left outer join M_HU p4_hu on (p4_hu.M_HU_ID=p4_hui.M_HU_ID)
left outer join M_HU_PI_Version p4_piv on (p4_piv.M_HU_PI_Version_ID=p4_hu.M_HU_PI_Version_ID)
--
left outer join M_HU_Item p5_hui on (p5_hui.M_HU_Item_ID=p4_hu.M_HU_Item_Parent_ID)
left outer join M_HU p5_hu on (p5_hu.M_HU_ID=p5_hui.M_HU_ID)
left outer join M_HU_PI_Version p5_piv on (p5_piv.M_HU_PI_Version_ID=p5_hu.M_HU_PI_Version_ID)
--
left outer join M_HU_Item p6_hui on (p6_hui.M_HU_Item_ID=p5_hu.M_HU_Item_Parent_ID)
left outer join M_HU p6_hu on (p6_hu.M_HU_ID=p6_hui.M_HU_ID)
left outer join M_HU_PI_Version p6_piv on (p6_piv.M_HU_PI_Version_ID=p6_hu.M_HU_PI_Version_ID)
*/
where vhu_piv.HU_UnitType='V'
;


COMMENT ON VIEW "de.metas.handlingunits".M_LU_TU_VHU_v IS '
In its results this view is similar to "de.metas.handlingunits".M_VHU_TU_LUv, but it searches/joins the other way around. 
In other words, *this* view starts at the VHU level and joins the higher-level HUs.
That means, for example if you have an VHU and need to join its TU, LU etc, then use this view.';


