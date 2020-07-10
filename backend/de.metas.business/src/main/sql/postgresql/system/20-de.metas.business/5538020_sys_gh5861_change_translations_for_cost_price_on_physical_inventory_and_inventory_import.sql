-- 2019-12-05T10:05:32.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kostenpreis', PrintName='Kostenpreis',Updated=TO_TIMESTAMP('2019-12-05 12:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577400 AND AD_Language='de_CH'
;

-- 2019-12-05T10:05:32.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577400,'de_CH') 
;

-- 2019-12-05T10:05:40.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kostenpreis', PrintName='Kostenpreis',Updated=TO_TIMESTAMP('2019-12-05 12:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577400 AND AD_Language='de_DE'
;

-- 2019-12-05T10:05:40.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577400,'de_DE') 
;

-- 2019-12-05T10:05:40.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577400,'de_DE') 
;

-- 2019-12-05T10:05:40.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CostPrice', Name='Kostenpreis', Description=NULL, Help=NULL WHERE AD_Element_ID=577400
;

-- 2019-12-05T10:05:40.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CostPrice', Name='Kostenpreis', Description=NULL, Help=NULL, AD_Element_ID=577400 WHERE UPPER(ColumnName)='COSTPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-05T10:05:40.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CostPrice', Name='Kostenpreis', Description=NULL, Help=NULL WHERE AD_Element_ID=577400 AND IsCentrallyMaintained='Y'
;

-- 2019-12-05T10:05:40.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kostenpreis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577400) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577400)
;

-- 2019-12-05T10:05:40.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kostenpreis', Name='Kostenpreis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577400)
;

-- 2019-12-05T10:05:40.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kostenpreis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577400
;

-- 2019-12-05T10:05:40.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kostenpreis', Description=NULL, Help=NULL WHERE AD_Element_ID = 577400
;

-- 2019-12-05T10:05:40.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kostenpreis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577400
;

-- 2019-12-05T10:05:58.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kostenpreis', PrintName='Kostenpreis',Updated=TO_TIMESTAMP('2019-12-05 12:05:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577400 AND AD_Language='nl_NL'
;

-- 2019-12-05T10:05:58.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577400,'nl_NL') 
;

