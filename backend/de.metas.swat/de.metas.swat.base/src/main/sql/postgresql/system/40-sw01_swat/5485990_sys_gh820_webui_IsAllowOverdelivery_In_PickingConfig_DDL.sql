

-- 2018-02-19T17:50:29.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Config','ALTER TABLE public.M_Picking_Config ADD COLUMN IsAllowOverdelivery CHAR(1) DEFAULT ''N'' CHECK (IsAllowOverdelivery IN (''Y'',''N'')) NOT NULL')
;

