-- 2021-11-17T11:57:27.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='GRSSignum Config', PrintName='GRSSignum Config',Updated=TO_TIMESTAMP('2021-11-17 13:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579956 AND AD_Language='de_CH'
;

-- 2021-11-17T11:57:27.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579956,'de_CH') 
;

-- 2021-11-17T11:57:33.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='GRSSignum Config', PrintName='GRSSignum Config',Updated=TO_TIMESTAMP('2021-11-17 13:57:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579956 AND AD_Language='de_DE'
;

-- 2021-11-17T11:57:33.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579956,'de_DE') 
;

-- 2021-11-17T11:57:33.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579956,'de_DE') 
;

-- 2021-11-17T11:57:33.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_GRSSignum_ID', Name='GRSSignum Config', Description=NULL, Help=NULL WHERE AD_Element_ID=579956
;

-- 2021-11-17T11:57:33.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_GRSSignum_ID', Name='GRSSignum Config', Description=NULL, Help=NULL, AD_Element_ID=579956 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_GRSSIGNUM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-17T11:57:33.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_GRSSignum_ID', Name='GRSSignum Config', Description=NULL, Help=NULL WHERE AD_Element_ID=579956 AND IsCentrallyMaintained='Y'
;

-- 2021-11-17T11:57:33.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='GRSSignum Config', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579956) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579956)
;

-- 2021-11-17T11:57:33.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='GRSSignum Config', Name='GRSSignum Config' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579956)
;

-- 2021-11-17T11:57:33.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='GRSSignum Config', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579956
;

-- 2021-11-17T11:57:33.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='GRSSignum Config', Description=NULL, Help=NULL WHERE AD_Element_ID = 579956
;

-- 2021-11-17T11:57:33.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'GRSSignum Config', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579956
;

-- 2021-11-17T11:57:38.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='GRSSignum Config', PrintName='GRSSignum Config',Updated=TO_TIMESTAMP('2021-11-17 13:57:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579956 AND AD_Language='en_US'
;

-- 2021-11-17T11:57:38.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579956,'en_US') 
;

-- 2021-11-17T11:57:42.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='GRSSignum Config', PrintName='GRSSignum Config',Updated=TO_TIMESTAMP('2021-11-17 13:57:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579956 AND AD_Language='nl_NL'
;

-- 2021-11-17T11:57:42.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579956,'nl_NL') 
;

