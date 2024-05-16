-- 2021-12-23T15:11:23.775Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Inhalt', PrintName='Inhalt',Updated=TO_TIMESTAMP('2021-12-23 16:11:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580321 AND AD_Language='de_CH'
;

-- 2021-12-23T15:11:23.796Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580321,'de_CH') 
;

-- 2021-12-23T15:11:40.368Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Inhalt', PrintName='Inhalt',Updated=TO_TIMESTAMP('2021-12-23 16:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580321 AND AD_Language='de_DE'
;

-- 2021-12-23T15:11:40.372Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580321,'de_DE') 
;

-- 2021-12-23T15:11:40.402Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(580321,'de_DE') 
;

-- 2021-12-23T15:11:40.407Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Content', Name='Inhalt', Description=NULL, Help=NULL WHERE AD_Element_ID=580321
;

-- 2021-12-23T15:11:40.410Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Content', Name='Inhalt', Description=NULL, Help=NULL, AD_Element_ID=580321 WHERE UPPER(ColumnName)='CONTENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-23T15:11:40.412Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Content', Name='Inhalt', Description=NULL, Help=NULL WHERE AD_Element_ID=580321 AND IsCentrallyMaintained='Y'
;

-- 2021-12-23T15:11:40.413Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Inhalt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580321) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580321)
;

-- 2021-12-23T15:11:40.441Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Inhalt', Name='Inhalt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580321)
;

-- 2021-12-23T15:11:40.442Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Inhalt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580321
;

-- 2021-12-23T15:11:40.443Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Inhalt', Description=NULL, Help=NULL WHERE AD_Element_ID = 580321
;

-- 2021-12-23T15:11:40.444Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Inhalt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580321
;

-- 2021-12-23T15:11:52.190Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Inhalt', PrintName='Inhalt',Updated=TO_TIMESTAMP('2021-12-23 16:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580321 AND AD_Language='nl_NL'
;

-- 2021-12-23T15:11:52.191Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580321,'nl_NL') 
;

-- 2021-12-23T15:16:01.009Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=5000,Updated=TO_TIMESTAMP('2021-12-23 16:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578723
;

-- 2021-12-23T15:16:08.858Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_product','Preparation','TEXT',null,null)
;

-- 2021-12-23T15:17:31.074Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=5000,Updated=TO_TIMESTAMP('2021-12-23 16:17:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578724
;

-- 2021-12-23T15:17:32.857Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_product','Consumption_Recommentation','TEXT',null,null)
;
