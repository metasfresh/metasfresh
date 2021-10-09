-- 2020-10-30T15:52:33.079Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-10-30 16:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573171 AND AD_Language='de_CH'
;

-- 2020-10-30T15:52:33.094Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573171,'de_CH') 
;

-- 2020-10-30T15:52:52.470Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Name='Buyer', PrintName='Buyer',Updated=TO_TIMESTAMP('2020-10-30 16:52:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573171 AND AD_Language='en_US'
;

-- 2020-10-30T15:52:52.492Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573171,'en_US') 
;

-- 2020-10-30T15:53:00.560Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-10-30 16:53:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573171 AND AD_Language='de_DE'
;

-- 2020-10-30T15:53:00.588Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573171,'de_DE') 
;

-- 2020-10-30T15:53:00.655Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(573171,'de_DE') 
;

-- 2020-10-30T15:53:00.670Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Einkäufer', Description='', Help=NULL WHERE AD_Element_ID=573171
;

-- 2020-10-30T15:53:00.702Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Einkäufer', Description='', Help=NULL WHERE AD_Element_ID=573171 AND IsCentrallyMaintained='Y'
;

-- 2020-10-30T15:53:00.717Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Einkäufer', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=573171) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 573171)
;

-- 2020-10-30T15:53:00.771Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Einkäufer', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 573171
;

-- 2020-10-30T15:53:00.786Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Einkäufer', Description='', Help=NULL WHERE AD_Element_ID = 573171
;

-- 2020-10-30T15:53:00.818Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Einkäufer', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 573171
;
