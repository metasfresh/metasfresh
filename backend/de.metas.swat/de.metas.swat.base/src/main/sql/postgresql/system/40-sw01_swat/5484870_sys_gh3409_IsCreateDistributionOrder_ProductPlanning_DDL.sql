
-- 2018-02-07T17:42:33.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN IsCreateDistributionOrder CHAR(1) DEFAULT ''N'' CHECK (IsCreateDistributionOrder IN (''Y'',''N'')) NOT NULL')
;

