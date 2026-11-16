-- Function DDL: 5831670_sys_Product_Statistics_Year_Comparison_report_function.sql
-- Report process "Artikelstatistik Jahresvergleich" / "Product Statistics Year Comparison" (Jasper, PDF)
-- with parameters Organisation (mandatory), Year (mandatory, years of the org's calendar) and Product (optional), plus its menu entry
--
-- IDs allocated from idserver.metas.de on 2026-10-27:
--   AD_MigrationScript 5831680
--   AD_Process         585690
--   AD_Process_Para    543361 (AD_Org_ID), 543362 (C_Year_ID), 543363 (M_Product_ID)
--   AD_Val_Rule        540809
--   AD_Element         585567 (menu caption)
--   AD_Menu            542371

-- Validation rule: the years of the org's calendar (AD_OrgInfo.C_Calendar_ID), else of the client's calendar (AD_ClientInfo.C_Calendar_ID)
INSERT INTO AD_Val_Rule (AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID, Code, Created, CreatedBy, Description, EntityType, IsActive, Name, Type, Updated, UpdatedBy)
VALUES (0, 0, 540809 /*From ID Server*/, 'C_Year.C_Calendar_ID = COALESCE((SELECT NULLIF(oi.C_Calendar_ID,0) FROM AD_OrgInfo oi WHERE oi.AD_Org_ID=@AD_Org_ID@), (SELECT ci.C_Calendar_ID FROM AD_ClientInfo ci WHERE ci.AD_Client_ID=@#AD_Client_ID@))',
        TO_TIMESTAMP('2026-10-27 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Jahre des Kalenders der Organisation (sonst des Mandanten)', 'D', 'Y', 'C_Year of Org Calendar', 'S', TO_TIMESTAMP('2026-10-27 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- Process
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, Created, CreatedBy, CSVFieldQuote, Description, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsIncludeCSVHeaderRow, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage, JasperReport, Name, PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585690 /*From ID Server*/, 'Y', 'de.metas.report.jasper.client.process.JasperReportStarter', TO_TIMESTAMP('2026-10-27 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, '"',
        'Artikelstatistik Jahresvergleich als PDF: Menge und Umsatz je Artikel und Monat, aktuelles Jahr im Vergleich zum Vorjahr', 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'Y',
        '@PREFIX@de/metas/reports/product_statistics_year_comparison/report.jasper', 'Artikelstatistik Jahresvergleich', 'json', 'N', 'N', 'JasperReportsSQL',
        TO_TIMESTAMP('2026-10-27 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Product_Statistics_Year_Comparison')
;

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585690
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Product Statistics Year Comparison', Description = 'Product statistics year comparison as PDF: quantity and sales amount per product and month, current year compared to the previous year', Updated = TO_TIMESTAMP('2026-10-27 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Process_ID = 585690 AND AD_Language IN ('en_US', 'en_GB')
;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-27 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Process_ID = 585690 AND AD_Language IN ('de_DE', 'de_CH')
;

-- Parameters (labels come from the existing AD_Element translations of the three columns)
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, DefaultValue, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 113, 0, 585690, 543361 /*From ID Server*/, 19, 'AD_Org_ID', TO_TIMESTAMP('2026-10-27 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, '@#AD_Org_ID@', 'D', 0, 'Y', 'N', 'Y', 'N', 'Y', 'N', 'Sektion', 10, TO_TIMESTAMP('2026-10-27 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, AD_Val_Rule_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 223, 0, 585690, 543362 /*From ID Server*/, 19, 540809, 'C_Year_ID', TO_TIMESTAMP('2026-10-27 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 0, 'Y', 'N', 'Y', 'N', 'Y', 'N', 'Jahr', 20, TO_TIMESTAMP('2026-10-27 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 454, 0, 585690, 543363 /*From ID Server*/, 30, 'M_Product_ID', TO_TIMESTAMP('2026-10-27 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'Produkt', 30, TO_TIMESTAMP('2026-10-27 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID IN (543361, 543362, 543363)
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- Parameter translations: take the existing AD_Element translations (en_US / de_DE / de_CH); other languages keep the base text
UPDATE AD_Process_Para_Trl ppt
SET Name = et.Name, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-27 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
FROM AD_Process_Para pp
         JOIN AD_Element_Trl et ON et.AD_Element_ID = pp.AD_Element_ID
WHERE pp.AD_Process_Para_ID = ppt.AD_Process_Para_ID
  AND et.AD_Language = ppt.AD_Language
  AND ppt.AD_Language IN ('en_US', 'en_GB', 'de_DE', 'de_CH')
  AND ppt.AD_Process_Para_ID IN (543361, 543362, 543363)
;

-- Menu caption element
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585567 /*From ID Server*/, 0, 'Product_Statistics_Year_Comparison', TO_TIMESTAMP('2026-10-27 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Artikelstatistik Jahresvergleich', 'Artikelstatistik Jahresvergleich', TO_TIMESTAMP('2026-10-27 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585567
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-27 10:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585567 AND AD_Language IN ('de_CH', 'de_DE')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585567, 'de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585567, 'de_DE')
;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Product Statistics Year Comparison', PrintName = 'Product Statistics Year Comparison', Updated = TO_TIMESTAMP('2026-10-27 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585567 AND AD_Language = 'en_US'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585567, 'en_US')
;

-- Menu entry
INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Process_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy)
VALUES ('R', 0, 585567, 542371 /*From ID Server*/, 0, 585690, TO_TIMESTAMP('2026-10-27 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Product_Statistics_Year_Comparison', 'Y', 'N', 'N', 'Y', 'N', 'Artikelstatistik Jahresvergleich', TO_TIMESTAMP('2026-10-27 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Menu_ID = 542371
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID)
;

SELECT update_menu_translation_from_ad_element(585567)
;

-- Tree: below "Berichte" (1000048), right after "Artikelstatistik (Excel)" (SeqNo 1): new node gets SeqNo 2, the following siblings move down by one
UPDATE AD_TreeNodeMM SET SeqNo = SeqNo + 1, Updated = now(), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Parent_ID = 1000048 AND SeqNo >= 2
;

INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y', now(), 100, now(), 100, t.AD_Tree_ID, 542371, 1000048, 2
FROM AD_Tree t
WHERE t.AD_Client_ID = 0 AND t.IsActive = 'Y' AND t.IsAllNodes = 'Y' AND t.AD_Table_ID = 116
  AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID = t.AD_Tree_ID AND e.Node_ID = 542371)
;
