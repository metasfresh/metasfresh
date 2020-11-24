-- 2020-05-11T09:35:45.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dienstleister für Rechnungsbearbeitung', PrintName='Dienstleister für Rechnungsbearbeitung',Updated=TO_TIMESTAMP('2020-05-11 12:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577692 AND AD_Language='de_DE'
;

-- 2020-05-11T09:35:45.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577692,'de_DE') 
;

-- 2020-05-11T09:35:45.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577692,'de_DE') 
;

-- 2020-05-11T09:35:45.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoiceProcessingServiceCompany_ID', Name='Dienstleister für Rechnungsbearbeitung', Description=NULL, Help=NULL WHERE AD_Element_ID=577692
;

-- 2020-05-11T09:35:45.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceProcessingServiceCompany_ID', Name='Dienstleister für Rechnungsbearbeitung', Description=NULL, Help=NULL, AD_Element_ID=577692 WHERE UPPER(ColumnName)='INVOICEPROCESSINGSERVICECOMPANY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-11T09:35:45.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceProcessingServiceCompany_ID', Name='Dienstleister für Rechnungsbearbeitung', Description=NULL, Help=NULL WHERE AD_Element_ID=577692 AND IsCentrallyMaintained='Y'
;

-- 2020-05-11T09:35:45.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dienstleister für Rechnungsbearbeitung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577692) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577692)
;

-- 2020-05-11T09:35:45.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Dienstleister für Rechnungsbearbeitung', Name='Dienstleister für Rechnungsbearbeitung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577692)
;

-- 2020-05-11T09:35:45.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Dienstleister für Rechnungsbearbeitung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577692
;

-- 2020-05-11T09:35:45.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Dienstleister für Rechnungsbearbeitung', Description=NULL, Help=NULL WHERE AD_Element_ID = 577692
;

-- 2020-05-11T09:35:45.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Dienstleister für Rechnungsbearbeitung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577692
;

-- 2020-05-11T09:35:48.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-11 12:35:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577692 AND AD_Language='en_US'
;

-- 2020-05-11T09:35:48.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577692,'en_US') 
;

-- 2020-05-11T09:35:53.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dienstleister für Rechnungsbearbeitung', PrintName='Dienstleister für Rechnungsbearbeitung',Updated=TO_TIMESTAMP('2020-05-11 12:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577692 AND AD_Language='de_CH'
;

-- 2020-05-11T09:35:53.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577692,'de_CH') 
;

-- 2020-05-11T18:54:14.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Serviceunternehmen', PrintName='Serviceunternehmen',Updated=TO_TIMESTAMP('2020-05-11 21:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577693 AND AD_Language='de_CH'
;

-- 2020-05-11T18:54:14.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577693,'de_CH') 
;

-- 2020-05-11T18:54:19.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Serviceunternehmen', PrintName='Serviceunternehmen',Updated=TO_TIMESTAMP('2020-05-11 21:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577693 AND AD_Language='de_DE'
;

-- 2020-05-11T18:54:19.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577693,'de_DE') 
;

-- 2020-05-11T18:54:19.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577693,'de_DE') 
;

-- 2020-05-11T18:54:19.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceCompany_BPartner_ID', Name='Serviceunternehmen', Description=NULL, Help=NULL WHERE AD_Element_ID=577693
;

-- 2020-05-11T18:54:19.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceCompany_BPartner_ID', Name='Serviceunternehmen', Description=NULL, Help=NULL, AD_Element_ID=577693 WHERE UPPER(ColumnName)='SERVICECOMPANY_BPARTNER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-11T18:54:19.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceCompany_BPartner_ID', Name='Serviceunternehmen', Description=NULL, Help=NULL WHERE AD_Element_ID=577693 AND IsCentrallyMaintained='Y'
;

-- 2020-05-11T18:54:19.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serviceunternehmen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577693) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577693)
;

-- 2020-05-11T18:54:19.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Serviceunternehmen', Name='Serviceunternehmen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577693)
;

-- 2020-05-11T18:54:19.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Serviceunternehmen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577693
;

-- 2020-05-11T18:54:19.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Serviceunternehmen', Description=NULL, Help=NULL WHERE AD_Element_ID = 577693
;

