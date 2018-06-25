-- 2018-05-09T18:28:28.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540432,0,540970,TO_TIMESTAMP('2018-05-09 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','MKTG_Campaign_UniqueNewsletter','N',TO_TIMESTAMP('2018-05-09 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-09T18:28:28.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540432 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-05-09T18:28:51.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='de.metas.marketing.base', IsUnique='Y', WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2018-05-09 18:28:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540432
;

-- 2018-05-09T18:39:03.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y'' and isDefaultNews = ''Y''',Updated=TO_TIMESTAMP('2018-05-09 18:39:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540432
;

-- 2018-05-09T18:39:35.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,559911,540855,540432,0,TO_TIMESTAMP('2018-05-09 18:39:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y',10,TO_TIMESTAMP('2018-05-09 18:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-09T18:39:50.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y'' and isDefaultNewsletter = ''Y''',Updated=TO_TIMESTAMP('2018-05-09 18:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540432
;

-- 2018-05-09T18:39:52.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MKTG_Campaign_UniqueNewsletter ON MKTG_Campaign (AD_Org_ID) WHERE IsActive = 'Y' and isDefaultNewsletter = 'Y'
;
