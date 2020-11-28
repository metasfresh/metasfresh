

-- 2018-05-09T13:20:06.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DerKurier_Shipper_Config','ALTER TABLE public.DerKurier_Shipper_Config ADD COLUMN CollectorCode VARCHAR(250)')
;

-- 2018-05-09T13:20:16.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DerKurier_Shipper_Config','ALTER TABLE public.DerKurier_Shipper_Config ADD COLUMN CustomerCode VARCHAR(250)')
;

