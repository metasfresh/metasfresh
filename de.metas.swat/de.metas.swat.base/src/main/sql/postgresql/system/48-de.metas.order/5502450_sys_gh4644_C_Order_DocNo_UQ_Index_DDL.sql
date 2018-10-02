
DROP INDEX public.c_order_documentno;








-- 2018-10-02T15:58:14.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Order_DocumentNo_UQ ON C_Order (C_DocType_ID,C_BPartner_ID,DocumentNo) WHERE DocStatus IN ('CO', 'CL')
;

