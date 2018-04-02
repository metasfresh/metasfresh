
-- 2018-03-30T14:02:50.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Config','ALTER TABLE public.M_Picking_Config ADD COLUMN IsAutoProcess CHAR(1) DEFAULT ''N'' CHECK (IsAutoProcess IN (''Y'',''N'')) NOT NULL')
;