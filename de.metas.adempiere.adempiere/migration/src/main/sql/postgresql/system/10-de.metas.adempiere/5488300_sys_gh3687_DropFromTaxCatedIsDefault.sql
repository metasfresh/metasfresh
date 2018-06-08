delete from exp_formatline WHERE AD_Column_ID=3885;

-- 2018-03-14T16:16:51.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=3885
;

-- 2018-03-14T16:16:51.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=3885
;


SELECT public.db_alter_table('C_TaxCategory','ALTER TABLE public.C_TaxCategory DROP COLUMN IsDefault')
;

DROP TRIGGER IF EXISTS isdefault_tg ON public.c_taxcategory;
DROP FUNCTION public.isdefault_tgfn();

