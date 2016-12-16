-- View: Report.fresh_IL_To_IOL_V

-- DROP VIEW Report.fresh_IL_To_IOL_V;

CREATE OR REPLACE VIEW Report.fresh_IL_To_IOL_V AS
-- for purchase, use MatchInv
SELECT 
	mi.C_Invoice_ID, mi.C_InvoiceLine_ID, iol.M_InOut_ID, mi.M_InOutLine_ID, ila.C_Invoice_Candidate_ID
FROM 
	M_MatchInv mi
	LEFT OUTER JOIN C_Invoice_Line_Alloc ila ON mi.C_InvoiceLine_ID = ila.C_InvoiceLine_ID AND ila.isActive = 'Y'
	INNER JOIN M_InOutLine iol ON mi.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
	INNER JOIN C_Invoice i ON mi.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
WHERE
	i.IsSOTrx = 'N' AND mi.isActive = 'Y'
UNION 
-- For Sales, use direkt link between invoice line and in out line if available
SELECT
	il.C_Invoice_ID, ila.C_InvoiceLine_ID, iol.M_InOut_ID, iol.M_InOutLine_ID, ila.C_Invoice_Candidate_ID
FROM
	C_InvoiceLine il
	INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
	INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
	LEFT OUTER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID
WHERE
	i.IsSOTrx = 'Y' AND il.isActive = 'Y'
UNION
-- Error Handling:
-- Use allocations to invoice candidate for sales invoice lines that don't have a direkt link - but also for purchase invoices, that have direct link but they don't have match inv for leergut
SELECT
	il.C_Invoice_ID, ila.C_InvoiceLine_ID, iol.M_InOut_ID, iciol.M_InOutLine_ID, ila.C_Invoice_Candidate_ID
FROM
	C_InvoiceLine il
	INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID AND ila.isActive = 'Y'
	INNER JOIN C_InvoiceCandidate_InOutLine iciol ON ila.C_Invoice_Candidate_ID = iciol.C_Invoice_Candidate_ID AND iciol.isActive = 'Y'
	INNER JOIN M_InOutLine iol ON iciol.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
	INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
WHERE il.isActive = 'Y'
;


COMMENT ON VIEW Report.fresh_IL_To_IOL_V IS 'Retrieves all links between invoice lines and in out lines as well as invoice lines and their invoice candidates 
For sales invoices the view uses the link over the invoice candidates because MatchInv is notused for sales invoices (yet).
For purchase invoice the Table MatchInv is used.';

