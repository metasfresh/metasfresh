-- 2020-03-12T05:19:10.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-03-12 07:19:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Element_ID=1482
;

-- 2020-03-12T05:19:10.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1482,'en_GB') 
;

-- 2020-03-12T05:19:24.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Kontoauszug Betrag', Name='Kontoauszug Betrag', IsTranslated='Y', Description='Kontoauszug Betrag',Updated=TO_TIMESTAMP('2020-03-12 07:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=1482
;

-- 2020-03-12T05:19:24.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1482,'de_CH') 
;

-- 2020-03-12T05:19:34.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Kontoauszug Betrag', Name='Kontoauszug Betrag', IsTranslated='Y', Description='Kontoauszug Betrag',Updated=TO_TIMESTAMP('2020-03-12 07:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=1482
;

-- 2020-03-12T05:19:34.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1482,'de_DE') 
;

-- 2020-03-12T05:19:34.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1482,'de_DE') 
;

-- 2020-03-12T05:19:34.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='StmtAmt', Name='Kontoauszug Betrag', Description='Kontoauszug Betrag', Help='The Statement Amount indicates the amount of a single statement line.' WHERE AD_Element_ID=1482
;

-- 2020-03-12T05:19:34.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='StmtAmt', Name='Kontoauszug Betrag', Description='Kontoauszug Betrag', Help='The Statement Amount indicates the amount of a single statement line.', AD_Element_ID=1482 WHERE UPPER(ColumnName)='STMTAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-03-12T05:19:34.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='StmtAmt', Name='Kontoauszug Betrag', Description='Kontoauszug Betrag', Help='The Statement Amount indicates the amount of a single statement line.' WHERE AD_Element_ID=1482 AND IsCentrallyMaintained='Y'
;

-- 2020-03-12T05:19:34.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kontoauszug Betrag', Description='Kontoauszug Betrag', Help='The Statement Amount indicates the amount of a single statement line.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1482) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1482)
;

-- 2020-03-12T05:19:34.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kontoauszug Betrag', Name='Kontoauszug Betrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1482)
;

-- 2020-03-12T05:19:34.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kontoauszug Betrag', Description='Kontoauszug Betrag', Help='The Statement Amount indicates the amount of a single statement line.', CommitWarning = NULL WHERE AD_Element_ID = 1482
;

-- 2020-03-12T05:19:34.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kontoauszug Betrag', Description='Kontoauszug Betrag', Help='The Statement Amount indicates the amount of a single statement line.' WHERE AD_Element_ID = 1482
;

-- 2020-03-12T05:19:34.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kontoauszug Betrag', Description = 'Kontoauszug Betrag', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1482
;

