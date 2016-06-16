-- 13.06.2016 12:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540658,TO_TIMESTAMP('2016-06-13 12:34:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','C_Period Ordered',TO_TIMESTAMP('2016-06-13 12:34:50','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 13.06.2016 12:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540658 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 13.06.2016 12:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,837,0,540658,145,TO_TIMESTAMP('2016-06-13 12:35:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N',TO_TIMESTAMP('2016-06-13 12:35:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.06.2016 12:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='C_Period_ID desc',Updated=TO_TIMESTAMP('2016-06-13 12:35:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540658
;

-- 13.06.2016 12:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 12:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540662
;

-- 13.06.2016 12:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-06-13 12:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540662
;

-- 13.06.2016 14:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 14:03:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540908
;

-- 13.06.2016 14:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2016-06-13 14:07:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540658
;

-- 13.06.2016 14:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='C_Period.StartDate desc',Updated=TO_TIMESTAMP('2016-06-13 14:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540658
;

-- 13.06.2016 14:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 14:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=434
;

-- 13.06.2016 14:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 14:13:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=435
;

-- 13.06.2016 14:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 14:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=436
;

-- 13.06.2016 14:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 14:26:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540857
;

-- 13.06.2016 14:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 14:32:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=53190
;

-- 13.06.2016 14:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 14:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=53197
;

-- 13.06.2016 14:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 14:36:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540775
;

-- 13.06.2016 14:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 14:53:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540841
;

-- 13.06.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 15:04:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540688
;

-- 13.06.2016 15:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 15:07:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540681
;

-- 13.06.2016 15:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540658,Updated=TO_TIMESTAMP('2016-06-13 15:55:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11934
;

