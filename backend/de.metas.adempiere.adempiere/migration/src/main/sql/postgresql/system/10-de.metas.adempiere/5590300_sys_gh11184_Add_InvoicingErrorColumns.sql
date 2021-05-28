-- 2021-05-27T10:51:19.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN IsInvoicingError CHAR(1) DEFAULT ''N'' CHECK (IsInvoicingError IN (''Y'',''N''))')
;

-- 2021-05-27T10:53:06.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN InvoicingErrorMsg TEXT')
;
