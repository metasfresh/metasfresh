-- Value: ImportDeliveryPlanning
-- Classname: de.metas.deliveryplanning.impexp.process.ImportDeliveryPlanning
-- 2023-02-02T23:35:35.754Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585210,'Y','de.metas.deliveryplanning.impexp.process.ImportDeliveryPlanning','N',TO_TIMESTAMP('2023-02-03 01:35:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Import Delivery Planning Updates','json','N','N','xls','Java',TO_TIMESTAMP('2023-02-03 01:35:35','YYYY-MM-DD HH24:MI:SS'),100,'ImportDeliveryPlanning')
;

-- 2023-02-02T23:35:35.758Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585210 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ImportDeliveryPlanning(de.metas.deliveryplanning.impexp.process.ImportDeliveryPlanning)
-- 2023-02-02T23:37:06.120Z
UPDATE AD_Process_Trl SET Name='Aktualisierungen der Importlieferplanung',Updated=TO_TIMESTAMP('2023-02-03 01:37:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585210
;

-- Process: ImportDeliveryPlanning(de.metas.deliveryplanning.impexp.process.ImportDeliveryPlanning)
-- 2023-02-02T23:37:07.531Z
UPDATE AD_Process_Trl SET Name='Aktualisierungen der Importlieferplanung',Updated=TO_TIMESTAMP('2023-02-03 01:37:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585210
;

-- 2023-02-02T23:37:07.532Z
UPDATE AD_Process SET Name='Aktualisierungen der Importlieferplanung' WHERE AD_Process_ID=585210
;

-- Process: ImportDeliveryPlanning(de.metas.deliveryplanning.impexp.process.ImportDeliveryPlanning)
-- 2023-02-02T23:37:09.502Z
UPDATE AD_Process_Trl SET Name='Aktualisierungen der Importlieferplanung',Updated=TO_TIMESTAMP('2023-02-03 01:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585210
;

-- Process: ImportDeliveryPlanning(de.metas.deliveryplanning.impexp.process.ImportDeliveryPlanning)
-- 2023-02-02T23:37:11.626Z
UPDATE AD_Process_Trl SET Name='Aktualisierungen der Importlieferplanung',Updated=TO_TIMESTAMP('2023-02-03 01:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585210
;

-- Value: ImportDeliveryPlanning
-- Classname: de.metas.deliveryplanning.impexp.process.ImportDeliveryPlanning
-- 2023-02-02T23:37:32.783Z
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2023-02-03 01:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585210
;

-- Process: ImportDeliveryPlanning(de.metas.deliveryplanning.impexp.process.ImportDeliveryPlanning)
-- Table: I_DeliveryPlanning_Data
-- EntityType: D
-- 2023-02-02T23:38:09.957Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585210,542290,541352,TO_TIMESTAMP('2023-02-03 01:38:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-02-03 01:38:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

