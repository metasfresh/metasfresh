-- Run mode: SWING_CLIENT

-- Value: Product costs per date
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/productcosts/report.jasper
-- 2023-10-24T08:00:40.892Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585331,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-10-24 11:00:40.684','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.acct','Y','N','N','Y','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/reports/productcosts/report.jasper','',0,'Product costs per date','json','N','N','JasperReportsSQL',TO_TIMESTAMP('2023-10-24 11:00:40.684','YYYY-MM-DD HH24:MI:SS.US'),100,'Product costs per date')
;

-- 2023-10-24T08:00:40.902Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585331 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Date
-- 2023-10-24T08:03:16.805Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577762,0,585331,542728,15,'Date',TO_TIMESTAMP('2023-10-24 11:03:16.66','YYYY-MM-DD HH24:MI:SS.US'),100,'@Date@','de.metas.acct',0,'Y','N','Y','N','Y','N','Datum',10,TO_TIMESTAMP('2023-10-24 11:03:16.66','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-24T08:03:16.807Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542728 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-24T08:03:16.829Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(577762)
;

-- Process: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: AD_Org_ID
-- 2023-10-24T08:03:46.643Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,585331,542729,19,'AD_Org_ID',TO_TIMESTAMP('2023-10-24 11:03:46.54','YYYY-MM-DD HH24:MI:SS.US'),100,'@AD_Org_ID@','Organisatorische Einheit des Mandanten','de.metas.acct',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Organisation',20,TO_TIMESTAMP('2023-10-24 11:03:46.54','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-24T08:03:46.645Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542729 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-24T08:03:46.648Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(113)
;

-- Process: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_AcctSchema_ID
-- 2023-10-24T08:04:36.450Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585331,542730,19,106,'C_AcctSchema_ID',TO_TIMESTAMP('2023-10-24 11:04:36.335','YYYY-MM-DD HH24:MI:SS.US'),100,'Stammdaten für Buchhaltung','de.metas.acct',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','N','N','Buchführungs-Schema',30,TO_TIMESTAMP('2023-10-24 11:04:36.335','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-24T08:04:36.452Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542730 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-24T08:04:36.456Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(181)
;

-- Process: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_AcctSchema_ID
-- 2023-10-24T08:04:39.794Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-24 11:04:39.794','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542730
;

-- Process: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: M_Product_Category_ID
-- 2023-10-24T08:05:07.139Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,453,0,585331,542731,19,'M_Product_Category_ID',TO_TIMESTAMP('2023-10-24 11:05:07.019','YYYY-MM-DD HH24:MI:SS.US'),100,'Kategorie eines Produktes','de.metas.acct',0,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','Produkt Kategorie',40,TO_TIMESTAMP('2023-10-24 11:05:07.019','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-24T08:05:07.141Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542731 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-24T08:05:07.143Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(453)
;

-- Process: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: M_Product_ID
-- 2023-10-24T08:07:36.964Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,585331,542732,18,540272,540554,'M_Product_ID',TO_TIMESTAMP('2023-10-24 11:07:36.855','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','de.metas.acct',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',50,TO_TIMESTAMP('2023-10-24 11:07:36.855','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-24T08:07:36.967Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542732 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-24T08:07:36.969Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(454)
;

-- Process: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: M_Product_Category_ID
-- 2023-10-24T08:08:42.179Z
UPDATE AD_Process_Para SET ReadOnlyLogic='@M_Product_ID@>0',Updated=TO_TIMESTAMP('2023-10-24 11:08:42.179','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542731
;

-- Process: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-10-24T08:09:13.382Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Produktkosten pro Datum',Updated=TO_TIMESTAMP('2023-10-24 11:09:13.382','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585331
;

-- Process: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-10-24T08:09:16.088Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Produktkosten pro Datum',Updated=TO_TIMESTAMP('2023-10-24 11:09:16.088','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585331
;

-- 2023-10-24T08:09:16.089Z
UPDATE AD_Process SET Name='Produktkosten pro Datum' WHERE AD_Process_ID=585331
;

-- 2023-10-24T08:11:43.059Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582778,0,TO_TIMESTAMP('2023-10-24 11:11:42.922','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.acct','Y','Product costs per date','Product costs per date',TO_TIMESTAMP('2023-10-24 11:11:42.922','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-24T08:11:43.064Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582778 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-10-24T08:11:50.913Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktkosten pro Datum', PrintName='Produktkosten pro Datum',Updated=TO_TIMESTAMP('2023-10-24 11:11:50.913','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582778 AND AD_Language='de_CH'
;

-- 2023-10-24T08:11:50.919Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582778,'de_CH')
;

-- Element: null
-- 2023-10-24T08:11:55.400Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktkosten pro Datum', PrintName='Produktkosten pro Datum',Updated=TO_TIMESTAMP('2023-10-24 11:11:55.399','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582778 AND AD_Language='de_DE'
;

-- 2023-10-24T08:11:55.402Z
UPDATE AD_Element SET Name='Produktkosten pro Datum', PrintName='Produktkosten pro Datum' WHERE AD_Element_ID=582778
;

-- 2023-10-24T08:11:55.644Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582778,'de_DE')
;

-- 2023-10-24T08:11:55.645Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582778,'de_DE')
;

-- Name: Produktkosten pro Datum
-- Action Type: R
-- Report: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-10-24T08:12:07.080Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,582778,542123,0,585331,TO_TIMESTAMP('2023-10-24 11:12:06.953','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.acct','Product costs per date','Y','N','N','N','N','Produktkosten pro Datum',TO_TIMESTAMP('2023-10-24 11:12:06.953','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-24T08:12:07.083Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542123 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-10-24T08:12:07.085Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542123, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542123)
;

-- 2023-10-24T08:12:07.093Z
/* DDL */  select update_menu_translation_from_ad_element(582778)
;

-- Reordering children of `Shipment`
-- Node name: `Shipping Notification (M_Shipping_Notification)`
-- 2023-10-24T08:12:07.792Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542113 AND AD_Tree_ID=10
;

-- Node name: `Shipment (M_InOut)`
-- 2023-10-24T08:12:07.795Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2023-10-24T08:12:07.796Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2023-10-24T08:12:07.798Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2023-10-24T08:12:07.800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2023-10-24T08:12:07.802Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2023-10-24T08:12:07.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2023-10-24T08:12:07.805Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2023-10-24T08:12:07.806Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2023-10-24T08:12:07.808Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2023-10-24T08:12:07.810Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2023-10-24T08:12:07.812Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2023-10-24T08:12:07.813Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2023-10-24T08:12:07.815Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2023-10-24T08:12:07.816Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2023-10-24T08:12:07.818Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2023-10-24T08:12:07.820Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-10-24T08:12:07.821Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2023-10-24T08:12:07.822Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-10-24T08:12:07.823Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-10-24T08:12:07.824Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2023-10-24T08:12:07.825Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `Produktkosten pro Datum`
-- 2023-10-24T08:12:07.825Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542123 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Next vs AfterNext Pricelist Comparison (@PREFIX@de/metas/reports/pricelist/report_NextVsAfterNextPricelistComparison.jasper)`
-- 2023-10-24T08:12:32.636Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541956 AND AD_Tree_ID=10
;

-- Node name: `Current vs Previous Pricelist Comparison Report (@PREFIX@de/metas/reports/pricelist/report_CurrentVsLastPricelistComparison.jasper)`
-- 2023-10-24T08:12:32.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541452 AND AD_Tree_ID=10
;

-- Node name: `Produktkosten pro Datum`
-- 2023-10-24T08:12:32.639Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542123 AND AD_Tree_ID=10
;

UPDATE AD_Process_Para set AD_Val_Rule_ID=null,  defaultvalue ='1000000' where ad_process_para_id=542730;

UPDATE ad_process_para
SET name = 'Buchungsdatum', description = 'Accounting Date',
    help         = 'The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.', ad_process_id = 585331, seqno = 10, ad_reference_id = 15, ad_reference_value_id = NULL, ad_val_rule_id = NULL, columnname = 'DateAcct', iscentrallymaintained = 'Y', fieldlength = 0, ismandatory = 'Y', isrange = 'N',
    defaultvalue = '@Date@',  ad_element_id = 263, entitytype = 'de.metas.acct'
WHERE ad_process_para_id = 542728
;

UPDATE ad_process_para_trl SET name = 'Date', description = null, help = null, istranslated = 'Y' WHERE ad_process_para_id = 542728 AND ad_language = 'fr_CH';
UPDATE ad_process_para_trl SET name = 'Datum', description = null, help = null, istranslated = 'Y' WHERE ad_process_para_id = 542728 AND ad_language = 'de_CH';
UPDATE ad_process_para_trl SET name = 'Date', description = null, help = null, istranslated = 'Y' WHERE ad_process_para_id = 542728 AND ad_language = 'en_US';
UPDATE ad_process_para_trl SET name = 'Datum', description = null, help = null, istranslated = 'Y' WHERE ad_process_para_id = 542728 AND ad_language = 'de_DE';