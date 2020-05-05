-- 2017-07-18T08:07:12.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540810,'Y','de.metas.ui.web.picking.process.WEBUI_Picking_M_Picking_Candidate_Processed','N',TO_TIMESTAMP('2017-07-18 08:07:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','N','N','N','N','N','N','Y',0,'Kommissionierung schließen','N','Y','Java',TO_TIMESTAMP('2017-07-18 08:07:12','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_M_Picking_Candidate_Processed')
;

-- 2017-07-18T08:07:12.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540810 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-07-18T08:07:34.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-18 08:07:34','YYYY-MM-DD HH24:MI:SS'),Name='Finalize Picking' WHERE AD_Process_ID=540810 AND AD_Language='en_US'
;

-- 2017-07-18T08:07:43.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2017-07-18 08:07:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540810
;

-- 2017-07-18T11:48:46.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544445,0,TO_TIMESTAMP('2017-07-18 11:48:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Keine zu abzuschließenden Vorgänge','I',TO_TIMESTAMP('2017-07-18 11:48:46','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_No_Unprocessed_Records')
;

-- 2017-07-18T11:48:46.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544445 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

