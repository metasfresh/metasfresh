

--
-- DDL to replace the not-working AD_Language.AD_Language_ID reference with an AD_Language.AD_Language reference
-- includes DML to update columns before setting them to not-null
--


ALTER TABLE C_Printing_Queue ADD COLUMN ad_language character varying(40);
UPDATE C_Printing_Queue q SET ad_language=(select AD_Language from AD_Language l where l.AD_Language_ID=q.AD_Language_ID);
COMMIT;
ALTER TABLE C_Printing_Queue DROP COLUMN AD_Language_ID;



-- C_Printing_Queue fix AD_Reference from table direct to table
-- 28.10.2016 11:03
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=106,Updated=TO_TIMESTAMP('2016-10-28 11:03:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548491
;

-- 28.10.2016 14:58
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=2159, AD_Reference_ID=19, ColumnName='AD_Language_ID', Description=NULL, Help=NULL, Name='Sprache',Updated=TO_TIMESTAMP('2016-10-28 14:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548491
;

-- 28.10.2016 14:58
-- URL zum Konzept
UPDATE AD_Field SET Name='Sprache', Description=NULL, Help=NULL WHERE AD_Column_ID=548491 AND IsCentrallyMaintained='Y'
;

-- 28.10.2016 14:58
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=109, ColumnName='AD_Language', Description='Sprache für diesen Eintrag', Help='Definiert die Sprache für Anzeige und Aufbereitung', Name='Sprache',Updated=TO_TIMESTAMP('2016-10-28 14:58:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548491
;

-- 28.10.2016 14:58
-- URL zum Konzept
UPDATE AD_Field SET Name='Sprache', Description='Sprache für diesen Eintrag', Help='Definiert die Sprache für Anzeige und Aufbereitung' WHERE AD_Column_ID=548491 AND IsCentrallyMaintained='Y'
;

-- 28.10.2016 14:59
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-10-28 14:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548491
;

