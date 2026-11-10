-- Sales shipment -> Related Documents -> "Rechnungsdisposition":
-- list the invoice candidates of the shipment (C_Invoice_Candidate.M_InOut_ID) AND the siblings of their compensation group,
-- because the invoicing demands the whole group (see view C_Invoice_Candidate_SelectionIncompleteGroups).
-- The generic foreign-key entry (C_Invoice_Candidate.M_InOut_ID) has the same window and category; the WebUI merges both and keeps the one with the higher priority (this relation type).

-- Name: C_Invoice_Candidate_Target_For_M_InOut_SOTrx
-- 2026-11-10T17:30:00.000Z
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542155, TO_TIMESTAMP('2026-11-10 17:30:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'de.metas.invoicecandidate', 'Y', 'N', 'C_Invoice_Candidate_Target_For_M_InOut_SOTrx', TO_TIMESTAMP('2026-11-10 17:30:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'T')
;

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Reference_ID = 542155
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate_Target_For_M_InOut_SOTrx
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- AD_Window_ID stays empty: the window comes from AD_Table.AD_Window_ID of C_Invoice_Candidate (540092).
-- WhereClause: direct rows (M_InOut_ID = the shipment) UNION their compensation group siblings.
-- The sibling predicate is the one of view C_Invoice_Candidate_SelectionIncompleteGroups (same C_Order_ID + C_Order_CompensationGroup_ID, IsAllowSeparateInvoicing='N' on both rows); keep both in step.
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 544906, 0, 542155, 540270, TO_TIMESTAMP('2026-11-10 17:30:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'de.metas.invoicecandidate', 'Y', 'N', 'N', TO_TIMESTAMP('2026-11-10 17:30:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'C_Invoice_Candidate.C_Invoice_Candidate_ID IN (
    SELECT d.C_Invoice_Candidate_ID FROM C_Invoice_Candidate d WHERE d.M_InOut_ID=@M_InOut_ID/-1@
    UNION
    SELECT g.C_Invoice_Candidate_ID FROM C_Invoice_Candidate d
      JOIN C_Invoice_Candidate g ON g.C_Order_ID=d.C_Order_ID AND g.C_Order_CompensationGroup_ID=d.C_Order_CompensationGroup_ID AND g.IsAllowSeparateInvoicing=''N''
    WHERE d.M_InOut_ID=@M_InOut_ID/-1@ AND d.C_Order_CompensationGroup_ID IS NOT NULL AND d.IsAllowSeparateInvoicing=''N'')')
;

-- Relation type: M_InOut_SOTrx -> C_Invoice_Candidate (source reference 540670 = M_InOut_SOTrx_Source)
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsTableRecordIDTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 540670, 542155, 540512, TO_TIMESTAMP('2026-11-10 17:30:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'de.metas.invoicecandidate', 'M_InOut_to_C_Invoice_Candidate_SO', 'Y', 'N', 'M_InOut_SOTrx -> C_Invoice_Candidate', TO_TIMESTAMP('2026-11-10 17:30:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;
