
-- 2018-08-09T11:38:03.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InventoryLine','ALTER TABLE public.M_InventoryLine ADD COLUMN IsCounted CHAR(1) DEFAULT ''N'' CHECK (IsCounted IN (''Y'',''N'')) NOT NULL')
;

