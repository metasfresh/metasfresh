-- 2019-10-22T10:55:30.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577237,0,TO_TIMESTAMP('2019-10-22 13:55:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Entfernen aller bestehenden Kampagnenteilnehmer','Entfernen aller bestehenden Kampagnenteilnehmer',TO_TIMESTAMP('2019-10-22 13:55:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T10:55:30.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577237 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-22T10:55:54.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Remove all existing campaign participants', PrintName='Remove all existing campaign participants',Updated=TO_TIMESTAMP('2019-10-22 13:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577237 AND AD_Language='en_US'
;

-- 2019-10-22T10:55:55.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577237,'en_US') 
;

-- 2019-10-22T10:56:46.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsRemoveAllExistingContactsFromCampaign',Updated=TO_TIMESTAMP('2019-10-22 13:56:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577237
;

-- 2019-10-22T10:56:46.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsRemoveAllExistingContactsFromCampaign', Name='Entfernen aller bestehenden Kampagnenteilnehmer', Description=NULL, Help=NULL WHERE AD_Element_ID=577237
;

-- 2019-10-22T10:56:46.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRemoveAllExistingContactsFromCampaign', Name='Entfernen aller bestehenden Kampagnenteilnehmer', Description=NULL, Help=NULL, AD_Element_ID=577237 WHERE UPPER(ColumnName)='ISREMOVEALLEXISTINGCONTACTSFROMCAMPAIGN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T10:56:46.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRemoveAllExistingContactsFromCampaign', Name='Entfernen aller bestehenden Kampagnenteilnehmer', Description=NULL, Help=NULL WHERE AD_Element_ID=577237 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T10:57:00.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Remove All Existing Contacts From Campaign', PrintName='Remove All Existing Contacts From Campaign',Updated=TO_TIMESTAMP('2019-10-22 13:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577237 AND AD_Language='en_US'
;

-- 2019-10-22T10:57:00.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577237,'en_US') 
;

-- 2019-10-22T10:57:29.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577237,0,540960,541632,20,'IsRemoveAllExistingContactsFromCampaign',TO_TIMESTAMP('2019-10-22 13:57:29','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.marketing.base',0,'Y','N','Y','N','Y','N','Entfernen aller bestehenden Kampagnenteilnehmer',20,TO_TIMESTAMP('2019-10-22 13:57:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T10:57:30.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541632 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-10-22T13:42:27.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET IsTranslated='Y', Name='Remove All Existing Contacts From Campaign',Updated=TO_TIMESTAMP('2019-10-22 16:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=541632
;

-- 2019-10-22T14:51:53.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577237,0,540990,541633,20,'IsRemoveAllExistingContactsFromCampaign',TO_TIMESTAMP('2019-10-22 17:51:52','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.marketing.base',0,'Y','N','Y','N','Y','N','Entfernen aller bestehenden Kampagnenteilnehmer',30,TO_TIMESTAMP('2019-10-22 17:51:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T14:51:53.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541633 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

