-- 26.01.2016 09:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540150,TO_TIMESTAMP('2016-01-26 09:51:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dunning','Y','N','N','C_Dunning_Candidate -> C_DunningDoc',TO_TIMESTAMP('2016-01-26 09:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.01.2016 09:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540633,TO_TIMESTAMP('2016-01-26 09:52:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dunning','Y','N','C_Dunning_Candidate ->C_DunningDoc_Line',TO_TIMESTAMP('2016-01-26 09:52:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.01.2016 09:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540633 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.01.2016 10:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,547331,0,540633,540396,TO_TIMESTAMP('2016-01-26 10:15:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dunning','Y','N',TO_TIMESTAMP('2016-01-26 10:15:02','YYYY-MM-DD HH24:MI:SS'),100,'exists
(
	select 1
	from C_Dunning_Candidate dc
	join C_DunningDoc_Line_Source dls on dls.C_Dunning_Candidate_ID = dc.C_Dunning_Candidate_ID
	join C_DunningDoc_Line dl on dls.C_DunningDoc_Line_ID = dl.C_DunningDoc_Line_ID
	join C_DunningDoc dd on dl.C_DunningDoc_ID = dd.C_DunningDoc_ID
	where
		C_Dunning_Candidate.C_Dunning_Candidate_ID = dc.C_Dunning_Candidate_ID and
		(dc.C_Dunning_Candidate_ID = @C_Dunning_Candidate_ID/-1@ or dd.C_DunningDoc_ID = @C_DunningDoc_ID/-1@)
)')
;

-- 26.01.2016 10:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540154,Updated=TO_TIMESTAMP('2016-01-26 10:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540633
;

-- 26.01.2016 10:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Dunning_Candidate ->C_DunningDoc',Updated=TO_TIMESTAMP('2016-01-26 10:24:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540633
;

-- 26.01.2016 10:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540633
;

-- 26.01.2016 10:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540633,Updated=TO_TIMESTAMP('2016-01-26 10:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540150
;

-- 26.01.2016 10:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540634,TO_TIMESTAMP('2016-01-26 10:27:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dunning','Y','N','RelType C_Dunning_Candidate -> C_DunningDoc',TO_TIMESTAMP('2016-01-26 10:27:15','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.01.2016 10:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540634 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.01.2016 10:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,547408,0,540634,540401,TO_TIMESTAMP('2016-01-26 10:27:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dunning','Y','N',TO_TIMESTAMP('2016-01-26 10:27:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.01.2016 10:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1
	from C_Dunning_Candidate dc
	join C_DunningDoc_Line_Source dls on dls.C_Dunning_Candidate_ID = dc.C_Dunning_Candidate_ID
	join C_DunningDoc_Line dl on dls.C_DunningDoc_Line_ID = dl.C_DunningDoc_Line_ID
	join C_DunningDoc dd on dl.C_DunningDoc_ID = dd.C_DunningDoc_ID
	where
		C_DunningDoc.C_DunningDoc_ID = dd.C_DunningDoc_ID and
		(dc.C_Dunning_Candidate_ID = @C_Dunning_Candidate_ID/-1@ or dd.C_DunningDoc_ID = @C_DunningDoc_ID/-1@)
)',Updated=TO_TIMESTAMP('2016-01-26 10:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540634
;

-- 26.01.2016 10:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540155,Updated=TO_TIMESTAMP('2016-01-26 10:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540634
;

-- 26.01.2016 10:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540634,Updated=TO_TIMESTAMP('2016-01-26 10:29:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540150
;

