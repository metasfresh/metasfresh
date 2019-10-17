-- 2019-08-29T16:54:25.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=5474
;

-- 2019-08-29T16:54:25.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=5474
;

-- 2019-08-29T16:54:25.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=5474
;

-- 2019-08-29T16:57:25.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543747
;

-- 2019-08-29T16:57:25.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=53297
;

-- 2019-08-29T16:57:25.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53297
;

-- 2019-08-29T16:57:25.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53297
;

-- 2019-08-29T16:57:32.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=6850
;

-- 2019-08-29T16:57:32.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=6850
;

select db_alter_table('S_Resource', 'ALTER TABLE S_Resource DROP COLUMN IF EXISTS M_Warehouse_ID;');

