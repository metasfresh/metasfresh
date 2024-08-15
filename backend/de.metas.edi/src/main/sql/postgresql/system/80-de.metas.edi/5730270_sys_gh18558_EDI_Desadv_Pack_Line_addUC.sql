-- 2024-07-29T10:54:01.242Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540799,0,542170,TO_TIMESTAMP('2024-07-29 12:54:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','Y','UC_EDI_Desadv_Pack_Line','N',TO_TIMESTAMP('2024-07-29 12:54:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-29T10:54:01.247Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540799 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-07-29T10:54:33.223Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588875,541415,540799,0,TO_TIMESTAMP('2024-07-29 12:54:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',10,TO_TIMESTAMP('2024-07-29 12:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-29T10:55:43.203Z
UPDATE AD_Index_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2024-07-29 12:55:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=541415
;

-- 2024-07-29T10:55:59.150Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583426,541416,540799,0,TO_TIMESTAMP('2024-07-29 12:55:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',10,TO_TIMESTAMP('2024-07-29 12:55:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-29T10:56:09.833Z
DROP INDEX IF EXISTS uc_edi_desadv_pack_line
;

-- 2024-07-29T10:56:09.835Z
CREATE UNIQUE INDEX UC_EDI_Desadv_Pack_Line ON EDI_Desadv_Pack (EDI_Desadv_ID,Line)
;

