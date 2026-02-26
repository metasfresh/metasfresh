-- Fix: "AD_Org of Client (excl 0)" had a syntax error - missing != operator
-- 2024-04-19T05:46:03.561Z
UPDATE AD_Val_Rule SET Code='AD_Org.AD_Client_ID=@AD_Client_ID@ AND AD_Org.AD_Org_ID!=0',Updated=TO_TIMESTAMP('2024-04-19 07:46:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=202
;

-- Column: ExternalSystem_Config_ProCareManagement.AD_Org_ID
-- 2024-04-19T06:58:59.313Z
UPDATE AD_Column SET AD_Val_Rule_ID=202,Updated=TO_TIMESTAMP('2024-04-19 08:58:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587995
;

