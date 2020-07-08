-- 2020-06-26T12:37:41.980Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verändert Kosten', PrintName='Verändert Kosten',Updated=TO_TIMESTAMP('2020-06-26 15:37:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543853 AND AD_Language='de_CH'
;

-- 2020-06-26T12:37:42.188Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543853,'de_CH') 
;

-- 2020-06-26T12:37:51.590Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verändert Kosten', PrintName='Verändert Kosten',Updated=TO_TIMESTAMP('2020-06-26 15:37:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543853 AND AD_Language='de_DE'
;

-- 2020-06-26T12:37:51.628Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543853,'de_DE') 
;

-- 2020-06-26T12:37:51.717Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543853,'de_DE') 
;

-- 2020-06-26T12:37:51.752Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsChangingCosts', Name='Verändert Kosten', Description='Set if this record is changing the costs.', Help=NULL WHERE AD_Element_ID=543853
;

-- 2020-06-26T12:37:51.792Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsChangingCosts', Name='Verändert Kosten', Description='Set if this record is changing the costs.', Help=NULL, AD_Element_ID=543853 WHERE UPPER(ColumnName)='ISCHANGINGCOSTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-26T12:37:51.828Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsChangingCosts', Name='Verändert Kosten', Description='Set if this record is changing the costs.', Help=NULL WHERE AD_Element_ID=543853 AND IsCentrallyMaintained='Y'
;

-- 2020-06-26T12:37:51.860Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Verändert Kosten', Description='Set if this record is changing the costs.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543853) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543853)
;

-- 2020-06-26T12:37:51.935Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Verändert Kosten', Name='Verändert Kosten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543853)
;

-- 2020-06-26T12:37:51.971Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Verändert Kosten', Description='Set if this record is changing the costs.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543853
;

-- 2020-06-26T12:37:52.008Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Verändert Kosten', Description='Set if this record is changing the costs.', Help=NULL WHERE AD_Element_ID = 543853
;

-- 2020-06-26T12:37:52.041Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Verändert Kosten', Description = 'Set if this record is changing the costs.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543853
;

