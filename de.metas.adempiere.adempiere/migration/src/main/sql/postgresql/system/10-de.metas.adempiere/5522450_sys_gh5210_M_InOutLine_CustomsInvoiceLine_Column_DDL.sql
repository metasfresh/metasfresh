
-- 2019-05-23T14:25:23.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_InOutLine ADD CONSTRAINT CCustomsInvoiceLine_MInOutLine FOREIGN KEY (C_Customs_Invoice_Line_ID) REFERENCES public.C_Customs_Invoice_Line DEFERRABLE INITIALLY DEFERRED
;

