
-- 2020-02-18T15:24:47.260Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.', IsTranslated='Y', Name='Aktionsbezug', PrintName='Aktionsbezug',Updated=TO_TIMESTAMP('2020-02-18 16:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576946 AND AD_Language='de_CH'
;

-- 2020-02-18T15:24:47.263Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576946,'de_CH') 
;

-- 2020-02-18T15:24:52.521Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.',Updated=TO_TIMESTAMP('2020-02-18 16:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576946 AND AD_Language='de_DE'
;

-- 2020-02-18T15:24:52.524Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576946,'de_DE') 
;

-- 2020-02-18T15:24:52.561Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(576946,'de_DE') 
;

-- 2020-02-18T15:24:52.564Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsAllowActionPrice', Name='Aktionsbezug', Description='Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.', Help=NULL WHERE AD_Element_ID=576946
;

-- 2020-02-18T15:24:52.566Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsAllowActionPrice', Name='Aktionsbezug', Description='Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.', Help=NULL, AD_Element_ID=576946 WHERE UPPER(ColumnName)='ISALLOWACTIONPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-18T15:24:52.567Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsAllowActionPrice', Name='Aktionsbezug', Description='Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.', Help=NULL WHERE AD_Element_ID=576946 AND IsCentrallyMaintained='Y'
;

-- 2020-02-18T15:24:52.568Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Aktionsbezug', Description='Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576946) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576946)
;

-- 2020-02-18T15:24:52.707Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Aktionsbezug', Description='Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576946
;

-- 2020-02-18T15:24:52.709Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Aktionsbezug', Description='Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.', Help=NULL WHERE AD_Element_ID = 576946
;

-- 2020-02-18T15:24:52.710Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Aktionsbezug', Description = 'Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576946
;

-- 2020-02-18T15:25:21.833Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Allow campaign prices', PrintName='Allow campaign prices',Updated=TO_TIMESTAMP('2020-02-18 16:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576946 AND AD_Language='en_US'
;

-- 2020-02-18T15:25:21.834Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576946,'en_US') 
;

