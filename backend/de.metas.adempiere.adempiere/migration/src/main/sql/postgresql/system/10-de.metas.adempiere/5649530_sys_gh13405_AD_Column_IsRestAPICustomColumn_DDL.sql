-- 2022-08-04T013:28:32.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN IsRestAPICustomColumn CHAR(1) DEFAULT ''N'' CHECK (IsRestAPICustomColumn IN (''Y'',''N'')) NOT NULL')
;
