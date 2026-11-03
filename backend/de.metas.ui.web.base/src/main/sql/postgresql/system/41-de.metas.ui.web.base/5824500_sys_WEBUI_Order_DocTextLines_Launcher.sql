-- Text lines in documents: the launcher process that opens the DocTextLines modal
-- (de.metas.ui.web.doc_textlines.DocTextLinesView) as a top action of the sales order line tab.
-- Bound ONLY on AD_Tab_ID=187 (Auftragsposition -- the sales order's line tab, window 143). AD_Tab_ID=293
-- (Bestellposition, the purchase order's line tab) is deliberately NOT bound here -- out of scope.
-- AD_Table_ID is 259 (C_Order), not 260 (C_OrderLine): a WEBUI_IncludedTabTopAction button operates on the
-- tab's PARENT (header) record, same as the precedent this shape follows
-- (WEBUI_Order_ProductsProposal_Launcher, AD_Process_ID=541038, bound the same way at
-- 5510820_sys_gh1134webui_WEBUI_ProductProposals_Launcher_config_related_processes.sql) and as this module's
-- own recent C_OrderLine_OpenMaterialCockpit process (5790230_C_OrderLine_OpenMaterialCockpit.sql).

-- IDs allocated from idserver.metas.de:
--   AD_Process         585678
--   AD_Table_Process   541694

-- 1. AD_Process
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname,
                         CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings,
                         IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsLogWarning,
                         IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders,
                         IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout, Name,
                         PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type,
                         Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585678 /*From ID Server*/, 'Y',
        'de.metas.ui.web.doc_textlines.process.WEBUI_Order_DocTextLines_Launcher',
        'N',
        TO_TIMESTAMP('2026-09-15 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Y', 0,
        'Freitextzeilen',
        'json', 'N', 'N', 'xls', 'Java',
        TO_TIMESTAMP('2026-09-15 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'WEBUI_Order_DocTextLines_Launcher');

-- 2. AD_Process_Trl -- skeleton rows for every system/base language
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585678
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- English translation
UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Text Lines',
    Updated = TO_TIMESTAMP('2026-09-15 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585678;

-- de_DE/de_CH already carry the base (German) Name copied in step 2 -- just mark them translated
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-15 10:01:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Process_ID = 585678;

-- 3. AD_Table_Process -- bind as a WEBUI_IncludedTabTopAction on AD_Tab_ID=187 (sales order line tab) only
INSERT INTO AD_Table_Process (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Table_ID, AD_Tab_ID, AD_Window_ID,
                               AD_Table_Process_ID, Created, CreatedBy, EntityType, IsActive, Updated, UpdatedBy,
                               WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction,
                               WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (0, 0, 585678, 259, 187, 143,
        541694 /*From ID Server*/,
        TO_TIMESTAMP('2026-09-15 10:01:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        TO_TIMESTAMP('2026-09-15 10:01:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'N', 'Y', 'N', 'N', 'N');
