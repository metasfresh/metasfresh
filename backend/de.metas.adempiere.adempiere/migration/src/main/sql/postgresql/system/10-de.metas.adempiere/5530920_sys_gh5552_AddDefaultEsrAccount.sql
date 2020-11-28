-- 2019-09-18T14:24:20.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541044,TO_TIMESTAMP('2019-09-18 17:24:20','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','C_BP_BankAccount_esr',TO_TIMESTAMP('2019-09-18 17:24:20','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-09-18T14:24:20.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541044 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-09-18T14:24:56.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,Updated,UpdatedBy) VALUES (0,3105,3094,0,541044,298,TO_TIMESTAMP('2019-09-18 17:24:56','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','IsDefaultEsr DESC',TO_TIMESTAMP('2019-09-18 17:24:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T14:25:07.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541044, DefaultValue='',Updated=TO_TIMESTAMP('2019-09-18 17:25:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548231
;



-- 2019-09-18T14:51:55.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=NULL,Updated=TO_TIMESTAMP('2019-09-18 17:51:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541044
;

