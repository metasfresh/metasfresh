-- Run mode: SWING_CLIENT

-- SysConfig Name: de.metas.OrderLine.MaxQtyLU
-- SysConfig Value: 100
-- 2025-08-04T07:37:44.691Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541770,'S',TO_TIMESTAMP('2025-08-04 07:37:44.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','de.metas.OrderLine.MaxQtyLU',TO_TIMESTAMP('2025-08-04 07:37:44.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'100')
;

-- SysConfig Name: de.metas.OrderLine.MaxLUQty
-- SysConfig Value: 100
-- 2025-08-04T07:40:46.325Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='de.metas.OrderLine.MaxLUQty',Updated=TO_TIMESTAMP('2025-08-04 07:40:46.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541770
;

