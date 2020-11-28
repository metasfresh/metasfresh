
CREATE VIEW "de.metas.handlingunits".CHK_HU_Storage_vs_Aggregated_TrxLines AS
select 
hus.M_HU_ID, hus.M_Product_ID, hu.M_Locator_ID
, hu.HUStatus
, hu.IsActive
, (select piv.HU_UnitType from M_HU_PI_Version piv where piv.M_HU_PI_Version_ID=hu.M_HU_PI_Version_ID) as HU_UnitType
--
, hus.Qty, hus.C_UOM_ID
, hus.Updated
--
, hus2.Qty as TL_AGG_Qty
, hus2.C_UOM_ID as TL_AGG_UOM_ID
, hus2.Min_Updated as hus2_TL_AGG_Min_Updated
, hus2.Max_Updated as hus2_TL_AGG_Max_Updated
, hus2.Count_TrxLines as TL_AGG_Count_TrxLines
from M_HU_Storage hus
	inner join M_HU hu on (hu.M_HU_ID=hus.M_HU_ID)
	inner join "de.metas.handlingunits".CHK_HU_Trx_Line_AggOn_VHU_v hus2 on (hus2.M_HU_ID=hus.M_HU_ID
		and hus2.M_Product_ID=hus.M_Product_ID
		and hus2.M_Locator_ID=hu.M_Locator_ID)
where true;


COMMENT ON VIEW "de.metas.handlingunits".CHK_HU_Storage_vs_Aggregated_TrxLines IS 'Compared the qtys from M_HU_Storage with those from HU_Trx_Line. Typical use:

SELECT * 
FROM "de.metas.handlingunits".CHK_HU_Storage_vs_Aggregated_TrxLines
WHERE true
	and hu.HUStatus not in (''D'')
	and (
		hus.Qty<>hus2.Qty
		or hus.C_UOM_ID <> hus2.C_UOM_ID
	);

NOTE: consider using a "materialized" version of the view CHK_HU_Trx_Line_AggOn_VHU_v.'
;
