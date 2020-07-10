-- 2020-05-19T06:59:39.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HU Typ', PrintName='HU Typ',Updated=TO_TIMESTAMP('2020-05-19 09:59:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542316 AND AD_Language='de_DE'
;

-- 2020-05-19T06:59:39.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542316,'de_DE') 
;

-- 2020-05-19T06:59:39.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542316,'de_DE') 
;

-- 2020-05-19T06:59:39.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HU_UnitType', Name='HU Typ', Description=NULL, Help=NULL WHERE AD_Element_ID=542316
;

-- 2020-05-19T06:59:39.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_UnitType', Name='HU Typ', Description=NULL, Help=NULL, AD_Element_ID=542316 WHERE UPPER(ColumnName)='HU_UNITTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-19T06:59:39.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_UnitType', Name='HU Typ', Description=NULL, Help=NULL WHERE AD_Element_ID=542316 AND IsCentrallyMaintained='Y'
;

-- 2020-05-19T06:59:39.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='HU Typ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542316) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542316)
;

-- 2020-05-19T06:59:39.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='HU Typ', Name='HU Typ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542316)
;

-- 2020-05-19T06:59:39.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='HU Typ', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542316
;

-- 2020-05-19T06:59:39.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='HU Typ', Description=NULL, Help=NULL WHERE AD_Element_ID = 542316
;

-- 2020-05-19T06:59:39.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'HU Typ', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542316
;

-- 2020-05-19T06:59:47.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HU Type', PrintName='HU Type',Updated=TO_TIMESTAMP('2020-05-19 09:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542316 AND AD_Language='nl_NL'
;

-- 2020-05-19T06:59:47.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542316,'nl_NL') 
;

-- 2020-05-19T06:59:51.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HU Type', PrintName='HU Type',Updated=TO_TIMESTAMP('2020-05-19 09:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542316 AND AD_Language='en_US'
;

-- 2020-05-19T06:59:51.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542316,'en_US') 
;

-- 2020-05-19T06:59:56.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HU Type', PrintName='HU Type',Updated=TO_TIMESTAMP('2020-05-19 09:59:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542316 AND AD_Language='de_CH'
;

-- 2020-05-19T06:59:56.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542316,'de_CH') 
;

-- 2020-05-19T07:00:00.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HU Type', PrintName='HU Type',Updated=TO_TIMESTAMP('2020-05-19 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542316 AND AD_Language='en_GB'
;

-- 2020-05-19T07:00:00.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542316,'en_GB') 
;

-- 2020-05-19T07:00:15.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-19 10:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542316 AND AD_Language='en_GB'
;

-- 2020-05-19T07:00:15.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542316,'en_GB') 
;

-- 2020-05-19T07:00:19.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-19 10:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542316 AND AD_Language='en_US'
;

-- 2020-05-19T07:00:19.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542316,'en_US') 
;

-- 2020-05-19T07:00:26.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HU Typ', PrintName='HU Typ',Updated=TO_TIMESTAMP('2020-05-19 10:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542316 AND AD_Language='fr_CH'
;

-- 2020-05-19T07:00:26.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542316,'fr_CH') 
;

-- 2020-05-19T07:00:29.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HU Typ', PrintName='HU Typ',Updated=TO_TIMESTAMP('2020-05-19 10:00:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542316 AND AD_Language='it_CH'
;

-- 2020-05-19T07:00:29.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542316,'it_CH') 
;

