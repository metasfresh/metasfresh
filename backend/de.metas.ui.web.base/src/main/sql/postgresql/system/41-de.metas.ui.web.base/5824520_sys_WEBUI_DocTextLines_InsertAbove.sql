-- Text lines in documents: the quick action that inserts a new text line above the selected row of
-- de.metas.ui.web.doc_textlines.DocTextLinesView. Registered as a DisplayPlace.ViewQuickActions process
-- directly on the view (DocTextLinesViewFactory#getRelatedProcessDescriptors), not via AD_Table_Process --
-- same shape as the products-proposal quick actions (WEBUI_ProductsProposal_Delete,
-- 5513310_sys_gh1150webui_WEBUI_ProductsProposal_Delete.sql), so no AD_Table_Process row is needed here.

-- IDs allocated from idserver.metas.de:
--   AD_Process   585679

-- 1. AD_Process
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname,
                         CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings,
                         IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsLogWarning,
                         IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders,
                         IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout, Name,
                         PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type,
                         Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585679 /*From ID Server*/, 'Y',
        'de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_InsertAbove',
        'N',
        TO_TIMESTAMP('2026-09-15 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Y', 0,
        'Freitextzeile oberhalb einfügen',
        'json', 'N', 'N', 'xls', 'Java',
        TO_TIMESTAMP('2026-09-15 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'WEBUI_DocTextLines_InsertAbove');

-- 2. AD_Process_Trl -- skeleton rows for every system/base language
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585679
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- English translation
UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Insert Text Line Above',
    Updated = TO_TIMESTAMP('2026-09-15 10:05:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585679;

-- de_DE/de_CH already carry the base (German) Name copied in step 2 -- just mark them translated
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-15 10:05:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Process_ID = 585679;
