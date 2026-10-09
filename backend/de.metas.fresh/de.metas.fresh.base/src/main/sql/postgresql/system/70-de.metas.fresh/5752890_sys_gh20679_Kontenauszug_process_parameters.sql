-- Run mode: SWING_CLIENT

-- Name: NonSummary_C_ElementValue_for_C_AcctSchema_ID
-- 2025-04-25T07:04:36.568Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540721,'exists (select 1 from c_acctschema_element cae where cae.c_acctschema_id=@C_AcctSchema_ID/0@ and cae.elementtype=''AC'' and cae.c_element_id=c_elementvalue.c_element_id AND c_elementvalue.issummary=''N'')',TO_TIMESTAMP('2025-04-25 10:04:36.254','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','NonSummary_C_ElementValue_for_C_AcctSchema_ID','S',TO_TIMESTAMP('2025-04-25 10:04:36.254','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account_ID
-- 2025-04-25T07:04:59.659Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540721,Updated=TO_TIMESTAMP('2025-04-25 10:04:59.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542758
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account2_ID
-- 2025-04-25T07:07:41.395Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540721,Updated=TO_TIMESTAMP('2025-04-25 10:07:41.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542946
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account3_ID
-- 2025-04-25T07:07:50.247Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540721,Updated=TO_TIMESTAMP('2025-04-25 10:07:50.247','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542947
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account4_ID
-- 2025-04-25T07:07:56.377Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540721,Updated=TO_TIMESTAMP('2025-04-25 10:07:56.377','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542948
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account5_ID
-- 2025-04-25T07:08:02.158Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540721,Updated=TO_TIMESTAMP('2025-04-25 10:08:02.158','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542949
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account6_ID
-- 2025-04-25T07:08:07.856Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540721,Updated=TO_TIMESTAMP('2025-04-25 10:08:07.856','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542950
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account7_ID
-- 2025-04-25T07:08:13.488Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540721,Updated=TO_TIMESTAMP('2025-04-25 10:08:13.488','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542951
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account8_ID
-- 2025-04-25T07:08:19.368Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540721,Updated=TO_TIMESTAMP('2025-04-25 10:08:19.368','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542952
;

