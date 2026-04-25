-- Run mode: SWING_CLIENT

-- Process: goods_sales_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-04-25T07:06:35.223Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-25 07:06:35.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585612
;

-- Process: goods_sales_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-04-25T07:06:41.855Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-25 07:06:41.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Process_ID=585612
;

-- Process: goods_sales_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: DateFrom
-- 2026-04-25T07:07:13.833Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1581,0,585612,543184,15,'DateFrom',TO_TIMESTAMP('2026-04-25 07:07:13.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Startdatum eines Abschnittes','U',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','Y','N','Datum von',10,'N',TO_TIMESTAMP('2026-04-25 07:07:13.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-25T07:07:13.849Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543184 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: goods_sales_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: DateFrom
-- 2026-04-25T07:07:18.878Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-04-25 07:07:18.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543184
;

-- Process: goods_sales_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: DateTo
-- 2026-04-25T07:07:30.812Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1582,0,585612,543185,15,'DateTo',TO_TIMESTAMP('2026-04-25 07:07:30.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enddatum eines Abschnittes','D',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','Y','N','Datum bis',20,'N',TO_TIMESTAMP('2026-04-25 07:07:30.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-25T07:07:30.817Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543185 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: goods_sales_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_ID
-- 2026-04-25T07:07:56.515Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,187,0,585612,543186,30,173,'C_BPartner_ID',TO_TIMESTAMP('2026-04-25 07:07:56.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','U',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',30,'N',TO_TIMESTAMP('2026-04-25 07:07:56.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-25T07:07:56.521Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543186 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: goods_sales_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BP_Group_ID
-- 2026-04-25T07:08:10.165Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1383,0,585612,543187,19,'C_BP_Group_ID',TO_TIMESTAMP('2026-04-25 07:08:10.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Geschäftspartnergruppe','U',0,'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','N','Geschäftspartnergruppe',40,'N',TO_TIMESTAMP('2026-04-25 07:08:10.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-25T07:08:10.170Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543187 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: goods_sales_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: M_Product_ID
-- 2026-04-25T07:08:35.764Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,454,0,585612,543188,30,540272,'M_Product_ID',TO_TIMESTAMP('2026-04-25 07:08:35.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','U',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',50,'N',TO_TIMESTAMP('2026-04-25 07:08:35.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-25T07:08:35.779Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543188 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-04-25T07:09:40.554Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584785,0,TO_TIMESTAMP('2026-04-25 07:09:40.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Sales by Product and Customer Report (Jasper)','Sales by Product and Customer Report (Jasper)',TO_TIMESTAMP('2026-04-25 07:09:40.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-25T07:09:40.563Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584785 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-04-25T07:09:54.126Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Umsatz nach Produkt und Kunde (Jasper)', PrintName='Umsatz nach Produkt und Kunde (Jasper)',Updated=TO_TIMESTAMP('2026-04-25 07:09:54.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584785 AND AD_Language in ( 'de_CH', 'de_DE')
;

-- 2026-04-25T07:09:54.127Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ( 'de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-25T07:09:54.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584785,'de_CH')
;


-- 2026-04-25T07:09:54.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584785,'de_DE')
;

-- Element: null
-- 2026-04-25T07:10:41.506Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-25 07:10:41.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584785 AND AD_Language='en_GB'
;

-- 2026-04-25T07:10:41.509Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584785,'en_GB')
;

-- Element: null
-- 2026-04-25T07:10:43.502Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-25 07:10:43.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584785 AND AD_Language='en_US'
;

-- 2026-04-25T07:10:43.504Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584785,'en_US')
;

-- Name: Sales by Product and Customer Report (Jasper)
-- Action Type: R
-- Report: goods_sales_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-04-25T07:11:14.800Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,584785,542317,0,585612,TO_TIMESTAMP('2026-04-25 07:11:14.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.fresh','goods_sales_report','Y','N','N','N','N','Sales by Product and Customer Report (Jasper)',TO_TIMESTAMP('2026-04-25 07:11:14.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-25T07:11:14.810Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542317 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-04-25T07:11:14.813Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542317, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542317)
;

-- 2026-04-25T07:11:14.822Z
/* DDL */  select update_menu_translation_from_ad_element(584785)
;

-- Reordering children of `Product Management`
-- Node name: `Replenishment (M_Material_Needs_Planner_V)`
-- 2026-04-25T07:11:23.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542202 AND AD_Tree_ID=10
;

-- Node name: `Product Data Capture (Jasper) (@PREFIX@de/metas/docs/label/dataentry/JSON_POC_Report.jasper)`
-- 2026-04-25T07:11:23.013Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- Node name: `Product (M_Product)`
-- 2026-04-25T07:11:23.016Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- Node name: `Customs Tariff (M_CustomsTariff)`
-- 2026-04-25T07:11:23.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- Node name: `Commodity Number (M_CommodityNumber)`
-- 2026-04-25T07:11:23.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- Node name: `Discount Schema (M_DiscountSchema)`
-- 2026-04-25T07:11:23.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- Node name: `Discount Rows (M_DiscountSchemaBreak)`
-- 2026-04-25T07:11:23.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- Node name: `Lot control (M_Product_LotNumber_Quarantine)`
-- 2026-04-25T07:11:23.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- Node name: `BPartner Product statistics (C_BPartner_Product_Stats)`
-- 2026-04-25T07:11:23.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol (M_HazardSymbol)`
-- 2026-04-25T07:11:23.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541870 AND AD_Tree_ID=10
;

-- Node name: `Product Certification (AD_Product_Certification_v)`
-- 2026-04-25T07:11:23.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541904 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol Trl (M_HazardSymbol_Trl)`
-- 2026-04-25T07:11:23.025Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541871 AND AD_Tree_ID=10
;

-- Node name: `Download Partner Products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-04-25T07:11:23.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-04-25T07:11:23.027Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-04-25T07:11:23.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- Node name: `Package-Licensing`
-- 2026-04-25T07:11:23.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542248 AND AD_Tree_ID=10
;

-- Node name: `Produkt Marktpläze (M_Product_Marketplace)`
-- 2026-04-25T07:11:23.030Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541864 AND AD_Tree_ID=10
;

-- Node name: `Additives translation (M_Additive_Trl)`
-- 2026-04-25T07:11:23.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541873 AND AD_Tree_ID=10
;

-- Node name: `Waste Management`
-- 2026-04-25T07:11:23.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542270 AND AD_Tree_ID=10
;

-- Node name: `Food`
-- 2026-04-25T07:11:23.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542272 AND AD_Tree_ID=10
;

-- Node name: `Sales by Product and Customer Report (Jasper)`
-- 2026-04-25T07:11:23.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542317 AND AD_Tree_ID=10
;

-- Reordering children of `Sales`
-- Node name: `Sales by Product and Customer Report (Jasper)`
-- 2026-04-25T07:11:41.963Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542317 AND AD_Tree_ID=10
;

-- Node name: `CreditPass configuration (CS_Creditpass_Config)`
-- 2026-04-25T07:11:41.965Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- Node name: `Sales Order (C_Order)`
-- 2026-04-25T07:11:41.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- Node name: `Purchase & Sales Overview (C_Order_M_InOut_C_Invoice_Overview_V)`
-- 2026-04-25T07:11:41.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542296 AND AD_Tree_ID=10
;

-- Node name: `Alberta Prescription Request (Alberta_PrescriptionRequest)`
-- 2026-04-25T07:11:41.967Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541703 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition (C_OLCand)`
-- 2026-04-25T07:11:41.969Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition (EDI-Import) (Legacy-EDI-Import) (C_OLCand)`
-- 2026-04-25T07:11:41.970Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542255 AND AD_Tree_ID=10
;

-- Node name: `Order Control (C_Order_MFGWarehouse_Report)`
-- 2026-04-25T07:11:41.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- Node name: `Sales Opportunity Board (Prototype)`
-- 2026-04-25T07:11:41.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit Type (C_CreditLimit_Type)`
-- 2026-04-25T07:11:41.973Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-04-25T07:11:41.974Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-04-25T07:11:41.974Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-04-25T07:11:41.975Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- Node name: `CreditPass transaction results (CS_Transaction_Result)`
-- 2026-04-25T07:11:41.977Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- Node name: `Commission`
-- 2026-04-25T07:11:41.978Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- Node name: `Incoterm (C_Incoterms)`
-- 2026-04-25T07:11:41.978Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541784 AND AD_Tree_ID=10
;

-- Node name: `Available for sales (MD_Available_For_Sales)`
-- 2026-04-25T07:11:41.979Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541962 AND AD_Tree_ID=10
;

-- Node name: `Point of Sale (POS)`
-- 2026-04-25T07:11:41.980Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542171 AND AD_Tree_ID=10
;

-- Node name: `Qty Reservation (M_QtyReservation)`
-- 2026-04-25T07:11:41.981Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542304 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Sales by Product and Customer Report (Jasper)`
-- 2026-04-25T07:11:49.190Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542317 AND AD_Tree_ID=10
;

-- Node name: `Article Statistics (@PREFIX@de/metas/reports/article_statistics/report.jasper)`
-- 2026-04-25T07:11:49.192Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- Node name: `Artikelstatistik (Excel)`
-- 2026-04-25T07:11:49.193Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541711 AND AD_Tree_ID=10
;

-- Node name: `Promotion Code Evaluation (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-04-25T07:11:49.194Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542303 AND AD_Tree_ID=10
;

-- Node name: `Reclamations Report (@PREFIX@de/metas/reports/request_report/report.jasper)`
-- 2026-04-25T07:11:49.195Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- Node name: `ADR Evaluation (@PREFIX@de/metas/reports/umsatzreport_adr_bpartner/report.jasper)`
-- 2026-04-25T07:11:49.196Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report (@PREFIX@de/metas/reports/umsatzreport/report.jasper)`
-- 2026-04-25T07:11:49.197Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- Node name: `Revenue Week Report (@PREFIX@de/metas/reports/umsatzreport_week/report.jasper)`
-- 2026-04-25T07:11:49.198Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Revenue (@PREFIX@de/metas/reports/umsatzreport_bpartner/report.jasper)`
-- 2026-04-25T07:11:49.199Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Week Revenue (@PREFIX@de/metas/reports/umsatzreport_bpartner_week/report.jasper)`
-- 2026-04-25T07:11:49.200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Week Revenue with Quantity (@PREFIX@de/metas/reports/umsatzreport_bpartner_week_with_qty/report.jasper)`
-- 2026-04-25T07:11:49.200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Revenue with Quantity (@PREFIX@de/metas/reports/umsatzreport_bpartner_with_qty/report.jasper)`
-- 2026-04-25T07:11:49.201Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Quantity (@PREFIX@de/metas/reports/qty_statistics/report.jasper)`
-- 2026-04-25T07:11:49.202Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Quantities (@PREFIX@de/metas/reports/qty_statistics_kg/report.jasper)`
-- 2026-04-25T07:11:49.203Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Week Quantity (@PREFIX@de/metas/reports/qty_statistics_week/report.jasper)`
-- 2026-04-25T07:11:49.204Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Week Quantities (@PREFIX@de/metas/reports/qty_statistics_kg_week/report.jasper)`
-- 2026-04-25T07:11:49.205Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- Node name: `Sales Trace (@PREFIX@de/metas/reports/sales_trace/report.xls)`
-- 2026-04-25T07:11:49.205Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540957 AND AD_Tree_ID=10
;

-- Node name: `Sales Price List to Date (@PREFIX@de/metas/reports/pricelist_todate/report.jasper)`
-- 2026-04-25T07:11:49.206Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542126 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Article Statistics (@PREFIX@de/metas/reports/article_statistics/report.jasper)`
-- 2026-04-25T07:11:56.314Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- Node name: `Artikelstatistik (Excel)`
-- 2026-04-25T07:11:56.315Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541711 AND AD_Tree_ID=10
;

-- Node name: `Promotion Code Evaluation (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-04-25T07:11:56.316Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542303 AND AD_Tree_ID=10
;

-- Node name: `Reclamations Report (@PREFIX@de/metas/reports/request_report/report.jasper)`
-- 2026-04-25T07:11:56.317Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- Node name: `ADR Evaluation (@PREFIX@de/metas/reports/umsatzreport_adr_bpartner/report.jasper)`
-- 2026-04-25T07:11:56.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report (@PREFIX@de/metas/reports/umsatzreport/report.jasper)`
-- 2026-04-25T07:11:56.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- Node name: `Revenue Week Report (@PREFIX@de/metas/reports/umsatzreport_week/report.jasper)`
-- 2026-04-25T07:11:56.319Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Revenue (@PREFIX@de/metas/reports/umsatzreport_bpartner/report.jasper)`
-- 2026-04-25T07:11:56.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Week Revenue (@PREFIX@de/metas/reports/umsatzreport_bpartner_week/report.jasper)`
-- 2026-04-25T07:11:56.321Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Week Revenue with Quantity (@PREFIX@de/metas/reports/umsatzreport_bpartner_week_with_qty/report.jasper)`
-- 2026-04-25T07:11:56.322Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Revenue with Quantity (@PREFIX@de/metas/reports/umsatzreport_bpartner_with_qty/report.jasper)`
-- 2026-04-25T07:11:56.323Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Quantity (@PREFIX@de/metas/reports/qty_statistics/report.jasper)`
-- 2026-04-25T07:11:56.324Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Quantities (@PREFIX@de/metas/reports/qty_statistics_kg/report.jasper)`
-- 2026-04-25T07:11:56.325Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Week Quantity (@PREFIX@de/metas/reports/qty_statistics_week/report.jasper)`
-- 2026-04-25T07:11:56.325Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Week Quantities (@PREFIX@de/metas/reports/qty_statistics_kg_week/report.jasper)`
-- 2026-04-25T07:11:56.326Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- Node name: `Sales Trace (@PREFIX@de/metas/reports/sales_trace/report.xls)`
-- 2026-04-25T07:11:56.327Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540957 AND AD_Tree_ID=10
;

-- Node name: `Sales by Product and Customer Report (Jasper)`
-- 2026-04-25T07:11:56.328Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542317 AND AD_Tree_ID=10
;

-- Node name: `Sales Price List to Date (@PREFIX@de/metas/reports/pricelist_todate/report.jasper)`
-- 2026-04-25T07:11:56.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542126 AND AD_Tree_ID=10
;

