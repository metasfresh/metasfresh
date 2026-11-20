-- Related Documents of a line row of the view C_Order_CompensationGroup_Line_V (tab 549518 of window 542196, script 5834210):
-- the entries "Rechnungsdisposition" (relation type 540221, target window 540092) and "Rechnungsdisposition Einkauf" (relation type
-- 540275, target window 540983) are lost on a view row. Both are IsTableRecordIDTarget='Y' types: they match a record by
-- AD_Table_ID + Record_ID, and the invoice candidates carry the AD_Table_ID of C_OrderLine, not that of the view.
--
-- Fix: for each of the two, a directed relation type from the view to C_Invoice_Candidate. Its target reference selects the invoice
-- candidates of the order line by C_Invoice_Candidate.AD_Table_ID (C_OrderLine) + Record_ID (the line's key C_OrderLine_ID).
-- IsActive is copied from the original relation type when this migration runs; a later change of 540221/540275 must be applied to 540514/540515 too.
--
-- IDs allocated from idserver.metas.de on 2026-11-19:
--   AD_MigrationScript 5836580 (this script); AD_Reference 542158, 542159; AD_RelationType 540514, 540515

-- ============================================================================
-- 1) Sales: C_Order_CompensationGroup_Line_V -> C_Invoice_Candidate (window 540092), like relation type 540221
-- ============================================================================
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542158 /*From ID Server*/, TO_TIMESTAMP('2026-11-19 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'RelType CompGroupLine_V->C_Invoice_Candidate Sales (Ziel)', TO_TIMESTAMP('2026-11-19 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Reference_ID = 542158
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Key, AD_Window_ID, WhereClause, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy)
SELECT 0, 0, 542158, rt.AD_Table_ID, rt.AD_Key, rt.AD_Window_ID,
       rt.WhereClause || ' AND c_invoice_candidate.AD_Table_ID=get_table_id(''C_OrderLine'') AND c_invoice_candidate.Record_ID=@C_OrderLine_ID/-1@',
       TO_TIMESTAMP('2026-11-19 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2026-11-19 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Ref_Table rt WHERE rt.AD_Reference_ID = 541237
;
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_RelationType_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsDirected, IsExplicit, IsTableRecordIDTarget, Name, Type, Updated, UpdatedBy)
SELECT 0, 0, 540514 /*From ID Server*/, 542156, 542158, TO_TIMESTAMP('2026-11-19 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'C_Order_CompensationGroup_Line_V_C_Invoice_Candidate_Sales',
       rt.IsActive, 'Y', 'N', 'N', 'CompGroupLine_V -> C_Invoice_Candidate (Sales)', 'I', TO_TIMESTAMP('2026-11-19 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_RelationType rt WHERE rt.AD_RelationType_ID = 540221
;
-- ============================================================================
-- 2) Purchase: C_Order_CompensationGroup_Line_V -> C_Invoice_Candidate (window 540983), like relation type 540275
-- ============================================================================
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542159 /*From ID Server*/, TO_TIMESTAMP('2026-11-19 12:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'RelType CompGroupLine_V->C_Invoice_Candidate Purchase (Ziel)', TO_TIMESTAMP('2026-11-19 12:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Reference_ID = 542159
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Key, AD_Window_ID, WhereClause, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy)
SELECT 0, 0, 542159, rt.AD_Table_ID, rt.AD_Key, rt.AD_Window_ID,
       rt.WhereClause || ' AND c_invoice_candidate.AD_Table_ID=get_table_id(''C_OrderLine'') AND c_invoice_candidate.Record_ID=@C_OrderLine_ID/-1@',
       TO_TIMESTAMP('2026-11-19 12:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2026-11-19 12:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Ref_Table rt WHERE rt.AD_Reference_ID = 541234
;
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_RelationType_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsDirected, IsExplicit, IsTableRecordIDTarget, Name, Type, Updated, UpdatedBy)
SELECT 0, 0, 540515 /*From ID Server*/, 542156, 542159, TO_TIMESTAMP('2026-11-19 12:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'C_Order_CompensationGroup_Line_V_C_Invoice_Candidate_Purchase',
       rt.IsActive, 'Y', 'N', 'N', 'CompGroupLine_V -> C_Invoice_Candidate (Purchase)', 'I', TO_TIMESTAMP('2026-11-19 12:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_RelationType rt WHERE rt.AD_RelationType_ID = 540275
;
