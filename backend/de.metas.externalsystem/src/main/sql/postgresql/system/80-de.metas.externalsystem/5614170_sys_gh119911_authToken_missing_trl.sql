-- 2021-11-17T18:43:47.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Authentifizierungs-Token', PrintName='Authentifizierungs-Token',Updated=TO_TIMESTAMP('2021-11-17 20:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543920 AND AD_Language='de_CH'
;

-- 2021-11-17T18:43:47.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543920,'de_CH') 
;

-- 2021-11-17T18:43:54.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Authentifizierungs-Token', PrintName='Authentifizierungs-Token',Updated=TO_TIMESTAMP('2021-11-17 20:43:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543920 AND AD_Language='nl_NL'
;

-- 2021-11-17T18:43:54.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543920,'nl_NL') 
;

-- 2021-11-17T18:44:07.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Authentifizierungs-Token', PrintName='Authentifizierungs-Token',Updated=TO_TIMESTAMP('2021-11-17 20:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543920 AND AD_Language='de_DE'
;

-- 2021-11-17T18:44:07.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543920,'de_DE') 
;

-- 2021-11-17T18:44:07.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543920,'de_DE') 
;

-- 2021-11-17T18:44:07.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AuthToken', Name='Authentifizierungs-Token', Description=NULL, Help=NULL WHERE AD_Element_ID=543920
;

-- 2021-11-17T18:44:07.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AuthToken', Name='Authentifizierungs-Token', Description=NULL, Help=NULL, AD_Element_ID=543920 WHERE UPPER(ColumnName)='AUTHTOKEN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-17T18:44:07.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AuthToken', Name='Authentifizierungs-Token', Description=NULL, Help=NULL WHERE AD_Element_ID=543920 AND IsCentrallyMaintained='Y'
;

-- 2021-11-17T18:44:07.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Authentifizierungs-Token', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543920) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543920)
;

-- 2021-11-17T18:44:07.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Authentifizierungs-Token', Name='Authentifizierungs-Token' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543920)
;

-- 2021-11-17T18:44:07.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Authentifizierungs-Token', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543920
;

-- 2021-11-17T18:44:07.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Authentifizierungs-Token', Description=NULL, Help=NULL WHERE AD_Element_ID = 543920
;

-- 2021-11-17T18:44:07.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Authentifizierungs-Token', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543920
;

