-- 2017-06-28T16:57:29.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540802,'N','org.compiere.report.ReportStarter','N',TO_TIMESTAMP('2017-06-28 16:57:29','YYYY-MM-DD HH24:MI:SS'),100,'CU Label (Jasper)','de.metas.fresh','Y','N','N','Y','N','Y','N','Y','@PREFIX@de/metas/docs/label/cu_manufacturing/label.jasper',0,'CU Label','N','S','Java',TO_TIMESTAMP('2017-06-28 16:57:29','YYYY-MM-DD HH24:MI:SS'),100,'CU Label (Jasper)')
;

-- 2017-06-28T16:57:29.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540802 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-06-28T16:58:54.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540802,53027,TO_TIMESTAMP('2017-06-28 16:58:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2017-06-28 16:58:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;