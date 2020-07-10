
-- 24.11.2015 11:22
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541130,53237,TO_TIMESTAMP('2015-11-24 11:22:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Document Before Un-Close',TO_TIMESTAMP('2015-11-24 11:22:06','YYYY-MM-DD HH24:MI:SS'),100,'DBUC','Document Before Un-Close')
;

-- 24.11.2015 11:22
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541130 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 24.11.2015 11:22
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541131,53237,TO_TIMESTAMP('2015-11-24 11:22:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Document After Un-Close',TO_TIMESTAMP('2015-11-24 11:22:33','YYYY-MM-DD HH24:MI:SS'),100,'DAUC','Document After Un-Close')
;

-- 24.11.2015 11:22
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541131 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 24.11.2015 11:25
-- URL zum Konzept
UPDATE AD_Ref_List SET ValueName='Document After UnClose',Updated=TO_TIMESTAMP('2015-11-24 11:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541131
;

-- 24.11.2015 11:25
-- URL zum Konzept
UPDATE AD_Ref_List SET ValueName='Document Before UnClose',Updated=TO_TIMESTAMP('2015-11-24 11:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541130
;


