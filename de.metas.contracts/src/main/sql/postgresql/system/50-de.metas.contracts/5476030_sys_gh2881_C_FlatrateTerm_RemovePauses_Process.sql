-- 2017-11-01T11:08:00.238
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540879,'N','de.metas.contracts.subscription.process.C_FlatrateTerm_RemovePauses','N',TO_TIMESTAMP('2017-11-01 11:07:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','Y','N','N','N','N','Y','Abo-Lieferpause entfernen','Y','Y','Java',TO_TIMESTAMP('2017-11-01 11:07:59','YYYY-MM-DD HH24:MI:SS'),100,'C_FlatrateTerm_RemovePauses')
;

-- 2017-11-01T11:08:00.266
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540879 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-11-01T11:08:59.976
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542844,0,540879,541233,15,'DateGeneral',TO_TIMESTAMP('2017-11-01 11:08:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts',0,'Y','N','Y','N','Y','Y','Datum',10,TO_TIMESTAMP('2017-11-01 11:08:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-01T11:08:59.981
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541233 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-11-01T11:14:06.948
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540879,540320,TO_TIMESTAMP('2017-11-01 11:14:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2017-11-01 11:14:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N')
;