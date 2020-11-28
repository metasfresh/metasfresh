
-- 04.03.2016 14:06
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540666,'Y','N',TO_TIMESTAMP('2016-03-04 14:06:09','YYYY-MM-DD HH24:MI:SS'),100,'Updates the AD_JAXRS_Endpoint records based on the available AD_JavaClass records','de.metas.jax.rs','Y','N','N','N','N','N',0,'AD_JAXRS_Endpoint_UpdateList','N','Y',0,0,'Java',TO_TIMESTAMP('2016-03-04 14:06:09','YYYY-MM-DD HH24:MI:SS'),100,'AD_JAXRS_Endpoint_UpdateList')
;

-- 04.03.2016 14:06
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540666 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 04.03.2016 14:07
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540666,540913,20,'p_alsoSyncClasses',TO_TIMESTAMP('2016-03-04 14:07:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.jax.rs',0,'Y','N','Y','N','Y','N','Also sync AD_JavaClass records',10,TO_TIMESTAMP('2016-03-04 14:07:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 14:07
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540913 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 04.03.2016 14:07
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540666,540750,TO_TIMESTAMP('2016-03-04 14:07:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y',TO_TIMESTAMP('2016-03-04 14:07:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 14:10
-- URL zum Konzept
UPDATE AD_Process SET AccessLevel='4',Updated=TO_TIMESTAMP('2016-03-04 14:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540666
;