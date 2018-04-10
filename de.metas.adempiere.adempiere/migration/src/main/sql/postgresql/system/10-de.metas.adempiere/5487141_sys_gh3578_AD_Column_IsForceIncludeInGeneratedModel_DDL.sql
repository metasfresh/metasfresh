-- 2018-02-28T11:28:04.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN IsForceIncludeInGeneratedModel CHAR(1) DEFAULT ''N'' CHECK (IsForceIncludeInGeneratedModel IN (''Y'',''N'')) NOT NULL')
;

