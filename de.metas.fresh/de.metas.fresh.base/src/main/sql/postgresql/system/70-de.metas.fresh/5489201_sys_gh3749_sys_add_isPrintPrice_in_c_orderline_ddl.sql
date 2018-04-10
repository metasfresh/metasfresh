
-- 2018-03-22T14:01:27.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN isPrintPrice CHAR(1) DEFAULT ''N'' CHECK (isPrintPrice IN (''Y'',''N'')) NOT NULL')
;
