-- 2019-11-13T13:03:40.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-13 15:03:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577263
;

-- 2019-11-13T13:03:40.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577263,'de_CH') 
;

-- 2019-11-13T13:03:43.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-13 15:03:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577263
;

-- 2019-11-13T13:03:43.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577263,'de_DE') 
;

-- 2019-11-13T13:03:43.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577263,'de_DE') 
;

-- 2019-11-13T13:03:45.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-13 15:03:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577263
;

-- 2019-11-13T13:03:45.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577263,'en_US') 
;

-- 2019-11-13T13:03:49.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-13 15:03:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Element_ID=577263
;

-- 2019-11-13T13:03:49.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577263,'nl_NL') 
;

-- 2019-11-13T13:04:13.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Delis Passwort', IsTranslated='Y', Name='Delis Passwort',Updated=TO_TIMESTAMP('2019-11-13 15:04:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577264
;

-- 2019-11-13T13:04:13.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577264,'de_CH') 
;

-- 2019-11-13T13:04:19.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Delis Passwort', IsTranslated='Y', Name='Delis Passwort',Updated=TO_TIMESTAMP('2019-11-13 15:04:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577264
;

-- 2019-11-13T13:04:19.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577264,'de_DE') 
;

-- 2019-11-13T13:04:19.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577264,'de_DE') 
;

-- 2019-11-13T13:04:19.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DelisPassword', Name='Delis Passwort', Description=NULL, Help=NULL WHERE AD_Element_ID=577264
;

-- 2019-11-13T13:04:19.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DelisPassword', Name='Delis Passwort', Description=NULL, Help=NULL, AD_Element_ID=577264 WHERE UPPER(ColumnName)='DELISPASSWORD' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-13T13:04:19.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DelisPassword', Name='Delis Passwort', Description=NULL, Help=NULL WHERE AD_Element_ID=577264 AND IsCentrallyMaintained='Y'
;

-- 2019-11-13T13:04:19.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Delis Passwort', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577264) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577264)
;

-- 2019-11-13T13:04:19.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Delis Passwort', Name='Delis Passwort' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577264)
;

-- 2019-11-13T13:04:19.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Delis Passwort', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577264
;

-- 2019-11-13T13:04:19.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Delis Passwort', Description=NULL, Help=NULL WHERE AD_Element_ID = 577264
;

-- 2019-11-13T13:04:19.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Delis Passwort', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577264
;

-- 2019-11-13T13:04:22.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-13 15:04:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577264
;

-- 2019-11-13T13:04:22.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577264,'en_US') 
;

-- 2019-11-13T13:04:56.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='URL Api Login', IsTranslated='Y', Name='URL Api Login',Updated=TO_TIMESTAMP('2019-11-13 15:04:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577261
;

-- 2019-11-13T13:04:56.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577261,'de_CH') 
;

-- 2019-11-13T13:05:01.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='URL Api Login', IsTranslated='Y', Name='URL Api Login',Updated=TO_TIMESTAMP('2019-11-13 15:05:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577261
;

-- 2019-11-13T13:05:01.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577261,'de_DE') 
;

-- 2019-11-13T13:05:01.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577261,'de_DE') 
;

-- 2019-11-13T13:05:01.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LoginApiUrl', Name='URL Api Login', Description=NULL, Help=NULL WHERE AD_Element_ID=577261
;

-- 2019-11-13T13:05:01.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginApiUrl', Name='URL Api Login', Description=NULL, Help=NULL, AD_Element_ID=577261 WHERE UPPER(ColumnName)='LOGINAPIURL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-13T13:05:01.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginApiUrl', Name='URL Api Login', Description=NULL, Help=NULL WHERE AD_Element_ID=577261 AND IsCentrallyMaintained='Y'
;

-- 2019-11-13T13:05:02Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='URL Api Login', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577261) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577261)
;

-- 2019-11-13T13:05:02.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='URL Api Login', Name='URL Api Login' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577261)
;

-- 2019-11-13T13:05:02.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='URL Api Login', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577261
;

-- 2019-11-13T13:05:02.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='URL Api Login', Description=NULL, Help=NULL WHERE AD_Element_ID = 577261
;

-- 2019-11-13T13:05:02.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'URL Api Login', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577261
;

-- 2019-11-13T13:05:04.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-13 15:05:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577261
;

-- 2019-11-13T13:05:04.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577261,'en_US') 
;

-- 2019-11-13T14:17:01.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:01','YYYY-MM-DD HH24:MI:SS'),100,'D',540185,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:01.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540185
;

-- 2019-11-13T14:17:01.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540185
;

-- 2019-11-13T14:17:01.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:01','YYYY-MM-DD HH24:MI:SS'),100,540048,569349,101,540185,0)
;

-- 2019-11-13T14:17:02.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:01','YYYY-MM-DD HH24:MI:SS'),100,'D',540186,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:02.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540186
;

-- 2019-11-13T14:17:02.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540186
;

-- 2019-11-13T14:17:02.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,540049,569349,101,540186,0)
;

-- 2019-11-13T14:17:02.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,'D',540187,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:02.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540187
;

-- 2019-11-13T14:17:02.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540187
;

-- 2019-11-13T14:17:02.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,540050,569349,101,540187,0)
;

-- 2019-11-13T14:17:02.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,'D',540188,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:02.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540188
;

-- 2019-11-13T14:17:02.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540188
;

-- 2019-11-13T14:17:03.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,540051,569349,101,540188,0)
;

-- 2019-11-13T14:17:03.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),100,'D',540189,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:03.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540189
;

-- 2019-11-13T14:17:03.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540189
;

-- 2019-11-13T14:17:03.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),100,540052,569349,101,540189,0)
;

-- 2019-11-13T14:17:03.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),100,'D',540190,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:03.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540190
;

-- 2019-11-13T14:17:03.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540190
;

-- 2019-11-13T14:17:04.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:03','YYYY-MM-DD HH24:MI:SS'),100,540053,569349,101,540190,0)
;

-- 2019-11-13T14:17:04.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:04','YYYY-MM-DD HH24:MI:SS'),100,'D',540191,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:04.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540191
;

-- 2019-11-13T14:17:04.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540191
;

-- 2019-11-13T14:17:04.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:04','YYYY-MM-DD HH24:MI:SS'),100,540054,569349,101,540191,0)
;

-- 2019-11-13T14:17:05.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:04','YYYY-MM-DD HH24:MI:SS'),100,'D',540192,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:05.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540192
;

-- 2019-11-13T14:17:05.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540192
;

-- 2019-11-13T14:17:05.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,540055,569349,101,540192,0)
;

-- 2019-11-13T14:17:05.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'D',540193,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:05.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540193
;

-- 2019-11-13T14:17:05.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540193
;

-- 2019-11-13T14:17:05.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,540056,569349,101,540193,0)
;

-- 2019-11-13T14:17:05.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'D',540194,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:05.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540194
;

-- 2019-11-13T14:17:05.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540194
;

-- 2019-11-13T14:17:05.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,540057,569349,101,540194,0)
;

-- 2019-11-13T14:17:06.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'D',540195,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:06.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540195
;

-- 2019-11-13T14:17:06.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540195
;

-- 2019-11-13T14:17:06.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,540058,569349,101,540195,0)
;

-- 2019-11-13T14:17:06.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,'D',540196,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:06.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540196
;

-- 2019-11-13T14:17:06.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540196
;

-- 2019-11-13T14:17:06.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,540059,569349,101,540196,0)
;

-- 2019-11-13T14:17:06.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,'D',540197,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:06.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540197
;

-- 2019-11-13T14:17:06.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540197
;

-- 2019-11-13T14:17:06.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,540060,569349,101,540197,0)
;

-- 2019-11-13T14:17:07.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:06','YYYY-MM-DD HH24:MI:SS'),100,'D',540198,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:07.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540198
;

-- 2019-11-13T14:17:07.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540198
;

-- 2019-11-13T14:17:07.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,540061,569349,101,540198,0)
;

-- 2019-11-13T14:17:07.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,'D',540199,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:07.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540199
;

-- 2019-11-13T14:17:07.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540199
;

