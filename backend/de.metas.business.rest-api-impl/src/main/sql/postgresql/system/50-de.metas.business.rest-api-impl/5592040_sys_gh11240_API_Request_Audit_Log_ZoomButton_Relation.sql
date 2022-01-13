-- 2021-06-10T14:50:53.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=28, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2021-06-10 17:50:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574240
;

-- 2021-06-10T14:51:03.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_request_audit_log','Record_ID','NUMERIC(10)',null,null)
;

-- 2021-06-10T14:57:08.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541331,TO_TIMESTAMP('2021-06-10 17:57:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','API_Request_Audit_Log',TO_TIMESTAMP('2021-06-10 17:57:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-06-10T14:57:08.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541331 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-06-10T14:57:35.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,573787,0,541331,541639,TO_TIMESTAMP('2021-06-10 17:57:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-06-10 17:57:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T14:58:22.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=541127,Updated=TO_TIMESTAMP('2021-06-10 17:58:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541639
;

-- 2021-06-10T15:00:53.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541331,540286,TO_TIMESTAMP('2021-06-10 18:00:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Y','API_Request_Audit_Log_TableRecordIdTarget',TO_TIMESTAMP('2021-06-10 18:00:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T15:10:25.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2021-06-10 18:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540286
;