-- 2020-05-11T18:54:19.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Serviceunternehmen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577693
;

-- 2020-05-11T18:54:21.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-11 21:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577693 AND AD_Language='en_US'
;

-- 2020-05-11T18:54:21.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577693,'en_US') 
;

-- 2020-05-11T18:54:48.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Belegart Servicerechnung', PrintName='Belegart Servicerechnung',Updated=TO_TIMESTAMP('2020-05-11 21:54:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577694 AND AD_Language='de_CH'
;

-- 2020-05-11T18:54:48.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577694,'de_CH') 
;

-- 2020-05-11T18:54:53.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Belegart Servicerechnung', PrintName='Belegart Servicerechnung',Updated=TO_TIMESTAMP('2020-05-11 21:54:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577694 AND AD_Language='de_DE'
;

-- 2020-05-11T18:54:53.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577694,'de_DE') 
;

-- 2020-05-11T18:54:53.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577694,'de_DE') 
;

-- 2020-05-11T18:54:53.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceInvoice_DocType_ID', Name='Belegart Servicerechnung', Description=NULL, Help=NULL WHERE AD_Element_ID=577694
;

-- 2020-05-11T18:54:53.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceInvoice_DocType_ID', Name='Belegart Servicerechnung', Description=NULL, Help=NULL, AD_Element_ID=577694 WHERE UPPER(ColumnName)='SERVICEINVOICE_DOCTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-11T18:54:53.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceInvoice_DocType_ID', Name='Belegart Servicerechnung', Description=NULL, Help=NULL WHERE AD_Element_ID=577694 AND IsCentrallyMaintained='Y'
;

-- 2020-05-11T18:54:53.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Belegart Servicerechnung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577694) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577694)
;

-- 2020-05-11T18:54:53.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Belegart Servicerechnung', Name='Belegart Servicerechnung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577694)
;

-- 2020-05-11T18:54:53.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Belegart Servicerechnung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577694
;

-- 2020-05-11T18:54:53.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Belegart Servicerechnung', Description=NULL, Help=NULL WHERE AD_Element_ID = 577694
;

-- 2020-05-11T18:54:53.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Belegart Servicerechnung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577694
;

-- 2020-05-11T18:54:55.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-11 21:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577694 AND AD_Language='en_US'
;

-- 2020-05-11T18:54:55.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577694,'en_US') 
;

-- 2020-05-11T18:55:21.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Servicegebühr Produkt', PrintName='Servicegebühr Produkt',Updated=TO_TIMESTAMP('2020-05-11 21:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577695 AND AD_Language='de_CH'
;

-- 2020-05-11T18:55:21.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577695,'de_CH') 
;

-- 2020-05-11T18:55:24.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Servicegebühr Produkt', PrintName='Servicegebühr Produkt',Updated=TO_TIMESTAMP('2020-05-11 21:55:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577695 AND AD_Language='de_DE'
;

-- 2020-05-11T18:55:24.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577695,'de_DE') 
;

-- 2020-05-11T18:55:24.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577695,'de_DE') 
;

-- 2020-05-11T18:55:24.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceFee_Product_ID', Name='Servicegebühr Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577695
;

-- 2020-05-11T18:55:24.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFee_Product_ID', Name='Servicegebühr Produkt', Description=NULL, Help=NULL, AD_Element_ID=577695 WHERE UPPER(ColumnName)='SERVICEFEE_PRODUCT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-11T18:55:24.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFee_Product_ID', Name='Servicegebühr Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577695 AND IsCentrallyMaintained='Y'
;

-- 2020-05-11T18:55:24.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Servicegebühr Produkt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577695) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577695)
;

-- 2020-05-11T18:55:24.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Servicegebühr Produkt', Name='Servicegebühr Produkt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577695)
;

-- 2020-05-11T18:55:24.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Servicegebühr Produkt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577695
;

-- 2020-05-11T18:55:24.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Servicegebühr Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID = 577695
;

-- 2020-05-11T18:55:24.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Servicegebühr Produkt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577695
;

-- 2020-05-11T18:55:27.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-11 21:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577695 AND AD_Language='en_US'
;

-- 2020-05-11T18:55:27.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577695,'en_US') 
;