-- 2019-11-13T14:17:07.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,540062,569349,101,540199,0)
;

-- 2019-11-13T14:17:07.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,'D',540200,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:07.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540200
;

-- 2019-11-13T14:17:07.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540200
;

-- 2019-11-13T14:17:08.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,540063,569349,101,540200,0)
;

-- 2019-11-13T14:17:08.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,'D',540201,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:08.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540201
;

-- 2019-11-13T14:17:08.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540201
;

-- 2019-11-13T14:17:08.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,540064,569349,101,540201,0)
;

-- 2019-11-13T14:17:08.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,'D',540202,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:08.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540202
;

-- 2019-11-13T14:17:08.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540202
;

-- 2019-11-13T14:17:08.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,540065,569349,101,540202,0)
;

-- 2019-11-13T14:17:09.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:08','YYYY-MM-DD HH24:MI:SS'),100,'D',540203,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:09.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540203
;

-- 2019-11-13T14:17:09.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540203
;

-- 2019-11-13T14:17:09.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,540066,569349,101,540203,0)
;

-- 2019-11-13T14:17:09.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,'D',540204,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:09.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540204
;

-- 2019-11-13T14:17:09.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540204
;

-- 2019-11-13T14:17:09.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,540067,569349,101,540204,0)
;

-- 2019-11-13T14:17:09.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,'D',540205,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:09.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540205
;

-- 2019-11-13T14:17:09.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540205
;

-- 2019-11-13T14:17:09.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,540068,569349,101,540205,0)
;

-- 2019-11-13T14:17:10.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:09','YYYY-MM-DD HH24:MI:SS'),100,'D',540206,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:10.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540206
;

-- 2019-11-13T14:17:10.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540206
;

-- 2019-11-13T14:17:10.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,540069,569349,101,540206,0)
;

-- 2019-11-13T14:17:10.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,'D',540207,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:10.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540207
;

-- 2019-11-13T14:17:10.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540207
;

-- 2019-11-13T14:17:10.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,540070,569349,101,540207,0)
;

-- 2019-11-13T14:17:10.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,'D',540208,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:10.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540208
;

-- 2019-11-13T14:17:10.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540208
;

-- 2019-11-13T14:17:10.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:10','YYYY-MM-DD HH24:MI:SS'),100,540071,569349,101,540208,0)
;

-- 2019-11-13T14:17:11.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,'D',540209,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:11.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540209
;

-- 2019-11-13T14:17:11.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540209
;

-- 2019-11-13T14:17:11.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,540072,569349,101,540209,0)
;

-- 2019-11-13T14:17:11.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,'D',540210,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:11.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540210
;

-- 2019-11-13T14:17:11.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540210
;

-- 2019-11-13T14:17:11.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,540073,569349,101,540210,0)
;

-- 2019-11-13T14:17:11.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,'D',540211,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:11.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540211
;

-- 2019-11-13T14:17:11.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540211
;

-- 2019-11-13T14:17:12.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:11','YYYY-MM-DD HH24:MI:SS'),100,540074,569349,101,540211,0)
;

-- 2019-11-13T14:17:12.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,'D',540212,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:12.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540212
;

-- 2019-11-13T14:17:12.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540212
;

-- 2019-11-13T14:17:12.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,540075,569349,101,540212,0)
;

-- 2019-11-13T14:17:12.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,'D',540213,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:12.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540213
;

-- 2019-11-13T14:17:12.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540213
;

-- 2019-11-13T14:17:12.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,540076,569349,101,540213,0)
;

-- 2019-11-13T14:17:12.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,'D',540214,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:12.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540214
;

-- 2019-11-13T14:17:12.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540214
;

-- 2019-11-13T14:17:13.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:12','YYYY-MM-DD HH24:MI:SS'),100,540077,569349,101,540214,0)
;

-- 2019-11-13T14:17:13.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:13','YYYY-MM-DD HH24:MI:SS'),100,'D',540215,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:13.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540215
;

-- 2019-11-13T14:17:13.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540215
;

-- 2019-11-13T14:17:14.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:13','YYYY-MM-DD HH24:MI:SS'),100,540078,569349,101,540215,0)
;

-- 2019-11-13T14:17:14.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'D',540216,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:14.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540216
;

-- 2019-11-13T14:17:14.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540216
;

-- 2019-11-13T14:17:14.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,540079,569349,101,540216,0)
;

-- 2019-11-13T14:17:14.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'D',540217,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:14.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540217
;

-- 2019-11-13T14:17:14.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540217
;

-- 2019-11-13T14:17:14.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,540080,569349,101,540217,0)
;

-- 2019-11-13T14:17:14.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'D',540218,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:14.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540218
;

-- 2019-11-13T14:17:14.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540218
;

-- 2019-11-13T14:17:15.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,540081,569349,101,540218,0)
;

-- 2019-11-13T14:17:15.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,'D',540219,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:15.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540219
;

-- 2019-11-13T14:17:15.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540219
;

-- 2019-11-13T14:17:15.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,540082,569349,101,540219,0)
;

-- 2019-11-13T14:17:15.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,'D',540220,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:15.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540220
;

-- 2019-11-13T14:17:15.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540220
;

-- 2019-11-13T14:17:15.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,540083,569349,101,540220,0)
;

-- 2019-11-13T14:17:15.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,'D',540221,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:15.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540221
;

-- 2019-11-13T14:17:15.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540221
;

-- 2019-11-13T14:17:16.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:15','YYYY-MM-DD HH24:MI:SS'),100,540084,569349,101,540221,0)
;

-- 2019-11-13T14:17:16.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'D',540222,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:16.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540222
;

-- 2019-11-13T14:17:16.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540222
;

-- 2019-11-13T14:17:16.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,540085,569349,101,540222,0)
;

-- 2019-11-13T14:17:16.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'D',540223,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:16.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540223
;

-- 2019-11-13T14:17:16.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540223
;

-- 2019-11-13T14:17:16.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,540086,569349,101,540223,0)
;

-- 2019-11-13T14:17:16.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'D',540224,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:16.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540224
;

-- 2019-11-13T14:17:16.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540224
;

-- 2019-11-13T14:17:17.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,540087,569349,101,540224,0)
;

-- 2019-11-13T14:17:17.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,'D',540225,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:17.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540225
;

-- 2019-11-13T14:17:17.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540225
;

-- 2019-11-13T14:17:17.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,540088,569349,101,540225,0)
;

-- 2019-11-13T14:17:17.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,'D',540226,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:17.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540226
;

-- 2019-11-13T14:17:17.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540226
;

-- 2019-11-13T14:17:17.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,540089,569349,101,540226,0)
;

-- 2019-11-13T14:17:17.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,'D',540227,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:17.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540227
;

-- 2019-11-13T14:17:17.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540227
;

-- 2019-11-13T14:17:18.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:17','YYYY-MM-DD HH24:MI:SS'),100,540090,569349,101,540227,0)
;

-- 2019-11-13T14:17:18.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),100,'D',540228,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:18.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540228
;

-- 2019-11-13T14:17:18.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540228
;

-- 2019-11-13T14:17:18.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),100,540091,569349,101,540228,0)
;

-- 2019-11-13T14:17:18.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),100,'D',540229,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:18.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540229
;

-- 2019-11-13T14:17:18.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540229
;

-- 2019-11-13T14:17:19.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:18','YYYY-MM-DD HH24:MI:SS'),100,540092,569349,101,540229,0)
;

-- 2019-11-13T14:17:19.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'application/octet-stream',TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),100,'D',540230,'Text Nov 13, 2019 4:17 PM',0)
;

-- 2019-11-13T14:17:19.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540230
;

-- 2019-11-13T14:17:19.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='Text Nov 13, 2019 4:17 PM',Updated=TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540230
;

-- 2019-11-13T14:17:19.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),100,540093,569349,101,540230,0)
;

-- 2019-11-13T14:17:19.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_AttachmentEntry (AD_Client_ID,ContentType,Created,CreatedBy,IsActive,Updated,UpdatedBy,Type,AD_AttachmentEntry_ID,FileName,AD_Org_ID) VALUES (0,'text/x-java-source',TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),100,'D',540231,'DhlTestHelper.java',0)
;

