
-- 2019-02-18T14:16:06.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version','C_Phonecall_Schema_ID','NUMERIC(10)',null,null)
;

-- 2019-02-18T14:16:06.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version','C_Phonecall_Schema_ID',null,'NOT NULL',null)
;

-- 2019-02-18T14:16:15.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version','IsMonthly','CHAR(1)',null,'N')
;

-- 2019-02-18T14:16:15.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Phonecall_Schema_Version SET IsMonthly='N' WHERE IsMonthly IS NULL
;

-- 2019-02-18T14:16:15.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version','IsMonthly',null,'NOT NULL',null)
;

-- 2019-02-18T14:16:19.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version','IsWeekly','CHAR(1)',null,'N')
;

-- 2019-02-18T14:16:19.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Phonecall_Schema_Version SET IsWeekly='N' WHERE IsWeekly IS NULL
;

-- 2019-02-18T14:16:19.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version','IsWeekly',null,'NOT NULL',null)
;

