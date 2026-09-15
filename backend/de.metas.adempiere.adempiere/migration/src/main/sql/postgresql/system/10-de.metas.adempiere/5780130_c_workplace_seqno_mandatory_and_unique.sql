-- Run mode: SWING_CLIENT

-- 2025-12-09T10:14:46.767Z
INSERT INTO t_alter_column values('c_workplace','SeqNo','NUMERIC(10)',null,null)
;

-- 2025-12-09T10:14:46.776Z
INSERT INTO t_alter_column values('c_workplace','SeqNo',null,'NOT NULL',null)
;

-- 2025-12-09T10:19:34.048Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540847,0,542375,TO_TIMESTAMP('2025-12-09 10:19:33.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Die Reihenfolge muss für aktive Einträge eindeutig sein.','Y','Y','c_workplace_seqno_uq','N',TO_TIMESTAMP('2025-12-09 10:19:33.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'isActive = ''Y''')
;

-- 2025-12-09T10:19:34.073Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540847 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-12-09T10:19:57.295Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='SeqNo needs to be unique for active records.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-09 10:19:57.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540847 AND AD_Language='en_US'
;

-- 2025-12-09T10:19:57.296Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-09T10:19:58.418Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-09 10:19:58.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540847 AND AD_Language='de_CH'
;

-- 2025-12-09T10:20:03.600Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-09 10:20:03.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540847 AND AD_Language='de_DE'
;

-- 2025-12-09T10:20:16.332Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591579,541496,540847,0,TO_TIMESTAMP('2025-12-09 10:20:16.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2025-12-09 10:20:16.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-09T10:20:17.332Z
CREATE UNIQUE INDEX c_workplace_seqno_uq ON C_Workplace (SeqNo) WHERE isActive = 'Y'
;

-- Column: C_Workplace_ExternalSystem.ExternalSystem_ID
-- 2025-12-10T07:41:58.137Z
UPDATE AD_Column SET EntityType='D', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-12-10 07:41:58.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591528
;

-- 2025-12-10T07:41:59.995Z
INSERT INTO t_alter_column values('c_workplace_externalsystem','ExternalSystem_ID','NUMERIC(10)',null,null)
;

-- 2025-12-10T07:42:00.001Z
INSERT INTO t_alter_column values('c_workplace_externalsystem','ExternalSystem_ID',null,'NOT NULL',null)
;
