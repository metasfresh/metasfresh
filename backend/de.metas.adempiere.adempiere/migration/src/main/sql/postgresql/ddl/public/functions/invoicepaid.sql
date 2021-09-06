-- DROP FUNCTION IF EXISTS invoicePaid(numeric, numeric, numeric);

CREATE OR REPLACE FUNCTION invoicePaid(p_C_Invoice_ID numeric, p_C_Currency_ID numeric, p_MultiplierAP numeric)
RETURNS numeric AS
$BODY$
BEGIN
	return invoicepaidtodate(
		p_C_Invoice_ID
		, p_C_Currency_ID
		, p_MultiplierAP
		, null::timestamp with time zone -- p_Date
	);
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
