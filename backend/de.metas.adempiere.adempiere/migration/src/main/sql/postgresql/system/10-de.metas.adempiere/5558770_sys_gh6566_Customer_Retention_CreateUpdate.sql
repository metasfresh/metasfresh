-- 2020-05-06T13:49:05.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.contracts.impl.CustomerRetentionRepository', Name='C_Customer_Retention_CreateUpdate', Value='C_Customer_Retention_CreateUpdate',Updated=TO_TIMESTAMP('2020-05-06 16:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541037
;

-- 2020-05-06T13:49:05.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='C_Customer_Retention_CreateUpdate',Updated=TO_TIMESTAMP('2020-05-06 16:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541170
;

-- 2020-05-06T13:56:14.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=541169
;

-- 2020-05-06T13:56:14.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=541169
;

-- 2020-05-06T13:56:14.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=541169 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;











-- 2020-05-06T14:00:12.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='C_BPartner_CustomerRetention_CreateMissing',Updated=TO_TIMESTAMP('2020-05-06 17:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541037
;

-- 2020-05-06T14:00:26.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='C_BPartner_CustomerRetention_CreateMissing',Updated=TO_TIMESTAMP('2020-05-06 17:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541037
;

-- 2020-05-06T14:00:31.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='C_Customer_Retention_CreateUpdate',Updated=TO_TIMESTAMP('2020-05-06 17:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541037
;

-- 2020-05-06T14:00:34.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='C_Customer_Retention_CreateUpdate',Updated=TO_TIMESTAMP('2020-05-06 17:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541037
;

-- 2020-05-06T14:00:37.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='C_Customer_Retention_CreateUpdate',Updated=TO_TIMESTAMP('2020-05-06 17:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541037
;

-- 2020-05-06T14:00:40.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='C_Customer_Retention_CreateUpdate',Updated=TO_TIMESTAMP('2020-05-06 17:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541037
;




delete from ad_scheduler where name = 'C_BPartner_Update_TimeSpan';




-- 2020-05-06T14:03:18.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=541036
;

-- 2020-05-06T14:03:18.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=541036
;

-- 2020-05-06T14:08:21.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,541037,0,550063,TO_TIMESTAMP('2020-05-06 17:08:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat',1,'D','Y','N',7,'N','C_Customer_Retention_CreateUpdate','N','P','F','NEW',100,TO_TIMESTAMP('2020-05-06 17:08:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-06T14:14:58.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Create/Update Customer Retentions',Updated=TO_TIMESTAMP('2020-05-06 17:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541037
;

-- 2020-05-06T14:14:58.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Create/Update Customer Retentions',Updated=TO_TIMESTAMP('2020-05-06 17:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541170
;

-- 2020-05-06T14:15:39.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.contracts.process.C_Customer_Retention_CreateUpdate',Updated=TO_TIMESTAMP('2020-05-06 17:15:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541037
;

-- 2020-05-06T14:16:13.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Create/Update Customer Retentions',Updated=TO_TIMESTAMP('2020-05-06 17:16:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541037
;

-- 2020-05-06T14:16:29.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Kundenbindungen erstellen/aktualisieren',Updated=TO_TIMESTAMP('2020-05-06 17:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541037
;

-- 2020-05-06T14:16:35.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Kundenbindungen erstellen/aktualisieren',Updated=TO_TIMESTAMP('2020-05-06 17:16:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541037
;

-- 2020-05-06T14:16:38.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-06 17:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541037
;

-- 2020-05-06T14:31:35.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Create/Update Customer Retentions', PrintName='Create/Update Customer Retentions',Updated=TO_TIMESTAMP('2020-05-06 17:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575921 AND AD_Language='en_US'
;

-- 2020-05-06T14:31:35.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575921,'en_US') 
;

-- 2020-05-06T14:31:53.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kundenbindungen erstellen/aktualisieren', PrintName='Kundenbindungen erstellen/aktualisieren',Updated=TO_TIMESTAMP('2020-05-06 17:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575921 AND AD_Language='de_DE'
;

-- 2020-05-06T14:31:53.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575921,'de_DE') 
;

-- 2020-05-06T14:31:53.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575921,'de_DE') 
;

-- 2020-05-06T14:31:53.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Kundenbindungen erstellen/aktualisieren', Description=NULL, Help=NULL WHERE AD_Element_ID=575921
;

-- 2020-05-06T14:31:53.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Kundenbindungen erstellen/aktualisieren', Description=NULL, Help=NULL WHERE AD_Element_ID=575921 AND IsCentrallyMaintained='Y'
;

-- 2020-05-06T14:31:53.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kundenbindungen erstellen/aktualisieren', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575921) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575921)
;

-- 2020-05-06T14:31:53.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kundenbindungen erstellen/aktualisieren', Name='Kundenbindungen erstellen/aktualisieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575921)
;

-- 2020-05-06T14:31:53.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kundenbindungen erstellen/aktualisieren', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575921
;

-- 2020-05-06T14:31:53.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kundenbindungen erstellen/aktualisieren', Description=NULL, Help=NULL WHERE AD_Element_ID = 575921
;

-- 2020-05-06T14:31:53.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kundenbindungen erstellen/aktualisieren', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575921
;

-- 2020-05-06T14:31:56.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kundenbindungen erstellen/aktualisieren', PrintName='Kundenbindungen erstellen/aktualisieren',Updated=TO_TIMESTAMP('2020-05-06 17:31:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575921 AND AD_Language='de_CH'
;

-- 2020-05-06T14:31:56.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575921,'de_CH') 
;

