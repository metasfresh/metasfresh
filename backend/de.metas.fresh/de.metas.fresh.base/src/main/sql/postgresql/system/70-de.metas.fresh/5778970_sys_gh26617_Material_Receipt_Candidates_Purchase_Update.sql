-- Run mode: SWING_CLIENT

-- Column: M_ReceiptSchedule.C_BP_Group_ID
-- Column SQL (old): (SELECT MAX(bp.C_BP_Group_ID) from C_BPartner bp where bp.C_BPartner_ID=M_ReceiptSchedule.C_BPartner_ID)
-- 2025-11-30T12:58:59.144Z
UPDATE AD_Column SET ColumnSQL='(SELECT bp.C_BP_Group_ID from C_BPartner bp where bp.C_BPartner_ID=M_ReceiptSchedule.C_BPartner_ID)',Updated=TO_TIMESTAMP('2025-11-30 12:58:59.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591609
;

-- 2025-11-30T13:08:30.074Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540845,0,291,TO_TIMESTAMP('2025-11-30 13:08:29.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Used to support SQL-columns where a partner''s group is displayed and/or filtered by','D','Y','N','C_BPartner_ID_C_BP_Group_ID','N',TO_TIMESTAMP('2025-11-30 13:08:29.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-30T13:08:30.153Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540845 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-11-30T13:09:35.772Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,4940,541493,540845,0,TO_TIMESTAMP('2025-11-30 13:09:35.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2025-11-30 13:09:35.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-30T13:11:42.484Z
UPDATE AD_Index_Column SET AD_Column_ID=2893,Updated=TO_TIMESTAMP('2025-11-30 13:11:42.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Column_ID=541493
;

-- 2025-11-30T13:11:56.865Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,4940,541494,540845,0,TO_TIMESTAMP('2025-11-30 13:11:56.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',20,TO_TIMESTAMP('2025-11-30 13:11:56.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-30T13:12:31.244Z
CREATE INDEX C_BPartner_ID_C_BP_Group_ID ON C_BPartner (C_BPartner_ID,C_BP_Group_ID)
;

COMMENT ON INDEX public.C_BPartner_ID_C_BP_Group_ID IS 'used to support SQL-columns where a partner''s group is displayed and/or filtered by'
;
