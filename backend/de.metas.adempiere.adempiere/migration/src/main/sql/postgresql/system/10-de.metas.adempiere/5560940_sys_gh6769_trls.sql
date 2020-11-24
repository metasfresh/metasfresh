-- 2020-06-10T14:21:39.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-10 17:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577746 AND AD_Language='de_CH'
;

-- 2020-06-10T14:21:39.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577746,'de_CH') 
;

-- 2020-06-10T14:21:42.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-10 17:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577746 AND AD_Language='de_DE'
;

-- 2020-06-10T14:21:42.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577746,'de_DE') 
;

-- 2020-06-10T14:21:42.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577746,'de_DE') 
;

-- 2020-06-10T14:21:51.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bank Fee', PrintName='Bank Fee',Updated=TO_TIMESTAMP('2020-06-10 17:21:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577746 AND AD_Language='en_US'
;

-- 2020-06-10T14:21:51.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577746,'en_US') 
;

-- 2020-06-10T14:22:18.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Konto für Bankgebühren', PrintName='Konto für Bankgebühren',Updated=TO_TIMESTAMP('2020-06-10 17:22:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577752 AND AD_Language='de_CH'
;

-- 2020-06-10T14:22:18.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577752,'de_CH') 
;

-- 2020-06-10T14:22:24.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Konto für Bankgebühren', PrintName='Konto für Bankgebühren',Updated=TO_TIMESTAMP('2020-06-10 17:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577752 AND AD_Language='de_DE'
;

-- 2020-06-10T14:22:24.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577752,'de_DE') 
;

-- 2020-06-10T14:22:24.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577752,'de_DE') 
;

-- 2020-06-10T14:22:24.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PayBankFee_Acct', Name='Konto für Bankgebühren', Description=NULL, Help=NULL WHERE AD_Element_ID=577752
;

-- 2020-06-10T14:22:24.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PayBankFee_Acct', Name='Konto für Bankgebühren', Description=NULL, Help=NULL, AD_Element_ID=577752 WHERE UPPER(ColumnName)='PAYBANKFEE_ACCT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-10T14:22:24.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PayBankFee_Acct', Name='Konto für Bankgebühren', Description=NULL, Help=NULL WHERE AD_Element_ID=577752 AND IsCentrallyMaintained='Y'
;

-- 2020-06-10T14:22:24.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Konto für Bankgebühren', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577752) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577752)
;

-- 2020-06-10T14:22:24.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Konto für Bankgebühren', Name='Konto für Bankgebühren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577752)
;

-- 2020-06-10T14:22:24.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Konto für Bankgebühren', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577752
;

-- 2020-06-10T14:22:24.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Konto für Bankgebühren', Description=NULL, Help=NULL WHERE AD_Element_ID = 577752
;

-- 2020-06-10T14:22:24.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Konto für Bankgebühren', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577752
;

-- 2020-06-10T14:22:26.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-10 17:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577752 AND AD_Language='en_US'
;

-- 2020-06-10T14:22:26.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577752,'en_US') 
;

