UPDATE C_Dunning_Candidate dc
SET
	DocumentNo = x.DocumentNo
FROM
	(
		SELECT i.DocumentNo, dc.C_Dunning_Candidate_ID
		FROM
			C_Invoice i
			JOIN C_Dunning_Candidate dc ON dc.AD_Table_ID = get_table_ID('C_Invoice') and dc.Record_ID = i.C_Invoice_ID
		
	) x
WHERE 
	dc.C_Dunning_Candidate_ID = x.C_Dunning_Candidate_ID;