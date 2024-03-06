-- 2021-11-12T13:27:10.522076600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-11-12 15:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578376
;

-- 2021-11-12T13:27:16.427611200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_term','C_Flatrate_Term_Master_ID','NUMERIC(10)',null,null)
;

-- 2021-11-12T13:27:16.431221300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_term','C_Flatrate_Term_Master_ID',null,'NULL',null)
;

