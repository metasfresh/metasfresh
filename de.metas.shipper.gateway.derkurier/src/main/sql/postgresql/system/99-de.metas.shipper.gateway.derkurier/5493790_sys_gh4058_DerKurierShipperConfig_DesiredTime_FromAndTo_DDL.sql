-- 2018-05-17T12:58:40.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DerKurier_Shipper_Config','ALTER TABLE public.DerKurier_Shipper_Config ADD COLUMN DK_DesiredDeliveryTime_From TIMESTAMP WITHOUT TIME ZONE')
;

-- 2018-05-17T12:58:45.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DerKurier_Shipper_Config','ALTER TABLE public.DerKurier_Shipper_Config ADD COLUMN DK_DesiredDeliveryTime_To TIMESTAMP WITHOUT TIME ZONE')
;

