

-- 2017-09-12T11:46:08.485
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540746,TO_TIMESTAMP('2017-09-12 11:46:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_ChangeLog',TO_TIMESTAMP('2017-09-12 11:46:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-09-12T11:46:08.488
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540746 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-09-12T11:48:07.380
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,8817,0,540746,580,TO_TIMESTAMP('2017-09-12 11:48:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2017-09-12 11:48:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-12T11:48:31.179
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8811,Updated=TO_TIMESTAMP('2017-09-12 11:48:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540746
;

-- 2017-09-12T13:12:35.168
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=270,Updated=TO_TIMESTAMP('2017-09-12 13:12:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540746
;



-- 2017-09-12T14:17:22.097
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540746,540188,TO_TIMESTAMP('2017-09-12 14:17:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','-> AD_ChangeLog',TO_TIMESTAMP('2017-09-12 14:17:21','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2017-09-12T14:49:50.919
-- URL zum Konzept
UPDATE AD_RelationType SET IsReferenceTarget='Y',Updated=TO_TIMESTAMP('2017-09-12 14:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540188
;

