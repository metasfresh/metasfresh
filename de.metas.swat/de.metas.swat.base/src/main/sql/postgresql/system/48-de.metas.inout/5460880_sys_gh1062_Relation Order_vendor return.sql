-- 2017-04-27T11:50:16.105
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540176,TO_TIMESTAMP('2017-04-27 11:50:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','C_Order (PO) -> Vendor Return InOutLine',TO_TIMESTAMP('2017-04-27 11:50:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-27T11:50:51.053
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540676,Updated=TO_TIMESTAMP('2017-04-27 11:50:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540176
;

-- 2017-04-27T11:51:32.787
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540717,TO_TIMESTAMP('2017-04-27 11:51:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','Vendor Return InOutLine Target for C_Order',TO_TIMESTAMP('2017-04-27 11:51:32','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-04-27T11:51:32.789
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540717 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-04-27T11:52:00.314
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3529,0,540717,320,TO_TIMESTAMP('2017-04-27 11:52:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2017-04-27 11:52:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-27T11:52:07.871
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=53098,Updated=TO_TIMESTAMP('2017-04-27 11:52:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540717
;

-- 2017-04-27T13:38:03.423
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InOut io
	join M_InOutLine iol on io.M_InOut_ID = iol.M_InOut_ID
	join M_InOutLine iol on iol.VendorReturn_Origin_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order  o on  ol.C_Order_ID = o.C_Order_ID
	where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''N'' and M_InOut.M_InOut_ID = io.M_InOut_ID 
)',Updated=TO_TIMESTAMP('2017-04-27 13:38:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540717
;

-- 2017-04-27T13:38:22.207
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540717,Updated=TO_TIMESTAMP('2017-04-27 13:38:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540176
;






-- 2017-04-27T13:42:42.952
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=53098,Updated=TO_TIMESTAMP('2017-04-27 13:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540716
;

-- 2017-04-27T13:42:49.220
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540716,540177,TO_TIMESTAMP('2017-04-27 13:42:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','Vendor Return InOutLine -> Order (PO)',TO_TIMESTAMP('2017-04-27 13:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-27T13:43:41.153
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540718,TO_TIMESTAMP('2017-04-27 13:43:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','C_Order POTrx target for Vendor Return InOutLine',TO_TIMESTAMP('2017-04-27 13:43:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-04-27T13:43:41.159
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540718 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-04-27T13:46:32.137
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540718,259,181,TO_TIMESTAMP('2017-04-27 13:46:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2017-04-27 13:46:32','YYYY-MM-DD HH24:MI:SS'),100,'
exists (
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InOutLine ret on iol.M_InOutLine_ID = ret.VendorReturn_Origin_InOutLine_ID
	join M_InOut io on ret.M_InOut_ID = io.M_InOut_ID 
	where io.M_InOut_ID = @M_InOut_ID/-1@ and o.IsSOTrx = ''N'' and C_Order.C_Order_ID = o.C_Order_ID
)')
;





-- 2017-04-27T13:48:12.083
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InOut io
	join M_InOutLine ret on io.M_InOut_ID = ret.M_InOut_ID
	join M_InOutLine iol on ret.VendorReturn_Origin_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order  o on  ol.C_Order_ID = o.C_Order_ID
	where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''N'' and M_InOut.M_InOut_ID = io.M_InOut_ID 
)',Updated=TO_TIMESTAMP('2017-04-27 13:48:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540717
;



-- 2017-04-27T14:35:10.466
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540718,Updated=TO_TIMESTAMP('2017-04-27 14:35:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540177
;

-- 2017-04-27T14:41:26.332
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=3521, AD_Table_ID=319,Updated=TO_TIMESTAMP('2017-04-27 14:41:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540717
;





-- 2017-04-27T14:47:32.552
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=337,Updated=TO_TIMESTAMP('2017-04-27 14:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540177
;

-- 2017-04-27T15:07:16.170
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540719,TO_TIMESTAMP('2017-04-27 15:07:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','Vendor Return Source',TO_TIMESTAMP('2017-04-27 15:07:16','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-04-27T15:07:16.176
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540719 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-04-27T15:07:43.817
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3521,0,540719,319,53098,TO_TIMESTAMP('2017-04-27 15:07:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2017-04-27 15:07:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-27T15:11:38.639
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540719,Updated=TO_TIMESTAMP('2017-04-27 15:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540177
;


-- 2017-04-27T15:36:16.062
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
exists (
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InOutLine ret on iol.M_InOutLine_ID = ret.VendorReturn_Origin_InOutLine_ID
	join M_InOut io on ret.M_InOut_ID = io.M_InOut_ID 
	where io.M_InOut_ID = @M_InOut_ID/-1@ and o.IsSOTrx = ''N'' and C_Order.C_Order_ID = o.C_Order_ID
)',Updated=TO_TIMESTAMP('2017-04-27 15:36:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;



-- 2017-04-27T15:42:28.088
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InOut io
	join M_InOutLine ret on io.M_InOut_ID = ret.M_InOut_ID
	join M_InOutLine iol on ret.VendorReturn_Origin_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order  o on  ol.C_Order_ID = o.C_Order_ID
	where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''N'' and M_InOut.M_InOut_ID = io.M_InOut_ID 
)',Updated=TO_TIMESTAMP('2017-04-27 15:42:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540717
;

-- 2017-04-27T16:31:20.715
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=295,Updated=TO_TIMESTAMP('2017-04-27 16:31:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556514
;

-- 2017-04-27T16:36:38.640
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='IsSOTrx = ''N''',Updated=TO_TIMESTAMP('2017-04-27 16:36:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540719
;



-- 2017-04-27T16:47:47.217
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
exists (
	select 1 from C_Ordepr o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InOutLine ret on iol.M_InOutLine_ID = ret.VendorReturn_Origin_InOutLine_ID
	join M_InOut io on ret.M_InOut_ID = io.M_InOut_ID 
	where io.M_InOut_ID = @M_InOut_ID/-1@ and o.IsSOTrx = ''N'' and C_Order.C_Order_ID = o.C_Order_ID
)',Updated=TO_TIMESTAMP('2017-04-27 16:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;

-- 2017-04-27T16:48:12.447
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
exists (
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InOutLine ret on iol.M_InOutLine_ID = ret.VendorReturn_Origin_InOutLine_ID
	join M_InOut io on ret.M_InOut_ID = io.M_InOut_ID 
	where io.M_InOut_ID = @M_InOut_ID/-1@ and o.IsSOTrx = ''N'' and C_Order.C_Order_ID = o.C_Order_ID
)',Updated=TO_TIMESTAMP('2017-04-27 16:48:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;


-- 2017-04-27T16:51:12.563
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
exists (
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InOutLine ret on iol.M_InOutLine_ID = ret.VendorReturn_Origin_InOutLine_ID
	join M_InOut io on ret.M_InOut_ID = io.M_InOut_ID 
	where io.M_InOut_ID = @M_InOut_ID/-1@ and o.IsSOTrx = ''N'' and C_Order.C_Order_ID = o.C_Order_ID
)',Updated=TO_TIMESTAMP('2017-04-27 16:51:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;

-- 2017-04-27T17:07:32.318
-- URL zum Konzept
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2017-04-27 17:07:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540176
;

-- 2017-04-27T17:07:32.775
-- URL zum Konzept
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2017-04-27 17:07:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540177
;

-- 2017-04-27T17:07:33.107
-- URL zum Konzept
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2017-04-27 17:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540175
;

-- 2017-04-27T17:07:37.127
-- URL zum Konzept
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2017-04-27 17:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540175
;

-- 2017-04-27T17:20:55.750
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2017-04-27 17:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540717
;

-- 2017-04-27T17:22:50.190
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=53098,Updated=TO_TIMESTAMP('2017-04-27 17:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540717
;

-- 2017-04-27T17:23:08.047
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2017-04-27 17:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;


-- 2017-05-02T13:52:55.244
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2017-05-02 13:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;

-- 2017-05-02T13:57:39.790
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2017-05-02 13:57:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;

-- 2017-05-02T14:14:54.652
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=2161,Updated=TO_TIMESTAMP('2017-05-02 14:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;

-- 2017-05-02T14:15:17.493
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2017-05-02 14:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;


