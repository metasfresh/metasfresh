
-- 2018-04-11T13:15:14.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_DiscountSchemaBreak','ALTER TABLE public.M_DiscountSchemaBreak ADD COLUMN IsPriceOverride CHAR(1) DEFAULT ''N'' CHECK (IsPriceOverride IN (''Y'',''N'')) NOT NULL')
;

