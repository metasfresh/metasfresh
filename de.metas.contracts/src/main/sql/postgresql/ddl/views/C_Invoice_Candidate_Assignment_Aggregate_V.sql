
DROP VIEW IF EXISTS C_Invoice_Candidate_Assignment_Aggregate_V;
CREATE VIEW C_Invoice_Candidate_Assignment_Aggregate_V AS
SELECT
	C_Invoice_Candidate_Term_ID,
	C_Flatrate_RefundConfig_ID,
	SUM(CASE WHEN IsAssignedQuantityIncludedInSum='Y' THEN AssignedQuantity ELSE 0 END) AS AssignedQuantity,
	SUM(AssignedMoneyAmount) AS AssignedMoneyAmount
FROM C_Invoice_Candidate_Assignment
WHERE IsActive='Y'
GROUP BY 
	C_Invoice_Candidate_Term_ID,
	C_Flatrate_RefundConfig_ID;
