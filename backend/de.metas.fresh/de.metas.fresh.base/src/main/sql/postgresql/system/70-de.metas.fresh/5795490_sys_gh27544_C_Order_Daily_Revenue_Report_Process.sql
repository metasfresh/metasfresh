-- Run mode: SWING_CLIENT

-- Value: C_Order_Daily_Revenue_Report
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/sales/daily_revenue/report.jasper
-- 2026-03-24T17:48:39.956Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585601,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2026-03-24 17:48:39.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Agesübersicht des Netto-Auftragsumsatzes, des Rechnungsumsatzes aus Debitoren- und Kreditorenrechnungen sowie der daraus resultierenden Marge in € und %.','D','Y','N','N','N','Y','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/sales/daily_revenue/report.jasper',0,'Tagesumsatz','json','N','N','xls','JasperReportsSQL',TO_TIMESTAMP('2026-03-24 17:48:39.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Order_Daily_Revenue_Report')
;

-- 2026-03-24T17:48:40.032Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585601 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: AD_Client_ID
-- 2026-03-24T17:54:36.262Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,102,0,585601,543163,30,'AD_Client_ID',TO_TIMESTAMP('2026-03-24 17:54:35.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@#AD_Client_ID@','Mandant für diese Installation.','1=2','D',0,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','N','Mandant',10,TO_TIMESTAMP('2026-03-24 17:54:35.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-24T17:54:36.324Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543163 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: AD_Org_ID
-- 2026-03-24T17:55:11.332Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,585601,543164,30,'AD_Org_ID',TO_TIMESTAMP('2026-03-24 17:55:10.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','D',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Sektion',20,TO_TIMESTAMP('2026-03-24 17:55:10.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-24T17:55:11.420Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543164 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: DateFrom
-- 2026-03-24T17:56:24.112Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,585601,543165,15,'DateFrom',TO_TIMESTAMP('2026-03-24 17:56:23.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Startdatum eines Abschnittes','D',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','N','N','Datum von',30,TO_TIMESTAMP('2026-03-24 17:56:23.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-24T17:56:24.190Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543165 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: DateTo
-- 2026-03-24T17:56:51.923Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,585601,543166,15,'DateTo',TO_TIMESTAMP('2026-03-24 17:56:51.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enddatum eines Abschnittes','D',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','N','N','Datum bis',40,TO_TIMESTAMP('2026-03-24 17:56:51.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-24T17:56:51.983Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543166 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_Customer_ID
-- 2026-03-24T17:58:03.941Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541356,0,585601,543167,30,173,'C_BPartner_Customer_ID',TO_TIMESTAMP('2026-03-24 17:58:03.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',0,'Y','N','Y','N','N','N','Kunde',50,TO_TIMESTAMP('2026-03-24 17:58:03.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-24T17:58:04.027Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543167 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: C_BP_Customer_Group
-- 2026-03-24T18:19:31.380Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542082,TO_TIMESTAMP('2026-03-24 18:19:31.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_BP_Customer_Group',TO_TIMESTAMP('2026-03-24 18:19:31.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2026-03-24T18:19:31.450Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542082 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_BP_Customer_Group
-- Table: C_BP_Group
-- Key: C_BP_Group.C_BP_Group_ID
-- 2026-03-24T18:20:54.325Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,4961,0,542082,394,TO_TIMESTAMP('2026-03-24 18:20:53.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2026-03-24 18:20:53.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BP_Group.IsCustomer=''Y''')
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Customer_Group_ID
-- 2026-03-24T18:21:11.927Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542888,0,585601,543168,30,542082,'Customer_Group_ID',TO_TIMESTAMP('2026-03-24 18:21:11.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat',0,'Y','N','Y','N','N','N','Kundengruppe',60,TO_TIMESTAMP('2026-03-24 18:21:11.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-24T18:21:12.007Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543168 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Vendor_ID
-- 2026-03-24T18:21:57.217Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1347,0,585601,543169,30,192,'Vendor_ID',TO_TIMESTAMP('2026-03-24 18:21:56.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Vendor of the product/service','D',0,'Y','N','Y','N','N','N','Lieferant',70,TO_TIMESTAMP('2026-03-24 18:21:56.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-24T18:21:57.277Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543169 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-03-24T18:23:22.986Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584695,0,'Vendor_Group_ID',TO_TIMESTAMP('2026-03-24 18:23:22.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Lieferantengruppe','Lieferantengruppe',TO_TIMESTAMP('2026-03-24 18:23:22.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-24T18:23:23.056Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584695 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Vendor_Group_ID
-- 2026-03-24T18:24:26.396Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vendor Group', PrintName='Vendor Group',Updated=TO_TIMESTAMP('2026-03-24 18:24:26.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584695 AND AD_Language='en_US'
;

-- 2026-03-24T18:24:26.457Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-24T18:24:43.608Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584695,'en_US')
;

-- Name: C_BP_Vendor_Group
-- 2026-03-24T18:25:44.036Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542083,TO_TIMESTAMP('2026-03-24 18:25:43.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_BP_Vendor_Group',TO_TIMESTAMP('2026-03-24 18:25:43.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2026-03-24T18:25:44.101Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542083 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_BP_Vendor_Group
-- Table: C_BP_Group
-- Key: C_BP_Group.C_BP_Group_ID
-- 2026-03-24T18:26:20.715Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,4961,0,542083,394,TO_TIMESTAMP('2026-03-24 18:26:20.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2026-03-24 18:26:20.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BP_Group.IsCustomer=''N''')
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Vendor_Group_ID
-- 2026-03-24T18:26:37.606Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584695,0,585601,543170,30,542083,'Vendor_Group_ID',TO_TIMESTAMP('2026-03-24 18:26:37.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',0,'Y','N','Y','N','N','N','Lieferantengruppe',80,TO_TIMESTAMP('2026-03-24 18:26:37.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-24T18:26:37.666Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543170 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: C_Order
-- EntityType: D
-- 2026-03-24T18:27:40.737Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585601,259,541635,TO_TIMESTAMP('2026-03-24 18:27:39.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2026-03-24 18:27:39.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-03-24T18:56:54.385Z
UPDATE AD_Process_Trl SET Description='Daily overview of net order revenue, accounts receivable and accounts payable invoice totals, and the resulting margin in € and %', IsTranslated='Y', Name='Daily Revenue',Updated=TO_TIMESTAMP('2026-03-24 18:56:54.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585601
;

-- 2026-03-24T18:56:54.447Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: AD_Client_ID
-- 2026-03-25T13:34:06.941Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=543163
;

-- 2026-03-25T13:34:07.292Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=543163
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: AD_Org_ID
-- 2026-03-25T13:34:51.855Z
UPDATE AD_Process_Para SET DefaultValue='@#AD_Org_ID/0@', DisplayLogic='1=2',Updated=TO_TIMESTAMP('2026-03-25 13:34:51.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543164
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Vendor_ID
-- 2026-03-25T13:35:21.111Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=543169
;

-- 2026-03-25T13:35:21.462Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=543169
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Vendor_Group_ID
-- 2026-03-25T13:35:25.788Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=543170
;

-- 2026-03-25T13:35:26.157Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=543170
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_ID
-- 2026-03-25T13:36:12.770Z
UPDATE AD_Process_Para SET AD_Element_ID=187, AD_Reference_Value_ID=138, ColumnName='C_BPartner_ID', Description='Bezeichnet einen Geschäftspartner', Help='Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.', Name='Geschäftspartner',Updated=TO_TIMESTAMP('2026-03-25 13:36:12.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543167
;

-- 2026-03-25T13:36:12.831Z
UPDATE AD_Process_Para_Trl trl SET Description='Bezeichnet einen Geschäftspartner',Help='Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',Name='Geschäftspartner' WHERE AD_Process_Para_ID=543167 AND AD_Language='de_DE'
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BP_Group_ID
-- 2026-03-25T13:36:46.428Z
UPDATE AD_Process_Para SET AD_Element_ID=1383, AD_Reference_Value_ID=NULL, ColumnName='C_BP_Group_ID', Description='Geschäftspartnergruppe', Help='Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.', Name='Geschäftspartnergruppe',Updated=TO_TIMESTAMP('2026-03-25 13:36:46.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543168
;

-- 2026-03-25T13:36:46.489Z
UPDATE AD_Process_Para_Trl trl SET Description='Geschäftspartnergruppe',Help='Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.',Name='Geschäftspartnergruppe' WHERE AD_Process_Para_ID=543168 AND AD_Language='de_DE'
;

-- Value: C_Order_Daily_Revenue_Report
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/sales/daily_revenue/report.jasper
-- 2026-03-25T15:04:26.546Z
UPDATE AD_Process SET Description='Tagesübersicht des Netto-Auftragsumsatzes, des Rechnungsumsatzes aus Debitoren- und Kreditorenrechnungen sowie der daraus resultierenden Marge in € und %.',Updated=TO_TIMESTAMP('2026-03-25 15:04:26.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585601
;

-- 2026-03-25T15:04:26.617Z
UPDATE AD_Process_Trl trl SET Description='Tagesübersicht des Netto-Auftragsumsatzes, des Rechnungsumsatzes aus Debitoren- und Kreditorenrechnungen sowie der daraus resultierenden Marge in € und %.' WHERE AD_Process_ID=585601 AND AD_Language='de_DE'
;

-- Value: C_Order_Daily_Revenue_Report
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/sales/daily_revenue/report.jasper
-- 2026-03-25T15:07:55.664Z
UPDATE AD_Process SET Description='Tagesübersicht des Netto-Auftragsumsatzes, des Rechnungsumsatzes aus Debitoren- und Kreditorenrechnungen sowie der daraus resultierenden Marge.',Updated=TO_TIMESTAMP('2026-03-25 15:07:55.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585601
;

-- 2026-03-25T15:07:55.723Z
UPDATE AD_Process_Trl trl SET Description='Tagesübersicht des Netto-Auftragsumsatzes, des Rechnungsumsatzes aus Debitoren- und Kreditorenrechnungen sowie der daraus resultierenden Marge.' WHERE AD_Process_ID=585601 AND AD_Language='de_DE'
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-03-25T15:08:09.082Z
UPDATE AD_Process_Trl SET Description='Tagesübersicht des Netto-Auftragsumsatzes, des Rechnungsumsatzes aus Debitoren- und Kreditorenrechnungen sowie der daraus resultierenden Marge.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-25 15:08:09.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585601
;

-- 2026-03-25T15:08:09.140Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-03-25T15:08:16.604Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-25 15:08:16.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585601
;

-- Process: C_Order_Daily_Revenue_Report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-03-25T15:08:26.769Z
UPDATE AD_Process_Trl SET Description='Daily overview of net order revenue, accounts receivable and accounts payable invoice totals, and the resulting margin.',Updated=TO_TIMESTAMP('2026-03-25 15:08:26.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585601
;

-- 2026-03-25T15:08:26.828Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T15:29:13.721Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=584695
;

-- 2026-03-26T15:29:14.070Z
DELETE FROM AD_Element WHERE AD_Element_ID=584695
;

-- Name: C_BP_Vendor_Group
-- 2026-03-26T15:30:50.868Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=542083
;

-- 2026-03-26T15:30:51.229Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=542083
;
