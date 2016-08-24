
-- 07.07.2016 12:32
-- URL zum Konzept
UPDATE AD_Field SET Name='Abw. oder Explizite Lieferanschrift',Updated=TO_TIMESTAMP('2016-07-07 12:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10124
;

-- 07.07.2016 12:32
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=10124
;

-- 07.07.2016 12:33
-- URL zum Konzept
UPDATE AD_Field SET Name='Abw. oder explizite Lieferanschrift',Updated=TO_TIMESTAMP('2016-07-07 12:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10124
;

-- 07.07.2016 12:33
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=10124
;

-- 07.07.2016 12:34
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Abw. oder expliziter Abladeort',Updated=TO_TIMESTAMP('2016-07-07 12:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553694
;

-- 07.07.2016 12:34
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=553694
;

-- 07.07.2016 12:36
-- URL zum Konzept
UPDATE AD_Element SET Name='Benutze abw. Lieferadresse', PrintName='Benutze abw. Lieferadresse',Updated=TO_TIMESTAMP('2016-07-07 12:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541325
;

-- 07.07.2016 12:36
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541325
;

-- 07.07.2016 12:36
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsUseDeliveryToAddress', Name='Benutze abw. Lieferadresse', Description=NULL, Help=NULL WHERE AD_Element_ID=541325
;

-- 07.07.2016 12:36
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsUseDeliveryToAddress', Name='Benutze abw. Lieferadresse', Description=NULL, Help=NULL, AD_Element_ID=541325 WHERE UPPER(ColumnName)='ISUSEDELIVERYTOADDRESS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2016 12:36
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsUseDeliveryToAddress', Name='Benutze abw. Lieferadresse', Description=NULL, Help=NULL WHERE AD_Element_ID=541325 AND IsCentrallyMaintained='Y'
;

-- 07.07.2016 12:36
-- URL zum Konzept
UPDATE AD_Field SET Name='Benutze abw. Lieferadresse', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541325) AND IsCentrallyMaintained='Y'
;

-- 07.07.2016 12:36
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Benutze abw. Lieferadresse', Name='Benutze abw. Lieferadresse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541325)
;

