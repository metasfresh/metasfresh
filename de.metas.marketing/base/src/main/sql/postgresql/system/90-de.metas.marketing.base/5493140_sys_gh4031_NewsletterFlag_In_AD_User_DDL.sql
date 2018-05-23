
-- 2018-05-10T14:07:07.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN IsNewsletter CHAR(1) DEFAULT ''N'' CHECK (IsNewsletter IN (''Y'',''N'')) NOT NULL')
;

