
-- 2018-05-09T13:10:20.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DerKurier_DeliveryOrderLine','ALTER TABLE public.DerKurier_DeliveryOrderLine ADD COLUMN CollectorCode VARCHAR(250)')
;

-- 2018-05-09T13:10:33.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DerKurier_DeliveryOrderLine','ALTER TABLE public.DerKurier_DeliveryOrderLine ADD COLUMN CustomerCode VARCHAR(250)')
;

