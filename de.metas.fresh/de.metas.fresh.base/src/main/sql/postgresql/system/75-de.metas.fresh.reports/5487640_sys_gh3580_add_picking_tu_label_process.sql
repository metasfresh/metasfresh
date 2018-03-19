-- 2018-03-07T11:49:07.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540933,'N','org.compiere.report.ReportStarter','N',TO_TIMESTAMP('2018-03-07 11:49:07','YYYY-MM-DD HH24:MI:SS'),100,'Picking TU Label (Jasper)','de.metas.fresh','Y','N','Y','Y','N','Y','N','Y','@PREFIX@de/metas/docs/label/picking_tu_label/report_lu.jasper',0,'Picking TU Label','N','S','Java',TO_TIMESTAMP('2018-03-07 11:49:07','YYYY-MM-DD HH24:MI:SS'),100,'Picking TU Label (Jasper)')
;

-- 2018-03-07T11:49:07.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540933 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-03-07T11:50:04.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540933,540516,TO_TIMESTAMP('2018-03-07 11:50:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2018-03-07 11:50:04','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-03-07T11:52:10.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,IsActive,IsApplyLU,IsApplyTU,IsApplyVirtualPI,M_HU_Process_ID,Updated,UpdatedBy) VALUES (0,0,540933,TO_TIMESTAMP('2018-03-07 11:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N',540015,TO_TIMESTAMP('2018-03-07 11:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

