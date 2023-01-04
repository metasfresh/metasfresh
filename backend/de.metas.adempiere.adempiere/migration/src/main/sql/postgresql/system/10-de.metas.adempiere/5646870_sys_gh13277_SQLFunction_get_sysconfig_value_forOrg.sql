
CREATE OR REPLACE FUNCTION get_sysconfig_value_forOrg(
    name           varchar,
    p_defaultValue varchar = NULL,
    p_AD_Org_ID    numeric = NULL
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
    WHERE c.name = get_sysconfig_value_forOrg.name
      AND c.ad_org_id IN (p_AD_Org_ID, 0)
      AND c.IsActive='Y'
    ORDER BY AD_Org_ID desc
    limit 1;


    IF (v_sysconfig_id IS NULL) THEN
        RAISE WARNING 'No sysconfig found for `%`. Returning default: %', get_sysconfig_value_forOrg.name, p_defaultValue;
        RETURN p_defaultValue;
    END IF;

    IF (v_value IS NULL OR TRIM(v_value) = '' OR v_value = TRIM('-')) THEN
        RAISE NOTICE 'Sysconfig `%` has empty value (i.e. `%`). Returning default: %', get_sysconfig_value_forOrg.name, v_value, p_defaultValue;
        RETURN p_defaultValue;
    END IF;

    RETURN v_value;
END;
$BODY$
    LANGUAGE plpgsql
;


-- Example
-- SELECT get_sysconfig_value_forOrg('C_Invoice_Send_To_Current_BillTo_Address_And_User', 'N', 1000000)


COMMENT ON FUNCTION get_sysconfig_value_forOrg(VARCHAR, VARCHAR, NUMERIC) IS 'Retrieve the sys config value for the given org, falling back on org 0.
Example:  SELECT get_sysconfig_value_forOrg(''C_Invoice_Send_To_Current_BillTo_Address_And_User'', ''N'', 1000000)';