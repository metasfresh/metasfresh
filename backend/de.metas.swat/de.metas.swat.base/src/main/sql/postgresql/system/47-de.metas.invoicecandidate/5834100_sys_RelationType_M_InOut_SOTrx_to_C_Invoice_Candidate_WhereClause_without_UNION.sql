-- Reference: C_Invoice_Candidate_Target_For_M_InOut_SOTrx (AD_RelationType 540512 "M_InOut_SOTrx -> C_Invoice_Candidate")
-- Rewrite the target WhereClause without UNION: a single sub-select in EXISTS.
-- Why: the WebUI adds the role's access SQL to the related-documents query (de.metas.security.impl.ParsedSql).
-- That parser finds a sub-select only when it starts with "(SELECT " and cannot handle a UNION with several FROM clauses;
-- with the UNION form the final SQL had a syntax error, so the WebUI dropped this entry and showed only the generic one.
-- Same rows as before: the candidates of the shipment (C_Invoice_Candidate.M_InOut_ID) plus their compensation group siblings.
-- The sibling predicate is still the one of view C_Invoice_Candidate_SelectionIncompleteGroups
-- (same C_Order_ID + C_Order_CompensationGroup_ID, IsAllowSeparateInvoicing='N' on both rows, group not null).
-- If the view changes, change this WhereClause too (nothing links them mechanically).
-- Keep the sub-select on one line and opening with "(SELECT ": the access-SQL parser expects that form.
-- 2026-11-10T19:10:00.000Z
UPDATE AD_Ref_Table
SET WhereClause = 'C_Invoice_Candidate.M_InOut_ID=@M_InOut_ID/-1@ OR EXISTS (SELECT 1 FROM C_Invoice_Candidate d WHERE d.M_InOut_ID=@M_InOut_ID/-1@ AND d.C_Order_CompensationGroup_ID IS NOT NULL AND d.IsAllowSeparateInvoicing=''N'' AND d.C_Order_ID=C_Invoice_Candidate.C_Order_ID AND d.C_Order_CompensationGroup_ID=C_Invoice_Candidate.C_Order_CompensationGroup_ID AND C_Invoice_Candidate.IsAllowSeparateInvoicing=''N'')',
    Updated   = TO_TIMESTAMP('2026-11-10 19:10:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_Reference_ID = 542155
;
