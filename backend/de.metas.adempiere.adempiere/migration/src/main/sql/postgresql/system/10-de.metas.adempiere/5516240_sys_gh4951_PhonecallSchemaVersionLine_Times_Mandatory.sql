-- 2019-03-15T15:41:36.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-03-15 15:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564189
;

-- 2019-03-15T15:41:39.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-03-15 15:41:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564188
;




-- 2019-03-15T15:43:01.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','PhonecallTimeMin','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2019-03-15T15:43:01.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','PhonecallTimeMin',null,'NOT NULL',null)
;





-- 2019-03-15T15:43:19.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','PhonecallTimeMax','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2019-03-15T15:43:19.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','PhonecallTimeMax',null,'NOT NULL',null)
;

