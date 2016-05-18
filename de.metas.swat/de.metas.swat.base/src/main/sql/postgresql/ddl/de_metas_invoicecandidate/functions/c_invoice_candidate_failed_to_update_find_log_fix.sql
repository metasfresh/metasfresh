
-- DROP FUNCTION de_metas_invoicecandidate.c_invoice_candidate_failed_to_update_find_log_fix();

CREATE OR REPLACE FUNCTION de_metas_invoicecandidate.c_invoice_candidate_failed_to_update_find_log_fix()
  RETURNS void AS
$BODY$

INSERT INTO de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update
SELECT * FROM de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v;

WITH source as (
	UPDATE de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update 
	SET reenqueued=now() 
	WHERE reenqueued is null
	RETURNING C_Invoice_Candidate_ID
)
INSERT INTO C_Invoice_Candidate_Recompute
SELECT C_Invoice_Candidate_ID
FROM source;

$BODY$
  LANGUAGE sql VOLATILE
  COST 100;

COMMENT ON FUNCTION de_metas_invoicecandidate.c_invoice_candidate_failed_to_update_find_log_fix() IS 'Executes the view de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v and inserts the results into the table de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update.
Then it inserts the problematic ICs'' C_Invoice_Candidate_IDs into C_Invoice_Candidate_Recompute
Issue FRESH-93';
