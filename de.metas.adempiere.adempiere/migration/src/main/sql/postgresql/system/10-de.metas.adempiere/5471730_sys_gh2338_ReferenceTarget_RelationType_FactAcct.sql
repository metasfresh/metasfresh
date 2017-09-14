-- 2017-09-14T19:20:30.403
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540747,TO_TIMESTAMP('2017-09-14 19:20:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Fact_Acct',TO_TIMESTAMP('2017-09-14 19:20:30','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-09-14T19:20:30.417
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540747 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-09-14T19:23:03.325
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3001,0,540747,270,162,TO_TIMESTAMP('2017-09-14 19:23:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2017-09-14 19:23:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T19:23:22.290
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsReferenceTarget,Name,Updated,UpdatedBy) VALUES (0,0,540747,540189,TO_TIMESTAMP('2017-09-14 19:23:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Y','-> Fact_Acct',TO_TIMESTAMP('2017-09-14 19:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

