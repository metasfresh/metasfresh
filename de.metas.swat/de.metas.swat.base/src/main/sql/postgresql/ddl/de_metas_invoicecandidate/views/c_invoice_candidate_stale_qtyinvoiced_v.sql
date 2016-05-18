DROP VIEW IF EXISTS de_metas_invoicecandidate.c_invoice_candidate_stale_qtyinvoiced_v;
CREATE OR REPLACE VIEW de_metas_invoicecandidate.c_invoice_candidate_stale_qtyinvoiced_v AS 
SELECT ic.c_invoice_candidate_id, ic.qtyinvoiced, sum(ila.qtyinvoiced) AS sum
FROM c_invoice_candidate ic
   JOIN c_invoice_line_alloc ila ON ic.c_invoice_candidate_id = ila.c_invoice_candidate_id
WHERE true 
	AND ic.processed = 'N' 
	AND (ic.updated + '00:10:00'::interval) < now()
GROUP BY ic.c_invoice_candidate_id, ic.qtyinvoiced
HAVING ic.qtyinvoiced <> sum(ila.qtyinvoiced);

COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_stale_qtyinvoiced_v
  IS 'ICs that have an inconsistend QtyInvoiced value and were created/updated more than 10 minutes ago.
Issue FRESH-93';

