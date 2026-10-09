-- Daily scheduler (05:00; it should run after the instance's daily contract extension, if there is one) for the process
-- "Kompensationsgruppen-Verträge: Vertragsstatus aktualisieren" (AD_Process_ID 585687, previous migration script).
-- Client-scoped like the other contract schedulers, run with the WebUI role of that client.
--
-- IDs allocated from idserver.metas.de:
--   AD_Scheduler 550130

INSERT INTO AD_Scheduler (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Role_ID, AD_Scheduler_ID, Created, CreatedBy,
                          CronPattern, Description, EntityType, Frequency, FrequencyType, IsActive, IsIgnoreProcessingTime,
                          KeepLogDays, ManageScheduler, Name, Processing, SchedulerProcessType, ScheduleType,
                          Status, Supervisor_ID, Updated, UpdatedBy)
VALUES (1000000, 0, 585687, 540024, 550130 /*From ID Server*/,
        TO_TIMESTAMP('2026-10-09 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        '0 5 * * *', 'Aktualisiert täglich den Vertragsstatus der Kompensationsgruppen-Verträge.', 'de.metas.contracts', 0, 'D', 'Y', 'N',
        7, 'N', 'Kompensationsgruppen-Verträge: Vertragsstatus aktualisieren', 'N', 'P', 'C',
        'NEW', 100, TO_TIMESTAMP('2026-10-09 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
