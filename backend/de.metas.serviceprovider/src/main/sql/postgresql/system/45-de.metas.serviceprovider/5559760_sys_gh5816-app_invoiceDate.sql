-- 2020-05-20T11:21:45.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoice date', PrintName='Invoice date',Updated=TO_TIMESTAMP('2020-05-20 14:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577730 AND AD_Language='de_CH'
;

-- 2020-05-20T11:21:45.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577730,'de_CH') 
;

-- 2020-05-20T11:21:59.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoice date', PrintName='Invoice date',Updated=TO_TIMESTAMP('2020-05-20 14:21:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577730 AND AD_Language='de_DE'
;

-- 2020-05-20T11:21:59.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577730,'de_DE') 
;

-- 2020-05-20T11:21:59.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577730,'de_DE') 
;

-- 2020-05-20T11:21:59.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoicedDate', Name='Invoice date', Description=NULL, Help=NULL WHERE AD_Element_ID=577730
;

-- 2020-05-20T11:21:59.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicedDate', Name='Invoice date', Description=NULL, Help=NULL, AD_Element_ID=577730 WHERE UPPER(ColumnName)='INVOICEDDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-20T11:21:59.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicedDate', Name='Invoice date', Description=NULL, Help=NULL WHERE AD_Element_ID=577730 AND IsCentrallyMaintained='Y'
;

-- 2020-05-20T11:21:59.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Invoice date', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577730) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577730)
;

-- 2020-05-20T11:21:59.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Invoice date', Name='Invoice date' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577730)
;

-- 2020-05-20T11:21:59.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Invoice date', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577730
;

-- 2020-05-20T11:21:59.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Invoice date', Description=NULL, Help=NULL WHERE AD_Element_ID = 577730
;

-- 2020-05-20T11:21:59.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Invoice date', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577730
;

-- 2020-05-20T11:22:03.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoice date', PrintName='Invoice date',Updated=TO_TIMESTAMP('2020-05-20 14:22:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577730 AND AD_Language='en_US'
;

-- 2020-05-20T11:22:03.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577730,'en_US') 
;

-- 2020-05-20T11:22:08.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoice date', PrintName='Invoice date',Updated=TO_TIMESTAMP('2020-05-20 14:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577730 AND AD_Language='nl_NL'
;

-- 2020-05-20T11:22:08.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577730,'nl_NL') 
;

