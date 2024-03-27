-- 2024-03-21T17:03:37.980Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540791,0,542399,TO_TIMESTAMP('2024-03-21 19:03:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Y','idx_externalsystempcm_unique_value','N',TO_TIMESTAMP('2024-03-21 19:03:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-21T17:03:37.993Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540791 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-03-21T17:04:03.208Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588003,541400,540791,0,TO_TIMESTAMP('2024-03-21 19:04:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2024-03-21 19:04:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-21T17:04:05.893Z
CREATE UNIQUE INDEX idx_externalsystempcm_unique_value ON ExternalSystem_Config_ProCareManagement (ExternalSystemValue)
;

-- 2024-03-21T17:04:31.420Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540792,0,542399,TO_TIMESTAMP('2024-03-21 19:04:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Y','idx_externalsystempcm_unique_parent_id','N',TO_TIMESTAMP('2024-03-21 19:04:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-21T17:04:31.429Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540792 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-03-21T17:04:43.143Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588002,541401,540792,0,TO_TIMESTAMP('2024-03-21 19:04:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2024-03-21 19:04:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-21T17:04:45.810Z
CREATE UNIQUE INDEX idx_externalsystempcm_unique_parent_id ON ExternalSystem_Config_ProCareManagement (ExternalSystem_Config_ID)
;

