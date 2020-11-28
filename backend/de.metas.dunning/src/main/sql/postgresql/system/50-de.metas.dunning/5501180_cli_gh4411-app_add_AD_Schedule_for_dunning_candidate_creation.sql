
--
-- insert a new AD_Scheduler to create missing dunning candidates
--
-- 2018-09-11T11:53:23.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,0,540268,1000000,550044,TO_TIMESTAMP('2018-09-11 11:53:23','YYYY-MM-DD HH24:MI:SS'),100,'15 1 * * *','Neue Mahnkandidaten restellen/ aktualisieren; Prozess läuft Nachts um 01:15 (falls aktiviert)','de.metas.swat',1,'D','Y','N',14,'N','C_Dunning_Candidate_Create','N','P','C','NEW',1000000,TO_TIMESTAMP('2018-09-11 11:53:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-11T11:54:02.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET EntityType='de.metas.dunning',Updated=TO_TIMESTAMP('2018-09-11 11:54:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550044
;

-- deactivate if by default
UPDATE AD_Scheduler SET IsActive='N' WHERE AD_Scheduler_ID=550044;

--
-- update and deactivate the existing AD_Scheduler to process dunning candidates into dunning docs
--
-- 2018-09-11T11:51:43.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET Description='Bestehnde Mahnkandidaten verarbeiten zu Mahnungen verarbeiten; Prozess läuft Nachts um 01:30 (falls aktiviert)', IsActive='N',Updated=TO_TIMESTAMP('2018-09-11 11:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550001
;

-- 2018-09-11T11:53:39.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET Description='Bestehende Mahnkandidaten zu Mahnungen verarbeiten; Prozess läuft Nachts um 01:30 (falls aktiviert)', IsActive='Y',Updated=TO_TIMESTAMP('2018-09-11 11:53:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550001
;

-- 2018-09-11T11:53:52.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='30 1 * * *',Updated=TO_TIMESTAMP('2018-09-11 11:53:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550001
;

-- 2018-09-11T11:54:14.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET IsActive='N',Updated=TO_TIMESTAMP('2018-09-11 11:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550001
;

