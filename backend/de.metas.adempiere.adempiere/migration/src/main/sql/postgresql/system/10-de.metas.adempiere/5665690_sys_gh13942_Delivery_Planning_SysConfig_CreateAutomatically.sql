-- 2022-11-23T10:22:51.262Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541577,'O',TO_TIMESTAMP('2022-11-23 12:22:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Delivery_Planning_CreateAutomatically',TO_TIMESTAMP('2022-11-23 12:22:50','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2022-11-23T10:30:11.954Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically',Updated=TO_TIMESTAMP('2022-11-23 12:30:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541577
;

