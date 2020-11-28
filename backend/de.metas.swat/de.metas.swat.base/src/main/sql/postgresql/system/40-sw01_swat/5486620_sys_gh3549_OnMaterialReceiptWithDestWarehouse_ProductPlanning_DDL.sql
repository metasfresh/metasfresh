
-- 2018-02-23T14:15:27.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN OnMaterialReceiptWithDestWarehouse CHAR(1) DEFAULT ''M''')
;

