------------------- Value: M_HU_Update_MonthsUntilStoragePeriodEnds_Attribute
-- Classname: de.metas.handlingunits.expiry.process.M_HU_Update_MonthsUntilStoragePeriodEnds_Attribute
-- 2023-02-03T09:02:04.365Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585211,'Y','de.metas.handlingunits.expiry.process.M_HU_Update_MonthsUntilStoragePeriodEnds_Attribute','N',TO_TIMESTAMP('2023-02-03 11:02:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Update Months Until Storage Ends','json','N','N','Java',TO_TIMESTAMP('2023-02-03 11:02:04','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_Update_MonthsUntilStoragePeriodEnds_Attribute')
;

-- 2023-02-03T09:02:04.367Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585211 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_HU_Update_MonthsUntilExpiry_Attribute
-- Classname: de.metas.handlingunits.expiry.process.M_HU_Update_MonthsUntilExpiry_Attribute
-- 2023-02-03T09:03:32.535Z
UPDATE AD_Process SET Name='Update Months Until Storage End Date',Updated=TO_TIMESTAMP('2023-02-03 11:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541193
;

-- 2023-02-03T09:03:32.536Z
UPDATE AD_Process_Trl trl SET Name='Update Months Until Storage End Date' WHERE AD_Process_ID=541193 AND AD_Language='de_DE'
;

-- 2023-02-03T09:04:32.411Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,541193,0,550102,TO_TIMESTAMP('2023-02-03 11:04:32','YYYY-MM-DD HH24:MI:SS'),100,'00 00 * * *','Runs at 00:00 every night to update the table Attrimute Storage End Date Attribut.','de.metas.swat',0,'D','Y','N',7,'N','Update Months Until Storage End Date','N','P','C','NEW',100,TO_TIMESTAMP('2023-02-03 11:04:32','YYYY-MM-DD HH24:MI:SS'),100)
;

