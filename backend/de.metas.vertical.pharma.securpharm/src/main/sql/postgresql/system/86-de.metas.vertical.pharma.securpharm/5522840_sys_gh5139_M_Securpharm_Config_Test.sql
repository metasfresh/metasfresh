-- 2019-05-28T16:53:49.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,541140,'N','de.metas.vertical.pharma.securpharm.process.M_Securpharm_Config_Test','N',TO_TIMESTAMP('2019-05-28 16:53:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.securpharm','Y','N','N','N','N','N','N','Y','Y',0,'Test','N','N','Java',TO_TIMESTAMP('2019-05-28 16:53:49','YYYY-MM-DD HH24:MI:SS'),100,'M_Securpharm_Config_Test')
;

-- 2019-05-28T16:53:49.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541140 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-05-28T16:54:23.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541140,541348,540719,TO_TIMESTAMP('2019-05-28 16:54:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.securpharm','Y',TO_TIMESTAMP('2019-05-28 16:54:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2019-05-28T17:24:05.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544923,0,TO_TIMESTAMP('2019-05-28 17:24:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.securpharm','Y','Authentication OK','I',TO_TIMESTAMP('2019-05-28 17:24:05','YYYY-MM-DD HH24:MI:SS'),100,'AuthenticationOK')
;

-- 2019-05-28T17:24:05.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544923 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-05-28T20:15:24.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Authentifizierung erfolgreich',Updated=TO_TIMESTAMP('2019-05-28 20:15:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544923
;

-- 2019-05-28T20:15:29.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Authentifizierung erfolgreich',Updated=TO_TIMESTAMP('2019-05-28 20:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544923
;

-- 2019-05-28T20:15:35.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-05-28 20:15:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544923
;

