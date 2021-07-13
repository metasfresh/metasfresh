-- 2021-02-15T15:13:50.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2021-02-15 17:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53912
;

-- 2021-02-15T15:14:20.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_order','Processed','CHAR(1)',null,'N')
;

-- 2021-02-15T15:14:21.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE DD_Order SET Processed='N' WHERE Processed IS NULL
;

