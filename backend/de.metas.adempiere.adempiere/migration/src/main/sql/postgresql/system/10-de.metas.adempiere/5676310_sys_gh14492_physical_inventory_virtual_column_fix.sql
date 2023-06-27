-- Column: M_Inventory.C_BPartner_ID
-- Column SQL (old): (SELECT DISTINCT dp.c_bpartner_id from m_inventoryline il LEFT JOIN m_hu_assignment hua on (hua.m_hu_id = il.m_hu_id) LEFT JOIN m_inout io on (io.m_inout_id = hua.record_id) LEFT JOIN m_delivery_planning dp on (io.m_delivery_planning_id = dp.m_delivery_planning_id) where (il.m_inventory_id = M_Inventory.m_inventory_id) LIMIT 1)
-- 2023-02-09T07:40:25.072Z
UPDATE AD_Column SET ColumnSQL='(SELECT DISTINCT dp.c_bpartner_id from m_inventoryline il LEFT JOIN m_hu_assignment hua on (hua.m_hu_id = il.m_hu_id) LEFT JOIN m_inoutline iol on (iol.m_inoutline_id = hua.record_id) LEFT JOIN m_inout io on iol.m_inout_id = io.m_inout_id LEFT JOIN m_delivery_planning dp on (io.m_delivery_planning_id = dp.m_delivery_planning_id) where (il.m_inventory_id = M_Inventory.m_inventory_id) LIMIT 1)',Updated=TO_TIMESTAMP('2023-02-09 08:40:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585930
;

-- Column: M_Inventory.C_BPartner_ID
-- Source Table: M_InOutLine
-- 2023-02-09T07:41:38.291Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585930,0,540128,321,TO_TIMESTAMP('2023-02-09 08:41:38','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',3529,320,TO_TIMESTAMP('2023-02-09 08:41:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- Column SQL (old): (SELECT DISTINCT dp.c_order_id from m_inventoryline il LEFT JOIN m_hu_assignment hua on (hua.m_hu_id = il.m_hu_id) LEFT JOIN m_inout io on (io.m_inout_id = hua.record_id) LEFT JOIN m_delivery_planning dp on (io.m_delivery_planning_id = dp.m_delivery_planning_id) where (il.m_inventory_id = M_Inventory.m_inventory_id) LIMIT 1)
-- 2023-02-09T07:42:59.566Z
UPDATE AD_Column SET ColumnSQL='(SELECT DISTINCT dp.c_order_id from m_inventoryline il LEFT JOIN m_hu_assignment hua on (hua.m_hu_id = il.m_hu_id) LEFT JOIN m_inoutline iol on (iol.m_inoutline_id = hua.record_id) LEFT JOIN m_inout io on iol.m_inout_id = io.m_inout_id LEFT JOIN m_delivery_planning dp on (io.m_delivery_planning_id = dp.m_delivery_planning_id) where (il.m_inventory_id = M_Inventory.m_inventory_id) LIMIT 1)',Updated=TO_TIMESTAMP('2023-02-09 08:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585929
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- Source Table: M_InOutLine
-- 2023-02-09T07:47:48.991Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585929,0,540129,321,TO_TIMESTAMP('2023-02-09 08:47:48','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',3529,320,TO_TIMESTAMP('2023-02-09 08:47:48','YYYY-MM-DD HH24:MI:SS'),100)
;

