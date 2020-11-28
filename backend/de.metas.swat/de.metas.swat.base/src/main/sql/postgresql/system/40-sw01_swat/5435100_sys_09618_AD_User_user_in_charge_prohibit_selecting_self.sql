
-- 09.12.2015 13:46
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='AD_User.AD_Client_ID IN (@AD_Client_ID@, 0) AND AD_User.AD_Org_ID IN (@AD_Org_ID@, 0)
AND 
AD_User.AD_User_ID!=@AD_User_ID@', Description='Different users with the same AD_Client_ID/AD_Org_ID or 0',Updated=TO_TIMESTAMP('2015-12-09 13:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540314
;
