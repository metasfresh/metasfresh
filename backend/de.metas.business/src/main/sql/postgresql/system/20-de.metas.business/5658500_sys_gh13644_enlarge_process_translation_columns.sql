-- 2022-09-23T12:33:02.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2022-09-29 15:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2852
;

-- 2022-09-23T12:33:03.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('AD_Process_TRL','Name','VARCHAR(600)',null,null)
;
