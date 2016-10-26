drop function if exists "de.metas.handlingunits".huInfo(numeric);
CREATE OR REPLACE FUNCTION "de.metas.handlingunits".huInfo(p_M_HU_ID numeric)
RETURNS varchar
AS
$BODY$
/*
 * Function gererates a summary information about given HU
 */
DECLARE
	huInfo RECORD;
	huInfoStr varchar;
BEGIN
	huInfoStr := null;
	for huInfo in ( 
		SELECT
			hu.M_HU_ID, hu.Value, hu.HUStatus, hu.IsActive
			, piv.HU_UnitType
			, pi.Name as PIName
			, bp.Value as BP_Value
			, wh.Name as WH_Name
		FROM M_HU hu
		LEFT OUTER JOIN M_HU_PI_Version piv ON (piv.M_HU_PI_Version_ID=hu.M_HU_PI_Version_ID)
		LEFT OUTER JOIN M_HU_PI pi ON (pi.M_HU_PI_ID=piv.M_HU_PI_ID)
		LEFT OUTER JOIN C_BPartner bp ON (bp.C_BPartner_ID=hu.C_BPartner_ID)
		LEFT OUTER JOIN M_Locator loc ON (loc.M_Locator_ID=hu.M_Locator_ID)
		LEFT OUTER JOIN M_Warehouse wh ON (wh.M_Warehouse_ID=loc.M_Warehouse_ID)
		where hu.M_HU_ID=p_M_HU_ID
	)
	loop
		huInfoStr := huInfo.Value || '('
			||'PI:'||huInfo.PIName
			||', IsActive:'||huInfo.IsActive
			||', HUStatus:'||huInfo.HUStatus
			||', UnitType:'||COALESCE(huInfo.HU_UnitType, '-')
			||', ID:'||huInfo.M_HU_ID
			||', BP:'||COALESCE(huInfo.BP_Value, '-')
			||', WH:'||COALESCE(huInfo.WH_Name, '-')
			||')';
	end loop;
	
	-- Case: no HU was found
	if (huInfoStr is null) then
		huInfoStr := 'ID='||p_M_HU_ID||' (not found)';
	end if;
	
	return huInfoStr;
END;
$BODY$
	LANGUAGE plpgsql STABLE
	COST 100;
--

