-- 2017-11-27T16:40:14.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN IsRangeFilter CHAR(1) DEFAULT ''N'' CHECK (IsRangeFilter IN (''Y'',''N'')) NOT NULL')
;

-- 2017-11-27T16:41:03.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN IsShowFilterIncrementButtons CHAR(1) DEFAULT ''N'' CHECK (IsShowFilterIncrementButtons IN (''Y'',''N'')) NOT NULL')
;

-- 2017-11-27T16:41:34.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN FilterDefaultValue VARCHAR(255)')
;

