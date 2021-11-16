

-- 2021-11-16T13:27:40.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=594989
;

-- 2021-11-16T13:27:40.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=668445
;

-- 2021-11-16T13:27:40.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=668445
;

-- 2021-11-16T13:27:40.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=668445
;

-- 2021-11-16T13:27:40.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_GRSSignum','ALTER TABLE ExternalSystem_Config_GRSSignum DROP COLUMN IF EXISTS RemoteURL')
;

-- 2021-11-16T13:27:40.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=578387
;

-- 2021-11-16T13:27:40.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=578387
;

-- 2021-11-16T13:27:56.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=580215
;

-- 2021-11-16T13:27:56.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=580215
;

