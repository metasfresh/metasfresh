-- ad_table_id = 542193 - C_InterimInvoice_FlatrateTerm
-- ad_table_id = 542194 - C_InterimInvoice_FlatrateTerm_Line
-- ad_table_id = 542188 - C_Interim_Invoice_Settings

SELECT backup_table('AD_PInstance_Log')
;

DELETE
FROM AD_PInstance_Log
WHERE ad_table_id IN (542193, 542194, 542188)
;

SELECT backup_table('ModCntr_Log_Status')
;

UPDATE ModCntr_Log_Status
SET AD_Issue_ID = NULL,
    Updated     = TO_TIMESTAMP(' 2023-09-18 10:57:55.314', 'YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy   = 100
WHERE ad_table_id IN (542193, 542194, 542188)
;

SELECT backup_table('AD_Issue')
;

DELETE
FROM AD_Issue
WHERE ad_table_id IN (542193, 542194, 542188)
;

