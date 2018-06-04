-- 2018-02-27T17:55:40.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=547214
;

-- 2018-02-27T17:55:40.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=547214
;

-- 2018-02-27T17:56:16.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=545140
;

-- 2018-02-27T17:56:16.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=545140
;

-- 2018-02-27T17:56:19.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=541310
;

-- 2018-02-27T17:56:19.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=541310
;

-- 2018-02-27T17:56:43.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=541306
;

-- 2018-02-27T17:56:43.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=541306
;

alter table C_OLCandProcessor drop column if exists AD_Scheduler_OLCandProcessor;

