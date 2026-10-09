-- Function DDL: 5828600_sys_tax_declaration_ustva_report_function.sql
-- Print process "Umsatzsteuer-Voranmeldung (PDF)" on the Tax Declaration window (C_TaxDeclaration, AD_Table_ID 818)
--
-- IDs allocated from idserver.metas.de on 2026-10-08:
--   AD_MigrationScript 5828610
--   AD_Process         585686
--   AD_Table_Process   541700

INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, Created, CreatedBy, CSVFieldQuote, Description, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsIncludeCSVHeaderRow, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage, JasperReport, Name, PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585686 /*From ID Server*/, 'Y', 'de.metas.report.jasper.client.process.JasperReportStarter', TO_TIMESTAMP('2026-10-08 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, '"', 'Umsatzsteuer-Voranmeldung als PDF drucken', 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'Y', '@PREFIX@de/metas/reports/tax_declaration_ustva/report.jasper', 'Umsatzsteuer-Voranmeldung (PDF)', 'json', 'N', 'N', 'JasperReportsSQL', TO_TIMESTAMP('2026-10-08 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'tax_declaration_ustva_report')
;

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585686
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'VAT advance return (PDF)', Description = 'Print the VAT advance return (UStVA) as PDF', Updated = TO_TIMESTAMP('2026-10-08 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Process_ID = 585686 AND AD_Language IN ('en_US', 'en_GB')
;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-08 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Process_ID = 585686 AND AD_Language IN ('de_DE', 'de_CH')
;

-- Wire the process to the C_TaxDeclaration table (Tax Declaration window), as a document action button
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Process_ID, WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default, WEBUI_IncludedTabTopAction, EntityType)
VALUES (541700 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-08 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-08 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 818, 585686, 'N', 'Y', 'N', 'N', 'N', 'D')
;

-- Value: tax_declaration_ustva_report
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/tax_declaration_ustva/report.jasper
-- 2026-10-09T08:52:59.881Z
UPDATE AD_Process SET Name='Umsatzsteuer-Voranmeldung',Updated=TO_TIMESTAMP('2026-10-09 08:52:59.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585686
;

-- 2026-10-09T08:52:59.920Z
UPDATE AD_Process_Trl trl SET Name='Umsatzsteuer-Voranmeldung' WHERE AD_Process_ID=585686 AND AD_Language='de_DE'
;

-- Process: tax_declaration_ustva_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: C_TaxDeclaration
-- EntityType: D
-- 2026-10-09T08:53:42.675Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='Y', WEBUI_ViewAction='N', WEBUI_ViewQuickAction='N', WEBUI_ViewQuickAction_Default='N',Updated=TO_TIMESTAMP('2026-10-09 08:53:42.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541700
;

-- Process: tax_declaration_ustva_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: C_TaxDeclaration
-- EntityType: D
-- 2026-10-09T08:53:53.424Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y', WEBUI_ViewQuickAction_Default='Y',Updated=TO_TIMESTAMP('2026-10-09 08:53:53.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541700
;

