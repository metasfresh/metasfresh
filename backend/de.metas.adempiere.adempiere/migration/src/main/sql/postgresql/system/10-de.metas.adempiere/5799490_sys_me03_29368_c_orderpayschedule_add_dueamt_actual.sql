-- 2026-04-24 https://github.com/metasfresh/me03/issues/29368
-- Iter 2: new physical column on C_OrderPaySchedule capturing the actual allocated amount
--         on the LC step, independent of DueAmt (= GrandTotal × break%).
ALTER TABLE C_OrderPaySchedule ADD COLUMN IF NOT EXISTS DueAmt_Actual numeric DEFAULT NULL;
