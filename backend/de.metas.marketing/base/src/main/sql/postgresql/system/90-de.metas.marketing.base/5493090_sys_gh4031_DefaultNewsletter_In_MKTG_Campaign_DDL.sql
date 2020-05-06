

-- 2018-05-09T18:23:27.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MKTG_Campaign','ALTER TABLE public.MKTG_Campaign ADD COLUMN IsDefaultNewsletter CHAR(1) DEFAULT ''N'' CHECK (IsDefaultNewsletter IN (''Y'',''N'')) NOT NULL')
;

