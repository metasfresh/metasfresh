-- Run mode: SWING_CLIENT

-- Value: C_Order_SSCC_Print
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/sscc/label.jasper
-- 2025-08-18T13:08:21.833Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585487,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2025-08-18 13:08:20.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','N','N','N','Y','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/label/sscc/label.jasper',0,'C_Order_SSCC_Print','json','N','N','xls','JasperReportsSQL',TO_TIMESTAMP('2025-08-18 13:08:20.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Order_SSCC_Print')
;

-- 2025-08-18T13:08:21.839Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585487 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Run mode: SWING_CLIENT

-- Process: C_Order_SSCC_Print(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: C_Order
-- EntityType: D
-- 2025-08-19T09:41:12.044Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585487,259,541562,TO_TIMESTAMP('2025-08-19 09:41:11.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-08-19 09:41:11.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N')
;

-- Value: C_Order_SSCC_Print_Jasper
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/sscc/label.jasper
-- 2025-08-19T12:22:35.344Z
UPDATE AD_Process SET Value='C_Order_SSCC_Print_Jasper',Updated=TO_TIMESTAMP('2025-08-19 12:22:35.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585487
;

-- Process: C_Order_SSCC_Print_Jasper(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: C_Order
-- EntityType: D
-- 2025-08-19T12:43:51.146Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541562
;

-- Value: C_Order_SSCC_Print
-- Classname: de.metas.shipping.process.C_Order_SSCC_Print
-- JasperReport: @PREFIX@de/metas/docs/label/sscc/label.jasper
-- 2025-08-19T12:45:52.477Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585489,'N','de.metas.shipping.process.C_Order_SSCC_Print','N',TO_TIMESTAMP('2025-08-19 12:45:51.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y','@PREFIX@de/metas/docs/label/sscc/label.jasper',0,'C_Order_SSCC_Print','json','N','N','xls','Java',TO_TIMESTAMP('2025-08-19 12:45:51.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Order_SSCC_Print')
;

-- 2025-08-19T12:45:52.480Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585489 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Order_SSCC_Print(de.metas.shipping.process.C_Order_SSCC_Print)
-- Table: C_Order
-- EntityType: D
-- 2025-08-19T12:46:15.121Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585489,259,541563,TO_TIMESTAMP('2025-08-19 12:46:14.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-08-19 12:46:14.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N')
;

-- Value: C_Order_SSCC_Print_Jasper
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/sscc/label.jasper
-- 2025-08-19T12:46:48.306Z
UPDATE AD_Process SET Name='C_Order_SSCC_Print_Jasper',Updated=TO_TIMESTAMP('2025-08-19 12:46:48.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585487
;

-- 2025-08-19T12:46:48.307Z
UPDATE AD_Process_Trl trl SET Name='C_Order_SSCC_Print_Jasper' WHERE AD_Process_ID=585487 AND AD_Language='de_DE'
;

