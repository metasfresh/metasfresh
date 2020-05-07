
DROP VIEW IF eXISTS de_metas_invoicecandidate.C_Invoice_Candidate_Missing_C_Invoice_Candidate_Agg_ID_v ;
CREATE OR REPLACE VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Missing_C_Invoice_Candidate_Agg_ID_v AS 
SELECT ic.c_invoice_candidate_id, ic.created, ic.updated
FROM c_invoice_candidate ic
WHERE true 
	AND ic.C_Invoice_Candidate_Agg_ID IS NULL 
	AND ic.processed = 'N'::bpchar 
	AND ic.istoclear = 'N'::bpchar 
	AND (ic.updated + '00:10:00'::interval) < now()
ORDER BY ic.updated DESC;

COMMENT ON VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Missing_C_Invoice_Candidate_Agg_ID_v
  IS 'ICs that don''t yet have C_Invoice_Candidate_Agg_ID (aparently that also means no line aggregation key!), but were created/updated more than 10 minutes ago.
Issue FRESH-388 (FRESH-93)';
