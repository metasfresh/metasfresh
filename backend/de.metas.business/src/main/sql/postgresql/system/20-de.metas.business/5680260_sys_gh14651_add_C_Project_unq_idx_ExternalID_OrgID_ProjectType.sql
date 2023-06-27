-- 2023-03-02T17:28:39.511Z
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540705
;

-- 2023-03-02T17:28:39.542Z
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540705
;



-- 2023-03-02T17:36:02.175Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540727,0,203,TO_TIMESTAMP('2023-03-02 19:36:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','ExternalID_OrgID_ProjectType_NonNull','N',TO_TIMESTAMP('2023-03-02 19:36:02','YYYY-MM-DD HH24:MI:SS'),100,'C_ProjectType_ID IS NOT NULL and isactive = ''Y''')
;

-- 2023-03-02T17:36:02.178Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540727 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-03-02T17:36:10.061Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584192,541316,540727,0,TO_TIMESTAMP('2023-03-02 19:36:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-03-02 19:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-02T17:36:18.845Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,1350,541317,540727,0,TO_TIMESTAMP('2023-03-02 19:36:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2023-03-02 19:36:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-02T17:36:26.035Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,8757,541318,540727,0,TO_TIMESTAMP('2023-03-02 19:36:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2023-03-02 19:36:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-02T17:36:30.391Z
CREATE UNIQUE INDEX ExternalID_OrgID_ProjectType_NonNull ON C_Project (ExternalId,AD_Org_ID,C_ProjectType_ID) WHERE C_ProjectType_ID IS NOT NULL and isactive = 'Y'
;

-- 2023-03-02T17:37:12.620Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540728,0,203,TO_TIMESTAMP('2023-03-02 19:37:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','ExternalID_OrgID_ProjectType_Nullable','N',TO_TIMESTAMP('2023-03-02 19:37:12','YYYY-MM-DD HH24:MI:SS'),100,'C_ProjectType_ID IS NULL and isactive = ''Y''')
;

-- 2023-03-02T17:37:12.622Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540728 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-03-02T17:37:24.198Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584192,541319,540728,0,TO_TIMESTAMP('2023-03-02 19:37:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-03-02 19:37:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-02T17:37:29.947Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,1350,541320,540728,0,TO_TIMESTAMP('2023-03-02 19:37:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2023-03-02 19:37:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-02T17:37:39.720Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,8757,541321,540728,0,TO_TIMESTAMP('2023-03-02 19:37:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2023-03-02 19:37:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-03T06:33:39.076Z
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541321
;

-- 2023-03-03T06:47:00.674Z
UPDATE AD_Index_Table SET Name='ExternalID_OrgID_with_null_project_type',Updated=TO_TIMESTAMP('2023-03-03 08:47:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540728
;

-- 2023-03-03T06:47:03.079Z
CREATE UNIQUE INDEX ExternalID_OrgID_with_null_project_type ON C_Project (ExternalId,AD_Org_ID) WHERE C_ProjectType_ID IS NULL and isactive = 'Y'
;

