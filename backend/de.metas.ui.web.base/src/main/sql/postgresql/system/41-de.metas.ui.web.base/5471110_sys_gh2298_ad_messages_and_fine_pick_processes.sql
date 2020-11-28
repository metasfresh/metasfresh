
--
-- ..but first rename two prexising processes
--
-- 2017-09-07T16:13:47.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.picking.process.WEBUI_Picking_PickQtyToNewHU', Name='In neue HU Kommissionieren', Value='WEBUI_Picking_PickQtyToNewHU',Updated=TO_TIMESTAMP('2017-09-07 16:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540807
;
-- 2017-09-07T16:14:02.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.picking.process.WEBUI_Picking_PickQtyToExistingHU', Name='In bestehende HU Kommissionieren', Value='WEBUI_Picking_PickQtyToExistingHU',Updated=TO_TIMESTAMP('2017-09-07 16:14:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540808
;

-- 2017-09-07T16:16:42.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540827,'N','de.metas.ui.web.picking.process.WEBUI_Picking_ReturnQtyToSourceHU','N',TO_TIMESTAMP('2017-09-07 16:16:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'Zurück in Quell-HU','N','Y','Java',TO_TIMESTAMP('2017-09-07 16:16:41','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_ReturnQtyToSourceHU')
;

-- 2017-09-07T16:16:42.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540827 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-09-07T16:19:40.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544490,0,TO_TIMESTAMP('2017-09-07 16:19:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Nur mit kommissionierter CU','I',TO_TIMESTAMP('2017-09-07 16:19:40','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_SelectPickedCU')
;

-- 2017-09-07T16:19:40.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544490 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
