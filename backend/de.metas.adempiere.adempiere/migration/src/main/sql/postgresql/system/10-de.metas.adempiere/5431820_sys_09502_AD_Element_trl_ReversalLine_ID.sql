
-- Trl "ReversalLine_ID"
-- !!! EntityType = D !!!

-- 30.10.2015 08:07
-- URL zum Konzept
UPDATE AD_Element SET Description=NULL, Name='Storno-Zeile', PrintName='Storno-Zeile',Updated=TO_TIMESTAMP('2015-10-30 08:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53689
;

-- 30.10.2015 08:07
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=53689
;

-- 30.10.2015 08:07
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ReversalLine_ID', Name='Storno-Zeile', Description=NULL, Help=NULL WHERE AD_Element_ID=53689
;

-- 30.10.2015 08:07
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ReversalLine_ID', Name='Storno-Zeile', Description=NULL, Help=NULL, AD_Element_ID=53689 WHERE UPPER(ColumnName)='REVERSALLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 30.10.2015 08:07
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ReversalLine_ID', Name='Storno-Zeile', Description=NULL, Help=NULL WHERE AD_Element_ID=53689 AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:07
-- URL zum Konzept
UPDATE AD_Field SET Name='Storno-Zeile', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53689) AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:07
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Storno-Zeile', Name='Storno-Zeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53689)
;

