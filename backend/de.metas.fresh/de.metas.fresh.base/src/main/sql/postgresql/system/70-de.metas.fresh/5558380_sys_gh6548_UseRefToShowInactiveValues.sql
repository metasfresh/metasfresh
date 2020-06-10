-- 2020-04-30T10:39:42.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541138,TO_TIMESTAMP('2020-04-30 13:39:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Product(including inactive values)',TO_TIMESTAMP('2020-04-30 13:39:42','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-04-30T10:39:42.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541138 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-04-30T10:40:41.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1402,0,541138,208,TO_TIMESTAMP('2020-04-30 13:40:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Name','Y',TO_TIMESTAMP('2020-04-30 13:40:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-30T10:40:57.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=30, AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-04-30 13:40:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540678
;

-- 2020-04-30T11:06:11.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='',Updated=TO_TIMESTAMP('2020-04-30 14:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541138
;







-- 2020-05-04T08:31:36.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=30, AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 11:31:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541060
;

-- 2020-05-04T08:31:48.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 11:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540947
;

-- 2020-05-04T08:31:56.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 11:31:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541074
;



-- 2020-05-04T09:01:06.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 12:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540692
;

-- 2020-05-04T09:01:20.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 12:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540999
;

-- 2020-05-04T09:01:26.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 12:01:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540989
;

-- 2020-05-04T09:01:34.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 12:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540685
;

-- 2020-05-04T09:01:45.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 12:01:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540778
;

-- 2020-05-04T09:01:52.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 12:01:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541092
;

-- 2020-05-04T09:02:07.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 12:02:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541083
;

-- 2020-05-04T09:03:28.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541138,Updated=TO_TIMESTAMP('2020-05-04 12:03:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541702
;

