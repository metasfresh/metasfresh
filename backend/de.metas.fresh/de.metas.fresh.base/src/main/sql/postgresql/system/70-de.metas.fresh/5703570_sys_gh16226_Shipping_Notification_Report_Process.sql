-- Run mode: SWING_CLIENT

-- Value: Shipping Notification (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/sales/shipping_notification/report.jasper
-- 2023-09-20T16:06:07.657Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585317,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-09-20 17:06:06.69','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/sales/shipping_notification/report.jasper',0,'Shipping Notification (Jasper)','json','N','N','xls','JasperReportsSQL',TO_TIMESTAMP('2023-09-20 17:06:06.69','YYYY-MM-DD HH24:MI:SS.US'),100,'Shipping Notification (Jasper)')
;

-- 2023-09-20T16:06:07.734Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585317 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Tab: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification
-- Table: M_Shipping_Notification
-- 2023-09-20T16:09:26.153Z
UPDATE AD_Tab SET AD_Process_ID=585317,Updated=TO_TIMESTAMP('2023-09-20 17:09:26.153','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547218
;

