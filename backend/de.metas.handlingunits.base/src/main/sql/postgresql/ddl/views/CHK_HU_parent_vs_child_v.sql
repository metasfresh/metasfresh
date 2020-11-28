CREATE OR REPLACE VIEW "de.metas.handlingunits".CHK_HU_parent_vs_child_v AS
select 
	-- HU
	hu.AD_Client_ID
	, hu.M_HU_ID
	, hu.M_Locator_ID
	, (select piv.HU_UnitType from M_HU_PI_Version piv where piv.M_HU_PI_Version_ID=hu.M_HU_PI_Version_ID) as HU_UnitType
	, hu.HUStatus
	, hu.IsActive
	, hu.Updated
	--
	-- Parent HU
	, p_hu.AD_Client_ID as Parent_AD_Client_ID
	, p_hu.M_HU_ID as Parent_HU_ID
	, p_hu.M_Locator_ID as Parent_Locator_ID
	, (select piv.HU_UnitType from M_HU_PI_Version piv where piv.M_HU_PI_Version_ID=p_hu.M_HU_PI_Version_ID) as Parent_HU_UnitType
	, p_hu.HUStatus as Parent_HUStatus
	, p_hu.IsActive as Parent_IsActive
	, p_hu.Updated as Parent_Updated
from M_HU hu
	inner join M_HU_Item p_hui on (p_hui.M_HU_Item_ID=hu.M_HU_Item_Parent_ID)
	inner join M_HU p_hu on (p_hu.M_HU_ID=p_hui.M_HU_ID)
where true;

COMMENT ON VIEW "de.metas.handlingunits".CHK_HU_parent_vs_child_v IS 'View can be used to spot inconsitencies between a child and a parent HU. Example where:
SELECT * from "de.metas.handlingunits".CHK_HU_parent_vs_child_v
WHERE false
		or hu.IsActive<>p_hu.IsActive
		or hu.HUStatus<>p_hu.HUStatus
		or hu.M_Locator_ID<>p_hu.M_Locator_ID;
';