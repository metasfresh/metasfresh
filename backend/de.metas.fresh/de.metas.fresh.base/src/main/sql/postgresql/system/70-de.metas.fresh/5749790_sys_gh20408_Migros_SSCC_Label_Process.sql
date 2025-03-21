-- Value: Migros SSCC (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/sscc/label_migros.jasper
-- 2025-03-21T08:08:25.959Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585458,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2025-03-21 08:08:25.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.fresh','Y','N','N','Y','Y','N','N','N','Y','N','N','Y','@PREFIX@de/metas/docs/label/sscc/label_migros.jasper',0,'Migros SSCC','json','N','N','JasperReportsSQL',TO_TIMESTAMP('2025-03-21 08:08:25.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Migros SSCC (Jasper)')
;

-- 2025-03-21T08:08:26.010Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585458 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Migros SSCC (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: M_HU
-- EntityType: de.metas.fresh
-- 2025-03-21T08:09:06.066Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585458,540516,541549,TO_TIMESTAMP('2025-03-21 08:09:05.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.fresh','Y',TO_TIMESTAMP('2025-03-21 08:09:05.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: Migros SSCC (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: M_HU
-- Window: Handling Unit(540189,de.metas.handlingunits)
-- EntityType: de.metas.fresh
-- 2025-03-21T08:15:51.335Z
UPDATE AD_Table_Process SET AD_Window_ID=540189,Updated=TO_TIMESTAMP('2025-03-21 08:15:51.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541549
;

-- Process: Migros SSCC (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: M_HU
-- Window: Handling Unit(540189,de.metas.handlingunits)
-- EntityType: de.metas.fresh
-- 2025-03-21T08:17:37.941Z
UPDATE AD_Table_Process SET WEBUI_IncludedTabTopAction='Y', WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2025-03-21 08:17:37.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541549
;

-- Value: Migros SSCC (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/sscc/label_migros.jasper
-- 2025-03-21T08:19:09.053Z
UPDATE AD_Process SET IsDirectPrint='N',Updated=TO_TIMESTAMP('2025-03-21 08:19:08.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585458
;

