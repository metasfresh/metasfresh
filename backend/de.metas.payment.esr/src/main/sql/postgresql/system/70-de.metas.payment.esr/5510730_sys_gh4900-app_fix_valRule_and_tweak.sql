-- 2019-01-24T15:42:12.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='1. V11-Datei importieren (aus Anhang)',Updated=TO_TIMESTAMP('2019-01-24 15:42:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540829
;

-- 2019-01-24T15:42:22.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-24 15:42:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='1. V11-Datei importieren (aus Anhang)' WHERE AD_Process_ID=540829 AND AD_Language='de_CH'
;

-- 2019-01-24T15:42:33.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-24 15:42:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='1. V11-Datei importieren (aus Anhang)' WHERE AD_Process_ID=540829 AND AD_Language='de_DE'
;

-- 2019-01-24T15:43:14.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-24 15:43:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Anhang',PrintName='Anhang' WHERE AD_Element_ID=543390 AND AD_Language='de_CH'
;

-- 2019-01-24T15:43:14.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543390,'de_CH') 
;

-- 2019-01-24T15:43:19.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-24 15:43:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Attachment',PrintName='Attachment' WHERE AD_Element_ID=543390 AND AD_Language='en_US'
;

-- 2019-01-24T15:43:19.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543390,'en_US') 
;

-- 2019-01-24T15:43:29.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-24 15:43:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Anhang',PrintName='Anhang' WHERE AD_Element_ID=543390 AND AD_Language='de_DE'
;

-- 2019-01-24T15:43:29.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543390,'de_DE') 
;

-- 2019-01-24T15:43:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543390,'de_DE') 
;

-- 2019-01-24T15:43:30.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_AttachmentEntry_ID', Name='Anhang', Description=NULL, Help=NULL WHERE AD_Element_ID=543390
;

-- 2019-01-24T15:43:30.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_AttachmentEntry_ID', Name='Anhang', Description=NULL, Help=NULL, AD_Element_ID=543390 WHERE UPPER(ColumnName)='AD_ATTACHMENTENTRY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-24T15:43:30.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_AttachmentEntry_ID', Name='Anhang', Description=NULL, Help=NULL WHERE AD_Element_ID=543390 AND IsCentrallyMaintained='Y'
;

-- 2019-01-24T15:43:30.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anhang', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543390) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543390)
;

-- 2019-01-24T15:43:30.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anhang', Name='Anhang' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543390)
;

-- 2019-01-24T15:43:30.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anhang', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543390
;

-- 2019-01-24T15:43:30.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anhang', Description=NULL, Help=NULL WHERE AD_Element_ID = 543390
;

-- 2019-01-24T15:43:30.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Anhang', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543390
;

-- 2019-01-24T15:45:15.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_AttachmentEntry_ID IN (
	select r.AD_AttachmentEntry_ID 
	from AD_Attachment_MultiRef r 
	where r.AD_Table_ID=get_Table_ID(''ESR_Import'') and r.Record_ID=@ESR_Import_ID/0@ )',Updated=TO_TIMESTAMP('2019-01-24 15:45:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540370
;

-- 2019-01-24T15:49:54.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2019-01-24 15:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3847
;

-- 2019-01-24T15:58:14.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540955,TO_TIMESTAMP('2019-01-24 15:58:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_AttachmentEntry',TO_TIMESTAMP('2019-01-24 15:58:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-01-24T15:58:14.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=540955 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-01-24T16:01:11.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=5,Updated=TO_TIMESTAMP('2019-01-24 16:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557040
;

-- 2019-01-24T16:02:29.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540955
;

-- 2019-01-24T16:02:29.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540955
;

