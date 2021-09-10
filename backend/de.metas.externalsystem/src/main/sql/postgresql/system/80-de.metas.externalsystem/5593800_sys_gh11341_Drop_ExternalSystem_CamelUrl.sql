-- 2021-06-19T06:33:14.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=576650
;

-- 2021-06-19T06:33:14.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=628837
;

-- 2021-06-19T06:33:14.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=628837
;

-- 2021-06-19T06:33:14.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=628837
;

-- 2021-06-19T06:33:14.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config','ALTER TABLE ExternalSystem_Config DROP COLUMN IF EXISTS CamelURL')
;


-- hotfix: delete all AD_Fields which are pointing to CamelUrl column
delete from ad_ui_element where ad_field_id in (select ad_field_id from ad_field where ad_column_id=572727);
delete from ad_field where ad_column_id=572727;


-- 2021-06-19T06:33:15.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572727
;

-- 2021-06-19T06:33:15.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=572727
;

