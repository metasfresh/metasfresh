

-- 2019-09-16T08:48:05.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541193,'Y','de.metas.handlingunits.expiry.process.M_HU_Update_MonthsUntilExpiry_Attribute','N',TO_TIMESTAMP('2019-09-16 11:48:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'Undate Months Until Expiry','N','N','Java',TO_TIMESTAMP('2019-09-16 11:48:05','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_Update_MonthsUntilExpiry_Attribute')
;

-- 2019-09-16T08:48:05.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541193 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;






-- 2019-09-16T08:46:04.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577068,0,TO_TIMESTAMP('2019-09-16 11:46:04','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Upadete Monthas Until Expiry','Upadete Monthas Until Expiry',TO_TIMESTAMP('2019-09-16 11:46:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-16T08:46:04.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577068 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- 2019-09-16T08:49:00.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-09-16 11:49:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577068
;

-- 2019-09-16T08:49:20.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Update Months Until Expiry', PrintName='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 11:49:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577068
;

-- 2019-09-16T08:49:20.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Update Months Until Expiry', Description=NULL, Help=NULL WHERE AD_Element_ID=577068
;

-- 2019-09-16T08:49:20.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Update Months Until Expiry', Description=NULL, Help=NULL WHERE AD_Element_ID=577068 AND IsCentrallyMaintained='Y'
;

-- 2019-09-16T08:49:20.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Update Months Until Expiry', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577068) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577068)
;

-- 2019-09-16T08:49:20.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Update Months Until Expiry', Name='Update Months Until Expiry' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577068)
;

-- 2019-09-16T08:49:20.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Update Months Until Expiry', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577068
;

-- 2019-09-16T08:49:20.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Update Months Until Expiry', Description=NULL, Help=NULL WHERE AD_Element_ID = 577068
;

-- 2019-09-16T08:49:20.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Update Months Until Expiry', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577068
;

-- 2019-09-16T08:49:29.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Upadete Months Until Expiry', PrintName='Upadete Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 11:49:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577068 AND AD_Language='de_CH'
;

-- 2019-09-16T08:49:29.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577068,'de_CH') 
;

-- 2019-09-16T08:49:33.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Upadete Months Until Expiry', PrintName='Upadete Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 11:49:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577068 AND AD_Language='de_DE'
;

-- 2019-09-16T08:49:33.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577068,'de_DE') 
;

-- 2019-09-16T08:49:33.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577068,'de_DE') 
;

-- 2019-09-16T08:49:33.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Upadete Months Until Expiry', Description=NULL, Help=NULL WHERE AD_Element_ID=577068
;

-- 2019-09-16T08:49:33.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Upadete Months Until Expiry', Description=NULL, Help=NULL WHERE AD_Element_ID=577068 AND IsCentrallyMaintained='Y'
;

-- 2019-09-16T08:49:33.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Upadete Months Until Expiry', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577068) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577068)
;

-- 2019-09-16T08:49:33.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Upadete Months Until Expiry', Name='Upadete Months Until Expiry' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577068)
;

-- 2019-09-16T08:49:33.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Upadete Months Until Expiry', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577068
;

-- 2019-09-16T08:49:33.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Upadete Months Until Expiry', Description=NULL, Help=NULL WHERE AD_Element_ID = 577068
;

-- 2019-09-16T08:49:33.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Upadete Months Until Expiry', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577068
;

-- 2019-09-16T08:49:37.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Upadete Months Until Expiry', PrintName='Upadete Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 11:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577068 AND AD_Language='en_US'
;

-- 2019-09-16T08:49:37.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577068,'en_US') 
;

-- 2019-09-16T08:49:57.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Update Months Until Expiry', PrintName='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 11:49:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577068 AND AD_Language='nl_NL'
;

-- 2019-09-16T08:49:57.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577068,'nl_NL') 
;

-- 2019-09-16T08:50:00.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Update Months Until Expiry', PrintName='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 11:50:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577068 AND AD_Language='en_US'
;

-- 2019-09-16T08:50:00.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577068,'en_US') 
;

-- 2019-09-16T08:50:03.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Update Months Until Expiry', PrintName='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 11:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577068 AND AD_Language='de_DE'
;

-- 2019-09-16T08:50:03.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577068,'de_DE') 
;

-- 2019-09-16T08:50:03.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577068,'de_DE') 
;

-- 2019-09-16T08:50:03.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Update Months Until Expiry', Description=NULL, Help=NULL WHERE AD_Element_ID=577068
;

-- 2019-09-16T08:50:03.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Update Months Until Expiry', Description=NULL, Help=NULL WHERE AD_Element_ID=577068 AND IsCentrallyMaintained='Y'
;

-- 2019-09-16T08:50:03.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Update Months Until Expiry', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577068) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577068)
;

-- 2019-09-16T08:50:03.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Update Months Until Expiry', Name='Update Months Until Expiry' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577068)
;

-- 2019-09-16T08:50:03.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Update Months Until Expiry', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577068
;

-- 2019-09-16T08:50:03.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Update Months Until Expiry', Description=NULL, Help=NULL WHERE AD_Element_ID = 577068
;

-- 2019-09-16T08:50:03.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Update Months Until Expiry', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577068
;

-- 2019-09-16T08:50:07.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Update Months Until Expiry', PrintName='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 11:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577068 AND AD_Language='de_CH'
;

-- 2019-09-16T08:50:07.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577068,'de_CH') 
;




-- 2019-09-16T08:51:03.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541193,540516,540744,540189,TO_TIMESTAMP('2019-09-16 11:51:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-09-16 11:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;


-- 2019-09-16T09:48:59.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N',Updated=TO_TIMESTAMP('2019-09-16 12:48:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540744
;

-- 2019-09-16T09:49:41.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2019-09-16 12:49:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540744
;

-- 2019-09-16T09:49:54.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 12:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541193
;

-- 2019-09-16T13:19:22.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,0,541193,1000000,550056,TO_TIMESTAMP('2019-09-16 16:19:22','YYYY-MM-DD HH24:MI:SS'),100,'23 03 * * *','Update the number of months until the HU expires','de.metas.swat',0,'D','Y','N',7,'N','M_HU_Update_MonthsUntilExpiry_Attribute','N','P','C','NEW',100,TO_TIMESTAMP('2019-09-16 16:19:22','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2019-09-16T14:18:33.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 17:18:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541193
;

-- 2019-09-16T14:18:37.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 17:18:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541193
;

-- 2019-09-16T14:18:40.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 17:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541193
;

-- 2019-09-16T14:18:43.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Update Months Until Expiry',Updated=TO_TIMESTAMP('2019-09-16 17:18:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541193
;

-- 2019-09-16T14:21:15.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='MHD Restlaufzeit in Monaten Aktualisieren',Updated=TO_TIMESTAMP('2019-09-16 17:21:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541193
;

-- 2019-09-16T14:21:18.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='MHD Restlaufzeit in Monaten Aktualisieren',Updated=TO_TIMESTAMP('2019-09-16 17:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541193
;

-- 2019-09-16T14:59:22.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name=' MHD Restlaufzeit in Monaten aktualisieren',Updated=TO_TIMESTAMP('2019-09-16 17:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541193
;

-- 2019-09-16T14:59:28.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='MHD Restlaufzeit in Monaten aktualisieren',Updated=TO_TIMESTAMP('2019-09-16 17:59:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541193
;

-- 2019-09-16T14:59:33.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='MHD Restlaufzeit in Monaten aktualisieren',Updated=TO_TIMESTAMP('2019-09-16 17:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541193
;

