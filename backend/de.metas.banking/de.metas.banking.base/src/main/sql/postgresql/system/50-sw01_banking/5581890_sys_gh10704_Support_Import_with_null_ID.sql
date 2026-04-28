-- 2021-03-11T14:27:57.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2021-03-11 16:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573001
;

-- 2021-03-11T14:28:00.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bankstatement','DebitorOrCreditorId','NUMERIC(10)',null,null)
;

-- 2021-03-11T14:28:00.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bankstatement','DebitorOrCreditorId',null,'NULL',null)
;

