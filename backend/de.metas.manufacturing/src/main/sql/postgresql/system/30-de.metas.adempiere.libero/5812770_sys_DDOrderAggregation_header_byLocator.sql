-- Run mode: SWING_CLIENT

-- SysConfig Name: DDOrderAggregation.header.byLocatorFrom
-- SysConfig Value: N
-- When 'Y', the (resolved) source locator is part of the DD_Order header aggregation key -> one source
-- locator per DD_Order. Default 'N' (header does not split by source locator). The DD_OrderLine always
-- carries the resolved source locator regardless of this flag (line-level aggregation is unconditional).
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541834 /*From ID Server*/,'O',TO_TIMESTAMP('2026-07-08 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'If enabled, the source locator is part of the DD_Order header aggregation key (one source locator per DD_Order). The DD_OrderLine always carries the resolved locator regardless.','D','Y','DDOrderAggregation.header.byLocatorFrom',TO_TIMESTAMP('2026-07-08 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- SysConfig Name: DDOrderAggregation.header.byLocatorTo
-- SysConfig Value: N
-- When 'Y', the (resolved) target locator is part of the DD_Order header aggregation key -> one target
-- locator per DD_Order. Default 'N' (header does not split by target locator). The DD_OrderLine always
-- carries the resolved target locator regardless of this flag (line-level aggregation is unconditional).
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541835 /*From ID Server*/,'O',TO_TIMESTAMP('2026-07-08 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'If enabled, the target locator is part of the DD_Order header aggregation key (one target locator per DD_Order). The DD_OrderLine always carries the resolved locator regardless.','D','Y','DDOrderAggregation.header.byLocatorTo',TO_TIMESTAMP('2026-07-08 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N')
;
