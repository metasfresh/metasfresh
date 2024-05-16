-- 2022-01-31T15:03:31.062Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Incoterms Stadt', PrintName='Incoterms Stadt',Updated=TO_TIMESTAMP('2022-01-31 16:03:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580529 AND AD_Language='de_CH'
;

-- 2022-01-31T15:03:31.088Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580529,'de_CH') 
;

-- 2022-01-31T15:03:48.424Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Incoterms Stadt', PrintName='Incoterms Stadt',Updated=TO_TIMESTAMP('2022-01-31 16:03:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580529 AND AD_Language='nl_NL'
;

-- 2022-01-31T15:03:48.426Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580529,'nl_NL') 
;

-- 2022-01-31T15:03:57.188Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Incoterms Stadt', PrintName='Incoterms Stadt',Updated=TO_TIMESTAMP('2022-01-31 16:03:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580529 AND AD_Language='de_DE'
;

-- 2022-01-31T15:03:57.192Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580529,'de_DE') 
;

-- 2022-01-31T15:03:57.213Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(580529,'de_DE') 
;

-- 2022-01-31T15:03:57.217Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Incoterms Stadt', Description=NULL, Help=NULL WHERE AD_Element_ID=580529
;

-- 2022-01-31T15:03:57.218Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Incoterms Stadt', Description=NULL, Help=NULL WHERE AD_Element_ID=580529 AND IsCentrallyMaintained='Y'
;

-- 2022-01-31T15:03:57.219Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Incoterms Stadt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580529)
;

-- 2022-01-31T15:03:57.233Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Incoterms Stadt', Name='Incoterms Stadt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580529)
;

-- 2022-01-31T15:03:57.235Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Incoterms Stadt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580529
;

-- 2022-01-31T15:03:57.236Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Incoterms Stadt', Description=NULL, Help=NULL WHERE AD_Element_ID = 580529
;

-- 2022-01-31T15:03:57.237Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Incoterms Stadt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580529
;

-- 2022-01-31T15:05:42.064Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Incoterms City', PrintName='Incoterms City',Updated=TO_TIMESTAMP('2022-01-31 16:05:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580529 AND AD_Language='en_US'
;

-- 2022-01-31T15:05:42.068Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580529,'en_US') 
;

