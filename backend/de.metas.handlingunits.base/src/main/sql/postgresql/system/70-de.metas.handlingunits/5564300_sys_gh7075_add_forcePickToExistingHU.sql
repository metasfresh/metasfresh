-- 2020-07-27T11:56:20.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584731,'Y','de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_ForcePickToExistingHU','N',TO_TIMESTAMP('2020-07-27 14:56:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','N','Y','Y',0,'In bestehende HU Kommissionieren','N','N','Java',TO_TIMESTAMP('2020-07-27 14:56:19','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_ForcePickToExistingHU')
;

-- 2020-07-27T11:56:20.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584731 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-07-27T11:57:32.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542492,0,584731,541846,29,'QtyCU',TO_TIMESTAMP('2020-07-27 14:57:32','YYYY-MM-DD HH24:MI:SS'),100,'Menge der CUs pro Einzelgebinde (normalerweise TU)','de.metas.ui.web',0,'Y','N','Y','N','Y','N','Menge CU/TU',10,TO_TIMESTAMP('2020-07-27 14:57:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-27T11:57:32.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541846 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-07-27T11:57:32.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541846
;

-- 2020-07-27T11:57:32.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541846, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541196
;

-- 2020-07-28T13:53:25.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Pick to existing HU',Updated=TO_TIMESTAMP('2020-07-28 16:53:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584731
;

-- 2020-07-29T05:49:53.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544991,0,TO_TIMESTAMP('2020-07-29 08:49:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','No picked HU was found.','I',TO_TIMESTAMP('2020-07-29 08:49:53','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_NoPickedHuFound')
;

-- 2020-07-29T05:49:53.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544991 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-07-29T05:50:34.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544992,0,TO_TIMESTAMP('2020-07-29 08:50:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Picking to existing CUs is not allowed.','E',TO_TIMESTAMP('2020-07-29 08:50:34','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_PickingToExistingCUsNotAllowed')
;

-- 2020-07-29T05:50:34.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544992 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-07-29T05:51:09.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544993,0,TO_TIMESTAMP('2020-07-29 08:51:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Picking to the same HU for multiple orders is not allowed.','E',TO_TIMESTAMP('2020-07-29 08:51:09','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_PickingToTheSameHUForMultipleOrders')
;

-- 2020-07-29T05:51:09.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544993 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-07-29T06:43:47.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Picking to VHUs is not allowed.',Updated=TO_TIMESTAMP('2020-07-29 09:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544992
;

-- 2020-07-29T08:34:24.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Es wurde keine kommissionierte HU gefunden.',Updated=TO_TIMESTAMP('2020-07-29 11:34:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544991
;

-- 2020-07-29T08:34:32.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Es wurde keine kommissionierte HU gefunden.',Updated=TO_TIMESTAMP('2020-07-29 11:34:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544991
;

-- 2020-07-29T08:34:34.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Es wurde keine kommissionierte HU gefunden.',Updated=TO_TIMESTAMP('2020-07-29 11:34:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544991
;

-- 2020-07-29T08:34:44.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Es wurde keine kommissionierte HU gefunden.',Updated=TO_TIMESTAMP('2020-07-29 11:34:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544991
;

-- 2020-07-29T08:35:12.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Das Kommissionieren in virtuelle HUs ist nicht erlaubt.',Updated=TO_TIMESTAMP('2020-07-29 11:35:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544992
;

-- 2020-07-29T08:35:19.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Kommissionieren in virtuelle HUs ist nicht erlaubt.',Updated=TO_TIMESTAMP('2020-07-29 11:35:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544992
;

-- 2020-07-29T08:35:24.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Kommissionieren in virtuelle HUs ist nicht erlaubt.',Updated=TO_TIMESTAMP('2020-07-29 11:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544992
;

-- 2020-07-29T08:35:34.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Picking toVHUs is not allowed.',Updated=TO_TIMESTAMP('2020-07-29 11:35:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544992
;

-- 2020-07-29T08:35:40.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Kommissionieren in virtuelle HUs ist nicht erlaubt.',Updated=TO_TIMESTAMP('2020-07-29 11:35:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544992
;

-- 2020-07-29T08:35:52.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Das Kommissionieren in dieselbe HU für mehrere Aufträge ist nicht erlaubt.',Updated=TO_TIMESTAMP('2020-07-29 11:35:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544993
;

-- 2020-07-29T08:35:56.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Kommissionieren in dieselbe HU für mehrere Aufträge ist nicht erlaubt.',Updated=TO_TIMESTAMP('2020-07-29 11:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544993
;

-- 2020-07-29T08:36:01.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Kommissionieren in dieselbe HU für mehrere Aufträge ist nicht erlaubt.',Updated=TO_TIMESTAMP('2020-07-29 11:36:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544993
;

-- 2020-07-29T08:36:06.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Kommissionieren in dieselbe HU für mehrere Aufträge ist nicht erlaubt.',Updated=TO_TIMESTAMP('2020-07-29 11:36:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544993
;


