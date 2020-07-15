-- 2020-02-10T08:13:26.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,584650,'Y','de.metas.impexp.excel.process.ExportToExcelProcess','N',TO_TIMESTAMP('2020-02-10 10:13:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'ProfitAndLossReport','N','N','SELECT * FROM ProfitAndLossReport(''1993-01-01''::Timestamp, ''2992-01-01''::Timestamp);','Excel',TO_TIMESTAMP('2020-02-10 10:13:26','YYYY-MM-DD HH24:MI:SS'),100,'ProfitAndLossReport')
;

-- 2020-02-10T08:13:26.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584650 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-02-10T08:14:16.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584650,188,540788,TO_TIMESTAMP('2020-02-10 10:14:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2020-02-10 10:14:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2020-02-10T08:17:28.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577520,0,'Three_Years_Ago',TO_TIMESTAMP('2020-02-10 10:17:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Three Years Ago','Three Years Ago',TO_TIMESTAMP('2020-02-10 10:17:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T08:17:28.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577520 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-10T08:22:58.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vor-Vor-Vorjah', PrintName='Vor-Vor-Vorjah',Updated=TO_TIMESTAMP('2020-02-10 10:22:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577520 AND AD_Language='de_CH'
;

-- 2020-02-10T08:22:58.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577520,'de_CH') 
;

-- 2020-02-10T08:23:10.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vor-Vor-Vorjah', PrintName='Vor-Vor-Vorjah',Updated=TO_TIMESTAMP('2020-02-10 10:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577520 AND AD_Language='de_DE'
;

-- 2020-02-10T08:23:10.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577520,'de_DE') 
;

-- 2020-02-10T08:23:10.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577520,'de_DE') 
;

-- 2020-02-10T08:23:10.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Three_Years_Ago', Name='Vor-Vor-Vorjah', Description=NULL, Help=NULL WHERE AD_Element_ID=577520
;

-- 2020-02-10T08:23:10.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Three_Years_Ago', Name='Vor-Vor-Vorjah', Description=NULL, Help=NULL, AD_Element_ID=577520 WHERE UPPER(ColumnName)='THREE_YEARS_AGO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-10T08:23:10.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Three_Years_Ago', Name='Vor-Vor-Vorjah', Description=NULL, Help=NULL WHERE AD_Element_ID=577520 AND IsCentrallyMaintained='Y'
;

-- 2020-02-10T08:23:10.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vor-Vor-Vorjah', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577520) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577520)
;

-- 2020-02-10T08:23:10.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vor-Vor-Vorjah', Name='Vor-Vor-Vorjah' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577520)
;

-- 2020-02-10T08:23:10.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vor-Vor-Vorjah', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577520
;

-- 2020-02-10T08:23:10.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vor-Vor-Vorjah', Description=NULL, Help=NULL WHERE AD_Element_ID = 577520
;

-- 2020-02-10T08:23:10.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vor-Vor-Vorjah', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577520
;

-- 2020-02-10T08:23:13.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-10 10:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577520 AND AD_Language='en_US'
;

-- 2020-02-10T08:23:13.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577520,'en_US') 
;

-- 2020-02-10T08:30:16.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577521,0,'Two_Years_Ago',TO_TIMESTAMP('2020-02-10 10:30:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Two Years Ago','Two Years Ago',TO_TIMESTAMP('2020-02-10 10:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T08:30:16.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577521 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-10T08:30:34.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vor-Vorjah', PrintName='Vor-Vorjah',Updated=TO_TIMESTAMP('2020-02-10 10:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577521 AND AD_Language='de_CH'
;

-- 2020-02-10T08:30:34.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577521,'de_CH') 
;

-- 2020-02-10T08:30:40.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vor-Vorjah', PrintName='Vor-Vorjah',Updated=TO_TIMESTAMP('2020-02-10 10:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577521 AND AD_Language='de_DE'
;

-- 2020-02-10T08:30:40.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577521,'de_DE') 
;

-- 2020-02-10T08:30:40.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577521,'de_DE') 
;

-- 2020-02-10T08:30:40.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Two_Years_Ago', Name='Vor-Vorjah', Description=NULL, Help=NULL WHERE AD_Element_ID=577521
;

-- 2020-02-10T08:30:40.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Two_Years_Ago', Name='Vor-Vorjah', Description=NULL, Help=NULL, AD_Element_ID=577521 WHERE UPPER(ColumnName)='TWO_YEARS_AGO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-10T08:30:40.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Two_Years_Ago', Name='Vor-Vorjah', Description=NULL, Help=NULL WHERE AD_Element_ID=577521 AND IsCentrallyMaintained='Y'
;

-- 2020-02-10T08:30:40.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vor-Vorjah', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577521) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577521)
;

-- 2020-02-10T08:30:40.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vor-Vorjah', Name='Vor-Vorjah' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577521)
;

-- 2020-02-10T08:30:40.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vor-Vorjah', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577521
;

