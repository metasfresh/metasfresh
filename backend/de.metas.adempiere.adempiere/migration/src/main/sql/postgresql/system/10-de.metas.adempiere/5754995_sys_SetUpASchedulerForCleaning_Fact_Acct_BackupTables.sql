-- Value: cleanup_backup_tables
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-05-16T10:20:28.300Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585472,'Y','de.metas.process.ExecuteUpdateSQL',TO_TIMESTAMP('2025-05-16 10:20:28.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','N','N','N','Y','N','N','N','N','Y','N','Y','cleanup_backup_tables','json','N','N','xls','SQL',TO_TIMESTAMP('2025-05-16 10:20:28.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'cleanup_backup_tables')
;

-- 2025-05-16T10:20:28.302Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585472 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: cleanup_backup_tables
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-05-16T10:21:01.483Z
UPDATE AD_Process SET SQLStatement='SELECT cleanup_backup_tables(''Fact_Acct'', 7);',Updated=TO_TIMESTAMP('2025-05-16 10:21:01.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585472
;

-- Value: cleanup_backup_tables
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-05-16T10:23:51.752Z
UPDATE AD_Process SET Name='Cleanup Fact_Acct backup tables',Updated=TO_TIMESTAMP('2025-05-16 10:23:51.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585472
;

-- 2025-05-16T10:23:51.754Z
UPDATE AD_Process_Trl trl SET Name='Cleanup Fact_Acct backup tables' WHERE AD_Process_ID=585472 AND AD_Language='de_CH'
;

-- Value: cleanup_backup_tables
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-05-16T10:24:08.852Z
UPDATE AD_Process SET Description='Clean backup tables for Fact_Acct older then 7 days',Updated=TO_TIMESTAMP('2025-05-16 10:24:08.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585472
;

-- 2025-05-16T10:24:08.853Z
UPDATE AD_Process_Trl trl SET Description='Clean backup tables for Fact_Acct older then 7 days' WHERE AD_Process_ID=585472 AND AD_Language='de_CH'
;

-- 2025-05-16T10:24:36.705Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,585472,0,550119,TO_TIMESTAMP('2025-05-16 10:24:36.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'0 2 * * 1,4','de.metas.sp80',0,'Y','N',7,'cleanup_backup_tables','N','P','C','NEW',100,TO_TIMESTAMP('2025-05-16 10:24:36.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-16T10:24:51.261Z
UPDATE AD_Scheduler SET Description='This process is scheduled to be run each night ',Updated=TO_TIMESTAMP('2025-05-16 10:24:51.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550119
;

-- 2025-05-16T10:25:18.312Z
UPDATE AD_Scheduler SET Description='This process is scheduled to be run every Monday and Thursday at 02:00 AM and cleans the Fac_Acct backup tables.',Updated=TO_TIMESTAMP('2025-05-16 10:25:18.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550119
;


-- 2025-05-16T11:00:42.710Z
UPDATE AD_Scheduler SET Name='Cleanup backup tables for Fact_Acct',Updated=TO_TIMESTAMP('2025-05-16 11:00:42.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550119
;

-- 2025-05-16T11:00:50.839Z
UPDATE AD_Scheduler SET Description='This process is scheduled to be run every Monday and Thursday at 02:00 AM and clean the Fac_Acct backup tables.',Updated=TO_TIMESTAMP('2025-05-16 11:00:50.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550119
;

-- 2025-05-16T11:01:20.324Z
UPDATE AD_Scheduler SET CronPattern='30 00 * * *',Updated=TO_TIMESTAMP('2025-05-16 11:01:20.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550119
;

-- 2025-05-16T11:01:39.007Z
UPDATE AD_Scheduler SET Description='Runs at 00:30 every night and cleans the Fac_Acct backup tables.',Updated=TO_TIMESTAMP('2025-05-16 11:01:39.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550119
;


-- 2025-05-16T11:02:37.308Z
UPDATE AD_Scheduler SET IsActive='N',Updated=TO_TIMESTAMP('2025-05-16 11:02:37.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550119
;


-- Value: cleanup_backup_tables
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-05-16T11:19:30.930Z
UPDATE AD_Process SET SQLStatement='SELECT cleanup_backup_tables(''Fact_Acct_Summary'', 7)
UNION
SELECT cleanup_backup_tables(''Fact_Acct_Log'', 7)
UNION
SELECT cleanup_backup_tables(''Fact_Acct_EndingBalance'', 7);',Updated=TO_TIMESTAMP('2025-05-16 11:19:30.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585472
;


-- Value: cleanup_backup_tables
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-05-16T11:20:20.384Z
UPDATE AD_Process SET Description='Clean backup tables for  Fact_Acct_Summary, Fact_Acct_Log and Fact_Acct_EndingBalance older then 7 days',Updated=TO_TIMESTAMP('2025-05-16 11:20:20.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585472
;

-- 2025-05-16T11:20:20.386Z
UPDATE AD_Process_Trl trl SET Description='Clean backup tables for  Fact_Acct_Summary, Fact_Acct_Log and Fact_Acct_EndingBalance older then 7 days' WHERE AD_Process_ID=585472 AND AD_Language='de_CH'
;

-- Value: cleanup_backup_tables
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-05-16T11:35:33.905Z
UPDATE AD_Process SET SQLStatement='SELECT cleanup_backup_tables(p_SourceTableName => ''Fact_Acct_Summary'', p_DaysToKeepBackup => 7)
UNION
SELECT cleanup_backup_tables(p_SourceTableName => ''Fact_Acct_Log'', p_DaysToKeepBackup => 7)
UNION
SELECT cleanup_backup_tables(p_SourceTableName => ''Fact_Acct_EndingBalance'', p_DaysToKeepBackup => 7);',Updated=TO_TIMESTAMP('2025-05-16 11:35:33.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585472
;
