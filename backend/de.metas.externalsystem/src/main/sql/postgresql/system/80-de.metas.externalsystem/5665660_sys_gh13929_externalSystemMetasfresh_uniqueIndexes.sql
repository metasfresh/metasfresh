-- 2022-11-22T13:33:40.856Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540712,0,542253,TO_TIMESTAMP('2022-11-22 15:33:40','YYYY-MM-DD HH24:MI:SS'),100,'Unique constraint on parent config id','D','Y','Y','IDX_S_ExternalSystemMetasfresh_unique_parent_id','N',TO_TIMESTAMP('2022-11-22 15:33:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T13:33:40.867Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540712 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-11-22T13:34:00.473Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584878,541280,540712,0,TO_TIMESTAMP('2022-11-22 15:34:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-11-22 15:34:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T13:34:08.115Z
CREATE UNIQUE INDEX IDX_S_ExternalSystemMetasfresh_unique_parent_id ON ExternalSystem_Config_Metasfresh (ExternalSystem_Config_ID) where isActive = 'Y'
;

-- 2022-11-22T13:35:38.694Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540713,0,542253,TO_TIMESTAMP('2022-11-22 15:35:38','YYYY-MM-DD HH24:MI:SS'),100,'Unique index external system metasfresh value','D','Y','Y','IDX_S_ExternalSystemMetasfresh_unique_value','N',TO_TIMESTAMP('2022-11-22 15:35:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T13:35:38.696Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540713 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-11-22T13:36:02.894Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584879,541281,540713,0,TO_TIMESTAMP('2022-11-22 15:36:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-11-22 15:36:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T13:36:09.523Z
CREATE UNIQUE INDEX IDX_S_ExternalSystemMetasfresh_unique_value ON ExternalSystem_Config_Metasfresh (ExternalSystemValue) where isActive = 'Y'
;

