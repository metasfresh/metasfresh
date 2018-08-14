
DROP VIEW IF EXISTS C_Invoice_Candidate_Assignment_Aggregate_V;
CREATE VIEW C_Invoice_Candidate_Assignment_Aggregate_V AS
SELECT
	C_Invoice_Candidate_Term_ID,
	C_Flatrate_RefundConfig_ID,
	SUM(AssignedQuantity) AS AssignedQuantity
FROM C_Invoice_Candidate_Assignment
WHERE IsActive='Y'
GROUP BY 
	C_Invoice_Candidate_Term_ID,
	C_Flatrate_RefundConfig_ID;
