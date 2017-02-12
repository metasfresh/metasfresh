-- 16.06.2016 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=10068
;

-- 16.06.2016 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=10068
;

-- 16.06.2016 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=268
;

-- 16.06.2016 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=268
;

-- 16.06.2016 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=11527
;

-- 16.06.2016 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=11527
;

alter table C_RfQ drop column CopyLines;

