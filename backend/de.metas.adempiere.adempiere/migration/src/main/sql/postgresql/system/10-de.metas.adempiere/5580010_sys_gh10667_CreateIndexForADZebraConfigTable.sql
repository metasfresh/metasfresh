-- 2021-02-19T17:33:33.729Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540565,0,541579,TO_TIMESTAMP('2021-02-19 18:33:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','isDefaultConfig','N',TO_TIMESTAMP('2021-02-19 18:33:28','YYYY-MM-DD HH24:MI:SS'),100,'isActive=''Y''')
;

-- 2021-02-19T17:33:33.732Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540565 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-02-19T17:34:30.637Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,572870,541059,540565,0,TO_TIMESTAMP('2021-02-19 18:34:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-02-19 18:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-19T17:34:58.637Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,572877,541060,540565,0,TO_TIMESTAMP('2021-02-19 18:34:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-02-19 18:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-19T17:35:04.435Z
-- URL zum Konzept
CREATE UNIQUE INDEX isDefaultConfig ON AD_Zebra_Config (AD_Org_ID,IsDefault) WHERE isActive='Y'
;

