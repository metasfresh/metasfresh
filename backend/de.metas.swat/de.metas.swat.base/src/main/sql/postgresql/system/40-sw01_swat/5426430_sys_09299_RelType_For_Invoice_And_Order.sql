-- 08.09.2015 13:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540122,TO_TIMESTAMP('2015-09-08 13:17:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Order -> Invoice (SO)',TO_TIMESTAMP('2015-09-08 13:17:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2015 13:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540123,TO_TIMESTAMP('2015-09-08 13:18:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Order -> Invoice (PO)',TO_TIMESTAMP('2015-09-08 13:18:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2015 13:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540124,TO_TIMESTAMP('2015-09-08 13:18:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Invoice -> Order (SO)',TO_TIMESTAMP('2015-09-08 13:18:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2015 13:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540125,TO_TIMESTAMP('2015-09-08 13:18:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Invoice -> Order (PO)',TO_TIMESTAMP('2015-09-08 13:18:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2015 13:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540565,TO_TIMESTAMP('2015-09-08 13:33:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType Order -> Invoice SO',TO_TIMESTAMP('2015-09-08 13:33:50','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 08.09.2015 13:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540565 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 08.09.2015 13:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540565,318,TO_TIMESTAMP('2015-09-08 13:34:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-08 13:34:17','YYYY-MM-DD HH24:MI:SS'),100,'
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	WHERE ol.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = ''Y''
)')
;

-- 08.09.2015 13:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	WHERE ol.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = ol.IsSOTrx
		AND i.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2015-09-08 13:34:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 13:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540100, AD_Reference_Target_ID=53334,Updated=TO_TIMESTAMP('2015-09-08 13:56:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 13:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540565,Updated=TO_TIMESTAMP('2015-09-08 13:56:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 13:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target='Invoice',Updated=TO_TIMESTAMP('2015-09-08 13:56:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 13:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540566,TO_TIMESTAMP('2015-09-08 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Order -> Invoice PO',TO_TIMESTAMP('2015-09-08 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 08.09.2015 13:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540566 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 08.09.2015 13:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540566,259,TO_TIMESTAMP('2015-09-08 13:57:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-08 13:57:57','YYYY-MM-DD HH24:MI:SS'),100,NULL)
;

-- 08.09.2015 13:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='RelType C_Order -> Invoice SO',Updated=TO_TIMESTAMP('2015-09-08 13:58:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 13:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540565
;

-- 08.09.2015 13:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	WHERE ol.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = ol.IsSOTrx
		AND i.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2015-09-08 13:58:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 13:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2015-09-08 13:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 13:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2015-09-08 13:58:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 13:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Role_Target,Updated,UpdatedBy) VALUES (0,0,540100,540566,540126,TO_TIMESTAMP('2015-09-08 13:59:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Order -> Invoice (PO)','PO_Invoice',TO_TIMESTAMP('2015-09-08 13:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2015 13:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540250,Updated=TO_TIMESTAMP('2015-09-08 13:59:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 08.09.2015 14:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540123
;

-- 08.09.2015 14:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540101,Updated=TO_TIMESTAMP('2015-09-08 14:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 14:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=336,Updated=TO_TIMESTAMP('2015-09-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 08.09.2015 14:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540567,TO_TIMESTAMP('2015-09-08 14:10:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Invoice -> Order (SO)',TO_TIMESTAMP('2015-09-08 14:10:26','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 08.09.2015 14:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540567 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 08.09.2015 14:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540567,259,TO_TIMESTAMP('2015-09-08 14:10:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-08 14:10:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2015 14:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=3484, AD_Table_ID=318,Updated=TO_TIMESTAMP('2015-09-08 14:11:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 14:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2015-09-08 14:14:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 14:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540568,TO_TIMESTAMP('2015-09-08 14:14:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Invoice -> Order (PO)',TO_TIMESTAMP('2015-09-08 14:14:35','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 08.09.2015 14:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540568 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 08.09.2015 14:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540568,259,TO_TIMESTAMP('2015-09-08 14:14:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-08 14:14:57','YYYY-MM-DD HH24:MI:SS'),100,'

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
)')
;

-- 08.09.2015 14:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540567,Updated=TO_TIMESTAMP('2015-09-08 14:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 14:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540568, Role_Target='PurchaseOrder',Updated=TO_TIMESTAMP('2015-09-08 14:16:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 08.09.2015 14:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target='Order',Updated=TO_TIMESTAMP('2015-09-08 14:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 14:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2015-09-08 14:17:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 14:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540567,Updated=TO_TIMESTAMP('2015-09-08 14:17:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 14:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2015-09-08 14:18:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 14:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Async_Batch SET Processed='Y',Updated=TO_TIMESTAMP('2015-09-08 14:26:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188280 WHERE C_Async_Batch_ID=1000316
;

-- 08.09.2015 14:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 14:27:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=50001
;

-- 08.09.2015 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540565,Updated=TO_TIMESTAMP('2015-09-08 14:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 14:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o IN o.C_Order_ID = ol.C_Order_ID
	WHERE olC_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = olIsSOTrx
		AND i.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2015-09-08 14:31:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 14:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o IN o.C_Order_ID = ol.C_Order_ID
	WHERE olC_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2015-09-08 14:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 14:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2015-09-08 14:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 14:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540567,Updated=TO_TIMESTAMP('2015-09-08 14:38:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 14:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	WHERE ol.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2015-09-08 14:39:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 14:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
	WHERE ol.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2015-09-08 14:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 14:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2015-09-08 14:39:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 14:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND i.C_Invoice_ID = C_Invoice.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-08 14:40:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 14:41:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 14:41:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND i.C_Invoice_ID = C_Invoice.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-08 14:41:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2015-09-08 14:43:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2015-09-08 14:43:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2015-09-08 14:43:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2015-09-08 14:43:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 14:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 14:45:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 14:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND i.C_Invoice_ID = C_Invoice.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-08 14:46:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 14:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 14:46:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 14:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND i.C_Invoice_ID = C_Invoice.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-08 14:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 15:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540566,Updated=TO_TIMESTAMP('2015-09-08 15:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 15:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=NULL, IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 15:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=50001
;

-- 08.09.2015 15:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2015-09-08 15:20:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=50001
;

-- 08.09.2015 15:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 15:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=50001
;

-- 08.09.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND i.C_Invoice_ID = C_Invoice.C_Invoice_ID
		
)',Updated=TO_TIMESTAMP('2015-09-08 15:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 15:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND i.C_Invoice_ID = C_Invoice.C_Invoice_ID
		
)',Updated=TO_TIMESTAMP('2015-09-08 15:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 15:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 15:22:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 15:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 15:22:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 15:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2015-09-08 15:27:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 15:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2015-09-08 15:27:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 08.09.2015 15:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2015-09-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 15:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2015-09-08 15:28:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 16:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540565,Updated=TO_TIMESTAMP('2015-09-08 16:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 16:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target=NULL,Updated=TO_TIMESTAMP('2015-09-08 16:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 16:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target=NULL,Updated=TO_TIMESTAMP('2015-09-08 16:04:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 16:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target=NULL,Updated=TO_TIMESTAMP('2015-09-08 16:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 08.09.2015 16:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target=NULL,Updated=TO_TIMESTAMP('2015-09-08 16:04:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 08.09.2015 16:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540569,TO_TIMESTAMP('2015-09-08 16:07:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Invoice PO',TO_TIMESTAMP('2015-09-08 16:07:55','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 08.09.2015 16:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540569 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 08.09.2015 16:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,Updated,UpdatedBy,WhereClause) VALUES (0,3492,3484,0,540569,318,TO_TIMESTAMP('2015-09-08 16:08:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','DocumentNo',TO_TIMESTAMP('2015-09-08 16:08:55','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''N''')
;

-- 08.09.2015 16:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540569,Updated=TO_TIMESTAMP('2015-09-08 16:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 08.09.2015 16:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	INNER JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	INNER JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	INNER JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 16:41:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 16:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT DISTINCT o.C_Order_ID
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 16:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 16:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 16:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 16:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT DISTINCT o.C_Order_ID
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 16:42:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 16:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT DISTINCT o.C_Order_ID
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 16:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 16:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 16:43:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 16:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''N''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 16:43:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 08.09.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=53334, AD_Reference_Target_ID=53333, IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 17:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=50001
;

-- 08.09.2015 17:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 17:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=50001
;
-- 08.09.2015 17:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540570,TO_TIMESTAMP('2015-09-08 17:14:48','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','de.metas.swat','Y','N','C_Order SO ',TO_TIMESTAMP('2015-09-08 17:14:48','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 08.09.2015 17:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540570 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;



-- 08.09.2015 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Order SO DRAFT INCLUDED',Updated=TO_TIMESTAMP('2015-09-08 17:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540570
;

-- 08.09.2015 17:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2169,2161,0,540570,259,TO_TIMESTAMP('2015-09-08 17:17:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-08 17:17:02','YYYY-MM-DD HH24:MI:SS'),100,'ISSOTrx = ''Y''')
;

-- 08.09.2015 17:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='documentNo',Updated=TO_TIMESTAMP('2015-09-08 17:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 17:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540570,Updated=TO_TIMESTAMP('2015-09-08 17:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 17:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540571,TO_TIMESTAMP('2015-09-08 17:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Ausgangsrechnung','de.metas.swat','Y','N','C_Invoice SO DRAFT INCLUDED',TO_TIMESTAMP('2015-09-08 17:49:56','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 08.09.2015 17:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540571 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 08.09.2015 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3484,0,540571,318,TO_TIMESTAMP('2015-09-08 17:50:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-08 17:50:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2015 17:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

C_Invoice.IsSOTrx=''Y'' 
AND
EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	WHERE C_Invoice.C_Invoice_ID = @C_Invoice_ID@
		AND C_Invoice.IsSOTrx = o.IsSOTrx
		AND C_Invoice.IsSOTrx = ''Y''
		AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-08 17:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 17:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540571,Updated=TO_TIMESTAMP('2015-09-08 17:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 17:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 17:56:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 08.09.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 17:57:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 17:57:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 08.09.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 17:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 17:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
C_Order.IsSOTrx=''Y'' 
AND
EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	WHERE i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND o.C_Order_ID = C_Order.C_Order_ID
)',Updated=TO_TIMESTAMP('2015-09-08 17:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 18:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 18:00:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 18:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 18:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 18:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 18:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='


EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID
	WHERE 
		C_Order.C_Order_ID = ol.C_Order_ID
		AND C_Order.C_Order_ID = @C_Order_ID@
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)',Updated=TO_TIMESTAMP('2015-09-08 18:10:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		C_Order.C_Order_ID = ol.C_Order_ID
		AND i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)',Updated=TO_TIMESTAMP('2015-09-08 18:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='


EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		C_Order.C_Order_ID = il.C_Order_ID
		AND i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)
',Updated=TO_TIMESTAMP('2015-09-08 18:21:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 18:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 18:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 18:24:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 18:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 18:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 18:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 18:29:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 18:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 18:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		(
		C_Order.C_Order_ID = ol.C_Order_ID
		OR
		C_Order.C_Order_ID = il.C_Order_ID
		)
		
		AND 
		(
		i.C_Invoice_ID = @C_Invoice_ID@
		OR
		C_Order.C_Order_ID = @C_Order_ID@
		)
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)
',Updated=TO_TIMESTAMP('2015-09-08 18:34:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		(
		C_Order.C_Order_ID = ol.C_Order_ID
		OR
		C_Order.C_Order_ID = il.C_Order_ID
		)
		
		AND 
		(
		
		
		C_Order.C_Order_ID = @C_Order_ID@
		)
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)
',Updated=TO_TIMESTAMP('2015-09-08 18:42:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		(
		C_Order.C_Order_ID = ol.C_Order_ID
		OR
		C_Order.C_Order_ID = il.C_Order_ID
		)
		
		AND 
		
		C_Order.C_Order_ID = @C_Order_ID@
		
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)
',Updated=TO_TIMESTAMP('2015-09-08 18:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		
		C_Order.C_Order_ID = ol.C_Order_ID
		AND 
		i.C_Invoice_ID = @C_Invoice_ID@
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)
',Updated=TO_TIMESTAMP('2015-09-08 18:48:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2015-09-08 18:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 18:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		
		C_Order.C_Order_ID = ol.C_Order_ID
		AND 
		(
		i.C_Invoice_ID = @C_Invoice_ID@
		OR
		C_Order.C_Order_ID = @C_Order_ID@
		)
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)
',Updated=TO_TIMESTAMP('2015-09-08 18:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 18:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

C_Invoice.IsSOTrx=''Y'' 
AND
EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN 
	WHERE 
	(C_Invoice.C_Invoice_ID = @C_Invoice_ID@
	OR
	o.C_Order_ID = @C_Order_ID@
	)
		AND C_Invoice.IsSOTrx = o.IsSOTrx
		AND C_Invoice.IsSOTrx = ''Y''
		AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)

',Updated=TO_TIMESTAMP('2015-09-08 18:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 18:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

C_Invoice.IsSOTrx=''Y'' 
AND
EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID

	WHERE 
	(C_Invoice.C_Invoice_ID = @C_Invoice_ID@
	OR
	o.C_Order_ID = @C_Order_ID@
	)
		AND C_Invoice.IsSOTrx = o.IsSOTrx
		AND C_Invoice.IsSOTrx = ''Y''
		AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)
',Updated=TO_TIMESTAMP('2015-09-08 18:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 18:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

C_Invoice.IsSOTrx=''Y'' 
AND
EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID

	WHERE 
	(C_Invoice.C_Invoice_ID = @C_Invoice_ID/-1@
	OR
	o.C_Order_ID = @C_Order_ID/-1@
	)
		AND C_Invoice.IsSOTrx = o.IsSOTrx
		AND C_Invoice.IsSOTrx = ''Y''
		AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)
',Updated=TO_TIMESTAMP('2015-09-08 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 18:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		
		C_Order.C_Order_ID = ol.C_Order_ID
		AND 
		(
		i.C_Invoice_ID = @C_Invoice_ID/-1@
		OR
		C_Order.C_Order_ID = @C_Order_ID/-1@
		)
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)',Updated=TO_TIMESTAMP('2015-09-08 18:55:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 08.09.2015 19:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 19:07:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 19:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y', IsDirected='N',Updated=TO_TIMESTAMP('2015-09-08 19:09:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 19:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 19:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 19:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 19:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 19:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 19:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 08.09.2015 19:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 19:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 08.09.2015 19:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 19:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 08.09.2015 19:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 19:11:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 08.09.2015 19:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 19:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 19:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=NULL,Updated=TO_TIMESTAMP('2015-09-08 19:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 19:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540565, AD_Reference_Target_ID=540570, Description='C_Order SO DRAFT INCLUDED',Updated=TO_TIMESTAMP('2015-09-08 19:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 19:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND i.C_Invoice_ID = C_Invoice.C_Invoice_ID
		
)
',Updated=TO_TIMESTAMP('2015-09-08 19:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 19:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS 
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_invoice_ID
	JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
	WHERE o.C_Order_ID=@C_Order_ID@
		AND i.IsSOTrx = o.IsSOTrx
		AND i.IsSOTrx = ''Y''
		AND o.C_Order_ID = C_Order.C_Order_ID
		
)
',Updated=TO_TIMESTAMP('2015-09-08 19:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 19:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='Order',Updated=TO_TIMESTAMP('2015-09-08 19:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 19:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target='Invoice',Updated=TO_TIMESTAMP('2015-09-08 19:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 19:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ISSOTRX = ''Y''',Updated=TO_TIMESTAMP('2015-09-08 19:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 08.09.2015 19:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='Invoice', Role_Target='Order',Updated=TO_TIMESTAMP('2015-09-08 19:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 19:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
C_Invoice.IsSOTrx=''Y'' ',Updated=TO_TIMESTAMP('2015-09-08 19:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 19:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='


C_Invoice.IsSOTrx=''Y'' 
AND
EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID

	WHERE 
	(C_Invoice.C_Invoice_ID = @C_Invoice_ID/-1@
	OR
	o.C_Order_ID = @C_Order_ID/-1@
	)
		AND C_Invoice.IsSOTrx = o.IsSOTrx
		AND C_Invoice.IsSOTrx = ''Y''
		AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)
',Updated=TO_TIMESTAMP('2015-09-08 19:28:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 08.09.2015 19:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-08 19:30:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 19:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2015-09-08 19:31:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 19:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 19:31:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 19:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2015-09-08 19:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 19:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2015-09-08 19:33:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 08.09.2015 19:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='issotrx = ''Y''',Updated=TO_TIMESTAMP('2015-09-08 19:35:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 19:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540570, AD_Reference_Target_ID=540565,Updated=TO_TIMESTAMP('2015-09-08 19:37:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 08.09.2015 19:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='issotrx = ''Y''
AND 1=2',Updated=TO_TIMESTAMP('2015-09-08 19:39:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 19:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' 1=2',Updated=TO_TIMESTAMP('2015-09-08 19:40:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 08.09.2015 19:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 19:41:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 08.09.2015 19:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-08 19:41:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

