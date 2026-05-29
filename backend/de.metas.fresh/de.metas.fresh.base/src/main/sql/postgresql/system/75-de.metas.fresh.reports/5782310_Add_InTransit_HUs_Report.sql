-- Value: InTransit_HUs_Report
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/intransit_hus_report/report.jasper
-- 2025-12-18T19:15:10.881Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585548,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2025-12-18 19:15:10.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','U','Y','N','N','Y','Y','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/reports/intransit_hus_report/report.jasper',0,'Materialbegleitschein','json','N','N','JasperReportsSQL',TO_TIMESTAMP('2025-12-18 19:15:10.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'InTransit_HUs_Report')
;

-- 2025-12-18T19:15:10.888Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585548 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: InTransit_HUs_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-12-18T19:15:51.632Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-18 19:15:51.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585548
;

-- Process: InTransit_HUs_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-12-18T19:15:53.630Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-18 19:15:53.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585548
;

-- Process: InTransit_HUs_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-12-18T19:15:57.872Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Material accompanying slip',Updated=TO_TIMESTAMP('2025-12-18 19:15:57.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585548
;

-- 2025-12-18T19:15:57.873Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: DD_Order_MaterialInTransitReport
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/intransit_hus_report/report.jasper
-- 2025-12-18T19:17:01.314Z
UPDATE AD_Process SET Value='DD_Order_MaterialInTransitReport',Updated=TO_TIMESTAMP('2025-12-18 19:17:01.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585548
;

