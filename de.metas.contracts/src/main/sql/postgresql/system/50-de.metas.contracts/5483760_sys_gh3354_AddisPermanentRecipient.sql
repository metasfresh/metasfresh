-- 2018-01-23T17:04:28.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543781,0,'isPermanentRecipient',TO_TIMESTAMP('2018-01-23 17:04:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Permanente Änderung','Permanente Änderung',TO_TIMESTAMP('2018-01-23 17:04:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-23T17:04:28.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543781 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-23T17:04:49.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-23 17:04:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Permanent Recipient',PrintName='Permanent Recipient' WHERE AD_Element_ID=543781 AND AD_Language='en_US'
;

-- 2018-01-23T17:04:49.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543781,'en_US') 
;

-- 2018-01-23T17:04:56.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsPermanentRecipient',Updated=TO_TIMESTAMP('2018-01-23 17:04:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543781
;

-- 2018-01-23T17:04:56.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPermanentRecipient', Name='Permanente Änderung', Description=NULL, Help=NULL WHERE AD_Element_ID=543781
;

-- 2018-01-23T17:04:56.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPermanentRecipient', Name='Permanente Änderung', Description=NULL, Help=NULL, AD_Element_ID=543781 WHERE UPPER(ColumnName)='ISPERMANENTRECIPIENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-23T17:04:56.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPermanentRecipient', Name='Permanente Änderung', Description=NULL, Help=NULL WHERE AD_Element_ID=543781 AND IsCentrallyMaintained='Y'
;

-- 2018-01-23T17:05:18.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543781,0,540838,541261,20,'IsPermanentRecipient',TO_TIMESTAMP('2018-01-23 17:05:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',0,'Y','N','Y','N','Y','N','Permanente Änderung',50,TO_TIMESTAMP('2018-01-23 17:05:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-23T17:05:18.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541261 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;




