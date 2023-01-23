-- Value: Delivery instructions(Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/deliveryinstructions/report.jasper
-- 2023-01-23T10:53:36.356Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585190,'N','de.metas.report.jasper.client.process.JasperReportStarter',TO_TIMESTAMP('2023-01-23 12:53:36','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Y','Y','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/deliveryinstructions/report.jasper','Delivery instructions(Jasper)','json','N','N','JasperReportsSQL',TO_TIMESTAMP('2023-01-23 12:53:36','YYYY-MM-DD HH24:MI:SS'),100,'Delivery instructions(Jasper)')
;

-- 2023-01-23T10:53:36.358Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585190 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

