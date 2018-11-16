
-- 2018-10-12T18:22:49.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Window','ALTER TABLE public.AD_Window ADD COLUMN AD_Element_ID NUMERIC(10)')
;

-- 2018-10-12T18:22:49.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Window ADD CONSTRAINT ADElement_ADWindow FOREIGN KEY (AD_Element_ID) REFERENCES public.AD_Element DEFERRABLE INITIALLY DEFERRED
;

