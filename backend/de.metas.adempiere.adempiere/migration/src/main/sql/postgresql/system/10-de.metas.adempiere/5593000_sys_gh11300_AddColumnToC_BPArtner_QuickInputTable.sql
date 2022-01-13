-- 2021-06-16T06:37:32.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_QuickInput','ALTER TABLE public.C_BPartner_QuickInput ADD COLUMN ExcludeFromPromotions CHAR(1) DEFAULT ''N'' CHECK (ExcludeFromPromotions IN (''Y'',''N'')) NOT NULL')
;

-- 2021-06-16T06:40:25.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_QuickInput','ALTER TABLE public.C_BPartner_QuickInput ADD COLUMN Referrer VARCHAR(255)')
;

