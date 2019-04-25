-- 2019-04-23T11:32:32.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='M_Shipment_Declaration_Config', TableName='M_Shipment_Declaration_Config',Updated=TO_TIMESTAMP('2019-04-23 11:32:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541353
;

-- 2019-04-23T11:32:32.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='M_Shipment_Declaration_Config',Updated=TO_TIMESTAMP('2019-04-23 11:32:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555007
;



-- 2019-04-23T11:32:49.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='M_Shipment_Declaration_Config_ID',Updated=TO_TIMESTAMP('2019-04-23 11:32:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576676
;

-- 2019-04-23T11:32:49.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Shipment_Declaration_Config_ID', Name='M_Shipment_Declaration_Creator', Description=NULL, Help=NULL WHERE AD_Element_ID=576676
;

-- 2019-04-23T11:32:49.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Config_ID', Name='M_Shipment_Declaration_Creator', Description=NULL, Help=NULL, AD_Element_ID=576676 WHERE UPPER(ColumnName)='M_SHIPMENT_DECLARATION_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-23T11:32:49.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Config_ID', Name='M_Shipment_Declaration_Creator', Description=NULL, Help=NULL WHERE AD_Element_ID=576676 AND IsCentrallyMaintained='Y'
;

-- 2019-04-23T11:32:59.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='M_Shipment_Declaration_Config', PrintName='M_Shipment_Declaration_Config',Updated=TO_TIMESTAMP('2019-04-23 11:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576676 AND AD_Language='de_CH'
;

-- 2019-04-23T11:32:59.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576676,'de_CH') 
;

-- 2019-04-23T11:33:06.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='M_Shipment_Declaration_Config', PrintName='M_Shipment_Declaration_Config',Updated=TO_TIMESTAMP('2019-04-23 11:33:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576676 AND AD_Language='de_DE'
;

-- 2019-04-23T11:33:06.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576676,'de_DE') 
;

-- 2019-04-23T11:33:06.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576676,'de_DE') 
;

-- 2019-04-23T11:33:06.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Shipment_Declaration_Config_ID', Name='M_Shipment_Declaration_Config', Description=NULL, Help=NULL WHERE AD_Element_ID=576676
;

-- 2019-04-23T11:33:06.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Config_ID', Name='M_Shipment_Declaration_Config', Description=NULL, Help=NULL, AD_Element_ID=576676 WHERE UPPER(ColumnName)='M_SHIPMENT_DECLARATION_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-23T11:33:06.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Config_ID', Name='M_Shipment_Declaration_Config', Description=NULL, Help=NULL WHERE AD_Element_ID=576676 AND IsCentrallyMaintained='Y'
;

-- 2019-04-23T11:33:06.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='M_Shipment_Declaration_Config', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576676) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576676)
;

-- 2019-04-23T11:33:06.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='M_Shipment_Declaration_Config', Name='M_Shipment_Declaration_Config' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576676)
;

-- 2019-04-23T11:33:06.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='M_Shipment_Declaration_Config', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576676
;

-- 2019-04-23T11:33:06.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='M_Shipment_Declaration_Config', Description=NULL, Help=NULL WHERE AD_Element_ID = 576676
;

-- 2019-04-23T11:33:06.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'M_Shipment_Declaration_Config', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576676
;

-- 2019-04-23T11:33:08.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-23 11:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576676 AND AD_Language='de_CH'
;

-- 2019-04-23T11:33:08.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576676,'de_CH') 
;

-- 2019-04-23T11:33:29.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540636,Updated=TO_TIMESTAMP('2019-04-23 11:33:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541353
;

-- 2019-04-23T11:34:01.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET InternalName='M_Shipment_Declaration_Config',Updated=TO_TIMESTAMP('2019-04-23 11:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541752
;

-- 2019-04-23T11:34:15.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='M_Shipment_Declaration_Config',Updated=TO_TIMESTAMP('2019-04-23 11:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541353
;

-- 2019-04-23T11:34:17.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='M_Shipment_Declaration_Config',Updated=TO_TIMESTAMP('2019-04-23 11:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=541353
;

-- 2019-04-23T11:34:20.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='M_Shipment_Declaration_Config',Updated=TO_TIMESTAMP('2019-04-23 11:34:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541353
;

-- 2019-04-23T11:34:23.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='M_Shipment_Declaration_Config',Updated=TO_TIMESTAMP('2019-04-23 11:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541353
;

-- 2019-04-23T11:34:27.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-23 11:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541353
;

-- 2019-04-23T11:34:29.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-23 11:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=541353
;

-- 2019-04-23T11:34:31.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-23 11:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541353
;