-- 2019-11-13T14:17:19.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET BinaryData=? WHERE AD_AttachmentEntry_ID=540231
;

-- 2019-11-13T14:17:19.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_AttachmentEntry SET URL=NULL, Type='D', Tags='', FileName='DhlTestHelper.java',Updated=TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_AttachmentEntry_ID=540231
;

-- 2019-11-13T14:17:19.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Attachment_MultiRef (AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_Attachment_MultiRef_ID,Record_ID,AD_Table_ID,AD_AttachmentEntry_ID,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-13 16:17:19','YYYY-MM-DD HH24:MI:SS'),100,540094,569349,101,540231,0)
;

-- 2019-11-14T06:33:54.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Papierformat', IsTranslated='Y', Name='Papierformat',Updated=TO_TIMESTAMP('2019-11-14 08:33:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577304
;

-- 2019-11-14T06:33:55.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577304,'de_CH') 
;

-- 2019-11-14T06:33:59.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Papierformat', IsTranslated='Y', Name='Papierformat',Updated=TO_TIMESTAMP('2019-11-14 08:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577304
;

-- 2019-11-14T06:33:59.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577304,'de_DE') 
;

-- 2019-11-14T06:33:59.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577304,'de_DE') 
;

-- 2019-11-14T06:33:59.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PaperFormat', Name='Papierformat', Description=NULL, Help=NULL WHERE AD_Element_ID=577304
;

-- 2019-11-14T06:33:59.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PaperFormat', Name='Papierformat', Description=NULL, Help=NULL, AD_Element_ID=577304 WHERE UPPER(ColumnName)='PAPERFORMAT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:34:00Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PaperFormat', Name='Papierformat', Description=NULL, Help=NULL WHERE AD_Element_ID=577304 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:34:00.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Papierformat', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577304) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577304)
;

-- 2019-11-14T06:34:00.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Papierformat', Name='Papierformat' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577304)
;

-- 2019-11-14T06:34:00.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Papierformat', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577304
;

-- 2019-11-14T06:34:00.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Papierformat', Description=NULL, Help=NULL WHERE AD_Element_ID = 577304
;

-- 2019-11-14T06:34:00.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Papierformat', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577304
;

-- 2019-11-14T06:34:02.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:34:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577304
;

-- 2019-11-14T06:34:02.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577304,'en_US') 
;

-- 2019-11-14T06:35:16.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='URL Api Shipment Service', IsTranslated='Y', Name='URL Api Shipment Service',Updated=TO_TIMESTAMP('2019-11-14 08:35:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577262
;

-- 2019-11-14T06:35:16.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577262,'de_CH') 
;

-- 2019-11-14T06:35:21.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='URL Api Shipment Service', IsTranslated='Y', Name='URL Api Shipment Service',Updated=TO_TIMESTAMP('2019-11-14 08:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577262
;

-- 2019-11-14T06:35:21.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577262,'de_DE') 
;

-- 2019-11-14T06:35:21.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577262,'de_DE') 
;

-- 2019-11-14T06:35:21.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShipmentServiceApiUrl', Name='URL Api Shipment Service', Description=NULL, Help=NULL WHERE AD_Element_ID=577262
;

-- 2019-11-14T06:35:21.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentServiceApiUrl', Name='URL Api Shipment Service', Description=NULL, Help=NULL, AD_Element_ID=577262 WHERE UPPER(ColumnName)='SHIPMENTSERVICEAPIURL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:35:21.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentServiceApiUrl', Name='URL Api Shipment Service', Description=NULL, Help=NULL WHERE AD_Element_ID=577262 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:35:21.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='URL Api Shipment Service', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577262) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577262)
;

-- 2019-11-14T06:35:21.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='URL Api Shipment Service', Name='URL Api Shipment Service' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577262)
;

-- 2019-11-14T06:35:21.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='URL Api Shipment Service', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577262
;

-- 2019-11-14T06:35:21.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='URL Api Shipment Service', Description=NULL, Help=NULL WHERE AD_Element_ID = 577262
;

-- 2019-11-14T06:35:21.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'URL Api Shipment Service', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577262
;

-- 2019-11-14T06:35:24.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577262
;

-- 2019-11-14T06:35:24.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577262,'en_US') 
;

-- 2019-11-14T06:35:59.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DPD Auftragsart', IsTranslated='Y', Name='DPD Auftragsart',Updated=TO_TIMESTAMP('2019-11-14 08:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577302
;

-- 2019-11-14T06:35:59.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577302,'de_CH') 
;

-- 2019-11-14T06:36:03.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DPD Auftragsart', IsTranslated='Y', Name='DPD Auftragsart',Updated=TO_TIMESTAMP('2019-11-14 08:36:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577302
;

-- 2019-11-14T06:36:03.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577302,'de_DE') 
;

-- 2019-11-14T06:36:03.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577302,'de_DE') 
;

-- 2019-11-14T06:36:03.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DpdOrderType', Name='DPD Auftragsart', Description=NULL, Help=NULL WHERE AD_Element_ID=577302
;

-- 2019-11-14T06:36:03.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DpdOrderType', Name='DPD Auftragsart', Description=NULL, Help=NULL, AD_Element_ID=577302 WHERE UPPER(ColumnName)='DPDORDERTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:36:03.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DpdOrderType', Name='DPD Auftragsart', Description=NULL, Help=NULL WHERE AD_Element_ID=577302 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:36:03.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DPD Auftragsart', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577302) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577302)
;

-- 2019-11-14T06:36:03.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DPD Auftragsart', Name='DPD Auftragsart' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577302)
;

-- 2019-11-14T06:36:03.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='DPD Auftragsart', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577302
;

-- 2019-11-14T06:36:03.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='DPD Auftragsart', Description=NULL, Help=NULL WHERE AD_Element_ID = 577302
;

-- 2019-11-14T06:36:03.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'DPD Auftragsart', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577302
;

-- 2019-11-14T06:36:06.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:36:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577302
;

-- 2019-11-14T06:36:06.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577302,'en_US') 
;

-- 2019-11-14T06:36:26.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DPD Produkt', IsTranslated='Y', Name='DPD Produkt',Updated=TO_TIMESTAMP('2019-11-14 08:36:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577301
;

-- 2019-11-14T06:36:26.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577301,'de_CH') 
;

-- 2019-11-14T06:36:31.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DPD Produkt', IsTranslated='Y', Name='DPD Produkt',Updated=TO_TIMESTAMP('2019-11-14 08:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577301
;

-- 2019-11-14T06:36:31.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577301,'de_DE') 
;

-- 2019-11-14T06:36:31.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577301,'de_DE') 
;

-- 2019-11-14T06:36:31.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DpdProduct', Name='DPD Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577301
;

-- 2019-11-14T06:36:31.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DpdProduct', Name='DPD Produkt', Description=NULL, Help=NULL, AD_Element_ID=577301 WHERE UPPER(ColumnName)='DPDPRODUCT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:36:31.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DpdProduct', Name='DPD Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577301 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:36:31.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DPD Produkt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577301) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577301)
;

-- 2019-11-14T06:36:31.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DPD Produkt', Name='DPD Produkt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577301)
;

-- 2019-11-14T06:36:31.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='DPD Produkt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577301
;

-- 2019-11-14T06:36:31.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='DPD Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID = 577301
;

-- 2019-11-14T06:36:31.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'DPD Produkt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577301
;

-- 2019-11-14T06:36:33.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:36:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577301
;

-- 2019-11-14T06:36:33.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577301,'en_US') 
;

-- 2019-11-14T06:36:56.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Benachrichtigungsart', IsTranslated='Y', Name='Benachrichtigungsart',Updated=TO_TIMESTAMP('2019-11-14 08:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577303
;

-- 2019-11-14T06:36:56.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577303,'de_CH') 
;

-- 2019-11-14T06:37:00.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Benachrichtigungsart', IsTranslated='Y', Name='Benachrichtigungsart',Updated=TO_TIMESTAMP('2019-11-14 08:37:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577303
;

-- 2019-11-14T06:37:00.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577303,'de_DE') 
;

