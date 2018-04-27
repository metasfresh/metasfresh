
-- 2018-04-25T11:41:40.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN Base_PricingSystem_ID NUMERIC(10)')
;

-- 2018-04-25T11:41:41.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_InvoiceLine ADD CONSTRAINT BasePricingSystem_CInvoiceLine FOREIGN KEY (Base_PricingSystem_ID) REFERENCES public.M_PricingSystem DEFERRABLE INITIALLY DEFERRED
;

