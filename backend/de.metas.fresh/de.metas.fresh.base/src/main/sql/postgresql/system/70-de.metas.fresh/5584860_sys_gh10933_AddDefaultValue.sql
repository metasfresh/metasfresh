-- 2021-04-06T13:26:17.298Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='Y',Updated=TO_TIMESTAMP('2021-04-06 15:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541958
;

-- 2021-04-06T13:27:17.132Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Quelle komplett übernehmen', PrintName='Quelle komplett übernehmen',Updated=TO_TIMESTAMP('2021-04-06 15:27:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579001 AND AD_Language='de_CH'
;

-- 2021-04-06T13:27:17.162Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579001,'de_CH') 
;

-- 2021-04-06T13:27:35.634Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Quelle komplett übernehmen', PrintName='Quelle komplett übernehmen',Updated=TO_TIMESTAMP('2021-04-06 15:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579001 AND AD_Language='de_DE'
;

-- 2021-04-06T13:27:35.637Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579001,'de_DE') 
;

-- 2021-04-06T13:27:35.654Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579001,'de_DE') 
;

-- 2021-04-06T13:27:35.657Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='MakeTargetAsSource', Name='Quelle komplett übernehmen', Description=NULL, Help=NULL WHERE AD_Element_ID=579001
;

-- 2021-04-06T13:27:35.658Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MakeTargetAsSource', Name='Quelle komplett übernehmen', Description=NULL, Help=NULL, AD_Element_ID=579001 WHERE UPPER(ColumnName)='MAKETARGETASSOURCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-06T13:27:35.659Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MakeTargetAsSource', Name='Quelle komplett übernehmen', Description=NULL, Help=NULL WHERE AD_Element_ID=579001 AND IsCentrallyMaintained='Y'
;

-- 2021-04-06T13:27:35.661Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Quelle komplett übernehmen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579001) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579001)
;

-- 2021-04-06T13:27:35.674Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Quelle komplett übernehmen', Name='Quelle komplett übernehmen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579001)
;

-- 2021-04-06T13:27:35.677Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Quelle komplett übernehmen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579001
;

-- 2021-04-06T13:27:35.679Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Quelle komplett übernehmen', Description=NULL, Help=NULL WHERE AD_Element_ID = 579001
;

-- 2021-04-06T13:27:35.681Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Quelle komplett übernehmen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579001
;

