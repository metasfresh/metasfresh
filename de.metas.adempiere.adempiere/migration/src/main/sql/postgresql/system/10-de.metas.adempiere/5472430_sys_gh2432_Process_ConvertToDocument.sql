-- 2017-09-22T18:10:57.286
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,540837,'N','org.adempiere.ad.table.process.AD_Table_ConvertToDocument','N',TO_TIMESTAMP('2017-09-22 18:10:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y',0,'Convert Table To Document','N','Y','Java',TO_TIMESTAMP('2017-09-22 18:10:57','YYYY-MM-DD HH24:MI:SS'),100,'AD_Table_ConvertToDocument')
;

-- 2017-09-22T18:10:57.296
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540837 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-09-22T18:12:09.513
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540837,100,100,TO_TIMESTAMP('2017-09-22 18:12:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2017-09-22 18:12:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N')
;
