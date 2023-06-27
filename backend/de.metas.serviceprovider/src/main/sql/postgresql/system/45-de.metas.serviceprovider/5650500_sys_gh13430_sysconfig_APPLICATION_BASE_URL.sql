--- 2022-08-11T10:07:39.878Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541470,'S',TO_TIMESTAMP('2022-08-11 13:07:39','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','APPLICATION_BASE_URL',TO_TIMESTAMP('2022-08-11 13:07:39','YYYY-MM-DD HH24:MI:SS'),100,'http://localhost:8282')
;

-- 2022-08-11T10:07:48.126Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-08-11 13:07:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541470
;

-- 2022-08-11T10:07:50.377Z
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2022-08-11 13:07:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541470
;

