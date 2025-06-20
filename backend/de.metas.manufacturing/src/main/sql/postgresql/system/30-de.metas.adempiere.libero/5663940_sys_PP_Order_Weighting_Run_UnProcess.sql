-- 2022-11-11T01:18:21.506Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585145,'Y','de.metas.manufacturing.order.weighting.process.PP_Order_Weighting_Run_UnProcess','N',TO_TIMESTAMP('2022-11-11 03:18:21.274','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'ReActivate','json','N','N','xls','Java',TO_TIMESTAMP('2022-11-11 03:18:21.274','YYYY-MM-DD HH24:MI:SS.US'),100,'PP_Order_Weighting_Run_UnProcess')
;

-- 2022-11-11T01:18:21.513Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585145 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-11-11T01:18:41.983Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585145,542254,541306,TO_TIMESTAMP('2022-11-11 03:18:41.86','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','Y',TO_TIMESTAMP('2022-11-11 03:18:41.86','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','N','N')
;

