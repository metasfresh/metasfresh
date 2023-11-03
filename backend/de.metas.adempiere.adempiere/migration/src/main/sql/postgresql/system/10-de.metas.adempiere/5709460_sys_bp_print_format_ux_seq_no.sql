UPDATE c_bp_printformat bpp
Set seqno = print_formats.seqNo,
    updated = TO_TIMESTAMP('2023-11-03 15:38:32','YYYY-MM-DD HH24:MI:SS'),
    updatedby = 99
FROM (SELECT
          pf.c_bp_printformat_id,
          (row_number() OVER (PARTITION BY bpp.c_bpartner_id ORDER BY bpp.c_bp_printformat_id)) * 10 AS seqNo
      FROM c_bp_printformat pf
     ) AS print_formats
WHERE bpp.c_bp_printformat_id = print_formats.c_bp_printformat_id
;

-- 2023-11-02T14:35:27.627Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540776,0,540638,TO_TIMESTAMP('2023-11-02 15:35:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Die Reihenfolge von aktiven Einträgen muss eindeutig sein.','Y','Y','bpartner_seqno_ux','N',TO_TIMESTAMP('2023-11-02 15:35:27','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2023-11-02T14:35:27.639Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540776 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-11-02T14:35:42.616Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-02 15:35:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540776 AND AD_Language='de_CH'
;

-- 2023-11-02T14:35:43.925Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-02 15:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540776 AND AD_Language='de_DE'
;

-- 2023-11-02T14:37:32.711Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='The Sequence of active records needs to be explicit.', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-02 15:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540776 AND AD_Language='en_US'
;

-- 2023-11-02T14:37:56.250Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551611,541374,540776,0,TO_TIMESTAMP('2023-11-02 15:37:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-11-02 15:37:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-02T14:38:32.409Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587626,541375,540776,0,TO_TIMESTAMP('2023-11-02 15:38:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2023-11-02 15:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-03T07:44:00.214Z
CREATE UNIQUE INDEX bpartner_seqno_ux ON C_BP_PrintFormat (C_BPartner_ID,SeqNo) WHERE IsActive='Y'
;