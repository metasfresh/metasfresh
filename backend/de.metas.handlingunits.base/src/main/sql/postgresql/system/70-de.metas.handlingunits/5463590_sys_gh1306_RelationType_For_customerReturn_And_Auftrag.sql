-- 2017-05-26T18:28:50.189
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540180,TO_TIMESTAMP('2017-05-26 18:28:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order (SO) -> Customer Return InOutLine',TO_TIMESTAMP('2017-05-26 18:28:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-26T18:28:52.601
-- URL zum Konzept
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2017-05-26 18:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540180
;

-- 2017-05-26T18:29:25.105
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540666,Updated=TO_TIMESTAMP('2017-05-26 18:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540180
;

-- 2017-05-26T18:29:52.043
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540725,TO_TIMESTAMP('2017-05-26 18:29:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','Customer Return InOutLine Target for C_Order',TO_TIMESTAMP('2017-05-26 18:29:51','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-05-26T18:29:52.046
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540725 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-05-26T18:30:53.227
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,540725,319,TO_TIMESTAMP('2017-05-26 18:30:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2017-05-26 18:30:53','YYYY-MM-DD HH24:MI:SS'),100,'exists (
	select 1 from M_InOut io
	join M_InOutLine ret on io.M_InOut_ID = ret.M_InOut_ID
	join M_InOutLine iol on ret.Return_Origin_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order  o on  ol.C_Order_ID = o.C_Order_ID
	where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''Y'' and M_InOut.M_InOut_ID = io.M_InOut_ID 
)')
;

-- 2017-05-26T18:31:09.969
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540725,Updated=TO_TIMESTAMP('2017-05-26 18:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540180
;

-- 2017-05-26T18:31:17.960
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (
	select 1 from M_InOut io
	join M_InOutLine ret on io.M_InOut_ID = ret.M_InOut_ID
	join M_InOutLine iol on ret.Return_Origin_InOutLine_ID = iol.M_InOutLine_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order  o on  ol.C_Order_ID = o.C_Order_ID
	where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''N'' and M_InOut.M_InOut_ID = io.M_InOut_ID 
)',Updated=TO_TIMESTAMP('2017-05-26 18:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540717
;

-- 2017-05-26T18:31:41.897
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=53097,Updated=TO_TIMESTAMP('2017-05-26 18:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540725
;

-- 2017-05-26T18:32:22.748
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540181,TO_TIMESTAMP('2017-05-26 18:32:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','Customer Return InOutLine -> Order (SO)',TO_TIMESTAMP('2017-05-26 18:32:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-26T18:32:46.198
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540726,TO_TIMESTAMP('2017-05-26 18:32:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','Customer Return Source',TO_TIMESTAMP('2017-05-26 18:32:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-05-26T18:32:46.200
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540726 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-05-26T18:33:19.576
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,540726,319,53097,TO_TIMESTAMP('2017-05-26 18:33:19','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2017-05-26 18:33:19','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''Y''')
;

-- 2017-05-26T18:33:40.216
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540726,Updated=TO_TIMESTAMP('2017-05-26 18:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540181
;

-- 2017-05-26T18:34:05.797
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540727,TO_TIMESTAMP('2017-05-26 18:34:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','C_Order SOTrx target for Customer Return InOutLine',TO_TIMESTAMP('2017-05-26 18:34:05','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-05-26T18:34:05.799
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540727 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-05-26T18:34:38.663
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,2161,0,540727,259,TO_TIMESTAMP('2017-05-26 18:34:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2017-05-26 18:34:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-26T18:34:52.880
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
exists (
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InOutLine ret on iol.M_InOutLine_ID = ret.Return_Origin_InOutLine_ID
	join M_InOut io on ret.M_InOut_ID = io.M_InOut_ID 
	where io.M_InOut_ID = @M_InOut_ID/-1@ and o.IsSOTrx = ''N'' and C_Order.C_Order_ID = o.C_Order_ID
)',Updated=TO_TIMESTAMP('2017-05-26 18:34:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540718
;

-- 2017-05-26T18:35:05.737
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
exists (
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID
	join M_InOutLine ret on iol.M_InOutLine_ID = ret.Return_Origin_InOutLine_ID
	join M_InOut io on ret.M_InOut_ID = io.M_InOut_ID 
	where io.M_InOut_ID = @M_InOut_ID/-1@ and o.IsSOTrx = ''Y'' and C_Order.C_Order_ID = o.C_Order_ID
)',Updated=TO_TIMESTAMP('2017-05-26 18:35:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540727
;

-- 2017-05-26T18:35:12.970
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2017-05-26 18:35:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540727
;

-- 2017-05-26T18:35:27.048
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540727,Updated=TO_TIMESTAMP('2017-05-26 18:35:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540181
;

-- 2017-05-26T18:38:43.442
-- URL zum Konzept
UPDATE AD_Ref_Table SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2017-05-26 18:38:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540726
;

