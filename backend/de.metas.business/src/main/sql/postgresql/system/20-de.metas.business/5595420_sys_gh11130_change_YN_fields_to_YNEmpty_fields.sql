

-- 2021-06-28T12:40:55.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='', Description='Wenn ja, dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht.',Updated=TO_TIMESTAMP('2021-06-28 14:40:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='de_CH'
;

-- 2021-06-28T12:40:55.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'de_CH')
;

-- 2021-06-28T12:41:07.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='', Description='Wenn ja, dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht.',Updated=TO_TIMESTAMP('2021-06-28 14:41:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='de_DE'
;

-- 2021-06-28T12:41:07.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'de_DE')
;

-- 2021-06-28T12:41:07.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579121,'de_DE')
;

-- 2021-06-28T12:41:07.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Partner hat eine Ust.-ID', Description='Wenn ja, dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht.', Help='' WHERE AD_Element_ID=579121
;

-- 2021-06-28T12:41:07.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Partner hat eine Ust.-ID', Description='Wenn ja, dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht.', Help='' WHERE AD_Element_ID=579121 AND IsCentrallyMaintained='Y'
;

-- 2021-06-28T12:41:07.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Partner hat eine Ust.-ID', Description='Wenn ja, dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579121) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579121)
;

-- 2021-06-28T12:41:07.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Partner hat eine Ust.-ID', Description='Wenn ja, dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 579121
;

-- 2021-06-28T12:41:07.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Partner hat eine Ust.-ID', Description='Wenn ja, dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht.', Help='' WHERE AD_Element_ID = 579121
;

-- 2021-06-28T12:41:07.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Partner hat eine Ust.-ID', Description = 'Wenn ja, dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579121
;

-- 2021-06-28T12:42:02.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='If set to yes, then the respective business partner needs to have a VAT-ID in order for the tax record to match.',Updated=TO_TIMESTAMP('2021-06-28 14:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='en_US'
;

-- 2021-06-28T12:42:02.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'en_US')
;

-- 2021-06-28T12:46:50.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If set to yes, then the respective business partner needs to have a small business tax exemption in order for the tax record to match.',Updated=TO_TIMESTAMP('2021-06-28 14:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579210 AND AD_Language='en_US'
;

-- 2021-06-28T12:46:50.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579210,'en_US')
;

-- 2021-06-28T12:48:30.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-28 14:48:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579210 AND AD_Language='de_DE'
;

-- 2021-06-28T12:48:30.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579210,'de_DE')
;

-- 2021-06-28T12:48:30.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579210,'de_DE')
;

-- 2021-06-28T12:48:30.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSmallbusiness', Name='Kleinunernehmen', Description='Wenn angehakt, dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht.', Help=NULL WHERE AD_Element_ID=579210
;

-- 2021-06-28T12:48:30.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSmallbusiness', Name='Kleinunernehmen', Description='Wenn angehakt, dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht.', Help=NULL, AD_Element_ID=579210 WHERE UPPER(ColumnName)='ISSMALLBUSINESS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-28T12:48:30.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSmallbusiness', Name='Kleinunernehmen', Description='Wenn angehakt, dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht.', Help=NULL WHERE AD_Element_ID=579210 AND IsCentrallyMaintained='Y'
;

-- 2021-06-28T12:48:30.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kleinunernehmen', Description='Wenn angehakt, dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579210) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579210)
;

-- 2021-06-28T12:48:30.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kleinunernehmen', Description='Wenn angehakt, dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579210
;

-- 2021-06-28T12:48:30.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kleinunernehmen', Description='Wenn angehakt, dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht.', Help=NULL WHERE AD_Element_ID = 579210
;

-- 2021-06-28T12:48:30.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kleinunernehmen', Description = 'Wenn angehakt, dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579210
;

-- 2021-06-28T12:48:41.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht.',Updated=TO_TIMESTAMP('2021-06-28 14:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579210 AND AD_Language='de_CH'
;

-- 2021-06-28T12:48:41.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579210,'de_CH')
;

-- 2021-06-28T12:48:47.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-28 14:48:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579210 AND AD_Language='de_CH'
;

-- 2021-06-28T12:48:47.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579210,'de_CH')
;

-- 2021-06-28T12:49:59.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540528,Updated=TO_TIMESTAMP('2021-06-28 14:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573975
;

-- 2021-06-28T12:50:02.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_tax','IsSmallbusiness','CHAR(1)',null,'N')
;

-- 2021-06-28T12:51:00.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine Befreiung vorliegen.',Updated=TO_TIMESTAMP('2021-06-28 14:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579210 AND AD_Language='de_CH'
;

-- 2021-06-28T12:51:00.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579210,'de_CH')
;

