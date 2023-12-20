-- 2023-05-16T17:22:48.339Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585265,'Y','de.metas.handlingunits.shipmentschedule.process.M_ShipmentSchedule_Unpick','N',TO_TIMESTAMP('2023-05-16 20:22:48.085','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Kommissionierung rückgängig machen','json','N','N','xls','Java',TO_TIMESTAMP('2023-05-16 20:22:48.085','YYYY-MM-DD HH24:MI:SS.US'),100,'M_ShipmentSchedule_Unpick')
;

-- 2023-05-16T17:22:48.360Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585265 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-05-16T17:22:55.990Z
UPDATE AD_Process_Trl SET Name='Unpick',Updated=TO_TIMESTAMP('2023-05-16 20:22:55.99','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585265
;

-- 2023-05-16T17:24:31.951Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585265,500221,541384,TO_TIMESTAMP('2023-05-16 20:24:31.792','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2023-05-16 20:24:31.792','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','N','N')
;

