-- Run mode: SWING_CLIENT

-- Column: DHL_Shipper_Config.ApplicationToken -> display type Password
UPDATE AD_Column SET AD_Reference_ID=43, Updated=TO_TIMESTAMP('2026-11-07 10:00:00.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=569139
;

-- Column: DHL_Shipper_Config.Signature -> display type Password
UPDATE AD_Column SET AD_Reference_ID=43, Updated=TO_TIMESTAMP('2026-11-07 10:00:00.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=569142
;
