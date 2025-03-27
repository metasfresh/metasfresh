DROP VIEW IF EXISTS C_Invoice_Candidate_SelectionIncompleteGroups
;

CREATE OR REPLACE VIEW C_Invoice_Candidate_SelectionIncompleteGroups AS
SELECT DISTINCT s.AD_PInstance_ID,
                ic.C_Order_ID,
                o.DocumentNo AS OrderDocumentNo,
                ic.C_Order_CompensationGroup_ID
FROM T_Selection s
         INNER JOIN C_Invoice_Candidate ic ON (ic.C_Invoice_Candidate_ID = s.T_Selection_ID)
         INNER JOIN C_Invoice_Candidate ic2 ON (ic2.C_Order_ID = ic.C_Order_ID AND ic2.C_Order_CompensationGroup_ID = ic.C_Order_CompensationGroup_ID AND ic2.isAllowSeparateInvoicing = 'N')
         INNER JOIN C_Order o ON (o.C_Order_ID = ic.C_Order_ID)
WHERE TRUE
  AND ic.C_Order_CompensationGroup_ID IS NOT NULL
  AND ic.isAllowSeparateInvoicing = 'N'
  AND NOT EXISTS (SELECT 1 FROM T_Selection s2 WHERE s2.AD_PInstance_ID = s.AD_PInstance_ID AND s2.T_Selection_ID = ic2.C_Invoice_Candidate_ID)
;

-- select * from C_Invoice_Candidate_SelectionIncompleteGroups;

