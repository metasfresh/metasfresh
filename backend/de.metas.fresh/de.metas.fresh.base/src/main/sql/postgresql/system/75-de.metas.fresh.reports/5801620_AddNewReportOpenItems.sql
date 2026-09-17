-- Process: Offene Posten (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-14T06:28:36.377Z
UPDATE AD_Process_Trl SET Description='Open items (Document Based)', IsTranslated='Y', Name='Open items (Document Based)',Updated=TO_TIMESTAMP('2026-05-14 06:28:36.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Process_ID=500174
;

-- 2026-05-14T06:28:36.385Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Offene Posten (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-14T06:28:39.826Z
UPDATE AD_Process_Trl SET Description='Open items (Document Based)', Name='Open items (Document Based)',Updated=TO_TIMESTAMP('2026-05-14 06:28:39.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=500174
;

-- 2026-05-14T06:28:39.828Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Offene Posten (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-14T06:29:00.934Z
UPDATE AD_Process_Trl SET Description='Offene Posten (Belegbasiert)', IsTranslated='Y', Name='Offene Posten (Belegbasiert)',Updated=TO_TIMESTAMP('2026-05-14 06:29:00.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language in ('de_CH', 'de_DE') AND AD_Process_ID=500174
;

-- 2026-05-14T06:29:00.935Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- Value: opent_items_doc_based
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/openitems/report.jasper
-- 2026-05-14T06:29:58.141Z
UPDATE AD_Process SET Value='opent_items_doc_based',Updated=TO_TIMESTAMP('2026-05-14 06:29:58.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=500174
;

-- Value: opent_items_doc_based
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/openitems/report.jasper
-- 2026-05-14T06:30:06.668Z
UPDATE AD_Process SET Description='Offene Posten (Belegbasiert)', Name='Offene Posten (Belegbasiert)',Updated=TO_TIMESTAMP('2026-05-14 06:30:06.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=500174
;

-- 2026-05-14T06:30:06.669Z
UPDATE AD_Process_Trl trl SET Description='Offene Posten (Belegbasiert)',Name='Offene Posten (Belegbasiert)' WHERE AD_Process_ID=500174 AND AD_Language='de_DE'
;

-- Name: Offene Posten (Belegbasiert)
-- Action Type: R
-- Report: opent_items_doc_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-14T06:30:06.687Z
UPDATE AD_Menu SET Description='Offene Posten (Belegbasiert)', IsActive='Y', Name='Offene Posten (Belegbasiert)',Updated=TO_TIMESTAMP('2026-05-14 06:30:06.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540948
;

-- 2026-05-14T06:30:06.693Z
UPDATE AD_Menu_Trl trl SET Description='Offene Posten (Belegbasiert)',Name='Offene Posten (Belegbasiert)' WHERE AD_Menu_ID=540948 AND AD_Language='de_DE'
;

-- Name: Offene Posten (Belegbasiert)
-- Action Type: R
-- Report: opent_items_doc_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-14T06:30:06.702Z
UPDATE AD_Menu SET Description='Offene Posten (Belegbasiert)', IsActive='Y', Name='Offene Posten (Belegbasiert)',Updated=TO_TIMESTAMP('2026-05-14 06:30:06.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=243
;

-- 2026-05-14T06:30:06.703Z
UPDATE AD_Menu_Trl trl SET Description='Offene Posten (Belegbasiert)',Name='Offene Posten (Belegbasiert)' WHERE AD_Menu_ID=243 AND AD_Language='de_DE'
;

-- Value: opent_items_fact_based
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/openitems_factbased/report.jasper
-- 2026-05-14T06:30:49.690Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,CSVFieldQuote,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsIncludeCSVHeaderRow,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585616,'Y','de.metas.report.jasper.client.process.JasperReportStarter',TO_TIMESTAMP('2026-05-14 06:30:48.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'"','Offene Posten','U','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/reports/openitems_factbased/report.jasper','@PREFIX@de/metas/reports/openitems_factbased/report_TabularView.jasper','Offene Posten','json','N','Y','JasperReportsSQL',TO_TIMESTAMP('2026-05-14 06:30:48.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'opent_items_fact_based')
;

-- 2026-05-14T06:30:49.698Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585616 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Date
-- 2026-05-14T06:39:37.135Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,577762,0,585616,543196,15,'Date',TO_TIMESTAMP('2026-05-14 06:39:37.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@#Date@','U',0,'Y','N','Y','N','Y','N','Datum',10,'N',TO_TIMESTAMP('2026-05-14 06:39:37.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T06:39:37.143Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543196 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_AcctSchema_ID
-- 2026-05-14T06:40:16.007Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,181,0,585616,543197,19,'C_AcctSchema_ID',TO_TIMESTAMP('2026-05-14 06:40:15.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stammdaten für Buchhaltung','@#C_AcctSchema_ID@','U',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','Y','N','Buchführungs-Schema',20,'N',TO_TIMESTAMP('2026-05-14 06:40:15.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T06:40:16.013Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543197 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_AcctSchema_ID
-- 2026-05-14T06:40:20.232Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-05-14 06:40:20.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543197
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Date
-- 2026-05-14T06:40:27.886Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-05-14 06:40:27.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543196
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: AD_Org_ID
-- 2026-05-14T06:41:01.768Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,113,0,585616,543198,19,'AD_Org_ID',TO_TIMESTAMP('2026-05-14 06:41:01.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','@AD_Org_ID@','U',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Sektion',30,'N',TO_TIMESTAMP('2026-05-14 06:41:01.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T06:41:01.773Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543198 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Account_ID
-- 2026-05-14T06:42:42.816Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,148,0,585616,543199,18,182,'Account_ID',TO_TIMESTAMP('2026-05-14 06:42:42.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwendetes Konto','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto',40,'N',TO_TIMESTAMP('2026-05-14 06:42:42.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T06:42:42.819Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543199 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: C_ElementValue (for org)
-- 2026-05-14T06:43:26.414Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542090,TO_TIMESTAMP('2026-05-14 06:43:26.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Element Values','D','Y','N','C_ElementValue (for org)',TO_TIMESTAMP('2026-05-14 06:43:26.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2026-05-14T06:43:26.419Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542090 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_ElementValue (for org)
-- Table: C_ElementValue
-- Key: C_ElementValue.C_ElementValue_ID
-- 2026-05-14T06:44:21.129Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1125,0,542090,188,TO_TIMESTAMP('2026-05-14 06:44:21.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Y','C_ElementValue.Value','N',TO_TIMESTAMP('2026-05-14 06:44:21.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_ElementValue.IsSummary<>''Y''')
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Account_ID
-- 2026-05-14T06:44:38.913Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=542090,Updated=TO_TIMESTAMP('2026-05-14 06:44:38.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543199
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_ID
-- 2026-05-14T06:45:40.335Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,187,0,585616,543200,30,540392,'C_BPartner_ID',TO_TIMESTAMP('2026-05-14 06:45:40.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','D',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',50,'N',TO_TIMESTAMP('2026-05-14 06:45:40.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T06:45:40.340Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543200 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-05-14T06:47:15.279Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584871,0,'Tolerance',TO_TIMESTAMP('2026-05-14 06:47:15.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Toleranz','Toleranz',TO_TIMESTAMP('2026-05-14 06:47:15.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T06:47:15.289Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584871 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Tolerance
-- 2026-05-14T06:47:23.192Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-14 06:47:23.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584871 AND AD_Language='de_CH'
;

-- 2026-05-14T06:47:23.197Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584871,'de_CH')
;

-- Element: Tolerance
-- 2026-05-14T06:47:26.862Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-14 06:47:26.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584871 AND AD_Language='en_US'
;

-- 2026-05-14T06:47:26.864Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584871,'en_US')
;

-- Element: Tolerance
-- 2026-05-14T06:47:32.702Z
UPDATE AD_Element_Trl SET Name='Tolerance', PrintName='Tolerance',Updated=TO_TIMESTAMP('2026-05-14 06:47:32.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584871 AND AD_Language='en_US'
;

-- 2026-05-14T06:47:32.703Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-14T06:47:32.960Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584871,'en_US')
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Tolerance
-- 2026-05-14T06:47:58.528Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,584871,0,585616,543201,22,'Tolerance',TO_TIMESTAMP('2026-05-14 06:47:58.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'0.5','U',0,'Y','N','Y','N','Y','N','Toleranz',60,'N',TO_TIMESTAMP('2026-05-14 06:47:58.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T06:47:58.531Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543201 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Tolerance
-- 2026-05-14T06:48:03.033Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-05-14 06:48:03.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543201
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: showdetails
-- 2026-05-14T06:48:37.665Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,543019,0,585616,543202,20,'showdetails',TO_TIMESTAMP('2026-05-14 06:48:37.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,'Y','N','Y','N','Y','N','Show Details',70,'N',TO_TIMESTAMP('2026-05-14 06:48:37.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T06:48:37.669Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543202 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: showdetails
-- 2026-05-14T06:48:41.413Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-05-14 06:48:41.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543202
;


-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Tolerance
-- 2026-05-14T06:51:49.976Z
UPDATE AD_Process_Para SET DefaultValue='0.05',Updated=TO_TIMESTAMP('2026-05-14 06:51:49.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543201
;

-- Element: showdetails
-- 2026-05-14T12:02:06.846Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-14 12:02:06.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543019 AND AD_Language='en_US'
;

-- 2026-05-14T12:02:06.854Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543019,'en_US')
;

-- Element: showdetails
-- 2026-05-14T12:02:10.356Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Details anzeigen', PrintName='Details anzeigen',Updated=TO_TIMESTAMP('2026-05-14 12:02:10.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543019 AND AD_Language='de_CH'
;

-- 2026-05-14T12:02:10.357Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-14T12:02:11.051Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543019,'de_CH')
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Date
-- 2026-05-14T12:47:10.077Z
UPDATE AD_Process_Para SET SeqNo=25,Updated=TO_TIMESTAMP('2026-05-14 12:47:10.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543196
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_AcctSchema_ID
-- 2026-05-14T12:47:46.402Z
UPDATE AD_Process_Para SET SeqNo=10,Updated=TO_TIMESTAMP('2026-05-14 12:47:46.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543197
;

-- Process: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: AD_Org_ID
-- 2026-05-14T12:47:52.399Z
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2026-05-14 12:47:52.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543198
;

-- 2026-05-14T12:48:57.988Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584879,0,TO_TIMESTAMP('2026-05-14 12:48:56.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Offene Posten (Belegbasiert)','D','Y','Offene Posten (Belegbasiert)','Offene Posten (Belegbasiert)',TO_TIMESTAMP('2026-05-14 12:48:56.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T12:48:57.996Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584879 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-05-14T12:49:01.261Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-14 12:49:01.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584879 AND AD_Language='de_CH'
;

-- 2026-05-14T12:49:01.263Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584879,'de_CH')
;

-- Element: null
-- 2026-05-14T12:49:16.230Z
UPDATE AD_Element_Trl SET Description='Open Items (document based)', IsTranslated='Y', Name='Open Items (document based)', PrintName='Open Items (document based)',Updated=TO_TIMESTAMP('2026-05-14 12:49:16.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584879 AND AD_Language='en_US'
;

-- 2026-05-14T12:49:16.230Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-14T12:49:16.438Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584879,'en_US')
;

-- Name: Offene Posten (Belegbasiert)
-- Action Type: R
-- Report: opent_items_doc_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-14T12:49:35.540Z
UPDATE AD_Menu SET AD_Element_ID=584879, Description='Offene Posten (Belegbasiert)', InternalName='open_items(docbased)', Name='Offene Posten (Belegbasiert)', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2026-05-14 12:49:35.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=243
;

-- 2026-05-14T12:49:35.543Z
/* DDL */  select update_menu_translation_from_ad_element(584879)
;

-- Name: Offene Posten
-- Action Type: R
-- Report: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-14T12:50:12.344Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,543858,542324,0,585616,TO_TIMESTAMP('2026-05-14 12:50:12.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D','opent_items_fact_based','Y','N','N','Y','N','Offene Posten',TO_TIMESTAMP('2026-05-14 12:50:12.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-14T12:50:12.349Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542324 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-05-14T12:50:12.351Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542324, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542324)
;

-- 2026-05-14T12:50:12.365Z
/* DDL */  select update_menu_translation_from_ad_element(543858)
;

-- Reordering children of `Open Items`
-- Node name: `Kassenjournal (C_BankStatement)`
-- 2026-05-14T12:50:12.940Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53324 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_Cash)`
-- 2026-05-14T12:50:12.943Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=241 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal Detail`
-- 2026-05-14T12:50:12.944Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=288 AND AD_Tree_ID=10
;

-- Node name: `Invoice Tax`
-- 2026-05-14T12:50:12.945Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=432 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten (Belegbasiert)`
-- 2026-05-14T12:50:12.946Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=243 AND AD_Tree_ID=10
;

-- Node name: `Dunning Run (C_DunningRun)`
-- 2026-05-14T12:50:12.947Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=462 AND AD_Tree_ID=10
;

-- Node name: `Print Dunning Letters (org.compiere.process.DunningPrint)`
-- 2026-05-14T12:50:12.948Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=505 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2026-05-14T12:50:12.949Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=235 AND AD_Tree_ID=10
;

-- Node name: `Payment Details`
-- 2026-05-14T12:50:12.949Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=511 AND AD_Tree_ID=10
;

-- Node name: `View Allocation (C_AllocationHdr)`
-- 2026-05-14T12:50:12.951Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=251 AND AD_Tree_ID=10
;

-- Node name: `Allocation`
-- 2026-05-14T12:50:12.952Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=246 AND AD_Tree_ID=10
;

-- Node name: `UnAllocated Invoices`
-- 2026-05-14T12:50:12.953Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=509 AND AD_Tree_ID=10
;

-- Node name: `UnAllocated Payments`
-- 2026-05-14T12:50:12.954Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=510 AND AD_Tree_ID=10
;

-- Node name: `Receivables Write-Off (org.compiere.process.InvoiceWriteOff)`
-- 2026-05-14T12:50:12.956Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=304 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-05-14T12:50:12.957Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=255 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (manual) (org.compiere.apps.form.VPaySelect)`
-- 2026-05-14T12:50:12.957Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=286 AND AD_Tree_ID=10
;

-- Node name: `Payment Print/Export (org.compiere.apps.form.VPayPrint)`
-- 2026-05-14T12:50:12.958Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=287 AND AD_Tree_ID=10
;

-- Node name: `Payment Batch (C_PaymentBatch)`
-- 2026-05-14T12:50:12.959Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=438 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-05-14T12:50:12.960Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=234 AND AD_Tree_ID=10
;

-- Node name: `UnReconciled Payments`
-- 2026-05-14T12:50:12.961Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=244 AND AD_Tree_ID=10
;

-- Node name: `Immediate Bank Transfer (org.adempiere.process.ImmediateBankTransfer)`
-- 2026-05-14T12:50:12.961Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53187 AND AD_Tree_ID=10
;

-- Node name: `Offene Rechnung - Skonto Zuordnung (de.metas.invoice.process.C_Invoice_MassDiscountOrWriteOff)`
-- 2026-05-14T12:50:12.963Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540642 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten Fibu (Jasper) (@PREFIX@de/metas/reports/openitems_fibu/report.jasper)`
-- 2026-05-14T12:50:12.964Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540651 AND AD_Tree_ID=10
;

-- Node name: `Rechnungsdisposition (C_Invoice_Candidate)`
-- 2026-05-14T12:50:12.965Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000103 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten`
-- 2026-05-14T12:50:12.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542324 AND AD_Tree_ID=10
;

-- Reordering children of `Open Items`
-- Node name: `Kassenjournal (C_BankStatement)`
-- 2026-05-14T12:50:20.623Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53324 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_Cash)`
-- 2026-05-14T12:50:20.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=241 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal Detail`
-- 2026-05-14T12:50:20.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=288 AND AD_Tree_ID=10
;

-- Node name: `Invoice Tax`
-- 2026-05-14T12:50:20.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=432 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten (Belegbasiert)`
-- 2026-05-14T12:50:20.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=243 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten`
-- 2026-05-14T12:50:20.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542324 AND AD_Tree_ID=10
;

-- Node name: `Dunning Run (C_DunningRun)`
-- 2026-05-14T12:50:20.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=462 AND AD_Tree_ID=10
;

-- Node name: `Print Dunning Letters (org.compiere.process.DunningPrint)`
-- 2026-05-14T12:50:20.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=505 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2026-05-14T12:50:20.630Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=235 AND AD_Tree_ID=10
;

-- Node name: `Payment Details`
-- 2026-05-14T12:50:20.631Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=511 AND AD_Tree_ID=10
;

-- Node name: `View Allocation (C_AllocationHdr)`
-- 2026-05-14T12:50:20.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=251 AND AD_Tree_ID=10
;

-- Node name: `Allocation`
-- 2026-05-14T12:50:20.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=246 AND AD_Tree_ID=10
;

-- Node name: `UnAllocated Invoices`
-- 2026-05-14T12:50:20.633Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=509 AND AD_Tree_ID=10
;

-- Node name: `UnAllocated Payments`
-- 2026-05-14T12:50:20.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=510 AND AD_Tree_ID=10
;

-- Node name: `Receivables Write-Off (org.compiere.process.InvoiceWriteOff)`
-- 2026-05-14T12:50:20.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=304 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-05-14T12:50:20.636Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=255 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (manual) (org.compiere.apps.form.VPaySelect)`
-- 2026-05-14T12:50:20.636Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=286 AND AD_Tree_ID=10
;

-- Node name: `Payment Print/Export (org.compiere.apps.form.VPayPrint)`
-- 2026-05-14T12:50:20.637Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=287 AND AD_Tree_ID=10
;

-- Node name: `Payment Batch (C_PaymentBatch)`
-- 2026-05-14T12:50:20.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=438 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-05-14T12:50:20.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=234 AND AD_Tree_ID=10
;

-- Node name: `UnReconciled Payments`
-- 2026-05-14T12:50:20.639Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=244 AND AD_Tree_ID=10
;

-- Node name: `Immediate Bank Transfer (org.adempiere.process.ImmediateBankTransfer)`
-- 2026-05-14T12:50:20.640Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53187 AND AD_Tree_ID=10
;

-- Node name: `Offene Rechnung - Skonto Zuordnung (de.metas.invoice.process.C_Invoice_MassDiscountOrWriteOff)`
-- 2026-05-14T12:50:20.641Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540642 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten Fibu (Jasper) (@PREFIX@de/metas/reports/openitems_fibu/report.jasper)`
-- 2026-05-14T12:50:20.642Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540651 AND AD_Tree_ID=10
;

-- Node name: `Rechnungsdisposition (C_Invoice_Candidate)`
-- 2026-05-14T12:50:20.643Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000103 AND AD_Tree_ID=10
;

-- Reordering children of `Open Items`
-- Node name: `Kassenjournal (C_BankStatement)`
-- 2026-05-14T12:50:23.037Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53324 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_Cash)`
-- 2026-05-14T12:50:23.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=241 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal Detail`
-- 2026-05-14T12:50:23.039Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=288 AND AD_Tree_ID=10
;

-- Node name: `Invoice Tax`
-- 2026-05-14T12:50:23.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=432 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten`
-- 2026-05-14T12:50:23.041Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542324 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten (Belegbasiert)`
-- 2026-05-14T12:50:23.041Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=243 AND AD_Tree_ID=10
;

-- Node name: `Dunning Run (C_DunningRun)`
-- 2026-05-14T12:50:23.042Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=462 AND AD_Tree_ID=10
;

-- Node name: `Print Dunning Letters (org.compiere.process.DunningPrint)`
-- 2026-05-14T12:50:23.043Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=505 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2026-05-14T12:50:23.044Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=235 AND AD_Tree_ID=10
;

-- Node name: `Payment Details`
-- 2026-05-14T12:50:23.044Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=511 AND AD_Tree_ID=10
;

-- Node name: `View Allocation (C_AllocationHdr)`
-- 2026-05-14T12:50:23.045Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=251 AND AD_Tree_ID=10
;

-- Node name: `Allocation`
-- 2026-05-14T12:50:23.046Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=246 AND AD_Tree_ID=10
;

-- Node name: `UnAllocated Invoices`
-- 2026-05-14T12:50:23.047Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=509 AND AD_Tree_ID=10
;

-- Node name: `UnAllocated Payments`
-- 2026-05-14T12:50:23.048Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=510 AND AD_Tree_ID=10
;

-- Node name: `Receivables Write-Off (org.compiere.process.InvoiceWriteOff)`
-- 2026-05-14T12:50:23.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=304 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-05-14T12:50:23.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=255 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (manual) (org.compiere.apps.form.VPaySelect)`
-- 2026-05-14T12:50:23.050Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=286 AND AD_Tree_ID=10
;

-- Node name: `Payment Print/Export (org.compiere.apps.form.VPayPrint)`
-- 2026-05-14T12:50:23.051Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=287 AND AD_Tree_ID=10
;

-- Node name: `Payment Batch (C_PaymentBatch)`
-- 2026-05-14T12:50:23.052Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=438 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-05-14T12:50:23.053Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=234 AND AD_Tree_ID=10
;

-- Node name: `UnReconciled Payments`
-- 2026-05-14T12:50:23.054Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=244 AND AD_Tree_ID=10
;

-- Node name: `Immediate Bank Transfer (org.adempiere.process.ImmediateBankTransfer)`
-- 2026-05-14T12:50:23.054Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53187 AND AD_Tree_ID=10
;

-- Node name: `Offene Rechnung - Skonto Zuordnung (de.metas.invoice.process.C_Invoice_MassDiscountOrWriteOff)`
-- 2026-05-14T12:50:23.055Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540642 AND AD_Tree_ID=10
;

-- Node name: `Offene Posten Fibu (Jasper) (@PREFIX@de/metas/reports/openitems_fibu/report.jasper)`
-- 2026-05-14T12:50:23.056Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540651 AND AD_Tree_ID=10
;

-- Node name: `Rechnungsdisposition (C_Invoice_Candidate)`
-- 2026-05-14T12:50:23.056Z
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000103 AND AD_Tree_ID=10
;


-- Value: opent_items_fact_based
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/openitems_factbased/report.jasper
-- 2026-05-14T15:54:56.383Z
UPDATE AD_Process SET Description='Offene Posten basierend auf Fact_Acct Buchunge',Updated=TO_TIMESTAMP('2026-05-14 15:54:56.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585616
;

-- 2026-05-14T15:54:56.393Z
UPDATE AD_Process_Trl trl SET Description='Offene Posten basierend auf Fact_Acct Buchunge' WHERE AD_Process_ID=585616 AND AD_Language='de_DE'
;

-- Name: Offene Posten
-- Action Type: R
-- Report: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-14T15:54:56.453Z
UPDATE AD_Menu SET Description='Offene Posten basierend auf Fact_Acct Buchunge', IsActive='Y', Name='Offene Posten',Updated=TO_TIMESTAMP('2026-05-14 15:54:56.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542324
;

-- 2026-05-14T15:54:56.456Z
UPDATE AD_Menu_Trl trl SET Description='Offene Posten basierend auf Fact_Acct Buchunge' WHERE AD_Menu_ID=542324 AND AD_Language='de_DE'
;

-- Value: opent_items_fact_based
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/openitems_factbased/report.jasper
-- 2026-05-14T15:55:01.002Z
UPDATE AD_Process SET Description='Offene Posten basierend auf Fact_Acct Buchungen',Updated=TO_TIMESTAMP('2026-05-14 15:55:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585616
;

-- 2026-05-14T15:55:01.005Z
UPDATE AD_Process_Trl trl SET Description='Offene Posten basierend auf Fact_Acct Buchungen' WHERE AD_Process_ID=585616 AND AD_Language='de_DE'
;

-- Name: Offene Posten
-- Action Type: R
-- Report: opent_items_fact_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-14T15:55:01.015Z
UPDATE AD_Menu SET Description='Offene Posten basierend auf Fact_Acct Buchungen', IsActive='Y', Name='Offene Posten',Updated=TO_TIMESTAMP('2026-05-14 15:55:01.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542324
;

-- 2026-05-14T15:55:01.017Z
UPDATE AD_Menu_Trl trl SET Description='Offene Posten basierend auf Fact_Acct Buchungen' WHERE AD_Menu_ID=542324 AND AD_Language='de_DE'
;


