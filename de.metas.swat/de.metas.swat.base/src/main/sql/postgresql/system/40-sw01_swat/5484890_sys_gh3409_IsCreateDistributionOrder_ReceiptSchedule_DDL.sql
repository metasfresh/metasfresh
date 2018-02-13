

-- 2018-02-07T18:14:11.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN IsCreateDistributionOrder CHAR(1) DEFAULT ''N'' CHECK (IsCreateDistributionOrder IN (''Y'',''N'')) NOT NULL')
;

