-- 2020-04-27T18:33:32.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541133,TO_TIMESTAMP('2020-04-27 21:33:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','Issue -> Parent Effort ',TO_TIMESTAMP('2020-04-27 21:33:31','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-04-27T18:33:32.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541133 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-04-27T18:35:46.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,570203,570203,0,541133,541468,540871,TO_TIMESTAMP('2020-04-27 21:35:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N',TO_TIMESTAMP('2020-04-27 21:35:46','YYYY-MM-DD HH24:MI:SS'),100,'S_Issue.S_Issue_ID=@S_Parent_Issue_ID@ and S_Issue.isEffortIssue=''Y''')
;

-- 2020-04-27T18:36:12.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541134,TO_TIMESTAMP('2020-04-27 21:36:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','Issue -> Parent Budget ',TO_TIMESTAMP('2020-04-27 21:36:12','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-04-27T18:36:12.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541134 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-04-27T18:37:16.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,570203,570203,0,541134,541468,540859,TO_TIMESTAMP('2020-04-27 21:37:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N',TO_TIMESTAMP('2020-04-27 21:37:16','YYYY-MM-DD HH24:MI:SS'),100,'S_Issue.S_Issue_ID=@S_Parent_Issue_ID@ and S_Issue.isEffortIssue-''N''')
;

-- 2020-04-27T18:40:27.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541122,541133,540244,TO_TIMESTAMP('2020-04-27 21:40:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','Issue -> Parent Effort',TO_TIMESTAMP('2020-04-27 21:40:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-27T18:40:58.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541122,541134,540245,TO_TIMESTAMP('2020-04-27 21:40:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Y','N','Issue -> Parent Budget',TO_TIMESTAMP('2020-04-27 21:40:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-27T18:41:08.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.serviceprovider',Updated=TO_TIMESTAMP('2020-04-27 21:41:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540244
;

-- 2020-04-27T18:43:13.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2020-04-27 21:43:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570216
;

-- 2020-04-27T18:45:43.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='S_Issue.S_Issue_ID=@S_Parent_Issue_ID@ and S_Issue.isEffortIssue=''N''',Updated=TO_TIMESTAMP('2020-04-27 21:45:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541134
;

-- 2020-04-27T18:50:48.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542117,53331,TO_TIMESTAMP('2020-04-27 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Parent effort issue',TO_TIMESTAMP('2020-04-27 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Parent effort issue','Parent effort issue')
;

-- 2020-04-27T18:50:48.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542117 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2020-04-27T18:51:04.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542118,53331,TO_TIMESTAMP('2020-04-27 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Parent budget issue',TO_TIMESTAMP('2020-04-27 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Parent budget issue','Parent budget issue')
;

-- 2020-04-27T18:51:04.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542118 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2020-04-27T18:52:23.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='Parent budget issue', Role_Target='Parent budget issue',Updated=TO_TIMESTAMP('2020-04-27 21:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540245
;

-- 2020-04-27T18:52:40.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='Parent effort issue', Role_Target='Parent effort issue',Updated=TO_TIMESTAMP('2020-04-27 21:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540244
;

