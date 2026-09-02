--
-- do some fixex/translations to C_Doc_Outbound_Config
--

-- 08.10.2015 09:53
-- URL zum Konzept
UPDATE AD_Field SET Description='Wenn dieser Datensatz aktiv ist, dann wird automatisch ein PDF-Druckstück erstellt.', IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2015-10-08 09:53:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551409
;

-- 08.10.2015 09:53
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=551409
;



-- 07.10.2015 15:30
-- URL zum Konzept
UPDATE AD_Table SET Name='Ausgehender Beleg',Updated=TO_TIMESTAMP('2015-10-07 15:30:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540453
;

-- 07.10.2015 15:30
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540453
;

-- 07.10.2015 15:31
-- URL zum Konzept
UPDATE AD_Table SET Name='Ausgehender Beleg Log',Updated=TO_TIMESTAMP('2015-10-07 15:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540453
;

-- 07.10.2015 15:31
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540453
;

-- 07.10.2015 15:31
-- URL zum Konzept
UPDATE AD_Table SET Name='Ausgehender Beleg Logposition',Updated=TO_TIMESTAMP('2015-10-07 15:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540454
;

-- 07.10.2015 15:31
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540454
;

--
-- Archive 
--
-- 08.10.2015 08:28
-- URL zum Konzept
UPDATE AD_Window_Trl SET IsTranslated='N' WHERE AD_Window_ID=540176
;

-- 08.10.2015 08:28
-- URL zum Konzept
UPDATE AD_Tab SET Name='Archiv',Updated=TO_TIMESTAMP('2015-10-08 08:28:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540483
;

-- 08.10.2015 08:28
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540483
;

