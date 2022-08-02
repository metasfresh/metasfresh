DROP FUNCTION IF EXISTS "de.metas.handlingunits".huInfo(
    p_M_HU_ID numeric
)
;

DROP FUNCTION IF EXISTS "de.metas.handlingunits".huInfo(
    p_M_HU_ID     numeric,
    p_storageInfo boolean
)
;


CREATE OR REPLACE FUNCTION "de.metas.handlingunits".huInfo(
    p_M_HU_ID     numeric,
    p_storageInfo boolean = FALSE
)
    RETURNS varchar
AS
$BODY$
    /*
     * Function generates a summary information about given HU
     */
DECLARE
    huInfo        RECORD;
    huStorageInfo text;
    result        varchar;
BEGIN
    SELECT hu.M_HU_ID
         , hu.Value
         , hu.HUStatus
         , hu.IsActive
         , piv.HU_UnitType
         , pi.Name  AS PIName
         , bp.Value AS BP_Value
         , wh.Name  AS WH_Name
    INTO huInfo
    FROM M_HU hu
             LEFT OUTER JOIN M_HU_PI_Version piv ON (piv.M_HU_PI_Version_ID = hu.M_HU_PI_Version_ID)
             LEFT OUTER JOIN M_HU_PI pi ON (pi.M_HU_PI_ID = piv.M_HU_PI_ID)
             LEFT OUTER JOIN C_BPartner bp ON (bp.C_BPartner_ID = hu.C_BPartner_ID)
             LEFT OUTER JOIN M_Locator loc ON (loc.M_Locator_ID = hu.M_Locator_ID)
             LEFT OUTER JOIN M_Warehouse wh ON (wh.M_Warehouse_ID = loc.M_Warehouse_ID)
    WHERE hu.M_HU_ID = p_M_HU_ID;

    IF huInfo.M_HU_ID IS NULL THEN
        RETURN 'ID=' || p_M_HU_ID || ' (not found)';
    END IF;


    result := huInfo.M_HU_ID || '('
                  || 'PI:' || huInfo.PIName
                  || ', IsActive:' || huInfo.IsActive
                  || ', HUStatus:' || huInfo.HUStatus
                  || ', UnitType:' || COALESCE(huInfo.HU_UnitType, '-')
                  || ', BP:' || COALESCE(huInfo.BP_Value, '-')
                  || ', WH:' || COALESCE(huInfo.WH_Name, '-')
        || ')';

    IF (p_storageInfo) THEN
        SELECT STRING_AGG(p.Name || ' ' || hus.qty::real || ' ' || uom.uomsymbol, ', ')
        INTO huStorageInfo
        FROM m_hu_storage hus
                 INNER JOIN m_product p ON p.m_product_id = hus.m_product_id
                 INNER JOIN c_uom uom ON uom.c_uom_id = hus.c_uom_id
        WHERE hus.m_hu_id = p_M_HU_ID;

        IF (huStorageInfo IS NOT NULL AND huStorageInfo != '') THEN
            result := result || ' | ' || huStorageInfo;
        END IF;
    END IF;

    RETURN result;
END;
$BODY$
    LANGUAGE plpgsql STABLE
                     COST 100
;
--

