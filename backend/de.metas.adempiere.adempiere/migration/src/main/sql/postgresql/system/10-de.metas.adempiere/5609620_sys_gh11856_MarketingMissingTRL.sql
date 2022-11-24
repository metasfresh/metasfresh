-- MKTG_ContactPerson TRL
-- 2021-10-15T09:13:17.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Marketing Contact', PrintName='Marketing Contact',Updated=TO_TIMESTAMP('2021-10-15 11:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544035 AND AD_Language='en_US'
;

-- 2021-10-15T09:13:17.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544035,'en_US')
;

-- 2021-10-15T09:13:53.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ansprechpartner Marketing', PrintName='Ansprechpartner Marketing',Updated=TO_TIMESTAMP('2021-10-15 11:13:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544035 AND AD_Language='nl_NL'
;

-- 2021-10-15T09:13:53.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544035,'nl_NL')
;

-- 2021-10-15T09:14:01.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ansprechpartner Marketing', PrintName='Ansprechpartner Marketing',Updated=TO_TIMESTAMP('2021-10-15 11:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544035 AND AD_Language='de_CH'
;

-- 2021-10-15T09:14:01.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544035,'de_CH')
;

-- 2021-10-15T09:14:17.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ansprechpartner Marketing', PrintName='Ansprechpartner Marketing',Updated=TO_TIMESTAMP('2021-10-15 11:14:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544035 AND AD_Language='de_DE'
;

-- 2021-10-15T09:14:17.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544035,'de_DE')
;

-- 2021-10-15T09:14:17.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(544035,'de_DE')
;

-- 2021-10-15T09:14:17.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MKTG_ContactPerson_ID', Name='Ansprechpartner Marketing', Description=NULL, Help=NULL WHERE AD_Element_ID=544035
;

-- 2021-10-15T09:14:17.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MKTG_ContactPerson_ID', Name='Ansprechpartner Marketing', Description=NULL, Help=NULL, AD_Element_ID=544035 WHERE UPPER(ColumnName)='MKTG_CONTACTPERSON_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-15T09:14:17.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MKTG_ContactPerson_ID', Name='Ansprechpartner Marketing', Description=NULL, Help=NULL WHERE AD_Element_ID=544035 AND IsCentrallyMaintained='Y'
;

-- 2021-10-15T09:14:17.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ansprechpartner Marketing', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544035) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544035)
;

-- 2021-10-15T09:14:17.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ansprechpartner Marketing', Name='Ansprechpartner Marketing' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544035)
;

-- 2021-10-15T09:14:17.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ansprechpartner Marketing', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 544035
;

-- 2021-10-15T09:14:17.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ansprechpartner Marketing', Description=NULL, Help=NULL WHERE AD_Element_ID = 544035
;

-- 2021-10-15T09:14:17.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ansprechpartner Marketing', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544035
;

-- Marketing Platform Gateway ID TRL
-- 2021-10-15T09:17:19.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Marketing Plattform Gateway-ID', PrintName='Marketing Plattform Gateway-ID',Updated=TO_TIMESTAMP('2021-10-15 11:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544040 AND AD_Language='de_CH'
;

-- 2021-10-15T09:17:19.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544040,'de_CH')
;

-- 2021-10-15T09:17:31.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Marketing-Plattform Gateway-ID', PrintName='Marketing-Plattform Gateway-ID',Updated=TO_TIMESTAMP('2021-10-15 11:17:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544040 AND AD_Language='de_CH'
;

-- 2021-10-15T09:17:31.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544040,'de_CH')
;

-- 2021-10-15T09:17:41.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Marketing-Plattform Gateway-ID', PrintName='Marketing-Plattform Gateway-ID',Updated=TO_TIMESTAMP('2021-10-15 11:17:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544040 AND AD_Language='nl_NL'
;

-- 2021-10-15T09:17:41.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544040,'nl_NL')
;

-- 2021-10-15T09:17:48.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Marketing-Plattform Gateway-ID', PrintName='Marketing-Plattform Gateway-ID',Updated=TO_TIMESTAMP('2021-10-15 11:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544040 AND AD_Language='de_DE'
;

-- 2021-10-15T09:17:48.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544040,'de_DE')
;

-- 2021-10-15T09:17:48.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(544040,'de_DE')
;

-- 2021-10-15T09:17:48.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MarketingPlatformGatewayId', Name='Marketing-Plattform Gateway-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544040
;

-- 2021-10-15T09:17:48.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MarketingPlatformGatewayId', Name='Marketing-Plattform Gateway-ID', Description=NULL, Help=NULL, AD_Element_ID=544040 WHERE UPPER(ColumnName)='MARKETINGPLATFORMGATEWAYID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-15T09:17:48.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MarketingPlatformGatewayId', Name='Marketing-Plattform Gateway-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544040 AND IsCentrallyMaintained='Y'
;

