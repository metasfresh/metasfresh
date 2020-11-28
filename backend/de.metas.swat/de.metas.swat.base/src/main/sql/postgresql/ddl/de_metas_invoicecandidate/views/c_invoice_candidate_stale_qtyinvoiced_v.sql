DROP VIEW IF EXISTS de_metas_invoicecandidate.c_invoice_candidate_stale_qtyinvoiced_v;
CREATE OR REPLACE VIEW de_metas_invoicecandidate.c_invoice_candidate_stale_qtyinvoiced_v AS 
SELECT 
	ic.C_Invoice_candidate_ID,
	ic.QtyInvoiced,
	SUM(COALESCE(ila.QtyInvoiced,0)) as sum,
	ic.Processed_override,
	ic.Processed,
	ic.Created,
	ic.Updated
FROM C_Invoice_Candidate ic 
	LEFT JOIN C_Invoice_Line_Alloc ila ON ila.C_Invoice_candidate_ID=ic.C_Invoice_candidate_ID AND ila.IsActive='Y'
WHERE true 
	AND ic.IsActive='Y'
	AND (ic.updated + '00:10:00'::interval) < now()
GROUP BY 
	ic.C_Invoice_candidate_ID,
	ic.QtyInvoiced
HAVING ic.QtyInvoiced!=SUM(ila.QtyInvoiced);

COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_stale_qtyinvoiced_v
  IS 'ICs that have an inconsistend QtyInvoiced value and were created/updated more than 10 minutes ago.
Issue FRESH-93';

