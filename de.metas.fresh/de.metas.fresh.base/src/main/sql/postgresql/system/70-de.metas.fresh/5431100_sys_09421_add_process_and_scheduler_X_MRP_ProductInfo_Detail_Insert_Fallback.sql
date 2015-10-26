
-- 23.10.2015 11:23
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,SQLStatement,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540618,'de.metas.adempiere.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2015-10-23 11:23:13','YYYY-MM-DD HH24:MI:SS'),100,'Inserts empty/fallback records into table X_MRP_ProductInfo_Detail_MV for a date that is 10 days from now on','de.metas.fresh','To init X_MRP_ProductInfo_Detail_MV with fallback data, you can execute
INSERT INTO X_MRP_ProductInfo_Detail_MV
SELECT * 
FROM X_MRP_ProductInfo_Detail_Fallback_V( (now() - interval ''10 days'')::date, (now() + interval ''9 days'')::date);','Y','N','N','N','N','N',0,'SQL refresh_matview for X_MRP_ProductInfo_MV','N','Y','select X_MRP_ProductInfo_Detail_Insert_Fallback( (now() + interval ''10 days'')::date);',0,0,'SQL',TO_TIMESTAMP('2015-10-23 11:23:13','YYYY-MM-DD HH24:MI:SS'),100,'X_MRP_ProductInfo_Detail_Insert_Fallback')
;

-- 23.10.2015 11:23
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540618 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 23.10.2015 11:37
-- URL zum Konzept
UPDATE AD_Process SET Name='SQL X_MRP_ProductInfo_Detail_Insert_Fallback',Updated=TO_TIMESTAMP('2015-10-23 11:37:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540618
;

-- 23.10.2015 11:37
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540618
;



-- 23.10.2015 11:23
-- URL zum Konzept
UPDATE AD_Scheduler SET CronPattern=NULL, Description='Task 08329; Obsolete as of task 09421',Updated=TO_TIMESTAMP('2015-10-23 11:23:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550022
;

-- 23.10.2015 11:27
-- URL zum Konzept
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2015-10-23 11:27:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550022
;



-- 23.10.2015 11:27
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,DateLastRun,DateNextRun,Description,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,540618,550027,TO_TIMESTAMP('2015-10-23 11:27:50','YYYY-MM-DD HH24:MI:SS'),100,'30 00 * * *',TO_TIMESTAMP('2015-10-18 19:54:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2015-10-18 20:04:14','YYYY-MM-DD HH24:MI:SS'),'Runs at 00:30 every night, to insert new empty/fallback records into X_MRP_ProductInfo_Detail_MV (not for the coming day, but for n days in future, so it''S not required to run early each day); task 09421',10,'M','Y','N',7,'N','X_MRP_ProductInfo_Detail_Insert_Fallback','N','P','C','STARTED',0,TO_TIMESTAMP('2015-10-23 11:27:50','YYYY-MM-DD HH24:MI:SS'),100)
;
