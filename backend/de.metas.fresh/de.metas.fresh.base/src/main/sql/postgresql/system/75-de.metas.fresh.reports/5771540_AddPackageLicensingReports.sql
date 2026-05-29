-- Run mode: SWING_CLIENT


-- Value: Package-Licencing-Report-Details
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-09-28T11:46:13.672Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585503,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2025-09-28 11:46:13.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','Y','Y','N','Y','',0,'Package Licensing Movement Details','json','N','Y','xls','SELECT * FROM Package_Licensing_InOut_Report_report( @DateFrom/null@, @DateTo/null@, @C_Country_ID/null@)','Excel',TO_TIMESTAMP('2025-09-28 11:46:13.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Package-Licencing-Report-Details')
;

-- 2025-09-28T11:46:13.681Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585503 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Package-Licencing-Report-Details(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-09-28T11:46:27.489Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='	',Updated=TO_TIMESTAMP('2025-09-28 11:46:27.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585503
;

-- 2025-09-28T11:46:27.492Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Package-Licencing-Report-Details(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-09-28T11:46:27.489Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Verpackungslizenzierungs-Bewegungsdetails',Updated=TO_TIMESTAMP('2025-09-28 11:46:27.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585503
;

-- 2025-09-28T12:11:20.769Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584042,0,TO_TIMESTAMP('2025-09-28 12:11:20.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Verpackungslizenzierungs-Bewegungsdetails','Verpackungslizenzierungs-Bewegungsdetails',TO_TIMESTAMP('2025-09-28 12:11:20.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T12:11:20.780Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584042 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-09-28T12:11:24.744Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-28 12:11:24.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584042 AND AD_Language='de_CH'
;

-- 2025-09-28T12:11:24.770Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584042,'de_CH')
;

-- Element: null
-- 2025-09-28T12:11:27.380Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-28 12:11:27.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584042 AND AD_Language='de_DE'
;

-- 2025-09-28T12:11:27.385Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584042,'de_DE')
;

-- 2025-09-28T12:11:27.391Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584042,'de_DE')
;

-- Element: null
-- 2025-09-28T12:12:37.743Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Package Licensing Movement Details', PrintName='Package Licensing Movement Details',Updated=TO_TIMESTAMP('2025-09-28 12:12:37.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584042 AND AD_Language='en_US'
;

-- 2025-09-28T12:12:37.745Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-28T12:12:38.014Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584042,'en_US')
;

-- Name: Verpackungslizenzierungs-Bewegungsdetails
-- Action Type: R
-- Report: Package-Licencing-Report-Details(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-09-28T12:14:39.565Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,584042,542252,0,585503,TO_TIMESTAMP('2025-09-28 12:14:39.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Package-Licencing-Report-Details','Y','N','N','N','N','Verpackungslizenzierungs-Bewegungsdetails',TO_TIMESTAMP('2025-09-28 12:14:39.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T12:14:39.570Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542252 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-09-28T12:14:39.574Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542252, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542252)
;

-- 2025-09-28T12:14:39.585Z
/* DDL */  select update_menu_translation_from_ad_element(584042)
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2025-09-28T12:14:40.205Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2025-09-28T12:14:40.207Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2025-09-28T12:14:40.211Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2025-09-28T12:14:40.213Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2025-09-28T12:14:40.215Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2025-09-28T12:14:40.216Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2025-09-28T12:14:40.218Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2025-09-28T12:14:40.220Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2025-09-28T12:14:40.221Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2025-09-28T12:14:40.223Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2025-09-28T12:14:40.224Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2025-09-28T12:14:40.227Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2025-09-28T12:14:40.228Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2025-09-28T12:14:40.230Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2025-09-28T12:14:40.232Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2025-09-28T12:14:40.233Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-09-28T12:14:40.234Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2025-09-28T12:14:40.236Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-09-28T12:14:40.237Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-09-28T12:14:40.239Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2025-09-28T12:14:40.241Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `Verpackungslizenzierungs-Bewegungsdetails`
-- 2025-09-28T12:14:40.242Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542252 AND AD_Tree_ID=10
;

-- Reordering children of `Package-Licensing`
-- Node name: `Verpackungslizenzierungs-Bewegungsdetails`
-- 2025-09-28T12:14:59.822Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542252 AND AD_Tree_ID=10
;

-- Node name: `Product group (M_PackageLicensing_ProductGroup)`
-- 2025-09-28T12:14:59.824Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542250 AND AD_Tree_ID=10
;

-- Node name: `Material group (M_PackageLicensing_MaterialGroup)`
-- 2025-09-28T12:14:59.826Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542249 AND AD_Tree_ID=10
;

-- Reordering children of `Product Management`
-- Node name: `Replenishment (M_Material_Needs_Planner_V)`
-- 2025-09-28T12:15:03.586Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542202 AND AD_Tree_ID=10
;

-- Node name: `Ingredients (M_Ingredients)`
-- 2025-09-28T12:15:03.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541735 AND AD_Tree_ID=10
;

-- Node name: `Product Data Capture (Jasper) (@PREFIX@de/metas/docs/label/dataentry/JSON_POC_Report.jasper)`
-- 2025-09-28T12:15:03.590Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- Node name: `Product (M_Product)`
-- 2025-09-28T12:15:03.592Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- Node name: `Food Advice (M_FoodAdvice)`
-- 2025-09-28T12:15:03.593Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541963 AND AD_Tree_ID=10
;

-- Node name: `Customs Tariff (M_CustomsTariff)`
-- 2025-09-28T12:15:03.595Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- Node name: `Commodity Number (M_CommodityNumber)`
-- 2025-09-28T12:15:03.597Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Version (PP_Product_BOM)`
-- 2025-09-28T12:15:03.598Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Components of the BOM & Formula (PP_Product_BOMLine)`
-- 2025-09-28T12:15:03.601Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- Node name: `Discount Schema (M_DiscountSchema)`
-- 2025-09-28T12:15:03.602Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- Node name: `Discount Rows (M_DiscountSchemaBreak)`
-- 2025-09-28T12:15:03.604Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- Node name: `Lot control (M_Product_LotNumber_Quarantine)`
-- 2025-09-28T12:15:03.605Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- Node name: `Nutrition Fact (M_Nutrition_Fact)`
-- 2025-09-28T12:15:03.606Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- Node name: `Nutrition Translation (M_Nutrition_Fact_Trl)`
-- 2025-09-28T12:15:03.608Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- Node name: `Allergen (M_Allergen)`
-- 2025-09-28T12:15:03.609Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- Node name: `Allergen Übersetzung (M_Allergen_Trl)`
-- 2025-09-28T12:15:03.611Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- Node name: `BPartner Product statistics (C_BPartner_Product_Stats)`
-- 2025-09-28T12:15:03.612Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol (M_HazardSymbol)`
-- 2025-09-28T12:15:03.613Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541870 AND AD_Tree_ID=10
;

-- Node name: `Product Certification (AD_Product_Certification_v)`
-- 2025-09-28T12:15:03.614Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541904 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol Trl (M_HazardSymbol_Trl)`
-- 2025-09-28T12:15:03.616Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541871 AND AD_Tree_ID=10
;

-- Node name: `Download Partner Products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-09-28T12:15:03.617Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-09-28T12:15:03.618Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-09-28T12:15:03.619Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-09-28T12:15:03.620Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- Node name: `Package-Licensing`
-- 2025-09-28T12:15:03.621Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542248 AND AD_Tree_ID=10
;

-- Node name: `Verpackungslizenzierungs-Bewegungsdetails`
-- 2025-09-28T12:15:03.622Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542252 AND AD_Tree_ID=10
;

-- Node name: `Produkt Marktpläze (M_Product_Marketplace)`
-- 2025-09-28T12:15:03.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541864 AND AD_Tree_ID=10
;

-- Node name: `Additives (M_Additive)`
-- 2025-09-28T12:15:03.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541867 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material (PP_Product_BOMVersions)`
-- 2025-09-28T12:15:03.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Node name: `Additives translation (M_Additive_Trl)`
-- 2025-09-28T12:15:03.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541873 AND AD_Tree_ID=10
;

-- Reordering children of `Product Management`
-- Node name: `Replenishment (M_Material_Needs_Planner_V)`
-- 2025-09-28T12:15:05.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542202 AND AD_Tree_ID=10
;

-- Node name: `Ingredients (M_Ingredients)`
-- 2025-09-28T12:15:05.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541735 AND AD_Tree_ID=10
;

-- Node name: `Product Data Capture (Jasper) (@PREFIX@de/metas/docs/label/dataentry/JSON_POC_Report.jasper)`
-- 2025-09-28T12:15:05.500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- Node name: `Product (M_Product)`
-- 2025-09-28T12:15:05.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- Node name: `Food Advice (M_FoodAdvice)`
-- 2025-09-28T12:15:05.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541963 AND AD_Tree_ID=10
;

-- Node name: `Customs Tariff (M_CustomsTariff)`
-- 2025-09-28T12:15:05.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- Node name: `Commodity Number (M_CommodityNumber)`
-- 2025-09-28T12:15:05.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Version (PP_Product_BOM)`
-- 2025-09-28T12:15:05.506Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Components of the BOM & Formula (PP_Product_BOMLine)`
-- 2025-09-28T12:15:05.508Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- Node name: `Discount Schema (M_DiscountSchema)`
-- 2025-09-28T12:15:05.509Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- Node name: `Discount Rows (M_DiscountSchemaBreak)`
-- 2025-09-28T12:15:05.511Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- Node name: `Lot control (M_Product_LotNumber_Quarantine)`
-- 2025-09-28T12:15:05.512Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- Node name: `Nutrition Fact (M_Nutrition_Fact)`
-- 2025-09-28T12:15:05.513Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- Node name: `Nutrition Translation (M_Nutrition_Fact_Trl)`
-- 2025-09-28T12:15:05.514Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- Node name: `Allergen (M_Allergen)`
-- 2025-09-28T12:15:05.517Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- Node name: `Allergen Übersetzung (M_Allergen_Trl)`
-- 2025-09-28T12:15:05.519Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- Node name: `BPartner Product statistics (C_BPartner_Product_Stats)`
-- 2025-09-28T12:15:05.520Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol (M_HazardSymbol)`
-- 2025-09-28T12:15:05.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541870 AND AD_Tree_ID=10
;

-- Node name: `Product Certification (AD_Product_Certification_v)`
-- 2025-09-28T12:15:05.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541904 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol Trl (M_HazardSymbol_Trl)`
-- 2025-09-28T12:15:05.525Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541871 AND AD_Tree_ID=10
;

-- Node name: `Download Partner Products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-09-28T12:15:05.527Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-09-28T12:15:05.528Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-09-28T12:15:05.529Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-09-28T12:15:05.531Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- Node name: `Package-Licensing`
-- 2025-09-28T12:15:05.532Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542248 AND AD_Tree_ID=10
;

-- Node name: `Produkt Marktpläze (M_Product_Marketplace)`
-- 2025-09-28T12:15:05.534Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541864 AND AD_Tree_ID=10
;

-- Node name: `Verpackungslizenzierungs-Bewegungsdetails`
-- 2025-09-28T12:15:05.535Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542252 AND AD_Tree_ID=10
;

-- Node name: `Additives (M_Additive)`
-- 2025-09-28T12:15:05.536Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541867 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material (PP_Product_BOMVersions)`
-- 2025-09-28T12:15:05.539Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Node name: `Additives translation (M_Additive_Trl)`
-- 2025-09-28T12:15:05.540Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541873 AND AD_Tree_ID=10
;

-- Reordering children of `Package-Licensing`
-- Node name: `Product group (M_PackageLicensing_ProductGroup)`
-- 2025-09-28T12:15:08.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542250 AND AD_Tree_ID=10
;

-- Node name: `Verpackungslizenzierungs-Bewegungsdetails`
-- 2025-09-28T12:15:08.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542252 AND AD_Tree_ID=10
;

-- Node name: `Material group (M_PackageLicensing_MaterialGroup)`
-- 2025-09-28T12:15:08.106Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542249 AND AD_Tree_ID=10
;

-- Reordering children of `Package-Licensing`
-- Node name: `Verpackungslizenzierungs-Bewegungsdetails`
-- 2025-09-28T12:15:11.968Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542252 AND AD_Tree_ID=10
;

-- Node name: `Product group (M_PackageLicensing_ProductGroup)`
-- 2025-09-28T12:15:11.970Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542250 AND AD_Tree_ID=10
;

-- Node name: `Material group (M_PackageLicensing_MaterialGroup)`
-- 2025-09-28T12:15:11.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542249 AND AD_Tree_ID=10
;

-- Process: Package-Licencing-Report-Details(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateFrom
-- 2025-09-28T13:21:15.280Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,585503,542996,15,'DateFrom',TO_TIMESTAMP('2025-09-28 13:21:15.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Startdatum eines Abschnittes','U',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','Y','N','Datum von',10,TO_TIMESTAMP('2025-09-28 13:21:15.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T13:21:15.287Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542996 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Package-Licencing-Report-Details(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateTo
-- 2025-09-28T13:21:32.256Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,585503,542997,15,'DateTo',TO_TIMESTAMP('2025-09-28 13:21:32.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enddatum eines Abschnittes','D',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','N','N','Datum bis',20,TO_TIMESTAMP('2025-09-28 13:21:32.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T13:21:32.259Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542997 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Package-Licencing-Report-Details(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateTo
-- 2025-09-28T13:21:37.614Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-09-28 13:21:37.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542997
;

-- Process: Package-Licencing-Report-Details(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_Country_ID
-- 2025-09-28T13:22:24.679Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,192,0,585503,542998,19,'C_Country_ID',TO_TIMESTAMP('2025-09-28 13:22:24.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Land','D',0,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','Y','N','Land',30,TO_TIMESTAMP('2025-09-28 13:22:24.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T13:22:24.683Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542998 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: C_Country_ID Warehouses
-- 2025-09-28T13:23:32.386Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540744,'not exists (
select 1
from M_PriceList p
where p.M_PriceList_ID<>@M_PriceList_ID/0@
                AND p.M_PricingSystem_ID=@M_PricingSystem_ID@
                AND p.IsSOPriceList=''@IsSOPriceList@''
                AND p.C_Country_ID=C_Country.C_Country_ID
)',TO_TIMESTAMP('2025-09-28 13:23:32.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Get countries from wareouses','D','Y','C_Country_ID Warehouses','S',TO_TIMESTAMP('2025-09-28 13:23:32.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: C_Country_ID Warehouses
-- 2025-09-28T13:26:29.982Z
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT 1 FROM M_warehouse wh
                                 INNER JOIN c_location l ON l.c_location_id = wh.c_location_id
                                 INNER JOIN c_country c ON c.c_country_id = l.c_country_id
                WHERE l.c_country_id = C_Country.c_country_id and wh.isactive=''Y'')',Updated=TO_TIMESTAMP('2025-09-28 13:26:29.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540744
;

-- Process: Package-Licencing-Report-Details(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_Country_ID
-- 2025-09-28T13:26:57.237Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540744,Updated=TO_TIMESTAMP('2025-09-28 13:26:57.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542998
;

-- Value: Package-Licencing-Summary-Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-09-28T13:27:44.096Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585504,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2025-09-28 13:27:43.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','Y','Y','N','Y','',0,'Package Licensing Movement Summary','json','N','Y','xls','SELECT * FROM Package_Licensing_InOut_Summary_Report( @DateFrom/null@, @DateTo/null@, @C_Country_ID/null@)','Excel',TO_TIMESTAMP('2025-09-28 13:27:43.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Package-Licencing-Summary-Report')
;

-- 2025-09-28T13:27:44.101Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585504 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Package-Licencing-Summary-Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateFrom
-- 2025-09-28T13:28:10.934Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,585504,542999,15,'DateFrom',TO_TIMESTAMP('2025-09-28 13:28:10.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@Date@','Startdatum eines Abschnittes','U',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','Y','N','Datum von',10,TO_TIMESTAMP('2025-09-28 13:28:10.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T13:28:10.939Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542999 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Package-Licencing-Summary-Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateTo
-- 2025-09-28T13:28:24.804Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,585504,543000,15,'DateTo',TO_TIMESTAMP('2025-09-28 13:28:24.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@Date@','Enddatum eines Abschnittes','D',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','Y','N','Datum bis',20,TO_TIMESTAMP('2025-09-28 13:28:24.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T13:28:24.808Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543000 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Package-Licencing-Summary-Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_Country_ID
-- 2025-09-28T13:28:47.195Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,192,0,585504,543001,19,540744,'C_Country_ID',TO_TIMESTAMP('2025-09-28 13:28:47.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Land','U',0,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','Y','N','Land',30,TO_TIMESTAMP('2025-09-28 13:28:47.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T13:28:47.197Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543001 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Run mode: SWING_CLIENT

-- Value: Package-Licencing-Report-Details
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-09-28T13:33:18.818Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.Package_Licensing_InOut_Report_report( @DateFrom/null@, @DateTo/null@, @C_Country_ID/null@)',Updated=TO_TIMESTAMP('2025-09-28 13:33:18.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585503
;

-- Value: Package-Licencing-Summary-Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-09-28T13:33:31.864Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.Package_Licensing_InOut_Summary_Report(@DateFrom/null@, @DateTo/null@, @C_Country_ID/null@)',Updated=TO_TIMESTAMP('2025-09-28 13:33:31.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585504
;

-- Value: Package-Licencing-Report-Details
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-09-28T13:33:38.980Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.Package_Licensing_InOut_Report_report(@DateFrom/null@, @DateTo/null@, @C_Country_ID/null@)',Updated=TO_TIMESTAMP('2025-09-28 13:33:38.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585503
;

-- Process: Package-Licencing-Summary-Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-09-28T13:35:11.184Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zusammenfassung Verpackungslizenzbewegungen',Updated=TO_TIMESTAMP('2025-09-28 13:35:11.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585504
;

-- 2025-09-28T13:35:11.186Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Run mode: SWING_CLIENT

-- 2025-09-28T13:43:24.976Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584043,0,TO_TIMESTAMP('2025-09-28 13:43:24.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Zusammenfassung Verpackungslizenzbewegungen','Zusammenfassung Verpackungslizenzbewegungen',TO_TIMESTAMP('2025-09-28 13:43:24.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T13:43:24.987Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584043 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-09-28T13:43:28.311Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-28 13:43:28.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584043 AND AD_Language='de_CH'
;

-- 2025-09-28T13:43:28.336Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584043,'de_CH')
;

-- Element: null
-- 2025-09-28T13:43:30.707Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-28 13:43:30.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584043 AND AD_Language='de_DE'
;

-- 2025-09-28T13:43:30.711Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584043,'de_DE')
;

-- 2025-09-28T13:43:30.716Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584043,'de_DE')
;

-- Element: null
-- 2025-09-28T13:43:49.074Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Package Licensing Movement Summary', PrintName='Package Licensing Movement Summary',Updated=TO_TIMESTAMP('2025-09-28 13:43:49.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584043 AND AD_Language='en_US'
;

-- 2025-09-28T13:43:49.076Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-28T13:43:49.392Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584043,'en_US')
;

-- Name: Zusammenfassung Verpackungslizenzbewegungen
-- Action Type: R
-- Report: Package-Licencing-Summary-Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-09-28T13:44:49.429Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,584043,542253,0,585504,TO_TIMESTAMP('2025-09-28 13:44:49.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Package-Licencing-Summary-Report','Y','N','N','N','N','Zusammenfassung Verpackungslizenzbewegungen',TO_TIMESTAMP('2025-09-28 13:44:49.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T13:44:49.433Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542253 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-09-28T13:44:49.437Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542253, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542253)
;

-- 2025-09-28T13:44:49.446Z
/* DDL */  select update_menu_translation_from_ad_element(584043)
;

-- Reordering children of `Package-Licensing`
-- Node name: `Package Licensing Movement Details`
-- 2025-09-28T13:45:17.463Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542252 AND AD_Tree_ID=10
;

-- Node name: `Zusammenfassung Verpackungslizenzbewegungen`
-- 2025-09-28T13:45:17.465Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542253 AND AD_Tree_ID=10
;

-- Node name: `Product group (M_PackageLicensing_ProductGroup)`
-- 2025-09-28T13:45:17.466Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542250 AND AD_Tree_ID=10
;

-- Node name: `Material group (M_PackageLicensing_MaterialGroup)`
-- 2025-09-28T13:45:17.468Z
UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542249 AND AD_Tree_ID=10
;

-- Value: Package-Licencing-Report-Details
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-09-28T13:51:07.081Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.Package_Licensing_InOut_Report_report(''@DateFrom@''::date, ''@DateTo@''::date, @C_Country_ID/null@)',Updated=TO_TIMESTAMP('2025-09-28 13:51:07.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585503
;


-- Value: Package-Licencing-Report-Details
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-09-28T13:52:16.522Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.Package_Licensing_InOut_Report(''@DateFrom@''::date, ''@DateTo@''::date, @C_Country_ID/null@)',Updated=TO_TIMESTAMP('2025-09-28 13:52:16.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585503
;


-- Value: Package-Licencing-Summary-Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-09-28T13:53:55.992Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.Package_Licensing_InOut_Summary_Report(''@DateFrom@''::date, ''@DateTo@''::date, @C_Country_ID/null@)',Updated=TO_TIMESTAMP('2025-09-28 13:53:55.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585504
;

