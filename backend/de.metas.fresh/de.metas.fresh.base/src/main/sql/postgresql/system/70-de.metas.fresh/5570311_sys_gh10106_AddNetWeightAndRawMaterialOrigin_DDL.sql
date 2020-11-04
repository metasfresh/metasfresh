-- 2019-01-28T11:24:44.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN NetWeight NUMERIC')
;




-- 2019-01-23T18:41:37.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN RawMaterialOrigin_ID NUMERIC(10)')
;

-- 2019-01-23T18:41:38.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product ADD CONSTRAINT RawMaterialOrigin_MProduct FOREIGN KEY (RawMaterialOrigin_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;


