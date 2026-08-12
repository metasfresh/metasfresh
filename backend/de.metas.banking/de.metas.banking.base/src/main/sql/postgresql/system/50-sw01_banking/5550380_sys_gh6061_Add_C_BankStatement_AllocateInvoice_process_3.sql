-- 2020-01-23T08:34:19.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 1', PrintName='Rechnung 1',Updated=TO_TIMESTAMP('2020-01-23 10:34:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577482 AND AD_Language='de_CH'
;

-- 2020-01-23T08:34:19.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577482,'de_CH') 
;

-- 2020-01-23T08:34:24.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 1', PrintName='Rechnung 1',Updated=TO_TIMESTAMP('2020-01-23 10:34:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577482 AND AD_Language='de_DE'
;

-- 2020-01-23T08:34:24.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577482,'de_DE') 
;

-- 2020-01-23T08:34:24.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577482,'de_DE') 
;

-- 2020-01-23T08:34:24.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_1_ID', Name='Rechnung 1', Description=NULL, Help=NULL WHERE AD_Element_ID=577482
;

-- 2020-01-23T08:34:24.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_1_ID', Name='Rechnung 1', Description=NULL, Help=NULL, AD_Element_ID=577482 WHERE UPPER(ColumnName)='C_INVOICE_1_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-23T08:34:24.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_1_ID', Name='Rechnung 1', Description=NULL, Help=NULL WHERE AD_Element_ID=577482 AND IsCentrallyMaintained='Y'
;

-- 2020-01-23T08:34:24.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnung 1', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577482) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577482)
;

-- 2020-01-23T08:34:24.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung 1', Name='Rechnung 1' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577482)
;

-- 2020-01-23T08:34:24.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnung 1', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577482
;

-- 2020-01-23T08:34:24.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnung 1', Description=NULL, Help=NULL WHERE AD_Element_ID = 577482
;

-- 2020-01-23T08:34:24.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnung 1', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577482
;

-- 2020-01-23T08:34:26.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-23 10:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577482 AND AD_Language='en_US'
;

-- 2020-01-23T08:34:26.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577482,'en_US') 
;

-- 2020-01-23T08:34:41.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 2', PrintName='Rechnung 2',Updated=TO_TIMESTAMP('2020-01-23 10:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577483 AND AD_Language='de_CH'
;

-- 2020-01-23T08:34:41.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577483,'de_CH') 
;

-- 2020-01-23T08:34:46.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 2', PrintName='Rechnung 2',Updated=TO_TIMESTAMP('2020-01-23 10:34:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577483 AND AD_Language='de_DE'
;

-- 2020-01-23T08:34:46.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577483,'de_DE') 
;

-- 2020-01-23T08:34:46.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577483,'de_DE') 
;

-- 2020-01-23T08:34:46.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_2_ID', Name='Rechnung 2', Description=NULL, Help=NULL WHERE AD_Element_ID=577483
;

-- 2020-01-23T08:34:46.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_2_ID', Name='Rechnung 2', Description=NULL, Help=NULL, AD_Element_ID=577483 WHERE UPPER(ColumnName)='C_INVOICE_2_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-23T08:34:46.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_2_ID', Name='Rechnung 2', Description=NULL, Help=NULL WHERE AD_Element_ID=577483 AND IsCentrallyMaintained='Y'
;

-- 2020-01-23T08:34:46.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnung 2', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577483) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577483)
;

-- 2020-01-23T08:34:46.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung 2', Name='Rechnung 2' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577483)
;

-- 2020-01-23T08:34:46.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnung 2', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577483
;

-- 2020-01-23T08:34:46.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnung 2', Description=NULL, Help=NULL WHERE AD_Element_ID = 577483
;

-- 2020-01-23T08:34:46.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnung 2', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577483
;

-- 2020-01-23T08:34:47.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-23 10:34:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577483 AND AD_Language='en_US'
;

-- 2020-01-23T08:34:47.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577483,'en_US') 
;

-- 2020-01-23T08:34:58.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 3', PrintName='Rechnung 3',Updated=TO_TIMESTAMP('2020-01-23 10:34:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577484 AND AD_Language='de_CH'
;

-- 2020-01-23T08:34:58.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577484,'de_CH') 
;

-- 2020-01-23T08:35:03.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 3', PrintName='Rechnung 3',Updated=TO_TIMESTAMP('2020-01-23 10:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577484 AND AD_Language='de_DE'
;

-- 2020-01-23T08:35:03.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577484,'de_DE') 
;

-- 2020-01-23T08:35:03.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577484,'de_DE') 
;

-- 2020-01-23T08:35:03.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_3_ID', Name='Rechnung 3', Description=NULL, Help=NULL WHERE AD_Element_ID=577484
;

-- 2020-01-23T08:35:03.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_3_ID', Name='Rechnung 3', Description=NULL, Help=NULL, AD_Element_ID=577484 WHERE UPPER(ColumnName)='C_INVOICE_3_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-23T08:35:03.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_3_ID', Name='Rechnung 3', Description=NULL, Help=NULL WHERE AD_Element_ID=577484 AND IsCentrallyMaintained='Y'
;

