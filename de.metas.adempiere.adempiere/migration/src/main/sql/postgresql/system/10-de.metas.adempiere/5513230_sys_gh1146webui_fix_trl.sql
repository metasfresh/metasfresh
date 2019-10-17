-- 2019-02-19T15:57:24.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 15:57:24','YYYY-MM-DD HH24:MI:SS'),Name='Schnelleingabe einschalten',PrintName='Schnelleingabe einschalten' WHERE AD_Element_ID=576109 AND AD_Language='de_CH'
;

-- 2019-02-19T15:57:24.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576109,'de_CH') 
;

-- 2019-02-19T15:57:31.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 15:57:31','YYYY-MM-DD HH24:MI:SS'),Name='Schnelleingabe einschalten',PrintName='Schnelleingabe einschalten' WHERE AD_Element_ID=576109 AND AD_Language='de_DE'
;

-- 2019-02-19T15:57:31.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576109,'de_DE') 
;

-- 2019-02-19T15:57:31.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576109,'de_DE') 
;

-- 2019-02-19T15:57:31.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AllowQuickInput', Name='Schnelleingabe einschalten', Description=NULL, Help=NULL WHERE AD_Element_ID=576109
;

-- 2019-02-19T15:57:31.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AllowQuickInput', Name='Schnelleingabe einschalten', Description=NULL, Help=NULL, AD_Element_ID=576109 WHERE UPPER(ColumnName)='ALLOWQUICKINPUT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-19T15:57:31.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AllowQuickInput', Name='Schnelleingabe einschalten', Description=NULL, Help=NULL WHERE AD_Element_ID=576109 AND IsCentrallyMaintained='Y'
;

-- 2019-02-19T15:57:31.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Schnelleingabe einschalten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576109) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576109)
;

-- 2019-02-19T15:57:31.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Schnelleingabe einschalten', Name='Schnelleingabe einschalten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576109)
;

-- 2019-02-19T15:57:31.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Schnelleingabe einschalten', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576109
;

-- 2019-02-19T15:57:31.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Schnelleingabe einschalten', Description=NULL, Help=NULL WHERE AD_Element_ID = 576109
;

-- 2019-02-19T15:57:31.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Schnelleingabe einschalten', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576109
;

