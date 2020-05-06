-- 2018-05-21T19:12:15.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN Template_Tab_ID NUMERIC(10)')
;

-- 2018-05-21T19:12:15.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Tab ADD CONSTRAINT TemplateTab_ADTab FOREIGN KEY (Template_Tab_ID) REFERENCES public.AD_Tab DEFERRABLE INITIALLY DEFERRED
;

