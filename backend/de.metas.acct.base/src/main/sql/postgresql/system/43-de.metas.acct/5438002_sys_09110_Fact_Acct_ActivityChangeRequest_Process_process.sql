-- 28.01.2016 17:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540649,'N',TO_TIMESTAMP('2016-01-28 17:10:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','N','N',0,'Process activity changes','N','Y',0,0,'Java',TO_TIMESTAMP('2016-01-28 17:10:36','YYYY-MM-DD HH24:MI:SS'),100,'Fact_Acct_ActivityChangeRequest_Process')
;

-- 28.01.2016 17:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540649 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 28.01.2016 17:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540649,540701,TO_TIMESTAMP('2016-01-28 17:10:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y',TO_TIMESTAMP('2016-01-28 17:10:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 28.01.2016 17:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.acct.process.Fact_Acct_ActivityChangeRequest_Process',Updated=TO_TIMESTAMP('2016-01-28 17:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540649
;

