-- 2017-07-11T17:48:42.375
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540182,TO_TIMESTAMP('2017-07-11 17:48:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Material Disposal -> Invoice Candidate',TO_TIMESTAMP('2017-07-11 17:48:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-11T17:49:19.952
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540730,TO_TIMESTAMP('2017-07-11 17:49:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Material Disposal Source',TO_TIMESTAMP('2017-07-11 17:49:19','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-07-11T17:49:19.955
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540730 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-07-11T17:50:04.889
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3542,0,540730,321,341,TO_TIMESTAMP('2017-07-11 17:50:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2017-07-11 17:50:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-11T17:50:21.175
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540730,Updated=TO_TIMESTAMP('2017-07-11 17:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540182
;

-- 2017-07-11T17:51:08.565
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540731,TO_TIMESTAMP('2017-07-11 17:51:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Invoice Candidate Target for Material Disposal',TO_TIMESTAMP('2017-07-11 17:51:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-07-11T17:51:08.567
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540731 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-07-11T18:00:16.221
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,544906,0,540731,540270,540092,TO_TIMESTAMP('2017-07-11 18:00:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2017-07-11 18:00:16','YYYY-MM-DD HH24:MI:SS'),100,'exists (
	select 1 from C_Invoice_Candidate ic
	join C_Invoice_Candidate ic on ic.AD_Table_ID = get_table_ID(''M_InventoryLine'') and ic.Record_ID =  il.M_InventoryLine_ID 
	where i.M_Inventory_ID = @M_Inventory_ID/-1@  and C_Invoice_Candidate.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate 
)')
;

-- 2017-07-11T18:00:34.692
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540731,Updated=TO_TIMESTAMP('2017-07-11 18:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540182
;

-- 2017-07-11T18:01:55.701
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540183,TO_TIMESTAMP('2017-07-11 18:01:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','Invoice Candidate -> Material Disposal',TO_TIMESTAMP('2017-07-11 18:01:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-11T18:01:58.580
-- URL zum Konzept
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2017-07-11 18:01:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540182
;

-- 2017-07-11T18:02:46.558
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540732,TO_TIMESTAMP('2017-07-11 18:02:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Invoice Candidate Source',TO_TIMESTAMP('2017-07-11 18:02:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-07-11T18:02:46.559
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540732 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-07-11T18:03:48.027
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,544906,0,540732,540270,540092,TO_TIMESTAMP('2017-07-11 18:03:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2017-07-11 18:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-11T18:04:23.641
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540732,Updated=TO_TIMESTAMP('2017-07-11 18:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540183
;

-- 2017-07-11T18:04:53.161
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540733,TO_TIMESTAMP('2017-07-11 18:04:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Material Disposal Target for Invoice Candidate',TO_TIMESTAMP('2017-07-11 18:04:53','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-07-11T18:04:53.162
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540733 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-07-11T18:09:02.436
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3542,3542,0,540733,321,TO_TIMESTAMP('2017-07-11 18:09:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2017-07-11 18:09:02','YYYY-MM-DD HH24:MI:SS'),100,'
exists (
	select 1 from M_Inventory i
	join M_InventoryLine il on i.MInventory_ID = il.M_Inventory_ID
	join C_Invoice_Candidate ic on ic.AD_Table_ID = get_table_ID(''M_InventoryLine'') and ic.Record_ID =  il.M_InventoryLine_ID 
	
	where ic.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID/-1@ and i.M_Inventory_ID = M_Inventory.M_Inventory_ID
	)')
;

-- 2017-07-11T18:09:25.477
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=341,Updated=TO_TIMESTAMP('2017-07-11 18:09:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540733
;

-- 2017-07-11T18:09:39.388
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540733,Updated=TO_TIMESTAMP('2017-07-11 18:09:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540183
;

-- 2017-07-11T18:10:40.154
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
	
exists (
	select 1 from M_Inventory i
	join M_InventoryLine il on i.MInventory_ID = il.M_Inventory_ID
	join C_Invoice_Candidate ic on ic.AD_Table_ID = get_table_ID(''M_InventoryLine'') and ic.Record_ID =  il.M_InventoryLine_ID 
	
	where ic.C_Invoice_Candidate_ID = C_Invoice_Candidate.C_Invoice_Candidate_ID and i.M_Inventory_ID = @M_Inventory_ID/-1@
	)',Updated=TO_TIMESTAMP('2017-07-11 18:10:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540731
;

-- 2017-07-11T18:13:27.888
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
	
exists (
	select 1 from M_Inventory i
	join M_InventoryLine il on i.M_Inventory_ID = il.M_Inventory_ID
	join C_Invoice_Candidate ic on ic.AD_Table_ID = get_table_ID(''M_InventoryLine'') and ic.Record_ID =  il.M_InventoryLine_ID 
	
	where ic.C_Invoice_Candidate_ID = C_Invoice_Candidate.C_Invoice_Candidate_ID and i.M_Inventory_ID = @M_Inventory_ID/-1@
	)',Updated=TO_TIMESTAMP('2017-07-11 18:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540731
;

-- 2017-07-11T18:13:43.330
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
exists (
	select 1 from M_Inventory i
	join M_InventoryLine il on i.M_Inventory_ID = il.M_Inventory_ID
	join C_Invoice_Candidate ic on ic.AD_Table_ID = get_table_ID(''M_InventoryLine'') and ic.Record_ID =  il.M_InventoryLine_ID 
	
	where ic.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID/-1@ and i.M_Inventory_ID = M_Inventory.M_Inventory_ID
	)',Updated=TO_TIMESTAMP('2017-07-11 18:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540733
;

