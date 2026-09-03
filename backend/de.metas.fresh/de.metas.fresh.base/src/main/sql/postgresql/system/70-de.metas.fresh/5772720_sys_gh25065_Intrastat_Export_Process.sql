-- Run mode: SWING_CLIENT

-- Name: C_Period of Year (Optional)
-- 2025-10-07T14:28:43.409Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540749,'(@C_Year_ID@=0 OR C_Period.C_Year_ID=@C_Year_ID@)',TO_TIMESTAMP('2025-10-07 14:28:42.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','C_Period of Year (Optional)','S',TO_TIMESTAMP('2025-10-07 14:28:42.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Process: Intrastat_Export(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_Period_ID
-- 2025-10-07T14:28:59.546Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540749,Updated=TO_TIMESTAMP('2025-10-07 14:28:59.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543005
;

-- Name: C_Period of Year (Optional)
-- 2025-10-07T15:37:31.935Z
UPDATE AD_Val_Rule SET Code='(CASE WHEN @C_Year_ID@>0 THEN C_Period.C_Year_ID=@C_Year_ID@ ELSE C_Period.C_Year_ID IN(SELECT y.C_Year_ID FROM C_Year y WHERE y.FiscalYear::Numeric >= 2025) END)',Updated=TO_TIMESTAMP('2025-10-07 15:37:31.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540749
;

