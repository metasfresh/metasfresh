-- 2021-06-08T13:23:51.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE public.C_DocType ADD COLUMN IsExcludeFromCommision CHAR(1) DEFAULT ''N'' CHECK (IsExcludeFromCommision IN (''Y'',''N'')) NOT NULL')
;




