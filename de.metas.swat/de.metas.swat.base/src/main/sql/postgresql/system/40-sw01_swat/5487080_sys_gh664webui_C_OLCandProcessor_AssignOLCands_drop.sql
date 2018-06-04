-- 2018-02-27T17:38:56.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=547208
;

-- 2018-02-27T17:38:56.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=547208
;

-- 2018-02-27T17:39:49.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=545129
;

-- 2018-02-27T17:39:49.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=545129
;

alter table C_OLCandProcessor drop column if exists AssignOLCands;

