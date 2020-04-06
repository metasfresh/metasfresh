-- 2019-09-20T09:13:28.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2091,0,541196,541592,10,'BankAccountNo',TO_TIMESTAMP('2019-09-20 12:13:28','YYYY-MM-DD HH24:MI:SS'),100,'Bank Account Number','de.metas.ui.web',0,'Y','N','Y','N','N','N','Bank Account No',50,TO_TIMESTAMP('2019-09-20 12:13:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T09:13:28.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541592 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-09-20T10:00:34.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577105,0,TO_TIMESTAMP('2019-09-20 13:00:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Will create a new bank account','Will create a new bank account',TO_TIMESTAMP('2019-09-20 13:00:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T10:00:34.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577105 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-20T10:00:55.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-20 13:00:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577105 AND AD_Language='en_US'
;

-- 2019-09-20T10:00:55.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577105,'en_US') 
;

-- 2019-09-20T10:01:00.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Neues Bankkonto wird erstellt', PrintName='Neues Bankkonto wird erstellt',Updated=TO_TIMESTAMP('2019-09-20 13:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577105 AND AD_Language='de_DE'
;

-- 2019-09-20T10:01:00.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577105,'de_DE') 
;

-- 2019-09-20T10:01:00.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577105,'de_DE') 
;

-- 2019-09-20T10:01:00.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Neues Bankkonto wird erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=577105
;

-- 2019-09-20T10:01:00.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Neues Bankkonto wird erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=577105 AND IsCentrallyMaintained='Y'
;

-- 2019-09-20T10:01:00.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Neues Bankkonto wird erstellt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577105) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577105)
;

-- 2019-09-20T10:01:00.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Neues Bankkonto wird erstellt', Name='Neues Bankkonto wird erstellt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577105)
;

-- 2019-09-20T10:01:00.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Neues Bankkonto wird erstellt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577105
;

-- 2019-09-20T10:01:00.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Neues Bankkonto wird erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID = 577105
;

-- 2019-09-20T10:01:00.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Neues Bankkonto wird erstellt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577105
;

-- 2019-09-20T10:01:14.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=31,Updated=TO_TIMESTAMP('2019-09-20 13:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541592
;

-- 2019-09-20T10:02:41.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Will_Create_a_new_Bank_Account',Updated=TO_TIMESTAMP('2019-09-20 13:02:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577105
;

-- 2019-09-20T10:02:41.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Will_Create_a_new_Bank_Account', Name='Neues Bankkonto wird erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=577105
;

-- 2019-09-20T10:02:41.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Will_Create_a_new_Bank_Account', Name='Neues Bankkonto wird erstellt', Description=NULL, Help=NULL, AD_Element_ID=577105 WHERE UPPER(ColumnName)='WILL_CREATE_A_NEW_BANK_ACCOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-20T10:02:41.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Will_Create_a_new_Bank_Account', Name='Neues Bankkonto wird erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=577105 AND IsCentrallyMaintained='Y'
;

-- 2019-09-20T10:04:38.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,577105,0,541196,541593,20,'Will_Create_a_new_Bank_Account',TO_TIMESTAMP('2019-09-20 13:04:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web',0,'Y','N','Y','N','N','N','Neues Bankkonto wird erstellt','1=1',32,TO_TIMESTAMP('2019-09-20 13:04:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T10:04:38.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541593 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-09-20T10:05:39.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Will create a new bank account',Updated=TO_TIMESTAMP('2019-09-20 13:05:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577105
;

-- 2019-09-20T10:05:39.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Will_Create_a_new_Bank_Account', Name='Neues Bankkonto wird erstellt', Description='Will create a new bank account', Help=NULL WHERE AD_Element_ID=577105
;

-- 2019-09-20T10:05:39.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Will_Create_a_new_Bank_Account', Name='Neues Bankkonto wird erstellt', Description='Will create a new bank account', Help=NULL, AD_Element_ID=577105 WHERE UPPER(ColumnName)='WILL_CREATE_A_NEW_BANK_ACCOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-20T10:05:39.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Will_Create_a_new_Bank_Account', Name='Neues Bankkonto wird erstellt', Description='Will create a new bank account', Help=NULL WHERE AD_Element_ID=577105 AND IsCentrallyMaintained='Y'
;

-- 2019-09-20T10:05:39.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Neues Bankkonto wird erstellt', Description='Will create a new bank account', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577105) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577105)
;

-- 2019-09-20T10:05:39.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Neues Bankkonto wird erstellt', Description='Will create a new bank account', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577105
;

-- 2019-09-20T10:05:39.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Neues Bankkonto wird erstellt', Description='Will create a new bank account', Help=NULL WHERE AD_Element_ID = 577105
;

-- 2019-09-20T10:05:39.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Neues Bankkonto wird erstellt', Description = 'Will create a new bank account', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577105
;

-- 2019-09-20T10:15:44.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@Will_Create_a_new_Bank_Account@=''Y''',Updated=TO_TIMESTAMP('2019-09-20 13:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541593
;

-- 2019-09-20T10:16:11.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@Will_Create_a_new_Bank_Account@=''Y''',Updated=TO_TIMESTAMP('2019-09-20 13:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541592
;

-- 2019-09-20T10:16:27.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@Will_Create_a_new_Bank_Account@=''N''',Updated=TO_TIMESTAMP('2019-09-20 13:16:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541590
;

-- 2019-09-20T10:21:44.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2019-09-20 13:21:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541593
;

-- 2019-09-20T10:27:14.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@Will_Create_a_new_Bank_Account@=Y',Updated=TO_TIMESTAMP('2019-09-20 13:27:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541593
;

-- 2019-09-20T10:27:18.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@Will_Create_a_new_Bank_Account@=Y',Updated=TO_TIMESTAMP('2019-09-20 13:27:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541592
;

-- 2019-09-20T10:27:23.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@Will_Create_a_new_Bank_Account@=N',Updated=TO_TIMESTAMP('2019-09-20 13:27:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541590
;

