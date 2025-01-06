-- 2024-02-14T14:07:05.858Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540788,0,542395,TO_TIMESTAMP('2024-02-14 16:07:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Y','idx_unq_m_hu_qrcode_assignmnet','N',TO_TIMESTAMP('2024-02-14 16:07:05','YYYY-MM-DD HH24:MI:SS'),100,'isActive=''Y''')
;

-- 2024-02-14T14:07:05.865Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540788 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-02-14T14:07:20.002Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587944,541395,540788,0,TO_TIMESTAMP('2024-02-14 16:07:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',10,TO_TIMESTAMP('2024-02-14 16:07:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T14:07:34.103Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587945,541396,540788,0,TO_TIMESTAMP('2024-02-14 16:07:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',20,TO_TIMESTAMP('2024-02-14 16:07:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T14:07:53.419Z
CREATE UNIQUE INDEX idx_unq_m_hu_qrcode_assignmnet ON M_HU_QRCode_Assignment (M_HU_ID,M_HU_QRCode_ID) WHERE isActive='Y'
;

