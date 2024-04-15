-- Run mode: SWING_CLIENT

-- Value: SplitShipmentView_RemoveRows
-- Classname: de.metas.ui.web.split_shipment.SplitShipmentView_RemoveRows
-- 2024-04-09T11:36:29.187Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585377,'Y','de.metas.ui.web.split_shipment.SplitShipmentView_RemoveRows','N',TO_TIMESTAMP('2024-04-09 14:36:28.918','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Remove','json','N','N','xls','Java',TO_TIMESTAMP('2024-04-09 14:36:28.918','YYYY-MM-DD HH24:MI:SS.US'),100,'SplitShipmentView_RemoveRows')
;

-- 2024-04-09T11:36:29.207Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585377 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: SplitShipmentView_ProcessAllRows
-- Classname: de.metas.ui.web.split_shipment.SplitShipmentView_ProcessAllRows
-- 2024-04-09T11:37:14.682Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585378,'Y','de.metas.ui.web.split_shipment.SplitShipmentView_ProcessAllRows','N',TO_TIMESTAMP('2024-04-09 14:37:14.522','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Process all','json','N','N','xls','Java',TO_TIMESTAMP('2024-04-09 14:37:14.522','YYYY-MM-DD HH24:MI:SS.US'),100,'SplitShipmentView_ProcessAllRows')
;

-- 2024-04-09T11:37:14.685Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585378 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

