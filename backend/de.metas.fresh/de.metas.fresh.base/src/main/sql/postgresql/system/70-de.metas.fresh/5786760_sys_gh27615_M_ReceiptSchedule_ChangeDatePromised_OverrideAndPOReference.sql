-- Run mode: SWING_CLIENT

-- Process: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference)
-- ParameterName: DatePromised_Override
-- 2026-02-04T15:30:13.621Z
UPDATE AD_Process_Para SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2026-02-04 15:30:13.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543058
;

