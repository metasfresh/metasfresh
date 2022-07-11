-- 2022-07-11T12:27:10.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name es Kontoinhabers, der ggf vom Namen des Geschäftsdpartners abweicht.', Help='', Name='Kontoinhaber', PrintName='Kontoinhaber',Updated=TO_TIMESTAMP('2022-07-11 14:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1354 AND AD_Language='de_CH'
;

-- 2022-07-11T12:27:10.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1354,'de_CH') 
;

-- 2022-07-11T12:27:24.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name es Kontoinhabers, der ggf vom Namen des Geschäftsdpartners abweicht.', Help='', Name='Kontoinhaber', PrintName='Kontoinhaber',Updated=TO_TIMESTAMP('2022-07-11 14:27:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1354 AND AD_Language='de_DE'
;

-- 2022-07-11T12:27:24.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1354,'de_DE') 
;

-- 2022-07-11T12:27:24.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1354,'de_DE') 
;

-- 2022-07-11T12:27:24.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='A_Name', Name='Kontoinhaber', Description='Name es Kontoinhabers, der ggf vom Namen des Geschäftsdpartners abweicht.', Help='' WHERE AD_Element_ID=1354
;

-- 2022-07-11T12:27:24.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='A_Name', Name='Kontoinhaber', Description='Name es Kontoinhabers, der ggf vom Namen des Geschäftsdpartners abweicht.', Help='', AD_Element_ID=1354 WHERE UPPER(ColumnName)='A_NAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-11T12:27:24.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='A_Name', Name='Kontoinhaber', Description='Name es Kontoinhabers, der ggf vom Namen des Geschäftsdpartners abweicht.', Help='' WHERE AD_Element_ID=1354 AND IsCentrallyMaintained='Y'
;

-- 2022-07-11T12:27:24.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kontoinhaber', Description='Name es Kontoinhabers, der ggf vom Namen des Geschäftsdpartners abweicht.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1354) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1354)
;

-- 2022-07-11T12:27:24.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kontoinhaber', Name='Kontoinhaber' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1354)
;

-- 2022-07-11T12:27:24.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kontoinhaber', Description='Name es Kontoinhabers, der ggf vom Namen des Geschäftsdpartners abweicht.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 1354
;

-- 2022-07-11T12:27:24.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kontoinhaber', Description='Name es Kontoinhabers, der ggf vom Namen des Geschäftsdpartners abweicht.', Help='' WHERE AD_Element_ID = 1354
;

-- 2022-07-11T12:27:24.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kontoinhaber', Description = 'Name es Kontoinhabers, der ggf vom Namen des Geschäftsdpartners abweicht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1354
;

-- 2022-07-11T12:27:51.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name of the account holder, which may be different from the name of the business partner.', Help='', Name='Account holder', PrintName='Account holder',Updated=TO_TIMESTAMP('2022-07-11 14:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1354 AND AD_Language='en_US'
;

-- 2022-07-11T12:27:51.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1354,'en_US') 
;

