-- Function to check if M_HU_PI_Item is TU level
CREATE OR REPLACE FUNCTION is_tu_level_packing(p_M_HU_PI_Item_ID numeric)
RETURNS boolean AS
$$
BEGIN
    RETURN EXISTS(
        SELECT 1
        FROM M_HU_PI_Item i, M_HU_PI_Version v
        WHERE i.M_HU_PI_Version_ID = v.M_HU_PI_Version_ID
          AND i.M_HU_PI_Item_ID = p_M_HU_PI_Item_ID
          AND v.HU_UnitType = 'TU'
    );
END;
$$
LANGUAGE plpgsql STABLE;
