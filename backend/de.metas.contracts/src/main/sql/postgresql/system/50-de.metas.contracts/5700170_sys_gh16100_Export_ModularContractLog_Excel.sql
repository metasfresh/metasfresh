-- Value: ModCntr_Log_Exporter
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/modcntrlog/report.xls
-- 2023-08-23T18:26:44.075621Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585309,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-08-23 21:26:43.88','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.fresh','Y','N','N','Y','Y','N','N','N','Y','Y','N','N','@PREFIX@de/metas/reports/modcntrlog/report.xls',0,'Log für modulare Verträge exportieren','json','N','N','xls','select * from ModCntr_Log
inner join ModCntr_Log_Details_Template_Report_V view ON view.contractmodulelog=ModCntr_Log.modcntr_log_id
WHERE @SELECTION_WHERECLAUSE/false@','JasperReportsSQL',TO_TIMESTAMP('2023-08-23 21:26:43.88','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Log_Exporter')
;

-- 2023-08-23T18:26:44.095620400Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585309 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ModCntr_Log_Exporter(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: ModCntr_Log
-- EntityType: D
-- 2023-08-23T18:27:14.976620100Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585309,542338,541409,TO_TIMESTAMP('2023-08-23 21:27:14.79','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-08-23 21:27:14.79','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: ModCntr_Log_Exporter(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-08-24T18:47:51.544413300Z
UPDATE AD_Process_Trl SET Name='Export Modular Contract Log',Updated=TO_TIMESTAMP('2023-08-24 21:47:51.543','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585309
;
