-- 2018-08-07T18:15:27.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=NULL, AD_Key=11252, AD_Table_ID=688, WhereClause='exists (select 1 from AD_SchedulerLog sl   join AD_Scheduler s on sl.AD_Scheduler_ID = s.AD_Scheduler_ID  where s.AD_Scheduler_ID = AD_Scheduler.AD_Scheduler_ID and sl.AD_PInstance_ID = @AD_PInstance_ID@)',Updated=TO_TIMESTAMP('2018-08-07 18:15:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540884
;

