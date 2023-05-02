-- 2021-12-21T10:51:08.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Empfänger EMail JSON-Path', PrintName='Empfänger EMail JSON-Path',Updated=TO_TIMESTAMP('2021-12-21 12:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='de_CH'
;

-- 2021-12-21T10:51:08.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'de_CH')
;

-- 2021-12-21T10:51:11.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Empfänger EMail JSON-Path', PrintName='Empfänger EMail JSON-Path',Updated=TO_TIMESTAMP('2021-12-21 12:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='de_DE'
;

-- 2021-12-21T10:51:11.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'de_DE')
;

-- 2021-12-21T10:51:11.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580369,'de_DE')
;

-- 2021-12-21T10:51:11.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathEmail', Name='Empfänger EMail JSON-Path', Description='', Help=NULL WHERE AD_Element_ID=580369
;

-- 2021-12-21T10:51:11.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathEmail', Name='Empfänger EMail JSON-Path', Description='', Help=NULL, AD_Element_ID=580369 WHERE UPPER(ColumnName)='JSONPATHEMAIL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-21T10:51:11.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathEmail', Name='Empfänger EMail JSON-Path', Description='', Help=NULL WHERE AD_Element_ID=580369 AND IsCentrallyMaintained='Y'
;

-- 2021-12-21T10:51:11.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Empfänger EMail JSON-Path', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580369) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580369)
;

-- 2021-12-21T10:51:11.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Empfänger EMail JSON-Path', Name='Empfänger EMail JSON-Path' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580369)
;

-- 2021-12-21T10:51:11.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Empfänger EMail JSON-Path', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-21T10:51:11.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Empfänger EMail JSON-Path', Description='', Help=NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-21T10:51:11.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Empfänger EMail JSON-Path', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-21T10:51:14.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Empfänger EMail JSON-Path', PrintName='Empfänger EMail JSON-Path',Updated=TO_TIMESTAMP('2021-12-21 12:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='nl_NL'
;

-- 2021-12-21T10:51:14.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'nl_NL')
;

-- 2021-12-21T10:51:20.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Recipient EMail JSON Path', PrintName='Recipient EMail JSON Path',Updated=TO_TIMESTAMP('2021-12-21 12:51:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='en_US'
;

-- 2021-12-21T10:51:20.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'en_US')
;

-- 2021-12-21T10:51:37.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.',Updated=TO_TIMESTAMP('2021-12-21 12:51:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='de_CH'
;

-- 2021-12-21T10:51:37.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'de_CH')
;

-- 2021-12-21T10:51:39.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.',Updated=TO_TIMESTAMP('2021-12-21 12:51:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='de_DE'
;

-- 2021-12-21T10:51:39.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'de_DE')
;

-- 2021-12-21T10:51:39.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580369,'de_DE')
;

-- 2021-12-21T10:51:39.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathEmail', Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL WHERE AD_Element_ID=580369
;

-- 2021-12-21T10:51:39.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathEmail', Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL, AD_Element_ID=580369 WHERE UPPER(ColumnName)='JSONPATHEMAIL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-21T10:51:39.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathEmail', Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL WHERE AD_Element_ID=580369 AND IsCentrallyMaintained='Y'
;

-- 2021-12-21T10:51:39.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580369) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580369)
;

-- 2021-12-21T10:51:39.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-21T10:51:39.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-21T10:51:39.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Empfänger EMail JSON-Path', Description = 'JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-21T10:51:41.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt.<br>Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.',Updated=TO_TIMESTAMP('2021-12-21 12:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='nl_NL'
;

-- 2021-12-21T10:51:41.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'nl_NL')
;

-- 2021-12-21T10:51:47.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON path specifying where within a customised Shopware address the delivery recipient''s email address can be read, if set there.<br>This value is transferred to the address master data and to the order.',Updated=TO_TIMESTAMP('2021-12-21 12:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='en_US'
;

