-- 2018-07-10T11:34:56.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Transport Auftrag',Updated=TO_TIMESTAMP('2018-07-10 11:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540020
;

-- 2018-07-10T11:34:56.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Transport Auftrag',Updated=TO_TIMESTAMP('2018-07-10 11:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540032
;

-- order async workpackages with the latest first
-- 2018-07-10T11:50:19.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-1.0,Updated=TO_TIMESTAMP('2018-07-10 11:50:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551115
;

-- 2018-07-10T12:00:58.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='M_ShipperTransportation',Updated=TO_TIMESTAMP('2018-07-10 12:00:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540030
;

-- 2018-07-10T13:15:33.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.document.archive.process.C_Doc_Outbound_Log_SendPDFMails', Value='C_Doc_Outbound_Log_SendPDFMails',Updated=TO_TIMESTAMP('2018-07-10 13:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540511
;

-- 2018-07-10T13:49:31.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='PDF als Email an Geschäftspartner senden',Updated=TO_TIMESTAMP('2018-07-10 13:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540511
;

-- 2018-07-10T13:51:06.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540988,'Y','de.metas.shipper.gateway.derkurier.process.M_ShipperTransportation_SendDerKurierEMail','N',TO_TIMESTAMP('2018-07-10 13:51:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.derkurier','Y','N','N','N','N','N','N','Y',0,'CSV als Email an Der Kurier senden','N','Y','Java',TO_TIMESTAMP('2018-07-10 13:51:06','YYYY-MM-DD HH24:MI:SS'),100,'M_ShipperTransportation_SendDerKurierEMail')
;

-- 2018-07-10T13:51:06.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540988 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-07-10T13:51:27.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540988,540030,TO_TIMESTAMP('2018-07-10 13:51:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.derkurier','Y',TO_TIMESTAMP('2018-07-10 13:51:27','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-07-10T14:28:21.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='GO! Versandauftrag', WEBUI_NameBrowse='',Updated=TO_TIMESTAMP('2018-07-10 14:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541011
;

-- 2018-07-10T14:28:33.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET EntityType='de.metas.shipper.gateway.go',Updated=TO_TIMESTAMP('2018-07-10 14:28:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541011
;

-- 2018-07-10T14:30:02.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='AD_MailBox',Updated=TO_TIMESTAMP('2018-07-10 14:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540242
;

-- 2018-07-10T14:31:34.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_MailBox (AD_Client_ID,AD_MailBox_ID,AD_Org_ID,Created,CreatedBy,EMail,IsActive,IsSmtpAuthorization,IsStartTLS,SMTPHost,SMTPPort,Updated,UpdatedBy) VALUES (0,540003,0,TO_TIMESTAMP('2018-07-10 14:31:34','YYYY-MM-DD HH24:MI:SS'),100,'test@localhost.org','Y','N','Y','localhost',25,TO_TIMESTAMP('2018-07-10 14:31:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-10T15:01:24.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544757,0,TO_TIMESTAMP('2018-07-10 15:01:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.derkurier','Y','Transportauftrag','I',TO_TIMESTAMP('2018-07-10 15:01:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.derkurier.DerKurier_DeliveryOrder_EmailSubject')
;

-- 2018-07-10T15:01:24.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544757 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-07-10T15:01:29.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-10 15:01:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544757 AND AD_Language='de_CH'
;

-- 2018-07-10T15:02:42.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544758,0,TO_TIMESTAMP('2018-07-10 15:02:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.derkurier','Y','Hallo zusammen,
anbei ein Transportauftrag als CSV:

{0}

Mit freundlichen Grüßen','I',TO_TIMESTAMP('2018-07-10 15:02:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.derkurier.DerKurier_DeliveryOrder_EmailMessage')
;

-- 2018-07-10T15:02:42.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544758 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-07-10T15:02:52.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.shipper.gateway.derkurier.DerKurier_DeliveryOrder_EmailMessage_1P',Updated=TO_TIMESTAMP('2018-07-10 15:02:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544758
;

-- 2018-07-10T15:16:52.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Hallo zusammen,
anbei ein Transportauftrag als CSV:


{0}
',Updated=TO_TIMESTAMP('2018-07-10 15:16:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544758
;

-- cleanup
DELETE FROM AD_SysConfig WHERE Name='DerKurier_DeliveryOrder_EmailSubject';
DELETE FROM AD_SysConfig WHERE Name='DerKurier_DeliveryOrder_EmailMessage';