-- 2020-01-23T08:35:03.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnung 3', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577484) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577484)
;

-- 2020-01-23T08:35:03.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung 3', Name='Rechnung 3' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577484)
;

-- 2020-01-23T08:35:03.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnung 3', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577484
;

-- 2020-01-23T08:35:03.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnung 3', Description=NULL, Help=NULL WHERE AD_Element_ID = 577484
;

-- 2020-01-23T08:35:03.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnung 3', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577484
;

-- 2020-01-23T08:35:05.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-23 10:35:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577484 AND AD_Language='en_US'
;

-- 2020-01-23T08:35:05.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577484,'en_US') 
;

-- 2020-01-23T08:35:17.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 4', PrintName='Rechnung 4',Updated=TO_TIMESTAMP('2020-01-23 10:35:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577485 AND AD_Language='de_CH'
;

-- 2020-01-23T08:35:17.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577485,'de_CH') 
;

-- 2020-01-23T08:35:21.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 4', PrintName='Rechnung 4',Updated=TO_TIMESTAMP('2020-01-23 10:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577485 AND AD_Language='de_DE'
;

-- 2020-01-23T08:35:21.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577485,'de_DE') 
;

-- 2020-01-23T08:35:21.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577485,'de_DE') 
;

-- 2020-01-23T08:35:21.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_4_ID', Name='Rechnung 4', Description=NULL, Help=NULL WHERE AD_Element_ID=577485
;

-- 2020-01-23T08:35:21.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_4_ID', Name='Rechnung 4', Description=NULL, Help=NULL, AD_Element_ID=577485 WHERE UPPER(ColumnName)='C_INVOICE_4_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-23T08:35:21.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_4_ID', Name='Rechnung 4', Description=NULL, Help=NULL WHERE AD_Element_ID=577485 AND IsCentrallyMaintained='Y'
;

-- 2020-01-23T08:35:21.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnung 4', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577485) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577485)
;

-- 2020-01-23T08:35:21.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung 4', Name='Rechnung 4' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577485)
;

-- 2020-01-23T08:35:21.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnung 4', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577485
;

-- 2020-01-23T08:35:21.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnung 4', Description=NULL, Help=NULL WHERE AD_Element_ID = 577485
;

-- 2020-01-23T08:35:21.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnung 4', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577485
;

-- 2020-01-23T08:35:25.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-23 10:35:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577485 AND AD_Language='en_US'
;

-- 2020-01-23T08:35:25.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577485,'en_US') 
;

-- 2020-01-23T08:35:40.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 5', PrintName='Rechnung 5',Updated=TO_TIMESTAMP('2020-01-23 10:35:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577486 AND AD_Language='de_CH'
;

-- 2020-01-23T08:35:40.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577486,'de_CH') 
;

-- 2020-01-23T08:35:44.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung 5', PrintName='Rechnung 5',Updated=TO_TIMESTAMP('2020-01-23 10:35:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577486 AND AD_Language='de_DE'
;

-- 2020-01-23T08:35:44.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577486,'de_DE') 
;

-- 2020-01-23T08:35:44.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577486,'de_DE') 
;

-- 2020-01-23T08:35:44.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_5_ID', Name='Rechnung 5', Description=NULL, Help=NULL WHERE AD_Element_ID=577486
;

-- 2020-01-23T08:35:44.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_5_ID', Name='Rechnung 5', Description=NULL, Help=NULL, AD_Element_ID=577486 WHERE UPPER(ColumnName)='C_INVOICE_5_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-23T08:35:44.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_5_ID', Name='Rechnung 5', Description=NULL, Help=NULL WHERE AD_Element_ID=577486 AND IsCentrallyMaintained='Y'
;

-- 2020-01-23T08:35:44.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnung 5', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577486) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577486)
;

-- 2020-01-23T08:35:44.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung 5', Name='Rechnung 5' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577486)
;

-- 2020-01-23T08:35:44.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnung 5', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577486
;

-- 2020-01-23T08:35:44.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnung 5', Description=NULL, Help=NULL WHERE AD_Element_ID = 577486
;

-- 2020-01-23T08:35:44.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnung 5', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577486
;

-- 2020-01-23T08:35:46.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-23 10:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577486 AND AD_Language='en_US'
;

-- 2020-01-23T08:35:46.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577486,'en_US') 
;

-- 2020-01-23T09:06:15.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Rechnungen zuordnen',Updated=TO_TIMESTAMP('2020-01-23 11:06:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584643
;

-- 2020-01-23T09:06:21.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Rechnungen zuordnen',Updated=TO_TIMESTAMP('2020-01-23 11:06:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584643
;

-- 2020-01-23T09:06:31.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Allocate Invoices',Updated=TO_TIMESTAMP('2020-01-23 11:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584643
;

