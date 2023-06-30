-- 2022-01-28T16:03:12.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584972,'Y','de.metas.ui.web.handlingunits.process.M_HU_SyncTo_GRS_HTTP','N',TO_TIMESTAMP('2022-01-28 18:03:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','Y','N','N','N','Y','Y',0,'An GRS senden','json','N','N','xls','Java',TO_TIMESTAMP('2022-01-28 18:03:12','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_SyncTo_GRS_HTTP')
;

-- 2022-01-28T16:03:12.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584972 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-01-28T16:03:30.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Send to GRS',Updated=TO_TIMESTAMP('2022-01-28 18:03:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584972
;

-- 2022-01-28T16:05:00.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,579956,0,584972,542185,18,541497,'ExternalSystem_Config_GRSSignum_ID',TO_TIMESTAMP('2022-01-28 18:05:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web',10,'Y','N','Y','N','Y','N','GRSSignum Config',10,TO_TIMESTAMP('2022-01-28 18:05:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-28T16:05:00.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542185 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-01-28T16:06:51.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584972,540516,541050,TO_TIMESTAMP('2022-01-28 18:06:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2022-01-28 18:06:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2022-01-31T14:18:02.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten HUs an die entsprechende GRS REST API-Konfiguration. Sobald eine HU an ein externes System gesendet wurde, wird sie automatisch erneut gesendet, wenn eine Transformation in metasfresh durchgeführt wird, sofern das entsprechende Kontrollkästchen in der Konfiguration des externen Systems aktiviert ist',Updated=TO_TIMESTAMP('2022-01-31 16:18:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584972
;

-- 2022-01-31T14:18:06.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Sendet die ausgewählten HUs an die entsprechende GRS REST API-Konfiguration. Sobald eine HU an ein externes System gesendet wurde, wird sie automatisch erneut gesendet, wenn eine Transformation in metasfresh durchgeführt wird, sofern das entsprechende Kontrollkästchen in der Konfiguration des externen Systems aktiviert ist', Help=NULL, Name='An GRS senden',Updated=TO_TIMESTAMP('2022-01-31 16:18:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584972
;

-- 2022-01-31T14:18:06.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten HUs an die entsprechende GRS REST API-Konfiguration. Sobald eine HU an ein externes System gesendet wurde, wird sie automatisch erneut gesendet, wenn eine Transformation in metasfresh durchgeführt wird, sofern das entsprechende Kontrollkästchen in der Konfiguration des externen Systems aktiviert ist',Updated=TO_TIMESTAMP('2022-01-31 16:18:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584972
;

-- 2022-01-31T14:18:11.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten HUs an die entsprechende GRS REST API-Konfiguration. Sobald eine HU an ein externes System gesendet wurde, wird sie automatisch erneut gesendet, wenn eine Transformation in metasfresh durchgeführt wird, sofern das entsprechende Kontrollkästchen in der Konfiguration des externen Systems aktiviert ist',Updated=TO_TIMESTAMP('2022-01-31 16:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584972
;

-- 2022-01-31T14:18:15.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sends the selected HUs to the corresponding GRS REST API config. Once an HU was send to an external system, it will be automatically resend whenever a transform operation is done in metasfresh, as long as the corresponding checkbox is ticked in the external system config.',Updated=TO_TIMESTAMP('2022-01-31 16:18:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584972
;

