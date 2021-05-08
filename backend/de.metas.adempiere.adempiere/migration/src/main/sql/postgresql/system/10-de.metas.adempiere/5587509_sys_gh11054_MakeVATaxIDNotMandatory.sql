-- 2021-05-07T11:15:53.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-05-07 14:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573813
;

-- 2021-05-07T11:16:19.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_fiscal_representation','VATaxID','VARCHAR(60)',null,null)
;

-- 2021-05-07T11:16:19.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_fiscal_representation','VATaxID',null,'NULL',null)
;

