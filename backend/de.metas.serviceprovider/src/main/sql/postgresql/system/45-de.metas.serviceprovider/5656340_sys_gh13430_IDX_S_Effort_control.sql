
-- 2022-09-15T05:29:47.300Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540708,0,542215,TO_TIMESTAMP('2022-09-15 08:29:47','YYYY-MM-DD HH24:MI:SS'),100,'Unique index effort control record','de.metas.serviceprovider','Y','Y','IDX_S_Effort_control','N',TO_TIMESTAMP('2022-09-15 08:29:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:29:47.310Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540708 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-09-15T05:30:02.070Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584334,541270,540708,0,TO_TIMESTAMP('2022-09-15 08:30:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',10,TO_TIMESTAMP('2022-09-15 08:30:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:30:15.934Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584349,541271,540708,0,TO_TIMESTAMP('2022-09-15 08:30:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',20,TO_TIMESTAMP('2022-09-15 08:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:30:26.631Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584350,541272,540708,0,TO_TIMESTAMP('2022-09-15 08:30:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',30,TO_TIMESTAMP('2022-09-15 08:30:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:30:31.221Z
CREATE UNIQUE INDEX IDX_S_Effort_control ON S_EffortControl (AD_Org_ID,C_Activity_ID,C_Project_ID)
;

