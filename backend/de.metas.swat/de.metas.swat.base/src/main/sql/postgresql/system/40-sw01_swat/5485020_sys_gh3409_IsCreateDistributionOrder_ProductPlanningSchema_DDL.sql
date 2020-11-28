

-- 2018-02-09T15:23:16.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product_PlanningSchema','ALTER TABLE public.M_Product_PlanningSchema ADD COLUMN IsCreateDistributionOrder CHAR(1) DEFAULT ''N'' CHECK (IsCreateDistributionOrder IN (''Y'',''N'')) NOT NULL')
;

