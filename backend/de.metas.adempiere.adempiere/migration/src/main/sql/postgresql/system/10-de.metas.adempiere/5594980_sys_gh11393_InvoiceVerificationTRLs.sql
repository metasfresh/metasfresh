-- Steuer OK
-- 2021-06-25T12:41:44.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Steuer stimmt überein', PrintName='Steuer stimmt überein',Updated=TO_TIMESTAMP('2021-06-25 15:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='de_CH'
;

-- 2021-06-25T12:41:44.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'de_CH') 
;

-- 2021-06-25T12:41:51.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Steuer stimmt überein', PrintName='Steuer stimmt überein',Updated=TO_TIMESTAMP('2021-06-25 15:41:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='de_DE'
;

-- 2021-06-25T12:41:51.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'de_DE') 
;

-- 2021-06-25T12:41:51.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579247,'de_DE') 
;

-- 2021-06-25T12:41:51.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsTaxIdMatch', Name='Steuer stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579247
;

-- 2021-06-25T12:41:51.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTaxIdMatch', Name='Steuer stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL, AD_Element_ID=579247 WHERE UPPER(ColumnName)='ISTAXIDMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-25T12:41:51.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTaxIdMatch', Name='Steuer stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579247 AND IsCentrallyMaintained='Y'
;

-- 2021-06-25T12:41:51.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Steuer stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579247) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579247)
;

-- 2021-06-25T12:41:51.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Steuer stimmt überein', Name='Steuer stimmt überein' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579247)
;

-- 2021-06-25T12:41:51.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Steuer stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-25T12:41:51.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Steuer stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-25T12:41:51.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Steuer stimmt überein', Description = 'Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-25T12:42:01.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Steuer stimmt überein', PrintName='Steuer stimmt überein',Updated=TO_TIMESTAMP('2021-06-25 15:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='nl_NL'
;

-- 2021-06-25T12:42:01.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'nl_NL') 
;

-- 2021-06-25T12:42:11.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tax matching', PrintName='Tax matching',Updated=TO_TIMESTAMP('2021-06-25 15:42:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='en_US'
;

-- 2021-06-25T12:42:11.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'en_US') 
;

--Steuersatz OK
-- 2021-06-25T12:43:30.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Steuersatz stimmt überein', PrintName='Steuersatz stimmt überein',Updated=TO_TIMESTAMP('2021-06-25 15:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='de_CH'
;

-- 2021-06-25T12:43:30.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'de_CH') 
;

-- 2021-06-25T12:43:34.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Steuersatz stimmt überein', PrintName='Steuersatz stimmt überein',Updated=TO_TIMESTAMP('2021-06-25 15:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='de_DE'
;

-- 2021-06-25T12:43:34.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'de_DE') 
;

-- 2021-06-25T12:43:34.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579248,'de_DE') 
;

-- 2021-06-25T12:43:34.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsTaxRateMatch', Name='Steuersatz stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579248
;

-- 2021-06-25T12:43:34.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTaxRateMatch', Name='Steuersatz stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL, AD_Element_ID=579248 WHERE UPPER(ColumnName)='ISTAXRATEMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-25T12:43:34.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTaxRateMatch', Name='Steuersatz stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579248 AND IsCentrallyMaintained='Y'
;

-- 2021-06-25T12:43:34.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Steuersatz stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579248) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579248)
;

-- 2021-06-25T12:43:34.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Steuersatz stimmt überein', Name='Steuersatz stimmt überein' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579248)
;

-- 2021-06-25T12:43:34.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Steuersatz stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-25T12:43:34.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Steuersatz stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-25T12:43:34.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Steuersatz stimmt überein', Description = 'Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-25T12:43:39.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Steuersatz stimmt überein', PrintName='Steuersatz stimmt überein',Updated=TO_TIMESTAMP('2021-06-25 15:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='nl_NL'
;

-- 2021-06-25T12:43:39.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'nl_NL') 
;

-- 2021-06-25T12:43:49.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tax rate matching', PrintName='Tax rate matching',Updated=TO_TIMESTAMP('2021-06-25 15:43:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='en_US'
;

-- 2021-06-25T12:43:49.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'en_US') 
;

--Textbaustein OK
-- 2021-06-25T12:44:42.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Textbaustein stimmt überein', PrintName='Textbaustein stimmt überein',Updated=TO_TIMESTAMP('2021-06-25 15:44:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='de_CH'
;

