-- 2022-03-24T10:24:44.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ReferenceAsEndToEndId',Updated=TO_TIMESTAMP('2022-03-24 12:24:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715
;

-- 2022-03-24T10:24:44.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ReferenceAsEndToEndId', Name='Sammelüberweisung', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE AD_Element_ID=580715
;

-- 2022-03-24T10:24:44.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReferenceAsEndToEndId', Name='Sammelüberweisung', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL, AD_Element_ID=580715 WHERE UPPER(ColumnName)='REFERENCEASENDTOENDID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-24T10:24:44.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReferenceAsEndToEndId', Name='Sammelüberweisung', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE AD_Element_ID=580715 AND IsCentrallyMaintained='Y'
;

-- 2022-03-24T10:25:27.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If set, the payment reference (or NOTPROVIDED) will be used for the EndToEndId. Otherwise a unique identifier will be generated instead.', Name='Reference as EndToEndId', PrintName='Reference as EndToEndId',Updated=TO_TIMESTAMP('2022-03-24 12:25:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='en_US'
;

-- 2022-03-24T10:25:27.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'en_US') 
;

-- 2022-03-24T10:25:29.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Referenz als EndToEndId', PrintName='Referenz als EndToEndId',Updated=TO_TIMESTAMP('2022-03-24 12:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='de_CH'
;

-- 2022-03-24T10:25:29.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'de_CH') 
;

-- 2022-03-24T10:25:31.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Referenz als EndToEndId', PrintName='Referenz als EndToEndId',Updated=TO_TIMESTAMP('2022-03-24 12:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='de_DE'
;

-- 2022-03-24T10:25:31.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'de_DE') 
;

-- 2022-03-24T10:25:31.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580715,'de_DE') 
;

-- 2022-03-24T10:25:31.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ReferenceAsEndToEndId', Name='Referenz als EndToEndId', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE AD_Element_ID=580715
;

-- 2022-03-24T10:25:31.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReferenceAsEndToEndId', Name='Referenz als EndToEndId', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL, AD_Element_ID=580715 WHERE UPPER(ColumnName)='REFERENCEASENDTOENDID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-24T10:25:31.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReferenceAsEndToEndId', Name='Referenz als EndToEndId', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE AD_Element_ID=580715 AND IsCentrallyMaintained='Y'
;

-- 2022-03-24T10:25:31.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Referenz als EndToEndId', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580715) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580715)
;

-- 2022-03-24T10:25:31.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Referenz als EndToEndId', Name='Referenz als EndToEndId' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580715)
;

-- 2022-03-24T10:25:31.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Referenz als EndToEndId', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580715
;

-- 2022-03-24T10:25:31.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Referenz als EndToEndId', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE AD_Element_ID = 580715
;

-- 2022-03-24T10:25:31.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Referenz als EndToEndId', Description = 'Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580715
;

-- 2022-03-24T10:25:38.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Referenz als EndToEndId', PrintName='Referenz als EndToEndId',Updated=TO_TIMESTAMP('2022-03-24 12:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='nl_NL'
;

-- 2022-03-24T10:25:38.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'nl_NL') 
;

-- 2022-03-24T10:25:39.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.',Updated=TO_TIMESTAMP('2022-03-24 12:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='de_CH'
;

-- 2022-03-24T10:25:39.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'de_CH') 
;

-- 2022-03-24T10:25:40.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.',Updated=TO_TIMESTAMP('2022-03-24 12:25:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='de_DE'
;

-- 2022-03-24T10:25:40.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'de_DE') 
;

-- 2022-03-24T10:25:40.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580715,'de_DE') 
;

-- 2022-03-24T10:25:40.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ReferenceAsEndToEndId', Name='Referenz als EndToEndId', Description='Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.', Help=NULL WHERE AD_Element_ID=580715
;

-- 2022-03-24T10:25:40.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReferenceAsEndToEndId', Name='Referenz als EndToEndId', Description='Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.', Help=NULL, AD_Element_ID=580715 WHERE UPPER(ColumnName)='REFERENCEASENDTOENDID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-24T10:25:40.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReferenceAsEndToEndId', Name='Referenz als EndToEndId', Description='Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.', Help=NULL WHERE AD_Element_ID=580715 AND IsCentrallyMaintained='Y'
;

-- 2022-03-24T10:25:40.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Referenz als EndToEndId', Description='Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580715) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580715)
;

-- 2022-03-24T10:25:40.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Referenz als EndToEndId', Description='Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580715
;

-- 2022-03-24T10:25:40.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Referenz als EndToEndId', Description='Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.', Help=NULL WHERE AD_Element_ID = 580715
;

-- 2022-03-24T10:25:40.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Referenz als EndToEndId', Description = 'Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580715
;

-- 2022-03-24T10:25:44.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn gesetzt, wird die Zahlungsreferenz (oder NOTPROVIDED) für die EndToEndId verwendet. Andernfalls wird stattdessen ein eindeutiger Bezeichner generiert.',Updated=TO_TIMESTAMP('2022-03-24 12:25:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='nl_NL'
;

-- 2022-03-24T10:25:44.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'nl_NL') 
;

-- 2022-03-24T10:25:58.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='0=1',Updated=TO_TIMESTAMP('2022-03-24 12:25:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542229
;

-- 2022-03-24T10:26:40.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='N',Updated=TO_TIMESTAMP('2022-03-24 12:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542229
;