-- 2020-05-11T18:56:13.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Prozentsatz der Rechnungssumme', PrintName='Prozentsatz der Rechnungssumme',Updated=TO_TIMESTAMP('2020-05-11 21:56:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577696 AND AD_Language='de_CH'
;

-- 2020-05-11T18:56:13.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577696,'de_CH') 
;

-- 2020-05-11T18:56:16.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Prozentsatz der Rechnungssumme', PrintName='Prozentsatz der Rechnungssumme',Updated=TO_TIMESTAMP('2020-05-11 21:56:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577696 AND AD_Language='de_DE'
;

-- 2020-05-11T18:56:16.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577696,'de_DE') 
;

-- 2020-05-11T18:56:16.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577696,'de_DE') 
;

-- 2020-05-11T18:56:16.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FeePercentageOfGrandTotal', Name='Prozentsatz der Rechnungssumme', Description=NULL, Help=NULL WHERE AD_Element_ID=577696
;

-- 2020-05-11T18:56:16.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FeePercentageOfGrandTotal', Name='Prozentsatz der Rechnungssumme', Description=NULL, Help=NULL, AD_Element_ID=577696 WHERE UPPER(ColumnName)='FEEPERCENTAGEOFGRANDTOTAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-11T18:56:16.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FeePercentageOfGrandTotal', Name='Prozentsatz der Rechnungssumme', Description=NULL, Help=NULL WHERE AD_Element_ID=577696 AND IsCentrallyMaintained='Y'
;

-- 2020-05-11T18:56:16.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Prozentsatz der Rechnungssumme', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577696) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577696)
;

-- 2020-05-11T18:56:16.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Prozentsatz der Rechnungssumme', Name='Prozentsatz der Rechnungssumme' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577696)
;

-- 2020-05-11T18:56:16.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Prozentsatz der Rechnungssumme', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577696
;

-- 2020-05-11T18:56:16.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Prozentsatz der Rechnungssumme', Description=NULL, Help=NULL WHERE AD_Element_ID = 577696
;

-- 2020-05-11T18:56:16.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Prozentsatz der Rechnungssumme', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577696
;

-- 2020-05-11T18:56:20.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-11 21:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577696 AND AD_Language='en_US'
;

-- 2020-05-11T18:56:20.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577696,'en_US') 
;

-- 2020-05-11T18:56:50.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungsserviceunternehmen', PrintName='Rechnungsserviceunternehmen',Updated=TO_TIMESTAMP('2020-05-11 21:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577692 AND AD_Language='de_CH'
;

-- 2020-05-11T18:56:50.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577692,'de_CH') 
;

-- 2020-05-11T18:56:54.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungsserviceunternehmen', PrintName='Rechnungsserviceunternehmen',Updated=TO_TIMESTAMP('2020-05-11 21:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577692 AND AD_Language='de_DE'
;

-- 2020-05-11T18:56:54.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577692,'de_DE') 
;

-- 2020-05-11T18:56:54.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577692,'de_DE') 
;

-- 2020-05-11T18:56:54.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoiceProcessingServiceCompany_ID', Name='Rechnungsserviceunternehmen', Description=NULL, Help=NULL WHERE AD_Element_ID=577692
;

-- 2020-05-11T18:56:54.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceProcessingServiceCompany_ID', Name='Rechnungsserviceunternehmen', Description=NULL, Help=NULL, AD_Element_ID=577692 WHERE UPPER(ColumnName)='INVOICEPROCESSINGSERVICECOMPANY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-11T18:56:54.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceProcessingServiceCompany_ID', Name='Rechnungsserviceunternehmen', Description=NULL, Help=NULL WHERE AD_Element_ID=577692 AND IsCentrallyMaintained='Y'
;

-- 2020-05-11T18:56:54.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungsserviceunternehmen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577692) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577692)
;

-- 2020-05-11T18:56:54.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungsserviceunternehmen', Name='Rechnungsserviceunternehmen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577692)
;

-- 2020-05-11T18:56:54.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungsserviceunternehmen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577692
;

-- 2020-05-11T18:56:54.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungsserviceunternehmen', Description=NULL, Help=NULL WHERE AD_Element_ID = 577692
;

