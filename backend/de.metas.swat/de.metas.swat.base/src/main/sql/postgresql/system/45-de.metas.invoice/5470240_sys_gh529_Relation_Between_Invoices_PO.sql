-- 2017-08-30T14:42:16.474
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540739,540740,540186,TO_TIMESTAMP('2017-08-30 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','C_Invoice -> Referenced Invoice PO',TO_TIMESTAMP('2017-08-30 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:42:25.386
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Invoice Reference target for C_Invoice PO',Updated=TO_TIMESTAMP('2017-08-30 14:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2017-08-30T14:42:38.684
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Invoice Reference target for C_Invoice SO',Updated=TO_TIMESTAMP('2017-08-30 14:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2017-08-30T14:43:02.940
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540742,TO_TIMESTAMP('2017-08-30 14:43:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice Reference target for C_Invoice PO',TO_TIMESTAMP('2017-08-30 14:43:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-08-30T14:43:02.941
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540742 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-08-30T14:43:31.955
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540742,318,183,TO_TIMESTAMP('2017-08-30 14:43:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2017-08-30 14:43:31','YYYY-MM-DD HH24:MI:SS'),100,'
exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.C_Invoice_ID = i.Ref_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
)')
;

-- 2017-08-30T14:43:52.315
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540742,Updated=TO_TIMESTAMP('2017-08-30 14:43:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540186
;

-- 2017-08-30T14:44:45.312
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540187,TO_TIMESTAMP('2017-08-30 14:44:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','Referenced Invoice -> Invoice PO',TO_TIMESTAMP('2017-08-30 14:44:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.596
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Invoice target for Reference Invoice SO',Updated=TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:45:27.885
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540743,TO_TIMESTAMP('2017-08-30 14:45:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice target for Reference Invoice PO',TO_TIMESTAMP('2017-08-30 14:45:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-08-30T14:45:27.889
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540743 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-08-30T14:45:50.450
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3484,3484,0,540743,318,TO_TIMESTAMP('2017-08-30 14:45:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2017-08-30 14:45:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:46:01.572
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=183, WhereClause='

exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2017-08-30 14:46:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540743
;

-- 2017-08-30T14:46:15.003
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540739, AD_Reference_Target_ID=540743,Updated=TO_TIMESTAMP('2017-08-30 14:46:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540187
;

-- 2017-08-30T14:48:11.555
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Invoice Source SO',Updated=TO_TIMESTAMP('2017-08-30 14:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540739
;

-- 2017-08-30T14:48:24.311
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='IsSOTrx = ''Y''',Updated=TO_TIMESTAMP('2017-08-30 14:48:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540739
;

-- 2017-08-30T14:48:53.429
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='

exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
and i.IsSOTrx = ''Y'' and i2.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2017-08-30 14:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:49:14.658
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540744,TO_TIMESTAMP('2017-08-30 14:49:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice SourcePO',TO_TIMESTAMP('2017-08-30 14:49:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-08-30T14:49:14.662
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540744 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-08-30T14:49:47.196
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540744,318,183,TO_TIMESTAMP('2017-08-30 14:49:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2017-08-30 14:49:47','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''N''')
;

-- 2017-08-30T14:50:22.252
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='

exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID

)',Updated=TO_TIMESTAMP('2017-08-30 14:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:57:33.651
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540744,Updated=TO_TIMESTAMP('2017-08-30 14:57:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540186
;

-- 2017-08-30T14:57:46.850
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540744,Updated=TO_TIMESTAMP('2017-08-30 14:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540187
;

