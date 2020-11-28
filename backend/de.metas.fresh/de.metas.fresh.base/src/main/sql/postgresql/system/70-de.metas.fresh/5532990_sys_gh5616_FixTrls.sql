-- 2019-10-04T07:31:14.536Z
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Product Ingredients',Updated=TO_TIMESTAMP('2019-10-04 09:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541412
;

-- 2019-10-04T07:31:21.203Z
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Product Ingredients',Updated=TO_TIMESTAMP('2019-10-04 09:31:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541412
;






-- 2019-10-04T07:38:01.155Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zutaten', PrintName='Zutaten',Updated=TO_TIMESTAMP('2019-10-04 09:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577143 AND AD_Language='de_CH'
;

-- 2019-10-04T07:38:01.159Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577143,'de_CH') 
;

-- 2019-10-04T07:38:12.315Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zutaten', PrintName='Zutaten',Updated=TO_TIMESTAMP('2019-10-04 09:38:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577143 AND AD_Language='de_DE'
;

-- 2019-10-04T07:38:12.317Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577143,'de_DE') 
;

-- 2019-10-04T07:38:12.339Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(577143,'de_DE') 
;

-- 2019-10-04T07:38:12.342Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Product_Ingredients_ID', Name='Zutaten', Description=NULL, Help=NULL WHERE AD_Element_ID=577143
;

-- 2019-10-04T07:38:12.345Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_Ingredients_ID', Name='Zutaten', Description=NULL, Help=NULL, AD_Element_ID=577143 WHERE UPPER(ColumnName)='M_PRODUCT_INGREDIENTS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-04T07:38:12.347Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_Ingredients_ID', Name='Zutaten', Description=NULL, Help=NULL WHERE AD_Element_ID=577143 AND IsCentrallyMaintained='Y'
;

-- 2019-10-04T07:38:12.348Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zutaten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577143) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577143)
;

-- 2019-10-04T07:38:12.370Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zutaten', Name='Zutaten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577143)
;

-- 2019-10-04T07:38:12.371Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zutaten', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577143
;

-- 2019-10-04T07:38:12.372Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zutaten', Description=NULL, Help=NULL WHERE AD_Element_ID = 577143
;

-- 2019-10-04T07:38:12.373Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zutaten', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577143
;

-- 2019-10-04T07:38:16.590Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-04 09:38:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577143 AND AD_Language='en_US'
;

-- 2019-10-04T07:38:16.593Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577143,'en_US') 
;

