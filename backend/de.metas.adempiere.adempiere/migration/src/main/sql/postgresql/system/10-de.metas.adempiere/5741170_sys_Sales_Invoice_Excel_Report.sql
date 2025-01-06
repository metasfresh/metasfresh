
-- Value: Sales Invoice Excel Report
-- 2024-12-05T15:12:38.369474986Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585439,'Y',TO_TIMESTAMP('2024-12-05 16:12:38.367','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y','Umsatz mit Gutschriften (Excel)','json','N','N','xls','Java',TO_TIMESTAMP('2024-12-05 16:12:38.367','YYYY-MM-DD HH24:MI:SS.US'),100,'Sales Invoice Excel Report')
;

-- 2024-12-05T15:12:38.379102174Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585439 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: Sales Invoice Excel Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T15:12:45.170574587Z
UPDATE AD_Process SET Classname='de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess', Type='Excel',Updated=TO_TIMESTAMP('2024-12-05 16:12:45.17','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;

-- Value: Sales Invoice Excel Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T15:13:00.095432695Z
UPDATE AD_Process SET SQLStatement='SELECT ',Updated=TO_TIMESTAMP('2024-12-05 16:13:00.095','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;

-- Value: Sales Invoice Excel Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T15:13:26.796168721Z
UPDATE AD_Process SET SQLStatement='SELECT sales_invoice_excel_report(@DateInvoiced@, @DeliveryDate@)',Updated=TO_TIMESTAMP('2024-12-05 16:13:26.796','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;




-- Value: Sales Invoice Excel Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T15:15:20.376155412Z
UPDATE AD_Process SET SQLStatement='SELECT sales_invoice_excel_report(`@DateInvoiced@`::date, `@DeliveryDate@`::date)',Updated=TO_TIMESTAMP('2024-12-05 16:15:20.375','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;








-- Process: Sales Invoice Excel Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateInvoiced
-- 2024-12-05T15:16:33.436181387Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,267,0,585439,542908,15,'DateInvoiced',TO_TIMESTAMP('2024-12-05 16:16:33.431','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum auf der Rechnung','U',0,'"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.','Y','N','Y','N','N','N','Rechnungsdatum',10,TO_TIMESTAMP('2024-12-05 16:16:33.431','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-05T15:16:33.437223117Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542908 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-12-05T15:16:33.439635389Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(267) 
;

-- Process: Sales Invoice Excel Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateInvoiced
-- 2024-12-05T15:16:42.996442487Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-05 16:16:42.996','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542908
;

-- Process: Sales Invoice Excel Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DeliveryDate
-- 2024-12-05T15:17:08.370200983Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541376,0,585439,542909,15,'DeliveryDate',TO_TIMESTAMP('2024-12-05 16:17:08.369','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat',0,'Y','N','Y','N','N','N','Lieferdatum',20,TO_TIMESTAMP('2024-12-05 16:17:08.369','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-05T15:17:08.371079231Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542909 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-12-05T15:17:08.371765978Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(541376) 
;

-- Process: Sales Invoice Excel Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DeliveryDate
-- 2024-12-05T15:17:13.947504481Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-05 16:17:13.947','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542909
;









-- 2024-12-05T15:19:58.340461079Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583387,0,TO_TIMESTAMP('2024-12-05 16:19:58.339','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Sales Invoice Excel Report','Sales Invoice Excel Report',TO_TIMESTAMP('2024-12-05 16:19:58.339','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-05T15:19:58.341886022Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583387 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-05T15:20:01.054347789Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-05 16:20:01.054','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387
;

-- 2024-12-05T15:20:01.055611581Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'de_DE') 
;

-- Value: Sales Invoice Excel Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T15:20:15.456819779Z
UPDATE AD_Process SET Value='Sales Invoice Excel Report (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:20:15.456','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;

-- 2024-12-05T15:20:26.284322394Z
UPDATE AD_Element SET Name='Sales Invoice Excel Report (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:20:26.284','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387
;

-- 2024-12-05T15:20:26.289098537Z
UPDATE AD_Element_Trl trl SET Name='Sales Invoice Excel Report (Excel)' WHERE AD_Element_ID=583387 AND AD_Language='de_DE'
;

-- 2024-12-05T15:20:26.298181441Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'de_DE') 
;

-- 2024-12-05T15:20:30.368583839Z
UPDATE AD_Element SET PrintName='Sales Invoice Excel Report (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:20:30.368','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387
;

-- 2024-12-05T15:20:30.369142824Z
UPDATE AD_Element_Trl trl SET PrintName='Sales Invoice Excel Report (Excel)' WHERE AD_Element_ID=583387 AND AD_Language='de_DE'
;

-- 2024-12-05T15:20:30.370265064Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'de_DE') 
;

-- Element: null
-- 2024-12-05T15:20:56.228167637Z
UPDATE AD_Element_Trl SET Name='Umsatz mit Gutschriften (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:20:56.227','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387 AND AD_Language='de_CH'
;

-- 2024-12-05T15:20:56.229625040Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'de_CH') 
;

-- Element: null
-- 2024-12-05T15:21:00.578930937Z
UPDATE AD_Element_Trl SET Name='Umsatz mit Gutschriften (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:21:00.578','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387 AND AD_Language='de_DE'
;

-- 2024-12-05T15:21:00.579492902Z
UPDATE AD_Element SET Name='Umsatz mit Gutschriften (Excel)', Updated=TO_TIMESTAMP('2024-12-05 16:21:00.579','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=583387
;

-- 2024-12-05T15:21:00.928195275Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583387,'de_DE') 
;

-- 2024-12-05T15:21:00.928981062Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'de_DE') 
;

-- Element: null
-- 2024-12-05T15:21:01.494349338Z
UPDATE AD_Element_Trl SET PrintName='Umsatz mit Gutschriften (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:21:01.494','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387 AND AD_Language='de_DE'
;

-- 2024-12-05T15:21:01.494979393Z
UPDATE AD_Element SET PrintName='Umsatz mit Gutschriften (Excel)', Updated=TO_TIMESTAMP('2024-12-05 16:21:01.494','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=583387
;

-- 2024-12-05T15:21:01.766387692Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583387,'de_DE') 
;

-- 2024-12-05T15:21:01.766859926Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'de_DE') 
;

-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T15:21:22.751883881Z
UPDATE AD_Process SET Value='Sales Invoice Report (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:21:22.751','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;

-- Element: null
-- 2024-12-05T15:21:28.892012831Z
UPDATE AD_Element_Trl SET Name='Sales Invoice Report (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:21:28.891','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387 AND AD_Language='en_US'
;

-- 2024-12-05T15:21:28.893068611Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'en_US') 
;

-- Element: null
-- 2024-12-05T15:21:29.667222286Z
UPDATE AD_Element_Trl SET PrintName='Sales Invoice Report (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:21:29.667','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387 AND AD_Language='en_US'
;

-- 2024-12-05T15:21:29.668490178Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'en_US') 
;

-- Element: null
-- 2024-12-05T15:21:33.165412788Z
UPDATE AD_Element_Trl SET Name='Sales Invoice Report (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:21:33.165','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387 AND AD_Language='fr_CH'
;

-- 2024-12-05T15:21:33.166117725Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'fr_CH') 
;

-- Element: null
-- 2024-12-05T15:21:33.778377924Z
UPDATE AD_Element_Trl SET PrintName='Sales Invoice Report (Excel)',Updated=TO_TIMESTAMP('2024-12-05 16:21:33.778','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583387 AND AD_Language='fr_CH'
;

-- 2024-12-05T15:21:33.779121890Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583387,'fr_CH') 
;






-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T15:55:16.250714723Z
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2024-12-05 16:55:16.25','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;






-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T16:49:03.374Z
UPDATE AD_Process SET SQLStatement='SELECT sales_invoice_excel_report(''@DateInvoiced@''::date, ''@DeliveryDate@''::date)',Updated=TO_TIMESTAMP('2024-12-05 18:49:03.372','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;


-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T16:54:05.797Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM sales_invoice_excel_report(''@DateInvoiced@''::date, ''@DeliveryDate@''::date)',Updated=TO_TIMESTAMP('2024-12-05 18:54:05.796','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;


-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T16:58:30.111Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM sales_invoice_excel_report(''@DateInvoiced/NULL@''::date, ''@DeliveryDate/NULL@''::date)',Updated=TO_TIMESTAMP('2024-12-05 18:58:30.109','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;


-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-05T17:07:50.550Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM sales_invoice_excel_report(@DateInvoiced/quotedIfNotDefault/NULL@, @DeliveryDate/quotedIfNotDefault/NULL@)',Updated=TO_TIMESTAMP('2024-12-05 19:07:50.549','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;


