-- 17.06.2016 17:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540687,'Y','N',TO_TIMESTAMP('2016-06-17 17:39:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','Y',0,'Auswahl Schließen','N','Y',0,0,'Java',TO_TIMESTAMP('2016-06-17 17:39:20','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_Candidate_Close')
;

-- 17.06.2016 17:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540687 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 17.06.2016 17:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.invoicecandidate.process.C_Invoice_Candidate_Close',Updated=TO_TIMESTAMP('2016-06-17 17:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540687
;

-- 17.06.2016 17:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540687,540270,TO_TIMESTAMP('2016-06-17 17:47:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y',TO_TIMESTAMP('2016-06-17 17:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

