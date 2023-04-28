-- 2022-03-22T10:17:06.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585027,'Y','de.metas.externalsystem.rabbitmqhttp.export.externalreference.S_ExternalReference_SyncTo_RabbitMQ_HTTP','N',TO_TIMESTAMP('2022-03-22 12:17:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Send external reference to RabbitMQ','json','N','N','xls','Java',TO_TIMESTAMP('2022-03-22 12:17:05','YYYY-MM-DD HH24:MI:SS'),100,'Send external reference to RabbitMQ')
;

-- 2022-03-22T10:17:06.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585027 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-03-22T10:23:12.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,579644,0,585027,542230,18,541400,'ExternalSystem_Config_RabbitMQ_HTTP_ID',TO_TIMESTAMP('2022-03-22 12:23:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',7,'Y','N','Y','N','Y','N','RabbitMQ REST API Konfig',10,TO_TIMESTAMP('2022-03-22 12:23:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-22T10:23:12.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542230 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-03-22T14:18:24.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585027,541486,541098,TO_TIMESTAMP('2022-03-22 16:18:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y',TO_TIMESTAMP('2022-03-22 16:18:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;


-- 2022-03-24T11:41:00.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sends the selected external references to the selected RabbitMQ REST API config. Once an external reference was sent, it will be automatically resent when changed, as long as the external system config says so. Supported external reference types to export are: BPartner, BPartnerLocation and UserID.',Updated=TO_TIMESTAMP('2022-03-24 13:41:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585027
;

-- 2022-03-24T11:41:11.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Sends the selected external references to the selected RabbitMQ REST API config. Once an external reference was sent, it will be automatically resent when changed, as long as the external system config says so. Supported external reference types to export are: BPartner, BPartnerLocation and UserID.',Updated=TO_TIMESTAMP('2022-03-24 13:41:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585027
;

-- 2022-03-24T11:41:34.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten externen Referenzen an die ausgewählte RabbitMQ REST API Konfiguration. Sobald eine externe Referenz gesendet wurde, wird sie automatisch erneut gesendet, wenn sie geändert wird, sofern die externe Systemkonfiguration dies vorsieht. Unterstützte externe Referenztypen für den Export sind: BPartner, BPartnerLocation und UserID.', Name='Externe Referenz an RabbitMQ senden',Updated=TO_TIMESTAMP('2022-03-24 13:41:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585027
;

-- 2022-03-24T11:41:41.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Sendet die ausgewählten externen Referenzen an die ausgewählte RabbitMQ REST API Konfiguration. Sobald eine externe Referenz gesendet wurde, wird sie automatisch erneut gesendet, wenn sie geändert wird, sofern die externe Systemkonfiguration dies vorsieht. Unterstützte externe Referenztypen für den Export sind: BPartner, BPartnerLocation und UserID.', Help=NULL, Name='Externe Referenz an RabbitMQ senden',Updated=TO_TIMESTAMP('2022-03-24 13:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585027
;

-- 2022-03-24T11:41:41.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten externen Referenzen an die ausgewählte RabbitMQ REST API Konfiguration. Sobald eine externe Referenz gesendet wurde, wird sie automatisch erneut gesendet, wenn sie geändert wird, sofern die externe Systemkonfiguration dies vorsieht. Unterstützte externe Referenztypen für den Export sind: BPartner, BPartnerLocation und UserID.', Name='Externe Referenz an RabbitMQ senden',Updated=TO_TIMESTAMP('2022-03-24 13:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585027
;

-- 2022-03-24T11:42:56.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Externe Referenz an RabbitMQ senden',Updated=TO_TIMESTAMP('2022-03-24 13:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585027
;

-- 2022-03-24T11:43:01.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten externen Referenzen an die ausgewählte RabbitMQ REST API Konfiguration. Sobald eine externe Referenz gesendet wurde, wird sie automatisch erneut gesendet, wenn sie geändert wird, sofern die externe Systemkonfiguration dies vorsieht. Unterstützte externe Referenztypen für den Export sind: BPartner, BPartnerLocation und UserID.',Updated=TO_TIMESTAMP('2022-03-24 13:43:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585027
;

