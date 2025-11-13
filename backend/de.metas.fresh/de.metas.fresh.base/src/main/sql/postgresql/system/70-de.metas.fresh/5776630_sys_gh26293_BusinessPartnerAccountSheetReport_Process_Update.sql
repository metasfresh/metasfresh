-- Run mode: SWING_CLIENT

-- Process: BusinessPartnerAccountSheetReport(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: p_IsSOTrx
-- 2025-11-13T13:47:06.953Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540528,Updated=TO_TIMESTAMP('2025-11-13 14:47:06.953','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=541756
;

-- Process: BusinessPartnerAccountSheetReport(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: p_IsSOTrx
-- 2025-11-13T16:35:15.001Z
UPDATE AD_Process_Para SET DefaultValue='Y',Updated=TO_TIMESTAMP('2025-11-13 17:35:15.001','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=541756
;

-- 2025-11-13T10:10:17.254Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584207,0,'p_IsSOTrx',TO_TIMESTAMP('2025-11-13 11:10:16.484','YYYY-MM-DD HH24:MI:SS.US'),100,'Legt fest, welche Transaktionsarten im Bericht berücksichtigt werden: * Ja → Nur Verkaufstransaktionen * Nein → Nur Einkaufstransaktionen * Leer → Verkaufs- und Einkaufstransaktionen','D','Legt fest, welche Transaktionsarten im Bericht berücksichtigt werden:
* Ja → Nur Verkaufstransaktionen
* Nein → Nur Einkaufstransaktionen
* Leer → Verkaufs- und Einkaufstransaktionen','Y','Verkaufstransaktion','Verkaufstransaktion',TO_TIMESTAMP('2025-11-13 11:10:16.484','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-11-13T10:10:17.313Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584207 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: p_IsSOTrx
-- 2025-11-13T10:10:43.258Z
UPDATE AD_Element_Trl SET Description='Determines which transaction types are included in the report: * Yes → Only sales transactions * No → Only purchase transactions * Empty → Both sales and purchase transactions', Help='Determines which transaction types are included in the report:
* Yes → Only sales transactions
* No → Only purchase transactions
* Empty → Both sales and purchase transactions', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-13 11:10:43.258','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=584207 AND AD_Language='en_US'
;

-- 2025-11-13T10:10:43.406Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584207,'en_US')
;

-- Process: BusinessPartnerAccountSheetReport(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: p_IsSOTrx
-- 2025-11-13T10:11:00.095Z
UPDATE AD_Process_Para SET AD_Element_ID=584207, ColumnName='p_IsSOTrx',Updated=TO_TIMESTAMP('2025-11-13 11:11:00.095','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=541756
;

-- 2025-11-13T10:11:00.150Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(584207)
;

-- Value: BusinessPartnerAccountSheetReport
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-13T16:42:51.768Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM BusinessPartnerAccountSheetReport_Union(@C_BPartner_ID@, ''@DateFrom@''::date, ''@DateTo@''::date, @#AD_Client_ID@, @AD_Org_ID@, @p_IsSOTrx/quotedIfNotDefault/NULL@, ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2025-11-13 17:42:51.503','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=584661
;

-- 2025-11-13T10:17:45.774Z
UPDATE AD_Menu_Trl SET Name='Geschäftspartner Kontenblatt',Updated=TO_TIMESTAMP('2025-11-13 11:17:45.607','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Menu_ID=541440
;

-- 2025-11-13T10:17:45.830Z
UPDATE AD_Menu SET Name='Geschäftspartner Kontenblatt' WHERE AD_Menu_ID=541440
;

