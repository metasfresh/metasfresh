-- 2018-07-30T18:17:28.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540894,TO_TIMESTAMP('2018-07-30 18:17:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','MD_Candidate Source',TO_TIMESTAMP('2018-07-30 18:17:28','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T18:17:28.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540894 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T18:18:30.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,556473,556473,0,540894,540808,540334,TO_TIMESTAMP('2018-07-30 18:18:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N',TO_TIMESTAMP('2018-07-30 18:18:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T18:19:25.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540895,TO_TIMESTAMP('2018-07-30 18:19:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','MD_Candidate Parent Target for MD_Candidate',TO_TIMESTAMP('2018-07-30 18:19:25','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T18:19:25.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540895 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T18:20:12.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,556473,556473,0,540895,540808,540334,TO_TIMESTAMP('2018-07-30 18:20:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N',TO_TIMESTAMP('2018-07-30 18:20:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T18:21:26.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from MD_Candidate parent  join MD_Candidate child on parent. MD_Candidate_ID = child.MD_Candidate_Parent_ID where parent.MD_Candidate_ID = @MD_Candidate_ID@ and child.MD_Candidate_ID = MD_Candidate.MD_Candidate_ID )',Updated=TO_TIMESTAMP('2018-07-30 18:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540895
;

-- 2018-07-30T18:21:37.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540894,540895,540217,TO_TIMESTAMP('2018-07-30 18:21:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','MD_Candidate Parent -> MD_Candidate',TO_TIMESTAMP('2018-07-30 18:21:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T18:22:15.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.material.dispo',Updated=TO_TIMESTAMP('2018-07-30 18:22:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540217
;

-- 2018-07-30T18:24:32.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from MD_Candidate parent  join MD_Candidate child on parent. MD_Candidate_ID = child.MD_Candidate_Parent_ID where parent.MD_Candidate_ID = @MD_Candidate_ID@ and child.MD_Candidate_ID = MD_CandidateTEST.MD_Candidate_ID )',Updated=TO_TIMESTAMP('2018-07-30 18:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540895
;

-- 2018-07-30T18:25:23.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2018-07-30 18:25:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540217
;

-- 2018-07-30T18:26:52.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from MD_Candidate parent  join MD_Candidate child on parent. MD_Candidate_ID = child.MD_Candidate_Parent_ID where parent.MD_Candidate_ID = @MD_Candidate_ID@ and child.MD_Candidate_ID = MD_Candidate.MD_Candidate_ID )',Updated=TO_TIMESTAMP('2018-07-30 18:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540895
;

-- 2018-07-30T18:28:40.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from MD_Candidate parent  join MD_Candidate child on parent.MD_Candidate_ID = child.MD_Candidate_Parent_ID where parent.MD_Candidate_ID = @MD_Candidate_ID@ and child.MD_Candidate_ID = MD_Candidate.MD_Candidate_ID)',Updated=TO_TIMESTAMP('2018-07-30 18:28:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540895
;

-- 2018-07-30T18:31:03.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from MD_Candidate parent  join MD_CandidateTEST child on parent.MD_Candidate_ID = child.MD_Candidate_Parent_ID where parent.MD_Candidate_ID = @MD_Candidate_ID@ and child.MD_Candidate_ID = MD_Candidate.MD_Candidate_ID)',Updated=TO_TIMESTAMP('2018-07-30 18:31:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540895
;

-- 2018-07-30T18:32:35.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from MD_Candidate child  join MD_CandidateTEST parent on child.MD_Candidate_Parent_ID = parent.MD_Candidate_ID  where parent.MD_Candidate_ID = @MD_Candidate_ID@ and child.MD_Candidate_ID = MD_Candidate.MD_Candidate_ID)',Updated=TO_TIMESTAMP('2018-07-30 18:32:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540895
;

-- 2018-07-30T18:41:20.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='MD_Candidate Target for MD_Candidate Parent ',Updated=TO_TIMESTAMP('2018-07-30 18:41:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540895
;

-- 2018-07-30T18:42:13.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2018-07-30 18:42:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540217
;

-- 2018-07-30T18:44:25.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from MD_Candidate child  join MD_Candidate parent on child.MD_Candidate_Parent_ID = parent.MD_Candidate_ID  where parent.MD_Candidate_ID = @MD_Candidate_ID@ and child.MD_Candidate_ID = MD_Candidate.MD_Candidate_ID)',Updated=TO_TIMESTAMP('2018-07-30 18:44:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540895
;

-- 2018-07-30T18:45:09.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='1=1',Updated=TO_TIMESTAMP('2018-07-30 18:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540894
;

