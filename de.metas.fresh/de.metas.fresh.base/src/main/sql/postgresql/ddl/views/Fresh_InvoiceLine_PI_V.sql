-- View: Report.fresh_InvoiceLine_PI_V

--DROP VIEW Report.fresh_InvoiceLine_PI_V;

CREATE OR REPLACE VIEW Report.fresh_InvoiceLine_PI_V AS 
SELECT DISTINCT
	iolpi.name,
	iliol.C_InvoiceLine_ID
FROM
	Report.fresh_IL_TO_IOL_V iliol
	INNER JOIN Report.fresh_InOutLine_PI_V iolpi ON iliol.M_InOutLine_ID = iolpi.M_InOutLine_ID
;


COMMENT ON VIEW Report.fresh_InvoiceLine_PI_V IS 'Lists all invoice lines together with their Packing instruction name. the packing instrucion name is retrieved via invoice candidate and in out line. (M_MatchInv was not working properly by the time of implementation, also this view is used by sales and purchase invoices)';
