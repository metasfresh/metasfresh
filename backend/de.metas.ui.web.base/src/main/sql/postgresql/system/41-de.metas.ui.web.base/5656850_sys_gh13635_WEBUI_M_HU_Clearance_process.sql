-- 2022-09-20T09:31:15.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585110,'Y','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Clearance','N',TO_TIMESTAMP('2022-09-20 12:31:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','Y','N','N','N','Y','Y',0,'Clearance','json','N','N','xls','Java',TO_TIMESTAMP('2022-09-20 12:31:15','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_HU_Clearance')
;

-- 2022-09-20T09:31:15.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585110 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-09-20T09:31:27.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Freigabe',Updated=TO_TIMESTAMP('2022-09-20 12:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585110
;

-- 2022-09-20T09:31:33.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Freigabe',Updated=TO_TIMESTAMP('2022-09-20 12:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585110
;

-- 2022-09-20T09:31:33.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Freigabe',Updated=TO_TIMESTAMP('2022-09-20 12:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585110
;

-- 2022-09-20T09:34:02.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585110,542306,17,541540,'ClearanceStatus',TO_TIMESTAMP('2022-09-20 12:34:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web',0,'Y','N','Y','N','Y','N','Clearance Status',10,TO_TIMESTAMP('2022-09-20 12:34:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-20T09:34:02.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542306 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-09-20T09:36:04.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,580556,0,585110,542307,10,'ClearanceNote',TO_TIMESTAMP('2022-09-20 12:36:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','N','N','Freigabe-Notiz',20,TO_TIMESTAMP('2022-09-20 12:36:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-20T09:36:04.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542307 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-09-20T09:36:47.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=580555, DefaultValue='C', EntityType='de.metas.handlingunits', Name='Freigabe',Updated=TO_TIMESTAMP('2022-09-20 12:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542306
;

-- 2022-09-20T09:39:22.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585110,540516,541282,TO_TIMESTAMP('2022-09-20 12:39:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2022-09-20 12:39:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2022-09-20T10:44:33.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2022-09-20 13:44:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541282
;

-- 2022-09-20T11:48:04.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2022-09-20 14:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585110
;

-- 2022-09-20T12:03:39.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Help='Updates the clearance status of the selected HUs propagating the change to all child HUs included in the hierarchy.',Updated=TO_TIMESTAMP('2022-09-20 15:03:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585110
;

-- 2022-09-20T12:07:29.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help='Aktualisiert den Freigabestatus der ausgewählten HUs und überträgt die Änderung auf alle untergeordneten HUs in der Hierarchie.', Name='Freigabe',Updated=TO_TIMESTAMP('2022-09-20 15:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585110
;

-- 2022-09-20T12:07:29.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Aktualisiert den Freigabestatus der ausgewählten HUs und überträgt die Änderung auf alle untergeordneten HUs in der Hierarchie.',Updated=TO_TIMESTAMP('2022-09-20 15:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585110
;

-- 2022-09-20T12:07:59.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Aktualisiert den Freigabestatus der ausgewählten HUs und überträgt die Änderung auf alle untergeordneten HUs in der Hierarchie.',Updated=TO_TIMESTAMP('2022-09-20 15:07:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585110
;

-- 2022-09-20T12:08:06.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Updates the clearance status of the selected HUs propagating the change to all child HUs included in the hierarchy.',Updated=TO_TIMESTAMP('2022-09-20 15:08:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585110
;

-- 2022-09-20T12:38:38.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Name='Freigabe',Updated=TO_TIMESTAMP('2022-09-20 15:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542306
;

-- 2022-09-20T12:38:48.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Name='Freigabe',Updated=TO_TIMESTAMP('2022-09-20 15:38:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_Para_ID=542306
;

-- 2022-09-20T12:41:09.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Name='Freigabe',Updated=TO_TIMESTAMP('2022-09-20 15:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542306
;

-- 2022-09-20T12:41:09.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Name='Clearance Note',Updated=TO_TIMESTAMP('2022-09-20 15:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542307
;

-- 2022-09-20T12:48:58.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Cleared',Updated=TO_TIMESTAMP('2022-09-20 15:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543121
;

-- 2022-09-20T12:49:10.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Locked',Updated=TO_TIMESTAMP('2022-09-20 15:49:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543122
;

-- 2022-09-20T12:49:18.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Quarantined',Updated=TO_TIMESTAMP('2022-09-20 15:49:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543123
;

-- 2022-09-20T14:05:08.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Der Prozess aktualisiert den Freigabestatus der ausgewählten HUs und überträgt die Änderung auf alle enthaltenen Kind-HUs.',Updated=TO_TIMESTAMP('2022-09-20 17:05:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585110
;

-- 2022-09-20T14:05:16.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help='Der Prozess aktualisiert den Freigabestatus der ausgewählten HUs und überträgt die Änderung auf alle enthaltenen Kind-HUs.', Name='Freigabe',Updated=TO_TIMESTAMP('2022-09-20 17:05:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585110
;

-- 2022-09-20T14:05:16.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Der Prozess aktualisiert den Freigabestatus der ausgewählten HUs und überträgt die Änderung auf alle enthaltenen Kind-HUs.',Updated=TO_TIMESTAMP('2022-09-20 17:05:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585110
;

-- 2022-09-20T14:05:23.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='The process updates the clearance status of the selected HUs propagating the change to all included HUs.',Updated=TO_TIMESTAMP('2022-09-20 17:05:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585110
;

-- 2022-09-20T14:14:01.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Der Prozess aktualisiert den Freigabestatus der ausgewählten HUs und überträgt die Änderung auf alle enthaltenen Kind-HUs.',Updated=TO_TIMESTAMP('2022-09-20 17:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585110
;

-- 2022-09-20T14:18:17.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Der Prozess aktualisiert den Freigabestatus der ausgewählten HUs und überträgt die Änderung auf alle enthaltenen Kind-HUs.',Updated=TO_TIMESTAMP('2022-09-20 17:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585110
;

-- 2022-09-20T14:18:19.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Der Prozess aktualisiert den Freigabestatus der ausgewählten HUs und überträgt die Änderung auf alle enthaltenen Kind-HUs.',Updated=TO_TIMESTAMP('2022-09-20 17:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585110
;

-- 2022-09-20T14:18:23.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='The process updates the clearance status of the selected HUs propagating the change to all included HUs.',Updated=TO_TIMESTAMP('2022-09-20 17:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585110
;

-- 2022-09-21T10:11:29.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Freigegeben',Updated=TO_TIMESTAMP('2022-09-21 13:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543121
;

-- 2022-09-21T10:13:39.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Gesperrt',Updated=TO_TIMESTAMP('2022-09-21 13:13:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543122
;

-- 2022-09-21T10:13:54.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='In Quarantäne',Updated=TO_TIMESTAMP('2022-09-21 13:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543123
;
