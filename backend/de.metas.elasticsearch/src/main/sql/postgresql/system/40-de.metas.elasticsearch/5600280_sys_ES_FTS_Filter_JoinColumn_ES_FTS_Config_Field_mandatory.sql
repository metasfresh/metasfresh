-- 2021-08-06T22:10:40.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-08-07 01:10:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575296
;

-- 2021-08-06T22:10:42.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('es_fts_filter_joincolumn','ES_FTS_Config_Field_ID','NUMERIC(10)',null,null)
;

-- 2021-08-06T22:10:42.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('es_fts_filter_joincolumn','ES_FTS_Config_Field_ID',null,'NOT NULL',null)
;

