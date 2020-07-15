-- 2019-12-04T08:08:17.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_Table WHERE AD_Reference_ID=541087
;

-- 2019-12-04T08:08:30.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541086,Updated=TO_TIMESTAMP('2019-12-04 10:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564184
;

-- 2019-12-04T08:08:44.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541087
;

-- 2019-12-04T08:08:44.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=541087
;

-- 2019-12-04T08:13:27.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=564079, AD_Table_ID=541175,Updated=TO_TIMESTAMP('2019-12-04 10:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541086
;

-- 2019-12-04T08:14:08.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Phonecall_Schedule_ID = @C_Phonecall_Schedule_ID/-1@',Updated=TO_TIMESTAMP('2019-12-04 10:14:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541086
;

-- 2019-12-04T08:17:53.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=564143, AD_Table_ID=541176,Updated=TO_TIMESTAMP('2019-12-04 10:17:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541086
;

-- 2019-12-04T08:22:41.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=564182,Updated=TO_TIMESTAMP('2019-12-04 10:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541086
;

-- 2019-12-04T08:27:47.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=564176, AD_Table_ID=541178,Updated=TO_TIMESTAMP('2019-12-04 10:27:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541086
;

-- 2019-12-04T08:29:38.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2019-12-04 10:29:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564184
;

-- 2019-12-04T08:44:50.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=564079, AD_Table_ID=541175,Updated=TO_TIMESTAMP('2019-12-04 10:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541086
;

-- 2019-12-04T08:51:04.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='',Updated=TO_TIMESTAMP('2019-12-04 10:51:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541086
;

-- 2019-12-04T08:54:23.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541086,Updated=TO_TIMESTAMP('2019-12-04 10:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564184
;

-- 2019-12-04T09:25:42.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-12-04 11:25:41','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-12-04 11:25:41','YYYY-MM-DD HH24:MI:SS'),100,541088,'T','C_Phonecall_Schedule -> C_Phonecall_Schema_Version_Line',0,'D')
;

-- 2019-12-04T09:25:42.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541088 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-12-04T09:26:08.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541088,564174,0,'Y',TO_TIMESTAMP('2019-12-04 11:26:08','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-12-04 11:26:08','YYYY-MM-DD HH24:MI:SS'),'N',540584,100,541178,0,'D')
;

-- 2019-12-04T09:26:22.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541088,Updated=TO_TIMESTAMP('2019-12-04 11:26:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564184
;

