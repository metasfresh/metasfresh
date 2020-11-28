
DROP VIEW IF EXISTS de_metas_invoicecandidate.c_invoice_candidate_wrong_qty_ol_v;

CREATE OR REPLACE VIEW de_metas_invoicecandidate.c_invoice_candidate_wrong_qty_ol_v AS 
SELECT ic.c_invoice_candidate_id, ic.created, ic.updated, ic.qtyordered, ol.qtyordered AS ol_qtyordered, ic.qtydelivered, ol.qtydelivered AS ol_qtydelivered
FROM c_invoice_candidate ic
   JOIN c_orderline ol ON ol.c_orderline_id = ic.c_orderline_id
   JOIN c_order o ON o.c_order_id = ol.c_order_id
   LEFT JOIN c_invoice_candidate_recompute icr ON icr.c_invoice_candidate_id = ic.c_invoice_candidate_id
WHERE true 
	AND ic.IsActive='Y'
	AND (ic.updated + interval '10 minutes') < now() 
	AND COALESCE(ic.processed_override, ic.processed) = 'N' 
	AND icr.c_invoice_candidate_id IS NULL 
	AND o.docstatus IN ('CO', 'CL') 
	AND (ic.qtydelivered <> ol.qtydelivered OR ic.qtyordered <> ol.qtyordered);

COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_wrong_qty_ol_v
  IS 'ICs that 
* reference a C_OrderLine and
* have an inconsistend QtyDelivered or QtyOrdered value and
* were created/updated more than 10 minutes ago.
see Issue FRESH-93';

