-- 2022-03-08T12:56:59.602Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mahnungsfrist', PrintName='Mahnungsfrist',Updated=TO_TIMESTAMP('2022-03-08 13:56:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53223 AND AD_Language='de_DE'
;

-- 2022-03-08T12:56:59.622Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53223,'de_DE')
;

-- 2022-03-08T12:56:59.633Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(53223,'de_DE')
;

-- 2022-03-08T12:56:59.634Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DunningGrace', Name='Mahnungsfrist', Description=NULL, Help=NULL WHERE AD_Element_ID=53223
;

-- 2022-03-08T12:56:59.635Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DunningGrace', Name='Mahnungsfrist', Description=NULL, Help=NULL, AD_Element_ID=53223 WHERE UPPER(ColumnName)='DUNNINGGRACE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-08T12:56:59.635Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DunningGrace', Name='Mahnungsfrist', Description=NULL, Help=NULL WHERE AD_Element_ID=53223 AND IsCentrallyMaintained='Y'
;

-- 2022-03-08T12:56:59.636Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Mahnungsfrist', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53223) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53223)
;

-- 2022-03-08T12:56:59.651Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Mahnungsfrist', Name='Mahnungsfrist' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53223)
;

-- 2022-03-08T12:56:59.652Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Mahnungsfrist', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 53223
;

-- 2022-03-08T12:56:59.654Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Mahnungsfrist', Description=NULL, Help=NULL WHERE AD_Element_ID = 53223
;

-- 2022-03-08T12:56:59.654Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Mahnungsfrist', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 53223
;
