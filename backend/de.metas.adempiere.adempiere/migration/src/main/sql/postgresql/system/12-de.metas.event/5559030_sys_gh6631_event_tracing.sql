-- 2020-05-11T15:11:42.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541321,'S',TO_TIMESTAMP('2020-05-11 17:11:42','YYYY-MM-DD HH24:MI:SS'),100,'If a thread posts an event to the local event bus and this is Y, then the respective event listeners are invoked in a dedicated worked-thread. 
This means the the posting thread doesn''t have to wait for the listerners to finish, but it can also make things more complex and harder to trace.','de.metas.event','Y','de.metas.event.EventBusPostEventsAsync',TO_TIMESTAMP('2020-05-11 17:11:42','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2020-05-11T15:14:43.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541322,'S',TO_TIMESTAMP('2020-05-11 17:14:43','YYYY-MM-DD HH24:MI:SS'),100,'When an event is posted via RabbitMQ then we invoke the performance monitor such that tracing infos are added to the event.
That way we can do distributred tracing.','de.metas.event','Y','de.metas.event.EventsWithTracingInfos',TO_TIMESTAMP('2020-05-11 17:14:43','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2020-05-11T15:49:28.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2020-05-11 17:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541322
;

-- 2020-05-11T15:56:07.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2020-05-11 17:56:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541322
;

-- 2020-05-11T17:16:26.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If a thread posts an event to the local event bus and this is Y, then the respective event listeners are invoked in a dedicated worked-thread. 
This means the the posting thread doesn''t have to wait for the listerners to finish, but it can also make things more complex and harder to trace.
A change requires a restart.',Updated=TO_TIMESTAMP('2020-05-11 19:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541321
;

-- 2020-05-11T17:27:43.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='When an event is posted via RabbitMQ then we invoke the performance monitor such that tracing infos are added to the event.
That way we can do distributred tracing.
',Updated=TO_TIMESTAMP('2020-05-11 19:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541322
;

-- 2020-05-11T17:28:36.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='When an event is posted via RabbitMQ then we invoke the performance monitor such that tracing infos are added to the event.
That way we can do distributred tracing.
Note that currently, EventBusPostEventsAsync needs to be N for this to work. If it''s Y them AFAIU the required context is lost right before the interesting things happen.',Updated=TO_TIMESTAMP('2020-05-11 19:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541322
;