-- 2021-12-21T10:51:47.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'en_US')
;

-- 2021-12-21T10:53:58.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!',Updated=TO_TIMESTAMP('2021-12-21 12:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578990 AND AD_Language='de_CH'
;

-- 2021-12-21T10:53:58.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578990,'de_CH')
;

-- 2021-12-21T10:54:01.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!',Updated=TO_TIMESTAMP('2021-12-21 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578990 AND AD_Language='de_DE'
;

-- 2021-12-21T10:54:01.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578990,'de_DE')
;

-- 2021-12-21T10:54:01.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578990,'de_DE')
;

-- 2021-12-21T10:54:01.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathConstantBPartnerLocationID', Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE AD_Element_ID=578990
;

-- 2021-12-21T10:54:01.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathConstantBPartnerLocationID', Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL, AD_Element_ID=578990 WHERE UPPER(ColumnName)='JSONPATHCONSTANTBPARTNERLOCATIONID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-21T10:54:01.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathConstantBPartnerLocationID', Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE AD_Element_ID=578990 AND IsCentrallyMaintained='Y'
;

-- 2021-12-21T10:54:01.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578990) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578990)
;

-- 2021-12-21T10:54:01.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578990
;

-- 2021-12-21T10:54:01.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE AD_Element_ID = 578990
;

-- 2021-12-21T10:54:01.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Adress JSON-Path', Description = 'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578990
;

-- 2021-12-21T10:54:06.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Adresse die permanente Adress-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!',Updated=TO_TIMESTAMP('2021-12-21 12:54:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578990 AND AD_Language='nl_NL'
;

-- 2021-12-21T10:54:06.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578990,'nl_NL')
;

-- 2021-12-21T10:54:21.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Adress JSON-Path', PrintName='Adress JSON-Path',Updated=TO_TIMESTAMP('2021-12-21 12:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578990 AND AD_Language='nl_NL'
;

-- 2021-12-21T10:54:21.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578990,'nl_NL')
;

-- 2021-12-21T11:04:14.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.',Updated=TO_TIMESTAMP('2021-12-21 13:04:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='de_CH'
;

-- 2021-12-21T11:04:14.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'de_CH')
;

-- 2021-12-21T11:04:19.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.',Updated=TO_TIMESTAMP('2021-12-21 13:04:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='de_DE'
;

-- 2021-12-21T11:04:19.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'de_DE')
;

-- 2021-12-21T11:04:19.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580369,'de_DE')
;

-- 2021-12-21T11:04:19.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathEmail', Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL WHERE AD_Element_ID=580369
;

-- 2021-12-21T11:04:19.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathEmail', Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL, AD_Element_ID=580369 WHERE UPPER(ColumnName)='JSONPATHEMAIL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-21T11:04:19.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathEmail', Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL WHERE AD_Element_ID=580369 AND IsCentrallyMaintained='Y'
;

-- 2021-12-21T11:04:19.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580369) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580369)
;

-- 2021-12-21T11:04:19.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-21T11:04:19.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Empfänger EMail JSON-Path', Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', Help=NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-21T11:04:19.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Empfänger EMail JSON-Path', Description = 'JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-21T11:04:24.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON path specifying where within a customised Shopware address the delivery recipient''s email address can be read, if set there. This value is transferred to the address master data and to the order.',Updated=TO_TIMESTAMP('2021-12-21 13:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='en_US'
;

-- 2021-12-21T11:04:24.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'en_US')
;

-- 2021-12-21T11:04:28.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt, wo innerhalb einer kundenspezifisch angepassten Shopware-Adresse die EMailadresse des Lieferempfängers ausgelesen werden kann, sofern dort gesetzt. Dieser Wert wird in die Adress-Stammdaten und in den Auftrag übernommen.',Updated=TO_TIMESTAMP('2021-12-21 13:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='nl_NL'
;

-- 2021-12-21T11:04:28.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'nl_NL')
;