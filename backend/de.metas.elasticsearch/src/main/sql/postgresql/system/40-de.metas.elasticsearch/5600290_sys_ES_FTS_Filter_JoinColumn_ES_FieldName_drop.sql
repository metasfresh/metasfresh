-- 2021-08-06T22:11:27.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650518
;

-- 2021-08-06T22:11:27.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=650518
;

-- 2021-08-06T22:11:27.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=650518
;

-- 2021-08-06T22:11:27.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ES_FTS_Filter_JoinColumn','ALTER TABLE ES_FTS_Filter_JoinColumn DROP COLUMN IF EXISTS ES_FieldName')
;

-- 2021-08-06T22:11:27.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575251
;

-- 2021-08-06T22:11:27.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575251
;

