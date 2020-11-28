
-- 2018-10-12T18:58:42.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Menu','ALTER TABLE public.AD_Menu ADD COLUMN AD_Element_ID NUMERIC(10)')
;

-- 2018-10-12T18:58:42.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Menu ADD CONSTRAINT ADElement_ADMenu FOREIGN KEY (AD_Element_ID) REFERENCES public.AD_Element DEFERRABLE INITIALLY DEFERRED
;

