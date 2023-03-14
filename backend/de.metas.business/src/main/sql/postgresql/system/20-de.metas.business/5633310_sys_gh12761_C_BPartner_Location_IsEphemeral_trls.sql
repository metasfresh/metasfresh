-- 2022-04-05T05:10:07.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einmalanschrift', PrintName='Einmalanschrift',Updated=TO_TIMESTAMP('2022-04-05 08:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='de_CH'
;

-- 2022-04-05T05:10:07.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'de_CH') 
;

-- 2022-04-05T05:10:15.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einmalanschrift', PrintName='Einmalanschrift',Updated=TO_TIMESTAMP('2022-04-05 08:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='de_DE'
;

-- 2022-04-05T05:10:15.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'de_DE') 
;

-- 2022-04-05T05:10:15.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580745,'de_DE') 
;

-- 2022-04-05T05:10:15.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsEphemeral', Name='Einmalanschrift', Description=NULL, Help=NULL WHERE AD_Element_ID=580745
;

-- 2022-04-05T05:10:15.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEphemeral', Name='Einmalanschrift', Description=NULL, Help=NULL, AD_Element_ID=580745 WHERE UPPER(ColumnName)='ISEPHEMERAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-05T05:10:15.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEphemeral', Name='Einmalanschrift', Description=NULL, Help=NULL WHERE AD_Element_ID=580745 AND IsCentrallyMaintained='Y'
;

-- 2022-04-05T05:10:15.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einmalanschrift', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580745) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580745)
;

-- 2022-04-05T05:10:15.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Einmalanschrift', Name='Einmalanschrift' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580745)
;

-- 2022-04-05T05:10:15.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Einmalanschrift', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580745
;

-- 2022-04-05T05:10:15.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Einmalanschrift', Description=NULL, Help=NULL WHERE AD_Element_ID = 580745
;

-- 2022-04-05T05:10:15.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Einmalanschrift', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580745
;

-- 2022-04-05T05:10:21.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einmalanschrift', PrintName='Einmalanschrift',Updated=TO_TIMESTAMP('2022-04-05 08:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='nl_NL'
;

-- 2022-04-05T05:10:21.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'nl_NL') 
;

-- 2022-04-05T05:10:28.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='One-time-address', PrintName='One-time-address',Updated=TO_TIMESTAMP('2022-04-05 08:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='en_US'
;

-- 2022-04-05T05:10:28.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'en_US') 
;


-- 2022-04-05T05:41:12.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='One-time address of the business partner',Updated=TO_TIMESTAMP('2022-04-05 08:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='en_US'
;

-- 2022-04-05T05:41:12.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'en_US')
;

-- 2022-04-05T05:41:31.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Einmalanschrift des Geschäftspartners',Updated=TO_TIMESTAMP('2022-04-05 08:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='de_CH'
;

-- 2022-04-05T05:41:31.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'de_CH')
;

-- 2022-04-05T05:41:34.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Einmalanschrift des Geschäftspartners',Updated=TO_TIMESTAMP('2022-04-05 08:41:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='de_DE'
;

-- 2022-04-05T05:41:34.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'de_DE')
;

-- 2022-04-05T05:41:34.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580745,'de_DE')
;

-- 2022-04-05T05:41:34.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsEphemeral', Name='Einmalanschrift', Description='Einmalanschrift des Geschäftspartners', Help=NULL WHERE AD_Element_ID=580745
;

-- 2022-04-05T05:41:34.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEphemeral', Name='Einmalanschrift', Description='Einmalanschrift des Geschäftspartners', Help=NULL, AD_Element_ID=580745 WHERE UPPER(ColumnName)='ISEPHEMERAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-05T05:41:34.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEphemeral', Name='Einmalanschrift', Description='Einmalanschrift des Geschäftspartners', Help=NULL WHERE AD_Element_ID=580745 AND IsCentrallyMaintained='Y'
;

-- 2022-04-05T05:41:34.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einmalanschrift', Description='Einmalanschrift des Geschäftspartners', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580745) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580745)
;

-- 2022-04-05T05:41:34.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Einmalanschrift', Description='Einmalanschrift des Geschäftspartners', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580745
;

-- 2022-04-05T05:41:34.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Einmalanschrift', Description='Einmalanschrift des Geschäftspartners', Help=NULL WHERE AD_Element_ID = 580745
;

-- 2022-04-05T05:41:34.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Einmalanschrift', Description = 'Einmalanschrift des Geschäftspartners', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580745
;

-- 2022-04-05T05:41:38.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Einmalanschrift des Geschäftspartners',Updated=TO_TIMESTAMP('2022-04-05 08:41:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='nl_NL'
;

-- 2022-04-05T05:41:38.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'nl_NL')
;

