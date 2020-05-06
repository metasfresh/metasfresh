-- 2017-08-29T18:54:53.253
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540739,TO_TIMESTAMP('2017-08-29 18:54:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice Source',TO_TIMESTAMP('2017-08-29 18:54:53','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-08-29T18:54:53.266
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540739 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-08-29T18:55:59.243
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3484,0,540739,318,167,TO_TIMESTAMP('2017-08-29 18:55:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2017-08-29 18:55:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-29T18:59:32.622
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540740,TO_TIMESTAMP('2017-08-29 18:59:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice target for C_Invoice',TO_TIMESTAMP('2017-08-29 18:59:32','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-08-29T18:59:32.628
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540740 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-08-30T10:21:01.671
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3484,3484,0,540740,318,TO_TIMESTAMP('2017-08-30 10:21:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2017-08-30 10:21:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T10:21:19.332
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Invoice Reference target for C_Invoice',Updated=TO_TIMESTAMP('2017-08-30 10:21:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2017-08-30T10:22:56.869
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=167, WhereClause='
exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.C_Invoice_ID = i.Ref_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2017-08-30 10:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2017-08-30T10:23:24.045
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540739,540740,540184,TO_TIMESTAMP('2017-08-30 10:23:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','Y','C_Invoice -> Referenced Invoice',TO_TIMESTAMP('2017-08-30 10:23:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T10:24:39.490
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540741,TO_TIMESTAMP('2017-08-30 10:24:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice target for Reference Invoice',TO_TIMESTAMP('2017-08-30 10:24:39','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-08-30T10:24:39.494
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540741 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-08-30T10:25:56.214
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540741,318,TO_TIMESTAMP('2017-08-30 10:25:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2017-08-30 10:25:56','YYYY-MM-DD HH24:MI:SS'),100,'

exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i2.C_Invoice_ID
)')
;

-- 2017-08-30T10:26:01.238
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2017-08-30 10:26:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T10:26:13.918
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540739,540741,540185,TO_TIMESTAMP('2017-08-30 10:26:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','Referenced Invoice -> Invoice',TO_TIMESTAMP('2017-08-30 10:26:13','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2017-08-30T13:44:40.531
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=3484,Updated=TO_TIMESTAMP('2017-08-30 13:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:02:49.232
-- URL zum Konzept
UPDATE AD_RelationType SET EntityType='de.metas.invoice',Updated=TO_TIMESTAMP('2017-08-30 14:02:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540185
;

-- 2017-08-30T14:07:42.956
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='

exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2017-08-30 14:07:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:12:07.950
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='

exists (
	select 1 from C_Invoice iu
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2017-08-30 14:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:37:17.061
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='

exists (
	select 1 from C_Invoice iu
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2017-08-30 14:37:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:37:19.909
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='

exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2017-08-30 14:37:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;


