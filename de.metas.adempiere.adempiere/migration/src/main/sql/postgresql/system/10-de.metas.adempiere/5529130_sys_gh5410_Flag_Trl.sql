-- 2019-08-21T10:08:13.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aktionsbezug', PrintName='Aktionsbezug',Updated=TO_TIMESTAMP('2019-08-21 13:08:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576946 AND AD_Language='de_DE'
;

-- 2019-08-21T10:08:13.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576946,'de_DE') 
;

-- 2019-08-21T10:08:13.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576946,'de_DE') 
;

-- 2019-08-21T10:08:13.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAllowActionPrice', Name='Aktionsbezug', Description=NULL, Help=NULL WHERE AD_Element_ID=576946
;

-- 2019-08-21T10:08:13.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllowActionPrice', Name='Aktionsbezug', Description=NULL, Help=NULL, AD_Element_ID=576946 WHERE UPPER(ColumnName)='ISALLOWACTIONPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-21T10:08:13.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllowActionPrice', Name='Aktionsbezug', Description=NULL, Help=NULL WHERE AD_Element_ID=576946 AND IsCentrallyMaintained='Y'
;

-- 2019-08-21T10:08:13.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aktionsbezug', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576946) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576946)
;

-- 2019-08-21T10:08:13.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Aktionsbezug', Name='Aktionsbezug' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576946)
;

-- 2019-08-21T10:08:13.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Aktionsbezug', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576946
;

-- 2019-08-21T10:08:13.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Aktionsbezug', Description=NULL, Help=NULL WHERE AD_Element_ID = 576946
;

-- 2019-08-21T10:08:13.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Aktionsbezug', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576946
;

