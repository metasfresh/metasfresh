

-- 2018-04-12T14:17:26.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN M_DiscountSchemaBreak_ID NUMERIC(10)')
;

-- 2018-04-12T14:17:27.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OrderLine ADD CONSTRAINT MDiscountSchemaBreak_COrderLine FOREIGN KEY (M_DiscountSchemaBreak_ID) REFERENCES public.M_DiscountSchemaBreak DEFERRABLE INITIALLY DEFERRED
;
