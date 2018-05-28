-- 2018-03-28T13:57:07.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,JasperReport,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Description,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Value,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-03-28 13:57:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-03-28 13:57:07','YYYY-MM-DD HH24:MI:SS'),'Y','N','3','S','Y','N','N',100,'',540948,'N','Y','N','Picking TU Label For HU','Java','N','N',0,0,'Picking TU Label For HU','10000001','de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_TU_Label','de.metas.picking')
;

-- 2018-03-28T13:57:07.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540948 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

