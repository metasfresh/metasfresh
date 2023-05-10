-- 2019-12-14T15:48:10.767Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Organisation', PrintName='Organisation',Updated=TO_TIMESTAMP('2019-12-14 16:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=113 AND AD_Language='de_DE'
;

-- 2019-12-14T15:48:10.795Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(113,'de_DE') 
;

-- 2019-12-14T15:48:10.926Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(113,'de_DE') 
;

-- 2019-12-14T15:48:10.927Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Org_ID', Name='Organisation', Description='Organisatorische Einheit des Mandanten', Help='Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.' WHERE AD_Element_ID=113
;

-- 2019-12-14T15:48:10.967Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Org_ID', Name='Organisation', Description='Organisatorische Einheit des Mandanten', Help='Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.', AD_Element_ID=113 WHERE UPPER(ColumnName)='AD_ORG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-14T15:48:10.968Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Org_ID', Name='Organisation', Description='Organisatorische Einheit des Mandanten', Help='Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.' WHERE AD_Element_ID=113 AND IsCentrallyMaintained='Y'
;

-- 2019-12-14T15:48:10.970Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Organisation', Description='Organisatorische Einheit des Mandanten', Help='Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=113) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 113)
;

-- 2019-12-14T15:48:11.012Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Organisation', Name='Organisation' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=113)
;

-- 2019-12-14T15:48:11.019Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Organisation', Description='Organisatorische Einheit des Mandanten', Help='Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.', CommitWarning = NULL WHERE AD_Element_ID = 113
;

-- 2019-12-14T15:48:11.020Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Organisation', Description='Organisatorische Einheit des Mandanten', Help='Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.' WHERE AD_Element_ID = 113
;

-- 2019-12-14T15:48:11.021Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Organisation', Description = 'Organisatorische Einheit des Mandanten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 113
;
