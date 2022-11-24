-- 2021-12-07T16:28:35.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebspartnergruppe', PrintName='Vertriebspartnergruppe',Updated=TO_TIMESTAMP('2021-12-07 18:28:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580269 AND AD_Language='de_CH'
;

-- 2021-12-07T16:28:35.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580269,'de_CH')
;

-- 2021-12-07T16:28:39.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebspartnergruppe', PrintName='Vertriebspartnergruppe',Updated=TO_TIMESTAMP('2021-12-07 18:28:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580269 AND AD_Language='de_DE'
;

-- 2021-12-07T16:28:39.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580269,'de_DE')
;

-- 2021-12-07T16:28:39.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580269,'de_DE')
;

-- 2021-12-07T16:28:39.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BP_Group_Match_ID', Name='Vertriebspartnergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=580269
;

-- 2021-12-07T16:28:39.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Group_Match_ID', Name='Vertriebspartnergruppe', Description=NULL, Help=NULL, AD_Element_ID=580269 WHERE UPPER(ColumnName)='C_BP_GROUP_MATCH_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-07T16:28:39.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Group_Match_ID', Name='Vertriebspartnergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=580269 AND IsCentrallyMaintained='Y'
;

-- 2021-12-07T16:28:39.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertriebspartnergruppe', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580269) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580269)
;

-- 2021-12-07T16:28:39.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vertriebspartnergruppe', Name='Vertriebspartnergruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580269)
;

-- 2021-12-07T16:28:39.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertriebspartnergruppe', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580269
;

-- 2021-12-07T16:28:39.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertriebspartnergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID = 580269
;

-- 2021-12-07T16:28:39.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertriebspartnergruppe', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580269
;

-- 2021-12-07T16:28:43.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebspartnergruppe', PrintName='Vertriebspartnergruppe',Updated=TO_TIMESTAMP('2021-12-07 18:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580269 AND AD_Language='nl_NL'
;

-- 2021-12-07T16:28:43.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580269,'nl_NL')
;
