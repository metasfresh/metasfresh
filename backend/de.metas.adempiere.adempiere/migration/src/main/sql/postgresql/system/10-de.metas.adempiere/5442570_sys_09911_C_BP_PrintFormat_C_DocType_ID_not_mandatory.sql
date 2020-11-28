-- 15.03.2016 15:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 15:08:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551613
;

-- 15.03.2016 15:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bp_printformat','C_DocType_ID','NUMERIC(10)',null,'NULL')
;

-- 15.03.2016 15:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bp_printformat','C_DocType_ID',null,'NULL',null)
;

