-- 2018-02-16T22:19:22.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','DATEV_CreateExportLines','3','de.metas.datev','Y','N','N','N',540923,'Y','Y','N','Java','N','N',0,0,'Create export lines',100,TO_TIMESTAMP('2018-02-16 22:19:22','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:19:22','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:19:22.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540923 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-02-16T22:19:42.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (EntityType,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('de.metas.datev',100,'Y',540934,0,'Y','N',540923,0,100,TO_TIMESTAMP('2018-02-16 22:19:42','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:19:42','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:20:17.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','DATEV_ExportFile','3','de.metas.datev','Y','N','N','N',540924,'Y','Y','N','Java','N','N',0,0,'Export file',100,TO_TIMESTAMP('2018-02-16 22:20:17','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:20:17','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:20:17.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540924 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-02-16T22:20:29.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (EntityType,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('de.metas.datev',100,'Y',540934,0,'N','N',540924,0,100,TO_TIMESTAMP('2018-02-16 22:20:29','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:20:29','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:20:38.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.datev.process.DATEV_ExportFile',Updated=TO_TIMESTAMP('2018-02-16 22:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540924
;

-- 2018-02-16T22:20:49.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.datev.process.DATEV_CreateExportLines',Updated=TO_TIMESTAMP('2018-02-16 22:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540923
;

-- 2018-02-17T11:19:53.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2018-02-17 11:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540934 AND AD_Process_ID=540924
;

