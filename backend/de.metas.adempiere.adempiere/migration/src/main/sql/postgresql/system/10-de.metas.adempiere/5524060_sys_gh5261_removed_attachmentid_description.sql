-- 2019-06-07T11:16:41.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568170
;

-- 2019-06-07T11:16:41.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568170
;

-- 2019-06-07T11:17:30.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568178
;

-- 2019-06-07T11:17:30.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568178
;
ALTER TABLE AD_Attachment_Log DROP COLUMN IF EXISTS AD_Attachment_ID;
