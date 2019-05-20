-- 2019-03-27T20:56:37.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2543 AND AD_Language='fr_CH'
;

-- 2019-03-27T20:56:37.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2543 AND AD_Language='it_CH'
;

-- 2019-03-27T20:56:37.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2543 AND AD_Language='en_GB'
;

-- 2019-03-27T21:01:26.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Strittig', PrintName='Strittig',Updated=TO_TIMESTAMP('2019-03-27 21:01:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2543 AND AD_Language='de_CH'
;

-- 2019-03-27T21:01:26.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2543,'de_CH') 
;

-- 2019-03-27T21:01:32.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2019-03-27 21:01:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2543 AND AD_Language='en_US'
;

-- 2019-03-27T21:01:32.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2543,'en_US') 
;

-- 2019-03-27T21:01:43.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Strittig', PrintName='Strittig',Updated=TO_TIMESTAMP('2019-03-27 21:01:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2543 AND AD_Language='de_DE'
;

-- 2019-03-27T21:01:43.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2543,'de_DE') 
;

-- 2019-03-27T21:01:43.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2543,'de_DE') 
;

-- 2019-03-27T21:01:43.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInDispute', Name='Strittig', Description='', Help='' WHERE AD_Element_ID=2543
;

-- 2019-03-27T21:01:43.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInDispute', Name='Strittig', Description='', Help='', AD_Element_ID=2543 WHERE UPPER(ColumnName)='ISINDISPUTE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-27T21:01:43.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInDispute', Name='Strittig', Description='', Help='' WHERE AD_Element_ID=2543 AND IsCentrallyMaintained='Y'
;

-- 2019-03-27T21:01:43.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Strittig', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2543) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2543)
;

-- 2019-03-27T21:01:43.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Strittig', Name='Strittig' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2543)
;

-- 2019-03-27T21:01:43.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Strittig', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 2543
;

-- 2019-03-27T21:01:43.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Strittig', Description='', Help='' WHERE AD_Element_ID = 2543
;

-- 2019-03-27T21:01:43.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Strittig', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2543
;

-- 2019-03-27T21:05:39.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Creates export data and attaches it to the respective invoice - if configured accordingly and if the invoice is applicable; does nothing otherwise.',Updated=TO_TIMESTAMP('2019-03-27 21:05:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540062
;

