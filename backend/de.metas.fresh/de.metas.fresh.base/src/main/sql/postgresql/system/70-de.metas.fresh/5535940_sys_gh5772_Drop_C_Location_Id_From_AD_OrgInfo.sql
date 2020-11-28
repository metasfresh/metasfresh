-- 2019-11-06T17:31:05.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_OrgInfo DROP COLUMN IF EXISTS C_Location_ID
;

-- 2019-11-06T17:31:05.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=549349
;

-- 2019-11-06T17:31:05.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=549349
;

