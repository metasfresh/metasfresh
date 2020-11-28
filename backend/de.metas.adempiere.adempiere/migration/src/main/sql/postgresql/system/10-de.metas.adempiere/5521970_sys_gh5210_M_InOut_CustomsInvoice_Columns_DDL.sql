
-- 2019-05-20T14:09:59.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN C_Customs_Invoice_ID NUMERIC(10)')
;


-- 2019-05-20T14:10:00.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_InOut ADD CONSTRAINT CCustomsInvoice_MInOut FOREIGN KEY (C_Customs_Invoice_ID) REFERENCES public.C_Customs_Invoice DEFERRABLE INITIALLY DEFERRED
;




-- 2019-05-20T14:12:52.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN IsExportedToCustomsInvoice CHAR(1) DEFAULT ''N'' CHECK (IsExportedToCustomsInvoice IN (''Y'',''N'')) NOT NULL')
;

