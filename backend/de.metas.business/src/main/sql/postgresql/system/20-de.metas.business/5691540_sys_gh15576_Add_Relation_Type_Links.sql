-- Name: C_Order (SO) => M_Inventory
-- 2023-06-13T16:45:01.143Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541744,TO_TIMESTAMP('2023-06-13 19:45:00','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','C_Order (SO) => M_Inventory',TO_TIMESTAMP('2023-06-13 19:45:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-13T16:45:01.155Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541744 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Order (SO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T16:51:32.750Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3542,0,541744,321,TO_TIMESTAMP('2023-06-13 19:51:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','N',TO_TIMESTAMP('2023-06-13 19:51:32','YYYY-MM-DD HH24:MI:SS'),100,'m_inventory.M_Inventory_ID IN (SELECT m_inventory_id from m_delivery_planning      inner join c_order on m_delivery_planning.c_order_id = c_order.c_order_id and c_order.issotrx=''N''     inner join m_inventory on c_order.c_order_id = m_inventory.c_po_order_id where c_order.link_order_id= @C_Order_ID/-1@)')
;

-- Name: C_Order (SO) => M_Inventory
-- 2023-06-13T16:51:50.889Z
UPDATE AD_Reference SET EntityType='de.metas.order',Updated=TO_TIMESTAMP('2023-06-13 19:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541744
;

-- 2023-06-13T16:52:47.149Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,290,540385,TO_TIMESTAMP('2023-06-13 19:52:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order (SO) => M_Inventory',TO_TIMESTAMP('2023-06-13 19:52:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-13T16:53:02.624Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541744,Updated=TO_TIMESTAMP('2023-06-13 19:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540385
;

-- Name: C_Order (PO) => M_Inventory
-- 2023-06-13T17:11:59.334Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541745,TO_TIMESTAMP('2023-06-13 20:11:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','C_Order (PO) => M_Inventory',TO_TIMESTAMP('2023-06-13 20:11:59','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-13T17:11:59.334Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541745 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Order (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T17:12:49.104Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3542,0,541745,321,TO_TIMESTAMP('2023-06-13 20:12:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','N',TO_TIMESTAMP('2023-06-13 20:12:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: C_Order (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T17:14:30.577Z
UPDATE AD_Ref_Table SET WhereClause='m_inventory.M_Inventory_ID IN (SELECT m_inventory_id from m_delivery_planning     inner join c_order on m_delivery_planning.c_order_id = c_order.c_order_id and c_order.issotrx=''N''     inner join m_inventory on c_order.c_order_id = m_inventory.c_po_order_id where c_order.c_order_id= @C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 20:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541745
;

-- Reference: C_Order (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T17:28:46.743Z
UPDATE AD_Ref_Table SET WhereClause='M_Inventory_ID IN (SELECT m_inventory_id from m_delivery_planning     inner join c_order on m_delivery_planning.c_order_id = c_order.c_order_id and c_order.issotrx=''N''     inner join m_inventory on c_order.c_order_id = m_inventory.c_po_order_id where c_order.c_order_id= @C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 20:28:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541745
;

-- 2023-06-13T17:30:55.304Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,290,541745,540386,TO_TIMESTAMP('2023-06-13 20:30:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order (PO) => M_Invetory',TO_TIMESTAMP('2023-06-13 20:30:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: C_Order (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T17:33:24.725Z
UPDATE AD_Ref_Table SET WhereClause='m_invetory.M_Inventory_ID IN (SELECT m_inventory_id from m_delivery_planning     inner join c_order on m_delivery_planning.c_order_id = c_order.c_order_id and c_order.issotrx=''N''     inner join m_inventory on c_order.c_order_id = m_inventory.c_po_order_id where c_order.c_order_id= @C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 20:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541745
;

-- Reference: C_Order (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T17:33:29.836Z
UPDATE AD_Ref_Table SET WhereClause='m_inventory.M_Inventory_ID IN (SELECT m_inventory_id from m_delivery_planning     inner join c_order on m_delivery_planning.c_order_id = c_order.c_order_id and c_order.issotrx=''N''     inner join m_inventory on c_order.c_order_id = m_inventory.c_po_order_id where c_order.c_order_id= @C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 20:33:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541745
;

-- Reference: C_Order (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T17:42:39.645Z
UPDATE AD_Ref_Table SET WhereClause='M_Inventory_ID IN (SELECT m.m_inventory_id from m_delivery_planning     inner join c_order c on m_delivery_planning.c_order_id = c.c_order_id and c_order.issotrx=''N''     inner join m_inventory m on c_order.c_order_id = m.c_po_order_id where c.c_order_id=@C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 20:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541745
;

-- Reference: C_Order (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T17:45:34.501Z
UPDATE AD_Ref_Table SET WhereClause='M_Inventory_ID IN (SELECT m.m_inventory_id from m_delivery_planning d inner join c_order c on d.c_order_id = c.c_order_id and c_order.issotrx=''N''     inner join m_inventory m on c_order.c_order_id = m.c_po_order_id where c.c_order_id=@C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 20:45:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541745
;

-- Reference: C_Order (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T17:46:14.711Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-06-13 20:46:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541745
;

-- Reference: C_Order (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T17:48:14.055Z
UPDATE AD_Ref_Table SET WhereClause='M_Inventory_ID IN (SELECT m.m_inventory_id from m_delivery_planning d inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join m_inventory m on c.c_order_id = m.c_po_order_id where c.c_order_id=@C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 20:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541745
;

-- Reference: RelType C_Invoice_ID->M_InOut
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2023-06-13T17:51:12.402Z
UPDATE AD_Ref_Table SET AD_Window_ID=168,Updated=TO_TIMESTAMP('2023-06-13 20:51:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540057
;

-- Reference: RelType C_Invoice_ID->M_InOut
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2023-06-13T17:56:29.514Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-06-13 20:56:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540057
;

-- Name: M_InOut (PO) => M_Inventory
-- 2023-06-13T17:58:36.678Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541746,TO_TIMESTAMP('2023-06-13 20:58:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','M_InOut (PO) => M_Inventory',TO_TIMESTAMP('2023-06-13 20:58:36','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-13T17:58:36.684Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541746 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2023-06-13T17:58:51.914Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,337,540387,TO_TIMESTAMP('2023-06-13 20:58:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','M_InOut_(PO) => M_Inventory',TO_TIMESTAMP('2023-06-13 20:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: M_InOut (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T18:00:08.839Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3542,0,541746,321,TO_TIMESTAMP('2023-06-13 21:00:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','N',TO_TIMESTAMP('2023-06-13 21:00:08','YYYY-MM-DD HH24:MI:SS'),100,'M_Inventory_ID IN (SELECT m.m_inventory_id from m_delivery_planning d     inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join m_inventory m on c.c_order_id = m.c_po_order_id     inner join m_inout i on i.c_order_id = c.c_order_id     where i.m_inout_id=@M_InOut_ID/-1@)')
;

-- 2023-06-13T18:01:07.414Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541746,Updated=TO_TIMESTAMP('2023-06-13 21:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540387
;

-- Name: C_Invoice (PO) => M_Inventory
-- 2023-06-13T18:04:35.009Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541747,TO_TIMESTAMP('2023-06-13 21:04:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice (PO) => M_Inventory',TO_TIMESTAMP('2023-06-13 21:04:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-13T18:04:35.009Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541747 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice (PO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T18:05:08.921Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3542,0,541747,321,TO_TIMESTAMP('2023-06-13 21:05:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-13 21:05:08','YYYY-MM-DD HH24:MI:SS'),100,'M_Inventory_ID IN (SELECT m.m_inventory_id from m_delivery_planning d     inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join m_inventory m on c.c_order_id = m.c_po_order_id     inner join c_invoice i on i.c_order_id = c.c_order_id     where i.c_invoice_id=@C_Invoice_ID/-1@)')
;

-- 2023-06-13T18:06:24.514Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,336,540388,TO_TIMESTAMP('2023-06-13 21:06:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice (PO) => M_Inventory',TO_TIMESTAMP('2023-06-13 21:06:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-13T18:06:41.024Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541747,Updated=TO_TIMESTAMP('2023-06-13 21:06:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540388
;

-- 2023-06-13T18:07:25.877Z
UPDATE AD_RelationType SET EntityType='de.metas.order', Name='C_Order (PO) => M_Inventory',Updated=TO_TIMESTAMP('2023-06-13 21:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540386
;

-- Reference: C_Order (SO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T18:11:10.626Z
UPDATE AD_Ref_Table SET WhereClause='M_Inventory_ID IN (SELECT m.m_inventory_id from m_delivery_planning d inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join m_inventory m on c.c_order_id = m.c_po_order_id where c.link_order_id=@C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:11:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541744
;

-- Reference: C_Order (SO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T18:11:23.713Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-06-13 21:11:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541744
;

-- 2023-06-13T18:16:15.965Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,337,540389,TO_TIMESTAMP('2023-06-13 21:16:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_InOut (SO) => M_Inventory',TO_TIMESTAMP('2023-06-13 21:16:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-13T18:16:27.854Z
UPDATE AD_RelationType SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2023-06-13 21:16:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540389
;

-- Name: M_InOut (SO) => M_Inventory
-- 2023-06-13T18:16:57.424Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541748,TO_TIMESTAMP('2023-06-13 21:16:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','M_InOut (SO) => M_Inventory',TO_TIMESTAMP('2023-06-13 21:16:57','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-13T18:16:57.424Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541748 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_InOut (SO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T18:17:43.394Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3542,0,541748,321,TO_TIMESTAMP('2023-06-13 21:17:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','N',TO_TIMESTAMP('2023-06-13 21:17:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: M_InOut (SO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T18:17:58.190Z
UPDATE AD_Ref_Table SET WhereClause='M_Inventory_ID IN (SELECT m.m_inventory_id from m_delivery_planning d     inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join m_inventory m on c.c_order_id = m.c_po_order_id     inner join m_inout i on i.c_order_id = c.link_order_id     where i.m_inout_id=@M_InOut_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:17:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541748
;

-- 2023-06-13T18:18:22.414Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541748,Updated=TO_TIMESTAMP('2023-06-13 21:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540389
;

-- 2023-06-13T18:18:57.283Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,336,540390,TO_TIMESTAMP('2023-06-13 21:18:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice (SO) => M_Inventory',TO_TIMESTAMP('2023-06-13 21:18:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_Invoice (SO) => M_Inventory
-- 2023-06-13T18:19:24.223Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541749,TO_TIMESTAMP('2023-06-13 21:19:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice (SO) => M_Inventory',TO_TIMESTAMP('2023-06-13 21:19:24','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-13T18:19:24.223Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541749 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice (SO) => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T18:19:58.398Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3542,0,541749,321,TO_TIMESTAMP('2023-06-13 21:19:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-13 21:19:58','YYYY-MM-DD HH24:MI:SS'),100,'M_Inventory_ID IN (SELECT m.m_inventory_id from m_delivery_planning d     inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join m_inventory m on c.c_order_id = m.c_po_order_id     inner join c_invoice i on i.c_order_id = c.link_order_id     where i.c_invoice_id=@C_Invoice_ID/-1@)')
;

-- 2023-06-13T18:23:58.243Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541707,540391,TO_TIMESTAMP('2023-06-13 21:23:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','M_Delivery_Planning => C_Invoice_Candidate',TO_TIMESTAMP('2023-06-13 21:23:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: M_Delivery_Planning => C_Invoice_Candidate
-- 2023-06-13T18:24:44.466Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541750,TO_TIMESTAMP('2023-06-13 21:24:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','M_Delivery_Planning => C_Invoice_Candidate',TO_TIMESTAMP('2023-06-13 21:24:44','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-13T18:24:44.466Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541750 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:26:19.649Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,544906,0,541750,540270,TO_TIMESTAMP('2023-06-13 21:26:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-13 21:26:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:32:32.561Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i      inner join m_delivery_planning d on i.c_order_id=d.c_order_id      where  d.m_delivery_planning_id=@M_Delivery_Planning/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:32:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- 2023-06-13T18:32:54.153Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541750,Updated=TO_TIMESTAMP('2023-06-13 21:32:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540391
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:36:28.487Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i      inner join m_delivery_planning d on i.c_order_id=d.c_order_id      where  d.m_delivery_planning_id=@M_Delivery_Planning_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:40:51.409Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id and i.issotrx=''N''     where  d.m_delivery_planning_id=@M_Delivery_Planning/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:41:05.943Z
UPDATE AD_Ref_Table SET AD_Window_ID=540983,Updated=TO_TIMESTAMP('2023-06-13 21:41:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:41:55.393Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id    where  d.m_delivery_planning_id=@M_Delivery_Planning/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:41:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:42:32.374Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-06-13 21:42:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:47:36.128Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id and i.issotrx=''N''     where  d.m_delivery_planning_id=@M_Delivery_Planning/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:47:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:48:07.803Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id     where  d.m_delivery_planning_id=@M_Delivery_Planning/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:48:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:54:12.106Z
UPDATE AD_Ref_Table SET WhereClause='SELECT i.c_invoice_candidate_id from c_invoice_candidate i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id     where  d.m_delivery_planning_id=@M_Delivery_Planning/-1@',Updated=TO_TIMESTAMP('2023-06-13 21:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:54:57.593Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id     where  d.m_delivery_planning_id=@M_Delivery_Planning/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:54:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T18:59:59.982Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id     where  d.m_delivery_planning_id=@M_Delivery_Planning_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 21:59:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T19:01:39.222Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id and i.issotrx=''N''     where  d.m_delivery_planning_id=@M_Delivery_Planning_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 22:01:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T19:02:07.706Z
UPDATE AD_Ref_Table SET AD_Window_ID=540983,Updated=TO_TIMESTAMP('2023-06-13 22:02:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- Name: M_Delivery_Planning => C_Invoice_Candidate (PO)
-- 2023-06-13T19:02:42.562Z
UPDATE AD_Reference SET Name='M_Delivery_Planning => C_Invoice_Candidate (PO)',Updated=TO_TIMESTAMP('2023-06-13 22:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541750
;

-- 2023-06-13T19:02:42.562Z
UPDATE AD_Reference_Trl trl SET Name='M_Delivery_Planning => C_Invoice_Candidate (PO)' WHERE AD_Reference_ID=541750 AND AD_Language='en_US'
;

-- 2023-06-13T19:03:06.491Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2023-06-13 22:03:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540391
;

-- 2023-06-13T19:03:13.487Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541750,Updated=TO_TIMESTAMP('2023-06-13 22:03:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540391
;

-- 2023-06-13T19:03:22.974Z
UPDATE AD_RelationType SET Name='M_Delivery_Planning => C_Invoice_Candidate (PO)',Updated=TO_TIMESTAMP('2023-06-13 22:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540391
;

-- 2023-06-13T19:04:32.052Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541707,540392,TO_TIMESTAMP('2023-06-13 22:04:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_Delivery_Planning => C_Invoice_Candidate (SO)',TO_TIMESTAMP('2023-06-13 22:04:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: M_Delivery_Planning => C_Invoice_Candidate (SO)
-- 2023-06-13T19:05:34.183Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541752,TO_TIMESTAMP('2023-06-13 22:05:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','M_Delivery_Planning => C_Invoice_Candidate (SO)',TO_TIMESTAMP('2023-06-13 22:05:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-13T19:05:34.197Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541752 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate (SO)
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T19:06:27.349Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,544906,0,541752,540270,TO_TIMESTAMP('2023-06-13 22:06:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-13 22:06:27','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_Candidate_ID IN (SELECT i.c_invoice_candidate_id from c_invoice_candidate i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id and i.issotrx=''Y''     where  d.m_delivery_planning_id=@M_Delivery_Planning_ID/-1@)')
;

-- 2023-06-13T19:07:33.091Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541707,540393,TO_TIMESTAMP('2023-06-13 22:07:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','M_Delivery_Planning => M_Inventory',TO_TIMESTAMP('2023-06-13 22:07:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: M_Delivery_Planning => M_Inventory
-- 2023-06-13T19:08:03.144Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541753,TO_TIMESTAMP('2023-06-13 22:08:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','M_Delivery_Planning => M_Inventory',TO_TIMESTAMP('2023-06-13 22:08:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-13T19:08:03.144Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541753 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Delivery_Planning => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T19:08:27.312Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3542,0,541753,321,TO_TIMESTAMP('2023-06-13 22:08:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','N',TO_TIMESTAMP('2023-06-13 22:08:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: M_Delivery_Planning => M_Inventory
-- Table: M_Inventory
-- Key: M_Inventory.M_Inventory_ID
-- 2023-06-13T19:12:19.737Z
UPDATE AD_Ref_Table SET WhereClause='M_Inventory_ID IN (SELECT i.m_inventory_id from m_inventory i     inner join m_delivery_planning d on i.c_po_order_id=d.c_order_id     where  d.m_delivery_planning_id=@M_Delivery_Planning_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-13 22:12:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541753
;

-- 2023-06-13T19:13:22.592Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541753,Updated=TO_TIMESTAMP('2023-06-13 22:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540393
;

-- Reference: M_Delivery_Planning => C_Invoice_Candidate (SO)
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-06-13T20:22:22.969Z
UPDATE AD_Ref_Table SET AD_Window_ID=540092,Updated=TO_TIMESTAMP('2023-06-13 23:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541752
;

-- 2023-06-13T20:34:18.983Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541752,Updated=TO_TIMESTAMP('2023-06-13 23:34:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540392
;

-- 2023-06-13T21:13:12.783Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541749,Updated=TO_TIMESTAMP('2023-06-14 00:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540390
;

