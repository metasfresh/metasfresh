

-- 2018-12-07T12:01:37.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540481,0,541157,'IsActive = ''Y''',TO_TIMESTAMP('2018-12-07 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','C_BPartner_TimeSpan_Unique_BPartner','N',TO_TIMESTAMP('2018-12-07 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-07T12:01:37.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540481 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-12-07T12:01:48.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,563682,540925,540481,0,TO_TIMESTAMP('2018-12-07 12:01:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2018-12-07 12:01:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-07T12:02:01.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET BeforeChangeCode='', WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2018-12-07 12:02:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540481
;

