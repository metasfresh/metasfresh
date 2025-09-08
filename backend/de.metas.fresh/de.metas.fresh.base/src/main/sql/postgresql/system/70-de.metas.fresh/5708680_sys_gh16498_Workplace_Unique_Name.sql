-- 2023-10-25T10:43:37.587Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540766,0,542375,TO_TIMESTAMP('2023-10-25 11:43:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Der Arbeitsplatz name muss eindeutig sein','Y','Y','WorkplaceName_UniqueIndex','N',TO_TIMESTAMP('2023-10-25 11:43:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-25T10:43:37.592Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540766 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-10-25T10:43:53.953Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587583,541358,540766,0,TO_TIMESTAMP('2023-10-25 11:43:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-10-25 11:43:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-25T10:44:02.135Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587575,541359,540766,0,TO_TIMESTAMP('2023-10-25 11:44:01','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',20,TO_TIMESTAMP('2023-10-25 11:44:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-25T10:44:07.688Z
UPDATE AD_Index_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-10-25 11:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=541359
;

-- 2023-10-25T10:44:09.730Z
CREATE UNIQUE INDEX WorkplaceName_UniqueIndex ON C_Workplace (Name,AD_Client_ID)
;

-- 2023-10-25T10:44:51.265Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='The workplace name must be unique', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-25 11:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540766 AND AD_Language='en_US'
;

