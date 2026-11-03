-- Text lines in documents: the quick actions that delete the selected text row, and move it up/down within
-- de.metas.ui.web.doc_textlines.DocTextLinesView. Registered as DisplayPlace.ViewQuickActions processes
-- directly on the view (DocTextLinesViewFactory#getRelatedProcessDescriptors), same shape as
-- WEBUI_DocTextLines_InsertAbove (5824520_sys_WEBUI_DocTextLines_InsertAbove.sql) -- no AD_Table_Process row
-- is needed here either.

-- IDs allocated from idserver.metas.de:
--   AD_Process   585680 (WEBUI_DocTextLines_Delete)
--   AD_Process   585681 (WEBUI_DocTextLines_MoveUp)
--   AD_Process   585682 (WEBUI_DocTextLines_MoveDown)

-- 1. AD_Process
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname,
                         CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings,
                         IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsLogWarning,
                         IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders,
                         IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout, Name,
                         PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type,
                         Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585680 /*From ID Server*/, 'Y',
        'de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_Delete',
        'N',
        TO_TIMESTAMP('2026-09-15 22:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Y', 0,
        'Freitextzeile löschen',
        'json', 'N', 'N', 'xls', 'Java',
        TO_TIMESTAMP('2026-09-15 22:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'WEBUI_DocTextLines_Delete');

INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname,
                         CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings,
                         IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsLogWarning,
                         IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders,
                         IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout, Name,
                         PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type,
                         Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585681 /*From ID Server*/, 'Y',
        'de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_MoveUp',
        'N',
        TO_TIMESTAMP('2026-09-15 22:35:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Y', 0,
        'Freitextzeile nach oben verschieben',
        'json', 'N', 'N', 'xls', 'Java',
        TO_TIMESTAMP('2026-09-15 22:35:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'WEBUI_DocTextLines_MoveUp');

INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname,
                         CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings,
                         IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsLogWarning,
                         IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders,
                         IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout, Name,
                         PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type,
                         Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585682 /*From ID Server*/, 'Y',
        'de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_MoveDown',
        'N',
        TO_TIMESTAMP('2026-09-15 22:35:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Y', 0,
        'Freitextzeile nach unten verschieben',
        'json', 'N', 'N', 'xls', 'Java',
        TO_TIMESTAMP('2026-09-15 22:35:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'WEBUI_DocTextLines_MoveDown');

-- 2. AD_Process_Trl -- skeleton rows for every system/base language
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID IN (585680, 585681, 585682)
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- English translations
UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Delete Text Line',
    Updated = TO_TIMESTAMP('2026-09-15 22:35:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585680;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Move Text Line Up',
    Updated = TO_TIMESTAMP('2026-09-15 22:35:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585681;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Move Text Line Down',
    Updated = TO_TIMESTAMP('2026-09-15 22:35:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585682;

-- de_DE/de_CH already carry the base (German) Name copied in step 2 -- just mark them translated
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-15 22:35:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Process_ID IN (585680, 585681, 585682);
