-- 2018-03-13T11:46:46.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540421,0,335,TO_TIMESTAMP('2018-03-13 11:46:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y','Y','C_Payment_Unique_DocumentNo','N',TO_TIMESTAMP('2018-03-13 11:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-13T11:46:46.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540421 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-03-13T11:47:02.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2018-03-13 11:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540421
;

-- 2018-03-13T11:47:18.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5401,540838,540421,0,TO_TIMESTAMP('2018-03-13 11:47:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y',10,TO_TIMESTAMP('2018-03-13 11:47:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-13T11:47:28.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5398,540839,540421,0,TO_TIMESTAMP('2018-03-13 11:47:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y',20,TO_TIMESTAMP('2018-03-13 11:47:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-13T11:47:39.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5302,540840,540421,0,TO_TIMESTAMP('2018-03-13 11:47:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y',30,TO_TIMESTAMP('2018-03-13 11:47:39','YYYY-MM-DD HH24:MI:SS'),100)
;
