-- 2021-01-20T07:07:39.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540561,0,318,TO_TIMESTAMP('2021-01-20 09:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Used to prevent multiple reversals of the same invoice','U','Y','Y','C_Invoice_Reversal_ID','N',TO_TIMESTAMP('2021-01-20 09:07:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-20T07:07:39.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540561 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-01-20T07:07:53.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,55305,541051,540561,0,TO_TIMESTAMP('2021-01-20 09:07:53','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',10,TO_TIMESTAMP('2021-01-20 09:07:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-20T08:11:58.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Invoice_Reversal_ID ON C_Invoice (Reversal_ID)
;

