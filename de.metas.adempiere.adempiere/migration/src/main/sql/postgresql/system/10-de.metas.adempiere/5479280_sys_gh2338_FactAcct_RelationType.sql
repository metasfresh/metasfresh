-- 2017-12-04T14:44:21.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540773,TO_TIMESTAMP('2017-12-04 14:44:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Fact_Acct',TO_TIMESTAMP('2017-12-04 14:44:20','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-12-04T14:44:21.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540773 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-12-04T14:47:15.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3001,3001,0,540773,270,TO_TIMESTAMP('2017-12-04 14:47:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2017-12-04 14:47:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-04T14:47:39.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=162,Updated=TO_TIMESTAMP('2017-12-04 14:47:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540773
;

-- 2017-12-04T14:47:58.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540773,540201,TO_TIMESTAMP('2017-12-04 14:47:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Y','Fact_Acct_TableRecordIdTarget',TO_TIMESTAMP('2017-12-04 14:47:58','YYYY-MM-DD HH24:MI:SS'),100)
;

