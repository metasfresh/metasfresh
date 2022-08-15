-- 2022-01-13T10:13:57.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sperre Einkauf Grund', PrintName='Sperre Einkauf Grund',Updated=TO_TIMESTAMP('2022-01-13 12:13:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580468 AND AD_Language='de_CH'
;

-- 2022-01-13T10:13:57.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580468,'de_CH') 
;

-- 2022-01-13T10:14:04.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sperre Einkauf Grund', PrintName='Sperre Einkauf Grund',Updated=TO_TIMESTAMP('2022-01-13 12:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580468 AND AD_Language='de_DE'
;

-- 2022-01-13T10:14:04.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580468,'de_DE') 
;

-- 2022-01-13T10:14:04.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580468,'de_DE') 
;

-- 2022-01-13T10:14:04.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExclusionFromPurchaseReason', Name='Sperre Einkauf Grund', Description=NULL, Help=NULL WHERE AD_Element_ID=580468
;

-- 2022-01-13T10:14:04.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromPurchaseReason', Name='Sperre Einkauf Grund', Description=NULL, Help=NULL, AD_Element_ID=580468 WHERE UPPER(ColumnName)='EXCLUSIONFROMPURCHASEREASON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-13T10:14:04.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromPurchaseReason', Name='Sperre Einkauf Grund', Description=NULL, Help=NULL WHERE AD_Element_ID=580468 AND IsCentrallyMaintained='Y'
;

-- 2022-01-13T10:14:04.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sperre Einkauf Grund', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580468) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580468)
;

-- 2022-01-13T10:14:04.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sperre Einkauf Grund', Name='Sperre Einkauf Grund' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580468)
;

-- 2022-01-13T10:14:04.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sperre Einkauf Grund', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580468
;

-- 2022-01-13T10:14:04.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sperre Einkauf Grund', Description=NULL, Help=NULL WHERE AD_Element_ID = 580468
;

-- 2022-01-13T10:14:04.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sperre Einkauf Grund', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580468
;

-- 2022-01-13T10:14:08.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sperre Einkauf Grund', PrintName='Sperre Einkauf Grund',Updated=TO_TIMESTAMP('2022-01-13 12:14:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580468 AND AD_Language='nl_NL'
;

-- 2022-01-13T10:14:08.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580468,'nl_NL') 
;

