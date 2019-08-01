
-- 2019-07-26T10:57:03.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyToInvoiceInPriceUOM_Nominal',Updated=TO_TIMESTAMP('2019-07-26 12:57:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529
;

-- 2019-07-26T10:57:03.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Zu berechn. Menge In Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-26T10:57:03.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Zu berechn. Menge In Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_NOMINAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-26T10:57:03.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Zu berechn. Menge In Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-26T10:58:48.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', IsTranslated='Y', Name='Zu berechn. Nominalmenge In Preiseinheit', PrintName='Zu berechn. Nominalmenge In Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 12:58:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_CH'
;

-- 2019-07-26T10:58:48.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_CH') 
;

-- 2019-07-26T10:59:17.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zu berechn. Nominalmenge in Preiseinheit', PrintName='Zu berechn. Nominalmenge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 12:59:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_CH'
;

-- 2019-07-26T10:59:17.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_CH') 
;

-- 2019-07-26T10:59:55.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zu ber. Nominalmenge in Preiseinheit', PrintName='Zu ber. Nominalmenge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 12:59:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_CH'
;

-- 2019-07-26T10:59:55.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_CH') 
;

-- 2019-07-26T11:00:10.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zu ber. Nominalmenge in Preiseinheit', PrintName='Zu ber. Nominalmenge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 13:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_DE'
;

-- 2019-07-26T11:00:10.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_DE') 
;

-- 2019-07-26T11:00:10.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542529,'de_DE') 
;

-- 2019-07-26T11:00:10.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Zu ber. Nominalmenge in Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-26T11:00:10.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Zu ber. Nominalmenge in Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_NOMINAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-26T11:00:10.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Zu ber. Nominalmenge in Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-26T11:00:10.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu ber. Nominalmenge in Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542529)
;

-- 2019-07-26T11:00:10.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zu ber. Nominalmenge in Preiseinheit', Name='Zu ber. Nominalmenge in Preiseinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542529)
;

-- 2019-07-26T11:00:10.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu ber. Nominalmenge in Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-26T11:00:10.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu ber. Nominalmenge in Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-26T11:00:10.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu ber. Nominalmenge in Preiseinheit', Description = 'Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-26T11:00:30.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 13:00:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_DE'
;

-- 2019-07-26T11:00:30.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_DE') 
;

-- 2019-07-26T11:00:30.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542529,'de_DE') 
;

-- 2019-07-26T11:00:30.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Zu ber. Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-26T11:00:30.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Zu ber. Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_NOMINAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-26T11:00:30.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Zu ber. Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-26T11:00:30.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu ber. Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542529)
;

-- 2019-07-26T11:00:30.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu ber. Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-26T11:00:30.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu ber. Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-26T11:00:30.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu ber. Nominalmenge in Preiseinheit', Description = 'Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-26T11:01:18.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nominal invoicable quantity in price-UOM', PrintName='Nominal invoicable quantity in price-UOM',Updated=TO_TIMESTAMP('2019-07-26 13:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='en_US'
;

-- 2019-07-26T11:01:18.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'en_US') 
;

-- 2019-07-26T11:01:44.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Nominal invoicable qty in price-UOM', PrintName='Nominal invoicable qty in price-UOM',Updated=TO_TIMESTAMP('2019-07-26 13:01:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='en_US'
;

-- 2019-07-26T11:01:44.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'en_US') 
;

-- we leave this physical column as it is; some migration scripts by the line it will once again be used
--ALTER TABLE C_Invoice_Candidate RENAME COLUMN QtyToInvoiceInPriceUOM TO QtyToInvoiceInPriceUOM_Nominal;
