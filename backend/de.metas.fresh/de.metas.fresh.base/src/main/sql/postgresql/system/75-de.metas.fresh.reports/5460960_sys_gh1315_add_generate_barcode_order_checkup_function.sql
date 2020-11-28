DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.generate_barcode_order_checkup(IN AD_Table_ID numeric, IN RECORD_ID numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.generate_barcode_order_checkup(IN AD_Table_ID numeric, IN RECORD_ID numeric)

RETURNS text
AS
$$ 
	SELECT '1_'||$1||'_'||$2 --'1_' is the version of this function. Each time the function is changed '1_' shall change into '2_' and so on
$$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.generate_barcode_order_checkup(numeric, numeric) IS 'first number of this function is the version. If you update the function please also change the version with +1';
