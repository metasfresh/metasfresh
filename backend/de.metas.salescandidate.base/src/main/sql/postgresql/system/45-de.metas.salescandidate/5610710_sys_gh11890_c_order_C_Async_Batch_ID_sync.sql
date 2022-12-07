
-- 2021-10-26T09:34:27.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2021-10-26 12:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575255
;

-- 2021-10-26T09:34:37.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','C_Async_Batch_ID','NUMERIC(10)',null,null)
;

