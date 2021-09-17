-- 2021-09-17T07:17:49.718Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540649,0,541772,TO_TIMESTAMP('2021-09-17 09:17:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_User_Occupation_Unique','N',TO_TIMESTAMP('2021-09-17 09:17:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-17T07:17:49.738Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540649 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-17T07:18:39.544Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575466,541173,540649,0,TO_TIMESTAMP('2021-09-17 09:18:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-17 09:18:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-17T07:18:52.017Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575465,541174,540649,0,TO_TIMESTAMP('2021-09-17 09:18:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-09-17 09:18:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-17T07:18:54.293Z
-- URL zum Konzept
CREATE INDEX AD_User_Occupation_Unique ON AD_User_Occupation_Job (AD_User_ID,CRM_Occupation_ID)
;

-- 2021-09-17T07:21:03.294Z
-- URL zum Konzept
DROP INDEX IF EXISTS ad_user_occupation_unique
;

-- 2021-09-17T07:21:03.309Z
-- URL zum Konzept
CREATE UNIQUE INDEX AD_User_Occupation_Unique ON AD_User_Occupation_Job (AD_User_ID,CRM_Occupation_ID)
;

-- 2021-09-17T07:21:01.110Z
-- URL zum Konzept
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2021-09-17 09:21:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540649
;


-- 2021-09-17T07:20:05.452Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540650,0,541774,TO_TIMESTAMP('2021-09-17 09:20:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','AD_User_Specialization_Unique','N',TO_TIMESTAMP('2021-09-17 09:20:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-17T07:20:05.454Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540650 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-17T07:20:14.951Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575482,541175,540650,0,TO_TIMESTAMP('2021-09-17 09:20:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-17 09:20:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-17T07:20:26.556Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575483,541176,540650,0,TO_TIMESTAMP('2021-09-17 09:20:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-09-17 09:20:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-17T07:20:29.047Z
-- URL zum Konzept
CREATE UNIQUE INDEX AD_User_Specialization_Unique ON AD_User_Occupation_Specialization (AD_User_ID,CRM_Occupation_ID)
;


-- 2021-09-17T07:22:45.734Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540651,0,541775,TO_TIMESTAMP('2021-09-17 09:22:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','AD_User_Occupation_AdditionalSpecialization_Unique','N',TO_TIMESTAMP('2021-09-17 09:22:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-17T07:22:45.737Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540651 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-17T07:22:55.877Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575496,541177,540651,0,TO_TIMESTAMP('2021-09-17 09:22:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-17 09:22:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-17T07:23:06.939Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575497,541178,540651,0,TO_TIMESTAMP('2021-09-17 09:23:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-09-17 09:23:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-17T07:23:08.763Z
-- URL zum Konzept
CREATE UNIQUE INDEX AD_User_Occupation_AdditionalSpecialization_Unique ON AD_User_Occupation_AdditionalSpecialization (AD_User_ID,CRM_Occupation_ID)
;
