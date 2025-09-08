DROP FUNCTION IF EXISTS getColor_ID_By_SysConfig(
    p_SysConfigName varchar
)
;


CREATE OR REPLACE FUNCTION getColor_ID_By_SysConfig(
    p_SysConfigName varchar
)
    RETURNS numeric
    LANGUAGE plpgsql
    STABLE
AS
$$
DECLARE
    v_colorName   varchar;
    v_AD_Color_ID numeric;
BEGIN
    v_colorName := get_sysconfig_value(p_SysConfigName, NULL);

    IF (v_colorName IS NULL OR v_colorName = '-') THEN
        RAISE NOTICE 'No sysconfig `%` or the sysconfig has no color name set', p_SysConfigName;
        RETURN NULL;
    END IF;

    SELECT c.ad_color_id
    INTO v_AD_Color_ID
    FROM ad_color c
    WHERE c.name = v_colorName
      AND c.isactive = 'Y'
    ORDER BY c.ad_color_id -- just to have a predictable order
    LIMIT 1;

    IF (v_AD_Color_ID IS NULL) THEN
        RAISE WARNING 'No AD_Color_ID found for Name=`%` (SysConfig: %)', v_colorName, p_SysConfigName;
        RETURN NULL;
    END IF;

    RETURN v_AD_Color_ID;
END;
$$
;


