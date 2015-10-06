--
-- this is just a dirty workaround. we need to set the number of copies per doctype/printformat and bpartner
--

-- 20.07.2015 19:02
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Rechnungskopien',Updated=TO_TIMESTAMP('2015-07-20 19:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2435
;

-- 20.07.2015 19:02
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=2435
;

-- 20.07.2015 19:02
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Rechnungskopien',Updated=TO_TIMESTAMP('2015-07-20 19:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=9701
;

-- 20.07.2015 19:02
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=9701
;

