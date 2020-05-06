drop view if exists "de.metas.handlingunits".CHK_HU_Storage_AggOn_ParentHU_v;

--
-- View
create or replace view "de.metas.handlingunits".CHK_HU_Storage_AggOn_ParentHU_v
as
	select
	-- Parent HU
	p_hu.M_HU_ID as Parent_HU_ID
	, p_hu.HUStatus as Parent_HUStatus
	, p_hu.M_Locator_ID as Parent_Locator_ID
	, p_piv.HU_UnitType as Parent_UnitType
	, (select p_hus.M_HU_Storage_ID from M_HU_Storage p_hus where p_hus.M_HU_ID=p_hu.M_HU_ID and p_hus.M_Product_ID=hus.M_Product_ID) as Parent_HU_Storage_ID
	-- Aggregated children of parent HU
	, hus.M_Product_ID AS hus_M_Product_ID 
	, hus.C_UOM_ID
	, coalesce(sum(hus.Qty), 0) as Qty
	, count(*) as Count_HUs
	, min(hus.Updated) as HUS_Min_Updated
	, max(hus.Updated) as HUS_Max_Updated
	--
	from M_HU_Storage hus
	inner join M_HU hu on (hu.M_HU_ID=hus.M_HU_ID)
	inner join M_HU_Item p_hui on (p_hui.M_HU_Item_ID=hu.M_HU_Item_Parent_ID)
	inner join M_HU p_hu on (p_hu.M_HU_ID=p_hui.M_HU_ID)
	inner join M_HU_PI_Version p_piv on (p_piv.M_HU_PI_Version_ID=p_hu.M_HU_PI_Version_ID)
	group by 
	p_hu.M_HU_ID
	, p_hu.HUStatus
	, p_hu.M_Locator_ID
	, p_piv.M_HU_PI_Version_ID
	, hus.M_Product_ID
	, hus.C_UOM_ID
;
COMMENT ON VIEW "de.metas.handlingunits".CHK_HU_Storage_AggOn_ParentHU_v IS 'Compares the storge of a parent HU with the aggregated storages of its children. Typical use:

-- Materialize
drop table if exists "de.metas.handlingunits".CHK_HU_Storage_AggOn_ParentHU;
create table "de.metas.handlingunits".CHK_HU_Storage_AggOn_ParentHU as select * from "de.metas.handlingunits".CHK_HU_Storage_AggOn_ParentHU_v;

-- Indexes for fast search
drop index if exists CHK_HU_Storage_AggOn_ParentHU_Parent_HU_ID;
create index CHK_HU_Storage_AggOn_ParentHU_Parent_HU_ID on "de.metas.handlingunits".CHK_HU_Storage_AggOn_ParentHU (Parent_HU_ID, M_Product_ID);
--
drop index if exists CHK_HU_Storage_AggOn_Parent_HU_Storage_ID;
create index CHK_HU_Storage_AggOn_Parent_HU_Storage_ID on "de.metas.handlingunits".CHK_HU_Storage_AggOn_ParentHU (Parent_HU_Storage_ID);
';