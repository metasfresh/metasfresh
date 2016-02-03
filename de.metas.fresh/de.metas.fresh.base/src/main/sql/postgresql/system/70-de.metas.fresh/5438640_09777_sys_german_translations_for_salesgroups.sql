-- 03.02.2016 13:32
-- URL zum Konzept
UPDATE AD_Element SET Name='Statistik Gruppe', PrintName='Statistik Gruppe',Updated=TO_TIMESTAMP('2016-02-03 13:32:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542949
;

-- 03.02.2016 13:32
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542949
;

-- 03.02.2016 13:32
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Salesgroup', Name='Statistik Gruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=542949
;

-- 03.02.2016 13:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Salesgroup', Name='Statistik Gruppe', Description=NULL, Help=NULL, AD_Element_ID=542949 WHERE UPPER(ColumnName)='SALESGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 03.02.2016 13:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Salesgroup', Name='Statistik Gruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=542949 AND IsCentrallyMaintained='Y'
;

-- 03.02.2016 13:32
-- URL zum Konzept
UPDATE AD_Field SET Name='Statistik Gruppe', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542949) AND IsCentrallyMaintained='Y'
;

-- 03.02.2016 13:32
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Statistik Gruppe', Name='Statistik Gruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542949)
;

-- 03.02.2016 13:34
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Gastronomie und Grosshandel', ValueName='Gastronomie und Grosshandel',Updated=TO_TIMESTAMP('2016-02-03 13:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541146
;

-- 03.02.2016 13:34
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541146
;

