-- Value: Bestellanforderung (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/purchase/request/report.jasper
-- 2023-05-22T22:01:43.265616600Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585268,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-05-22 23:01:41.782','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','Y','Y','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/purchase/request/report.jasper',0,'Bestellanforderung (Jasper)','json','N','N','xls','JasperReportsSQL',TO_TIMESTAMP('2023-05-22 23:01:41.782','YYYY-MM-DD HH24:MI:SS.US'),100,'Bestellanforderung (Jasper)')
;

-- 2023-05-22T22:01:43.374606400Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585268 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Bestellanforderung (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintLogo
-- 2023-05-22T22:05:03.564312300Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578551,0,585268,542627,20,'PRINTER_OPTS_IsPrintLogo',TO_TIMESTAMP('2023-05-22 23:05:02.258','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','D',0,'Y','N','Y','N','Y','N','Logo drucken',10,TO_TIMESTAMP('2023-05-22 23:05:02.258','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-22T22:05:03.670435600Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542627 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-05-22T22:05:03.789639900Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(578551) 
;

-- 2023-05-22T22:12:04.735315619Z
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_Table_ID,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,540006,'None',540131,102,711,TO_TIMESTAMP('2023-05-23 00:12:04.726','YYYY-MM-DD HH24:MI:SS.US'),100,0,0,'Y','N','N','Y','Y','Bestellanforderung',TO_TIMESTAMP('2023-05-23 00:12:04.726','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-22T22:12:06.617442176Z
UPDATE AD_PrintFormat SET IsForm='Y',Updated=TO_TIMESTAMP('2023-05-23 00:12:06.617','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540131
;

-- 2023-05-22T22:12:30.297976556Z
UPDATE AD_PrintFormat SET JasperProcess_ID=585268,Updated=TO_TIMESTAMP('2023-05-23 00:12:30.297','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540131
;

-- 2023-05-22T22:13:16.336268833Z
UPDATE C_DocType SET AD_PrintFormat_ID=540131,Updated=TO_TIMESTAMP('2023-05-23 00:13:16.336','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=1000018
;

-- 2023-05-22T22:18:06.537786435Z
UPDATE C_DocType SET PrintName='Bestellanforderung',Updated=TO_TIMESTAMP('2023-05-23 00:18:06.537','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=1000018
;

-- 2023-05-22T22:18:06.543835184Z
UPDATE C_DocType_Trl trl SET PrintName='Bestellanforderung' WHERE C_DocType_ID=1000018 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- Tab: Bestellanforderung(322,D) -> Bedarf
-- Table: M_Requisition
-- 2023-05-24T18:28:17.774227100Z
UPDATE AD_Tab SET AD_Process_ID=585268,Updated=TO_TIMESTAMP('2023-05-24 19:28:17.774','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=641
;