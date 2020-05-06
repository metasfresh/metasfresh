-- 2018-03-22T10:28:45.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544660,0,TO_TIMESTAMP('2018-03-22 10:28:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ein freigegebener Datensatz kann nicht gelöscht werden!','I',TO_TIMESTAMP('2018-03-22 10:28:45','YYYY-MM-DD HH24:MI:SS'),100,'org.adempiere.bpartner.model.interceptor.C_BPartner_CreditLimit.prohibitDeletingApprovedCreditLimit')
;

-- 2018-03-22T10:28:45.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544660 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-03-22T14:48:37.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540938,'Y','org.adempiere.bpartner.process.BPartnerCreditLimit_RemoveHoldStatus','N',TO_TIMESTAMP('2018-03-22 14:48:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Remove OnHold status for credit limit','Y','N','N','N','N','N','N','Y',0,'BPartnerCreditLimit_RemoveHoldStatus','N','Y','Java',TO_TIMESTAMP('2018-03-22 14:48:37','YYYY-MM-DD HH24:MI:SS'),100,'BPartnerCreditLimit_RemoveHoldStatus')
;

-- 2018-03-22T14:48:37.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540938 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-03-22T14:49:03.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540938,540763,TO_TIMESTAMP('2018-03-22 14:49:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2018-03-22 14:49:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

