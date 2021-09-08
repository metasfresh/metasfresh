-- 2021-09-07T10:51:35.984Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergeordnete Fachrichtung', PrintName='Übergeordnete Fachrichtung',Updated=TO_TIMESTAMP('2021-09-07 12:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579576 AND AD_Language='de_CH'
;

-- 2021-09-07T10:51:36.010Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579576,'de_CH') 
;

-- 2021-09-07T10:51:41.735Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergeordnete Fachrichtung', PrintName='Übergeordnete Fachrichtung',Updated=TO_TIMESTAMP('2021-09-07 12:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579576 AND AD_Language='de_DE'
;

-- 2021-09-07T10:51:41.736Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579576,'de_DE') 
;

-- 2021-09-07T10:51:41.818Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579576,'de_DE') 
;

-- 2021-09-07T10:51:41.821Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='CRM_Occupation_Parent_ID', Name='Übergeordnete Fachrichtung', Description=NULL, Help=NULL WHERE AD_Element_ID=579576
;

-- 2021-09-07T10:51:41.824Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='CRM_Occupation_Parent_ID', Name='Übergeordnete Fachrichtung', Description=NULL, Help=NULL, AD_Element_ID=579576 WHERE UPPER(ColumnName)='CRM_OCCUPATION_PARENT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-07T10:51:41.827Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='CRM_Occupation_Parent_ID', Name='Übergeordnete Fachrichtung', Description=NULL, Help=NULL WHERE AD_Element_ID=579576 AND IsCentrallyMaintained='Y'
;

-- 2021-09-07T10:51:41.828Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Übergeordnete Fachrichtung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579576) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579576)
;

-- 2021-09-07T10:51:41.923Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Übergeordnete Fachrichtung', Name='Übergeordnete Fachrichtung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579576)
;

-- 2021-09-07T10:51:41.931Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Übergeordnete Fachrichtung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579576
;

-- 2021-09-07T10:51:41.934Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Übergeordnete Fachrichtung', Description=NULL, Help=NULL WHERE AD_Element_ID = 579576
;

-- 2021-09-07T10:51:41.936Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Übergeordnete Fachrichtung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579576
;

-- 2021-09-07T10:51:48.687Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergeordnete Fachrichtung', PrintName='Übergeordnete Fachrichtung',Updated=TO_TIMESTAMP('2021-09-07 12:51:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579576 AND AD_Language='nl_NL'
;

-- 2021-09-07T10:51:48.688Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579576,'nl_NL') 
;

