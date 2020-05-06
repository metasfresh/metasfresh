-- 2017-04-18T18:08:16.091
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540174,TO_TIMESTAMP('2017-04-18 18:08:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order (PO) -> M_Inventory_Line',TO_TIMESTAMP('2017-04-18 18:08:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-18T18:10:09.516
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540711,TO_TIMESTAMP('2017-04-18 18:10:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_InventoryLine_Target_For_C_Order',TO_TIMESTAMP('2017-04-18 18:10:09','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-04-18T18:10:09.519
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540711 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-04-18T18:14:15.706
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3555,0,540711,322,TO_TIMESTAMP('2017-04-18 18:14:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2017-04-18 18:14:15','YYYY-MM-DD HH24:MI:SS'),100,'exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	where ol.C_Order_ID = @C_Order_ID/-1@
)')
;

-- 2017-04-18T18:14:25.231
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=341,Updated=TO_TIMESTAMP('2017-04-18 18:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540711
;

-- 2017-04-18T18:15:14.124
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540676, AD_Reference_Target_ID=540711,Updated=TO_TIMESTAMP('2017-04-18 18:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540174
;

-- 2017-04-18T18:24:26.311
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540175,TO_TIMESTAMP('2017-04-18 18:24:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_InventoryLine -> C_Order (PO)',TO_TIMESTAMP('2017-04-18 18:24:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-18T18:24:57.257
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540712,TO_TIMESTAMP('2017-04-18 18:24:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_InventoryLine_Source',TO_TIMESTAMP('2017-04-18 18:24:57','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-04-18T18:24:57.261
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540712 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-04-18T18:25:49.040
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3555,0,540712,322,341,TO_TIMESTAMP('2017-04-18 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2017-04-18 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-18T18:26:00.485
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540712,Updated=TO_TIMESTAMP('2017-04-18 18:26:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540175
;

-- 2017-04-18T18:26:44.028
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540713,TO_TIMESTAMP('2017-04-18 18:26:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order POTrx target for M_InventoryLine',TO_TIMESTAMP('2017-04-18 18:26:43','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-04-18T18:26:44.033
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540713 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-04-18T18:28:08.915
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540713,259,TO_TIMESTAMP('2017-04-18 18:28:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2017-04-18 18:28:08','YYYY-MM-DD HH24:MI:SS'),100,'exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	where il.M_InventryLine_ID = @M_InventoryLine_ID/-1@
)')
;

-- 2017-04-18T18:28:30.645
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540713,Updated=TO_TIMESTAMP('2017-04-18 18:28:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540175
;

-- 2017-04-18T18:29:36.663
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	where il.M_InventoryLine_ID = @M_InventoryLine_ID/-1@
)',Updated=TO_TIMESTAMP('2017-04-18 18:29:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540713
;

-- 2017-04-18T18:34:01.895
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2017-04-18 18:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556484
;

-- 2017-04-18T18:37:18.028
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2017-04-18 18:37:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540713
;

-- 2017-04-18T18:38:32.839
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order on on ol.C_Order_ID = o.C_Order_ID
	where il.M_InventoryLine_ID = @M_InventoryLine_ID/-1@ and o.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2017-04-18 18:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540713
;

-- 2017-04-18T18:39:44.354
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order o on ol.C_Order_ID = o.C_Order_ID
	where ol.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2017-04-18 18:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540711
;

-- 2017-04-18T18:41:31.477
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order on  ol.C_Order_ID = o.C_Order_ID
	where il.M_InventoryLine_ID = @M_InventoryLine_ID/-1@ and o.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2017-04-18 18:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540713
;

-- 2017-04-18T18:43:50.421
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order  o on  ol.C_Order_ID = o.C_Order_ID
	where il.M_InventoryLine_ID = @M_InventoryLine_ID/-1@ and o.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2017-04-18 18:43:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540713
;

-- 2017-04-19T12:04:13.288
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order o on ol.C_Order_ID = o.C_Order_ID
	where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''N'' and M_InventoryLine.M_InventoryLine_ID = il.M_InventoryLine_ID 
)',Updated=TO_TIMESTAMP('2017-04-19 12:04:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540711
;

-- 2017-04-19T12:04:48.254
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order  o on  ol.C_Order_ID = o.C_Order_ID
	where il.M_InventoryLine_ID = @M_InventoryLine_ID/-1@ and o.IsSOTrx = ''N'' and C_Order.C_Order_ID = o.C_Order_ID
)',Updated=TO_TIMESTAMP('2017-04-19 12:04:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540713
;

-- 2017-04-19T12:15:12.325
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InventoryLine il on iol.M_InOutLine_ID = il.M_InOutLine_ID
	where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''N'' and M_InventoryLine.M_InventoryLine_ID = il.M_InventoryLine_ID 
)',Updated=TO_TIMESTAMP('2017-04-19 12:15:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540711
;


-- 2017-04-19T12:25:03.367
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InventoryLine il on iol.M_InOutLine_ID = il.M_InOutLine_ID
	where il.M_InventoryLine_ID = @M_InventoryLine_ID/-1@ and o.IsSOTrx = ''N'' and C_Order.C_Order_ID = o.C_Order_ID
)',Updated=TO_TIMESTAMP('2017-04-19 12:25:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540713
;

-- 2017-04-19T12:27:32.741
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InventoryLine il
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order  o on  ol.C_Order_ID = o.C_Order_ID
	where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''N'' and M_InventoryLine.M_InventoryLine_ID = il.M_InventoryLine_ID 
)',Updated=TO_TIMESTAMP('2017-04-19 12:27:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540711
;

-- 2017-04-19T14:39:36.684
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=3542, AD_Table_ID=321, WhereClause='exists (
	select 1 from M_Inventory i
	join M_InventoryLine il on i.M_Inventory_ID = il.M_Inventory_ID
	join M_InOutLine iol on il.M_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order  o on  ol.C_Order_ID = o.C_Order_ID
	where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''N'' and M_Inventory.M_Inventory_ID = i.M_Inventory_ID 
)',Updated=TO_TIMESTAMP('2017-04-19 14:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540711
;

-- 2017-04-19T14:40:27.477
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InventoryLine il on iol.M_InOutLine_ID = il.M_InOutLine_ID
	join M_Inventory i on il.M_Inventory_ID = i.M_Inventory_ID 
	where i.M_Inventory_ID = @M_Inventory_ID/-1@ and o.IsSOTrx = ''N'' and C_Order.C_Order_ID = o.C_Order_ID
)',Updated=TO_TIMESTAMP('2017-04-19 14:40:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540713
;






