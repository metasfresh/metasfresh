
--
-- here I clean up AD_Pinstance_Log at least a bit, removing the most puzzling column P_ID. for the other columns that are probably obsolete, I don'T have time now
--

--
-- DDL
--
ALTER TABLE AD_PInstance_Log DROP COLUMN IF EXISTS P_ID;

COMMIT;

--
-- DML
--

-- 27.10.2016 07:15
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=10523
;

-- 27.10.2016 07:15
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=10523
;

DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554983
;
DELETE FROM AD_Field WHERE AD_Field_ID=554983
;


-- 27.10.2016 07:21
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=8783
;

-- 27.10.2016 07:21
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=8783
;




-- 27.10.2016 07:16
-- URL zum Konzept
UPDATE AD_Element SET Name='Eintrag-Nr', PrintName='Eintrag-Nr',Updated=TO_TIMESTAMP('2016-10-27 07:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2064
;

-- 27.10.2016 07:16
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2064
;

-- 27.10.2016 07:16
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Log_ID', Name='Eintrag-Nr', Description=NULL, Help=NULL WHERE AD_Element_ID=2064
;

-- 27.10.2016 07:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Log_ID', Name='Eintrag-Nr', Description=NULL, Help=NULL, AD_Element_ID=2064 WHERE UPPER(ColumnName)='LOG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 27.10.2016 07:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Log_ID', Name='Eintrag-Nr', Description=NULL, Help=NULL WHERE AD_Element_ID=2064 AND IsCentrallyMaintained='Y'
;

-- 27.10.2016 07:16
-- URL zum Konzept
UPDATE AD_Field SET Name='Eintrag-Nr', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2064) AND IsCentrallyMaintained='Y'
;

-- 27.10.2016 07:16
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Eintrag-Nr', Name='Eintrag-Nr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2064)
;

