
-- 2021-10-08T04:11:26.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2021-10-08 06:11:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574239
;

ALTER TABLE api_request_audit_log
    DROP CONSTRAINT IF EXISTS adtable_apirequestauditlog
;
