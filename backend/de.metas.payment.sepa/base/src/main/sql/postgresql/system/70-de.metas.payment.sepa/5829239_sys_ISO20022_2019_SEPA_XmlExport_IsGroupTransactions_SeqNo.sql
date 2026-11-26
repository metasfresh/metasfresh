-- Fixes failure of: 5829240_sys_ISO20022_2019_SEPA_Export_SEPA_Protocol_param.sql
-- That script adds the "SEPA_Protocol" parameter to the SEPA XML export process (AD_Process_ID=540461) with SeqNo 20.
-- On branches that also carry the "IsGroupTransactions" parameter (SeqNo 20), the unique constraint on (AD_Process_ID, SeqNo) would make it fail.
-- So the "IsGroupTransactions" parameter is moved to SeqNo 30, after "SEPA Protocol". On branches without that parameter, this updates nothing.
UPDATE AD_Process_Para SET SeqNo=30, Updated=TO_TIMESTAMP('2026-10-19 23:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_Para_ID=543086 AND SeqNo=20
;
