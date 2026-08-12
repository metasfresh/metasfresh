-- gh#28565 Promotion Code / Aktionskennzeichen
-- Step 1.7/1.10: AD_Message for duplicate validation + AD_Process for report

-- ============================================================
-- AD_Message: C_PromotionCode_DuplicateError
-- ============================================================
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive,
                        MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545640, 0, TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100, 'D', 'Y',
        'Aktionskennzeichen 1 und 2 müssen verschieden sein', 'E', 'C_PromotionCode_DuplicateError',
        TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Message_ID = 545640
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText = 'Promotion Code 1 and 2 must be different', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Message_ID = 545640 AND AD_Language = 'en_US';

-- Set ErrorCode (mandatory for MsgType='E')
UPDATE AD_Message SET ErrorCode = 'PROMOTION_CODE_DUPLICATE',
                      Updated = TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Message_ID = 545640;

-- ============================================================
-- AD_Process: Promotion Code Evaluation Report
-- Uses ExportToSpreadsheetProcess (report function in de.metas.fresh.base DDL)
-- ============================================================

-- AD_Element for the process (required for menu naming propagation)
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy,
                        EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584621, 0, 'PromotionCode_Report', TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100,
        'D', 'Y', 'Aktionskennzeichen Auswertung', 'Aktionskennzeichen Auswertung',
        TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Element_ID = 584621
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'Promotion Code Evaluation', PrintName = 'Promotion Code Evaluation', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584621 AND AD_Language = 'en_US';

INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, Classname, Created, CreatedBy,
                        EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint,
                        IsFormatExcelFile, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport,
                        IsTranslateExcelHeaders, IsUseBPartnerLanguage, LockWaitTimeout, Name,
                        ShowHelp, SpreadsheetFormat, SQLStatement, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585592, 'de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess',
        TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100,
        'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'Y', 'Y', 'Y', 0,
        'Aktionskennzeichen Auswertung', 'Y', 'xls',
        'SELECT * FROM report.report_promotion_code_evaluation(@C_PromotionCode_ID/null@, @C_PromotionCode2_ID/null@, @Invoiced/null@)',
        'Excel', TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100, 'PromotionCode_Report');

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Process_ID = 585592
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- ============================================================
-- AD_Process_Para: C_PromotionCode_ID (Ref=19 TableDir)
-- ============================================================
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID,
                             AD_Reference_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive,
                             IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
                             Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 584619, 0, 585592, 543140,
        19, 'C_PromotionCode_ID', TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100, 'D', 10, 'Y',
        'N', 'Y', 'N', 'N', 'N',
        'Aktionskennzeichen', 10, TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Process_Para_ID = 543140
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID);

-- ============================================================
-- AD_Process_Para: C_PromotionCode2_ID (Ref=18 Table + AD_Reference_Value_ID)
-- ============================================================
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID,
                             AD_Reference_ID, AD_Reference_Value_ID, ColumnName, Created, CreatedBy, EntityType,
                             FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted,
                             IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 584620, 0, 585592, 543141,
        18, 542070, 'C_PromotionCode2_ID', TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100, 'D',
        10, 'Y', 'N', 'Y', 'N', 'N', 'N',
        'Aktionskennzeichen 2', 20, TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Process_Para_ID = 543141
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID);

-- ============================================================
-- AD_Process_Para: Invoiced (Ref=20 YesNo)
-- ============================================================
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID,
                             AD_Reference_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive,
                             IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
                             Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 348, 0, 585592, 543142,
        20, 'Invoiced', TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100, 'D', 1, 'Y',
        'N', 'N', 'N', 'N', 'N',
        'Fakturiert', 30, TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Process_Para_ID = 543142
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID);

UPDATE AD_Process_Para_Trl SET Name = 'Invoiced', IsTranslated = 'Y',
                               Updated = TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Process_Para_ID = 543142 AND AD_Language = 'en_US';

-- ============================================================
-- AD_Menu entry for report
-- ============================================================
INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Process_ID, Created, CreatedBy,
                     EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name,
                     Updated, UpdatedBy)
VALUES ('R', 0, 584621, 542303, 0, 585592, TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100,
        'D', 'PromotionCode_Report', 'Y', 'N', 'N', 'N', 'N', 'Aktionskennzeichen Auswertung',
        TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WebUI_NameBrowse, WebUI_NameNew, WebUI_NameNewBreadcrumb,
                         IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WebUI_NameBrowse, t.WebUI_NameNew, t.WebUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Menu_ID = 542303
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID);

UPDATE AD_Menu_Trl SET Name = 'Promotion Code Evaluation', IsTranslated = 'Y',
                       Updated = TO_TIMESTAMP('2026-03-05 12:05', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Menu_ID = 542303 AND AD_Language = 'en_US';

-- Tree node
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y', now(), 100, now(), 100,
       t.AD_Tree_ID, 542303, 0, 999
FROM AD_Tree t
WHERE t.AD_Client_ID = 0 AND t.IsActive = 'Y' AND t.IsAllNodes = 'Y'
  AND t.AD_Table_ID = 116
  AND NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM tnm WHERE tnm.AD_Tree_ID = t.AD_Tree_ID AND tnm.Node_ID = 542303);
