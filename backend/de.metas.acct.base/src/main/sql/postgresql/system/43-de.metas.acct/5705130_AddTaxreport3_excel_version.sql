-- 2023-10-05T09:59:09.538Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aktueller Saldo', PrintName='Aktueller Saldo',Updated=TO_TIMESTAMP('2023-10-05 12:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=858 AND AD_Language='de_CH'
;

-- 2023-10-05T09:59:09.573Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(858,'de_CH') 
;

-- 2023-10-05T09:59:24.998Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aktueller Saldo', PrintName='Aktueller Saldo',Updated=TO_TIMESTAMP('2023-10-05 12:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=858 AND AD_Language='de_DE'
;

-- 2023-10-05T09:59:25.003Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(858,'de_DE') 
;

-- 2023-10-05T09:59:25.010Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(858,'de_DE') 
;

-- 2023-10-05T10:03:05.494Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582739,0,'Taxname',TO_TIMESTAMP('2023-10-05 13:03:05','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Tax','Steuer',TO_TIMESTAMP('2023-10-05 13:03:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T10:03:05.497Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582739 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-10-05T10:03:12.509Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Tax',Updated=TO_TIMESTAMP('2023-10-05 13:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582739 AND AD_Language='en_US'
;

-- 2023-10-05T10:03:12.513Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582739,'en_US') 
;

-- 2023-10-05T10:03:26.964Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuer',Updated=TO_TIMESTAMP('2023-10-05 13:03:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582739 AND AD_Language='de_DE'
;

-- 2023-10-05T10:03:26.967Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582739,'de_DE') 
;

-- 2023-10-05T10:03:26.969Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582739,'de_DE') 
;

-- 2023-10-05T10:03:42.198Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuer',Updated=TO_TIMESTAMP('2023-10-05 13:03:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582739 AND AD_Language='de_CH'
;

-- 2023-10-05T10:03:42.201Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582739,'de_CH') 
;

-- 2023-10-05T10:07:48.519Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582740,0,TO_TIMESTAMP('2023-10-05 13:07:48','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','TotalWithoutVATPerDoc','Total ohne MWST',TO_TIMESTAMP('2023-10-05 13:07:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T10:07:48.522Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582740 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-10-05T10:07:56.838Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Total ohne MWST',Updated=TO_TIMESTAMP('2023-10-05 13:07:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582740 AND AD_Language='de_DE'
;

-- 2023-10-05T10:07:56.841Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582740,'de_DE') 
;

-- 2023-10-05T10:07:56.843Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582740,'de_DE') 
;

-- 2023-10-05T10:08:11.204Z
UPDATE AD_Element_Trl SET PrintName='Total Without VAT Per Document',Updated=TO_TIMESTAMP('2023-10-05 13:08:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582740 AND AD_Language='en_US'
;

-- 2023-10-05T10:08:11.207Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582740,'en_US') 
;

-- 2023-10-05T10:08:23.302Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 13:08:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582740 AND AD_Language='de_CH'
;

-- 2023-10-05T10:08:23.305Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582740,'de_CH') 
;

-- 2023-10-05T10:09:29.423Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582741,0,TO_TIMESTAMP('2023-10-05 13:09:29','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','TotalWithoutVAT','TotalWithoutVAT',TO_TIMESTAMP('2023-10-05 13:09:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T10:09:29.425Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582741 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-10-05T10:09:40.478Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Total exkl. MwSt.',Updated=TO_TIMESTAMP('2023-10-05 13:09:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582741 AND AD_Language='de_DE'
;

-- 2023-10-05T10:09:40.481Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582741,'de_DE') 
;

-- 2023-10-05T10:09:40.483Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582741,'de_DE') 
;

-- 2023-10-05T10:10:02.118Z
UPDATE AD_Element_Trl SET PrintName='Total Without VAT',Updated=TO_TIMESTAMP('2023-10-05 13:10:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582741 AND AD_Language='en_US'
;

-- 2023-10-05T10:10:02.122Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582741,'en_US') 
;

-- 2023-10-05T10:10:09.996Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Total exkl. MwSt.',Updated=TO_TIMESTAMP('2023-10-05 13:10:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582741 AND AD_Language='de_CH'
;

-- 2023-10-05T10:10:09.998Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582741,'de_CH') 
;




