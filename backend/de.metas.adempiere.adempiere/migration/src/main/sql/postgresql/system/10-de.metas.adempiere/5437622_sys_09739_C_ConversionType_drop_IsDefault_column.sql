-- 26.01.2016 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=8640
;

-- 26.01.2016 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=8640
;

-- 26.01.2016 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=10276
;

-- 26.01.2016 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=10276
;





create table C_ConversionType_BKP20160126 as select * from C_ConversionType;
alter table C_ConversionType drop column IsDefault;

