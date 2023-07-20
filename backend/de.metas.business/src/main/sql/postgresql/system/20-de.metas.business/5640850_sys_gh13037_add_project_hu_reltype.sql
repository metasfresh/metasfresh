-- 2022-05-25T16:39:35.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541136,541180,540347,TO_TIMESTAMP('2022-05-25 17:39:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Project -> M_HU',TO_TIMESTAMP('2022-05-25 17:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T16:40:31.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540668,Updated=TO_TIMESTAMP('2022-05-25 17:40:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541136
;

-- 2022-05-25T16:51:11.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540499, IsTableRecordIdTarget='Y',Updated=TO_TIMESTAMP('2022-05-25 17:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540347
;

-- 2022-05-25T16:56:29.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541585,TO_TIMESTAMP('2022-05-25 17:56:27','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','M_HU -> C_Project (Target Refference)',TO_TIMESTAMP('2022-05-25 17:56:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-05-25T16:56:29.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541585 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-05-25T16:58:12.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,549251,0,541585,540516,TO_TIMESTAMP('2022-05-25 17:58:12','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N',TO_TIMESTAMP('2022-05-25 17:58:12','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from m_hu hu where m_hu.m_hu_id = hu.m_hu_id and hu.c_project_id=@C_Project_ID/-1@)')
;

-- 2022-05-25T16:58:26.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540189,Updated=TO_TIMESTAMP('2022-05-25 17:58:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541585
;

-- 2022-05-25T16:58:32.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from m_hu hu where m_hu.m_hu_id = hu.m_hu_id and hu.c_project_id=@C_Project_ID/-1@)',Updated=TO_TIMESTAMP('2022-05-25 17:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541585
;

-- 2022-05-25T17:10:51.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541585,Updated=TO_TIMESTAMP('2022-05-25 18:10:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540347
;

-- 2022-05-25T17:12:07.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsTableRecordIdTarget='N',Updated=TO_TIMESTAMP('2022-05-25 18:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540347
;

-- 2022-05-25T17:12:21.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541427,Updated=TO_TIMESTAMP('2022-05-25 18:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540347
;

-- 2022-05-25T17:12:35.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540668,Updated=TO_TIMESTAMP('2022-05-25 18:12:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541427
;



-- 2022-05-25T17:18:22.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='', Name='C_Project->M_HU_Target_Reference',Updated=TO_TIMESTAMP('2022-05-25 18:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541585
;

-- 2022-05-25T17:18:33.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='C_Project->M_HU_Target_Reference',Updated=TO_TIMESTAMP('2022-05-25 18:18:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541585
;

-- 2022-05-25T17:18:35.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='C_Project->M_HU_Target_Reference',Updated=TO_TIMESTAMP('2022-05-25 18:18:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541585
;

-- 2022-05-25T17:18:36.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='C_Project->M_HU_Target_Reference',Updated=TO_TIMESTAMP('2022-05-25 18:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541585
;

-- 2022-05-25T17:18:40.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='C_Project->M_HU_Target_Reference',Updated=TO_TIMESTAMP('2022-05-25 18:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541585
;

-- 2022-05-25T17:20:03.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='U',Updated=TO_TIMESTAMP('2022-05-25 18:20:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540347
;

