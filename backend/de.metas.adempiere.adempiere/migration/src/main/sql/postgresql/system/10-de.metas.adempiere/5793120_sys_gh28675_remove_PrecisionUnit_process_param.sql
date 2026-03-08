-- gh#28675: Remove Forecast_PrecisionUnit from process parameters
-- The time unit only makes sense together with frequency and buffer time,
-- which are always per-product (PP_Product_Planning). Overriding just the
-- time unit at process level would produce wrong results.

UPDATE AD_Process_Para
SET IsActive='N',
    Updated=TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'),
    UpdatedBy=100
WHERE AD_Process_Para_ID=543146;
