-- 2022-04-15T08:51:29.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540101
;

delete from ad_pinstance_log where ad_pinstance_id in (select ad_pinstance_id from ad_pinstance where ad_scheduler_id=550015);
delete from ad_pinstance_para where ad_pinstance_id in (select ad_pinstance_id from ad_pinstance where ad_scheduler_id=550015);
delete from ad_pinstance where ad_scheduler_id=550015;

update c_olcandprocessor set ad_scheduler_id=null where ad_scheduler_id=550015; 

DELETE FROM ad_schedulerlog WHERE AD_Scheduler_ID=550015
;

-- 2022-04-15T08:52:00.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Scheduler WHERE AD_Scheduler_ID=550015
;

-- 2022-04-27T10:12:50.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540475
;

-- 2022-04-27T10:12:50.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540475
;

-- 2022-04-27T10:14:08.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540476
;

-- 2022-04-27T10:14:08.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540476
;

