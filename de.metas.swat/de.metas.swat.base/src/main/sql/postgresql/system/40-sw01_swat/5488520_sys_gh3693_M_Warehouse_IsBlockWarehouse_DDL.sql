
-- 2018-03-16T15:26:01.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Warehouse','ALTER TABLE public.M_Warehouse ADD COLUMN IsBlockWarehouse CHAR(1) DEFAULT ''N'' CHECK (IsBlockWarehouse IN (''Y'',''N'')) NOT NULL')
;

