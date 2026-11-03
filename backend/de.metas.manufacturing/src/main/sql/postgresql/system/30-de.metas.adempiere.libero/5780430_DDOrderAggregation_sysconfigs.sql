-- Run mode: SWING_CLIENT

-- SysConfig Name: DDOrderAggregation.header.bySalesOrderId
-- SysConfig Value: Y
-- 2025-12-10T15:47:44.193Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541780,'O',TO_TIMESTAMP('2025-12-10 15:47:43.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'If enabled, prevents DD_Order_Candidates of different sales orders from ending up in the same DD_Order.','D','Y','DDOrderAggregation.header.bySalesOrderId',TO_TIMESTAMP('2025-12-10 15:47:43.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y')
;

-- SysConfig Name: DDOrderAggregation.header.byPPOrderRef
-- SysConfig Value: Y
-- 2025-12-10T15:48:19.709Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541781,'O',TO_TIMESTAMP('2025-12-10 15:48:19.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'If enabled, prevents DD_Order_Candidates of different manufacturing orders from ending up in the same DD_Order.','D','Y','DDOrderAggregation.header.byPPOrderRef',TO_TIMESTAMP('2025-12-10 15:48:19.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y')
;

-- SysConfig Name: DDOrderAggregation.line.bySalesOrderLineId
-- SysConfig Value: Y
-- 2025-12-10T15:49:13.310Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541782,'O',TO_TIMESTAMP('2025-12-10 15:49:13.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'If enabled, prevents DD_Order_Candidates of different sales order lines from ending up in the same DD_OrderLine.','D','Y','DDOrderAggregation.line.bySalesOrderLineId',TO_TIMESTAMP('2025-12-10 15:49:13.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y')
;

