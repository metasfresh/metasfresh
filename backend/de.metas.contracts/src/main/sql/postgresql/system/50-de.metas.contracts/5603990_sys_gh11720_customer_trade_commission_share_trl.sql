-- 2021-09-09T16:29:04.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Customer Margin Settings', PrintName='Customer Margin Settings',Updated=TO_TIMESTAMP('2021-09-09 19:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577414
;

-- 2021-09-09T16:29:04.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Customer_Trade_Margin_ID', Name='Customer Margin Settings', Description=NULL, Help=NULL WHERE AD_Element_ID=577414
;

-- 2021-09-09T16:29:04.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customer_Trade_Margin_ID', Name='Customer Margin Settings', Description=NULL, Help=NULL, AD_Element_ID=577414 WHERE UPPER(ColumnName)='C_CUSTOMER_TRADE_MARGIN_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-09T16:29:04.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customer_Trade_Margin_ID', Name='Customer Margin Settings', Description=NULL, Help=NULL WHERE AD_Element_ID=577414 AND IsCentrallyMaintained='Y'
;

-- 2021-09-09T16:29:04.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Customer Margin Settings', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577414) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577414)
;

-- 2021-09-09T16:29:04.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Customer Margin Settings', Name='Customer Margin Settings' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577414)
;

-- 2021-09-09T16:29:04.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Customer Margin Settings', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577414
;

-- 2021-09-09T16:29:04.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Customer Margin Settings', Description=NULL, Help=NULL WHERE AD_Element_ID = 577414
;

-- 2021-09-09T16:29:04.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Customer Margin Settings', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577414
;

-- 2021-09-09T16:29:17.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Margeneinstellungen Kunde', PrintName='Margeneinstellungen Kunde',Updated=TO_TIMESTAMP('2021-09-09 19:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577414 AND AD_Language='de_CH'
;

-- 2021-09-09T16:29:17.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577414,'de_CH') 
;

-- 2021-09-09T16:29:31.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Margeneinstellungen Kunde', PrintName='Margeneinstellungen Kunde',Updated=TO_TIMESTAMP('2021-09-09 19:29:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577414 AND AD_Language='de_DE'
;

-- 2021-09-09T16:29:31.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577414,'de_DE') 
;

-- 2021-09-09T16:29:31.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577414,'de_DE') 
;

-- 2021-09-09T16:29:31.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Customer_Trade_Margin_ID', Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL WHERE AD_Element_ID=577414
;

-- 2021-09-09T16:29:31.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customer_Trade_Margin_ID', Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL, AD_Element_ID=577414 WHERE UPPER(ColumnName)='C_CUSTOMER_TRADE_MARGIN_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-09T16:29:31.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customer_Trade_Margin_ID', Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL WHERE AD_Element_ID=577414 AND IsCentrallyMaintained='Y'
;

-- 2021-09-09T16:29:31.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577414) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577414)
;

-- 2021-09-09T16:29:31.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Margeneinstellungen Kunde', Name='Margeneinstellungen Kunde' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577414)
;

-- 2021-09-09T16:29:31.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577414
;

-- 2021-09-09T16:29:31.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL WHERE AD_Element_ID = 577414
;

-- 2021-09-09T16:29:31.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Margeneinstellungen Kunde', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577414
;

-- 2021-09-09T16:29:42.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Customer Margin Settings', PrintName='Customer Margin Settings',Updated=TO_TIMESTAMP('2021-09-09 19:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577414 AND AD_Language='en_US'
;

-- 2021-09-09T16:29:42.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577414,'en_US') 
;

-- 2021-09-09T16:29:50.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Margeneinstellungen Kunde', PrintName='Margeneinstellungen Kunde',Updated=TO_TIMESTAMP('2021-09-09 19:29:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577414 AND AD_Language='nl_NL'
;

-- 2021-09-09T16:29:50.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577414,'nl_NL') 
;

