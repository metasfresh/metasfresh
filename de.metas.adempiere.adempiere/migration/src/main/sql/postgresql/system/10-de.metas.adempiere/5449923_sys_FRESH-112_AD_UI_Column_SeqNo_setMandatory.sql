-- 30.08.2016 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-08-30 18:36:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555024
;

-- 30.08.2016 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ui_column','SeqNo','NUMERIC(10)',null,null)
;

-- 30.08.2016 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ui_column','SeqNo',null,'NOT NULL',null)
;

