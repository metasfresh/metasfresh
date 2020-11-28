-- 04.03.2016 16:42
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,540667,'Y','de.metas.jax.rs.process.AD_JAXRS_Endpoint_ManageServerEndpoints','N',TO_TIMESTAMP('2016-03-04 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','N','N','N','N','N',0,'AD_JAXRS_Endpoint_ManageServerEndpoints','N','Y',0,0,'Java',TO_TIMESTAMP('2016-03-04 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'AD_JAXRS_Endpoint_ManageServerEndpoints')
;

-- 04.03.2016 16:42
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540667 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 04.03.2016 16:44
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540647,TO_TIMESTAMP('2016-03-04 16:44:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','N','AD_JAXRS_Endpoint_ManageServerEndpoints_Action',TO_TIMESTAMP('2016-03-04 16:44:56','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 04.03.2016 16:44
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540647 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 04.03.2016 16:45
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540647,541187,TO_TIMESTAMP('2016-03-04 16:45:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','start',TO_TIMESTAMP('2016-03-04 16:45:17','YYYY-MM-DD HH24:MI:SS'),100,'start','start')
;

-- 04.03.2016 16:45
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541187 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 04.03.2016 16:45
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540647,541188,TO_TIMESTAMP('2016-03-04 16:45:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','stop',TO_TIMESTAMP('2016-03-04 16:45:38','YYYY-MM-DD HH24:MI:SS'),100,'stop','stop')
;

-- 04.03.2016 16:45
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541188 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 04.03.2016 16:45
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='start/restart',Updated=TO_TIMESTAMP('2016-03-04 16:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541187
;

-- 04.03.2016 16:45
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541187
;

-- 04.03.2016 16:46
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,152,0,540667,540914,17,540647,'Action',TO_TIMESTAMP('2016-03-04 16:46:05','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt die durchzuführende Aktion an','de.metas.jax.rs',0,'"Aktion" ist ein Auswahlfeld, das die für diesen Vorgang durchzuführende Aktion anzeigt.','Y','N','Y','N','N','N','Aktion',10,TO_TIMESTAMP('2016-03-04 16:46:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 16:46
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540914 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 04.03.2016 16:46
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='start', IsMandatory='Y',Updated=TO_TIMESTAMP('2016-03-04 16:46:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540914
;

-- 04.03.2016 16:47
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540667,540750,TO_TIMESTAMP('2016-03-04 16:47:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y',TO_TIMESTAMP('2016-03-04 16:47:49','YYYY-MM-DD HH24:MI:SS'),100)
;