-- 2019-11-14T06:37:00.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577303,'de_DE') 
;

-- 2019-11-14T06:37:00.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NotificationChannel', Name='Benachrichtigungsart', Description=NULL, Help=NULL WHERE AD_Element_ID=577303
;

-- 2019-11-14T06:37:00.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NotificationChannel', Name='Benachrichtigungsart', Description=NULL, Help=NULL, AD_Element_ID=577303 WHERE UPPER(ColumnName)='NOTIFICATIONCHANNEL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:37:00.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NotificationChannel', Name='Benachrichtigungsart', Description=NULL, Help=NULL WHERE AD_Element_ID=577303 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:37:00.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Benachrichtigungsart', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577303) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577303)
;

-- 2019-11-14T06:37:00.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Benachrichtigungsart', Name='Benachrichtigungsart' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577303)
;

-- 2019-11-14T06:37:00.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Benachrichtigungsart', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577303
;

-- 2019-11-14T06:37:00.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Benachrichtigungsart', Description=NULL, Help=NULL WHERE AD_Element_ID = 577303
;

-- 2019-11-14T06:37:00.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Benachrichtigungsart', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577303
;

-- 2019-11-14T06:37:03.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:37:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577303
;

-- 2019-11-14T06:37:03.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577303,'en_US') 
;

-- 2019-11-14T06:38:01.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Paketscheindaten PDF', IsTranslated='Y', Name='Paketscheindaten PDF',Updated=TO_TIMESTAMP('2019-11-14 08:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577216
;

-- 2019-11-14T06:38:01.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577216,'de_CH') 
;

-- 2019-11-14T06:38:05.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Paketscheindaten PDF', IsTranslated='Y', Name='Paketscheindaten PDF',Updated=TO_TIMESTAMP('2019-11-14 08:38:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577216
;

-- 2019-11-14T06:38:05.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577216,'de_DE') 
;

-- 2019-11-14T06:38:05.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577216,'de_DE') 
;

-- 2019-11-14T06:38:05.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PdfLabelData', Name='Paketscheindaten PDF', Description=NULL, Help=NULL WHERE AD_Element_ID=577216
;

-- 2019-11-14T06:38:05.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PdfLabelData', Name='Paketscheindaten PDF', Description=NULL, Help=NULL, AD_Element_ID=577216 WHERE UPPER(ColumnName)='PDFLABELDATA' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:38:05.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PdfLabelData', Name='Paketscheindaten PDF', Description=NULL, Help=NULL WHERE AD_Element_ID=577216 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:38:05.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Paketscheindaten PDF', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577216) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577216)
;

-- 2019-11-14T06:38:05.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Paketscheindaten PDF', Name='Paketscheindaten PDF' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577216)
;

-- 2019-11-14T06:38:05.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Paketscheindaten PDF', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577216
;

-- 2019-11-14T06:38:05.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Paketscheindaten PDF', Description=NULL, Help=NULL WHERE AD_Element_ID = 577216
;

-- 2019-11-14T06:38:05.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Paketscheindaten PDF', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577216
;

-- 2019-11-14T06:38:08.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:38:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577216
;

-- 2019-11-14T06:38:08.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577216,'en_US') 
;

-- 2019-11-14T06:38:27.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Datum der Abholung', IsTranslated='Y', Name='Datum der Abholung',Updated=TO_TIMESTAMP('2019-11-14 08:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577288
;

-- 2019-11-14T06:38:27.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577288,'de_CH') 
;

-- 2019-11-14T06:38:32.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Datum der Abholung', IsTranslated='Y', Name='Datum der Abholung',Updated=TO_TIMESTAMP('2019-11-14 08:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577288
;

-- 2019-11-14T06:38:32.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577288,'de_DE') 
;

-- 2019-11-14T06:38:32.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577288,'de_DE') 
;

-- 2019-11-14T06:38:32.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PickupDate', Name='Datum der Abholung', Description=NULL, Help=NULL WHERE AD_Element_ID=577288
;

-- 2019-11-14T06:38:32.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickupDate', Name='Datum der Abholung', Description=NULL, Help=NULL, AD_Element_ID=577288 WHERE UPPER(ColumnName)='PICKUPDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:38:32.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickupDate', Name='Datum der Abholung', Description=NULL, Help=NULL WHERE AD_Element_ID=577288 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:38:32.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Datum der Abholung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577288) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577288)
;

-- 2019-11-14T06:38:32.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Datum der Abholung', Name='Datum der Abholung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577288)
;

-- 2019-11-14T06:38:32.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Datum der Abholung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577288
;

-- 2019-11-14T06:38:32.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Datum der Abholung', Description=NULL, Help=NULL WHERE AD_Element_ID = 577288
;

-- 2019-11-14T06:38:32.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Datum der Abholung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577288
;

-- 2019-11-14T06:38:34.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577288
;

-- 2019-11-14T06:38:34.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577288,'en_US') 
;

-- 2019-11-14T06:38:49.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Tag der Abholung', IsTranslated='Y', Name='Tag der Abholung',Updated=TO_TIMESTAMP('2019-11-14 08:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577289
;

-- 2019-11-14T06:38:49.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577289,'de_CH') 
;

-- 2019-11-14T06:38:55.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Tag der Abholung', IsTranslated='Y', Name='Tag der Abholung',Updated=TO_TIMESTAMP('2019-11-14 08:38:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577289
;

-- 2019-11-14T06:38:55.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577289,'de_DE') 
;

-- 2019-11-14T06:38:55.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577289,'de_DE') 
;

-- 2019-11-14T06:38:55.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PickupDay', Name='Tag der Abholung', Description=NULL, Help=NULL WHERE AD_Element_ID=577289
;

-- 2019-11-14T06:38:55.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickupDay', Name='Tag der Abholung', Description=NULL, Help=NULL, AD_Element_ID=577289 WHERE UPPER(ColumnName)='PICKUPDAY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:38:55.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickupDay', Name='Tag der Abholung', Description=NULL, Help=NULL WHERE AD_Element_ID=577289 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:38:55.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Tag der Abholung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577289) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577289)
;

-- 2019-11-14T06:38:55.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Tag der Abholung', Name='Tag der Abholung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577289)
;

-- 2019-11-14T06:38:55.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Tag der Abholung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577289
;

-- 2019-11-14T06:38:55.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Tag der Abholung', Description=NULL, Help=NULL WHERE AD_Element_ID = 577289
;

-- 2019-11-14T06:38:55.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Tag der Abholung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577289
;

-- 2019-11-14T06:38:58.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:38:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577289
;

-- 2019-11-14T06:38:58.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577289,'en_US') 
;

-- 2019-11-14T06:39:17.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Abhoung Uhrzeit ab', IsTranslated='Y', Name='Abhoung Uhrzeit ab',Updated=TO_TIMESTAMP('2019-11-14 08:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577278
;

-- 2019-11-14T06:39:17.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577278,'de_CH') 
;

-- 2019-11-14T06:39:21.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Abhoung Uhrzeit ab', IsTranslated='Y', Name='Abhoung Uhrzeit ab',Updated=TO_TIMESTAMP('2019-11-14 08:39:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577278
;

-- 2019-11-14T06:39:21.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577278,'de_DE') 
;

-- 2019-11-14T06:39:21.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577278,'de_DE') 
;

-- 2019-11-14T06:39:21.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PickupTimeFrom', Name='Abhoung Uhrzeit ab', Description=NULL, Help=NULL WHERE AD_Element_ID=577278
;

-- 2019-11-14T06:39:21.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickupTimeFrom', Name='Abhoung Uhrzeit ab', Description=NULL, Help=NULL, AD_Element_ID=577278 WHERE UPPER(ColumnName)='PICKUPTIMEFROM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:39:21.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickupTimeFrom', Name='Abhoung Uhrzeit ab', Description=NULL, Help=NULL WHERE AD_Element_ID=577278 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:39:21.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abhoung Uhrzeit ab', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577278) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577278)
;

-- 2019-11-14T06:39:22.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abhoung Uhrzeit ab', Name='Abhoung Uhrzeit ab' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577278)
;

