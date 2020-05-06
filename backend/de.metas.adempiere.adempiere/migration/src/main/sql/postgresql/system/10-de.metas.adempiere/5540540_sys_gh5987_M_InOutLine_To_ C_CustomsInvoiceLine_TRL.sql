










-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540833,Updated=TO_TIMESTAMP('2020-01-07 11:40:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541451
;

-- 2020-01-07T09:40:48.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferung-Zollrechnung Zuordnung', PrintName='Lieferung-Zollrechnung Zuordnung',Updated=TO_TIMESTAMP('2020-01-07 11:40:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577450 AND AD_Language='de_CH'
;

-- 2020-01-07T09:40:48.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577450,'de_CH') 
;

-- 2020-01-07T09:40:54.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferung-Zollrechnung Zuordnung', PrintName='Lieferung-Zollrechnung Zuordnung',Updated=TO_TIMESTAMP('2020-01-07 11:40:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577450 AND AD_Language='de_DE'
;

-- 2020-01-07T09:40:54.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577450,'de_DE') 
;

-- 2020-01-07T09:40:54.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577450,'de_DE') 
;

-- 2020-01-07T09:40:54.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_InOutLine_To_C_Customs_Invoice_Line_ID', Name='Lieferung-Zollrechnung Zuordnung', Description=NULL, Help=NULL WHERE AD_Element_ID=577450
;

-- 2020-01-07T09:40:54.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_InOutLine_To_C_Customs_Invoice_Line_ID', Name='Lieferung-Zollrechnung Zuordnung', Description=NULL, Help=NULL, AD_Element_ID=577450 WHERE UPPER(ColumnName)='M_INOUTLINE_TO_C_CUSTOMS_INVOICE_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-07T09:40:54.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_InOutLine_To_C_Customs_Invoice_Line_ID', Name='Lieferung-Zollrechnung Zuordnung', Description=NULL, Help=NULL WHERE AD_Element_ID=577450 AND IsCentrallyMaintained='Y'
;

-- 2020-01-07T09:40:54.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferung-Zollrechnung Zuordnung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577450) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577450)
;

-- 2020-01-07T09:40:54.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferung-Zollrechnung Zuordnung', Name='Lieferung-Zollrechnung Zuordnung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577450)
;

-- 2020-01-07T09:40:54.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferung-Zollrechnung Zuordnung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577450
;

-- 2020-01-07T09:40:54.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferung-Zollrechnung Zuordnung', Description=NULL, Help=NULL WHERE AD_Element_ID = 577450
;

-- 2020-01-07T09:40:54.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferung-Zollrechnung Zuordnung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577450
;

-- 2020-01-07T09:41:13.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipment Line to Customs Invoice Line', PrintName='Shipment Line to Customs Invoice Line',Updated=TO_TIMESTAMP('2020-01-07 11:41:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577450 AND AD_Language='en_US'
;

-- 2020-01-07T09:41:13.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577450,'en_US') 
;

