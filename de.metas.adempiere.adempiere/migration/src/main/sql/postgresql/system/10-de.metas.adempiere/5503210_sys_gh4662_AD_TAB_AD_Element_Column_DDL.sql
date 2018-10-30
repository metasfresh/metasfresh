

-- 2018-10-12T13:55:32.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN AD_Element_ID NUMERIC(10)')
;

-- 2018-10-12T13:55:33.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Tab ADD CONSTRAINT ADElement_ADTab FOREIGN KEY (AD_Element_ID) REFERENCES public.AD_Element DEFERRABLE INITIALLY DEFERRED
;

