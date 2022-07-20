-- 2022-07-19T17:28:14.648Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541604,TO_TIMESTAMP('2022-07-19 18:28:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order_SO_Counter_For_C_Order_PO',TO_TIMESTAMP('2022-07-19 18:28:13','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-07-19T17:28:14.744Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541604 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-07-19T17:33:18.617Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541604,259,181,TO_TIMESTAMP('2022-07-19 18:33:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-07-19 18:33:18','YYYY-MM-DD HH24:MI:SS'),100,'C_Order.Ref_Order_ID =@C_Order_ID/-1@')
;

-- 2022-07-19T17:34:23.195Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540666,541604,540348,TO_TIMESTAMP('2022-07-19 18:34:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order(SO) -> C_Order(PO - Counter)',TO_TIMESTAMP('2022-07-19 18:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T17:36:33.871Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Order(PO)_Counter_For_C_Order(SO)',Updated=TO_TIMESTAMP('2022-07-19 18:36:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541604
;

-- 2022-07-19T17:37:36.148Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Order_Counter',Updated=TO_TIMESTAMP('2022-07-19 18:37:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541604
;

-- 2022-07-19T17:39:00.596Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=290,Updated=TO_TIMESTAMP('2022-07-19 18:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540348
;

-- 2022-07-19T17:39:40.680Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='C_Order_Counter',Updated=TO_TIMESTAMP('2022-07-19 18:39:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541604
;

-- 2022-07-19T17:39:45.475Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='C_Order_Counter',Updated=TO_TIMESTAMP('2022-07-19 18:39:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541604
;

-- 2022-07-19T17:39:48.959Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='C_Order_Counter',Updated=TO_TIMESTAMP('2022-07-19 18:39:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541604
;

-- 2022-07-19T17:39:54.407Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='C_Order_Counter',Updated=TO_TIMESTAMP('2022-07-19 18:39:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541604
;

-- 2022-07-19T17:40:32.351Z
-- URL zum Konzept
UPDATE AD_RelationType SET Name='C_Order -> Counter',Updated=TO_TIMESTAMP('2022-07-19 18:40:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540348
;

-- 2022-07-19T17:44:07.645Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540666, Name='C_Order (SO) -> C_Order (PO) Counter',Updated=TO_TIMESTAMP('2022-07-19 18:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540348
;

-- 2022-07-19T17:44:45.064Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Order (PO) Counter',Updated=TO_TIMESTAMP('2022-07-19 18:44:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541604
;

-- 2022-07-19T17:44:54.048Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='C_Order (PO) Counter',Updated=TO_TIMESTAMP('2022-07-19 18:44:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541604
;

-- 2022-07-19T17:44:57.568Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='C_Order (PO) Counter',Updated=TO_TIMESTAMP('2022-07-19 18:44:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541604
;

-- 2022-07-19T17:45:00.725Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='C_Order (PO) Counter',Updated=TO_TIMESTAMP('2022-07-19 18:45:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541604
;

-- 2022-07-19T17:45:05.096Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='C_Order (PO) Counter',Updated=TO_TIMESTAMP('2022-07-19 18:45:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541604
;

-- 2022-07-19T17:46:04.265Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='C_Order.Ref_Order_ID =@C_Order_ID/-1@ AND C_Order.IsSOTrx = ''N''',Updated=TO_TIMESTAMP('2022-07-19 18:46:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541604
;

-- 2022-07-19T17:47:34.519Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541605,TO_TIMESTAMP('2022-07-19 18:47:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order (SO) Counter',TO_TIMESTAMP('2022-07-19 18:47:33','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-07-19T17:47:34.622Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541605 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-07-19T17:48:30.365Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,2161,0,541605,259,143,TO_TIMESTAMP('2022-07-19 18:48:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-07-19 18:48:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T17:48:57.773Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='C_Order.Ref_Order_ID =@C_Order_ID/-1@ AND C_Order.IsSOTrx = ''Y''',Updated=TO_TIMESTAMP('2022-07-19 18:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541605
;

-- 2022-07-19T17:51:36.802Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540676,541605,540349,TO_TIMESTAMP('2022-07-19 18:51:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order (PO) -> C_Order (SO) Counter',TO_TIMESTAMP('2022-07-19 18:51:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T18:24:18.480Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541606,TO_TIMESTAMP('2022-07-19 19:24:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_InOut (SO) Counter',TO_TIMESTAMP('2022-07-19 19:24:17','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-07-19T18:24:18.583Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541606 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-07-19T18:25:40.661Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3521,0,541606,319,TO_TIMESTAMP('2022-07-19 19:25:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-07-19 19:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T18:26:51.980Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=169, WhereClause='M_InOut.Ref_InOut_ID =@M_InOut_ID/-1@ AND M_InOut.IsSOTrx = ''Y''',Updated=TO_TIMESTAMP('2022-07-19 19:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541606
;

-- 2022-07-19T18:28:19.801Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540672,541606,540350,TO_TIMESTAMP('2022-07-19 19:28:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_InOut (PO) -> M_InOut (SO) Counter',TO_TIMESTAMP('2022-07-19 19:28:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T18:45:33.405Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541607,TO_TIMESTAMP('2022-07-19 19:45:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_InOut (PO) Counter',TO_TIMESTAMP('2022-07-19 19:45:32','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-07-19T18:45:33.508Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541607 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-07-19T18:46:29.204Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3521,0,541607,319,TO_TIMESTAMP('2022-07-19 19:46:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-07-19 19:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T18:47:22.399Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=184, WhereClause='M_InOut.Ref_InOut_ID =@M_InOut_ID/-1@ AND M_InOut.IsSOTrx = ''N''',Updated=TO_TIMESTAMP('2022-07-19 19:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541607
;

-- 2022-07-19T18:48:18.320Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540670,541607,540351,TO_TIMESTAMP('2022-07-19 19:48:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_InOut (SO) -> M_InOut (PO) Counter',TO_TIMESTAMP('2022-07-19 19:48:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T18:53:26.408Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541608,TO_TIMESTAMP('2022-07-19 19:53:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Invoice (PO) Counter',TO_TIMESTAMP('2022-07-19 19:53:25','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-07-19T18:53:26.513Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541608 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-07-19T18:55:53.758Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3484,0,541608,318,183,TO_TIMESTAMP('2022-07-19 19:55:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-07-19 19:55:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T18:56:53.697Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='C_Invoice.Ref_Invoice_ID =@C_Invoice_ID/-1@ AND C_Invoice.IsSOTrx = ''N''',Updated=TO_TIMESTAMP('2022-07-19 19:56:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541608
;

-- 2022-07-19T18:57:34.690Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540678,541608,540352,TO_TIMESTAMP('2022-07-19 19:57:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Invoice (SO) -> C_Invoice (PO) Counter',TO_TIMESTAMP('2022-07-19 19:57:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T20:24:35.690Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541609,TO_TIMESTAMP('2022-07-19 21:24:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Invoice (SO) Counter',TO_TIMESTAMP('2022-07-19 21:24:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-07-19T20:24:35.797Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541609 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-07-19T20:26:30.674Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3484,0,541609,318,167,TO_TIMESTAMP('2022-07-19 21:26:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-07-19 21:26:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-19T20:27:03.346Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='C_Invoice.Ref_Invoice_ID =@C_Invoice_ID/-1@ AND C_Invoice.IsSOTrx = ''Y''',Updated=TO_TIMESTAMP('2022-07-19 21:27:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541609
;

-- 2022-07-19T20:30:14.322Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540680,541608,540353,TO_TIMESTAMP('2022-07-19 21:30:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Invoice (PO) -> C_Invoice (SO) Counter',TO_TIMESTAMP('2022-07-19 21:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

