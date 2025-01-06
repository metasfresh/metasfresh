-- Run mode: SWING_CLIENT

-- Value: POS(Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/sales/pos/report.jasper
-- 2024-10-02T06:41:28.013Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585422,'N','de.metas.report.jasper.client.process.JasperReportStarter',TO_TIMESTAMP('2024-10-02 09:41:27.803','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','Y','Y','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/sales/pos/report.jasper','POS(Jasper)','json','N','N','JasperReportsSQL',TO_TIMESTAMP('2024-10-02 09:41:27.803','YYYY-MM-DD HH24:MI:SS.US'),100,'POS(Jasper)')
;

-- 2024-10-02T06:41:28.038Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585422 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

update ad_tab set ad_process_id=585422 where ad_tab_id=547591;