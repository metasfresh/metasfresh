-- Value: SAP_Export_FactAcct_GL
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2023-03-10T07:27:06.477Z
UPDATE AD_Process SET JSONPath='/fact_acct?ad_table_id=eq.@AD_Table_ID/-1@&record_id=eq.@Record_ID/-1@', Name='SAP_Export_FactAcct_GL', Value='SAP_Export_FactAcct_GL',Updated=TO_TIMESTAMP('2023-03-10 09:27:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585230
;

-- 2023-03-10T07:27:06.479Z
UPDATE AD_Process_Trl trl SET Name='SAP_Export_FactAcct_GL' WHERE AD_Process_ID=585230 AND AD_Language='de_DE'
;

