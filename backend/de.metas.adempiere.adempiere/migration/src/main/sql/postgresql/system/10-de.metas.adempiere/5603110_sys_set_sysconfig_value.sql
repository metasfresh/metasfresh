DROP FUNCTION IF EXISTS set_sysconfig_value(
    p_name  varchar,
    p_value varchar
)
;


CREATE OR REPLACE FUNCTION set_sysconfig_value(
    name     varchar,
    newValue varchar
)
    RETURNS void
AS
$BODY$
DECLARE
    v_previousValue varchar;
    v_sysconfig_id  numeric;
BEGIN
    SELECT c.value, ad_sysconfig_id
    INTO v_previousValue, v_sysconfig_id
    FROM ad_sysconfig c
    WHERE c.name = set_sysconfig_value.name;

    IF (v_sysconfig_id IS NULL) THEN
        RAISE EXCEPTION 'No sysconfig found for `%`', set_sysconfig_value.name;
    END IF;

    UPDATE ad_sysconfig c
    SET value=set_sysconfig_value.newValue,
        updated=NOW(),
        updatedby=99
    WHERE c.ad_sysconfig_id = v_sysconfig_id;

    RAISE INFO 'Changed `%` from `%` to `%`',
        set_sysconfig_value.name, v_previousValue, set_sysconfig_value.newValue;
END;
$BODY$
    LANGUAGE plpgsql
;

