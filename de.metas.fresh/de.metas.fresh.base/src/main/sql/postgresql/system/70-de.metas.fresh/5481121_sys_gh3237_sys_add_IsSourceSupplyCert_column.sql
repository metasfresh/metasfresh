
-- 2017-12-21T13:51:28.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsSourceSupplyCert CHAR(1) DEFAULT ''N'' CHECK (IsSourceSupplyCert IN (''Y'',''N'')) NOT NULL')
;

