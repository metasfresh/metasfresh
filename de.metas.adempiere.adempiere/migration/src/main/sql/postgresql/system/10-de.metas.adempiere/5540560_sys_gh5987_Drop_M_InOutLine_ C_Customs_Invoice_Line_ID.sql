


-- 2020-01-07T09:31:00.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID in ( select ad_field_ID from ad_field where AD_Column_ID=568078)
;

DELETE FROM ad_ui_element WHERE AD_Field_ID in ( select ad_field_ID from ad_field where AD_Column_ID=568078);

-- 2020-01-07T09:31:00.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID in ( select ad_field_ID from ad_field where AD_Column_ID=568078)
;

-- 2020-01-07T09:31:00.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID in ( select ad_field_ID from ad_field where AD_Column_ID=568078)
;


-- 2020-01-07T09:55:28.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568078
;

-- 2020-01-07T09:55:28.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568078
;




ALTER TABLE M_InOutLine DROP COLUMN C_Customs_Invoice_Line_ID;