-- 2022-01-31T06:34:45.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2022-01-31 08:34:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578385
;

-- 2022-01-31T06:34:48.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_grssignum','IsSyncBPartnersToRestEndpoint','CHAR(1)',null,'Y')
;

-- 2022-01-31T06:34:57.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2022-01-31 08:34:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578964
;

-- 2022-01-31T06:34:59.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_grssignum','IsSyncHUsOnMaterialReceipt','CHAR(1)',null,'Y')
;

-- 2022-01-31T06:34:59.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ExternalSystem_Config_GRSSignum SET IsSyncHUsOnMaterialReceipt='Y' WHERE IsSyncHUsOnMaterialReceipt IS NULL
;

-- 2022-01-31T06:35:09.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2022-01-31 08:35:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578963
;

-- 2022-01-31T06:35:12.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_grssignum','IsSyncHUsOnProductionReceipt','CHAR(1)',null,'Y')
;

-- 2022-01-31T06:35:12.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ExternalSystem_Config_GRSSignum SET IsSyncHUsOnProductionReceipt='Y' WHERE IsSyncHUsOnProductionReceipt IS NULL
;

-- 2022-01-31T13:54:24.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If checked, when receiving goods from a purchase order, the received HUs will be locked and automatically exported to GRS. Once initially send, they will from there onwards be automatically send whenever transform operation is done in metasfresh.',Updated=TO_TIMESTAMP('2022-01-31 15:54:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580400 AND AD_Language='en_US'
;

-- 2022-01-31T13:54:24.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580400,'en_US')
;

-- 2022-01-31T13:54:34.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.',Updated=TO_TIMESTAMP('2022-01-31 15:54:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580400 AND AD_Language='de_DE'
;

-- 2022-01-31T13:54:34.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580400,'de_DE')
;

-- 2022-01-31T13:54:34.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580400,'de_DE')
;

-- 2022-01-31T13:54:34.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSyncHUsOnMaterialReceipt', Name='HUs bei Materialeingang senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.', Help=NULL WHERE AD_Element_ID=580400
;

-- 2022-01-31T13:54:34.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnMaterialReceipt', Name='HUs bei Materialeingang senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.', Help=NULL, AD_Element_ID=580400 WHERE UPPER(ColumnName)='ISSYNCHUSONMATERIALRECEIPT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-31T13:54:34.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnMaterialReceipt', Name='HUs bei Materialeingang senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.', Help=NULL WHERE AD_Element_ID=580400 AND IsCentrallyMaintained='Y'
;

-- 2022-01-31T13:54:34.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='HUs bei Materialeingang senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580400) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580400)
;

-- 2022-01-31T13:54:34.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='HUs bei Materialeingang senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580400
;

-- 2022-01-31T13:54:34.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='HUs bei Materialeingang senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.', Help=NULL WHERE AD_Element_ID = 580400
;

-- 2022-01-31T13:54:34.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'HUs bei Materialeingang senden', Description = 'Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580400
;

-- 2022-01-31T13:54:37.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.',Updated=TO_TIMESTAMP('2022-01-31 15:54:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580400 AND AD_Language='nl_NL'
;

-- 2022-01-31T13:54:37.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580400,'nl_NL')
;

-- 2022-01-31T13:54:42.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einer Bestellung die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, wenn eine Transformation in metasfresh durchgeführt wird.',Updated=TO_TIMESTAMP('2022-01-31 15:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580400 AND AD_Language='de_CH'
;

-- 2022-01-31T13:54:42.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580400,'de_CH')
;

-- 2022-01-31T13:55:05.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If checked, when receiving goods from a production order, the received HUs will be locked and automatically exported to GRS. Once initially send, they will from there onwards be automatically send whenever transform operation is done in metasfresh.',Updated=TO_TIMESTAMP('2022-01-31 15:55:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='en_US'
;

-- 2022-01-31T13:55:05.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'en_US')
;

-- 2022-01-31T13:55:14.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.',Updated=TO_TIMESTAMP('2022-01-31 15:55:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='nl_NL'
;

-- 2022-01-31T13:55:14.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'nl_NL')
;

-- 2022-01-31T13:55:16.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.',Updated=TO_TIMESTAMP('2022-01-31 15:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='de_DE'
;

-- 2022-01-31T13:55:16.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'de_DE')
;

-- 2022-01-31T13:55:16.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580401,'de_DE')
;

-- 2022-01-31T13:55:16.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSyncHUsOnProductionReceipt', Name='Neu produzierte HUs senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.', Help=NULL WHERE AD_Element_ID=580401
;

-- 2022-01-31T13:55:16.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnProductionReceipt', Name='Neu produzierte HUs senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.', Help=NULL, AD_Element_ID=580401 WHERE UPPER(ColumnName)='ISSYNCHUSONPRODUCTIONRECEIPT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-31T13:55:16.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnProductionReceipt', Name='Neu produzierte HUs senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.', Help=NULL WHERE AD_Element_ID=580401 AND IsCentrallyMaintained='Y'
;

-- 2022-01-31T13:55:16.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Neu produzierte HUs senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580401) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580401)
;

-- 2022-01-31T13:55:16.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Neu produzierte HUs senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580401
;

-- 2022-01-31T13:55:16.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Neu produzierte HUs senden', Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.', Help=NULL WHERE AD_Element_ID = 580401
;

-- 2022-01-31T13:55:16.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Neu produzierte HUs senden', Description = 'Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580401
;

-- 2022-01-31T13:55:19.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, werden beim Empfang von Waren aus einem Produktionsauftrag die empfangenen HUs gesperrt und automatisch an GRS exportiert. Sobald sie einmal gesendet wurden, werden sie von da an automatisch gesendet, sobald eine Transformation in metasfresh durchgeführt wird.',Updated=TO_TIMESTAMP('2022-01-31 15:55:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='de_CH'
;

-- 2022-01-31T13:55:19.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'de_CH')
;

