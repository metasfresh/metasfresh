-- 2021-01-28T17:14:24.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auftragsposition Details', PrintName='Auftragsposition Details',Updated=TO_TIMESTAMP('2021-01-28 19:14:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578671 AND AD_Language='de_CH'
;

-- 2021-01-28T17:14:24.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578671,'de_CH') 
;

-- 2021-01-28T17:14:29.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auftragsposition Details', PrintName='Auftragsposition Details',Updated=TO_TIMESTAMP('2021-01-28 19:14:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578671 AND AD_Language='de_DE'
;

-- 2021-01-28T17:14:29.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578671,'de_DE') 
;

-- 2021-01-28T17:14:29.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578671,'de_DE') 
;

-- 2021-01-28T17:14:29.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_OrderLine_Detail_ID', Name='Auftragsposition Details', Description=NULL, Help=NULL WHERE AD_Element_ID=578671
;

-- 2021-01-28T17:14:29.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_OrderLine_Detail_ID', Name='Auftragsposition Details', Description=NULL, Help=NULL, AD_Element_ID=578671 WHERE UPPER(ColumnName)='C_ORDERLINE_DETAIL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-01-28T17:14:29.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_OrderLine_Detail_ID', Name='Auftragsposition Details', Description=NULL, Help=NULL WHERE AD_Element_ID=578671 AND IsCentrallyMaintained='Y'
;

-- 2021-01-28T17:14:29.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auftragsposition Details', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578671) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578671)
;

-- 2021-01-28T17:14:29.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auftragsposition Details', Name='Auftragsposition Details' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578671)
;

-- 2021-01-28T17:14:29.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auftragsposition Details', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578671
;

-- 2021-01-28T17:14:29.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auftragsposition Details', Description=NULL, Help=NULL WHERE AD_Element_ID = 578671
;

-- 2021-01-28T17:14:29.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auftragsposition Details', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578671
;

-- 2021-01-28T17:14:36.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-28 19:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578671 AND AD_Language='en_US'
;

-- 2021-01-28T17:14:36.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578671,'en_US') 
;

-- 2021-01-28T17:14:39.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Auftragsposition Details', PrintName='Auftragsposition Details',Updated=TO_TIMESTAMP('2021-01-28 19:14:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578671 AND AD_Language='nl_NL'
;

-- 2021-01-28T17:14:39.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578671,'nl_NL') 
;

