-- 2019-11-21T12:17:47.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591854
;

-- 2019-11-21T12:17:47.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=591854
;

-- 2019-11-21T12:17:47.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=591854
;

-- 2019-11-21T12:19:22.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DPD Versandauftrag', IsTranslated='Y', Name='DPD Versandauftrag',Updated=TO_TIMESTAMP('2019-11-21 14:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577339
;

-- 2019-11-21T12:19:22.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577339,'de_CH') 
;

-- 2019-11-21T12:19:25.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DPD Versandauftrag', IsTranslated='Y', Name='DPD Versandauftrag',Updated=TO_TIMESTAMP('2019-11-21 14:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577339
;

-- 2019-11-21T12:19:25.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577339,'de_DE') 
;

-- 2019-11-21T12:19:25.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577339,'de_DE') 
;

-- 2019-11-21T12:19:25.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DpdDeliveryOrder', Name='DPD Versandauftrag', Description=NULL, Help=NULL WHERE AD_Element_ID=577339
;

-- 2019-11-21T12:19:25.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DpdDeliveryOrder', Name='DPD Versandauftrag', Description=NULL, Help=NULL, AD_Element_ID=577339 WHERE UPPER(ColumnName)='DPDDELIVERYORDER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-21T12:19:25.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DpdDeliveryOrder', Name='DPD Versandauftrag', Description=NULL, Help=NULL WHERE AD_Element_ID=577339 AND IsCentrallyMaintained='Y'
;

-- 2019-11-21T12:19:25.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DPD Versandauftrag', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577339) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577339)
;

-- 2019-11-21T12:19:25.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DPD Versandauftrag', Name='DPD Versandauftrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577339)
;

-- 2019-11-21T12:19:25.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='DPD Versandauftrag', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577339
;

-- 2019-11-21T12:19:25.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='DPD Versandauftrag', Description=NULL, Help=NULL WHERE AD_Element_ID = 577339
;

-- 2019-11-21T12:19:25.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'DPD Versandauftrag', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577339
;

-- 2019-11-21T12:19:28.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-21 14:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577339
;

-- 2019-11-21T12:19:28.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577339,'en_US') 
;

