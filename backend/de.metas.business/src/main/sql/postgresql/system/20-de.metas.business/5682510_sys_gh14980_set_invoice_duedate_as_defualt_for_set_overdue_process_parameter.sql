-- Process: C_Invoice_OverrideDueDate(de.metas.invoice.process.C_Invoice_OverrideDueDate)
-- ParameterName: OverrideDueDate
-- 2023-03-24T10:20:00.106Z
UPDATE AD_Process_Para SET DefaultValue='@DueDate@',Updated=TO_TIMESTAMP('2023-03-24 11:20:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542539
;
