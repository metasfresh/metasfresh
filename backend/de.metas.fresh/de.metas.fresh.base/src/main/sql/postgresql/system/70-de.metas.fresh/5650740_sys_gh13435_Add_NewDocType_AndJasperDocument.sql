--
-- Script: /tmp/webui_migration_scripts_2022-08-12_5058539368034800699/5650730_migration_2022-08-12_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- 2022-08-12T08:36:32.044Z
-- URL zum Konzept
INSERT INTO C_DocType (AD_Client_ID, AD_Org_ID, C_DocType_ID, Created, CreatedBy, DocBaseType, DocumentCopies, EntityType, GL_Category_ID, HasCharges, HasProforma, IsActive, IsCopyDescriptionToDocument, IsCreateCounter, IsDefault, IsDefaultCounterDoc, IsDocNoControlled, IsExcludeFromCommision, IsIndexed, IsInTransit, IsOverwriteDateOnComplete, IsOverwriteSeqOnComplete, IsPickQAConfirm,
                       IsShipConfirm, IsSOTrx, IsSplitWhenDifference, Name, PrintName, Updated, UpdatedBy)
VALUES (1000000, 1000000, 541061, TO_TIMESTAMP('2022-08-12 08:36:32', 'YYYY-MM-DD HH24:MI:SS'), 100, 'API', 1, 'de.metas.fresh', 1000006, 'N', 'N', 'Y',
        'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Akonto', 'Akonto', TO_TIMESTAMP('2022-08-12 08:36:32', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2022-08-12T08:36:32.048Z
-- URL zum Konzept
INSERT INTO C_DocType_Trl (AD_Language, C_DocType_ID, Description, DocumentNote, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.C_DocType_ID,
       t.Description,
       t.DocumentNote,
       t.Name,
       t.PrintName,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     C_DocType t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.C_DocType_ID = 541061
  AND NOT EXISTS(SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.C_DocType_ID = t.C_DocType_ID)
;

-- 2022-08-12T08:36:32.049Z
-- URL zum Konzept
INSERT INTO AD_Document_Action_Access (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_DocType_ID, AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,
                                                                                                                                                                    0,
                                                                                                                                                                    'Y',
                                                                                                                                                                    NOW(),
                                                                                                                                                                    100,
                                                                                                                                                                    NOW(),
                                                                                                                                                                    100,
                                                                                                                                                                    doctype.C_DocType_ID,
                                                                                                                                                                    action.AD_Ref_List_ID,
                                                                                                                                                                    rol.AD_Role_ID
                                                                                                                                                             FROM AD_Client client
                                                                                                                                                                      INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID = client.AD_Client_ID)
                                                                                                                                                                      INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID = 135)
                                                                                                                                                                      INNER JOIN AD_Role rol ON (rol.AD_Client_ID = client.AD_Client_ID)
                                                                                                                                                             WHERE client.AD_Client_ID = 1000000
                                                                                                                                                               AND doctype.C_DocType_ID = 541061
                                                                                                                                                               AND rol.IsManual = 'N')
;

-- 2022-08-12T08:36:51.789Z
-- URL zum Konzept
UPDATE C_DocType
SET DocNoSequence_ID=555719, Updated=TO_TIMESTAMP('2022-08-12 08:36:51', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_DocType_ID = 541061
;

-- 2022-08-12T08:36:57.589Z
-- URL zum Konzept
UPDATE C_DocType
SET AD_PrintFormat_ID=1000012, Updated=TO_TIMESTAMP('2022-08-12 08:36:57', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_DocType_ID = 541061
;

-- 2022-08-12T08:37:12.437Z
-- URL zum Konzept
UPDATE C_DocType_Trl
SET Name='Interim Invoice', Updated=TO_TIMESTAMP('2022-08-12 08:37:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND C_DocType_ID = 541061
;

-- 2022-08-12T08:37:14.237Z
-- URL zum Konzept
UPDATE C_DocType_Trl
SET PrintName='Interim Invoice', Updated=TO_TIMESTAMP('2022-08-12 08:37:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND C_DocType_ID = 541061
;

-- 2022-08-12T08:37:14.311Z
-- URL zum Konzept
UPDATE C_DocType_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2022-08-12 08:37:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND C_DocType_ID = 541061
;

-- 2022-08-12T08:37:19.770Z
-- URL zum Konzept
UPDATE C_DocType_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2022-08-12 08:37:19', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND C_DocType_ID = 541061
;

-- 2022-08-12T08:37:23.980Z
-- URL zum Konzept
UPDATE C_DocType_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2022-08-12 08:37:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND C_DocType_ID = 541061
;

-- 2022-08-12T08:38:18.449Z
-- URL zum Konzept
INSERT INTO AD_PrintFormat (AD_Client_ID, AD_Org_ID, AD_PrintColor_ID, AD_PrintFont_ID, AD_Printformat_Group, AD_PrintFormat_ID, AD_PrintPaper_ID, AD_Table_ID, Created, CreatedBy, FooterMargin, HeaderMargin, IsActive, IsDefault, IsForm, IsStandardHeaderFooter, IsTableBased, Name, Updated, UpdatedBy)
VALUES (1000000, 1000000, 100, 540006, 'None', 540126, 102, 516, TO_TIMESTAMP('2022-08-12 08:38:18', 'YYYY-MM-DD HH24:MI:SS'), 100, 0, 0, 'Y', 'N', 'N', 'Y', 'Y', 'Akonto', TO_TIMESTAMP('2022-08-12 08:38:18', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2022-08-12T08:38:24.489Z
-- URL zum Konzept
UPDATE AD_PrintFormat
SET JasperProcess_ID=500012, Updated=TO_TIMESTAMP('2022-08-12 08:38:24', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_PrintFormat_ID = 540126
;

-- 2022-08-12T08:38:32.305Z
-- URL zum Konzept
UPDATE C_DocType
SET AD_PrintFormat_ID=NULL, Updated=TO_TIMESTAMP('2022-08-12 08:38:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_DocType_ID = 541061
;

-- 2022-08-12T08:38:39.089Z
-- URL zum Konzept
UPDATE C_DocType
SET AD_PrintFormat_ID=540126, Updated=TO_TIMESTAMP('2022-08-12 08:38:39', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_DocType_ID = 541061
;



-- 2022-08-12T08:47:45.651042400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUseBPartnerLanguage, JasperReport, LockWaitTimeout, Name,
                        PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585094, 'Y', 'de.metas.report.jasper.client.process.JasperReportStarter', 'N', TO_TIMESTAMP('2022-08-12 11:47:45', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.fresh', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'Y', 'Y', 'Y', '@PREFIX@de/metas/docs/purchase/interim_invoice/report.jasper', 0, 'Akonto(Jasper)', 'json', 'N', 'N', 'xls', 'JasperReportsSQL',
        TO_TIMESTAMP('2022-08-12 11:47:45', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Akonto(Jasper)')
;

-- 2022-08-12T08:47:45.652640700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Process_ID,
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
     AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Process_ID = 585094
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;



-- 2022-08-12T08:57:45.652640700Z
-- URL zum Konzept
UPDATE AD_PrintFormat
SET JasperProcess_ID=585094, Updated=TO_TIMESTAMP('2022-08-12 08:57:45', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_PrintFormat_ID = 540126
;