CREATE OR REPLACE FUNCTION formatQtyToDisplay(p_qty numeric) RETURNS character varying
    LANGUAGE plpgsql
AS
$$
DECLARE
    precision int;
BEGIN
    SELECT get_sysconfig_value('webui.frontend.widget.Quantity.defaultPrecision', '2')::INTEGER INTO precision;
    RETURN TO_CHAR(COALESCE(p_qty, 0), '99999990.' || REPEAT('0', precision));
END;
$$
;

ALTER FUNCTION formatQtyToDisplay(p_qty numeric ) OWNER TO metasfresh
;

COMMENT ON FUNCTION formatQtyToDisplay(p_qty numeric) IS
    'This function formats the provided qty as a parameter using the webui.frontend.widget.Quantity.defaultPrecision SysConfig'
;
