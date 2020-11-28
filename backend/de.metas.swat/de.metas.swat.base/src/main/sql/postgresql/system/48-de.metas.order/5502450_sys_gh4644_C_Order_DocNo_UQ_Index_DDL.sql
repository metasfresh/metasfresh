
DROP INDEX IF EXISTS public.c_order_documentno;










-- 2018-10-10T11:40:42.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_order_documentno_uq
;

-- 2018-10-10T11:40:42.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Order_DocumentNo_UQ ON C_Order (AD_Org_ID,DocumentNo,C_DocType_ID) WHERE DocStatus IN ('CO', 'CL') AND COALESCE(C_DocType_ID, 0)>0
;

