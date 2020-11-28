

-- 24.11.2015 13:10
-- URL zum Konzept
UPDATE AD_Element SET Name='davor andrucken', PrintName='davor andrucken',Updated=TO_TIMESTAMP('2015-11-24 13:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542552
;

-- 24.11.2015 13:10
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542552
;

-- 24.11.2015 13:10
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsPrintBefore', Name='davor andrucken', Description=NULL, Help='If set, this line will be printed before the line to which it links.' WHERE AD_Element_ID=542552
;

-- 24.11.2015 13:10
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPrintBefore', Name='davor andrucken', Description=NULL, Help='If set, this line will be printed before the line to which it links.', AD_Element_ID=542552 WHERE UPPER(ColumnName)='ISPRINTBEFORE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 24.11.2015 13:10
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPrintBefore', Name='davor andrucken', Description=NULL, Help='If set, this line will be printed before the line to which it links.' WHERE AD_Element_ID=542552 AND IsCentrallyMaintained='Y'
;

-- 24.11.2015 13:10
-- URL zum Konzept
UPDATE AD_Field SET Name='davor andrucken', Description=NULL, Help='If set, this line will be printed before the line to which it links.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542552) AND IsCentrallyMaintained='Y'
;

-- 24.11.2015 13:10
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='davor andrucken', Name='davor andrucken' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542552)
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Element SET Name='Detail-Info statt Rechnungszeile andrucken', PrintName='Detail-Info statt Rechnungszeile andrucken',Updated=TO_TIMESTAMP('2015-11-24 13:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542573
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542573
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDetailOverridesLine', Name='Detail-Info statt Rechnungszeile andrucken', Description=NULL, Help=NULL WHERE AD_Element_ID=542573
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDetailOverridesLine', Name='Detail-Info statt Rechnungszeile andrucken', Description=NULL, Help=NULL, AD_Element_ID=542573 WHERE UPPER(ColumnName)='ISDETAILOVERRIDESLINE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDetailOverridesLine', Name='Detail-Info statt Rechnungszeile andrucken', Description=NULL, Help=NULL WHERE AD_Element_ID=542573 AND IsCentrallyMaintained='Y'
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_Field SET Name='Detail-Info statt Rechnungszeile andrucken', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542573) AND IsCentrallyMaintained='Y'
;

-- 24.11.2015 13:11
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Detail-Info statt Rechnungszeile andrucken', Name='Detail-Info statt Rechnungszeile andrucken' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542573)
;