-- 2019-11-14T06:39:22.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abhoung Uhrzeit ab', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577278
;

-- 2019-11-14T06:39:22.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abhoung Uhrzeit ab', Description=NULL, Help=NULL WHERE AD_Element_ID = 577278
;

-- 2019-11-14T06:39:22.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abhoung Uhrzeit ab', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577278
;

-- 2019-11-14T06:39:24.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:39:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577278
;

-- 2019-11-14T06:39:24.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577278,'en_US') 
;

-- 2019-11-14T06:39:39.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Abholung Uhrzeit bis', IsTranslated='Y', Name='Abholung Uhrzeit bis',Updated=TO_TIMESTAMP('2019-11-14 08:39:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577279
;

-- 2019-11-14T06:39:39.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577279,'de_CH') 
;

-- 2019-11-14T06:39:44.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Abholung Uhrzeit bis', IsTranslated='Y', Name='Abholung Uhrzeit bis',Updated=TO_TIMESTAMP('2019-11-14 08:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577279
;

-- 2019-11-14T06:39:44.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577279,'de_DE') 
;

-- 2019-11-14T06:39:44.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577279,'de_DE') 
;

-- 2019-11-14T06:39:44.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PickupTimeTo', Name='Abholung Uhrzeit bis', Description=NULL, Help=NULL WHERE AD_Element_ID=577279
;

-- 2019-11-14T06:39:44.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickupTimeTo', Name='Abholung Uhrzeit bis', Description=NULL, Help=NULL, AD_Element_ID=577279 WHERE UPPER(ColumnName)='PICKUPTIMETO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:39:44.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickupTimeTo', Name='Abholung Uhrzeit bis', Description=NULL, Help=NULL WHERE AD_Element_ID=577279 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:39:44.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abholung Uhrzeit bis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577279) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577279)
;

-- 2019-11-14T06:39:44.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abholung Uhrzeit bis', Name='Abholung Uhrzeit bis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577279)
;

-- 2019-11-14T06:39:44.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abholung Uhrzeit bis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577279
;

-- 2019-11-14T06:39:44.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abholung Uhrzeit bis', Description=NULL, Help=NULL WHERE AD_Element_ID = 577279
;

-- 2019-11-14T06:39:44.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abholung Uhrzeit bis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577279
;

-- 2019-11-14T06:39:48.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Abholung Uhrzeit bis', IsTranslated='Y', Name='Abholung Uhrzeit bis',Updated=TO_TIMESTAMP('2019-11-14 08:39:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577279
;

-- 2019-11-14T06:39:48.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577279,'en_US') 
;

-- 2019-11-14T06:40:35.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Sprache Paketschein', IsTranslated='Y', Name='Sprache Paketschein',Updated=TO_TIMESTAMP('2019-11-14 08:40:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577305
;

-- 2019-11-14T06:40:35.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577305,'de_CH') 
;

-- 2019-11-14T06:40:41.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Sprache Paketschein', IsTranslated='Y', Name='Sprache Paketschein',Updated=TO_TIMESTAMP('2019-11-14 08:40:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577305
;

-- 2019-11-14T06:40:41.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577305,'de_DE') 
;

-- 2019-11-14T06:40:41.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577305,'de_DE') 
;

-- 2019-11-14T06:40:41.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PrinterLanguage', Name='Sprache Paketschein', Description=NULL, Help=NULL WHERE AD_Element_ID=577305
;

-- 2019-11-14T06:40:41.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PrinterLanguage', Name='Sprache Paketschein', Description=NULL, Help=NULL, AD_Element_ID=577305 WHERE UPPER(ColumnName)='PRINTERLANGUAGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:40:41.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PrinterLanguage', Name='Sprache Paketschein', Description=NULL, Help=NULL WHERE AD_Element_ID=577305 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:40:41.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sprache Paketschein', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577305) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577305)
;

-- 2019-11-14T06:40:41.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sprache Paketschein', Name='Sprache Paketschein' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577305)
;

-- 2019-11-14T06:40:41.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sprache Paketschein', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577305
;

-- 2019-11-14T06:40:41.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sprache Paketschein', Description=NULL, Help=NULL WHERE AD_Element_ID = 577305
;

-- 2019-11-14T06:40:41.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sprache Paketschein', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577305
;

-- 2019-11-14T06:40:48.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:40:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577305
;

-- 2019-11-14T06:40:48.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577305,'en_US') 
;

-- 2019-11-14T06:41:07.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Ort Empfänger', IsTranslated='Y', Name='Ort Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:41:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577295
;

-- 2019-11-14T06:41:07.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577295,'de_CH') 
;

-- 2019-11-14T06:41:12.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Ort Empfänger', IsTranslated='Y', Name='Ort Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577295
;

-- 2019-11-14T06:41:12.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577295,'de_DE') 
;

-- 2019-11-14T06:41:12.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577295,'de_DE') 
;

-- 2019-11-14T06:41:12.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RecipientCity', Name='Ort Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577295
;

-- 2019-11-14T06:41:12.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientCity', Name='Ort Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577295 WHERE UPPER(ColumnName)='RECIPIENTCITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:41:12.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientCity', Name='Ort Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577295 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:41:12.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ort Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577295) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577295)
;

-- 2019-11-14T06:41:12.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ort Empfänger', Name='Ort Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577295)
;

-- 2019-11-14T06:41:12.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ort Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577295
;

-- 2019-11-14T06:41:12.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ort Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577295
;

-- 2019-11-14T06:41:12.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ort Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577295
;

-- 2019-11-14T06:41:14.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:41:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577295
;

-- 2019-11-14T06:41:14.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577295,'en_US') 
;

-- 2019-11-14T06:41:36.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Land Empfänger', IsTranslated='Y', Name='Land Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577296
;

-- 2019-11-14T06:41:36.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577296,'de_CH') 
;

-- 2019-11-14T06:41:41.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Land Empfänger', IsTranslated='Y', Name='Land Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577296
;

-- 2019-11-14T06:41:41.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577296,'de_DE') 
;

-- 2019-11-14T06:41:41.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577296,'de_DE') 
;

-- 2019-11-14T06:41:41.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RecipientCountry', Name='Land Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577296
;

-- 2019-11-14T06:41:41.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientCountry', Name='Land Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577296 WHERE UPPER(ColumnName)='RECIPIENTCOUNTRY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:41:41.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientCountry', Name='Land Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577296 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:41:41.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Land Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577296) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577296)
;

-- 2019-11-14T06:41:41.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Land Empfänger', Name='Land Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577296)
;

-- 2019-11-14T06:41:41.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Land Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577296
;

-- 2019-11-14T06:41:41.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Land Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577296
;

-- 2019-11-14T06:41:41.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Land Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577296
;

-- 2019-11-14T06:41:45.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:41:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577296
;

-- 2019-11-14T06:41:45.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577296,'en_US') 
;

-- 2019-11-14T06:42:05.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='E-Mail-Adresse Empfänger', IsTranslated='Y', Name='E-Mail-Adresse Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:42:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577297
;

-- 2019-11-14T06:42:05.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577297,'de_CH') 
;

-- 2019-11-14T06:42:11.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='E-Mail-Adresse Empfänger', IsTranslated='Y', Name='E-Mail-Adresse Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:42:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577297
;

-- 2019-11-14T06:42:11.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577297,'de_DE') 
;

-- 2019-11-14T06:42:11.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577297,'de_DE') 
;

-- 2019-11-14T06:42:11.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RecipientEmailAddress', Name='E-Mail-Adresse Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577297
;

-- 2019-11-14T06:42:11.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientEmailAddress', Name='E-Mail-Adresse Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577297 WHERE UPPER(ColumnName)='RECIPIENTEMAILADDRESS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:42:11.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientEmailAddress', Name='E-Mail-Adresse Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577297 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:42:11.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='E-Mail-Adresse Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577297) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577297)
;

-- 2019-11-14T06:42:11.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='E-Mail-Adresse Empfänger', Name='E-Mail-Adresse Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577297)
;

-- 2019-11-14T06:42:11.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='E-Mail-Adresse Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577297
;

-- 2019-11-14T06:42:11.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='E-Mail-Adresse Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577297
;