-- 2020-05-11T18:56:54.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnungsserviceunternehmen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577692
;

-- 2020-05-11T18:57:19.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Rechnungsserviceunternehmen',Updated=TO_TIMESTAMP('2020-05-11 21:57:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541493
;

-- 2020-05-11T18:57:22.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-11 21:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541493
;

-- 2020-05-11T18:57:29.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Rechnungsserviceunternehmen',Updated=TO_TIMESTAMP('2020-05-11 21:57:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541493
;

-- 2020-05-11T18:58:21.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Zugewiesene Kunden',Updated=TO_TIMESTAMP('2020-05-11 21:58:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541494
;

-- 2020-05-11T18:58:25.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Zugewiesene Kunden',Updated=TO_TIMESTAMP('2020-05-11 21:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541494
;

-- 2020-05-11T18:58:51.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Assigned Customers',Updated=TO_TIMESTAMP('2020-05-11 21:58:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541494
;

-- 2020-05-11T18:59:05.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zugewiesene Kunden', PrintName='Zugewiesene Kunden',Updated=TO_TIMESTAMP('2020-05-11 21:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577697 AND AD_Language='de_CH'
;

-- 2020-05-11T18:59:05.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577697,'de_CH') 
;

-- 2020-05-11T18:59:08.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zugewiesene Kunden', PrintName='Zugewiesene Kunden',Updated=TO_TIMESTAMP('2020-05-11 21:59:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577697 AND AD_Language='de_DE'
;

-- 2020-05-11T18:59:08.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577697,'de_DE') 
;

-- 2020-05-11T18:59:08.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577697,'de_DE') 
;

-- 2020-05-11T18:59:08.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoiceProcessingServiceCompany_BPartnerAssignment_ID', Name='Zugewiesene Kunden', Description=NULL, Help=NULL WHERE AD_Element_ID=577697
;

-- 2020-05-11T18:59:08.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceProcessingServiceCompany_BPartnerAssignment_ID', Name='Zugewiesene Kunden', Description=NULL, Help=NULL, AD_Element_ID=577697 WHERE UPPER(ColumnName)='INVOICEPROCESSINGSERVICECOMPANY_BPARTNERASSIGNMENT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-11T18:59:08.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceProcessingServiceCompany_BPartnerAssignment_ID', Name='Zugewiesene Kunden', Description=NULL, Help=NULL WHERE AD_Element_ID=577697 AND IsCentrallyMaintained='Y'
;

-- 2020-05-11T18:59:08.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugewiesene Kunden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577697) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577697)
;

-- 2020-05-11T18:59:08.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugewiesene Kunden', Name='Zugewiesene Kunden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577697)
;

-- 2020-05-11T18:59:08.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zugewiesene Kunden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577697
;

-- 2020-05-11T18:59:08.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zugewiesene Kunden', Description=NULL, Help=NULL WHERE AD_Element_ID = 577697
;

-- 2020-05-11T18:59:08.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zugewiesene Kunden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577697
;

-- 2020-05-11T18:59:18.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Assigned Customers', PrintName='Assigned Customers',Updated=TO_TIMESTAMP('2020-05-11 21:59:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577697 AND AD_Language='en_US'
;

-- 2020-05-11T18:59:18.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577697,'en_US') 
;

-- 2020-05-11T19:02:16.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577711,0,'ServiceFeeAmt',TO_TIMESTAMP('2020-05-11 22:02:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Servicegebühr','Servicegebühr',TO_TIMESTAMP('2020-05-11 22:02:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-11T19:02:16.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577711 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-05-11T19:02:20.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-11 22:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577711 AND AD_Language='de_CH'
;

-- 2020-05-11T19:02:20.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577711,'de_CH') 
;

-- 2020-05-11T19:02:22.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-11 22:02:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577711 AND AD_Language='de_DE'
;

-- 2020-05-11T19:02:22.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577711,'de_DE') 
;

-- 2020-05-11T19:02:22.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577711,'de_DE') 
;

-- 2020-05-11T19:02:30.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sevice Fee', PrintName='Sevice Fee',Updated=TO_TIMESTAMP('2020-05-11 22:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577711 AND AD_Language='en_US'
;

-- 2020-05-11T19:02:30.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577711,'en_US') 
;

