
-- 26.11.2015 15:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540144,TO_TIMESTAMP('2015-11-26 15:34:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','AD_Sequence -> C_DocType',TO_TIMESTAMP('2015-11-26 15:34:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.11.2015 15:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540615,TO_TIMESTAMP('2015-11-26 15:39:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','AD_Sequence -> C_DocType',TO_TIMESTAMP('2015-11-26 15:39:28','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.11.2015 15:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540615 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.11.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,1186,0,540615,115,TO_TIMESTAMP('2015-11-26 15:43:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-11-26 15:43:17','YYYY-MM-DD HH24:MI:SS'),100,'

exists
(
		select 1 
		from AD_Sequence s
		join C_DocType dt on s.AD_Sequence_ID = dt.DocNoSequence_ID
		
		where
		AD_Sequence.AD_Sequence_ID = s.AD_Sequence_ID
		and
		(s.AD_Sequence_ID = @AD_Sequence_ID/-1@ or dt.C_DocType_ID = @C_DocType_ID/-1@)
)')
;

-- 26.11.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
		select 1 
		from AD_Sequence s
		join C_DocType dt on s.AD_Sequence_ID = dt.DocNoSequence_ID
		
		where
		AD_Sequence.AD_Sequence_ID = s.AD_Sequence_ID
		and
		(s.AD_Sequence_ID = @AD_Sequence_ID/-1@ or dt.C_DocType_ID = @C_DocType_ID/-1@)
)',Updated=TO_TIMESTAMP('2015-11-26 15:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540615
;

-- 26.11.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540615,Updated=TO_TIMESTAMP('2015-11-26 15:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540144
;

-- 26.11.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=112,Updated=TO_TIMESTAMP('2015-11-26 15:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540615
;

-- 26.11.2015 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540616,TO_TIMESTAMP('2015-11-26 15:45:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType AD_Sequence -> C_DocType',TO_TIMESTAMP('2015-11-26 15:45:11','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.11.2015 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540616 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.11.2015 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,1501,0,540616,217,TO_TIMESTAMP('2015-11-26 15:46:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-11-26 15:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.11.2015 15:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
		select 1 
		from AD_Sequence s
		join C_DocType dt on s.AD_Sequence_ID = dt.DocNoSequence_ID
		
		where
		C_DocType.C_DocType_ID = dt.C_DocType_ID
		and
		(s.AD_Sequence_ID = @AD_Sequence_ID/-1@ or dt.C_DocType_ID = @C_DocType_ID/-1@)
)',Updated=TO_TIMESTAMP('2015-11-26 15:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540616
;

-- 26.11.2015 15:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=135,Updated=TO_TIMESTAMP('2015-11-26 15:47:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540616
;

-- 26.11.2015 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540616,Updated=TO_TIMESTAMP('2015-11-26 15:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540144
;

