-- Run mode: SWING_CLIENT

-- IDs allocated from idserver: AD_MigrationScript=5812710, AD_SysConfig_ID=541833
-- SysConfig Name: DDOrderAggregation.header.byLocatorId
-- SysConfig Value: N
-- When 'Y', the source/target locator is part of the DD_Order header+line aggregation key (one DD_Order per from/to locator move). Default 'N'.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541833 /*From ID Server*/,'O',TO_TIMESTAMP('2026-07-08 11:30:00','YYYY-MM-DD HH24:MI:SS'),100,'When enabled, prevents DD_Order_Candidates of different source/target locators from ending up in the same DD_Order.','D','Y','DDOrderAggregation.header.byLocatorId',TO_TIMESTAMP('2026-07-08 11:30:00','YYYY-MM-DD HH24:MI:SS'),100,'N')
;
