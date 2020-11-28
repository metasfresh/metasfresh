
CREATE OR REPLACE VIEW de_metas_invoicecandidate.c_invoice_candidate_diagnose_haschanges_error_v AS
SELECT 
	p.Value, 
	p.Name, 
	pi.Updated, 
	pi.whereClause, 
	pi.ErrorMsg, 
	pil.P_Msg,
	SUBSTRING(p_msg, '^[0-9]+')::numeric as C_Invoice_Candidate_ID -- extract the IC id from the error message
FROM AD_Process p
	JOIN AD_PInstance pi ON pi.AD_Process_ID=p.AD_Process_ID
		JOIN AD_PInstance_Log pil ON pil.AD_PInstance_ID=pi.AD_PInstance_ID
WHERE pi.errormsg ='HasChanges';
COMMENT ON VIEW de_metas_invoicecandidate.c_invoice_candidate_diagnose_HasChanges_error_v IS
'Usage example: 
select * from de_metas_invoicecandidate.c_invoice_candidate_diagnose_HasChanges_error_v where Updated between ''2017-11-21 09:00'' and ''2017-11-21 09:30''

might give you something like "4965463: @LineNetAmt@: 17.00->34.00".
With that, you can open the C_Invoice_Candidate with ID=4965463 and see what went wrong. Often, simply deleting that IC (and have the system recreate it) solves the problem.
';
