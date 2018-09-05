-- 2018-07-30T19:02:00.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,53631,540219,TO_TIMESTAMP('2018-07-30 19:01:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','N','DD_Order -> MD_Candidate_Dist_Detail',TO_TIMESTAMP('2018-07-30 19:01:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T19:02:39.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540897,TO_TIMESTAMP('2018-07-30 19:02:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','MD_Candidate_Dist_Detail target for DD_Order',TO_TIMESTAMP('2018-07-30 19:02:39','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T19:02:39.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540897 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T19:03:06.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,556473,556473,0,540897,540808,540334,TO_TIMESTAMP('2018-07-30 19:03:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N',TO_TIMESTAMP('2018-07-30 19:03:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T19:03:54.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists  ( select 1 from MD_Candidate mdc join MD_Candidate_Dist_Detail cdd on mdc.MD_Candidate_ID = cdd.MD_Candidate_ID join DD_Order dd on cdd.dd_Order_ID = dd.DD_Order_ID where mdc.MD_Candidate_ID = MD_Candidate.MD_Candidate_ID and dd.DD_Order_ID = @DD_Order_ID@ )',Updated=TO_TIMESTAMP('2018-07-30 19:03:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540897
;

-- 2018-07-30T19:04:04.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540897,Updated=TO_TIMESTAMP('2018-07-30 19:04:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540219
;

