
-- 2018-02-23T14:36:07.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN OnMaterialReceiptWithDestWarehouse CHAR(1) DEFAULT ''M''')
;