-- 2023-10-05T13:45:47.794Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582742,0,TO_TIMESTAMP('2023-10-05 16:45:47','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','balance_alltimes','Bisherige Jahresbilanz',TO_TIMESTAMP('2023-10-05 16:45:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T13:45:47.800Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582742 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-10-05T13:45:54.387Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 16:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582742 AND AD_Language='de_CH'
;

-- 2023-10-05T13:45:54.390Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582742,'de_CH') 
;

-- 2023-10-05T13:45:55.958Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 16:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582742 AND AD_Language='de_DE'
;

-- 2023-10-05T13:45:55.960Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582742,'de_DE') 
;

-- 2023-10-05T13:45:55.962Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582742,'de_DE') 
;

-- 2023-10-05T13:46:09.111Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Balance from all times',Updated=TO_TIMESTAMP('2023-10-05 16:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582742 AND AD_Language='en_US'
;

-- 2023-10-05T13:46:09.113Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582742,'en_US') 
;

-- 2023-10-05T13:51:32.839Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585325,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-10-05 16:51:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','Y','N','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/reports/tax_accounting_v3/report.jasper','',0,'Mehrwertsteuer-Verprobung(Excel) 3','json','N','Y','xls','','JasperReportsSQL',TO_TIMESTAMP('2023-10-05 16:51:32','YYYY-MM-DD HH24:MI:SS'),100,'Mehrwertsteuer-Verprobung(Excel) 3')
;

-- 2023-10-05T13:51:32.843Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585325 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-10-05T13:51:38.345Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 16:51:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585325
;

-- 2023-10-05T13:51:41.059Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 16:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585325
;

-- 2023-10-05T13:51:56.446Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Tax Accounts Report (Excel) 3',Updated=TO_TIMESTAMP('2023-10-05 16:51:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585325
;

