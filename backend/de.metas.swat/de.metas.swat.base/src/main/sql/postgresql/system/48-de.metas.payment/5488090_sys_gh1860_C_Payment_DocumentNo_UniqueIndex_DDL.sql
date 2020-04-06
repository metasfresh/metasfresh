
-- 2018-03-13T11:49:51.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Payment_Unique_DocumentNo ON C_Payment (DocumentNo,C_BPartner_ID,C_DocType_ID) WHERE IsActive = 'Y'
;

