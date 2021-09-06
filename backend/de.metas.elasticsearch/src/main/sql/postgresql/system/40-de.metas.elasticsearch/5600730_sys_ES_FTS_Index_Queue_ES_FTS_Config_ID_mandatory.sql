-- 2021-08-11T12:29:03.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-08-11 15:29:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575302
;

-- 2021-08-11T12:29:04.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('es_fts_index_queue','ES_FTS_Config_ID','NUMERIC(10)',null,null)
;

-- 2021-08-11T12:29:04.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('es_fts_index_queue','ES_FTS_Config_ID',null,'NOT NULL',null)
;

