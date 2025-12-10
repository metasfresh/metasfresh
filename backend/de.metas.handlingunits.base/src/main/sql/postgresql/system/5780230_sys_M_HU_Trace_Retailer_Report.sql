-- Run mode: SWING_CLIENT

-- Value: M_HU_Trace_Retailer_Report_Excel
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel
-- 2025-12-09T18:01:58.932Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585544,'Y','de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel','N',TO_TIMESTAMP('2025-12-09 18:01:58.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','U','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Rückverfolgungsbericht ','json','N','N','xls','Java',TO_TIMESTAMP('2025-12-09 18:01:58.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_HU_Trace_Retailer_Report_Excel')
;

-- 2025-12-09T18:01:58.939Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585544 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_HU_Trace_Retailer_Report_Excel
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel
-- 2025-12-09T18:06:30.545Z
UPDATE AD_Process SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2025-12-09 18:06:30.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585544
;

-- Value: M_HU_Trace_Retailer_Report_Excel
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel
-- 2025-12-09T18:08:18.132Z
UPDATE AD_Process SET Name='Rückverfolgungsbericht Handel',Updated=TO_TIMESTAMP('2025-12-09 18:08:18.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585544
;

-- 2025-12-09T18:08:18.134Z
UPDATE AD_Process_Trl trl SET Name='Rückverfolgungsbericht Handel' WHERE AD_Process_ID=585544 AND AD_Language='de_DE'
;

-- Process: M_HU_Trace_Retailer_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel)
-- 2025-12-09T18:08:29.782Z
UPDATE AD_Process_Trl SET Name='Rückverfolgungsbericht Handel ',Updated=TO_TIMESTAMP('2025-12-09 18:08:29.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585544
;

-- 2025-12-09T18:08:29.784Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_HU_Trace_Retailer_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel)
-- 2025-12-09T18:08:33.315Z
UPDATE AD_Process_Trl SET Name='Rückverfolgungsbericht Handel ',Updated=TO_TIMESTAMP('2025-12-09 18:08:33.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585544
;

-- 2025-12-09T18:08:33.316Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_HU_Trace_Retailer_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel)
-- 2025-12-09T18:08:43.460Z
UPDATE AD_Process_Trl SET Name='Traceability Report Retail',Updated=TO_TIMESTAMP('2025-12-09 18:08:43.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585544
;

-- 2025-12-09T18:08:43.461Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_HU_Trace_Retailer_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel)
-- ParameterName: M_Product_ID
-- 2025-12-09T18:09:41.539Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,585544,543081,30,'M_Product_ID',TO_TIMESTAMP('2025-12-09 18:09:41.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','de.metas.handlingunits',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',10,TO_TIMESTAMP('2025-12-09 18:09:41.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-09T18:09:41.543Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543081 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_HU_Trace_Retailer_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel)
-- ParameterName: LotNumber
-- 2025-12-09T18:10:11.376Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,576652,0,585544,543082,10,'LotNumber',TO_TIMESTAMP('2025-12-09 18:10:11.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits',0,'Y','N','Y','N','N','N','Chargennummer',20,TO_TIMESTAMP('2025-12-09 18:10:11.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-09T18:10:11.379Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543082 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-12-09T18:11:41.054Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584349,0,TO_TIMESTAMP('2025-12-09 18:11:40.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Rückverfolgungsbericht Handel','Rückverfolgungsbericht Handel',TO_TIMESTAMP('2025-12-09 18:11:40.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-09T18:11:41.059Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584349 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-12-09T18:11:55.799Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Traceability Report Retail', PrintName='Traceability Report Retail',Updated=TO_TIMESTAMP('2025-12-09 18:11:55.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584349 AND AD_Language='en_US'
;

-- 2025-12-09T18:11:55.799Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-09T18:11:56.010Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584349,'en_US')
;

-- Name: Rückverfolgungsbericht Handel
-- Action Type: P
-- Process: M_HU_Trace_Retailer_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Retailer_Excel)
-- 2025-12-09T18:12:54.885Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,584349,542282,0,585544,TO_TIMESTAMP('2025-12-09 18:12:54.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','M_HU_Trace_Retailer_Report_Excel','Y','N','N','N','N','Rückverfolgungsbericht Handel',TO_TIMESTAMP('2025-12-09 18:12:54.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-09T18:12:54.888Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542282 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-12-09T18:12:54.892Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542282, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542282)
;

-- 2025-12-09T18:12:54.900Z
/* DDL */  select update_menu_translation_from_ad_element(584349)
;

-- Reordering children of `Reports`
-- Node name: `Handling Unit Trace Report (Excel) (de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)`
-- 2025-12-09T18:13:03.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542081 AND AD_Tree_ID=10
;

-- Node name: `Containers management (@PREFIX@de/metas/reports/balance_empties/report.jasper)`
-- 2025-12-09T18:13:03.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541390 AND AD_Tree_ID=10
;

-- Node name: `Package Overview (@PREFIX@de/metas/reports/hubalance_per_day/report.jasper)`
-- 2025-12-09T18:13:03.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540936 AND AD_Tree_ID=10
;

-- Node name: `Package Overall (@PREFIX@de/metas/reports/hubalancegeneral/report.jasper)`
-- 2025-12-09T18:13:03.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540943 AND AD_Tree_ID=10
;

-- Node name: `Rückverfolgungsbericht Handel`
-- 2025-12-09T18:13:03.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542282 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Handling Unit Trace Report (Excel) (de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)`
-- 2025-12-09T18:13:10.458Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542081 AND AD_Tree_ID=10
;

-- Node name: `Rückverfolgungsbericht Handel`
-- 2025-12-09T18:13:10.459Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542282 AND AD_Tree_ID=10
;

-- Node name: `Containers management (@PREFIX@de/metas/reports/balance_empties/report.jasper)`
-- 2025-12-09T18:13:10.460Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541390 AND AD_Tree_ID=10
;

-- Node name: `Package Overview (@PREFIX@de/metas/reports/hubalance_per_day/report.jasper)`
-- 2025-12-09T18:13:10.461Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540936 AND AD_Tree_ID=10
;

-- Node name: `Package Overall (@PREFIX@de/metas/reports/hubalancegeneral/report.jasper)`
-- 2025-12-09T18:13:10.462Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540943 AND AD_Tree_ID=10
;

