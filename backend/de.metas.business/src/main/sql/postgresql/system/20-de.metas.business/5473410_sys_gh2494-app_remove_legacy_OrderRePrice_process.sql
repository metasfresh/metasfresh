
--
-- delete legacy process OrderRePrice
--
delete from AD_Menu where (ad_process_id)=(232);

-- 2017-10-02T15:15:05.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=232
;

-- 2017-10-02T15:15:05.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=232
;

