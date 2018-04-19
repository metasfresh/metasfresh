
CREATE OR REPLACE VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Missing_C_Invoice_Candidate_Agg_ID_v AS 
SELECT ic.c_invoice_candidate_id, ic.created, ic.updated
FROM c_invoice_candidate ic
WHERE true 
	AND ic.IsActive='Y'
	AND ic.C_Invoice_Candidate_Agg_ID IS NULL 
	AND ic.processed = 'N'
	AND ic.istoclear = 'N'
	AND (ic.updated + '00:10:00'::interval) < now()
ORDER BY ic.updated DESC
;
COMMENT ON VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Missing_C_Invoice_Candidate_Agg_ID_v
  IS 'ICs that don''t yet have C_Invoice_Candidate_Agg_ID (aparently that also means no line aggregation key!), but were created/updated more than 10 minutes ago.
Issue FRESH-388 (FRESH-93)';

CREATE OR REPLACE VIEW de_metas_invoicecandidate.c_invoice_candidate_missing_aggregation_group_v AS 
SELECT ic.c_invoice_candidate_id, ic.created, ic.updated
FROM c_invoice_candidate ic
WHERE true 
	AND ic.IsActive='Y'
	AND ic.c_invoice_candidate_headeraggregation_effective_id IS NULL 
	AND ic.processed = 'N'
	AND ic.istoclear = 'N'
	AND (ic.updated + '00:10:00'::interval) < now()
ORDER BY ic.updated DESC
;
COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_missing_aggregation_group_v
  IS 'ICs that don''t yet have an aggregation group, but were created/updated more than 10 minutes ago.
Issue FRESH-93';

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
HAVING ic.QtyInvoiced!=SUM(ila.QtyInvoiced)
;
COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_stale_qtyinvoiced_v
  IS 'ICs that have an inconsistend QtyInvoiced value and were created/updated more than 10 minutes ago.
Issue FRESH-93';

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
	AND (ic.qtydelivered <> ol.qtydelivered OR ic.qtyordered <> ol.qtyordered)
;
COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_wrong_qty_ol_v
  IS 'ICs that 
* reference a C_OrderLine and
* have an inconsistend QtyDelivered or QtyOrdered value and
* were created/updated more than 10 minutes ago.
see Issue FRESH-93';

CREATE OR REPLACE VIEW de_metas_invoicecandidate.c_invoice_candidate_wrong_qtytoinvoice_v AS
SELECT 
	ic.C_Invoice_candidate_ID,
	ic.Processed_Calc, 
	ic.Processed_Override,
	ic.QTyToInvoice_Override,
	ic.QtyToInvoice,
	ic.PriceActual,
	ic.NetAmtToInvoice,
	ic.Updated, 
	ic.UpdatedBy
FROM C_Invoice_candidate ic 
WHERE true 
	AND ic.IsActive='Y'
	AND (ic.updated + '00:10:00'::interval) < now() 
	AND COALESCE(ic.processed_override, ic.processed) = 'N'
	AND ic.QTyToInvoice_Override IS NOT NULL 
	AND ic.QTyToInvoice!=QTyToInvoice_Override
	AND NOT EXISTS (select 1 from C_Invoice_Candidate_Recompute r where r.C_Invoice_Candidate_ID=ic.C_Invoice_Candidate_ID)
	AND ic.SchedulerResult!='Auftrag hat zur Zeit den Status In Verarbeitung.'
;
COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_wrong_qtytoinvoice_v IS 
'Selects ICs that have QtyToInvoice!=QtyToInvoice_Override and that have a processed order line, were last updated more than 10 minutes ago and are currently not flagged as invalid.';
