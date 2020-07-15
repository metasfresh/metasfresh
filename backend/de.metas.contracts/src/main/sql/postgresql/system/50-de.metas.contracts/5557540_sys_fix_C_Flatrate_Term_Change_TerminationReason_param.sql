
--
-- it's correct on my local (vanuilla-DB) but at least one long-time-user has in wrong in theirs
--
-- 2020-04-20T17:00:53.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Process_Para SET FieldLength=3,Updated=TO_TIMESTAMP('2020-04-20 19:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541232
-- ;

-- 2020-04-20T17:16:12.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TerminationReason',Updated=TO_TIMESTAMP('2020-04-20 19:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541232
;

