-- 2019-11-26T17:32:31.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Role_Source,Role_Target,Updated,UpdatedBy) VALUES (0,0,540060,540059,540233,TO_TIMESTAMP('2019-11-26 19:32:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Element <-> C_Eleemnt_Value','','',TO_TIMESTAMP('2019-11-26 19:32:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-26T17:32:35.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='D',Updated=TO_TIMESTAMP('2019-11-26 19:32:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540233
;

-- 2019-11-26T17:32:54.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET EntityType='D',Updated=TO_TIMESTAMP('2019-11-26 19:32:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540761
;

-- 2019-11-26T17:32:59.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET EntityType='D',Updated=TO_TIMESTAMP('2019-11-26 19:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542127
;

-- 2019-11-26T17:33:35.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541081,TO_TIMESTAMP('2019-11-26 19:33:34','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','N','RelType C_Element -> C_Element_Value',TO_TIMESTAMP('2019-11-26 19:33:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-11-26T17:33:35.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541081 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-11-26T17:34:15.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,466,0,541081,142,TO_TIMESTAMP('2019-11-26 19:34:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2019-11-26 19:34:15','YYYY-MM-DD HH24:MI:SS'),100,'C_Element_ID =  @C_Element_ID@')
;

-- 2019-11-26T17:34:25.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541081,Updated=TO_TIMESTAMP('2019-11-26 19:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540233
;

-- 2019-11-26T17:34:50.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2019-11-26 19:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540233
;

-- 2019-11-26T17:35:14.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540761,Updated=TO_TIMESTAMP('2019-11-26 19:35:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541081
;

-- 2019-11-26T17:37:29.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=1135, AD_Key=1137, AD_Table_ID=188, IsValueDisplayed='Y',Updated=TO_TIMESTAMP('2019-11-26 19:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541081
;








-- 2019-11-27T12:38:35.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541082,TO_TIMESTAMP('2019-11-27 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','N','RelType C_Element_Value -> C_Element',TO_TIMESTAMP('2019-11-27 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-11-27T12:38:35.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541082 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-11-27T12:38:58.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,466,464,0,541082,142,TO_TIMESTAMP('2019-11-27 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2019-11-27 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T12:40:22.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Element_ID =  @C_Element_ID@',Updated=TO_TIMESTAMP('2019-11-27 14:40:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541082
;

-- 2019-11-27T12:40:38.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541082,Updated=TO_TIMESTAMP('2019-11-27 14:40:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540233
;

-- 2019-11-27T12:43:06.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541082, AD_Reference_Target_ID=541081,Updated=TO_TIMESTAMP('2019-11-27 14:43:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540233
;






----------------------


-- 2019-11-27T12:47:45.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541083,TO_TIMESTAMP('2019-11-27 14:47:45','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','N','RelType C_Element_Value -> Fact_acct',TO_TIMESTAMP('2019-11-27 14:47:45','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-11-27T12:47:45.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541083 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-11-27T12:48:41.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3001,0,541083,270,TO_TIMESTAMP('2019-11-27 14:48:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2019-11-27 14:48:41','YYYY-MM-DD HH24:MI:SS'),100,'Account_id = @C_Element_Value_ID@')
;

-- 2019-11-27T12:52:46.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Role_Source,Role_Target,Updated,UpdatedBy) VALUES (0,0,541083,541081,540234,TO_TIMESTAMP('2019-11-27 14:52:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','RelType C_Element_Value <-> Fact_acct','','',TO_TIMESTAMP('2019-11-27 14:52:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T12:55:01.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='D',Updated=TO_TIMESTAMP('2019-11-27 14:55:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540234
;

-- 2019-11-27T12:55:19.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541084,TO_TIMESTAMP('2019-11-27 14:55:18','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','N','RelType Fact_acct -> C_Element_Value',TO_TIMESTAMP('2019-11-27 14:55:18','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-11-27T12:55:19.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541084 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-11-27T12:56:17.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,1135,1125,0,541084,188,TO_TIMESTAMP('2019-11-27 14:56:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y',TO_TIMESTAMP('2019-11-27 14:56:17','YYYY-MM-DD HH24:MI:SS'),100,'C_ElementValue_ID =  @Account_ID@')
;

-- 2019-11-27T12:56:27.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='Account_id = @C_ElementValue_ID@',Updated=TO_TIMESTAMP('2019-11-27 14:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541083
;

-- 2019-11-27T12:56:48.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541084,Updated=TO_TIMESTAMP('2019-11-27 14:56:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540234
;

-- 2019-11-27T13:01:00.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='account_id = @C_ElementValue_ID@',Updated=TO_TIMESTAMP('2019-11-27 15:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541083
;

-- 2019-11-27T13:01:34.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541084, AD_Reference_Target_ID=541083,Updated=TO_TIMESTAMP('2019-11-27 15:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540234
;

-- 2019-11-27T13:02:47.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=NULL, IsValueDisplayed='N',Updated=TO_TIMESTAMP('2019-11-27 15:02:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541084
;

-- 2019-11-27T13:03:04.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540761,Updated=TO_TIMESTAMP('2019-11-27 15:03:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541084
;

-- 2019-11-27T13:04:00.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=162, WhereClause='Account_ID = @C_ElementValue_ID@',Updated=TO_TIMESTAMP('2019-11-27 15:04:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541083
;

-- 2019-11-27T13:04:30.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541083, AD_Reference_Target_ID=541084,Updated=TO_TIMESTAMP('2019-11-27 15:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540234
;

-- 2019-11-27T13:04:42.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2019-11-27 15:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540234
;

-- 2019-11-27T13:09:51.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541084,Updated=TO_TIMESTAMP('2019-11-27 15:09:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540234
;

-- 2019-11-27T13:14:28.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541084, AD_Reference_Target_ID=541083,Updated=TO_TIMESTAMP('2019-11-27 15:14:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540234
;

-- 2019-11-27T13:14:36.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='',Updated=TO_TIMESTAMP('2019-11-27 15:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541084
;



