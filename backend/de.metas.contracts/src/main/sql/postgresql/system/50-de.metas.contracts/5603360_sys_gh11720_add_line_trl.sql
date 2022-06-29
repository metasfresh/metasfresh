-- 2021-09-03T12:43:49.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Line', PrintName='Line',Updated=TO_TIMESTAMP('2021-09-03 15:43:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579624 AND AD_Language='de_CH'
;

-- 2021-09-03T12:43:50.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579624,'de_CH') 
;

-- 2021-09-03T12:43:54.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Line', PrintName='Line',Updated=TO_TIMESTAMP('2021-09-03 15:43:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579624 AND AD_Language='de_DE'
;

-- 2021-09-03T12:43:54.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579624,'de_DE') 
;

-- 2021-09-03T12:43:54.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579624,'de_DE') 
;

-- 2021-09-03T12:43:54.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Customer_Trade_Margin_Line_ID', Name='Line', Description=NULL, Help=NULL WHERE AD_Element_ID=579624
;

-- 2021-09-03T12:43:54.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customer_Trade_Margin_Line_ID', Name='Line', Description=NULL, Help=NULL, AD_Element_ID=579624 WHERE UPPER(ColumnName)='C_CUSTOMER_TRADE_MARGIN_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-03T12:43:54.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customer_Trade_Margin_Line_ID', Name='Line', Description=NULL, Help=NULL WHERE AD_Element_ID=579624 AND IsCentrallyMaintained='Y'
;

-- 2021-09-03T12:43:54.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Line', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579624) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579624)
;

-- 2021-09-03T12:43:54.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Line', Name='Line' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579624)
;


-- 2021-09-03T12:43:54.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Line', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579624
;

-- 2021-09-03T12:43:54.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Line', Description=NULL, Help=NULL WHERE AD_Element_ID = 579624
;

-- 2021-09-03T12:43:54.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Line', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579624
;

-- 2021-09-03T12:43:57.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Line', PrintName='Line',Updated=TO_TIMESTAMP('2021-09-03 15:43:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579624 AND AD_Language='en_US'
;

-- 2021-09-03T12:43:57.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579624,'en_US') 
;

-- 2021-09-03T12:44:01.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Line', PrintName='Line',Updated=TO_TIMESTAMP('2021-09-03 15:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579624 AND AD_Language='nl_NL'
;

-- 2021-09-03T12:44:01.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579624,'nl_NL') 
;

