-- 2020-02-14T14:02:39.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anzahl Rechnungen pro Organisation', PrintName='Anzahl Rechnungen pro Organisation',Updated=TO_TIMESTAMP('2020-02-14 16:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577459 AND AD_Language='de_CH'
;

-- 2020-02-14T14:02:39.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577459,'de_CH') 
;

-- 2020-02-14T14:02:42.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anzahl Rechnungen pro Organisation', PrintName='Anzahl Rechnungen pro Organisation',Updated=TO_TIMESTAMP('2020-02-14 16:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577459 AND AD_Language='de_DE'
;

-- 2020-02-14T14:02:42.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577459,'de_DE') 
;

-- 2020-02-14T14:02:42.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577459,'de_DE') 
;

-- 2020-02-14T14:02:42.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Anzahl Rechnungen pro Organisation', Description=NULL, Help=NULL WHERE AD_Element_ID=577459
;

-- 2020-02-14T14:02:42.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Anzahl Rechnungen pro Organisation', Description=NULL, Help=NULL WHERE AD_Element_ID=577459 AND IsCentrallyMaintained='Y'
;

-- 2020-02-14T14:02:42.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Rechnungen pro Organisation', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577459) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577459)
;

-- 2020-02-14T14:02:42.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anzahl Rechnungen pro Organisation', Name='Anzahl Rechnungen pro Organisation' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577459)
;

-- 2020-02-14T14:02:42.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anzahl Rechnungen pro Organisation', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577459
;

-- 2020-02-14T14:02:42.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anzahl Rechnungen pro Organisation', Description=NULL, Help=NULL WHERE AD_Element_ID = 577459
;

-- 2020-02-14T14:02:42.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anzahl Rechnungen pro Organisation', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577459
;

-- 2020-02-14T14:02:50.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anzahl Rechnungen pro Organisation', PrintName='Anzahl Rechnungen pro Organisation',Updated=TO_TIMESTAMP('2020-02-14 16:02:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577459 AND AD_Language='nl_NL'
;

-- 2020-02-14T14:02:50.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577459,'nl_NL') 
;

-- 2020-02-14T14:02:57.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name=' Export Number of Invoices per Organisation', PrintName=' Export Number of Invoices per Organisation',Updated=TO_TIMESTAMP('2020-02-14 16:02:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577459 AND AD_Language='en_US'
;

-- 2020-02-14T14:02:57.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577459,'en_US') 
;

