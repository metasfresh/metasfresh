
-- 2018-11-08T18:05:25.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Element_Link','ALTER TABLE public.AD_Element_Link ADD COLUMN AD_Tab_ID NUMERIC(10)')
;

-- 2018-11-08T18:05:25.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Element_Link ADD CONSTRAINT ADTab_ADElementLink FOREIGN KEY (AD_Tab_ID) REFERENCES public.AD_Tab DEFERRABLE INITIALLY DEFERRED
;


-- 2018-11-08T18:05:55.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Element_Link','ALTER TABLE public.AD_Element_Link ADD COLUMN AD_Field_ID NUMERIC(10)')
;

-- 2018-11-08T18:05:55.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Element_Link ADD CONSTRAINT ADField_ADElementLink FOREIGN KEY (AD_Field_ID) REFERENCES public.AD_Field DEFERRABLE INITIALLY DEFERRED
;

