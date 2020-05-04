-- 2020-01-30T08:28:32.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN IsFacetFilter CHAR(1) DEFAULT ''N'' CHECK (IsFacetFilter IN (''Y'',''N'')) NOT NULL')
;

-- 2020-01-30T08:29:10.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN MaxFacetsToFetch NUMERIC(10) DEFAULT 0')
;

-- 2020-01-30T11:49:49.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN FacetFilterSeqNo NUMERIC(10)')
;

