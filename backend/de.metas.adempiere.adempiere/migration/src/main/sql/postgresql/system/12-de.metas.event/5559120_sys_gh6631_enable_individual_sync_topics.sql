
-- 2020-05-12T16:42:40.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If a thread posts an event to the local event bus and this is Y, then the respective event listeners are enqueued and invoked within a dedicated worker-thread. 
This means that the posting thread doesn''t have to wait for the listerners to finish, but it can also make things more complex and harder to trace.
For a specific topic such as "de.metas.acct.UserNotifications", the default seeting can be overridden with a Y/N sysconfig with name = "de.metas.event.asyncEventBus.topic_de.metas.acct.UserNotifications"
A change requires a restart.', Name='de.metas.event.asyncEventBus',Updated=TO_TIMESTAMP('2020-05-12 18:42:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541321
;

-- 2020-05-12T16:43:19.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2020-05-12 18:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541321
;

-- 2020-05-12T16:43:29.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2020-05-12 18:43:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541321
;

-- 2020-05-12T18:45:55.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='When an event is received via RabbitMQ we invoke the performance monitor such that tracing infos are added to the event.
That way we can also do distributred tracing.
Note that currently, the sysconfig "de.metas.event.asyncEventBus" or "de.metas.event.asyncEventBus.topic_<topicName>" needs to be N for the respective event''s processing to be monitored.
', Name='de.metas.event.MonitorIncomingEvents',Updated=TO_TIMESTAMP('2020-05-12 20:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541322
;

-- 2020-05-12T18:48:36.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='When an event is received via RabbitMQ we invoke the performance monitor such that tracing infos are added to the event.
That way we can also do distributred tracing.
Note that currently, the sysconfig "de.metas.event.asyncEventBus" or "de.metas.event.asyncEventBus.topic_<topicName>" needs to be N for the respective event''s processing to be monitored.
Also note that I don''t see why this config should be N. It is here just to be able to switch it off if neccesary for unexpected reasons.',Updated=TO_TIMESTAMP('2020-05-12 20:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541322
;

-- 2020-05-12T18:56:44.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541323,'S',TO_TIMESTAMP('2020-05-12 20:56:44','YYYY-MM-DD HH24:MI:SS'),100,'Tells if cache invalidation events in particular shall be processed asynchronously. 
I think this should be N because there are a lot of them and their processing is supposed to be real quick. 
So processing them synchronously in the thread that posted the event should per-se introduce no performance bottle-neck.
In case of async, if they are not processed quickly, then a huge (async) queue can build up quickly, and weird things happen (mostly UIs are not updated).
So in other words: having them synchronous shall be fine, and if it''s not, then we need to know about it anyways because it directly affects the users'' experience.','de.metas.event','Y','de.metas.event.asyncEventBus.topic_de.topic_de.metas.cache.CacheInvalidationRemoteHandler',TO_TIMESTAMP('2020-05-12 20:56:44','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

UPDATE AD_SysConfig SET Value='N' WHERE Name='de.metas.event.asyncEventBus.topic_de.topic_de.metas.cache.CacheInvalidationRemoteHandler';
