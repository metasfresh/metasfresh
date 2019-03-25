-- 2019-02-13T17:07:53.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Phone call list',Updated=TO_TIMESTAMP('2019-02-13 17:07:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541173
;

-- 2019-02-13T17:08:37.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 17:08:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Anrufliste',PrintName='Anrufliste' WHERE AD_Element_ID=576098 AND AD_Language='de_DE'
;

-- 2019-02-13T17:08:37.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576098,'de_DE') 
;

-- 2019-02-13T17:08:37.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576098,'de_DE') 
;

-- 2019-02-13T17:08:37.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Phonecall_Schema_ID', Name='Anrufliste', Description=NULL, Help=NULL WHERE AD_Element_ID=576098
;

-- 2019-02-13T17:08:37.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_ID', Name='Anrufliste', Description=NULL, Help=NULL, AD_Element_ID=576098 WHERE UPPER(ColumnName)='C_PHONECALL_SCHEMA_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-13T17:08:37.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_ID', Name='Anrufliste', Description=NULL, Help=NULL WHERE AD_Element_ID=576098 AND IsCentrallyMaintained='Y'
;

-- 2019-02-13T17:08:37.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anrufliste', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576098) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576098)
;

-- 2019-02-13T17:08:37.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anrufliste', Name='Anrufliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576098)
;

-- 2019-02-13T17:08:37.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anrufliste', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576098
;

-- 2019-02-13T17:08:37.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anrufliste', Description=NULL, Help=NULL WHERE AD_Element_ID = 576098
;

-- 2019-02-13T17:08:37.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Anrufliste', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576098
;

-- 2019-02-13T17:08:51.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 17:08:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Phone call list',PrintName='Phone call list' WHERE AD_Element_ID=576098 AND AD_Language='en_US'
;

-- 2019-02-13T17:08:51.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576098,'en_US') 
;

-- 2019-02-13T17:09:17.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Anrufliste',Updated=TO_TIMESTAMP('2019-02-13 17:09:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541173
;

-- 2019-02-13T17:09:52.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Anruflistenversion',Updated=TO_TIMESTAMP('2019-02-13 17:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541175
;

-- 2019-02-13T17:10:10.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 17:10:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Anruflistenversion',PrintName='Anruflistenversion' WHERE AD_Element_ID=576100 AND AD_Language='de_DE'
;

-- 2019-02-13T17:10:10.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576100,'de_DE') 
;

-- 2019-02-13T17:10:10.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576100,'de_DE') 
;

-- 2019-02-13T17:10:10.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Phonecall_Schema_Version_ID', Name='Anruflistenversion', Description=NULL, Help=NULL WHERE AD_Element_ID=576100
;

-- 2019-02-13T17:10:10.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_Version_ID', Name='Anruflistenversion', Description=NULL, Help=NULL, AD_Element_ID=576100 WHERE UPPER(ColumnName)='C_PHONECALL_SCHEMA_VERSION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-13T17:10:10.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_Version_ID', Name='Anruflistenversion', Description=NULL, Help=NULL WHERE AD_Element_ID=576100 AND IsCentrallyMaintained='Y'
;

-- 2019-02-13T17:10:10.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anruflistenversion', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576100) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576100)
;

-- 2019-02-13T17:10:10.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anruflistenversion', Name='Anruflistenversion' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576100)
;

-- 2019-02-13T17:10:10.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anruflistenversion', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576100
;

-- 2019-02-13T17:10:10.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anruflistenversion', Description=NULL, Help=NULL WHERE AD_Element_ID = 576100
;

-- 2019-02-13T17:10:10.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Anruflistenversion', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576100
;

-- 2019-02-13T17:10:28.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 17:10:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Phone call list version',PrintName='Phone call list version' WHERE AD_Element_ID=576100 AND AD_Language='en_US'
;

-- 2019-02-13T17:10:28.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576100,'en_US') 
;

-- 2019-02-13T17:10:40.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 17:10:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Anruflistenversion',PrintName='Anruflistenversion' WHERE AD_Element_ID=576100 AND AD_Language='de_CH'
;

-- 2019-02-13T17:10:40.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576100,'de_CH') 
;

-- 2019-02-13T17:10:56.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 17:10:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Anrufliste',PrintName='Anrufliste' WHERE AD_Element_ID=576098 AND AD_Language='de_CH'
;

-- 2019-02-13T17:10:56.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576098,'de_CH') 
;

-- 2019-02-13T17:11:22.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Anruf',Updated=TO_TIMESTAMP('2019-02-13 17:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541176
;

-- 2019-02-13T17:11:40.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 17:11:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Anruf',PrintName='Anruf' WHERE AD_Element_ID=576104 AND AD_Language='de_CH'
;

-- 2019-02-13T17:11:40.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576104,'de_CH') 
;

-- 2019-02-13T17:11:47.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 17:11:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Anruf',PrintName='Anruf' WHERE AD_Element_ID=576104 AND AD_Language='de_DE'
;

-- 2019-02-13T17:11:47.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576104,'de_DE') 
;

-- 2019-02-13T17:11:47.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576104,'de_DE') 
;

-- 2019-02-13T17:11:47.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Phonecall_Data_ID', Name='Anruf', Description=NULL, Help=NULL WHERE AD_Element_ID=576104
;

-- 2019-02-13T17:11:47.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Data_ID', Name='Anruf', Description=NULL, Help=NULL, AD_Element_ID=576104 WHERE UPPER(ColumnName)='C_PHONECALL_DATA_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-13T17:11:47.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Data_ID', Name='Anruf', Description=NULL, Help=NULL WHERE AD_Element_ID=576104 AND IsCentrallyMaintained='Y'
;

-- 2019-02-13T17:11:47.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anruf', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576104) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576104)
;

-- 2019-02-13T17:11:47.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anruf', Name='Anruf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576104)
;

-- 2019-02-13T17:11:47.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anruf', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576104
;

-- 2019-02-13T17:11:47.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anruf', Description=NULL, Help=NULL WHERE AD_Element_ID = 576104
;

-- 2019-02-13T17:11:47.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Anruf', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576104
;

-- 2019-02-13T17:12:04.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 17:12:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Phone call',PrintName='Phone call' WHERE AD_Element_ID=576104 AND AD_Language='en_US'
;

-- 2019-02-13T17:12:04.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576104,'en_US') 
;

