
-- 2019-11-05T14:47:50.888Z
-- URL zum Konzept
UPDATE AD_Element SET Name='Aktionspreise',Updated=TO_TIMESTAMP('2019-11-05 15:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576099
;

-- 2019-11-05T14:47:50.892Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Campaign_Price_ID', Name='Aktionspreise', Description=NULL, Help=NULL WHERE AD_Element_ID=576099
;

-- 2019-11-05T14:47:50.894Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Campaign_Price_ID', Name='Aktionspreise', Description=NULL, Help=NULL, AD_Element_ID=576099 WHERE UPPER(ColumnName)='C_CAMPAIGN_PRICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-05T14:47:50.895Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Campaign_Price_ID', Name='Aktionspreise', Description=NULL, Help=NULL WHERE AD_Element_ID=576099 AND IsCentrallyMaintained='Y'
;

-- 2019-11-05T14:47:50.896Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Aktionspreise', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576099) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576099)
;

-- 2019-11-05T14:47:50.920Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Aktionspreise', Name='Aktionspreise' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576099)
;

-- 2019-11-05T14:47:50.922Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Aktionspreise', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576099
;

-- 2019-11-05T14:47:50.924Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Aktionspreise', Description=NULL, Help=NULL WHERE AD_Element_ID = 576099
;

-- 2019-11-05T14:47:50.926Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Aktionspreise', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576099
;

