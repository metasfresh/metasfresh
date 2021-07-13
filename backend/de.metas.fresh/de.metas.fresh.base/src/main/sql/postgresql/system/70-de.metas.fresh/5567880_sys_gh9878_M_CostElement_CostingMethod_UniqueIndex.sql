-- 2020-09-17T08:32:28.233Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540552,0,770,TO_TIMESTAMP('2020-09-17 11:32:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','M_CostElement_CostingMethod_Unique','N',TO_TIMESTAMP('2020-09-17 11:32:27','YYYY-MM-DD HH24:MI:SS'),100,'IsActive = ''Y''')
;

-- 2020-09-17T08:32:28.406Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540552 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-09-17T08:33:51.579Z
-- URL zum Konzept
UPDATE AD_Index_Table SET Description='This index is important for the sql function M_Cost_CostPrice_Function. Please, keep them synchronized.',Updated=TO_TIMESTAMP('2020-09-17 11:33:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540552
;

-- 2020-09-17T08:34:09.835Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,13463,541040,540552,0,TO_TIMESTAMP('2020-09-17 11:34:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2020-09-17 11:34:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-17T08:34:15.585Z
-- URL zum Konzept
CREATE UNIQUE INDEX M_CostElement_CostingMethod_Unique ON M_CostElement (CostingMethod) WHERE IsActive = 'Y'
;

