-- 2017-10-03T13:18:39.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540752,TO_TIMESTAMP('2017-10-03 13:18:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','RelType C_Invoice_Candidate->C_FlatRate_Term',TO_TIMESTAMP('2017-10-03 13:18:39','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-10-03T13:18:39.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540752 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-10-03T13:24:46.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,545802,0,540752,540320,TO_TIMESTAMP('2017-10-03 13:24:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N',TO_TIMESTAMP('2017-10-03 13:24:46','YYYY-MM-DD HH24:MI:SS'),100,'C_Flatrate_Term_ID IN
(
	SELECT Record_Id FROM C_Invoice_Candidate ic
	WHERE C_Flatrate_Term_ID = ic.Record_ID AND ic.AD_table_ID = 540320
)')
;

-- 2017-10-03T13:25:19.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540752,540190,TO_TIMESTAMP('2017-10-03 13:25:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_FlatRate_Term<->C_Invoice_Candidate',TO_TIMESTAMP('2017-10-03 13:25:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-03T13:26:02.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540753,TO_TIMESTAMP('2017-10-03 13:26:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','RelType C_FlatRate_Term->C_Invoice_Candidate',TO_TIMESTAMP('2017-10-03 13:26:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-10-03T13:26:02.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540753 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-10-03T13:27:03.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,544906,544906,0,540753,540270,TO_TIMESTAMP('2017-10-03 13:27:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N',TO_TIMESTAMP('2017-10-03 13:27:03','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_Candidate_ID IN
(
	SELECT C_Invoice_Candidate_ID FROM C_Invoice_Candidate ic
	WHERE @C_Flatrate_Term_ID@ = ic.Record_ID AND ic.AD_table_ID = 540320
)')
;

-- 2017-10-03T13:27:16.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540753,Updated=TO_TIMESTAMP('2017-10-03 13:27:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540190
;

