-- 2017-11-07T15:09:29.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=552888
;

-- 2017-11-07T15:09:29.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=552888
;

-- 2017-11-07T15:16:36.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=545554
;

-- 2017-11-07T15:18:14.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=552902
;

-- 2017-11-07T15:18:14.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=552902
;

-- 2017-11-07T15:18:34.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=548771
;

-- 2017-11-07T15:18:34.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=548771
;

alter table C_Print_Job_Instructions drop column UserOK;

