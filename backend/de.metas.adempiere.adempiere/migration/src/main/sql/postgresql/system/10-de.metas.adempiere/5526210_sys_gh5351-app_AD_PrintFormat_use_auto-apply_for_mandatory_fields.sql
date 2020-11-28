-- 2019-06-27T10:27:28.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=269,Updated=TO_TIMESTAMP('2019-06-27 10:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7023
;

-- 2019-06-27T10:31:11.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541006,TO_TIMESTAMP('2019-06-27 10:31:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_PrintPaper_Default_First',TO_TIMESTAMP('2019-06-27 10:31:11','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-06-27T10:31:11.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541006 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-06-27T10:32:14.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,Updated,UpdatedBy) VALUES (0,6997,0,541006,492,TO_TIMESTAMP('2019-06-27 10:32:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_PrintPaper.IsDefault DESC',TO_TIMESTAMP('2019-06-27 10:32:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-27T10:33:11.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541006, IsAutoApplyValidationRule='Y', TechnicalNote='I don''t believe this column is still needed, but i don''t have time right now..',Updated=TO_TIMESTAMP('2019-06-27 10:33:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7023
;

-- 2019-06-27T10:39:00.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541007,TO_TIMESTAMP('2019-06-27 10:39:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_PrintFont_ID_Default_First',TO_TIMESTAMP('2019-06-27 10:39:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-06-27T10:39:00.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541007 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-06-27T10:40:23.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,Updated,UpdatedBy) VALUES (0,6989,0,541007,491,TO_TIMESTAMP('2019-06-27 10:40:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_PrintFont.IsDefault DESC',TO_TIMESTAMP('2019-06-27 10:40:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-27T10:40:39.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541007, IsAutoApplyValidationRule='Y', TechnicalNote='I don''t believe this column is still needed, but i don''t have time right now..',Updated=TO_TIMESTAMP('2019-06-27 10:40:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7012
;

-- 2019-06-27T10:41:42.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='AD_PrintColor.IsDefault DESC',Updated=TO_TIMESTAMP('2019-06-27 10:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=266
;

-- 2019-06-27T10:41:49.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='AD_PrintColor_Default_First',Updated=TO_TIMESTAMP('2019-06-27 10:41:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=266
;

-- 2019-06-27T10:41:56.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=266, IsAutoApplyValidationRule='Y', TechnicalNote='I don''t believe this column is still needed, but i don''t have time right now..',Updated=TO_TIMESTAMP('2019-06-27 10:41:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7017
;

