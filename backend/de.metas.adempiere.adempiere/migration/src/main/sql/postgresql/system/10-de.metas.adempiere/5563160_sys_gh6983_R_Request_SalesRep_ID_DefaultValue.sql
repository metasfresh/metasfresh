
-- 2020-07-08T15:44:01.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@#AD_User_ID/-1@',Updated=TO_TIMESTAMP('2020-07-08 18:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5432
;

-- 2020-07-08T15:44:04.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('r_request','SalesRep_ID','NUMERIC(10)',null,null)
;