-- 2019-11-14T06:42:11.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'E-Mail-Adresse Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577297
;

-- 2019-11-14T06:42:15.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:42:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577297
;

-- 2019-11-14T06:42:15.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577297,'en_US') 
;

-- 2019-11-14T06:42:31.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Haus-Nr. Empfänger', IsTranslated='Y', Name='Haus-Nr. Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:42:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577293
;

-- 2019-11-14T06:42:31.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577293,'de_CH') 
;

-- 2019-11-14T06:42:35.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Haus-Nr. Empfänger', IsTranslated='Y', Name='Haus-Nr. Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:42:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577293
;

-- 2019-11-14T06:42:35.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577293,'de_DE') 
;

-- 2019-11-14T06:42:35.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577293,'de_DE') 
;

-- 2019-11-14T06:42:35.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RecipientHouseNo', Name='Haus-Nr. Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577293
;

-- 2019-11-14T06:42:35.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientHouseNo', Name='Haus-Nr. Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577293 WHERE UPPER(ColumnName)='RECIPIENTHOUSENO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:42:35.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientHouseNo', Name='Haus-Nr. Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577293 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:42:35.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Haus-Nr. Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577293) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577293)
;

-- 2019-11-14T06:42:35.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Haus-Nr. Empfänger', Name='Haus-Nr. Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577293)
;

-- 2019-11-14T06:42:35.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Haus-Nr. Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577293
;

-- 2019-11-14T06:42:35.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Haus-Nr. Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577293
;

-- 2019-11-14T06:42:35.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Haus-Nr. Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577293
;

-- 2019-11-14T06:42:38.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577293
;

-- 2019-11-14T06:42:38.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577293,'en_US') 
;

-- 2019-11-14T06:42:55.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Name 1 Empfänger', IsTranslated='Y', Name='Name 1 Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:42:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577290
;

-- 2019-11-14T06:42:55.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577290,'de_CH') 
;

-- 2019-11-14T06:42:59.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Name 1 Empfänger', IsTranslated='Y', Name='Name 1 Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577290
;

-- 2019-11-14T06:42:59.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577290,'de_DE') 
;

-- 2019-11-14T06:42:59.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577290,'de_DE') 
;

-- 2019-11-14T06:42:59.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RecipientName1', Name='Name 1 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577290
;

-- 2019-11-14T06:42:59.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientName1', Name='Name 1 Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577290 WHERE UPPER(ColumnName)='RECIPIENTNAME1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:42:59.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientName1', Name='Name 1 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577290 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:42:59.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name 1 Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577290) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577290)
;

-- 2019-11-14T06:42:59.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Name 1 Empfänger', Name='Name 1 Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577290)
;

-- 2019-11-14T06:42:59.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Name 1 Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577290
;

-- 2019-11-14T06:42:59.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Name 1 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577290
;

-- 2019-11-14T06:42:59.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Name 1 Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577290
;

-- 2019-11-14T06:43:01.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:43:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577290
;

-- 2019-11-14T06:43:01.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577290,'en_US') 
;

-- 2019-11-14T06:43:39.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Name 2 Empfänger', IsTranslated='Y', Name='Name 2 Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577291
;

-- 2019-11-14T06:43:39.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577291,'de_CH') 
;

-- 2019-11-14T06:43:44.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Name 2 Empfänger', IsTranslated='Y', Name='Name 2 Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:43:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577291
;

-- 2019-11-14T06:43:44.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577291,'de_DE') 
;

-- 2019-11-14T06:43:44.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577291,'de_DE') 
;

-- 2019-11-14T06:43:44.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RecipientName2', Name='Name 2 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577291
;

-- 2019-11-14T06:43:44.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientName2', Name='Name 2 Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577291 WHERE UPPER(ColumnName)='RECIPIENTNAME2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:43:44.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientName2', Name='Name 2 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577291 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:43:44.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name 2 Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577291) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577291)
;

-- 2019-11-14T06:43:44.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Name 2 Empfänger', Name='Name 2 Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577291)
;

-- 2019-11-14T06:43:44.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Name 2 Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577291
;

-- 2019-11-14T06:43:44.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Name 2 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577291
;

-- 2019-11-14T06:43:44.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Name 2 Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577291
;

-- 2019-11-14T06:43:46.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:43:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577291
;

-- 2019-11-14T06:43:46.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577291,'en_US') 
;

-- 2019-11-14T06:44:02.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Telefon Empfänger', IsTranslated='Y', Name='Telefon Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:44:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577298
;

-- 2019-11-14T06:44:02.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577298,'de_CH') 
;

-- 2019-11-14T06:44:06.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Telefon Empfänger', IsTranslated='Y', Name='Telefon Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577298
;

-- 2019-11-14T06:44:06.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577298,'de_DE') 
;

-- 2019-11-14T06:44:06.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577298,'de_DE') 
;

-- 2019-11-14T06:44:06.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RecipientPhone', Name='Telefon Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577298
;

-- 2019-11-14T06:44:06.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientPhone', Name='Telefon Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577298 WHERE UPPER(ColumnName)='RECIPIENTPHONE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:44:06.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientPhone', Name='Telefon Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577298 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:44:06.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Telefon Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577298) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577298)
;

-- 2019-11-14T06:44:06.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Telefon Empfänger', Name='Telefon Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577298)
;

-- 2019-11-14T06:44:06.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Telefon Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577298
;

-- 2019-11-14T06:44:06.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Telefon Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577298
;

-- 2019-11-14T06:44:06.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Telefon Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577298
;

-- 2019-11-14T06:44:08.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:44:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577298
;

-- 2019-11-14T06:44:08.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577298,'en_US') 
;

-- 2019-11-14T06:44:24.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Straße Empfänger', IsTranslated='Y', Name='Straße Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:44:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577292
;

-- 2019-11-14T06:44:24.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577292,'de_CH') 
;

-- 2019-11-14T06:44:28.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Straße Empfänger', IsTranslated='Y', Name='Straße Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577292
;

-- 2019-11-14T06:44:28.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577292,'de_DE') 
;

-- 2019-11-14T06:44:28.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577292,'de_DE') 
;

-- 2019-11-14T06:44:28.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RecipientStreet', Name='Straße Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577292
;

-- 2019-11-14T06:44:28.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientStreet', Name='Straße Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577292 WHERE UPPER(ColumnName)='RECIPIENTSTREET' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:44:28.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientStreet', Name='Straße Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577292 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:44:28.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Straße Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577292) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577292)
;

-- 2019-11-14T06:44:28.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Straße Empfänger', Name='Straße Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577292)
;

-- 2019-11-14T06:44:28.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Straße Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577292
;

-- 2019-11-14T06:44:28.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Straße Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577292
;

-- 2019-11-14T06:44:28.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Straße Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577292
;

-- 2019-11-14T06:44:30.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577292
;

-- 2019-11-14T06:44:30.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577292,'en_US') 
;

-- 2019-11-14T06:45:19.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='PLZ Empfänger', IsTranslated='Y', Name='PLZ Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:45:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577294
;

-- 2019-11-14T06:45:19.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577294,'de_CH') 
;

-- 2019-11-14T06:45:23.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='PLZ Empfänger', IsTranslated='Y', Name='PLZ Empfänger',Updated=TO_TIMESTAMP('2019-11-14 08:45:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577294
;

-- 2019-11-14T06:45:23.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577294,'de_DE') 
;

-- 2019-11-14T06:45:23.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577294,'de_DE') 
;

-- 2019-11-14T06:45:23.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RecipientZipCode', Name='PLZ Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577294
;

-- 2019-11-14T06:45:23.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientZipCode', Name='PLZ Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577294 WHERE UPPER(ColumnName)='RECIPIENTZIPCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:45:23.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RecipientZipCode', Name='PLZ Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577294 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:45:23.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='PLZ Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577294) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577294)
;

-- 2019-11-14T06:45:23.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='PLZ Empfänger', Name='PLZ Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577294)
;

-- 2019-11-14T06:45:23.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='PLZ Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577294
;

-- 2019-11-14T06:45:23.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='PLZ Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577294
;

-- 2019-11-14T06:45:23.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'PLZ Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577294
;

-- 2019-11-14T06:45:25.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:45:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577294
;

