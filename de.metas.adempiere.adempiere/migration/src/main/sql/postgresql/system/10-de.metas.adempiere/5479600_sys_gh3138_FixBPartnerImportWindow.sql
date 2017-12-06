-- 2017-12-06T12:01:45.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN C_InvoiceSchedule_ID NUMERIC(10)')
;

-- 2017-12-06T12:01:45.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_BPartner ADD CONSTRAINT CInvoiceSchedule_IBPartner FOREIGN KEY (C_InvoiceSchedule_ID) REFERENCES public.C_InvoiceSchedule DEFERRABLE INITIALLY DEFERRED
;

