
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
	AND (ic.updated + '00:10:00'::interval) < now() AND COALESCE(ic.processed_override, ic.processed) = 'N'
	AND ic.QTyToInvoice_Override IS NOT NULL 
	AND ic.QTyToInvoice!=QTyToInvoice_Override
;
