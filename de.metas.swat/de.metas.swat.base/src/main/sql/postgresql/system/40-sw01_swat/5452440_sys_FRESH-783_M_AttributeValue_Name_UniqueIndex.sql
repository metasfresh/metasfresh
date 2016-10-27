-- 26.10.2016 11:32
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540390,0,558,TO_TIMESTAMP('2016-10-26 11:32:43','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Y','M_AttributeValue_Name_Unique','N',TO_TIMESTAMP('2016-10-26 11:32:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 11:32
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540390 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 26.10.2016 11:32
-- URL zum Konzept
UPDATE AD_Index_Table SET EntityType='D',Updated=TO_TIMESTAMP('2016-10-26 11:32:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540390
;

-- 26.10.2016 11:33
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' AND AD_Ref_List_ID IS NOT NULL',Updated=TO_TIMESTAMP('2016-10-26 11:33:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540390
;

-- 26.10.2016 11:33
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' ',Updated=TO_TIMESTAMP('2016-10-26 11:33:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540390
;

-- 26.10.2016 11:33
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,8466,540780,540390,0,TO_TIMESTAMP('2016-10-26 11:33:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2016-10-26 11:33:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 11:34
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,8468,540781,540390,0,TO_TIMESTAMP('2016-10-26 11:34:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2016-10-26 11:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 11:34
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,8458,540782,540390,0,TO_TIMESTAMP('2016-10-26 11:34:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2016-10-26 11:34:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 11:34
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,8464,540783,540390,0,TO_TIMESTAMP('2016-10-26 11:34:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',40,TO_TIMESTAMP('2016-10-26 11:34:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 12:10
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' AND EXISTS (select 1 from M_Attribute a where a.M_Attribute_ID = M_AttributeValue.M_Attribute_ID and a.IsActive = ''Y'' )',Updated=TO_TIMESTAMP('2016-10-26 12:10:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540390
;


-- 26.10.2016 15:22
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' ',Updated=TO_TIMESTAMP('2016-10-26 15:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540390
;


-- 26.10.2016 16:17
-- URL zum Konzept
--CREATE UNIQUE INDEX M_AttributeValue_Name_Unique ON M_AttributeValue (M_Attribute_ID,Name,AD_Org_ID,AD_Client_ID) WHERE IsActive='Y' 
--;
