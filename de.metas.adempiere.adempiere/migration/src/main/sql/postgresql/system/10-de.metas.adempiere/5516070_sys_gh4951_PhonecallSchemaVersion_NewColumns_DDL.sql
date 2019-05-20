

-- 2019-03-13T17:22:56.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Phonecall_Schema_Version','ALTER TABLE public.C_Phonecall_Schema_Version ADD COLUMN IsCancelPhonecallDay CHAR(1) DEFAULT ''N'' CHECK (IsCancelPhonecallDay IN (''Y'',''N'')) NOT NULL')
;


-- 2019-03-13T17:35:40.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Phonecall_Schema_Version','ALTER TABLE public.C_Phonecall_Schema_Version ADD COLUMN IsMovePhonecallDay CHAR(1) DEFAULT ''N'' CHECK (IsMovePhonecallDay IN (''Y'',''N'')) NOT NULL')
;

