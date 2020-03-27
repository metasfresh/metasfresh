-- 2020-03-20T12:04:27.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560093
;

-- 2020-03-20T12:04:27.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=7089
;

-- 2020-03-20T12:04:27.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=7089
;

-- 2020-03-20T12:04:27.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=7089
;

-- 2020-03-20T12:04:27.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_BankStatement DROP COLUMN IF EXISTS C_Payment_ID
;

-- 2020-03-20T12:04:27.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=9314
;

-- 2020-03-20T12:04:27.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=9314
;

