-- Run mode: SWING_CLIENT

-- SysConfig Name: DDOrderAggregation.header.byProductId
-- Seeds the on-switch (default 'N') for keeping DD_Order_Candidates of different products in separate DD_Orders.
-- When 'Y', M_Product_ID becomes part of the DD_Order header aggregation key, so candidates of different
-- products no longer share a DD_Order (each product gets its own order) instead of one big multi-product order.
-- Default 'N' preserves today's behaviour; enable per instance via the customer repo.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541832 /*From ID Server*/,'O',TO_TIMESTAMP('2026-07-07 10:40:00','YYYY-MM-DD HH24:MI:SS'),100,'If enabled, prevents DD_Order_Candidates of different products from ending up in the same DD_Order.','D','Y','DDOrderAggregation.header.byProductId',TO_TIMESTAMP('2026-07-07 10:40:00','YYYY-MM-DD HH24:MI:SS'),100,'N')
;
