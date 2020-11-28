-- 2019-03-22T13:42:53.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=80,Updated=TO_TIMESTAMP('2019-03-22 13:42:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564205
;

-- 2019-03-22T13:42:55.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dataentry_section','SectionName','VARCHAR(80)',null,null)
;

-- 2019-03-22T13:43:08.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=80,Updated=TO_TIMESTAMP('2019-03-22 13:43:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563960
;

-- 2019-03-22T13:43:09.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dataentry_group','TabName','VARCHAR(80)',null,null)
;

-- 2019-03-22T13:43:21.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=80,Updated=TO_TIMESTAMP('2019-03-22 13:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563961
;

-- 2019-03-22T13:43:21.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dataentry_subgroup','TabName','VARCHAR(80)',null,null)
;

