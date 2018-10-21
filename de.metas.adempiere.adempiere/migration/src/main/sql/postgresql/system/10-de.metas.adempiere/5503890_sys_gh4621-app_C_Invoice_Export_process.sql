-- 2018-10-17T11:08:52.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.document.archive.mailrecipient.process.C_Doc_Outbound_Log_SendPDFMails',Updated=TO_TIMESTAMP('2018-10-17 11:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540511
;

-- 2018-10-17T11:47:57.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='AbstractMailDocumentsForSelection.No_DocOutboundLog_Selection',Updated=TO_TIMESTAMP('2018-10-17 11:47:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=542866
;

-- 2018-10-17T11:48:33.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('I','Die eingestellte Filterung enthält keinen Datensatz, zu dem ein Anhang gespeichert werden kann.',0,'Y',TO_TIMESTAMP('2018-10-17 11:48:33','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-17 11:48:33','YYYY-MM-DD HH24:MI:SS'),100,544801,'C_Doc_Outbound_Log_StoreAttachments.No_DocOutboundLog_Selection',0,'de.metas.document.archive')
;

-- 2018-10-17T11:48:33.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544801 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


-- 2018-10-17T14:09:16.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Description,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,EntityType,Classname,Type) VALUES (0,'Y',TO_TIMESTAMP('2018-10-17 14:09:16','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-17 14:09:16','YYYY-MM-DD HH24:MI:SS'),'N','N','3','Y','N','N','N',100,541025,'C_Doc_Outbound_Log_StoreAttachments','Y','Y','N','Speichert die Anhänge der ausgewählten Datensätze, soweit entsprechend konfiguriert.','N','N',0,0,'Anhänge speichern','de.metas.document.archive','de.metas.document.archive.process.C_Doc_Outbound_Log_StoreAttachments','Java')
;

-- 2018-10-17T14:09:16.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=541025 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-10-17T14:09:20.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 14:09:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541025 AND AD_Language='de_CH'
;

-- 2018-10-17T14:09:28.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 14:09:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Store attachments',Description='' WHERE AD_Process_ID=541025 AND AD_Language='en_US'
;

-- 2018-10-17T14:09:46.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2018-10-17 14:09:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541025
;

-- 2018-10-17T14:09:55.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2018-10-17 14:09:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541025
;

-- 2018-10-17T14:10:21.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,EntityType,Updated,UpdatedBy,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID) VALUES (TO_TIMESTAMP('2018-10-17 14:10:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',540453,'de.metas.document.archive',TO_TIMESTAMP('2018-10-17 14:10:21','YYYY-MM-DD HH24:MI:SS'),100,0,'N','N',541025,0)
;

-- 2018-10-19T07:37:41.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-19 07:37:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544801 AND AD_Language='de_CH'
;

-- 2018-10-19T07:38:20.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-19 07:38:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='No selected record has exportable attachments' WHERE AD_Message_ID=544801 AND AD_Language='en_US'
;


