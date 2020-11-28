
-- 24.11.2015 12:10
-- URL zum Konzept
UPDATE AD_Element SET Name='von Rechnungsdispo verarbeitet', PrintName='von Rechnungsdispo verarbeitet',Updated=TO_TIMESTAMP('2015-11-24 12:10:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542255
;

-- 24.11.2015 12:10
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542255
;

-- 24.11.2015 12:10
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsInvoiceCandidate', Name='von Rechnungsdispo verarbeitet', Description=NULL, Help=NULL WHERE AD_Element_ID=542255
;

-- 24.11.2015 12:10
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsInvoiceCandidate', Name='von Rechnungsdispo verarbeitet', Description=NULL, Help=NULL, AD_Element_ID=542255 WHERE UPPER(ColumnName)='ISINVOICECANDIDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 24.11.2015 12:10
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsInvoiceCandidate', Name='von Rechnungsdispo verarbeitet', Description=NULL, Help=NULL WHERE AD_Element_ID=542255 AND IsCentrallyMaintained='Y'
;

-- 24.11.2015 12:10
-- URL zum Konzept
UPDATE AD_Field SET Name='von Rechnungsdispo verarbeitet', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542255) AND IsCentrallyMaintained='Y'
;

-- 24.11.2015 12:10
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='von Rechnungsdispo verarbeitet', Name='von Rechnungsdispo verarbeitet' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542255)
;
