
-- Jun 16, 2016 5:36 PM
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType = ''MST'' 
AND (C_DocType.AD_Org_ID = @AD_Org_ID@ OR C_DocType.AD_Org_ID = 0) 
AND C_DocType.AD_Client_ID = @AD_Client_ID@',Updated=TO_TIMESTAMP('2016-06-16 17:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540057
;


