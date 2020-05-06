
-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Element SET Name='andrucken', PrintName='andrucken',Updated=TO_TIMESTAMP('2015-11-24 13:11:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=399
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=399
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsPrinted', Name='andrucken', Description='Indicates if this document / line is printed', Help='The Printed checkbox indicates if this document or line will included when printing.' WHERE AD_Element_ID=399
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPrinted', Name='andrucken', Description='Indicates if this document / line is printed', Help='The Printed checkbox indicates if this document or line will included when printing.', AD_Element_ID=399 WHERE UPPER(ColumnName)='ISPRINTED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPrinted', Name='andrucken', Description='Indicates if this document / line is printed', Help='The Printed checkbox indicates if this document or line will included when printing.' WHERE AD_Element_ID=399 AND IsCentrallyMaintained='Y'
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Field SET Name='andrucken', Description='Indicates if this document / line is printed', Help='The Printed checkbox indicates if this document or line will included when printing.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=399) AND IsCentrallyMaintained='Y'
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='andrucken', Name='andrucken' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=399)
;
