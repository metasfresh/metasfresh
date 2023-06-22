-- Name: C_Invoice (PO) => M_Delivery_Planning
-- 2023-06-22T14:38:23.864Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541759,TO_TIMESTAMP('2023-06-22 17:38:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice (PO) => M_Delivery_Planning',TO_TIMESTAMP('2023-06-22 17:38:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T14:38:23.874Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541759 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice (PO) => M_Delivery_Planning
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-06-22T14:39:34.945Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,584986,0,541759,542259,TO_TIMESTAMP('2023-06-22 17:39:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 17:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: C_Invoice (PO) => M_Delivery_Planning
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-06-22T14:42:08.100Z
UPDATE AD_Ref_Table SET WhereClause='M_Delivery_Planning_ID IN (SELECT d.m_delivery_planning_id from m_delivery_planning d     inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join c_invoice i on i.c_order_id = c.c_order_id     where i.c_invoice_id=@C_Invoice_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-22 17:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541759
;

-- Name: C_Invoice (SO) => M_Delivery_Planning
-- 2023-06-22T14:42:57.914Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541760,TO_TIMESTAMP('2023-06-22 17:42:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice (SO) => M_Delivery_Planning',TO_TIMESTAMP('2023-06-22 17:42:57','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T14:42:57.914Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541760 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice (SO) => M_Delivery_Planning
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-06-22T14:43:30.339Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,584986,0,541760,542259,TO_TIMESTAMP('2023-06-22 17:43:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 17:43:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: C_Invoice (SO) => M_Delivery_Planning
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-06-22T14:45:24.118Z
UPDATE AD_Ref_Table SET WhereClause='M_Delivery_Planning_ID IN (SELECT d.m_delivery_planning_id from m_delivery_planning d     inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''Y''     inner join c_invoice i on i.c_order_id = c.c_order_id     where i.c_invoice_id=@C_Invoice_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-22 17:45:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541760
;

-- Name: C_Invoice_Candidate (PO) => M_Delivery_Planning
-- 2023-06-22T14:49:29.168Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541761,TO_TIMESTAMP('2023-06-22 17:49:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_Candidate (PO) => M_Delivery_Planning',TO_TIMESTAMP('2023-06-22 17:49:28','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T14:49:29.168Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541761 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate (PO) => M_Delivery_Planning
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-06-22T14:49:59.563Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,584986,0,541761,542259,TO_TIMESTAMP('2023-06-22 17:49:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 17:49:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: C_Invoice_Candidate (PO) => M_Delivery_Planning
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-06-22T14:51:35.011Z
UPDATE AD_Ref_Table SET WhereClause='M_Delivery_Planning_ID IN (SELECT d.m_delivery_planning_id from m_delivery_planning d     inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join c_invoice_candidate i on i.c_order_id = c.c_order_id     where i.c_invoice_id=@C_Invoice_ID/-1@);',Updated=TO_TIMESTAMP('2023-06-22 17:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541761
;

-- Name: C_Invoice_Candidate (SO) => M_Delivery_Planning
-- 2023-06-22T14:51:49.160Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541762,TO_TIMESTAMP('2023-06-22 17:51:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_Candidate (SO) => M_Delivery_Planning',TO_TIMESTAMP('2023-06-22 17:51:48','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T14:51:49.162Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541762 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate (SO) => M_Delivery_Planning
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-06-22T14:53:49.273Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,584986,0,541762,542259,TO_TIMESTAMP('2023-06-22 17:53:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 17:53:49','YYYY-MM-DD HH24:MI:SS'),100,'M_Delivery_Planning_ID IN (SELECT d.m_delivery_planning_id from m_delivery_planning d     inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''Y''     inner join c_invoice_candidate i on i.c_order_id = c.c_order_id     where i.c_invoice_candidate_id=@C_Invoice_Candidate_ID/-1@)')
;

-- Reference: C_Invoice_Candidate (PO) => M_Delivery_Planning
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-06-22T14:54:00.353Z
UPDATE AD_Ref_Table SET WhereClause='M_Delivery_Planning_ID IN (SELECT d.m_delivery_planning_id from m_delivery_planning d     inner join c_order c on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join c_invoice_candidate i on i.c_order_id = c.c_order_id     where i.c_invoice_id=@C_Invoice_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-22 17:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541761
;

-- Name: C_Invoice_Candidate (PO) => C_Order (PO)
-- 2023-06-22T14:55:17.849Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541763,TO_TIMESTAMP('2023-06-22 17:55:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_Candidate (PO) => C_Order (PO)',TO_TIMESTAMP('2023-06-22 17:55:17','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T14:55:17.849Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541763 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate (PO) => C_Order (PO)
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-06-22T14:58:43.033Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541763,259,181,TO_TIMESTAMP('2023-06-22 17:58:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 17:58:43','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_ID IN (SELECT c.c_order_id from c_order c     inner join c_invoice_candidate i on i.c_order_id = c.c_order_id and c.issotrx=''N''     where i.c_invoice_candidate_id=@C_Invoice_Candidate_ID/-1@)')
;

-- Name: C_Invoice_Candidate (SO) => C_Order (SO)
-- 2023-06-22T14:59:09.794Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541764,TO_TIMESTAMP('2023-06-22 17:59:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_Candidate (SO) => C_Order (SO)',TO_TIMESTAMP('2023-06-22 17:59:09','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T14:59:09.794Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541764 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate (SO) => C_Order (SO)
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-06-22T14:59:51.745Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541764,259,TO_TIMESTAMP('2023-06-22 17:59:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 17:59:51','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_ID IN (SELECT c.c_order_id from c_order c     inner join c_invoice_candidate i on i.c_order_id = c.c_order_id and c.issotrx=''Y''     where i.c_invoice_candidate_id=@C_Invoice_Candidate_ID/-1@)')
;

-- Reference: C_Invoice_Candidate (SO) => C_Order (SO)
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-06-22T14:59:57.804Z
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2023-06-22 17:59:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541764
;

-- Name: C_Invoice_Candidate (PO) => M_InOut (PO)
-- 2023-06-22T15:01:27.137Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541765,TO_TIMESTAMP('2023-06-22 18:01:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_Candidate (PO) => M_InOut (PO)',TO_TIMESTAMP('2023-06-22 18:01:26','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T15:01:27.137Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541765 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate (PO) => M_InOut (PO)
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2023-06-22T15:04:18.396Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,541765,319,184,TO_TIMESTAMP('2023-06-22 18:04:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 18:04:18','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut_ID IN (SELECT m.m_inout_id from m_inout m     inner join c_order c on m.c_order_id = c.c_order_id and c.issotrx=''N''     inner join c_invoice_candidate i on i.c_order_id = c.c_order_id     where i.c_invoice_candidate_id=@C_Invoice_Candidate_ID/-1@)')
;

-- Name: C_Invoice_Candidate (SO) => M_InOut (SO)
-- 2023-06-22T15:04:56.991Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541766,TO_TIMESTAMP('2023-06-22 18:04:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_Candidate (SO) => M_InOut (SO)',TO_TIMESTAMP('2023-06-22 18:04:56','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T15:04:56.993Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541766 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate (SO) => M_InOut (SO)
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2023-06-22T15:05:56.953Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,541766,319,169,TO_TIMESTAMP('2023-06-22 18:05:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 18:05:56','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut_ID IN (SELECT m.m_inout_id from m_inout m     inner join c_order c on m.c_order_id = c.c_order_id and c.issotrx=''Y''     inner join c_invoice_candidate i on i.c_order_id = c.c_order_id     where i.c_invoice_candidate_id=@C_Invoice_Candidate_ID/-1@)')
;

-- Name: M_Delivery_Planning => C_Invoice (PO)
-- 2023-06-22T15:07:49.805Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541767,TO_TIMESTAMP('2023-06-22 18:07:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','M_Delivery_Planning => C_Invoice (PO)',TO_TIMESTAMP('2023-06-22 18:07:49','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T15:07:49.805Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541767 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Delivery_Planning => C_Invoice (PO)
-- Table: C_Invoice
-- Key: C_Invoice.C_Invoice_ID
-- 2023-06-22T15:10:24.937Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,541767,318,167,TO_TIMESTAMP('2023-06-22 18:10:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 18:10:24','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_ID IN (SELECT i.c_invoice_id from c_invoice i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id and i.issotrx=''N''     where  d.m_delivery_planning_id=@M_Delivery_Planning_ID/-1@)')
;

-- Name: M_Delivery_Planning => C_Invoice (SO)
-- 2023-06-22T15:10:46.091Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541768,TO_TIMESTAMP('2023-06-22 18:10:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','M_Delivery_Planning => C_Invoice (SO)',TO_TIMESTAMP('2023-06-22 18:10:45','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T15:10:46.091Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541768 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Delivery_Planning => C_Invoice (SO)
-- Table: C_Invoice
-- Key: C_Invoice.C_Invoice_ID
-- 2023-06-22T15:11:36.061Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,541768,318,167,TO_TIMESTAMP('2023-06-22 18:11:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N',TO_TIMESTAMP('2023-06-22 18:11:36','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_ID IN (SELECT i.c_invoice_id from c_invoice i     inner join m_delivery_planning d on i.c_order_id=d.c_order_id and i.issotrx=''Y''     where  d.m_delivery_planning_id=@M_Delivery_Planning_ID/-1@)')
;

-- Reference: M_Delivery_Planning => C_Invoice (PO)
-- Table: C_Invoice
-- Key: C_Invoice.C_Invoice_ID
-- 2023-06-22T15:11:52.203Z
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2023-06-22 18:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541767
;

-- Name: M_Inventory => C_Order (PO)
-- 2023-06-22T15:17:45.986Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541769,TO_TIMESTAMP('2023-06-22 18:17:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','M_Inventory => C_Order (PO)',TO_TIMESTAMP('2023-06-22 18:17:45','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-06-22T15:17:45.986Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541769 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Inventory => C_Order (PO)
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-06-22T15:18:26.309Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541769,259,181,TO_TIMESTAMP('2023-06-22 18:18:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','N',TO_TIMESTAMP('2023-06-22 18:18:26','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_ID IN (SELECT c.c_order_id from c_order c     inner join m_delivery_planning d on d.c_order_id = c.c_order_id and c.issotrx=''N''     inner join m_inventory m on c.c_order_id = m.c_po_order_id     where m.m_inventory_id=@M_Inventory_ID/-1@)')
;

