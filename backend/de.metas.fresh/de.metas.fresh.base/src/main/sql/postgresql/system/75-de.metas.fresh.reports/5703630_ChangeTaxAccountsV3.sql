-- Run mode: SWING_CLIENT

-- Value: Mehrwertsteuer-Verprobung(Jasper) 3
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/tax_accounting_v3/report.jasper
-- 2023-09-21T14:54:43.233Z
UPDATE AD_Process SET EntityType='D', IsDirectPrint='Y', JasperReport_Tabular='@PREFIX@de/metas/reports/tax_accounting_v3/report_TabularView.jasper',Updated=TO_TIMESTAMP('2023-09-21 17:54:43.232','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585167
;

-- Process: Mehrwertsteuer-Verprobung(Jasper) 3(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: ReportFormat
-- 2023-09-21T14:55:38.844Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577492,0,585167,542700,17,541097,'ReportFormat',TO_TIMESTAMP('2023-09-21 17:55:38.701','YYYY-MM-DD HH24:MI:SS.US'),100,'PDF','U',0,'Y','N','Y','N','Y','N','Report format',70,TO_TIMESTAMP('2023-09-21 17:55:38.701','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-21T14:55:38.860Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542700 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-09-21T14:55:38.902Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(577492)
;

-- Process: Mehrwertsteuer-Verprobung(Jasper) 3(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: ReportFormat
-- 2023-09-21T14:55:43.801Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-09-21 17:55:43.801','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542700
;

