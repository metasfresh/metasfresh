

-- 2017-12-08T17:19:15.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540414,0,540753,TO_TIMESTAMP('2017-12-08 17:19:15','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','pmm_week_uq','N',TO_TIMESTAMP('2017-12-08 17:19:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-08T17:19:15.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540414 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-12-08T17:21:25.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2017-12-08 17:21:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540414
;

-- 2017-12-08T17:21:39.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2017-12-08 17:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540414
;

-- 2017-12-08T17:21:50.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='de.metas.procurement',Updated=TO_TIMESTAMP('2017-12-08 17:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540414
;

-- 2017-12-08T17:22:09.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554247,540827,540414,0,TO_TIMESTAMP('2017-12-08 17:22:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',10,TO_TIMESTAMP('2017-12-08 17:22:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-08T17:22:30.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554251,540828,540414,0,TO_TIMESTAMP('2017-12-08 17:22:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',20,TO_TIMESTAMP('2017-12-08 17:22:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-08T17:22:41.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554257,540829,540414,0,TO_TIMESTAMP('2017-12-08 17:22:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',30,TO_TIMESTAMP('2017-12-08 17:22:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-08T17:22:52.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554327,540830,540414,0,TO_TIMESTAMP('2017-12-08 17:22:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',40,TO_TIMESTAMP('2017-12-08 17:22:52','YYYY-MM-DD HH24:MI:SS'),100)
;