-- DROP FUNCTION IF EXISTS invoiceOpen(numeric, numeric);

CREATE OR REPLACE FUNCTION invoiceOpen(p_c_invoice_id numeric, p_c_invoicepayschedule_id numeric)
RETURNS numeric AS
$BODY$
DECLARE
	v_result InvoiceOpenResult;
BEGIN
	v_result := invoiceOpenToDate (
		p_C_Invoice_ID
		, p_C_InvoicePaySchedule_ID
		, 'X' -- DateTrx
		, null -- Date
	);
	
	if (v_result is null) then
		return null;
	end if;
	
	return v_result.OpenAmt;
END;

$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
