-- 19.01.2017 12:48
-- URL zum Konzept
UPDATE AD_Element SET Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Gegenbeleg-Position aus einer anderen Organisation referenzieren', Help='References a counterdoc orderline', Name='Gegenbelegzeile-Fremdorganisation', PrintName='Gegenbelegzeile-Fremdorganisation',Updated=TO_TIMESTAMP('2017-01-19 12:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1905
;

-- 19.01.2017 12:48
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=1905
;

-- 19.01.2017 12:48
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Ref_OrderLine_ID', Name='Gegenbelegzeile-Fremdorganisation', Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Gegenbeleg-Position aus einer anderen Organisation referenzieren', Help='References a counterdoc orderline' WHERE AD_Element_ID=1905
;

-- 19.01.2017 12:48
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Ref_OrderLine_ID', Name='Gegenbelegzeile-Fremdorganisation', Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Gegenbeleg-Position aus einer anderen Organisation referenzieren', Help='References a counterdoc orderline', AD_Element_ID=1905 WHERE UPPER(ColumnName)='REF_ORDERLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 19.01.2017 12:48
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Ref_OrderLine_ID', Name='Gegenbelegzeile-Fremdorganisation', Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Gegenbeleg-Position aus einer anderen Organisation referenzieren', Help='References a counterdoc orderline' WHERE AD_Element_ID=1905 AND IsCentrallyMaintained='Y'
;

-- 19.01.2017 12:48
-- URL zum Konzept
UPDATE AD_Field SET Name='Gegenbelegzeile-Fremdorganisation', Description='Mit diesem Feld kann eine Auftragsposition die ihr zugehörige Gegenbeleg-Position aus einer anderen Organisation referenzieren', Help='References a counterdoc orderline' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1905) AND IsCentrallyMaintained='Y'
;

-- 19.01.2017 12:48
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Gegenbelegzeile-Fremdorganisation', Name='Gegenbelegzeile-Fremdorganisation' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1905)
;
