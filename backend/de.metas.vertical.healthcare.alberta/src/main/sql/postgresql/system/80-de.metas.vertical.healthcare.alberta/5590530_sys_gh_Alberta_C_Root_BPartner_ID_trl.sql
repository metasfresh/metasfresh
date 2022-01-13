-- 2021-05-31T06:10:18.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Geschäftspartner, unter dem Alberta-Nutzer bei der Synchronisation nach metasfersh angelegt werden.', Name='Alberta-Geschäftspartner', PrintName='Alberta-Geschäftspartner',Updated=TO_TIMESTAMP('2021-05-31 09:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579240 AND AD_Language='de_CH'
;

-- 2021-05-31T06:10:18.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579240,'de_CH') 
;

-- 2021-05-31T06:10:29.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Geschäftspartner, unter dem Alberta-Nutzer bei der Synchronisation nach metasfersh angelegt werden.', Name='Alberta-Geschäftspartner', PrintName='Alberta-Geschäftspartner',Updated=TO_TIMESTAMP('2021-05-31 09:10:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579240 AND AD_Language='de_DE'
;

-- 2021-05-31T06:10:29.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579240,'de_DE') 
;

-- 2021-05-31T06:10:29.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579240,'de_DE') 
;

-- 2021-05-31T06:10:29.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Root_BPartner_ID', Name='Alberta-Geschäftspartner', Description='Geschäftspartner, unter dem Alberta-Nutzer bei der Synchronisation nach metasfersh angelegt werden.', Help=NULL WHERE AD_Element_ID=579240
;

-- 2021-05-31T06:10:29.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Root_BPartner_ID', Name='Alberta-Geschäftspartner', Description='Geschäftspartner, unter dem Alberta-Nutzer bei der Synchronisation nach metasfersh angelegt werden.', Help=NULL, AD_Element_ID=579240 WHERE UPPER(ColumnName)='C_ROOT_BPARTNER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-31T06:10:29.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Root_BPartner_ID', Name='Alberta-Geschäftspartner', Description='Geschäftspartner, unter dem Alberta-Nutzer bei der Synchronisation nach metasfersh angelegt werden.', Help=NULL WHERE AD_Element_ID=579240 AND IsCentrallyMaintained='Y'
;

-- 2021-05-31T06:10:29.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Alberta-Geschäftspartner', Description='Geschäftspartner, unter dem Alberta-Nutzer bei der Synchronisation nach metasfersh angelegt werden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579240) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579240)
;

-- 2021-05-31T06:10:29.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Alberta-Geschäftspartner', Name='Alberta-Geschäftspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579240)
;

-- 2021-05-31T06:10:29.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Alberta-Geschäftspartner', Description='Geschäftspartner, unter dem Alberta-Nutzer bei der Synchronisation nach metasfersh angelegt werden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579240
;

-- 2021-05-31T06:10:29.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Alberta-Geschäftspartner', Description='Geschäftspartner, unter dem Alberta-Nutzer bei der Synchronisation nach metasfersh angelegt werden.', Help=NULL WHERE AD_Element_ID = 579240
;

-- 2021-05-31T06:10:29.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Alberta-Geschäftspartner', Description = 'Geschäftspartner, unter dem Alberta-Nutzer bei der Synchronisation nach metasfersh angelegt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579240
;

-- 2021-05-31T06:10:44.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Business partner to which Alberta users are assigned when synched to metasfresh.', Name='Alberta business partner', PrintName='Alberta business partner',Updated=TO_TIMESTAMP('2021-05-31 09:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579240 AND AD_Language='en_US'
;

-- 2021-05-31T06:10:44.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579240,'en_US') 
;

-- 2021-05-31T06:10:54.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Business partner to which Alberta users are assigned when synched to metasfresh.', Name='Alberta business partner', PrintName='Alberta business partner',Updated=TO_TIMESTAMP('2021-05-31 09:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579240 AND AD_Language='nl_NL'
;

-- 2021-05-31T06:10:54.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579240,'nl_NL') 
;

