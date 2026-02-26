-- Run mode: SWING_CLIENT

-- Column: M_ForecastLine.DatePromised
-- 2025-06-30T07:09:42.664Z
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2025-06-30 07:09:42.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53412
;

-- 2025-06-30T07:09:44.844Z
INSERT INTO t_alter_column values('m_forecastline','DatePromised','TIMESTAMP WITH TIME ZONE',null,null)
;

