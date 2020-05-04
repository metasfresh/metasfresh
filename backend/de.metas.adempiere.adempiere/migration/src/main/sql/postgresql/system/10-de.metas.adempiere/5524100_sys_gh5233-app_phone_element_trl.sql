-- 2019-06-07T16:56:59.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-07 16:56:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=505 AND AD_Language='de_CH'
;

-- 2019-06-07T16:56:59.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505,'de_CH') 
;

-- 2019-06-07T16:57:13.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', Name='Telefon', PrintName='Telefon',Updated=TO_TIMESTAMP('2019-06-07 16:57:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=505 AND AD_Language='de_CH'
;

-- 2019-06-07T16:57:13.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505,'de_CH') 
;

-- 2019-06-07T16:57:19.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Beschreibt eine Telefon Nummer',Updated=TO_TIMESTAMP('2019-06-07 16:57:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=505 AND AD_Language='de_CH'
;

-- 2019-06-07T16:57:19.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505,'de_CH') 
;

-- 2019-06-07T16:57:31.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='',Updated=TO_TIMESTAMP('2019-06-07 16:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=505 AND AD_Language='en_US'
;

-- 2019-06-07T16:57:31.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505,'en_US') 
;

-- 2019-06-07T16:57:40.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-07 16:57:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=505 AND AD_Language='de_DE'
;

-- 2019-06-07T16:57:40.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505,'de_DE') 
;

-- 2019-06-07T16:57:40.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(505,'de_DE') 
;

-- 2019-06-07T16:57:40.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Phone', Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='' WHERE AD_Element_ID=505
;

-- 2019-06-07T16:57:40.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Phone', Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='', AD_Element_ID=505 WHERE UPPER(ColumnName)='PHONE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-07T16:57:40.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Phone', Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='' WHERE AD_Element_ID=505 AND IsCentrallyMaintained='Y'
;

-- 2019-06-07T16:57:40.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=505) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 505)
;

-- 2019-06-07T16:57:40.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='', CommitWarning = NULL WHERE AD_Element_ID = 505
;

-- 2019-06-07T16:57:40.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='' WHERE AD_Element_ID = 505
;

-- 2019-06-07T16:57:40.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Telefon', Description = 'Beschreibt eine Telefon Nummer', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 505
;

