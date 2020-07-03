-- 2020-06-25T11:07:38.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541010
;

-- 2020-06-25T11:07:40.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540534
;

-- 2020-06-25T11:07:40.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540534
;


drop index  if EXISTS  InvoiceProcessingServiceCompany_BPartnerAssignment_UQ;
