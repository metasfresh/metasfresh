-- 28.01.2016 15:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,540647,'org.adempiere.ad.process.AD_Process_Para_UpdateFromAnnotations','N',TO_TIMESTAMP('2016-01-28 15:01:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N',0,'Update process from classname annotations','N','N',0,0,'Java',TO_TIMESTAMP('2016-01-28 15:01:26','YYYY-MM-DD HH24:MI:SS'),100,'AD_Process_Para_UpdateFromAnnotations')
;

-- 28.01.2016 15:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540647 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 28.01.2016 15:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540647,285,TO_TIMESTAMP('2016-01-28 15:01:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2016-01-28 15:01:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 28.01.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Process_ID=540647 AND AD_Table_ID=285
;

-- 28.01.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540647,284,TO_TIMESTAMP('2016-01-28 15:04:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2016-01-28 15:04:26','YYYY-MM-DD HH24:MI:SS'),100)
;