-- 2021-06-28T12:51:07.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine Befreiung vorliegen.',Updated=TO_TIMESTAMP('2021-06-28 14:51:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579210 AND AD_Language='de_DE'
;

-- 2021-06-28T12:51:07.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579210,'de_DE')
;

-- 2021-06-28T12:51:07.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579210,'de_DE')
;

-- 2021-06-28T12:51:07.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSmallbusiness', Name='Kleinunernehmen', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine Befreiung vorliegen.', Help=NULL WHERE AD_Element_ID=579210
;

-- 2021-06-28T12:51:07.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSmallbusiness', Name='Kleinunernehmen', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine Befreiung vorliegen.', Help=NULL, AD_Element_ID=579210 WHERE UPPER(ColumnName)='ISSMALLBUSINESS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-28T12:51:07.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSmallbusiness', Name='Kleinunernehmen', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine Befreiung vorliegen.', Help=NULL WHERE AD_Element_ID=579210 AND IsCentrallyMaintained='Y'
;

-- 2021-06-28T12:51:07.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kleinunernehmen', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine Befreiung vorliegen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579210) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579210)
;

-- 2021-06-28T12:51:07.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kleinunernehmen', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine Befreiung vorliegen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579210
;

-- 2021-06-28T12:51:07.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kleinunernehmen', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine Befreiung vorliegen.', Help=NULL WHERE AD_Element_ID = 579210
;

-- 2021-06-28T12:51:07.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kleinunernehmen', Description = 'Wenn "Ja", dann muss der betreffende Geschäftspartner eine Kleinunternehmer-Steuerbefreiung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine Befreiung vorliegen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579210
;

-- 2021-06-28T12:51:38.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If set to "yes", then the respective business partner needs to have a small business tax exemption in order for the tax record to match. If set to "No", then there may be no such exception.',Updated=TO_TIMESTAMP('2021-06-28 14:51:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579210 AND AD_Language='en_US'
;

-- 2021-06-28T12:51:38.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579210,'en_US')
;

-- 2021-06-28T12:52:13.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, DefaultValue='N', AD_Reference_Value_ID=540528,Updated=TO_TIMESTAMP('2021-06-28 14:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3695
;

-- 2021-06-28T12:52:15.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_tax','RequiresTaxCertificate','CHAR(1)',null,'N')
;

-- 2021-06-28T12:52:15.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--UPDATE C_Tax SET RequiresTaxCertificate='N' WHERE RequiresTaxCertificate IS NULL
--;

-- 2021-06-28T12:53:38.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine  Umsatzsteuer-ID vorliegen.',Updated=TO_TIMESTAMP('2021-06-28 14:53:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='de_DE'
;

-- 2021-06-28T12:53:38.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'de_DE')
;

-- 2021-06-28T12:53:38.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579121,'de_DE')
;

-- 2021-06-28T12:53:38.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Partner hat eine Ust.-ID', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine  Umsatzsteuer-ID vorliegen.', Help='' WHERE AD_Element_ID=579121
;

-- 2021-06-28T12:53:38.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Partner hat eine Ust.-ID', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine  Umsatzsteuer-ID vorliegen.', Help='' WHERE AD_Element_ID=579121 AND IsCentrallyMaintained='Y'
;

-- 2021-06-28T12:53:38.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Partner hat eine Ust.-ID', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine  Umsatzsteuer-ID vorliegen.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579121) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579121)
;

-- 2021-06-28T12:53:38.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Partner hat eine Ust.-ID', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine  Umsatzsteuer-ID vorliegen.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 579121
;

-- 2021-06-28T12:53:38.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Partner hat eine Ust.-ID', Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine  Umsatzsteuer-ID vorliegen.', Help='' WHERE AD_Element_ID = 579121
;

-- 2021-06-28T12:53:38.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Partner hat eine Ust.-ID', Description = 'Wenn "Ja", dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine  Umsatzsteuer-ID vorliegen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579121
;

-- 2021-06-28T12:53:47.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "Ja", dann muss der betreffende Geschäftspartner eine Umsatzsteuer-ID haben, damit der Steuersatz matcht. Wenn "Nein", dann darf keine  Umsatzsteuer-ID vorliegen.',Updated=TO_TIMESTAMP('2021-06-28 14:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='de_CH'
;