-- 2019-11-14T06:45:25.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577294,'en_US') 
;

-- 2019-11-14T06:45:40.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Ort Absender', IsTranslated='Y', Name='Ort Absender',Updated=TO_TIMESTAMP('2019-11-14 08:45:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577286
;

-- 2019-11-14T06:45:40.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577286,'de_CH') 
;

-- 2019-11-14T06:45:45.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Ort Absender', IsTranslated='Y', Name='Ort Absender',Updated=TO_TIMESTAMP('2019-11-14 08:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577286
;

-- 2019-11-14T06:45:45.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577286,'de_DE') 
;

-- 2019-11-14T06:45:45.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577286,'de_DE') 
;

-- 2019-11-14T06:45:45.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SenderCity', Name='Ort Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577286
;

-- 2019-11-14T06:45:45.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderCity', Name='Ort Absender', Description=NULL, Help=NULL, AD_Element_ID=577286 WHERE UPPER(ColumnName)='SENDERCITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:45:45.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderCity', Name='Ort Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577286 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:45:45.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ort Absender', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577286) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577286)
;

-- 2019-11-14T06:45:45.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ort Absender', Name='Ort Absender' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577286)
;

-- 2019-11-14T06:45:45.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ort Absender', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577286
;

-- 2019-11-14T06:45:45.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ort Absender', Description=NULL, Help=NULL WHERE AD_Element_ID = 577286
;

-- 2019-11-14T06:45:45.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ort Absender', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577286
;

-- 2019-11-14T06:45:48.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577286
;

-- 2019-11-14T06:45:48.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577286,'en_US') 
;

-- 2019-11-14T06:46:02.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Land Absender', IsTranslated='Y', Name='Land Absender',Updated=TO_TIMESTAMP('2019-11-14 08:46:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577287
;

-- 2019-11-14T06:46:02.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577287,'de_CH') 
;

-- 2019-11-14T06:46:06.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Land Absender', IsTranslated='Y', Name='Land Absender',Updated=TO_TIMESTAMP('2019-11-14 08:46:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577287
;

-- 2019-11-14T06:46:06.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577287,'de_DE') 
;

-- 2019-11-14T06:46:06.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577287,'de_DE') 
;

-- 2019-11-14T06:46:06.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SenderCountry', Name='Land Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577287
;

-- 2019-11-14T06:46:06.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderCountry', Name='Land Absender', Description=NULL, Help=NULL, AD_Element_ID=577287 WHERE UPPER(ColumnName)='SENDERCOUNTRY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:46:06.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderCountry', Name='Land Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577287 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:46:06.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Land Absender', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577287) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577287)
;

-- 2019-11-14T06:46:06.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Land Absender', Name='Land Absender' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577287)
;

-- 2019-11-14T06:46:06.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Land Absender', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577287
;

-- 2019-11-14T06:46:06.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Land Absender', Description=NULL, Help=NULL WHERE AD_Element_ID = 577287
;

-- 2019-11-14T06:46:06.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Land Absender', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577287
;

-- 2019-11-14T06:46:24.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Haus-Nr. Absender', IsTranslated='Y', Name='Haus-Nr. Absender',Updated=TO_TIMESTAMP('2019-11-14 08:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577284
;

-- 2019-11-14T06:46:24.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577284,'de_CH') 
;

-- 2019-11-14T06:46:29.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Haus-Nr. Absender', IsTranslated='Y', Name='Haus-Nr. Absender',Updated=TO_TIMESTAMP('2019-11-14 08:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577284
;

-- 2019-11-14T06:46:29.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577284,'de_DE') 
;

-- 2019-11-14T06:46:29.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577284,'de_DE') 
;

-- 2019-11-14T06:46:29.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SenderHouseNo', Name='Haus-Nr. Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577284
;

-- 2019-11-14T06:46:29.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderHouseNo', Name='Haus-Nr. Absender', Description=NULL, Help=NULL, AD_Element_ID=577284 WHERE UPPER(ColumnName)='SENDERHOUSENO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:46:29.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderHouseNo', Name='Haus-Nr. Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577284 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:46:29.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Haus-Nr. Absender', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577284) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577284)
;

-- 2019-11-14T06:46:29.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Haus-Nr. Absender', Name='Haus-Nr. Absender' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577284)
;

-- 2019-11-14T06:46:29.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Haus-Nr. Absender', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577284
;

-- 2019-11-14T06:46:29.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Haus-Nr. Absender', Description=NULL, Help=NULL WHERE AD_Element_ID = 577284
;

-- 2019-11-14T06:46:29.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Haus-Nr. Absender', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577284
;

-- 2019-11-14T06:46:33.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577284
;

-- 2019-11-14T06:46:33.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577284,'en_US') 
;

-- 2019-11-14T06:47:13.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Name 1 Absender', IsTranslated='Y', Name='Name 1 Absender',Updated=TO_TIMESTAMP('2019-11-14 08:47:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577281
;

-- 2019-11-14T06:47:13.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577281,'de_CH') 
;

-- 2019-11-14T06:47:16.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Name 1 Absender', IsTranslated='Y', Name='Name 1 Absender',Updated=TO_TIMESTAMP('2019-11-14 08:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577281
;

-- 2019-11-14T06:47:16.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577281,'de_DE') 
;

-- 2019-11-14T06:47:16.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577281,'de_DE') 
;

-- 2019-11-14T06:47:16.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SenderName1', Name='Name 1 Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577281
;

-- 2019-11-14T06:47:16.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderName1', Name='Name 1 Absender', Description=NULL, Help=NULL, AD_Element_ID=577281 WHERE UPPER(ColumnName)='SENDERNAME1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:47:16.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderName1', Name='Name 1 Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577281 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:47:16.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name 1 Absender', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577281) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577281)
;

-- 2019-11-14T06:47:16.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Name 1 Absender', Name='Name 1 Absender' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577281)
;

-- 2019-11-14T06:47:16.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Name 1 Absender', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577281
;

-- 2019-11-14T06:47:16.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Name 1 Absender', Description=NULL, Help=NULL WHERE AD_Element_ID = 577281
;

-- 2019-11-14T06:47:16.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Name 1 Absender', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577281
;

-- 2019-11-14T06:47:18.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577281
;

-- 2019-11-14T06:47:18.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577281,'en_US') 
;

-- 2019-11-14T06:47:34.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Name 2 Absender', IsTranslated='Y', Name='Name 2 Absender',Updated=TO_TIMESTAMP('2019-11-14 08:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577282
;

-- 2019-11-14T06:47:34.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577282,'de_CH') 
;

-- 2019-11-14T06:47:37.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Name 2 Absender', IsTranslated='Y', Name='Name 2 Absender',Updated=TO_TIMESTAMP('2019-11-14 08:47:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577282
;

-- 2019-11-14T06:47:37.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577282,'de_DE') 
;

-- 2019-11-14T06:47:37.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577282,'de_DE') 
;

-- 2019-11-14T06:47:37.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SenderName2', Name='Name 2 Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577282
;

-- 2019-11-14T06:47:37.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderName2', Name='Name 2 Absender', Description=NULL, Help=NULL, AD_Element_ID=577282 WHERE UPPER(ColumnName)='SENDERNAME2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:47:37.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderName2', Name='Name 2 Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577282 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:47:37.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name 2 Absender', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577282) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577282)
;

-- 2019-11-14T06:47:37.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Name 2 Absender', Name='Name 2 Absender' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577282)
;

-- 2019-11-14T06:47:37.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Name 2 Absender', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577282
;

-- 2019-11-14T06:47:37.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Name 2 Absender', Description=NULL, Help=NULL WHERE AD_Element_ID = 577282
;

-- 2019-11-14T06:47:37.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Name 2 Absender', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577282
;

-- 2019-11-14T06:47:39.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:47:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577282
;

-- 2019-11-14T06:47:39.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577282,'en_US') 
;

-- 2019-11-14T06:47:59.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Straße Absender', IsTranslated='Y', Name='Straße Absender',Updated=TO_TIMESTAMP('2019-11-14 08:47:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577283
;

-- 2019-11-14T06:47:59.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577283,'de_CH') 
;

