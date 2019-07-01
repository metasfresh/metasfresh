
-- 2019-06-26T11:51:11.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN CustomsTariff NUMERIC')
;







------------------
ALTER TABLE M_Product DROP COLUMN CustomsTariff;


-- 2019-06-26T15:33:18.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN CustomsTariff VARCHAR(250)')
;

