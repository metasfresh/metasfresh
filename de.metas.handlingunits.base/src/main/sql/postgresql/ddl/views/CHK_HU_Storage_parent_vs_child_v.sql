CREATE OR REPLACE VIEW "de.metas.handlingunits".CHK_HU_Storage_parent_vs_child_v AS
select
	hu.AD_CLient_ID
	, hu.Value as HUValue
	, hu.M_HU_ID
	, hu.M_Locator_ID
	, hu.HUStatus
	, (select piv.HU_UnitType from M_HU_PI_Version piv where piv.M_HU_PI_Version_ID=hu.M_HU_PI_Version_ID) as HU_UnitType
	--
	, hus.M_Product_ID
	, hus.Qty as hus_Qty
	, hus.C_UOM_ID as hus_C_UOM_ID
	--
	, c_hus.*
from "de.metas.handlingunits".CHK_HU_Storage_AggOn_ParentHU_v c_hus 
	inner join M_HU_Storage hus on (hus.M_HU_Storage_ID=c_hus.Parent_HU_Storage_ID)
	inner join M_HU hu on (hu.M_HU_ID=c_hus.Parent_HU_ID)
;

COMMENT ON VIEW "de.metas.handlingunits".CHK_HU_Storage_parent_vs_child_v IS 'Check for HU storage inconsistencies between parent HU and child HU. Typical where:
select * from "de.metas.handlingunits".CHK_HU_Storage_parent_vs_child_v
where hus.Qty<>c_hus.Qty

NOTE: consider using a "materialized" version of the view CHK_HU_Storage_AggOn_ParentHU_v.'
;