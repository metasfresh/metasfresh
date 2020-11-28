-- 29.08.2016 10:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=73428
;

-- 29.08.2016 10:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=73428
;

-- 29.08.2016 10:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=72908
;

-- 29.08.2016 10:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=72908
;


ALTER TABLE M_AttributeValue DROP COLUMN AD_Ref_List_ID;