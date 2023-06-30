-- 2022-05-27T08:42:52.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=557552
;

-- 2022-05-27T08:42:52.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=557552
;

-- 2022-05-27T08:42:52.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=557552
;

-- 2022-05-27T08:42:52.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541801
;

-- 2022-05-27T08:42:52.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=557837
;

-- 2022-05-27T08:42:52.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=557837
;

-- 2022-05-27T08:42:52.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=557837
;

-- 2022-05-27T08:42:52.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=565315
;

-- 2022-05-27T08:42:52.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565315
;

-- 2022-05-27T08:42:52.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=565315
;



-- 2022-05-27T08:42:52.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ProductPrice','ALTER TABLE M_ProductPrice DROP COLUMN IF EXISTS IsInfiniteCapacity')
;

delete from ad_ui_element where ad_field_id in (select ad_field_id  from ad_field where AD_Column_ID=556082);
delete from ad_field where AD_Column_ID=556082;

-- 2022-05-27T08:42:52.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=556082
;

-- 2022-05-27T08:42:52.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=556082
;

