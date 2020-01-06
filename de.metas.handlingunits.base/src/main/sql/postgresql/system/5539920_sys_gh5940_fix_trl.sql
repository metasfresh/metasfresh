-- 2019-12-20T09:32:58.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Standard-Packvorschrift', PrintName='Standard-Packvorschrift',Updated=TO_TIMESTAMP('2019-12-20 11:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577437 AND AD_Language='de_CH'
;

-- 2019-12-20T09:32:58.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577437,'de_CH') 
;

-- 2019-12-20T09:33:03.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Standard-Packvorschrift', PrintName='Standard-Packvorschrift',Updated=TO_TIMESTAMP('2019-12-20 11:33:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577437 AND AD_Language='de_DE'
;

-- 2019-12-20T09:33:03.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577437,'de_DE') 
;

-- 2019-12-20T09:33:03.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577437,'de_DE') 
;

-- 2019-12-20T09:33:03.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsDefaultForProduct', Name='Standard-Packvorschrift', Description=NULL, Help=NULL WHERE AD_Element_ID=577437
;

-- 2019-12-20T09:33:03.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDefaultForProduct', Name='Standard-Packvorschrift', Description=NULL, Help=NULL, AD_Element_ID=577437 WHERE UPPER(ColumnName)='ISDEFAULTFORPRODUCT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-20T09:33:03.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDefaultForProduct', Name='Standard-Packvorschrift', Description=NULL, Help=NULL WHERE AD_Element_ID=577437 AND IsCentrallyMaintained='Y'
;

-- 2019-12-20T09:33:03.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Standard-Packvorschrift', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577437) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577437)
;

-- 2019-12-20T09:33:03.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Standard-Packvorschrift', Name='Standard-Packvorschrift' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577437)
;

-- 2019-12-20T09:33:03.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Standard-Packvorschrift', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577437
;

-- 2019-12-20T09:33:03.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Standard-Packvorschrift', Description=NULL, Help=NULL WHERE AD_Element_ID = 577437
;

-- 2019-12-20T09:33:03.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Standard-Packvorschrift', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577437
;

