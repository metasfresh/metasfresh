-- Reference: ModCntr_Log_DocumentType
-- Value: Inventory
-- ValueName: Inventory
-- 2023-07-04T13:37:50.144888400Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543519,541770,TO_TIMESTAMP('2023-07-04 16:37:49.948','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Inventory',TO_TIMESTAMP('2023-07-04 16:37:49.948','YYYY-MM-DD HH24:MI:SS.US'),100,'Inventory','Inventory')
;

-- 2023-07-04T13:37:50.151887500Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543519 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> Inventory_Inventory
-- 2023-07-04T13:38:04.229728200Z
UPDATE AD_Ref_List_Trl SET Name='Inventur',Updated=TO_TIMESTAMP('2023-07-04 16:38:04.229','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543519
;

-- Reference Item: ModCntr_Log_DocumentType -> Inventory_Inventory
-- 2023-07-04T13:38:06.542112200Z
UPDATE AD_Ref_List_Trl SET Name='Inventur',Updated=TO_TIMESTAMP('2023-07-04 16:38:06.542','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543519
;

-- 2023-07-04T13:38:06.545076400Z
UPDATE AD_Ref_List SET Name='Inventur' WHERE AD_Ref_List_ID=543519
;

-- Reference Item: ModCntr_Log_DocumentType -> Inventory_Inventory
-- 2023-07-04T13:38:08.123851900Z
UPDATE AD_Ref_List_Trl SET Name='Inventur',Updated=TO_TIMESTAMP('2023-07-04 16:38:08.123','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543519
;

-- Reference Item: ModCntr_Log_DocumentType -> Inventory_Inventory
-- 2023-07-04T13:38:09.661611200Z
UPDATE AD_Ref_List_Trl SET Name='Inventur',Updated=TO_TIMESTAMP('2023-07-04 16:38:09.661','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Ref_List_ID=543519
;

-- Name: ModCntr_Log_Target_M_Inventory_PhyWindow
-- 2023-07-05T08:58:57.376574700Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541793,TO_TIMESTAMP('2023-07-05 11:58:57.235','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ModCntr_Log_Target_M_Inventory_PhyWindow',TO_TIMESTAMP('2023-07-05 11:58:57.235','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-05T08:58:57.379577700Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541793 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_Target_M_Inventory_PhyWindow
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-07-05T09:04:33.303583800Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,586758,0,541793,542338,null,TO_TIMESTAMP('2023-07-05 12:04:33.299','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-05 12:04:33.299','YYYY-MM-DD HH24:MI:SS.US'),100,'EXISTS ( SELECT 1 FROM m_inventoryline il INNER JOIN m_inventory inv on il.m_inventory_id = inv.m_inventory_id  where inv.m_inventory_id = @M_Inventory_ID@  AND ModCntr_Log.ad_table_id = get_table_id(''m_inventoryline'') AND ModCntr_Log.Record_ID = il.m_inventoryLine_ID )')
;

-- 2023-07-05T09:05:47.718683100Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,53249,541793,540412,TO_TIMESTAMP('2023-07-05 12:05:47.579','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','M_Inventory -> ModCntr_Log',TO_TIMESTAMP('2023-07-05 12:05:47.579','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: M_Inventory_Source_InternalInventory
-- 2023-07-05T09:06:56.085123800Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541794,TO_TIMESTAMP('2023-07-05 12:06:55.94','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','M_Inventory_Source_InternalInventory',TO_TIMESTAMP('2023-07-05 12:06:55.94','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-05T09:06:56.088120300Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541794 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Inventory_Source_InternalInventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-07-05T09:07:55.310616100Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3542,0,541794,321,null,TO_TIMESTAMP('2023-07-05 12:07:55.307','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-05 12:07:55.307','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: ModCntr_Log_Target_M_Inventory_InternalWindow
-- 2023-07-05T09:08:25.649842400Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541795,TO_TIMESTAMP('2023-07-05 12:08:25.514','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ModCntr_Log_Target_M_Inventory_InternalWindow',TO_TIMESTAMP('2023-07-05 12:08:25.514','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-05T09:08:25.650842300Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541795 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_Target_M_Inventory_InternalWindow
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-07-05T09:09:15.104212Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,586758,0,541795,542338,null,TO_TIMESTAMP('2023-07-05 12:09:15.1','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-05 12:09:15.1','YYYY-MM-DD HH24:MI:SS.US'),100,'EXISTS ( SELECT 1 FROM m_inventoryline il INNER JOIN m_inventory inv on il.m_inventory_id = inv.m_inventory_id  where inv.m_inventory_id = @M_Inventory_ID@  AND ModCntr_Log.ad_table_id = get_table_id(''m_inventoryline'') AND ModCntr_Log.Record_ID = il.m_inventoryLine_ID )')
;

-- 2023-07-05T09:10:03.362616500Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541794,541795,540413,TO_TIMESTAMP('2023-07-05 12:10:03.21','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','M_Inventory (Internal) -> ModCntr_Log',TO_TIMESTAMP('2023-07-05 12:10:03.21','YYYY-MM-DD HH24:MI:SS.US'),100)
;

