-- 2020-04-22T13:41:39.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541131,TO_TIMESTAMP('2020-04-22 16:41:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','Parent -> Effort issue',TO_TIMESTAMP('2020-04-22 16:41:39','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-04-22T13:41:39.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541131 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-04-22T13:43:48.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,570207,570216,0,541131,541468,540871,TO_TIMESTAMP('2020-04-22 16:43:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N',TO_TIMESTAMP('2020-04-22 16:43:48','YYYY-MM-DD HH24:MI:SS'),100,'S_Issue.IsEffortIssue=''Y''')
;

-- 2020-04-22T13:47:36.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541122,541131,540242,TO_TIMESTAMP('2020-04-22 16:47:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','Parent -> EffortIssue',TO_TIMESTAMP('2020-04-22 16:47:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-22T13:59:37.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2020-04-22 16:59:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540242
;

-- 2020-04-22T14:05:43.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2020-04-22 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570203
;

-- 2020-04-22T14:09:00.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=570203,Updated=TO_TIMESTAMP('2020-04-22 17:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541131
;

-- 2020-04-22T14:14:27.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=570203, WhereClause='S_Issue.IsEffortIssue=''Y'' and S_Issue.S_Parent_Issue_ID=@S_Issue_ID@',Updated=TO_TIMESTAMP('2020-04-22 17:14:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541131
;

-- 2020-04-22T14:59:06.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542115,53331,TO_TIMESTAMP('2020-04-22 17:59:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Effort sub-issues',TO_TIMESTAMP('2020-04-22 17:59:06','YYYY-MM-DD HH24:MI:SS'),100,'Effort sub-issues','Effort sub-issues')
;

-- 2020-04-22T14:59:06.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542115 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2020-04-22T14:59:46.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542116,53331,TO_TIMESTAMP('2020-04-22 17:59:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Budget sub-issues',TO_TIMESTAMP('2020-04-22 17:59:46','YYYY-MM-DD HH24:MI:SS'),100,'Budget sub-issues','Budget sub-issues')
;

-- 2020-04-22T14:59:46.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542116 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2020-04-22T15:00:29.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541132,TO_TIMESTAMP('2020-04-22 18:00:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','Parent => Budget Issue',TO_TIMESTAMP('2020-04-22 18:00:28','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-04-22T15:00:29.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541132 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-04-22T15:02:16.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,570203,570203,0,541132,541468,540859,TO_TIMESTAMP('2020-04-22 18:02:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N',TO_TIMESTAMP('2020-04-22 18:02:16','YYYY-MM-DD HH24:MI:SS'),100,'S_Issue.IsEffortIssue=''N'' and S_Issue.S_Parent_Issue_ID=@S_Issue_ID@')
;

-- 2020-04-22T15:18:06.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Role_Source,Updated,UpdatedBy) VALUES (0,0,541122,541132,540243,TO_TIMESTAMP('2020-04-22 18:18:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','Parent -> Budget Issue','Budget sub-issues',TO_TIMESTAMP('2020-04-22 18:18:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-22T15:18:23.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='Effort sub-issues',Updated=TO_TIMESTAMP('2020-04-22 18:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540242
;

-- 2020-04-22T15:19:07.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target='Effort sub-issues',Updated=TO_TIMESTAMP('2020-04-22 18:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540242
;

-- 2020-04-22T15:22:04.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target='Budget sub-issues',Updated=TO_TIMESTAMP('2020-04-22 18:22:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540243
;

