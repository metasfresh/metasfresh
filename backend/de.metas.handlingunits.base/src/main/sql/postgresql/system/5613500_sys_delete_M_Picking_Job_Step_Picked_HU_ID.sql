-- 2021-11-15T12:19:11.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=669069
;

-- 2021-11-15T12:19:11.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=669069
;

-- 2021-11-15T12:19:11.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=669069
;

-- 2021-11-15T12:19:12.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step','ALTER TABLE M_Picking_Job_Step DROP COLUMN IF EXISTS Picked_HU_ID')
;

-- 2021-11-15T12:19:12.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=578099
;

-- 2021-11-15T12:19:12.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=578099
;

