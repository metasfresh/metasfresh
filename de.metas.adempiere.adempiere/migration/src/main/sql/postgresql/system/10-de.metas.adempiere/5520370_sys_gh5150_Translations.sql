-- 2019-04-24T16:25:08.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Die Lieferung kann nicht reaktiviert werden weil sie bereits eine fertiggestellte Abgabemeldung hat.',Updated=TO_TIMESTAMP('2019-04-24 16:25:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544914
;

-- 2019-04-24T16:25:15.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Die Lieferung kann nicht reaktiviert werden weil sie bereits eine fertiggestellte Abgabemeldung hat.',Updated=TO_TIMESTAMP('2019-04-24 16:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544914
;

-- 2019-04-24T16:27:16.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abgabemeldung Konfiguration', PrintName='Abgabemeldung Konfiguration',Updated=TO_TIMESTAMP('2019-04-24 16:27:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576676 AND AD_Language='de_CH'
;

-- 2019-04-24T16:27:16.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576676,'de_CH') 
;

-- 2019-04-24T16:27:22.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abgabemeldung Konfiguration', PrintName='Abgabemeldung Konfiguration',Updated=TO_TIMESTAMP('2019-04-24 16:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576676 AND AD_Language='de_DE'
;

-- 2019-04-24T16:27:22.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576676,'de_DE') 
;

-- 2019-04-24T16:27:22.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576676,'de_DE') 
;

-- 2019-04-24T16:27:22.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Shipment_Declaration_Config_ID', Name='Abgabemeldung Konfiguration', Description=NULL, Help=NULL WHERE AD_Element_ID=576676
;

-- 2019-04-24T16:27:22.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Config_ID', Name='Abgabemeldung Konfiguration', Description=NULL, Help=NULL, AD_Element_ID=576676 WHERE UPPER(ColumnName)='M_SHIPMENT_DECLARATION_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-24T16:27:22.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Config_ID', Name='Abgabemeldung Konfiguration', Description=NULL, Help=NULL WHERE AD_Element_ID=576676 AND IsCentrallyMaintained='Y'
;

-- 2019-04-24T16:27:22.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abgabemeldung Konfiguration', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576676) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576676)
;

-- 2019-04-24T16:27:22.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abgabemeldung Konfiguration', Name='Abgabemeldung Konfiguration' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576676)
;

-- 2019-04-24T16:27:22.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abgabemeldung Konfiguration', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576676
;

-- 2019-04-24T16:27:22.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abgabemeldung Konfiguration', Description=NULL, Help=NULL WHERE AD_Element_ID = 576676
;

-- 2019-04-24T16:27:22.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abgabemeldung Konfiguration', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576676
;

-- 2019-04-24T16:27:36.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipment Declaration Config', PrintName='Shipment Declaration Config',Updated=TO_TIMESTAMP('2019-04-24 16:27:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576676 AND AD_Language='en_US'
;

-- 2019-04-24T16:27:36.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576676,'en_US') 
;

-- 2019-04-24T16:27:41.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipment Declaration Config', PrintName='Shipment Declaration Config',Updated=TO_TIMESTAMP('2019-04-24 16:27:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576676 AND AD_Language='nl_NL'
;

-- 2019-04-24T16:27:41.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576676,'nl_NL') 
;

-- 2019-04-24T16:27:48.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2019-04-24 16:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576676 AND AD_Language='nl_NL'
;

-- 2019-04-24T16:27:48.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576676,'nl_NL') 
;

