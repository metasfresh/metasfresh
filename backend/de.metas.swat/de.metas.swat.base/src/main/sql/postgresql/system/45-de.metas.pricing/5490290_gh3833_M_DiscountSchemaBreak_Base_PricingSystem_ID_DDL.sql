
-- 2018-04-11T11:43:55.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_DiscountSchemaBreak','ALTER TABLE public.M_DiscountSchemaBreak ADD COLUMN Base_PricingSystem_ID NUMERIC(10)')
;

-- 2018-04-11T11:43:55.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_DiscountSchemaBreak ADD CONSTRAINT BasePricingSystem_MDiscountSchemaBreak FOREIGN KEY (Base_PricingSystem_ID) REFERENCES public.M_PricingSystem DEFERRABLE INITIALLY DEFERRED
;

