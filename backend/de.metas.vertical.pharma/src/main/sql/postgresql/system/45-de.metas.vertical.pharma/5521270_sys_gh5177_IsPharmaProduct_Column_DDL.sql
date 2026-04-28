-- 2019-05-09T16:58:58.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IsPharmaProduct CHAR(1) DEFAULT ''N'' CHECK (IsPharmaProduct IN (''Y'',''N'')) NOT NULL')
;