-- 2021-06-25T12:44:42.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'de_CH') 
;

-- 2021-06-25T12:44:46.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Textbaustein stimmt überein', PrintName='Textbaustein stimmt überein',Updated=TO_TIMESTAMP('2021-06-25 15:44:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='de_DE'
;

-- 2021-06-25T12:44:46.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'de_DE') 
;

-- 2021-06-25T12:44:46.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579249,'de_DE') 
;

-- 2021-06-25T12:44:46.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsTaxBoilerPlateMatch', Name='Textbaustein stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579249
;

-- 2021-06-25T12:44:46.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTaxBoilerPlateMatch', Name='Textbaustein stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL, AD_Element_ID=579249 WHERE UPPER(ColumnName)='ISTAXBOILERPLATEMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-25T12:44:46.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTaxBoilerPlateMatch', Name='Textbaustein stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579249 AND IsCentrallyMaintained='Y'
;

-- 2021-06-25T12:44:46.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Textbaustein stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579249) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579249)
;

-- 2021-06-25T12:44:46.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Textbaustein stimmt überein', Name='Textbaustein stimmt überein' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579249)
;

-- 2021-06-25T12:44:46.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Textbaustein stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579249
;

-- 2021-06-25T12:44:46.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Textbaustein stimmt überein', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID = 579249
;

-- 2021-06-25T12:44:46.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Textbaustein stimmt überein', Description = 'Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579249
;

-- 2021-06-25T12:44:51.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Textbaustein stimmt überein', PrintName='Textbaustein stimmt überein',Updated=TO_TIMESTAMP('2021-06-25 15:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='nl_NL'
;

-- 2021-06-25T12:44:51.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'nl_NL') 
;

-- 2021-06-25T12:45:08.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Text snippet matching', PrintName='Text snippet matching',Updated=TO_TIMESTAMP('2021-06-25 15:45:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='en_US'
;

-- 2021-06-25T12:45:08.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'en_US') 
;

--Zugeordnete Steuer
-- 2021-06-25T12:46:22.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugeordnete Steuer im System', PrintName='Zugeordnete Steuer im System',Updated=TO_TIMESTAMP('2021-06-25 15:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='de_CH'
;

-- 2021-06-25T12:46:22.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'de_CH') 
;

-- 2021-06-25T12:46:27.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugeordnete Steuer im System', PrintName='Zugeordnete Steuer im System',Updated=TO_TIMESTAMP('2021-06-25 15:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='de_DE'
;

-- 2021-06-25T12:46:27.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'de_DE') 
;

-- 2021-06-25T12:46:27.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579246,'de_DE') 
;

-- 2021-06-25T12:46:27.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Run_Tax_ID', Name='Zugeordnete Steuer im System', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL WHERE AD_Element_ID=579246
;

-- 2021-06-25T12:46:27.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Run_Tax_ID', Name='Zugeordnete Steuer im System', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL, AD_Element_ID=579246 WHERE UPPER(ColumnName)='RUN_TAX_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-25T12:46:27.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Run_Tax_ID', Name='Zugeordnete Steuer im System', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL WHERE AD_Element_ID=579246 AND IsCentrallyMaintained='Y'
;

-- 2021-06-25T12:46:27.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordnete Steuer im System', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579246) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579246)
;

-- 2021-06-25T12:46:27.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugeordnete Steuer im System', Name='Zugeordnete Steuer im System' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579246)
;

-- 2021-06-25T12:46:27.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zugeordnete Steuer im System', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579246
;

-- 2021-06-25T12:46:27.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zugeordnete Steuer im System', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL WHERE AD_Element_ID = 579246
;

-- 2021-06-25T12:46:27.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zugeordnete Steuer im System', Description = 'Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579246
;

-- 2021-06-25T12:46:34.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugeordnete Steuer im System', PrintName='Zugeordnete Steuer im System',Updated=TO_TIMESTAMP('2021-06-25 15:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='nl_NL'
;

-- 2021-06-25T12:46:34.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'nl_NL') 
;

-- 2021-06-25T12:46:44.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Assigned tax in System', PrintName='Assigned tax in System',Updated=TO_TIMESTAMP('2021-06-25 15:46:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='en_US'
;

