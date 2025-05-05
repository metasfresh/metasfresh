-- 2022-08-26T07:34:35.442Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541480,'S',TO_TIMESTAMP('2022-08-26 10:34:35','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.serviceprovider.everhour.bpartnerUserImport',TO_TIMESTAMP('2022-08-26 10:34:35','YYYY-MM-DD HH24:MI:SS'),100,'2155894')
;

-- 2022-08-26T07:34:40.823Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Business partner id under which the Everhour users will be imported',Updated=TO_TIMESTAMP('2022-08-26 10:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541480
;

-- 2022-08-26T07:34:44.043Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-08-26 10:34:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541480
;

-- 2022-08-29T08:20:52.522Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='de.metas.serviceprovider',Updated=TO_TIMESTAMP('2022-08-29 11:20:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541480
;