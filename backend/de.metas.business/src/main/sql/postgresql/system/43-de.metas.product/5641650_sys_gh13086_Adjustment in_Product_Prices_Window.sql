-- 2022-05-31T17:54:53.613Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-05-31 17:54:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541797
;

-- 2022-05-31T17:58:28.700Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Preis editierbar', PrintName='Preis editierbar',Updated=TO_TIMESTAMP('2022-05-31 17:58:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543891 AND AD_Language='de_DE'
;

-- 2022-05-31T17:58:28.738Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543891,'de_DE') 
;

-- 2022-05-31T17:58:28.753Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543891,'de_DE') 
;

-- 2022-05-31T17:58:28.754Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsPriceEditable', Name='Preis editierbar', Description='', Help=NULL WHERE AD_Element_ID=543891
;

-- 2022-05-31T17:58:28.756Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPriceEditable', Name='Preis editierbar', Description='', Help=NULL, AD_Element_ID=543891 WHERE UPPER(ColumnName)='ISPRICEEDITABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-31T17:58:28.757Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPriceEditable', Name='Preis editierbar', Description='', Help=NULL WHERE AD_Element_ID=543891 AND IsCentrallyMaintained='Y'
;

-- 2022-05-31T17:58:28.757Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Preis editierbar', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543891) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543891)
;

-- 2022-05-31T17:58:28.774Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Preis editierbar', Name='Preis editierbar' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543891)
;

-- 2022-05-31T17:58:28.776Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Preis editierbar', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543891
;

-- 2022-05-31T17:58:28.777Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Preis editierbar', Description='', Help=NULL WHERE AD_Element_ID = 543891
;

-- 2022-05-31T17:58:28.778Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Preis editierbar', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543891
;

-- 2022-05-31T17:58:42.965Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Name='Preis editierbar', PrintName='Preis editierbar',Updated=TO_TIMESTAMP('2022-05-31 17:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543891 AND AD_Language='nl_NL'
;

-- 2022-05-31T17:58:42.966Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543891,'nl_NL') 
;

-- 2022-05-31T17:58:52.651Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Preis editierbar', PrintName='Preis editierbar',Updated=TO_TIMESTAMP('2022-05-31 17:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543891 AND AD_Language='de_CH'
;

-- 2022-05-31T17:58:52.653Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543891,'de_CH') 
;

-- 2022-05-31T17:58:57.842Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2022-05-31 17:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543891 AND AD_Language='de_CH'
;

-- 2022-05-31T17:58:57.843Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543891,'de_CH') 
;

-- 2022-05-31T18:00:47.628Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Name='Rabatt editierbar', PrintName='Rabatt editierbar',Updated=TO_TIMESTAMP('2022-05-31 18:00:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543892 AND AD_Language='de_DE'
;

-- 2022-05-31T18:00:47.629Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543892,'de_DE') 
;

-- 2022-05-31T18:00:47.638Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543892,'de_DE') 
;

-- 2022-05-31T18:00:47.640Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDiscountEditable', Name='Rabatt editierbar', Description='', Help=NULL WHERE AD_Element_ID=543892
;

-- 2022-05-31T18:00:47.641Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDiscountEditable', Name='Rabatt editierbar', Description='', Help=NULL, AD_Element_ID=543892 WHERE UPPER(ColumnName)='ISDISCOUNTEDITABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-31T18:00:47.642Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDiscountEditable', Name='Rabatt editierbar', Description='', Help=NULL WHERE AD_Element_ID=543892 AND IsCentrallyMaintained='Y'
;

-- 2022-05-31T18:00:47.642Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rabatt editierbar', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543892) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543892)
;

-- 2022-05-31T18:00:47.658Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rabatt editierbar', Name='Rabatt editierbar' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543892)
;

-- 2022-05-31T18:00:47.659Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rabatt editierbar', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543892
;

-- 2022-05-31T18:00:47.661Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rabatt editierbar', Description='', Help=NULL WHERE AD_Element_ID = 543892
;

-- 2022-05-31T18:00:47.661Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rabatt editierbar', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543892
;

-- 2022-05-31T18:01:02.658Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rabatt editierbar', PrintName='Rabatt editierbar',Updated=TO_TIMESTAMP('2022-05-31 18:01:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543892 AND AD_Language='nl_NL'
;

-- 2022-05-31T18:01:02.659Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543892,'nl_NL') 
;

-- 2022-05-31T18:01:13.671Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Name='Rabatt editierbar', PrintName='Rabatt editierbar',Updated=TO_TIMESTAMP('2022-05-31 18:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543892 AND AD_Language='de_CH'
;

-- 2022-05-31T18:01:13.672Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543892,'de_CH') 
;

