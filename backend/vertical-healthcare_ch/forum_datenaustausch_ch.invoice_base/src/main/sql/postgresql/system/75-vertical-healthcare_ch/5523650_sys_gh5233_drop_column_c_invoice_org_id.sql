
-- 2019-06-05T10:20:11.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580887
;

-- 2019-06-05T10:20:11.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=580887
;

-- 2019-06-05T10:20:11.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=580887
;

-- 2019-06-05T10:20:16.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568150
;

-- 2019-06-05T10:20:16.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568150
;

alter table C_Invoice_Rejection_Detail drop column IF EXISTS c_invoice_org_id;
