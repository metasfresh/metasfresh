--
-- Replication-Trx-Stuff
-- Fxingin parent column and reference (=>search)
--
-- 23.07.2015 07:30
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=550421,Updated=TO_TIMESTAMP('2015-07-23 07:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540582
;

-- 23.07.2015 07:30
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2015-07-23 07:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550421
;

-- 23.07.2015 08:44
-- URL zum Konzept
UPDATE AD_Tab SET Name='Lieferung/Kommissionierung',Updated=TO_TIMESTAMP('2015-07-23 08:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540564
;

-- 23.07.2015 08:44
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540564
;

-- 23.07.2015 08:45
-- URL zum Konzept
UPDATE AD_Tab SET Name='Handling Unit Zuordnung',Updated=TO_TIMESTAMP('2015-07-23 08:45:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540623
;

-- 23.07.2015 08:45
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540623
;

-- 23.07.2015 08:45
-- URL zum Konzept
UPDATE AD_Tab SET Name='Liefer-/Kommissionierzeile',Updated=TO_TIMESTAMP('2015-07-23 08:45:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540564
;

-- 23.07.2015 08:45
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540564
;

