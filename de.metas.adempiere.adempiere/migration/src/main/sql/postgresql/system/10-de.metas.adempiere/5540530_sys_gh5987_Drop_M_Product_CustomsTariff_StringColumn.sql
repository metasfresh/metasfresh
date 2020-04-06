-- 2020-01-07T09:31:00.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582016
;

-- 2020-01-07T09:31:00.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=582016
;

-- 2020-01-07T09:31:00.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=582016
;

-- 2020-01-07T09:31:00.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID in ( select ad_field_ID from ad_field where AD_Column_ID=568324)
;

DELETE FROM ad_ui_element WHERE AD_Field_ID in ( select ad_field_ID from ad_field where AD_Column_ID=568324)
;

-- 2020-01-07T09:31:00.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID in ( select ad_field_ID from ad_field where AD_Column_ID=568324)
;

-- 2020-01-07T09:31:00.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID in ( select ad_field_ID from ad_field where AD_Column_ID=568324)
;

-- 2020-01-07T09:33:19.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568324
;

-- 2020-01-07T09:33:19.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568324
;

ALTER TABLE M_Product DROP COLUMN CustomsTariff;-- 2020-01-07T09:40:32.828Z







