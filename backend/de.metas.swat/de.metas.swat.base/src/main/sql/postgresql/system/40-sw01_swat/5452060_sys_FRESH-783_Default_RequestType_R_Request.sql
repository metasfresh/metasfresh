-- 20.10.2016 16:19
-- URL zum Konzept
UPDATE R_RequestType SET Name='Kundenbeanstandung',Updated=TO_TIMESTAMP('2016-10-20 16:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_RequestType_ID=540005
;

-- 20.10.2016 16:19
-- URL zum Konzept
UPDATE R_RequestType SET Name='Lieferantenbeanstandung',Updated=TO_TIMESTAMP('2016-10-20 16:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_RequestType_ID=540006
;


-- 20.10.2016 16:38
-- URL zum Konzept
UPDATE AD_Field SET DefaultValue='@SQL=SELECT R_RequestType_ID from R_RequestType WHERE InternalName = ''A_CustomerComplaint''',Updated=TO_TIMESTAMP('2016-10-20 16:38:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5858
;


-- 20.10.2016 16:44
-- URL zum Konzept
UPDATE AD_Field SET DefaultValue='@SQL=SELECT R_RequestType_ID from R_RequestType WHERE InternalName = ''A_CustomerComplaint''',Updated=TO_TIMESTAMP('2016-10-20 16:44:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5860
;

