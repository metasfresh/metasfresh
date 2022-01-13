-- 2021-07-09T16:23:51.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe Norm', PrintName='Lieferantenfreigabe Norm',Updated=TO_TIMESTAMP('2021-07-09 19:23:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579454 AND AD_Language='de_DE'
;

-- 2021-07-09T16:23:51.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579454,'de_DE') 
;

-- 2021-07-09T16:23:51.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579454,'de_DE') 
;

-- 2021-07-09T16:23:51.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SupplierApproval_Norm', Name='Lieferantenfreigabe Norm', Description=NULL, Help=NULL WHERE AD_Element_ID=579454
;

-- 2021-07-09T16:23:51.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval_Norm', Name='Lieferantenfreigabe Norm', Description=NULL, Help=NULL, AD_Element_ID=579454 WHERE UPPER(ColumnName)='SUPPLIERAPPROVAL_NORM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-09T16:23:51.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval_Norm', Name='Lieferantenfreigabe Norm', Description=NULL, Help=NULL WHERE AD_Element_ID=579454 AND IsCentrallyMaintained='Y'
;

-- 2021-07-09T16:23:51.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferantenfreigabe Norm', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579454) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579454)
;

-- 2021-07-09T16:23:51.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferantenfreigabe Norm', Name='Lieferantenfreigabe Norm' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579454)
;

-- 2021-07-09T16:23:51.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferantenfreigabe Norm', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579454
;

-- 2021-07-09T16:23:51.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferantenfreigabe Norm', Description=NULL, Help=NULL WHERE AD_Element_ID = 579454
;

-- 2021-07-09T16:23:51.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferantenfreigabe Norm', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579454
;

-- 2021-07-09T16:23:56.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe Norm', PrintName='Lieferantenfreigabe Norm',Updated=TO_TIMESTAMP('2021-07-09 19:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579454 AND AD_Language='de_CH'
;

-- 2021-07-09T16:23:56.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579454,'de_CH') 
;

