-- 2021-04-05T06:56:03.319Z
-- URL zum Konzept
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2021-04-05 08:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541957
;

-- 2021-04-05T07:00:17.169Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540537,'AD_PrintFormat.AD_PrintFormat_ID=(SELECT pf.AD_PrintFormat_ID FROM M_Product_PrintFormat pf where pf.M_Product_ID=@AD_PrintFormat_ID@)',TO_TIMESTAMP('2021-04-05 09:00:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_PrintFormat_for_M_Ptoduct','S',TO_TIMESTAMP('2021-04-05 09:00:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-05T07:00:30.262Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540537,Updated=TO_TIMESTAMP('2021-04-05 09:00:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541957
;

-- 2021-04-05T07:07:06.447Z
-- URL zum Konzept
UPDATE AD_Process_Para SET FieldLength=15,Updated=TO_TIMESTAMP('2021-04-05 09:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541957
;

-- 2021-04-05T07:08:30.145Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='AD_PrintFormat.AD_PrintFormat_ID=(SELECT pf.AD_PrintFormat_ID FROM M_Product_PrintFormat pf where pf.M_Product_ID=@M_Product_ID@)',Updated=TO_TIMESTAMP('2021-04-05 09:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540537
;

-- 2021-04-05T07:10:58.035Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXIST (SELECT 1 FROM M_Product_PrintFormat pf where pf.M_Product_ID=@M_Product_ID@)',Updated=TO_TIMESTAMP('2021-04-05 09:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540537
;

-- 2021-04-05T07:13:15.365Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT 1 FROM M_Product_PrintFormat pf where pf.M_Product_ID=@M_Product_ID@ AND AD_PrintFormat.AD_PrintFormat_ID = pf.AD_PrintFormat_ID)',Updated=TO_TIMESTAMP('2021-04-05 09:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540537
;