-- 2023-10-05T13:52:51.492Z
UPDATE AD_Process SET Classname='de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess', JasperReport='', Type='Excel',Updated=TO_TIMESTAMP('2023-10-05 16:52:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585325
;

-- 2023-10-05T13:54:22.661Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,585325,542708,15,'DateFrom',TO_TIMESTAMP('2023-10-05 16:54:22','YYYY-MM-DD HH24:MI:SS'),100,'@Date@','Startdatum eines Abschnittes','D',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','Y','N','Datum von',10,TO_TIMESTAMP('2023-10-05 16:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T13:54:22.663Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542708 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-05T13:54:41.060Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,585325,542710,15,'DateTo',TO_TIMESTAMP('2023-10-05 16:54:40','YYYY-MM-DD HH24:MI:SS'),100,'@Date@','Enddatum eines Abschnittes','U',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','Y','N','Datum bis',20,TO_TIMESTAMP('2023-10-05 16:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T13:54:41.061Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542710 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-05T13:55:20.255Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,585325,542711,30,'AD_Org_ID',TO_TIMESTAMP('2023-10-05 16:55:20','YYYY-MM-DD HH24:MI:SS'),100,'@#AD_Org_ID@','Organisatorische Einheit des Mandanten','U',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Organisation',30,TO_TIMESTAMP('2023-10-05 16:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T13:55:20.257Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542711 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-05T13:55:45.505Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542958,0,585325,542712,30,541071,'C_VAT_Code_ID',TO_TIMESTAMP('2023-10-05 16:55:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct',0,'Y','N','Y','N','N','N','VAT Code',40,TO_TIMESTAMP('2023-10-05 16:55:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T13:55:45.507Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542712 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-05T13:56:22.084Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,148,0,585325,542713,30,540322,'Account_ID',TO_TIMESTAMP('2023-10-05 16:56:21','YYYY-MM-DD HH24:MI:SS'),100,'Verwendetes Konto','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto',50,TO_TIMESTAMP('2023-10-05 16:56:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T13:56:22.086Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542713 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-05T13:56:52.974Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543019,0,585325,542714,20,'showdetails',TO_TIMESTAMP('2023-10-05 16:56:52','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,'Y','N','Y','N','Y','N','Show Details',60,TO_TIMESTAMP('2023-10-05 16:56:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T13:56:52.975Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542714 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-05T13:58:08.519Z
UPDATE AD_Process SET SQLStatement='SELECT *
FROM de_metas_acct.taxaccounts_report(@AD_Org_ID@, @Account_ID@, @C_VAT_Code_ID@, ''@DateFrom@''::date, ''@DateTo@''::date, @showdetails@)',Updated=TO_TIMESTAMP('2023-10-05 16:58:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585325
;

-- 2023-10-05T13:59:08.270Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582743,0,TO_TIMESTAMP('2023-10-05 16:59:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mehrwertsteuer-Verprobung(Excel) 3','Mehrwertsteuer-Verprobung(Excel) 3',TO_TIMESTAMP('2023-10-05 16:59:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T13:59:08.271Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582743 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-10-05T13:59:10.967Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 16:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582743 AND AD_Language='de_CH'
;

-- 2023-10-05T13:59:10.969Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582743,'de_CH') 
;

-- 2023-10-05T13:59:13.896Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 16:59:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582743 AND AD_Language='de_DE'
;

-- 2023-10-05T13:59:13.898Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582743,'de_DE') 
;

-- 2023-10-05T13:59:13.900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582743,'de_DE') 
;

-- 2023-10-05T13:59:31.030Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Tax Accounts Report (Excel) 3', PrintName='Tax Accounts Report (Excel) 3',Updated=TO_TIMESTAMP('2023-10-05 16:59:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582743 AND AD_Language='en_US'
;

-- 2023-10-05T13:59:31.032Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582743,'en_US') 
;

-- 2023-10-05T13:59:48.858Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,582743,542118,0,585325,TO_TIMESTAMP('2023-10-05 16:59:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Mehrwertsteuer-Verprobung(Excel) 3','Y','N','N','N','N','Mehrwertsteuer-Verprobung(Excel) 3',TO_TIMESTAMP('2023-10-05 16:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-05T13:59:48.860Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542118 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-10-05T13:59:48.862Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542118, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542118)
;

-- 2023-10-05T13:59:48.870Z
/* DDL */  select update_menu_translation_from_ad_element(582743) 
;

-- Reordering children of `Reports`
-- Node name: `Unallocated Payments (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.914Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542039 AND AD_Tree_ID=10
;

-- Node name: `Inventory Clearing (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.915Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542037 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.916Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- Node name: `Summary and Balance Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.917Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- Node name: `Profit And Loss Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.917Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- Node name: `Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.918Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- Node name: `Customer Item Statistics (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.919Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.920Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue customers (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.920Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- Node name: `Master Data Mutation Log (@PREFIX@de/metas/reports/bpartner_changes/report.jasper)`
-- 2023-10-05T13:59:48.921Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (@PREFIX@de/metas/reports/bank_statement/report.jasper)`
-- 2023-10-05T13:59:48.922Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- Node name: `Federal Statistical Office (@PREFIX@de/metas/reports/salesgroups/report.jasper)`
-- 2023-10-05T13:59:48.923Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (Accounting) (@PREFIX@de/metas/reports/fact_acct/report.jasper)`
-- 2023-10-05T13:59:48.924Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- Node name: `Balance Sheet (@PREFIX@de/metas/reports/balance_sheet/report.jasper)`
-- 2023-10-05T13:59:48.925Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing (@PREFIX@de/metas/docs/direct_costing/report.jasper)`
-- 2023-10-05T13:59:48.926Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion/report.jasper)`
-- 2023-10-05T13:59:48.926Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version with previous year (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion_with_last_year/report.jasper)`
-- 2023-10-05T13:59:48.927Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- Node name: `Tax Accounting Report (@PREFIX@de/metas/reports/tax_accounting/report.jasper)`
-- 2023-10-05T13:59:48.928Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- Node name: `Tax Accounting report 3 (@PREFIX@de/metas/reports/tax_accounting_v3/report.jasper)`
-- 2023-10-05T13:59:48.928Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542030 AND AD_Tree_ID=10
;

-- Node name: `Tax accounting report 2 (@PREFIX@de/metas/reports/tax_accounting_new/report.jasper)`
-- 2023-10-05T13:59:48.929Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- Node name: `Accounts Information (@PREFIX@de/metas/reports/account_info/report.jasper)`
-- 2023-10-05T13:59:48.930Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- Node name: `Balance (@PREFIX@de/metas/reports/de.metas.reports.saldoblilanz/report.jasper)`
-- 2023-10-05T13:59:48.931Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- Node name: `Open Items (@PREFIX@de/metas/reports/openitems/report.jasper)`
-- 2023-10-05T13:59:48.931Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- Node name: `Cost Center Movements (@PREFIX@de/metas/reports/movements/report.jasper)`
-- 2023-10-05T13:59:48.932Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

-- Node name: `Deposit in transit (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.933Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542036 AND AD_Tree_ID=10
;

-- Node name: `Payment selection (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.934Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542038 AND AD_Tree_ID=10
;

-- Node name: `Goods received not yet invoiced (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:48.934Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542040 AND AD_Tree_ID=10
;

-- Node name: `Mehrwertsteuer-Verprobung(Excel) 3`
-- 2023-10-05T13:59:48.935Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542118 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Unallocated Payments (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.342Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542039 AND AD_Tree_ID=10
;

-- Node name: `Inventory Clearing (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.343Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542037 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.343Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- Node name: `Summary and Balance Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.344Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- Node name: `Profit And Loss Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.345Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- Node name: `Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.346Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- Node name: `Customer Item Statistics (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.346Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.347Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue customers (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.348Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- Node name: `Master Data Mutation Log (@PREFIX@de/metas/reports/bpartner_changes/report.jasper)`
-- 2023-10-05T13:59:51.348Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (@PREFIX@de/metas/reports/bank_statement/report.jasper)`
-- 2023-10-05T13:59:51.349Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- Node name: `Federal Statistical Office (@PREFIX@de/metas/reports/salesgroups/report.jasper)`
-- 2023-10-05T13:59:51.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (Accounting) (@PREFIX@de/metas/reports/fact_acct/report.jasper)`
-- 2023-10-05T13:59:51.351Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- Node name: `Balance Sheet (@PREFIX@de/metas/reports/balance_sheet/report.jasper)`
-- 2023-10-05T13:59:51.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing (@PREFIX@de/metas/docs/direct_costing/report.jasper)`
-- 2023-10-05T13:59:51.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion/report.jasper)`
-- 2023-10-05T13:59:51.353Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version with previous year (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion_with_last_year/report.jasper)`
-- 2023-10-05T13:59:51.354Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- Node name: `Tax Accounting Report (@PREFIX@de/metas/reports/tax_accounting/report.jasper)`
-- 2023-10-05T13:59:51.355Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- Node name: `Tax Accounting report 3 (@PREFIX@de/metas/reports/tax_accounting_v3/report.jasper)`
-- 2023-10-05T13:59:51.355Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542030 AND AD_Tree_ID=10
;

-- Node name: `Mehrwertsteuer-Verprobung(Excel) 3`
-- 2023-10-05T13:59:51.356Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542118 AND AD_Tree_ID=10
;

-- Node name: `Tax accounting report 2 (@PREFIX@de/metas/reports/tax_accounting_new/report.jasper)`
-- 2023-10-05T13:59:51.357Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- Node name: `Accounts Information (@PREFIX@de/metas/reports/account_info/report.jasper)`
-- 2023-10-05T13:59:51.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- Node name: `Balance (@PREFIX@de/metas/reports/de.metas.reports.saldoblilanz/report.jasper)`
-- 2023-10-05T13:59:51.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- Node name: `Open Items (@PREFIX@de/metas/reports/openitems/report.jasper)`
-- 2023-10-05T13:59:51.359Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- Node name: `Cost Center Movements (@PREFIX@de/metas/reports/movements/report.jasper)`
-- 2023-10-05T13:59:51.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

-- Node name: `Deposit in transit (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542036 AND AD_Tree_ID=10
;

-- Node name: `Payment selection (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542038 AND AD_Tree_ID=10
;

-- Node name: `Goods received not yet invoiced (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-10-05T13:59:51.362Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542040 AND AD_Tree_ID=10
;


-- 2023-10-05T14:08:36.509Z
UPDATE AD_Process SET SQLStatement='SELECT *
FROM de_metas_acct.taxaccounts_report(@AD_Org_ID@, @Account_ID/NULL@, @C_VAT_Code_ID/NULL@, ''@DateFrom@''::date, ''@DateTo@''::date,''@showdetails/N@'')
;',Updated=TO_TIMESTAMP('2023-10-05 17:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585325
;

-- 2023-10-05T15:15:27.216Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545351,0,TO_TIMESTAMP('2023-10-05 18:15:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Keine MwSt.','I',TO_TIMESTAMP('2023-10-05 18:15:27','YYYY-MM-DD HH24:MI:SS'),100,'notax')
;

-- 2023-10-05T15:15:27.220Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545351 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-10-05T15:15:31.552Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 18:15:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545351
;

-- 2023-10-05T15:15:39.137Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No Tax',Updated=TO_TIMESTAMP('2023-10-05 18:15:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545351
;

-- 2023-10-05T15:15:46.077Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 18:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545351
;

