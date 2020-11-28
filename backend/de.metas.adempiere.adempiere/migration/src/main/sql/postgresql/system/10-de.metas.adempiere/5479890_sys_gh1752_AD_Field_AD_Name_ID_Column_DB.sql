



-- 2017-12-07T17:57:03.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN AD_Name_ID NUMERIC(10)')
;

-- 2017-12-07T17:57:03.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Field ADD CONSTRAINT ADName_ADField FOREIGN KEY (AD_Name_ID) REFERENCES public.AD_Element DEFERRABLE INITIALLY DEFERRED
;

