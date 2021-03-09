-- 2021-02-05T21:14:53.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL, AD_Reference_ID=30,Updated=TO_TIMESTAMP('2021-02-05 23:14:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=14588
;

-- 2021-02-05T21:14:55.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_acctschema_element','AD_Column_ID','NUMERIC(10)',null,null)
;

