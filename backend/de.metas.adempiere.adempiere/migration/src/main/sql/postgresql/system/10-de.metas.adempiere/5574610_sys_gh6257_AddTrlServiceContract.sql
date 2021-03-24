-- 2020-12-11T10:58:27.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Servicevertrag', PrintName='Servicevertrag',Updated=TO_TIMESTAMP('2020-12-11 12:58:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578592 AND AD_Language='de_CH'
;

-- 2020-12-11T10:58:27.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578592,'de_CH') 
;

-- 2020-12-11T10:58:35.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Servicevertrag', PrintName='Servicevertrag',Updated=TO_TIMESTAMP('2020-12-11 12:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578592 AND AD_Language='de_DE'
;

-- 2020-12-11T10:58:35.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578592,'de_DE') 
;

-- 2020-12-11T10:58:35.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578592,'de_DE') 
;

-- 2020-12-11T10:58:35.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceContract', Name='Servicevertrag', Description=NULL, Help=NULL WHERE AD_Element_ID=578592
;

-- 2020-12-11T10:58:35.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceContract', Name='Servicevertrag', Description=NULL, Help=NULL, AD_Element_ID=578592 WHERE UPPER(ColumnName)='SERVICECONTRACT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-12-11T10:58:35.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceContract', Name='Servicevertrag', Description=NULL, Help=NULL WHERE AD_Element_ID=578592 AND IsCentrallyMaintained='Y'
;

-- 2020-12-11T10:58:35.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Servicevertrag', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578592) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578592)
;

-- 2020-12-11T10:58:35.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Servicevertrag', Name='Servicevertrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578592)
;

-- 2020-12-11T10:58:35.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Servicevertrag', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578592
;

-- 2020-12-11T10:58:35.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Servicevertrag', Description=NULL, Help=NULL WHERE AD_Element_ID = 578592
;

-- 2020-12-11T10:58:35.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Servicevertrag', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578592
;

-- 2020-12-11T10:58:46.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-12-11 12:58:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578592 AND AD_Language='en_US'
;

-- 2020-12-11T10:58:46.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578592,'en_US') 
;

-- 2020-12-11T10:59:15.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Service Contract', PrintName='Service Contract',Updated=TO_TIMESTAMP('2020-12-11 12:59:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578592 AND AD_Language='en_US'
;

-- 2020-12-11T10:59:15.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578592,'en_US') 
;

