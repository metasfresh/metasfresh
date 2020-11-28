-- 2017-11-03T14:09:39.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Role_Source,Updated,UpdatedBy) VALUES (0,0,540266,540562,540194,TO_TIMESTAMP('2017-11-03 14:09:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_InvoiceCandidate -> C_Flatrate_Term','PO_Invoice',TO_TIMESTAMP('2017-11-03 14:09:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-03T14:17:45.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540763,TO_TIMESTAMP('2017-11-03 14:17:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','RelType C_Invoice_Candidate->C_Flatrate_Term',TO_TIMESTAMP('2017-11-03 14:17:45','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-11-03T14:17:45.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540763 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-11-03T14:23:08.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,557170,545802,0,540763,540320,TO_TIMESTAMP('2017-11-03 14:23:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N',TO_TIMESTAMP('2017-11-03 14:23:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-03T14:28:46.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists 
	(
		select 1 from C_Flatrate_Term ft	
		where C_Invoice_Candidate.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			and C_Invoice_Candidate.Record_ID = ft.C_Flatrate_Term_ID and C_Invoice_Candidate.AD_Table_ID=540320
)',Updated=TO_TIMESTAMP('2017-11-03 14:28:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540763
;

-- 2017-11-03T14:29:38.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_Table WHERE AD_Reference_ID=540763
;

-- 2017-11-03T14:29:42.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540763
;

-- 2017-11-03T14:29:42.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540763
;

-- 2017-11-03T14:29:59.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540752, EntityType='de.metas.contracts', Role_Source='',Updated=TO_TIMESTAMP('2017-11-03 14:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540194
;

-- 2017-11-03T14:41:40.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540359,Updated=TO_TIMESTAMP('2017-11-03 14:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540752
;

-- 2017-11-03T14:42:44.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540764,TO_TIMESTAMP('2017-11-03 14:42:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_Invoice_Candidate->C_FlatRate_Term',TO_TIMESTAMP('2017-11-03 14:42:44','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-11-03T14:42:44.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540764 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-11-03T14:42:54.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2017-11-03 14:42:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540752
;

-- 2017-11-03T14:44:03.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,557170,545802,0,540764,540320,540359,TO_TIMESTAMP('2017-11-03 14:44:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N',TO_TIMESTAMP('2017-11-03 14:44:03','YYYY-MM-DD HH24:MI:SS'),100,'exists 
	(
		select 1 from C_Flatrate_Term ft	
		where C_Invoice_Candidate.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			and C_Invoice_Candidate.Record_ID = ft.C_Flatrate_Term_ID and C_Invoice_Candidate.AD_Table_ID=540320
)')
;

-- 2017-11-03T14:44:18.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540764,Updated=TO_TIMESTAMP('2017-11-03 14:44:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540194
;

-- 2017-11-03T15:39:55.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists 
	(
		select 1 from C_Flatrate_Term ft	
		join C_Invoice_Candidate ic on (ic.Record_ID = ft.C_Flatrate_Term_ID and ic.AD_Table_ID=540320)
		where ic.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
		and C_Flatrate_Term.C_Flatrate_Term_ID = ft.C_Flatrate_Term_ID 
			
)',Updated=TO_TIMESTAMP('2017-11-03 15:39:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540764
;

