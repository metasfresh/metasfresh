-- 2021-06-15T09:14:15.730Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Relevantes Datum', PrintName='Relevantes Datum',Updated=TO_TIMESTAMP('2021-06-15 11:14:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579237 AND AD_Language='de_DE'
;

-- 2021-06-15T09:14:15.747Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579237,'de_DE')
;

-- 2021-06-15T09:14:15.757Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579237,'de_DE')
;

-- 2021-06-15T09:14:15.758Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='RelevantDate', Name='Relevantes Datum', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL WHERE AD_Element_ID=579237
;

-- 2021-06-15T09:14:15.759Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RelevantDate', Name='Relevantes Datum', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL, AD_Element_ID=579237 WHERE UPPER(ColumnName)='RELEVANTDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-15T09:14:15.759Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RelevantDate', Name='Relevantes Datum', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL WHERE AD_Element_ID=579237 AND IsCentrallyMaintained='Y'
;

-- 2021-06-15T09:14:15.760Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Relevantes Datum', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579237) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579237)
;

-- 2021-06-15T09:14:15.770Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Relevantes Datum', Name='Relevantes Datum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579237)
;

-- 2021-06-15T09:14:15.771Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Relevantes Datum', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579237
;

-- 2021-06-15T09:14:15.772Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Relevantes Datum', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL WHERE AD_Element_ID = 579237
;

-- 2021-06-15T09:14:15.772Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Relevantes Datum', Description = 'Datum anhand dessen der Steuersatz ermittelt wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579237
;

--Desc fix-- 2021-06-15T09:28:02.001Z
-- URL zum Konzept
UPDATE AD_Field SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat',Updated=TO_TIMESTAMP('2021-06-15 11:28:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647461
;

-- Fix Flags Descriptions
-- 2021-06-15T10:40:25.040Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat.',Updated=TO_TIMESTAMP('2021-06-15 12:40:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='de_CH'
;

-- 2021-06-15T10:40:25.057Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'de_CH')
;

-- 2021-06-15T10:40:48.405Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat',Updated=TO_TIMESTAMP('2021-06-15 12:40:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='de_DE'
;

-- 2021-06-15T10:40:48.408Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'de_DE')
;

-- 2021-06-15T10:40:48.430Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579247,'de_DE')
;

-- 2021-06-15T10:40:48.432Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsTaxIdMatch', Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579247
;

-- 2021-06-15T10:40:48.434Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxIdMatch', Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL, AD_Element_ID=579247 WHERE UPPER(ColumnName)='ISTAXIDMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-15T10:40:48.435Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxIdMatch', Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579247 AND IsCentrallyMaintained='Y'
;

-- 2021-06-15T10:40:48.436Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579247) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579247)
;

-- 2021-06-15T10:40:48.457Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-15T10:40:48.459Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-15T10:40:48.460Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Steuer OK', Description = 'Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-15T10:40:58.028Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Rechnungszeile hat.',Updated=TO_TIMESTAMP('2021-06-15 12:40:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='nl_NL'
;

-- 2021-06-15T10:40:58.029Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'nl_NL')
;

-- 2021-06-15T10:43:19.226Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat',Updated=TO_TIMESTAMP('2021-06-15 12:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='de_CH'
;

-- 2021-06-15T10:43:19.227Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'de_CH')
;

-- 2021-06-15T10:43:27.364Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat',Updated=TO_TIMESTAMP('2021-06-15 12:43:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='de_DE'
;

-- 2021-06-15T10:43:27.365Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'de_DE')
;

-- 2021-06-15T10:43:27.395Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579248,'de_DE')
;

-- 2021-06-15T10:43:27.400Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsTaxRateMatch', Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579248
;

-- 2021-06-15T10:43:27.401Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxRateMatch', Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL, AD_Element_ID=579248 WHERE UPPER(ColumnName)='ISTAXRATEMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-15T10:43:27.402Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxRateMatch', Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579248 AND IsCentrallyMaintained='Y'
;

-- 2021-06-15T10:43:27.402Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579248) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579248)
;

-- 2021-06-15T10:43:27.420Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-15T10:43:27.422Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-15T10:43:27.422Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Steuersatz OK', Description = 'Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-15T10:43:37.849Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Rechnungszeile hat',Updated=TO_TIMESTAMP('2021-06-15 12:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='nl_NL'
;

-- 2021-06-15T10:43:37.850Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'nl_NL')
;

-- 2021-06-15T10:44:01.618Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat',Updated=TO_TIMESTAMP('2021-06-15 12:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='de_CH'
;

-- 2021-06-15T10:44:01.618Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'de_CH')
;

-- 2021-06-15T10:44:10.984Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat',Updated=TO_TIMESTAMP('2021-06-15 12:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='de_DE'
;

-- 2021-06-15T10:44:10.987Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'de_DE')
;

-- 2021-06-15T10:44:11.005Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579249,'de_DE')
;

-- 2021-06-15T10:44:11.007Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsTaxBoilerPlateMatch', Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579249
;

-- 2021-06-15T10:44:11.008Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxBoilerPlateMatch', Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL, AD_Element_ID=579249 WHERE UPPER(ColumnName)='ISTAXBOILERPLATEMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-15T10:44:11.009Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxBoilerPlateMatch', Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID=579249 AND IsCentrallyMaintained='Y'
;

-- 2021-06-15T10:44:11.009Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579249) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579249)
;

-- 2021-06-15T10:44:11.030Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579249
;

-- 2021-06-15T10:44:11.033Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', Help=NULL WHERE AD_Element_ID = 579249
;

-- 2021-06-15T10:44:11.034Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Textbaustein OK', Description = 'Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579249
;

-- 2021-06-15T10:44:19.501Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Rechnungszeile hat',Updated=TO_TIMESTAMP('2021-06-15 12:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='nl_NL'
;

-- 2021-06-15T10:44:19.502Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'nl_NL')
;


