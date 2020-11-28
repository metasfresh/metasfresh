-- 08.09.2016 15:57
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540386,0,529,TO_TIMESTAMP('2016-09-08 15:57:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','R_RequestType_InternalName_Unique','N',TO_TIMESTAMP('2016-09-08 15:57:38','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y'' AND InternalName IS NOT NULL')
;

-- 08.09.2016 15:57
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540386 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 08.09.2016 15:57
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555042,540771,540386,0,TO_TIMESTAMP('2016-09-08 15:57:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2016-09-08 15:57:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2016 16:00
-- URL zum Konzept
CREATE UNIQUE INDEX R_RequestType_InternalName_Unique ON R_RequestType (InternalName) WHERE IsActive='Y' AND InternalName IS NOT NULL
;

