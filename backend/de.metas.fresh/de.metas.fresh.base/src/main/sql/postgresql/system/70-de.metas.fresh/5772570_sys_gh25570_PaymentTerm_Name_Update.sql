-- Run mode: SWING_CLIENT

-- Column: C_PaymentTerm.Name
-- 2025-10-06T19:44:24.120Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-10-06 19:44:24.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2035
;

-- 2025-10-06T19:44:52.756Z
INSERT INTO t_alter_column values('c_paymentterm','Name','VARCHAR(255)',null,null)
;

-- Column: C_PaymentTerm_Trl.Name
-- 2025-10-06T19:49:19.843Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-10-06 19:49:19.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=3174
;

-- 2025-10-06T19:52:10.593Z
INSERT INTO t_alter_column values('c_paymentterm_trl','Name','VARCHAR(255)',null,null)
;

