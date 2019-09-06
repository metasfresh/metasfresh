-- 2019-09-05T14:47:50.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541033,TO_TIMESTAMP('2019-09-05 17:47:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Pricelist Version newest to oldest',TO_TIMESTAMP('2019-09-05 17:47:49','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-09-05T14:47:50.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541033 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-09-05T14:50:19.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,Updated,UpdatedBy) VALUES (0,2987,0,541033,295,TO_TIMESTAMP('2019-09-05 17:50:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','ValidFrom DESC',TO_TIMESTAMP('2019-09-05 17:50:19','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2019-09-05T14:52:13.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541033, IsUpdateable='N',Updated=TO_TIMESTAMP('2019-09-05 17:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2760
;

-- 2019-09-05T15:27:24.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='M_PriceList_Version.ValidFrom desc',Updated=TO_TIMESTAMP('2019-09-05 18:27:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541033
;

