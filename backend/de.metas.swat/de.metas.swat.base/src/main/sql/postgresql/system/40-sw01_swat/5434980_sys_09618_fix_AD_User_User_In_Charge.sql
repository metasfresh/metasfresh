
-- 08.12.2015 12:58
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=110,Updated=TO_TIMESTAMP('2015-12-08 12:58:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552868
;

-- 08.12.2015 14:38
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540314,'AD_User.AD_Client_ID IN (@AD_Client_ID@, 0) AND AD_User.AD_Org_ID IN (@AD_Org_ID@, 0)',TO_TIMESTAMP('2015-12-08 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Users with the same AD_Client_ID/AD_Org_ID or 0','D','Y','AD_User System, Client, Org','S',TO_TIMESTAMP('2015-12-08 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.12.2015 14:38
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540314,Updated=TO_TIMESTAMP('2015-12-08 14:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100,IsAutocomplete='Y' WHERE AD_Column_ID=552868
;



-- 08.12.2015 14:39
-- URL zum Konzept
--UPDATE AD_User SET AD_User_InCharge_ID=100, NotificationType='O',Updated=TO_TIMESTAMP('2015-12-08 14:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540012
--;
