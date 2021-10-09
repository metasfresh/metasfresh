-- 2021-06-01T15:06:11.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=1000000,Updated=TO_TIMESTAMP('2021-06-01 18:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573790
;

-- 2021-06-01T15:06:14.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_request_audit_log','Logmessage','VARCHAR(1000000)',null,null)
;

-- 2021-06-01T15:06:33.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-06-01 18:06:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573799
;

-- 2021-06-01T15:07:01.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2021-06-01 18:07:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573800
;

update api_audit_config set isinvokerwaitsforresult = 'Y' where api_audit_config_id = 540001
;
