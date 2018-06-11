-- 2018-06-06T12:34:31.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Async_Batch_Type (AD_Client_ID,AD_Org_ID,C_Async_Batch_Type_ID,Created,CreatedBy,InternalName,IsActive,IsSendMail,IsSendNotification,KeepAliveTimeHours,NotificationType,SkipTimeoutMillis,Updated,UpdatedBy) VALUES (1000000,0,540009,TO_TIMESTAMP('2018-06-06 12:34:30','YYYY-MM-DD HH24:MI:SS'),100,'CreateLettersAsync','Y','N','N','24','ABP',0,TO_TIMESTAMP('2018-06-06 12:34:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-06T12:43:44.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540061,'de.metas.letter.service.async.spi.impl.C_Letter_CreateFromMKTG_ContactPerson_Async',TO_TIMESTAMP('2018-06-06 12:43:44','YYYY-MM-DD HH24:MI:SS'),100,'Creates C_Letters from MKTG_ContactPerson','de.metas.marketing.serialletter','C_Letter_CreateFromMKTG_ContactPerson_Async','Y',TO_TIMESTAMP('2018-06-06 12:43:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-06T12:44:06.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540048,TO_TIMESTAMP('2018-06-06 12:44:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'C_Letter_CreateFromMKTG_ContactPerson_Async',1,TO_TIMESTAMP('2018-06-06 12:44:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-06T12:44:28.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540061,540080,540048,TO_TIMESTAMP('2018-06-06 12:44:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-06-06 12:44:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-06T13:13:21.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540977,'Y','de.metas.letter.process.C_Letter_PrintAutomatically','N',TO_TIMESTAMP('2018-06-06 13:13:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.serialletter','Y','N','N','N','N','N','N','Y',0,'C_Letter_PrintAutomatically','N','Y','Java',TO_TIMESTAMP('2018-06-06 13:13:21','YYYY-MM-DD HH24:MI:SS'),100,'C_Letter_PrintAutomatically')
;

-- 2018-06-06T13:13:21.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540977 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