-- 2020-02-10T08:30:40.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vor-Vorjah', Description=NULL, Help=NULL WHERE AD_Element_ID = 577521
;

-- 2020-02-10T08:30:40.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vor-Vorjah', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577521
;

-- 2020-02-10T08:30:46.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-10 10:30:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577521 AND AD_Language='en_US'
;

-- 2020-02-10T08:30:46.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577521,'en_US') 
;

-- 2020-02-10T08:31:17.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577522,0,'One_Year_Ago',TO_TIMESTAMP('2020-02-10 10:31:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','One Year Ago','One Year Ago',TO_TIMESTAMP('2020-02-10 10:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T08:31:17.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577522 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-10T08:32:56.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vorjahr', PrintName='Vorjahr',Updated=TO_TIMESTAMP('2020-02-10 10:32:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577522 AND AD_Language='de_CH'
;

-- 2020-02-10T08:32:56.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577522,'de_CH') 
;

-- 2020-02-10T08:33:00.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vorjahr', PrintName='Vorjahr',Updated=TO_TIMESTAMP('2020-02-10 10:33:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577522 AND AD_Language='de_DE'
;

-- 2020-02-10T08:33:00.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577522,'de_DE') 
;

-- 2020-02-10T08:33:00.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577522,'de_DE') 
;

-- 2020-02-10T08:33:00.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='One_Year_Ago', Name='Vorjahr', Description=NULL, Help=NULL WHERE AD_Element_ID=577522
;

-- 2020-02-10T08:33:00.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='One_Year_Ago', Name='Vorjahr', Description=NULL, Help=NULL, AD_Element_ID=577522 WHERE UPPER(ColumnName)='ONE_YEAR_AGO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-10T08:33:00.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='One_Year_Ago', Name='Vorjahr', Description=NULL, Help=NULL WHERE AD_Element_ID=577522 AND IsCentrallyMaintained='Y'
;

-- 2020-02-10T08:33:00.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorjahr', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577522) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577522)
;

-- 2020-02-10T08:33:00.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vorjahr', Name='Vorjahr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577522)
;

-- 2020-02-10T08:33:00.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vorjahr', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577522
;

-- 2020-02-10T08:33:00.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vorjahr', Description=NULL, Help=NULL WHERE AD_Element_ID = 577522
;

-- 2020-02-10T08:33:00.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vorjahr', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577522
;

-- 2020-02-10T08:33:01.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-10 10:33:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577522 AND AD_Language='en_US'
;

-- 2020-02-10T08:33:01.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577522,'en_US') 
;

-- 2020-02-10T08:33:28.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vor-Vor-Vorjahr', PrintName='Vor-Vor-Vorjahr',Updated=TO_TIMESTAMP('2020-02-10 10:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577520 AND AD_Language='de_CH'
;

-- 2020-02-10T08:33:28.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577520,'de_CH') 
;

-- 2020-02-10T08:33:32.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vor-Vor-Vorjahr', PrintName='Vor-Vor-Vorjahr',Updated=TO_TIMESTAMP('2020-02-10 10:33:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577520 AND AD_Language='de_DE'
;

-- 2020-02-10T08:33:32.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577520,'de_DE') 
;

-- 2020-02-10T08:33:32.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577520,'de_DE') 
;

-- 2020-02-10T08:33:32.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Three_Years_Ago', Name='Vor-Vor-Vorjahr', Description=NULL, Help=NULL WHERE AD_Element_ID=577520
;

-- 2020-02-10T08:33:32.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Three_Years_Ago', Name='Vor-Vor-Vorjahr', Description=NULL, Help=NULL, AD_Element_ID=577520 WHERE UPPER(ColumnName)='THREE_YEARS_AGO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-10T08:33:32.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Three_Years_Ago', Name='Vor-Vor-Vorjahr', Description=NULL, Help=NULL WHERE AD_Element_ID=577520 AND IsCentrallyMaintained='Y'
;

-- 2020-02-10T08:33:32.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vor-Vor-Vorjahr', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577520) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577520)
;

-- 2020-02-10T08:33:32.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vor-Vor-Vorjahr', Name='Vor-Vor-Vorjahr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577520)
;

-- 2020-02-10T08:33:32.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vor-Vor-Vorjahr', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577520
;

-- 2020-02-10T08:33:32.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vor-Vor-Vorjahr', Description=NULL, Help=NULL WHERE AD_Element_ID = 577520
;

-- 2020-02-10T08:33:32.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vor-Vor-Vorjahr', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577520
;

-- 2020-02-10T08:33:49.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vor-Vorjahr', PrintName='Vor-Vorjahr',Updated=TO_TIMESTAMP('2020-02-10 10:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577521 AND AD_Language='de_CH'
;

-- 2020-02-10T08:33:49.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577521,'de_CH') 
;

-- 2020-02-10T08:33:53.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vor-Vorjahr', PrintName='Vor-Vorjahr',Updated=TO_TIMESTAMP('2020-02-10 10:33:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577521 AND AD_Language='de_DE'
;

