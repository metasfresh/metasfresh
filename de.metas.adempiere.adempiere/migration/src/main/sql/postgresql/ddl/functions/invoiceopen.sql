-- DROP FUNCTION IF EXISTS invoiceOpen(numeric, numeric);

CREATE OR REPLACE FUNCTION invoiceOpen(p_c_invoice_id numeric, p_c_invoicepayschedule_id numeric)
RETURNS numeric AS
$BODY$
BEGIN
	return invoiceOpenToDate (
		p_C_Invoice_ID
		, p_C_InvoicePaySchedule_ID
		, 'X' -- DateTrx
		, null -- Date
	);
END;

$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