-- 2021-06-25T12:46:44.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'en_US') 
;

--Steuer 

-- 2021-06-25T12:47:23.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Steuer Rechnungsposition', PrintName='Steuer Rechnungsposition',Updated=TO_TIMESTAMP('2021-06-25 15:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579238 AND AD_Language='de_CH'
;

-- 2021-06-25T12:47:23.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579238,'de_CH') 
;

-- 2021-06-25T12:47:28.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Steuer Rechnungsposition', PrintName='Steuer Rechnungsposition',Updated=TO_TIMESTAMP('2021-06-25 15:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579238 AND AD_Language='de_DE'
;

-- 2021-06-25T12:47:28.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579238,'de_DE') 
;

-- 2021-06-25T12:47:28.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579238,'de_DE') 
;

-- 2021-06-25T12:47:28.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_InvoiceLine_Tax_ID', Name='Steuer Rechnungsposition', Description=NULL, Help=NULL WHERE AD_Element_ID=579238
;

-- 2021-06-25T12:47:28.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_InvoiceLine_Tax_ID', Name='Steuer Rechnungsposition', Description=NULL, Help=NULL, AD_Element_ID=579238 WHERE UPPER(ColumnName)='C_INVOICELINE_TAX_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-25T12:47:28.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_InvoiceLine_Tax_ID', Name='Steuer Rechnungsposition', Description=NULL, Help=NULL WHERE AD_Element_ID=579238 AND IsCentrallyMaintained='Y'
;

-- 2021-06-25T12:47:28.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Steuer Rechnungsposition', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579238) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579238)
;

-- 2021-06-25T12:47:28.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Steuer Rechnungsposition', Name='Steuer Rechnungsposition' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579238)
;

-- 2021-06-25T12:47:28.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Steuer Rechnungsposition', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579238
;

-- 2021-06-25T12:47:28.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Steuer Rechnungsposition', Description=NULL, Help=NULL WHERE AD_Element_ID = 579238
;

-- 2021-06-25T12:47:28.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Steuer Rechnungsposition', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579238
;

-- 2021-06-25T12:47:37.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Steuer Rechnungsposition', PrintName='Steuer Rechnungsposition',Updated=TO_TIMESTAMP('2021-06-25 15:47:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579238 AND AD_Language='nl_NL'
;

-- 2021-06-25T12:47:37.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579238,'nl_NL') 
;

-- 2021-06-25T12:47:39.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-25 15:47:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579238 AND AD_Language='nl_NL'
;

-- 2021-06-25T12:47:39.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579238,'nl_NL') 
;

-- 2021-06-25T12:47:49Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tax invoice Line', PrintName='Tax invoice Line',Updated=TO_TIMESTAMP('2021-06-25 15:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579238 AND AD_Language='en_US'
;

-- 2021-06-25T12:47:49.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579238,'en_US') 
;

--StartDate
-- 2021-06-25T12:50:17.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579394,0,TO_TIMESTAMP('2021-06-25 15:50:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','StartDate','StartDate',TO_TIMESTAMP('2021-06-25 15:50:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-25T12:50:17.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579394 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-25T12:50:30.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Startzeitpunkt', PrintName='Startzeitpunkt',Updated=TO_TIMESTAMP('2021-06-25 15:50:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579394 AND AD_Language='de_CH'
;

-- 2021-06-25T12:50:30.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579394,'de_CH') 
;

-- 2021-06-25T12:50:37.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Startzeitpunkt', PrintName='Startzeitpunkt',Updated=TO_TIMESTAMP('2021-06-25 15:50:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579394 AND AD_Language='de_DE'
;

-- 2021-06-25T12:50:37.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579394,'de_DE') 
;

-- 2021-06-25T12:50:37.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579394,'de_DE') 
;

-- 2021-06-25T12:50:37.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Startzeitpunkt', Description=NULL, Help=NULL WHERE AD_Element_ID=579394
;

-- 2021-06-25T12:50:37.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Startzeitpunkt', Description=NULL, Help=NULL WHERE AD_Element_ID=579394 AND IsCentrallyMaintained='Y'
;

-- 2021-06-25T12:50:37.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Startzeitpunkt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579394) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579394)
;

-- 2021-06-25T12:50:37.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Startzeitpunkt', Name='Startzeitpunkt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579394)
;

