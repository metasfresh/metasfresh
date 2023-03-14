-- Value: C_Invoice_Overdue_With_Open_Order_Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-02-03T16:07:10.101Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585212,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2023-02-03 18:07:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Overdue with open orders list','json','N','N','xls','select * from getOverdueInvoicesWithOpenOrders();','Excel',TO_TIMESTAMP('2023-02-03 18:07:09','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_Overdue_With_Open_Order_Excel')
;

-- 2023-02-03T16:07:10.104Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585212 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-02-03T16:07:44.062Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582016,0,TO_TIMESTAMP('2023-02-03 18:07:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Overdue with open orders list','Overdue with open orders list',TO_TIMESTAMP('2023-02-03 18:07:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:07:44.066Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582016 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: Overdue with open orders list
-- Action Type: P
-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-02-03T16:10:28.047Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,582016,542049,0,585212,TO_TIMESTAMP('2023-02-03 18:10:27','YYYY-MM-DD HH24:MI:SS'),100,'D','C_Invoice_Overdue_With_Open_Order_Excel','Y','N','N','N','N','Overdue with open orders list',TO_TIMESTAMP('2023-02-03 18:10:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:10:28.049Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542049 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-02-03T16:10:28.052Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542049, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542049)
;

-- 2023-02-03T16:10:28.086Z
/* DDL */  select update_menu_translation_from_ad_element(582016) 
;

-- Reordering children of `Depreciation Setup`
-- Node name: `Depreciation Methods (A_Depreciation)`
-- 2023-02-03T16:10:28.665Z
UPDATE AD_TreeNodeMM SET Parent_ID=53171, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53173 AND AD_Tree_ID=10
;

-- Node name: `Depreciation Calculation Method (A_Depreciation_Method)`
-- 2023-02-03T16:10:28.667Z
UPDATE AD_TreeNodeMM SET Parent_ID=53171, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53176 AND AD_Tree_ID=10
;

-- Node name: `Depreciation First Year Conventions  (A_Depreciation_Convention)`
-- 2023-02-03T16:10:28.668Z
UPDATE AD_TreeNodeMM SET Parent_ID=53171, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53175 AND AD_Tree_ID=10
;

-- Node name: `Depreciation Period Spread Type (A_Asset_Spread)`
-- 2023-02-03T16:10:28.669Z
UPDATE AD_TreeNodeMM SET Parent_ID=53171, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53172 AND AD_Tree_ID=10
;

-- Node name: `Depreciation Tables (A_Depreciation_Table_Header)`
-- 2023-02-03T16:10:28.669Z
UPDATE AD_TreeNodeMM SET Parent_ID=53171, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53174 AND AD_Tree_ID=10
;

-- Node name: `Overdue with open orders list`
-- 2023-02-03T16:10:28.670Z
UPDATE AD_TreeNodeMM SET Parent_ID=53171, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542049 AND AD_Tree_ID=10
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2023-02-03T16:13:16.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2023-02-03T16:13:16.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2023-02-03T16:13:16.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2023-02-03T16:13:16.497Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2023-02-03T16:13:16.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2023-02-03T16:13:16.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2023-02-03T16:13:16.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2023-02-03T16:13:16.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2023-02-03T16:13:16.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2023-02-03T16:13:16.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2023-02-03T16:13:16.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2023-02-03T16:13:16.506Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing (M_HU_Trace)`
-- 2023-02-03T16:13:16.507Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning (M_Delivery_Planning)`
-- 2023-02-03T16:13:16.508Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Delivery Instruction (M_ShipperTransportation)`
-- 2023-02-03T16:13:16.509Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542032 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2023-02-03T16:13:16.510Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2023-02-03T16:13:16.511Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2023-02-03T16:13:16.512Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2023-02-03T16:13:16.513Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2023-02-03T16:13:16.514Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2023-02-03T16:13:16.515Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2023-02-03T16:13:16.517Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2023-02-03T16:13:16.518Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2023-02-03T16:13:16.519Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2023-02-03T16:13:16.520Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-02-03T16:13:16.521Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-02-03T16:13:16.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-02-03T16:13:16.523Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2023-02-03T16:13:16.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2023-02-03T16:13:16.526Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2023-02-03T16:13:16.527Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation (M_MeansOfTransportation)`
-- 2023-02-03T16:13:16.528Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Department (M_Department)`
-- 2023-02-03T16:13:16.529Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542041 AND AD_Tree_ID=10
;

-- Node name: `Overdue with open orders list`
-- 2023-02-03T16:13:16.530Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542049 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit (Departments) (C_BPartner_CreditLimit_Departments_V)`
-- 2023-02-03T16:13:16.532Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542045 AND AD_Tree_ID=10
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2023-02-03T16:13:19.540Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2023-02-03T16:13:19.541Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2023-02-03T16:13:19.542Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2023-02-03T16:13:19.543Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2023-02-03T16:13:19.544Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2023-02-03T16:13:19.560Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2023-02-03T16:13:19.563Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2023-02-03T16:13:19.564Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2023-02-03T16:13:19.565Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2023-02-03T16:13:19.566Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2023-02-03T16:13:19.567Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2023-02-03T16:13:19.568Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing (M_HU_Trace)`
-- 2023-02-03T16:13:19.569Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning (M_Delivery_Planning)`
-- 2023-02-03T16:13:19.570Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Delivery Instruction (M_ShipperTransportation)`
-- 2023-02-03T16:13:19.571Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542032 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2023-02-03T16:13:19.572Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2023-02-03T16:13:19.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2023-02-03T16:13:19.574Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2023-02-03T16:13:19.575Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2023-02-03T16:13:19.576Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2023-02-03T16:13:19.577Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2023-02-03T16:13:19.578Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2023-02-03T16:13:19.578Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2023-02-03T16:13:19.579Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2023-02-03T16:13:19.580Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-02-03T16:13:19.581Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-02-03T16:13:19.582Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-02-03T16:13:19.583Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2023-02-03T16:13:19.584Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2023-02-03T16:13:19.585Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2023-02-03T16:13:19.586Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation (M_MeansOfTransportation)`
-- 2023-02-03T16:13:19.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Department (M_Department)`
-- 2023-02-03T16:13:19.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542041 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit (Departments) (C_BPartner_CreditLimit_Departments_V)`
-- 2023-02-03T16:13:19.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542045 AND AD_Tree_ID=10
;

-- Node name: `Overdue with open orders list`
-- 2023-02-03T16:13:19.590Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542049 AND AD_Tree_ID=10
;

