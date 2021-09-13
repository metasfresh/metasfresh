DROP FUNCTION IF EXISTS get_sysconfig_value(
    p_name         varchar,
    p_defaultValue varchar
)
;


CREATE OR REPLACE FUNCTION get_sysconfig_value(
    name           varchar,
    p_defaultValue varchar = NULL
)
    RETURNS varchar
AS
$BODY$
DECLARE
    v_value        varchar;
    v_sysconfig_id numeric;
BEGIN
    SELECT c.value, ad_sysconfig_id
    INTO v_value, v_sysconfig_id
    FROM ad_sysconfig c
    WHERE c.name = get_sysconfig_value.name;

    IF (v_sysconfig_id IS NULL) THEN
        RAISE WARNING 'No sysconfig found for `%`. Returning default: %', get_sysconfig_value.name, p_defaultValue;
        RETURN p_defaultValue;
    END IF;

    IF (v_value IS NULL OR TRIM(v_value) = '' OR v_value = TRIM('-')) THEN
        RAISE NOTICE 'Sysconfig `%` has empty value (i.e. `%`). Returning default: %', get_sysconfig_value.name, v_value, p_defaultValue;
        RETURN p_defaultValue;
    END IF;

    RETURN v_value;
END;
$BODY$
    LANGUAGE plpgsql
;