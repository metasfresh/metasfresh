-- 2017-11-16T16:14:25.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540413,0,540860,TO_TIMESTAMP('2017-11-16 16:14:25','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','ESR_PostFinanceUserNumber_ESRAccountNo_Unique','N',TO_TIMESTAMP('2017-11-16 16:14:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-16T16:14:25.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540413 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-11-16T16:14:33.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='de.metas.payment.esr',Updated=TO_TIMESTAMP('2017-11-16 16:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540413
;

-- 2017-11-16T16:15:31.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET Name='ESR_PostFinanceUserNumber_RenderedAccountNo_Unique',Updated=TO_TIMESTAMP('2017-11-16 16:15:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540413
;

-- 2017-11-16T16:32:27.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2017-11-16 16:32:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540413
;

-- 2017-11-16T16:32:39.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557848,540825,540413,0,TO_TIMESTAMP('2017-11-16 16:32:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',10,TO_TIMESTAMP('2017-11-16 16:32:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-16T16:32:56.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557849,540826,540413,0,TO_TIMESTAMP('2017-11-16 16:32:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',20,TO_TIMESTAMP('2017-11-16 16:32:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-16T16:32:58.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2017-11-16 16:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540413
;
