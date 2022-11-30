CREATE OR REPLACE FUNCTION formatQtyToDisplay(p_qty numeric ) RETURNS character varying
    LANGUAGE plpgsql
AS
$$
DECLARE
    precision      int;
BEGIN

    SELECT get_sysconfig_value('webui.frontend.widget.Quantity.defaultPrecision', '2')::INTEGER INTO precision;
    RETURN to_char(coalesce(p_qty,0), '99999990.'|| repeat('0', precision));

END;
$$
;

ALTER FUNCTION formatQtyToDisplay(p_qty numeric ) OWNER TO metasfresh
;