-- 2019-11-14T06:48:02.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Straße Absender', IsTranslated='Y', Name='Straße Absender',Updated=TO_TIMESTAMP('2019-11-14 08:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577283
;

-- 2019-11-14T06:48:02.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577283,'de_DE') 
;

-- 2019-11-14T06:48:02.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577283,'de_DE') 
;

-- 2019-11-14T06:48:02.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SenderStreet', Name='Straße Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577283
;

-- 2019-11-14T06:48:02.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderStreet', Name='Straße Absender', Description=NULL, Help=NULL, AD_Element_ID=577283 WHERE UPPER(ColumnName)='SENDERSTREET' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:48:02.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderStreet', Name='Straße Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577283 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:48:02.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Straße Absender', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577283) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577283)
;

-- 2019-11-14T06:48:02.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Straße Absender', Name='Straße Absender' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577283)
;

-- 2019-11-14T06:48:02.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Straße Absender', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577283
;

-- 2019-11-14T06:48:02.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Straße Absender', Description=NULL, Help=NULL WHERE AD_Element_ID = 577283
;

-- 2019-11-14T06:48:02.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Straße Absender', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577283
;

-- 2019-11-14T06:48:05.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:48:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577283
;

-- 2019-11-14T06:48:05.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577283,'en_US') 
;

-- 2019-11-14T06:48:40.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='PLZ Absender', IsTranslated='Y', Name='PLZ Absender',Updated=TO_TIMESTAMP('2019-11-14 08:48:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577285
;

-- 2019-11-14T06:48:40.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577285,'de_CH') 
;

-- 2019-11-14T06:48:44.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='PLZ Absender', IsTranslated='Y', Name='PLZ Absender',Updated=TO_TIMESTAMP('2019-11-14 08:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577285
;

-- 2019-11-14T06:48:44.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577285,'de_DE') 
;

-- 2019-11-14T06:48:44.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577285,'de_DE') 
;

-- 2019-11-14T06:48:44.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SenderZipCode', Name='PLZ Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577285
;

-- 2019-11-14T06:48:44.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderZipCode', Name='PLZ Absender', Description=NULL, Help=NULL, AD_Element_ID=577285 WHERE UPPER(ColumnName)='SENDERZIPCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:48:44.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SenderZipCode', Name='PLZ Absender', Description=NULL, Help=NULL WHERE AD_Element_ID=577285 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:48:44.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='PLZ Absender', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577285) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577285)
;

-- 2019-11-14T06:48:44.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='PLZ Absender', Name='PLZ Absender' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577285)
;

-- 2019-11-14T06:48:44.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='PLZ Absender', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577285
;

-- 2019-11-14T06:48:44.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='PLZ Absender', Description=NULL, Help=NULL WHERE AD_Element_ID = 577285
;

-- 2019-11-14T06:48:44.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'PLZ Absender', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577285
;

-- 2019-11-14T06:48:46.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 08:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577285
;

-- 2019-11-14T06:48:46.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577285,'en_US') 
;

-- 2019-11-14T06:49:10.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Versandlager', IsTranslated='Y', Name='Versandlager',Updated=TO_TIMESTAMP('2019-11-14 08:49:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577300
;

-- 2019-11-14T06:49:10.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577300,'de_CH') 
;

-- 2019-11-14T06:49:13.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Versandlager', IsTranslated='Y', Name='Versandlager',Updated=TO_TIMESTAMP('2019-11-14 08:49:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577300
;

-- 2019-11-14T06:49:13.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577300,'de_DE') 
;

-- 2019-11-14T06:49:13.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577300,'de_DE') 
;

-- 2019-11-14T06:49:13.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SendingDepot', Name='Versandlager', Description=NULL, Help=NULL WHERE AD_Element_ID=577300
;

-- 2019-11-14T06:49:13.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SendingDepot', Name='Versandlager', Description=NULL, Help=NULL, AD_Element_ID=577300 WHERE UPPER(ColumnName)='SENDINGDEPOT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T06:49:13.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SendingDepot', Name='Versandlager', Description=NULL, Help=NULL WHERE AD_Element_ID=577300 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T06:49:13.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Versandlager', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577300) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577300)
;

-- 2019-11-14T06:49:13.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Versandlager', Name='Versandlager' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577300)
;

-- 2019-11-14T06:49:13.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Versandlager', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577300
;

-- 2019-11-14T06:49:13.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Versandlager', Description=NULL, Help=NULL WHERE AD_Element_ID = 577300
;

-- 2019-11-14T06:49:13.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Versandlager', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577300
;

-- 2019-11-14T07:15:36.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='chunk', IsTranslated='Y', Name='DPD Konfiguration',Updated=TO_TIMESTAMP('2019-11-14 09:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577260
;

-- 2019-11-14T07:15:36.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577260,'de_CH') 
;

-- 2019-11-14T07:15:41.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='chunk', IsTranslated='Y', Name='chunk',Updated=TO_TIMESTAMP('2019-11-14 09:15:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577260
;

-- 2019-11-14T07:15:41.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577260,'de_DE') 
;

-- 2019-11-14T07:15:41.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577260,'de_DE') 
;

-- 2019-11-14T07:15:41.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DPD_Shipper_config_ID', Name='chunk', Description=NULL, Help=NULL WHERE AD_Element_ID=577260
;

-- 2019-11-14T07:15:41.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DPD_Shipper_config_ID', Name='chunk', Description=NULL, Help=NULL, AD_Element_ID=577260 WHERE UPPER(ColumnName)='DPD_SHIPPER_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T07:15:41.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DPD_Shipper_config_ID', Name='chunk', Description=NULL, Help=NULL WHERE AD_Element_ID=577260 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T07:15:41.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='chunk', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577260) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577260)
;

-- 2019-11-14T07:15:41.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='chunk', Name='chunk' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577260)
;

-- 2019-11-14T07:15:41.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='chunk', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577260
;

-- 2019-11-14T07:15:41.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='chunk', Description=NULL, Help=NULL WHERE AD_Element_ID = 577260
;

-- 2019-11-14T07:15:41.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'chunk', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577260
;

-- 2019-11-14T07:15:45.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DPD Konfiguration',Updated=TO_TIMESTAMP('2019-11-14 09:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577260
;

-- 2019-11-14T07:15:45.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577260,'de_CH') 
;

-- 2019-11-14T07:15:49.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DPD Konfiguration', Name='DPD Konfiguration',Updated=TO_TIMESTAMP('2019-11-14 09:15:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577260
;

-- 2019-11-14T07:15:49.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577260,'de_DE') 
;

-- 2019-11-14T07:15:49.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577260,'de_DE') 
;

-- 2019-11-14T07:15:49.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DPD_Shipper_config_ID', Name='DPD Konfiguration', Description=NULL, Help=NULL WHERE AD_Element_ID=577260
;

-- 2019-11-14T07:15:49.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DPD_Shipper_config_ID', Name='DPD Konfiguration', Description=NULL, Help=NULL, AD_Element_ID=577260 WHERE UPPER(ColumnName)='DPD_SHIPPER_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-14T07:15:49.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DPD_Shipper_config_ID', Name='DPD Konfiguration', Description=NULL, Help=NULL WHERE AD_Element_ID=577260 AND IsCentrallyMaintained='Y'
;

-- 2019-11-14T07:15:49.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DPD Konfiguration', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577260) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577260)
;

-- 2019-11-14T07:15:49.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DPD Konfiguration', Name='DPD Konfiguration' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577260)
;

-- 2019-11-14T07:15:49.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='DPD Konfiguration', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577260
;

-- 2019-11-14T07:15:49.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='DPD Konfiguration', Description=NULL, Help=NULL WHERE AD_Element_ID = 577260
;

-- 2019-11-14T07:15:49.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'DPD Konfiguration', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577260
;

-- 2019-11-14T07:15:51.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-14 09:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577260
;

-- 2019-11-14T07:15:51.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577260,'en_US') 
;

-- 2019-11-14T07:16:00.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DPD Configuration', Name='DPD Configuration',Updated=TO_TIMESTAMP('2019-11-14 09:16:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577260
;

-- 2019-11-14T07:16:00.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577260,'en_US') 
;

