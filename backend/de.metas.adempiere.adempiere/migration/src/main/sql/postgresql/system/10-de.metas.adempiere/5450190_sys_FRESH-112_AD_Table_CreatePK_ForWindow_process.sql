-- Sep 6, 2016 12:17 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,540722,'Y','org.adempiere.ad.table.process.AD_Table_CreatePK_ForWindow','N',TO_TIMESTAMP('2016-09-06 12:17:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y',0,'Generate primary key column for all window tables','N','Y',0,0,'Java',TO_TIMESTAMP('2016-09-06 12:17:30','YYYY-MM-DD HH24:MI:SS'),100,'AD_Table_CreatePK_ForWindow')
;

-- Sep 6, 2016 12:17 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540722 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Sep 6, 2016 12:17 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540722,105,TO_TIMESTAMP('2016-09-06 12:17:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2016-09-06 12:17:41','YYYY-MM-DD HH24:MI:SS'),100)
;

