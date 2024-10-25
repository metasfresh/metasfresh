-- Run mode: SWING_CLIENT

-- Tab: Inventur(168,D) -> Bestandszählung
-- Table: M_Inventory
-- 2024-10-25T10:15:01.081Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2024-10-25 10:15:01.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=255
;

-- Tab: Inventur(168,D) -> Bestandszählung
-- Table: M_Inventory
-- 2024-10-25T10:15:03.783Z
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2024-10-25 10:15:03.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=255
;

-- Process: M_Inventory_Mark_As_Counted(de.metas.inventory.process.M_Inventory_Mark_As_Counted)
-- Table: M_Inventory
-- EntityType: D
-- 2024-10-25T10:42:34.564Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2024-10-25 10:42:34.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541387
;

-- Process: M_Inventory_Mark_As_Counted(de.metas.inventory.process.M_Inventory_Mark_As_Counted)
-- Table: M_Inventory
-- EntityType: D
-- 2024-10-25T10:43:35.411Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='Y',Updated=TO_TIMESTAMP('2024-10-25 10:43:35.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541387
;

-- Process: M_Inventory_Mark_As_Counted(de.metas.inventory.process.M_Inventory_Mark_As_Counted)
-- Table: M_Inventory
-- EntityType: D
-- 2024-10-25T10:52:41.640Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction_Default='Y',Updated=TO_TIMESTAMP('2024-10-25 10:52:41.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541387
;

-- Process: WEBUI_Order_ProductsProposal_Launcher(de.metas.ui.web.order.products_proposal.process.WEBUI_Order_ProductsProposal_Launcher)
-- Table: C_Order
-- Tab: Auftrag(143,D) -> Auftragsposition(187,D)
-- Window: Auftrag(143,D)
-- EntityType: D
-- 2024-10-25T11:00:13.022Z
UPDATE AD_Table_Process SET WEBUI_IncludedTabTopAction='N',Updated=TO_TIMESTAMP('2024-10-25 11:00:13.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=540665
;

-- Process: WEBUI_Order_ProductsProposal_Launcher(de.metas.ui.web.order.products_proposal.process.WEBUI_Order_ProductsProposal_Launcher)
-- Table: C_Order
-- Tab: Auftrag(143,D) -> Auftragsposition(187,D)
-- Window: Auftrag(143,D)
-- EntityType: D
-- 2024-10-25T11:00:51.180Z
UPDATE AD_Table_Process SET WEBUI_IncludedTabTopAction='Y',Updated=TO_TIMESTAMP('2024-10-25 11:00:51.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=540665
;

-- Process: M_Inventory_Mark_As_Counted(de.metas.inventory.process.M_Inventory_Mark_As_Counted)
-- Table: M_Inventory
-- EntityType: D
-- 2024-10-25T11:01:20.779Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N', WEBUI_IncludedTabTopAction='Y', WEBUI_ViewAction='N', WEBUI_ViewQuickAction='N',Updated=TO_TIMESTAMP('2024-10-25 11:01:20.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541387
;

-- Value: M_Inventory_Mark_As_Counted
-- Classname: de.metas.inventory.process.M_Inventory_Mark_As_Counted
-- 2024-10-25T11:33:06.701Z
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2024-10-25 11:33:06.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585274
;

-- Process: M_Inventory_Mark_As_Counted(de.metas.inventory.process.M_Inventory_Mark_As_Counted)
-- Table: M_Inventory
-- EntityType: D
-- 2024-10-25T11:33:31.982Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='Y', WEBUI_IncludedTabTopAction='N',Updated=TO_TIMESTAMP('2024-10-25 11:33:31.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541387
;

-- Value: M_Inventory_CreateLines_ForLocatorAndProduct
-- Classname: de.metas.handlingunits.inventory.draftlinescreator.process.M_Inventory_CreateLines_ForLocatorAndProduct
-- 2024-10-25T11:35:25.440Z
UPDATE AD_Process SET AllowProcessReRun='Y',Updated=TO_TIMESTAMP('2024-10-25 11:35:25.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540935
;

-- Value: M_Inventory_CreateLines_ForLocatorAndProduct
-- Classname: de.metas.handlingunits.inventory.draftlinescreator.process.M_Inventory_CreateLines_ForLocatorAndProduct
-- 2024-10-25T11:36:40.920Z
UPDATE AD_Process SET AllowProcessReRun='N', RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2024-10-25 11:36:40.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540935
;

