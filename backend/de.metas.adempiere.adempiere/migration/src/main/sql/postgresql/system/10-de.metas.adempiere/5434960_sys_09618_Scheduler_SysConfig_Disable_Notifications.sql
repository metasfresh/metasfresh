
-- 08.12.2015 10:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540918,'S',TO_TIMESTAMP('2015-12-08 10:15:00','YYYY-MM-DD HH24:MI:SS'),100,'Notify the supervisor (only if one is set!) if a process run by AD_Scheduler failed.','D','Y','org.compiere.server.Scheduler.notifyOnNotOK',TO_TIMESTAMP('2015-12-08 10:15:00','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 08.12.2015 10:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540919,'S',TO_TIMESTAMP('2015-12-08 10:16:20','YYYY-MM-DD HH24:MI:SS'),100,'Notify the "recipients" that are configured via AD_SchedulerRecipient if a process, report etc run by AD_Scheduler succeeded.','D','Y','org.compiere.server.Scheduler.notifyOntOK',TO_TIMESTAMP('2015-12-08 10:16:20','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 08.12.2015 10:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Notify the supervisor (only if one is set!) if a process, report etc run by AD_Scheduler failed.',Updated=TO_TIMESTAMP('2015-12-08 10:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540918
;