-- 2020-02-10T08:33:53.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577521,'de_DE') 
;

-- 2020-02-10T08:33:53.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577521,'de_DE') 
;

-- 2020-02-10T08:33:53.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Two_Years_Ago', Name='Vor-Vorjahr', Description=NULL, Help=NULL WHERE AD_Element_ID=577521
;

-- 2020-02-10T08:33:53.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Two_Years_Ago', Name='Vor-Vorjahr', Description=NULL, Help=NULL, AD_Element_ID=577521 WHERE UPPER(ColumnName)='TWO_YEARS_AGO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-10T08:33:53.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Two_Years_Ago', Name='Vor-Vorjahr', Description=NULL, Help=NULL WHERE AD_Element_ID=577521 AND IsCentrallyMaintained='Y'
;

-- 2020-02-10T08:33:53.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vor-Vorjahr', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577521) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577521)
;

-- 2020-02-10T08:33:53.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vor-Vorjahr', Name='Vor-Vorjahr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577521)
;

-- 2020-02-10T08:33:53.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vor-Vorjahr', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577521
;

-- 2020-02-10T08:33:53.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vor-Vorjahr', Description=NULL, Help=NULL WHERE AD_Element_ID = 577521
;

-- 2020-02-10T08:33:53.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vor-Vorjahr', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577521
;

-- 2020-02-10T08:34:20.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577523,0,'Current_Period',TO_TIMESTAMP('2020-02-10 10:34:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Current Period','Current Period',TO_TIMESTAMP('2020-02-10 10:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T08:34:20.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577523 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-10T08:35:05.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aktueller Zeitraum', PrintName='Aktueller Zeitraum',Updated=TO_TIMESTAMP('2020-02-10 10:35:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577523 AND AD_Language='de_CH'
;

-- 2020-02-10T08:35:05.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577523,'de_CH') 
;

-- 2020-02-10T08:35:08.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aktueller Zeitraum', PrintName='Aktueller Zeitraum',Updated=TO_TIMESTAMP('2020-02-10 10:35:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577523 AND AD_Language='de_DE'
;

-- 2020-02-10T08:35:08.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577523,'de_DE') 
;

-- 2020-02-10T08:35:08.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577523,'de_DE') 
;

-- 2020-02-10T08:35:08.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Current_Period', Name='Aktueller Zeitraum', Description=NULL, Help=NULL WHERE AD_Element_ID=577523
;

-- 2020-02-10T08:35:08.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Current_Period', Name='Aktueller Zeitraum', Description=NULL, Help=NULL, AD_Element_ID=577523 WHERE UPPER(ColumnName)='CURRENT_PERIOD' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-10T08:35:08.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Current_Period', Name='Aktueller Zeitraum', Description=NULL, Help=NULL WHERE AD_Element_ID=577523 AND IsCentrallyMaintained='Y'
;

-- 2020-02-10T08:35:08.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aktueller Zeitraum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577523) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577523)
;

-- 2020-02-10T08:35:08.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Aktueller Zeitraum', Name='Aktueller Zeitraum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577523)
;

-- 2020-02-10T08:35:08.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Aktueller Zeitraum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577523
;

-- 2020-02-10T08:35:08.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Aktueller Zeitraum', Description=NULL, Help=NULL WHERE AD_Element_ID = 577523
;

-- 2020-02-10T08:35:08.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Aktueller Zeitraum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577523
;

-- 2020-02-10T08:35:10.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-10 10:35:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577523 AND AD_Language='en_US'
;

-- 2020-02-10T08:35:10.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577523,'en_US') 
;

-- 2020-02-10T09:25:53.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,584650,541703,15,'DateFrom',TO_TIMESTAMP('2020-02-10 11:25:53','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes','D',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','N','N','Datum von',10,TO_TIMESTAMP('2020-02-10 11:25:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T09:25:53.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541703 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-10T09:26:09.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,584650,541704,15,'DateTo',TO_TIMESTAMP('2020-02-10 11:26:09','YYYY-MM-DD HH24:MI:SS'),100,'Enddatum eines Abschnittes','D',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','N','N','Datum bis',20,TO_TIMESTAMP('2020-02-10 11:26:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T09:26:09.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541704 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-10T09:27:46.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM ProfitAndLossReport(''@DateFrom/1993-01-01@''::Timestamp, ''@DateTo/2992-01-01@''::Timestamp);',Updated=TO_TIMESTAMP('2020-02-10 11:27:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584650
;

-- 2020-02-10T09:43:39.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Profit And Loss Report',Updated=TO_TIMESTAMP('2020-02-10 11:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584650
;

-- 2020-02-10T10:11:20.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Gewinn- und Verlustbericht',Updated=TO_TIMESTAMP('2020-02-10 12:11:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584650
;

-- 2020-02-10T10:11:24.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Gewinn- und Verlustbericht',Updated=TO_TIMESTAMP('2020-02-10 12:11:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584650
;

