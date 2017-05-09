
--
-- C_Order => MD_Candidate
--

-- 2017-05-04T16:28:40.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540720,TO_TIMESTAMP('2017-05-04 16:28:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','MD_Candidate_Target_For_C_Order',TO_TIMESTAMP('2017-05-04 16:28:40','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-05-04T16:28:40.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540720 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-05-04T16:29:27.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,556473,0,540720,540808,540334,TO_TIMESTAMP('2017-05-04 16:29:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N',TO_TIMESTAMP('2017-05-04 16:29:27','YYYY-MM-DD HH24:MI:SS'),100,'exists
(
	select 1
	from MD_Candidate_Demand_Detail cdd
		join C_OrderLine ol on cdd.C_OrderLine_ID = ol.C_OrderLine_ID	
	where
		ol.C_Order_ID = @C_Order_ID/-1@  AND MD_Candidate.MD_Candidate_ID = cdd.MD_Candidate_ID
)')
;

-- 2017-05-04T16:30:03.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540666,540720,540178,TO_TIMESTAMP('2017-05-04 16:30:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Y','C_Order -> MD_Candidate',TO_TIMESTAMP('2017-05-04 16:30:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-05T14:07:06.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540721,TO_TIMESTAMP('2017-05-05 14:07:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','MD_Candidate_Production_Target_For_C_Order',TO_TIMESTAMP('2017-05-05 14:07:06','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-05-05T14:07:06.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540721 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-05-05T14:24:48.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,556473,0,540721,540808,540334,TO_TIMESTAMP('2017-05-05 14:24:48','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2017-05-05 14:24:48','YYYY-MM-DD HH24:MI:SS'),100,'MD_Candidate_SubType=''PRODUCTION''
AND exists (
	select 1 from MD_Candidate_Demand_Detail cdd
		join C_OrderLine ol on cdd.C_OrderLine_ID = ol.C_OrderLine_ID	
	where
		ol.C_Order_ID = @C_Order_ID/-1@  AND MD_Candidate.MD_Candidate_ID = cdd.MD_Candidate_ID
)')
;

-- 2017-05-05T14:24:59.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='MD_Candidate_PRODUCTION_Target_For_C_Order',Updated=TO_TIMESTAMP('2017-05-05 14:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540721
;

-- 2017-05-05T14:24:59.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540721
;

-- 2017-05-05T14:42:02.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540666,540721,540179,TO_TIMESTAMP('2017-05-05 14:42:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','C_Order -> MD_Candidate_Just_Production',TO_TIMESTAMP('2017-05-05 14:42:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-05T14:45:08.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541272,53331,TO_TIMESTAMP('2017-05-05 14:45:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Materialdispo (Produktion)',TO_TIMESTAMP('2017-05-05 14:45:08','YYYY-MM-DD HH24:MI:SS'),100,'MD_Candidate_PRODUCTION','MD_Candidate_PRODUCTION')
;

-- 2017-05-05T14:45:08.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541272 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2017-05-05T14:45:45.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.material.dispo', Role_Target='MD_Candidate_PRODUCTION',Updated=TO_TIMESTAMP('2017-05-05 14:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540179
;

-- 2017-05-05T14:45:51.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2017-05-05 14:45:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540178
;

-- 2017-05-05T14:45:59.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2017-05-05 14:45:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540178
;



-- 2017-05-05T16:05:41.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N', Name='C_Order -> MD_Candidate_all',Updated=TO_TIMESTAMP('2017-05-05 16:05:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540178
;

-- 2017-05-05T16:06:06.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.material.dispo',Updated=TO_TIMESTAMP('2017-05-05 16:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540721
;

