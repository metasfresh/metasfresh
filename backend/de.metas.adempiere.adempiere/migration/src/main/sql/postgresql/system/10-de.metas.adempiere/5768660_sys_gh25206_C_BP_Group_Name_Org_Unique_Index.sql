-- Run mode: SWING_CLIENT

-- 2025-09-12T13:34:21.511Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540823,0,394,TO_TIMESTAMP('2025-09-12 13:34:21.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Unique index on name and organization','D','Eine Geschäftspartnergruppe mit diesem Namen existiert bereits in Ihrer Organisation. Bitte wählen Sie einen anderen Namen oder deaktivieren Sie die bestehende Gruppe.','Y','Y','C_BP_Group_Name_Org','N',TO_TIMESTAMP('2025-09-12 13:34:21.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsActive=''Y''')
;

-- 2025-09-12T13:34:21.522Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540823 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-09-12T13:34:33.561Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='A Business Partner Group with this name already exists in your organization. Please choose a different name or deactivate the existing one.',Updated=TO_TIMESTAMP('2025-09-12 13:34:33.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540823 AND AD_Language='en_US'
;

-- 2025-09-12T13:34:33.562Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-12T13:35:28.013Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,4970,541460,540823,0,TO_TIMESTAMP('2025-09-12 13:35:27.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2025-09-12 13:35:27.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-12T13:35:39.719Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,4963,541461,540823,0,TO_TIMESTAMP('2025-09-12 13:35:39.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',20,TO_TIMESTAMP('2025-09-12 13:35:39.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

DROP INDEX IF EXISTS C_BP_Group_Name_Org
;

-- 2025-09-12T13:35:48.791Z
CREATE UNIQUE INDEX C_BP_Group_Name_Org ON C_BP_Group (Name,AD_Org_ID) WHERE IsActive='Y'
;
