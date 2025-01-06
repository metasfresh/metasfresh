-- 2024-05-22T14:52:23.492Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,585392,0,550115,TO_TIMESTAMP('2024-05-22 14:52:23.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'50 01 * * *','Runs at 01:50 every night to update the purchase_order_highestprice_per_day_mv table','de.metas.swat',0,'D','Y','N',7,'N','update_fresh_statistics_kg_week','N','P','C','NEW',100,TO_TIMESTAMP('2024-05-22 14:52:23.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-05-22T14:52:55.759Z
UPDATE AD_Scheduler SET EntityType='D', Name='Update purchase_order_highestprice_per_day_mv',Updated=TO_TIMESTAMP('2024-05-22 14:52:55.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550115
;

