--
-- sidenote: hide the "ad_org field"
-- 10.09.2015 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2015-09-10 09:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547060
;

-- 10.09.2015 09:16
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', IsDisplayed='Y', IsDisplayedGrid='Y', Name='Eintrag übernehmen', SeqNo=10, SeqNoGrid=10,Updated=TO_TIMESTAMP('2015-09-10 09:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547043
;

-- 10.09.2015 09:16
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=547043
;

-- 10.09.2015 09:16
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-09-10 09:16:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547053
;

-- 10.09.2015 09:29
-- URL zum Konzept
UPDATE AD_Field SET Description='Sagt aus, ob der Eintrag in eine Auftragsposition überführt werden oder irgnoriert werden soll',Updated=TO_TIMESTAMP('2015-09-10 09:29:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547043
;

-- 10.09.2015 09:29
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=547043
;

