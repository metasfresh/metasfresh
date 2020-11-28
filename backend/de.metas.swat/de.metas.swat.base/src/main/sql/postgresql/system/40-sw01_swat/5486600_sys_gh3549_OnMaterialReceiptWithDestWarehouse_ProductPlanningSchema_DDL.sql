
-- 2018-02-23T13:59:30.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product_PlanningSchema','ALTER TABLE public.M_Product_PlanningSchema ADD COLUMN OnMaterialReceiptWithDestWarehouse CHAR(1) DEFAULT ''M'' NOT NULL')
;

