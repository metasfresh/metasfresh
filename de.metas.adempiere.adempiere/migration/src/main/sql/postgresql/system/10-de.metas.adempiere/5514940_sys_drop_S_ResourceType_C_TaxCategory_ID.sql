-- 2019-03-05T11:29:03.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543785
;

-- 2019-03-05T11:29:03.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=5587
;

-- 2019-03-05T11:29:03.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=5587
;

-- 2019-03-05T11:29:03.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=599829
;

-- 2019-03-05T11:29:03.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE S_ResourceType DROP COLUMN IF EXISTS C_TaxCategory_ID
;

-- 2019-03-05T11:29:03.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=6932
;

-- 2019-03-05T11:29:03.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=6932
;

