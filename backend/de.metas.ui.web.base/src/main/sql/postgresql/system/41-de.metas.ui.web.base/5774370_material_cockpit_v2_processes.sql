-- Run mode: SWING_CLIENT

-- Value: QtyDemand_QtySupply_V_to_ShipmentSchedule
-- Classname: de.metas.material.process.QtyDemand_QtySupply_V_to_ShipmentSchedule
-- 2025-10-23T15:41:57.105Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585513,'Y','de.metas.material.process.QtyDemand_QtySupply_V_to_ShipmentSchedule','N',TO_TIMESTAMP('2025-10-23 15:41:56.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Sprung zu Lieferdisposition','json','N','N','xls','Java',TO_TIMESTAMP('2025-10-23 15:41:56.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyDemand_QtySupply_V_to_ShipmentSchedule')
;

-- 2025-10-23T15:41:57.111Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585513 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: QtyDemand_QtySupply_V_to_ShipmentSchedule(de.metas.material.process.QtyDemand_QtySupply_V_to_ShipmentSchedule)
-- 2025-10-23T15:42:22.488Z
UPDATE AD_Process_Trl SET Name='Go to ShipmentDisposition',Updated=TO_TIMESTAMP('2025-10-23 15:42:22.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585513
;

-- 2025-10-23T15:42:22.490Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: QtyDemand_QtySupply_V_to_ShipmentSchedule(de.metas.material.process.QtyDemand_QtySupply_V_to_ShipmentSchedule)
-- Table: QtyDemand_QtySupply_V
-- EntityType: D
-- 2025-10-23T15:42:55.587Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585513,542218,541577,TO_TIMESTAMP('2025-10-23 15:42:55.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-10-23 15:42:55.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','Y')
;

-- Table: QtyDemand_QtySupply_V
-- 2025-10-23T15:45:24.346Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2025-10-23 15:45:24.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542218
;

-- Reordering children of `Warehouse Management`
-- Node name: `Users assigned to workplace (C_Workplace_User_Assign)`
-- 2025-10-23T15:46:14.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542121 AND AD_Tree_ID=10
;

-- Node name: `Workplace (C_Workplace)`
-- 2025-10-23T15:46:14.030Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse (M_Warehouse)`
-- 2025-10-23T15:46:14.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type (M_Warehouse_Type)`
-- 2025-10-23T15:46:14.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move (M_Movement)`
-- 2025-10-23T15:46:14.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme (M_Inventory)`
-- 2025-10-23T15:46:14.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase (Fresh_QtyOnHand)`
-- 2025-10-23T15:46:14.035Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule (MD_Candidate)`
-- 2025-10-23T15:46:14.036Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast (M_Forecast)`
-- 2025-10-23T15:46:14.037Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Forecast Lines (M_ForecastLine)`
-- 2025-10-23T15:46:14.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542225 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit (MD_Cockpit)`
-- 2025-10-23T15:46:14.039Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit v2 (QtyDemand_QtySupply_V)`
-- 2025-10-23T15:46:14.039Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542264 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-23T15:46:14.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-23T15:46:14.041Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-23T15:46:14.042Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory (M_Inventory)`
-- 2025-10-23T15:46:14.043Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting (M_InventoryLine)`
-- 2025-10-23T15:46:14.044Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Handling Units (M_InventoryLine_HU)`
-- 2025-10-23T15:46:14.044Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542254 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate (M_Inventory_Candidate)`
-- 2025-10-23T15:46:14.046Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule Quantity Details (MD_Candidate_QtyDetails)`
-- 2025-10-23T15:46:14.046Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542226 AND AD_Tree_ID=10
;

-- Process: QtyDemand_QtySupply_V_to_ShipmentSchedule(de.metas.material.process.QtyDemand_QtySupply_V_to_ShipmentSchedule)
-- 2025-10-23T15:58:59.592Z
UPDATE AD_Process_Trl SET Name='Go to Shipment Disposition',Updated=TO_TIMESTAMP('2025-10-23 15:58:59.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585513
;

-- 2025-10-23T15:58:59.593Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: QtyDemand_QtySupply_V_to_ReceiptSchedule
-- Classname: de.metas.material.process.QtyDemand_QtySupply_V_to_ReceiptSchedule
-- 2025-10-23T16:48:39.061Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585514,'Y','de.metas.material.process.QtyDemand_QtySupply_V_to_ReceiptSchedule','N',TO_TIMESTAMP('2025-10-23 16:48:38.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Sprung zu Wareneingangsdispo','json','N','N','xls','Java',TO_TIMESTAMP('2025-10-23 16:48:38.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyDemand_QtySupply_V_to_ReceiptSchedule')
;

-- 2025-10-23T16:48:39.067Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585514 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: QtyDemand_QtySupply_V_to_ReceiptSchedule(de.metas.material.process.QtyDemand_QtySupply_V_to_ReceiptSchedule)
-- Table: QtyDemand_QtySupply_V
-- EntityType: D
-- 2025-10-23T16:48:58.611Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585514,542218,541578,TO_TIMESTAMP('2025-10-23 16:48:58.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-10-23 16:48:58.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N')
;

-- Process: QtyDemand_QtySupply_V_to_ReceiptSchedule(de.metas.material.process.QtyDemand_QtySupply_V_to_ReceiptSchedule)
-- 2025-10-23T16:54:00.085Z
UPDATE AD_Process_Trl SET Name='Go to Material Receipt Candidate',Updated=TO_TIMESTAMP('2025-10-23 16:54:00.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585514
;

-- 2025-10-23T16:54:00.086Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: QtyDemand_QtySupply_V_to_Forecast
-- Classname: de.metas.material.process.QtyDemand_QtySupply_V_to_Forecast
-- 2025-10-23T16:54:40.309Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585515,'Y','de.metas.material.process.QtyDemand_QtySupply_V_to_Forecast','N',TO_TIMESTAMP('2025-10-23 16:54:40.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Sprung zu Prognose','json','N','N','xls','Java',TO_TIMESTAMP('2025-10-23 16:54:40.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyDemand_QtySupply_V_to_Forecast')
;

-- 2025-10-23T16:54:40.311Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585515 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: QtyDemand_QtySupply_V_to_Forecast(de.metas.material.process.QtyDemand_QtySupply_V_to_Forecast)
-- 2025-10-23T16:54:55.954Z
UPDATE AD_Process_Trl SET Name='Go to Forecast',Updated=TO_TIMESTAMP('2025-10-23 16:54:55.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585515
;

-- 2025-10-23T16:54:55.955Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: QtyDemand_QtySupply_V_to_PP_Order_Candidate
-- Classname: de.metas.material.process.QtyDemand_QtySupply_V_to_PP_Order_Candidate
-- 2025-10-23T16:56:27.040Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585516,'Y','de.metas.material.process.QtyDemand_QtySupply_V_to_PP_Order_Candidate','N',TO_TIMESTAMP('2025-10-23 16:56:26.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Sprung zu Produktionsdisposition','json','N','N','xls','Java',TO_TIMESTAMP('2025-10-23 16:56:26.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyDemand_QtySupply_V_to_PP_Order_Candidate')
;

-- 2025-10-23T16:56:27.042Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585516 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: QtyDemand_QtySupply_V_to_PP_Order_Candidate(de.metas.material.process.QtyDemand_QtySupply_V_to_PP_Order_Candidate)
-- 2025-10-23T16:56:37.492Z
UPDATE AD_Process_Trl SET Name='Sprung zu Production disposition',Updated=TO_TIMESTAMP('2025-10-23 16:56:37.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585516
;

-- 2025-10-23T16:56:37.493Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: QtyDemand_QtySupply_V_to_PP_Order_Candidate(de.metas.material.process.QtyDemand_QtySupply_V_to_PP_Order_Candidate)
-- Table: QtyDemand_QtySupply_V
-- EntityType: D
-- 2025-10-23T16:59:00.672Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585516,542218,541579,TO_TIMESTAMP('2025-10-23 16:59:00.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-10-23 16:59:00.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N')
;

-- Process: QtyDemand_QtySupply_V_to_Forecast(de.metas.material.process.QtyDemand_QtySupply_V_to_Forecast)
-- Table: QtyDemand_QtySupply_V
-- EntityType: D
-- 2025-10-23T16:59:23.659Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585515,542218,541580,TO_TIMESTAMP('2025-10-23 16:59:23.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-10-23 16:59:23.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N')
;

-- Process: QtyDemand_QtySupply_V_to_PP_Order_Candidate(de.metas.material.process.QtyDemand_QtySupply_V_to_PP_Order_Candidate)
-- 2025-10-23T17:07:33.083Z
UPDATE AD_Process_Trl SET Name='Go to Manufacturing Order',Updated=TO_TIMESTAMP('2025-10-23 17:07:33.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585516
;

-- 2025-10-23T17:07:33.084Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Tab: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply
-- Table: QtyDemand_QtySupply_V
-- 2025-10-23T17:29:37.710Z
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2025-10-23 17:29:37.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548476
;

