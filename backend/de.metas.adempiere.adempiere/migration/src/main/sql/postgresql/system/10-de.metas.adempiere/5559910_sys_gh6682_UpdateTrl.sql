-- 2020-05-25T09:47:19.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Kontoschlüssel von', Name='Kontoschlüssel von',Updated=TO_TIMESTAMP('2020-05-25 12:47:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542900
;

-- 2020-05-25T09:47:19.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AccountValueFrom', Name='Kontoschlüssel von', Description=NULL, Help=NULL WHERE AD_Element_ID=542900
;

-- 2020-05-25T09:47:19.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AccountValueFrom', Name='Kontoschlüssel von', Description=NULL, Help=NULL, AD_Element_ID=542900 WHERE UPPER(ColumnName)='ACCOUNTVALUEFROM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-25T09:47:19.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AccountValueFrom', Name='Kontoschlüssel von', Description=NULL, Help=NULL WHERE AD_Element_ID=542900 AND IsCentrallyMaintained='Y'
;

-- 2020-05-25T09:47:19.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kontoschlüssel von', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542900) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542900)
;

-- 2020-05-25T09:47:19.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kontoschlüssel von', Name='Kontoschlüssel von' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542900)
;

-- 2020-05-25T09:47:19.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kontoschlüssel von', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542900
;

-- 2020-05-25T09:47:19.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kontoschlüssel von', Description=NULL, Help=NULL WHERE AD_Element_ID = 542900
;

-- 2020-05-25T09:47:19.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kontoschlüssel von', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542900
;

-- 2020-05-25T09:47:26.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2020-05-25 12:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Element_ID=542900
;

-- 2020-05-25T09:47:26.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542900,'fr_CH') 
;

-- 2020-05-25T09:47:34.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Kontoschlüssel von', Name='Kontoschlüssel von',Updated=TO_TIMESTAMP('2020-05-25 12:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=542900
;

-- 2020-05-25T09:47:34.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542900,'de_CH') 
;

-- 2020-05-25T09:47:42.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Kontoschlüssel von', Name='Kontoschlüssel von',Updated=TO_TIMESTAMP('2020-05-25 12:47:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=542900
;

-- 2020-05-25T09:47:42.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542900,'de_DE') 
;

-- 2020-05-25T09:47:42.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542900,'de_DE') 
;

-- 2020-05-25T09:47:42.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AccountValueFrom', Name='Kontoschlüssel von', Description=NULL, Help=NULL WHERE AD_Element_ID=542900
;

-- 2020-05-25T09:47:42.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AccountValueFrom', Name='Kontoschlüssel von', Description=NULL, Help=NULL, AD_Element_ID=542900 WHERE UPPER(ColumnName)='ACCOUNTVALUEFROM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-25T09:47:42.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AccountValueFrom', Name='Kontoschlüssel von', Description=NULL, Help=NULL WHERE AD_Element_ID=542900 AND IsCentrallyMaintained='Y'
;

-- 2020-05-25T09:47:42.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kontoschlüssel von', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542900) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542900)
;

-- 2020-05-25T09:47:42.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kontoschlüssel von', Name='Kontoschlüssel von' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542900)
;

-- 2020-05-25T09:47:42.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kontoschlüssel von', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542900
;

-- 2020-05-25T09:47:42.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kontoschlüssel von', Description=NULL, Help=NULL WHERE AD_Element_ID = 542900
;

-- 2020-05-25T09:47:42.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kontoschlüssel von', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542900
;

-- 2020-05-25T09:48:08.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Kontoschlüssel bis', Name='Kontoschlüssel bis',Updated=TO_TIMESTAMP('2020-05-25 12:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=542901
;

-- 2020-05-25T09:48:08.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542901,'de_CH') 
;

-- 2020-05-25T09:48:14.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Kontoschlüssel bis', Name='Kontoschlüssel bis',Updated=TO_TIMESTAMP('2020-05-25 12:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=542901
;

-- 2020-05-25T09:48:14.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542901,'de_DE') 
;

-- 2020-05-25T09:48:14.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542901,'de_DE') 
;

-- 2020-05-25T09:48:14.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AccountValueTo', Name='Kontoschlüssel bis', Description=NULL, Help=NULL WHERE AD_Element_ID=542901
;

-- 2020-05-25T09:48:14.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AccountValueTo', Name='Kontoschlüssel bis', Description=NULL, Help=NULL, AD_Element_ID=542901 WHERE UPPER(ColumnName)='ACCOUNTVALUETO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-25T09:48:14.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AccountValueTo', Name='Kontoschlüssel bis', Description=NULL, Help=NULL WHERE AD_Element_ID=542901 AND IsCentrallyMaintained='Y'
;

-- 2020-05-25T09:48:14.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kontoschlüssel bis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542901) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542901)
;

-- 2020-05-25T09:48:14.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kontoschlüssel bis', Name='Kontoschlüssel bis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542901)
;

-- 2020-05-25T09:48:14.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kontoschlüssel bis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542901
;

-- 2020-05-25T09:48:14.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kontoschlüssel bis', Description=NULL, Help=NULL WHERE AD_Element_ID = 542901
;

-- 2020-05-25T09:48:14.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kontoschlüssel bis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542901
;

