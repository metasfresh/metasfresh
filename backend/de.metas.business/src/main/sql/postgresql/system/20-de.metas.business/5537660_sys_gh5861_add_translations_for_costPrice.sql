-- 2019-12-02T16:00:36.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Cost Preise', PrintName='Cost Preise',Updated=TO_TIMESTAMP('2019-12-02 18:00:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577400 AND AD_Language='de_CH'
;

-- 2019-12-02T16:00:36.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577400,'de_CH') 
;

-- 2019-12-02T16:00:44.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Cost Preise', PrintName='Cost Preise',Updated=TO_TIMESTAMP('2019-12-02 18:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577400 AND AD_Language='de_DE'
;

-- 2019-12-02T16:00:44.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577400,'de_DE') 
;

-- 2019-12-02T16:00:44.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577400,'de_DE') 
;

-- 2019-12-02T16:00:44.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CostPrice', Name='Cost Preise', Description=NULL, Help=NULL WHERE AD_Element_ID=577400
;

-- 2019-12-02T16:00:44.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CostPrice', Name='Cost Preise', Description=NULL, Help=NULL, AD_Element_ID=577400 WHERE UPPER(ColumnName)='COSTPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-02T16:00:44.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CostPrice', Name='Cost Preise', Description=NULL, Help=NULL WHERE AD_Element_ID=577400 AND IsCentrallyMaintained='Y'
;

-- 2019-12-02T16:00:44.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Cost Preise', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577400) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577400)
;

-- 2019-12-02T16:00:44.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Cost Preise', Name='Cost Preise' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577400)
;

-- 2019-12-02T16:00:44.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Cost Preise', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577400
;

-- 2019-12-02T16:00:44.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Cost Preise', Description=NULL, Help=NULL WHERE AD_Element_ID = 577400
;

-- 2019-12-02T16:00:44.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Cost Preise', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577400
;

