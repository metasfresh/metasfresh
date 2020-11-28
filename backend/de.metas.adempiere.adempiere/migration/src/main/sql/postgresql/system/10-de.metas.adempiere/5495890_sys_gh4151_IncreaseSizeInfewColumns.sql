-- 2018-06-15T16:44:59.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=200,Updated=TO_TIMESTAMP('2018-06-15 16:44:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560155
;

-- 2018-06-15T16:45:01.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner','AggregationName','VARCHAR(200)',null,null)
;

-- 2018-06-15T17:29:45.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=100,Updated=TO_TIMESTAMP('2018-06-15 17:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=13789
;

-- 2018-06-15T17:29:47.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_job','Name','VARCHAR(100)',null,null)
;

-- 2018-06-15T17:36:31.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2018-06-15 17:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557896
;

-- 2018-06-15T17:36:33.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner','InvoiceSchedule','VARCHAR(255)',null,null)
;
