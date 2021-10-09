-- 2018-07-30T18:52:49.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540503,540218,TO_TIMESTAMP('2018-07-30 18:52:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Y','N','PP_Order -> MD_Candidate_Prod_Detail',TO_TIMESTAMP('2018-07-30 18:52:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T18:53:09.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540896,TO_TIMESTAMP('2018-07-30 18:53:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','MD_Candidate_Prod_Detail target for PP_Order',TO_TIMESTAMP('2018-07-30 18:53:09','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T18:53:09.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540896 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T18:54:03.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,556473,556473,0,540896,540808,540334,TO_TIMESTAMP('2018-07-30 18:54:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N',TO_TIMESTAMP('2018-07-30 18:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T18:56:32.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists  ( select 1 from MD_Candidate mdc join MD_Candidate_Prod_Detail cpd on mdc.MD_Candidate_ID = cpd.MD_Candidate_ID join PP_Order pp on cpd.pp_Order_ID = pp.PP_Order_ID where mdc.MD_Candidate_ID = MD_Candidate.MD_Candidate_ID and pp.PP_Order_ID = @PP_Order_ID@ )',Updated=TO_TIMESTAMP('2018-07-30 18:56:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540896
;

-- 2018-07-30T18:56:45.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540896,Updated=TO_TIMESTAMP('2018-07-30 18:56:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540218
;