-- 2021-10-15T09:17:48.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Marketing-Plattform Gateway-ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544040) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544040)
;

-- 2021-10-15T09:17:48.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Marketing-Plattform Gateway-ID', Name='Marketing-Plattform Gateway-ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544040)
;

-- 2021-10-15T09:17:48.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Marketing-Plattform Gateway-ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 544040
;

-- 2021-10-15T09:17:48.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Marketing-Plattform Gateway-ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 544040
;

-- 2021-10-15T09:17:48.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Marketing-Plattform Gateway-ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544040
;

-- Fix TRL for Marketing Platform
-- 2021-10-15T09:21:05.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Marketing-Plattform', PrintName='Marketing-Plattform',Updated=TO_TIMESTAMP('2021-10-15 11:21:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574507 AND AD_Language='de_CH'
;

-- 2021-10-15T09:21:05.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574507,'de_CH')
;

-- 2021-10-15T09:21:13.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-15 11:21:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574507 AND AD_Language='en_US'
;

-- 2021-10-15T09:21:13.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574507,'en_US')
;

-- 2021-10-15T09:21:19.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Marketing-Plattform', PrintName='Marketing-Plattform',Updated=TO_TIMESTAMP('2021-10-15 11:21:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574507 AND AD_Language='nl_NL'
;

-- 2021-10-15T09:21:19.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574507,'nl_NL')
;

-- 2021-10-15T09:21:26.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Marketing-Plattform', PrintName='Marketing-Plattform',Updated=TO_TIMESTAMP('2021-10-15 11:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574507 AND AD_Language='de_DE'
;

-- 2021-10-15T09:21:26.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574507,'de_DE')
;

-- 2021-10-15T09:21:26.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574507,'de_DE')
;

-- 2021-10-15T09:21:26.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Marketing-Plattform', Description=NULL, Help=NULL WHERE AD_Element_ID=574507
;

-- 2021-10-15T09:21:26.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Marketing-Plattform', Description=NULL, Help=NULL WHERE AD_Element_ID=574507 AND IsCentrallyMaintained='Y'
;

-- 2021-10-15T09:21:26.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Marketing-Plattform', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574507) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574507)
;

-- 2021-10-15T09:21:26.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Marketing-Plattform', Name='Marketing-Plattform' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574507)
;

-- 2021-10-15T09:21:26.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Marketing-Plattform', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 574507
;

-- 2021-10-15T09:21:26.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Marketing-Plattform', Description=NULL, Help=NULL WHERE AD_Element_ID = 574507
;

-- 2021-10-15T09:21:26.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Marketing-Plattform', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574507
;

-- 2021-10-15T09:23:42.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Marketing-Plattform', PrintName='Marketing-Plattform',Updated=TO_TIMESTAMP('2021-10-15 11:23:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575738 AND AD_Language='de_CH'
;

-- 2021-10-15T09:23:42.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575738,'de_CH')
;

-- 2021-10-15T09:23:48.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-15 11:23:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575738 AND AD_Language='en_US'
;

-- 2021-10-15T09:23:48.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575738,'en_US')
;

-- 2021-10-15T09:23:54.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Marketing-Plattform', PrintName='Marketing-Plattform',Updated=TO_TIMESTAMP('2021-10-15 11:23:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575738 AND AD_Language='nl_NL'
;

-- 2021-10-15T09:23:54.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575738,'nl_NL')
;

-- 2021-10-15T09:23:59.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Marketing-Plattform', PrintName='Marketing-Plattform',Updated=TO_TIMESTAMP('2021-10-15 11:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575738 AND AD_Language='de_DE'
;

-- 2021-10-15T09:23:59.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575738,'de_DE')
;

-- 2021-10-15T09:23:59.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575738,'de_DE')
;

-- 2021-10-15T09:23:59.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Marketing-Plattform', Description=NULL, Help=NULL WHERE AD_Element_ID=575738
;

-- 2021-10-15T09:23:59.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Marketing-Plattform', Description=NULL, Help=NULL WHERE AD_Element_ID=575738 AND IsCentrallyMaintained='Y'
;

-- 2021-10-15T09:23:59.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Marketing-Plattform', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575738) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575738)
;

-- 2021-10-15T09:23:59.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Marketing-Plattform', Name='Marketing-Plattform' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575738)
;

-- 2021-10-15T09:23:59.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Marketing-Plattform', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575738
;

-- 2021-10-15T09:23:59.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Marketing-Plattform', Description=NULL, Help=NULL WHERE AD_Element_ID = 575738
;

-- 2021-10-15T09:23:59.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Marketing-Plattform', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575738
;