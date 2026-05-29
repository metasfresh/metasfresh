-- Run mode: SWING_CLIENT

-- SysConfig Name: mobileui.frontend.barcodeScanner.showInputVideo
-- SysConfig Value: Y
-- 2025-10-21T08:04:27.443Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541774,'S',TO_TIMESTAMP('2025-10-21 08:04:26.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','mobileui.frontend.barcodeScanner.showInputVideo',TO_TIMESTAMP('2025-10-21 08:04:26.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.useCamera
-- SysConfig Value: Y
-- 2025-10-21T08:05:22.024Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='mobileui.frontend.barcodeScanner.useCamera',Updated=TO_TIMESTAMP('2025-10-21 08:05:22.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541774
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.useCamera
-- SysConfig Value: N
-- SysConfig Value (old): Y
-- 2025-10-21T08:05:52.376Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='N',Updated=TO_TIMESTAMP('2025-10-21 08:05:52.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541774
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.useCamera
-- SysConfig Value: Y
-- SysConfig Value (old): N
-- 2025-10-21T08:06:04.154Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='Y',Updated=TO_TIMESTAMP('2025-10-21 08:06:04.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541774
;

