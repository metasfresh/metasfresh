-- 2020-12-16T03:21:07.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN IsShowFilterInline CHAR(1) DEFAULT ''N'' CHECK (IsShowFilterInline IN (''Y'',''N'')) NOT NULL')
;

