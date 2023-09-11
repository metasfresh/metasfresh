-- Name: ModCntr_Log_Status
-- 2023-09-11T08:04:22.886502300Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541821,TO_TIMESTAMP('2023-09-11 11:04:21.77','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','ModCntr_Log_Status',TO_TIMESTAMP('2023-09-11 11:04:21.77','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-11T08:04:22.905367700Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541821 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_Status
-- Table: ModCntr_Log_Status
-- Key: ModCntr_Log_Status.ModCntr_Log_Status_ID
-- 2023-09-11T08:10:13.570741900Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,587337,0,541821,542363,TO_TIMESTAMP('2023-09-11 11:10:13.527','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2023-09-11 11:10:13.527','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-11T08:10:42.859379Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540422,TO_TIMESTAMP('2023-09-11 11:10:42.73','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','Y','ModCntr_Log_Status_TableRecordIdTarget',TO_TIMESTAMP('2023-09-11 11:10:42.73','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-11T08:10:52.612340800Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541821,Updated=TO_TIMESTAMP('2023-09-11 11:10:52.61','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540422
;

-- Name: ModCntr_Log_Status_For_C_Order
-- 2023-09-11T08:12:54.958880700Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541822,TO_TIMESTAMP('2023-09-11 11:12:54.792','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','ModCntr_Log_Status_For_C_Order',TO_TIMESTAMP('2023-09-11 11:12:54.792','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-11T08:12:54.962377500Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541822 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_Status_For_C_Order
-- Table: ModCntr_Log_Status
-- Key: ModCntr_Log_Status.ModCntr_Log_Status_ID
-- 2023-09-11T08:15:46.994821800Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,587337,0,541822,542363,TO_TIMESTAMP('2023-09-11 11:15:46.988','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2023-09-11 11:15:46.988','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Log_Status.AD_Table_ID = get_table_id(''C_OrderLine'') AND ModCntr_Log_Status.Record_ID IN  (SELECT c_orderline_id        from c_orderline ol        where ol.c_order_id = @C_Order_ID / -1@)')
;

-- 2023-09-11T08:16:20.602021400Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2023-09-11 11:16:20.6','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540422
;

-- 2023-09-11T08:16:26.298442900Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541822,Updated=TO_TIMESTAMP('2023-09-11 11:16:26.296','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540422
;

-- 2023-09-11T08:16:50.672772100Z
UPDATE AD_RelationType SET Name='C_Order -> ModCntr_Log_Status',Updated=TO_TIMESTAMP('2023-09-11 11:16:50.671','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540422
;

-- 2023-09-11T08:47:49.924627900Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,53249,541793,540423,TO_TIMESTAMP('2023-09-11 11:47:49.784','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','M_Inventory -> ModCntr_Log_Status',TO_TIMESTAMP('2023-09-11 11:47:49.784','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-11T08:49:45.191492500Z
UPDATE AD_RelationType SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2023-09-11 11:49:45.189','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540423
;

-- Name: ModCntr_Log_Status_Target_M_Inventory_PhyWindow
-- 2023-09-11T08:49:58.410586700Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541823,TO_TIMESTAMP('2023-09-11 11:49:58.285','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','ModCntr_Log_Status_Target_M_Inventory_PhyWindow',TO_TIMESTAMP('2023-09-11 11:49:58.285','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-11T08:49:58.414500300Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541823 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_Status_Target_M_Inventory_PhyWindow
-- Table: ModCntr_Log_Status
-- Key: ModCntr_Log_Status.ModCntr_Log_Status_ID
-- 2023-09-11T08:51:22.334676300Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,587337,587337,0,541823,542363,TO_TIMESTAMP('2023-09-11 11:51:22.33','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2023-09-11 11:51:22.33','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: ModCntr_Log_Status_Target_M_Inventory_PhyWindow
-- Table: ModCntr_Log_Status
-- Key: ModCntr_Log_Status.ModCntr_Log_Status_ID
-- 2023-09-11T08:53:00.822773100Z
UPDATE AD_Ref_Table SET WhereClause='ModCntr_Log_Status.AD_Table_ID = get_table_id(''M_InventoryLine'') AND ModCntr_Log_Status.Record_ID IN  (SELECT M_InventoryLine_ID        from M_InventoryLine il        where il.m_inventory_id = @M_Inventory_ID / -1@)',Updated=TO_TIMESTAMP('2023-09-11 11:53:00.821','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541823
;

-- 2023-09-11T08:54:23.454240300Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541822,Updated=TO_TIMESTAMP('2023-09-11 11:54:23.452','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540423
;

-- 2023-09-11T08:54:51.222044700Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541823,Updated=TO_TIMESTAMP('2023-09-11 11:54:51.221','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540423
;

-- 2023-09-11T08:55:44.017995600Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541794,541823,540424,TO_TIMESTAMP('2023-09-11 11:55:43.863','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','M_Inventory (Internal) -> ModCntr_Log',TO_TIMESTAMP('2023-09-11 11:55:43.863','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-11T08:55:58.508317600Z
UPDATE AD_RelationType SET Name='M_Inventory (Internal) -> ModCntr_Log_Status',Updated=TO_TIMESTAMP('2023-09-11 11:55:58.507','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540424
;

-- 2023-09-11T09:16:12.569796400Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541797,541799,540425,TO_TIMESTAMP('2023-09-11 12:16:12.347','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','M_InOut Receipt -> ModCntr_Log_Status',TO_TIMESTAMP('2023-09-11 12:16:12.347','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: ModCntr_Log_Status target for M_InOut Receipt
-- 2023-09-11T09:16:35.555258600Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541824,TO_TIMESTAMP('2023-09-11 12:16:35.368','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','N','ModCntr_Log_Status target for M_InOut Receipt',TO_TIMESTAMP('2023-09-11 12:16:35.368','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-11T09:16:35.559782900Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541824 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: ModCntr_Log_Status target for M_InOut Receipt
-- 2023-09-11T09:29:12.160649500Z
UPDATE AD_Reference SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2023-09-11 12:29:12.159','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541824
;

-- Reference: ModCntr_Log_Status target for M_InOut Receipt
-- Table: ModCntr_Log_Status
-- Key: ModCntr_Log_Status.ModCntr_Log_Status_ID
-- 2023-09-11T09:30:17.279045300Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,587337,0,541824,542363,TO_TIMESTAMP('2023-09-11 12:30:17.272','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2023-09-11 12:30:17.272','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Log_Status.AD_Table_ID = get_table_id(''M_InOutLine'') AND ModCntr_Log_Status.Record_ID IN  (SELECT M_InOutLine_ID        from M_InOutLine il        where il.M_InOut_ID = @M_InOut_ID / -1@)')
;

-- 2023-09-11T09:30:37.122970300Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2023-09-11 12:30:37.12','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540425
;

-- 2023-09-11T09:30:44.253494900Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541824,Updated=TO_TIMESTAMP('2023-09-11 12:30:44.252','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540425
;

-- 2023-09-11T09:41:14.659782100Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540100,541805,540426,TO_TIMESTAMP('2023-09-11 12:41:14.393','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','C_Order (SO)-> ModCntr_Log_Status',TO_TIMESTAMP('2023-09-11 12:41:14.393','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-11T09:41:25.663159700Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541822,Updated=TO_TIMESTAMP('2023-09-11 12:41:25.661','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540426
;

-- 2023-09-11T09:41:47.990538100Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540250,541822,540427,TO_TIMESTAMP('2023-09-11 12:41:47.799','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','C_Order (PO)-> ModCntr_Log_Status',TO_TIMESTAMP('2023-09-11 12:41:47.799','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-11T09:44:34.329183800Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,337,541812,540428,TO_TIMESTAMP('2023-09-11 12:44:34.105','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','M_InOut -> ModCntr_Log_Status',TO_TIMESTAMP('2023-09-11 12:44:34.105','YYYY-MM-DD HH24:MI:SS.US'),100)
;
-- Name: ModCntr_Log_Status target for M_InOut
-- 2023-09-11T09:45:56.305247400Z
UPDATE AD_Reference SET Name='ModCntr_Log_Status target for M_InOut',Updated=TO_TIMESTAMP('2023-09-11 12:45:56.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541824
;

-- 2023-09-11T09:45:56.309760800Z
UPDATE AD_Reference_Trl trl SET Name='ModCntr_Log_Status target for M_InOut' WHERE AD_Reference_ID=541824 AND AD_Language='de_DE'
;

-- 2023-09-11T09:45:59.779040600Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541824,Updated=TO_TIMESTAMP('2023-09-11 12:45:59.778','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540428
;

-- 2023-09-11T09:46:32.255772700Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540101,541815,540429,TO_TIMESTAMP('2023-09-11 12:46:32.102','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','C_Invoice (SO) -> ModCntr_Log_Status',TO_TIMESTAMP('2023-09-11 12:46:32.102','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: ModCntr_Log_Status_Target_C_Invoice
-- 2023-09-11T09:46:54.557181600Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541827,TO_TIMESTAMP('2023-09-11 12:46:54.392','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','ModCntr_Log_Status_Target_C_Invoice',TO_TIMESTAMP('2023-09-11 12:46:54.392','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-11T09:46:54.560511800Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541827 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_Status_Target_C_Invoice
-- Table: ModCntr_Log_Status
-- Key: ModCntr_Log_Status.ModCntr_Log_Status_ID
-- 2023-09-11T09:47:41.283144900Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,587337,0,541827,542363,TO_TIMESTAMP('2023-09-11 12:47:41.276','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2023-09-11 12:47:41.276','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Log_Status.AD_Table_ID = get_table_id(''C_InvoiceLine'') AND ModCntr_Log_Status.Record_ID IN  (SELECT C_InvoiceLine_ID        from C_InvoiceLine il        where il.C_Invoice_ID = @C_Invoice_ID / -1@)')
;

-- 2023-09-11T09:47:52.504953Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2023-09-11 12:47:52.502','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540429
;

-- 2023-09-11T09:47:57.219800100Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541827,Updated=TO_TIMESTAMP('2023-09-11 12:47:57.218','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540429
;

