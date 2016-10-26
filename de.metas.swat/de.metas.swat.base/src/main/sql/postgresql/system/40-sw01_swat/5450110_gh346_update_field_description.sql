--
-- intermediate: update the description in C_InvoiceLine and C_Invoice_candidate
--
-- 01.09.2016 18:51
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Help=NULL, Description='Preisnachlass (d.h. Rabatt auch den Einzelpreis) in Prozent.',Updated=TO_TIMESTAMP('2016-09-01 18:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547032
;

-- 01.09.2016 18:51
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=547032
;

-- 01.09.2016 18:52
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Help=NULL, Description='Preisnachlass (d.h. Rabatt auch den Einzelpreis) in Prozent.',Updated=TO_TIMESTAMP('2016-09-01 18:52:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548656
;

-- 01.09.2016 18:52
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=548656
;

