-- 2019-10-07T16:02:23.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='pickingV2',Updated=TO_TIMESTAMP('2019-10-07 19:02:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540485
;

-- 2019-10-07T16:02:29.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='picking',Updated=TO_TIMESTAMP('2019-10-07 19:02:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540345
;

-- 2019-10-07T16:02:46.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-07 19:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574217 AND AD_Language='de_CH'
;

-- 2019-10-07T16:02:46.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574217,'de_CH') 
;

-- 2019-10-07T16:02:58.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Picking Terminal (v2)', PrintName='Picking Terminal (v2)',Updated=TO_TIMESTAMP('2019-10-07 19:02:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574217 AND AD_Language='en_US'
;

-- 2019-10-07T16:02:58.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574217,'en_US') 
;

-- 2019-10-07T16:03:04.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-07 19:03:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574217 AND AD_Language='de_DE'
;

-- 2019-10-07T16:03:04.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574217,'de_DE') 
;

-- 2019-10-07T16:03:04.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574217,'de_DE') 
;

-- 2019-10-07T16:04:09.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Kommissionier Terminal', PrintName='Kommissionier Terminal',Updated=TO_TIMESTAMP('2019-10-07 19:04:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574330 AND AD_Language='de_CH'
;

-- 2019-10-07T16:04:09.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574330,'de_CH') 
;

-- 2019-10-07T16:04:18.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Picking Terminal', PrintName='Picking Terminal',Updated=TO_TIMESTAMP('2019-10-07 19:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574330 AND AD_Language='en_US'
;

-- 2019-10-07T16:04:18.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574330,'en_US') 
;

-- 2019-10-07T16:04:26.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-07 19:04:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574330 AND AD_Language='de_DE'
;

-- 2019-10-07T16:04:26.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574330,'de_DE') 
;

-- 2019-10-07T16:04:26.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574330,'de_DE') 
;

-- 2019-10-07T16:04:26.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Kommissionier Terminal', Description='', Help=NULL WHERE AD_Element_ID=574330
;

-- 2019-10-07T16:04:26.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Kommissionier Terminal', Description='', Help=NULL WHERE AD_Element_ID=574330 AND IsCentrallyMaintained='Y'
;

-- 2019-10-07T16:04:26.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissionier Terminal', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574330) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574330)
;

-- 2019-10-07T16:04:26.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kommissionier Terminal', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 574330
;

-- 2019-10-07T16:04:26.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kommissionier Terminal', Description='', Help=NULL WHERE AD_Element_ID = 574330
;

-- 2019-10-07T16:04:26.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kommissionier Terminal', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574330
;