-- 2021-06-28T12:53:47.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'de_CH')
;

-- 2021-06-28T12:54:16.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='If set to yes, then the respective business partner needs to have a VAT-ID in order for the tax record to match. If set no no, then there may not be a VAT-ID.',Updated=TO_TIMESTAMP('2021-06-28 14:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='en_US'
;

-- 2021-06-28T12:54:16.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'en_US')
;

-- 2021-06-28T12:59:13.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, DefaultValue='', AD_Reference_Value_ID=540528,Updated=TO_TIMESTAMP('2021-06-28 14:59:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573974
;

-- 2021-06-28T12:59:15.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_tax','IsFiscalRepresentation','CHAR(1)',null,null)
;

-- 2021-06-28T13:00:41.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "Ja", dann muss die betreffende Organisation im Bestimmungsland eine Fiskalvertretung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf sie keine Fiskalvertretung haben.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-28 15:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579209 AND AD_Language='de_CH'
;

-- 2021-06-28T13:00:41.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579209,'de_CH')
;

-- 2021-06-28T13:00:50.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "Ja", dann muss die betreffende Organisation im Bestimmungsland eine Fiskalvertretung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf sie keine Fiskalvertretung haben.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-28 15:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579209 AND AD_Language='de_DE'
;

-- 2021-06-28T13:00:50.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579209,'de_DE')
;

-- 2021-06-28T13:00:50.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579209,'de_DE')
;

-- 2021-06-28T13:00:50.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsFiscalRepresentation', Name='Fiskalvertretung', Description='Wenn "Ja", dann muss die betreffende Organisation im Bestimmungsland eine Fiskalvertretung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf sie keine Fiskalvertretung haben.', Help=NULL WHERE AD_Element_ID=579209
;

-- 2021-06-28T13:00:50.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsFiscalRepresentation', Name='Fiskalvertretung', Description='Wenn "Ja", dann muss die betreffende Organisation im Bestimmungsland eine Fiskalvertretung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf sie keine Fiskalvertretung haben.', Help=NULL, AD_Element_ID=579209 WHERE UPPER(ColumnName)='ISFISCALREPRESENTATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-28T13:00:50.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsFiscalRepresentation', Name='Fiskalvertretung', Description='Wenn "Ja", dann muss die betreffende Organisation im Bestimmungsland eine Fiskalvertretung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf sie keine Fiskalvertretung haben.', Help=NULL WHERE AD_Element_ID=579209 AND IsCentrallyMaintained='Y'
;

-- 2021-06-28T13:00:50.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fiskalvertretung', Description='Wenn "Ja", dann muss die betreffende Organisation im Bestimmungsland eine Fiskalvertretung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf sie keine Fiskalvertretung haben.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579209) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579209)
;

-- 2021-06-28T13:00:50.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Fiskalvertretung', Description='Wenn "Ja", dann muss die betreffende Organisation im Bestimmungsland eine Fiskalvertretung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf sie keine Fiskalvertretung haben.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579209
;

-- 2021-06-28T13:00:50.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Fiskalvertretung', Description='Wenn "Ja", dann muss die betreffende Organisation im Bestimmungsland eine Fiskalvertretung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf sie keine Fiskalvertretung haben.', Help=NULL WHERE AD_Element_ID = 579209
;

-- 2021-06-28T13:00:50.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Fiskalvertretung', Description = 'Wenn "Ja", dann muss die betreffende Organisation im Bestimmungsland eine Fiskalvertretung haben, damit der Steuersatz matcht. Wenn "Nein", dann darf sie keine Fiskalvertretung haben.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579209
;

-- 2021-06-28T13:01:55.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='If set to yes, then the respective org needs to have a fiscal representation in the destination country in order for the tax record to match. If set no no, then there may not be a fiscal represenation.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-28 15:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579209 AND AD_Language='en_US'
;

-- 2021-06-28T13:01:55.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579209,'en_US')
;

-- 2021-06-28T13:02:35.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2021-06-28 15:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3695
;

-- 2021-06-28T13:02:35.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_tax','RequiresTaxCertificate','CHAR(1)',null,null)
;

-- 2021-06-28T13:02:57.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_tax','IsFiscalRepresentation','CHAR(1)',null,null)
;

-- 2021-06-28T13:59:49.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-06-28 15:59:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3695
;

-- 2021-06-28T13:59:52.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_tax','RequiresTaxCertificate','CHAR(1)',null,null)
;

-- 2021-06-28T13:59:53.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_tax','RequiresTaxCertificate',null,'NULL',null)
;


