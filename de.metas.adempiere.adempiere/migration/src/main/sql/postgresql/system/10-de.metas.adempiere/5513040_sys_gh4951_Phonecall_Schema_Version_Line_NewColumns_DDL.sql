
-- 2019-02-18T15:27:19.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2019-02-18T15:27:19.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','C_BPartner_ID',null,'NOT NULL',null)
;

-- 2019-02-18T15:27:23.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- 2019-02-18T15:27:23.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','C_BPartner_Location_ID',null,'NOT NULL',null)
;

-- 2019-02-18T15:27:34.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','C_Phonecall_Schema_ID','NUMERIC(10)',null,null)
;

-- 2019-02-18T15:27:34.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','C_Phonecall_Schema_ID',null,'NOT NULL',null)
;

-- 2019-02-18T15:27:38.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','C_Phonecall_Schema_Version_ID','NUMERIC(10)',null,null)
;

-- 2019-02-18T15:27:38.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','C_Phonecall_Schema_Version_ID',null,'NOT NULL',null)
;


-- 2019-02-18T15:29:19.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Phonecall_Schema_Version_Line','ALTER TABLE public.C_Phonecall_Schema_Version_Line ADD COLUMN PhonecallTimeMin TIMESTAMP WITHOUT TIME ZONE')
;



-- 2019-02-18T15:29:38.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Phonecall_Schema_Version_Line','ALTER TABLE public.C_Phonecall_Schema_Version_Line ADD COLUMN PhonecallTimeMax TIMESTAMP WITHOUT TIME ZONE')
;

-- 2019-02-18T15:30:02.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_phonecall_schema_version_line','PhonecallTimeMax','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

