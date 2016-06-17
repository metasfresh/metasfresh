
-- DROP VIEW IF EXISTS de_metas_invoicecandidate.c_invoice_candidate_missing_aggregation_group_v;

CREATE OR REPLACE VIEW de_metas_invoicecandidate.c_invoice_candidate_missing_aggregation_group_v AS 
SELECT ic.c_invoice_candidate_id, ic.created, ic.updated
FROM c_invoice_candidate ic
WHERE true 
	AND ic.c_invoice_candidate_headeraggregation_effective_id IS NULL 
	AND ic.processed = 'N'::bpchar 
	AND ic.istoclear = 'N'::bpchar 
	AND (ic.updated + '00:10:00'::interval) < now()
ORDER BY ic.updated DESC;

COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_missing_aggregation_group_v
  IS 'ICs that don''t yet have an aggregation group, but were created/updated more than 10 minutes ago.
Issue FRESH-93';

