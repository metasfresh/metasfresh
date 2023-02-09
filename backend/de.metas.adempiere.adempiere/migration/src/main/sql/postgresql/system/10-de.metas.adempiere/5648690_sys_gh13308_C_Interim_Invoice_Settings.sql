DROP TABLE IF EXISTS C_Prefinancing_Settings CASCADE;





-- 2022-08-01T12:39:24.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TableName='C_Interim_Invoice_Settings',Updated=TO_TIMESTAMP('2022-08-01 15:39:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542188
;

-- 2022-08-01T12:39:24.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='C_Interim_Invoice_Settings',Updated=TO_TIMESTAMP('2022-08-01 15:39:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555977
;

-- 2022-08-01T12:39:24.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE C_Prefinancing_Settings_SEQ RENAME TO C_Interim_Invoice_Settings_SEQ
;

-- 2022-08-01T12:39:57.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_Interim_Invoice_Settings_ID',Updated=TO_TIMESTAMP('2022-08-01 15:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581151
;

-- 2022-08-01T12:39:57.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Interim_Invoice_Settings_ID', Name='Einstellungen für Vorfinanzierungen', Description=NULL, Help=NULL WHERE AD_Element_ID=581151
;

-- 2022-08-01T12:39:57.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Interim_Invoice_Settings_ID', Name='Einstellungen für Vorfinanzierungen', Description=NULL, Help=NULL, AD_Element_ID=581151 WHERE UPPER(ColumnName)='C_INTERIM_INVOICE_SETTINGS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-01T12:39:57.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Interim_Invoice_Settings_ID', Name='Einstellungen für Vorfinanzierungen', Description=NULL, Help=NULL WHERE AD_Element_ID=581151 AND IsCentrallyMaintained='Y'
;

-- 2022-08-01T12:42:31.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Interim Invoice Settings', PrintName='Interim Invoice Settings',Updated=TO_TIMESTAMP('2022-08-01 15:42:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581151 AND AD_Language='en_US'
;

-- 2022-08-01T12:42:31.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581151,'en_US') 
;

-- 2022-08-01T12:42:44.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Prepayment Settings', PrintName='Prepayment Settings',Updated=TO_TIMESTAMP('2022-08-01 15:42:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581151 AND AD_Language='nl_NL'
;

-- 2022-08-01T12:42:44.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581151,'nl_NL') 
;

-- 2022-08-01T12:43:00.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Interim Invoice Settings', PrintName='Interim Invoice Settings',Updated=TO_TIMESTAMP('2022-08-01 15:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581151 AND AD_Language='fr_CH'
;

-- 2022-08-01T12:43:00.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581151,'fr_CH') 
;

-- 2022-08-01T12:43:05.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Interim Invoice Settings', PrintName='Interim Invoice Settings',Updated=TO_TIMESTAMP('2022-08-01 15:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581151 AND AD_Language='nl_NL'
;

-- 2022-08-01T12:43:05.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581151,'nl_NL') 
;

-- 2022-08-01T12:43:21.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Interim Invoice Settings',Updated=TO_TIMESTAMP('2022-08-01 15:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542188
;

-- 2022-08-01T12:43:39.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Interim Invoice Settings',Updated=TO_TIMESTAMP('2022-08-01 15:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542188
;

-- 2022-08-01T12:43:45.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Interim Invoice Settings',Updated=TO_TIMESTAMP('2022-08-01 15:43:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542188
;

-- 2022-08-01T12:43:49.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Interim Invoice Settings',Updated=TO_TIMESTAMP('2022-08-01 15:43:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=542188
;

-- 2022-08-01T12:44:31.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_Interim_Invoice_Settings (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Harvesting_Calendar_ID NUMERIC(10) NOT NULL, C_Interim_Invoice_Settings_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_Witholding_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CHarvestingCalendar_CInterimInvoiceSettings FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Interim_Invoice_Settings_Key PRIMARY KEY (C_Interim_Invoice_Settings_ID), CONSTRAINT MProductWitholding_CInterimInvoiceSettings FOREIGN KEY (M_Product_Witholding_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;



-- 2022-08-01T12:58:11.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='C_Interim_Invoice_Settings',Updated=TO_TIMESTAMP('2022-08-01 15:58:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541562
;




