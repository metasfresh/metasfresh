

-- 07.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='LastExportBy_ID',Updated=TO_TIMESTAMP('2015-12-07 15:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540625
;

-- 07.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='LastExportBy_ID', Name='Zuletzt exportiert von', Description=NULL, Help=NULL WHERE AD_Element_ID=540625
;

-- 07.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LastExportBy_ID', Name='Zuletzt exportiert von', Description=NULL, Help=NULL, AD_Element_ID=540625 WHERE UPPER(ColumnName)='LASTEXPORTBY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LastExportBy_ID', Name='Zuletzt exportiert von', Description=NULL, Help=NULL WHERE AD_Element_ID=540625 AND IsCentrallyMaintained='Y'
;

-- 07.12.2015 15:59
-- URL zum Konzept
ALTER TABLE C_PaySelection ADD LastExportBy_ID NUMERIC(10) DEFAULT NULL 
;

