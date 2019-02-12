-- 2019-01-30T13:15:36.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_QuickInput','ALTER TABLE public.C_BPartner_QuickInput ADD COLUMN IsNewsletter CHAR(1) DEFAULT ''N'' CHECK (IsNewsletter IN (''Y'',''N'')) NOT NULL')
;
