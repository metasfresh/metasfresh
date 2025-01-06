-- Run mode: SWING_CLIENT

-- 2024-12-13T09:33:16.994Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540811,0,542386,TO_TIMESTAMP('2024-12-13 10:33:16.816','YYYY-MM-DD HH24:MI:SS.US'),100,'PostFinance Sender Biller ID needs to be unique','de.metas.postfinance','Biller ID is already used for another Organisation','Y','N','PostFinance_Org_Config_PostFinance_Sender_BillerId_unique','N',TO_TIMESTAMP('2024-12-13 10:33:16.816','YYYY-MM-DD HH24:MI:SS.US'),100,'isActive = ''Y''')
;

-- 2024-12-13T09:33:17.003Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540811 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-12-13T10:35:48.698Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587824,541439,540811,0,TO_TIMESTAMP('2024-12-13 11:35:48.527','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y',10,TO_TIMESTAMP('2024-12-13 11:35:48.527','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-13T10:35:54.347Z
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2024-12-13 11:35:54.345','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540811
;

-- 2024-12-13T10:35:54.665Z
DROP INDEX IF EXISTS postfinance_org_config_postfinance_sender_billerid_unique
;

-- 2024-12-13T10:35:54.669Z
CREATE UNIQUE INDEX PostFinance_Org_Config_PostFinance_Sender_BillerId_unique ON PostFinance_Org_Config (PostFinance_Sender_BillerId) WHERE isActive = 'Y'
;

