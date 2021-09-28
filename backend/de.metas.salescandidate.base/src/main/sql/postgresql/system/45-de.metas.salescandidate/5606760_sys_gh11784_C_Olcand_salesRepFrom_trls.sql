-- 2021-09-28T12:16:05.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebpartner aus', PrintName='Vertriebpartner aus',Updated=TO_TIMESTAMP('2021-09-28 15:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579911 AND AD_Language='de_CH'
;

-- 2021-09-28T12:16:05.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579911,'de_CH') 
;

-- 2021-09-28T12:16:10.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebpartner aus', PrintName='Vertriebpartner aus',Updated=TO_TIMESTAMP('2021-09-28 15:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579911 AND AD_Language='de_DE'
;

-- 2021-09-28T12:16:10.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579911,'de_DE') 
;

-- 2021-09-28T12:16:10.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579911,'de_DE') 
;

-- 2021-09-28T12:16:10.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ApplySalesRepFrom', Name='Vertriebpartner aus', Description=NULL, Help=NULL WHERE AD_Element_ID=579911
;

-- 2021-09-28T12:16:10.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ApplySalesRepFrom', Name='Vertriebpartner aus', Description=NULL, Help=NULL, AD_Element_ID=579911 WHERE UPPER(ColumnName)='APPLYSALESREPFROM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-28T12:16:10.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ApplySalesRepFrom', Name='Vertriebpartner aus', Description=NULL, Help=NULL WHERE AD_Element_ID=579911 AND IsCentrallyMaintained='Y'
;

-- 2021-09-28T12:16:10.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertriebpartner aus', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579911) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579911)
;

-- 2021-09-28T12:16:10.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vertriebpartner aus', Name='Vertriebpartner aus' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579911)
;

-- 2021-09-28T12:16:10.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertriebpartner aus', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579911
;

-- 2021-09-28T12:16:10.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertriebpartner aus', Description=NULL, Help=NULL WHERE AD_Element_ID = 579911
;

-- 2021-09-28T12:16:10.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertriebpartner aus', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579911
;

-- 2021-09-28T12:16:16.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebpartner aus', PrintName='Vertriebpartner aus',Updated=TO_TIMESTAMP('2021-09-28 15:16:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579911 AND AD_Language='nl_NL'
;

-- 2021-09-28T12:16:16.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579911,'nl_NL') 
;

