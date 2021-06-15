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