-- 2021-06-25T12:50:37.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Startzeitpunkt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579394
;

-- 2021-06-25T12:50:37.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Startzeitpunkt', Description=NULL, Help=NULL WHERE AD_Element_ID = 579394
;

-- 2021-06-25T12:50:37.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Startzeitpunkt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579394
;

-- 2021-06-25T12:51:17.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Start Date', PrintName='Start Date',Updated=TO_TIMESTAMP('2021-06-25 15:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579394 AND AD_Language='en_US'
;

-- 2021-06-25T12:51:17.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579394,'en_US') 
;

-- 2021-06-25T12:51:23.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Startzeitpunkt', PrintName='Startzeitpunkt',Updated=TO_TIMESTAMP('2021-06-25 15:51:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579394 AND AD_Language='nl_NL'
;

-- 2021-06-25T12:51:23.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579394,'nl_NL') 
;

-- 2021-06-25T12:51:42.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=579394, Description=NULL, Help=NULL, Name='Startzeitpunkt',Updated=TO_TIMESTAMP('2021-06-25 15:51:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646803
;

-- 2021-06-25T12:51:42.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579394) 
;

-- 2021-06-25T12:51:42.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646803
;

-- 2021-06-25T12:51:42.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(646803)
;

-- Enddate
-- 2021-06-25T12:52:36.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name=' Endzeitpunkt', PrintName=' Endzeitpunkt',Updated=TO_TIMESTAMP('2021-06-25 15:52:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579243 AND AD_Language='de_CH'
;

-- 2021-06-25T12:52:36.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579243,'de_CH') 
;

-- 2021-06-25T12:52:41.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name=' Endzeitpunkt', PrintName=' Endzeitpunkt',Updated=TO_TIMESTAMP('2021-06-25 15:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579243 AND AD_Language='de_DE'
;

-- 2021-06-25T12:52:41.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579243,'de_DE') 
;

-- 2021-06-25T12:52:41.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579243,'de_DE') 
;

-- 2021-06-25T12:52:41.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DateEnd', Name=' Endzeitpunkt', Description=NULL, Help=NULL WHERE AD_Element_ID=579243
;

-- 2021-06-25T12:52:41.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DateEnd', Name=' Endzeitpunkt', Description=NULL, Help=NULL, AD_Element_ID=579243 WHERE UPPER(ColumnName)='DATEEND' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-25T12:52:41.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DateEnd', Name=' Endzeitpunkt', Description=NULL, Help=NULL WHERE AD_Element_ID=579243 AND IsCentrallyMaintained='Y'
;

-- 2021-06-25T12:52:41.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name=' Endzeitpunkt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579243) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579243)
;

-- 2021-06-25T12:52:41.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName=' Endzeitpunkt', Name=' Endzeitpunkt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579243)
;

-- 2021-06-25T12:52:41.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name=' Endzeitpunkt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579243
;

-- 2021-06-25T12:52:41.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name=' Endzeitpunkt', Description=NULL, Help=NULL WHERE AD_Element_ID = 579243
;

-- 2021-06-25T12:52:41.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = ' Endzeitpunkt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579243
;

-- 2021-06-25T12:52:47.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name=' Endzeitpunkt', PrintName=' Endzeitpunkt',Updated=TO_TIMESTAMP('2021-06-25 15:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579243 AND AD_Language='nl_NL'
;

-- 2021-06-25T12:52:47.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579243,'nl_NL') 
;

-- Add Read Only Logic when status is 'Finished'
-- 2021-06-25T14:34:27.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Status@=''F''',Updated=TO_TIMESTAMP('2021-06-25 17:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574067
;

-- 2021-06-25T14:35:27.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Status@=''F''',Updated=TO_TIMESTAMP('2021-06-25 17:35:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574059
;

-- 2021-06-25T14:50:31.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Status/''P''@=''F''',Updated=TO_TIMESTAMP('2021-06-25 17:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574059
;

-- 2021-06-25T14:52:45.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Status/''P''@!''P''',Updated=TO_TIMESTAMP('2021-06-25 17:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574059
;

-- 2021-06-25T14:57:49.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Status/''P''@!''P''',Updated=TO_TIMESTAMP('2021-06-25 17:57:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574067
;

-- 2021-06-28T05:52:46.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2021-06-28 08:52:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574059
;