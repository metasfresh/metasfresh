-- Nightly AD_Scheduler for the existing C_BPartner_VATaxID_Check process (AD_Process_ID 585650,
-- migration 5818940). No new AD_Process: this wires the SAME process a user runs by hand to a daily
-- schedule, per DESIGN.md §5 ("One AD_Process ... the nightly schedule is the same process with
-- different selection parameters"). Invoked with no selection at all -- the process branches on that
-- (see C_BPartner_VATaxID_Check#doIt) to cover every VAT-ID system-wide instead of a user's selection.

-- IDs allocated from idserver.metas.de:
--   AD_Scheduler       550129
--   AD_Scheduler_Para  540056

-- --- AD_Scheduler (daily, 03:00) ---
INSERT INTO AD_Scheduler (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Role_ID, AD_Scheduler_ID, Created, CreatedBy,
                           CronPattern, EntityType, Frequency, FrequencyType, IsActive, IsIgnoreProcessingTime,
                           KeepLogDays, ManageScheduler, Name, Processing, SchedulerProcessType, ScheduleType,
                           Status, Supervisor_ID, Updated, UpdatedBy)
VALUES (0, 0, 585650, 0, 550129 /*From ID Server*/,
        TO_TIMESTAMP('2026-08-14 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        '0 3 * * *', 'D', 0, 'D', 'Y', 'N',
        7, 'N', 'VAT-ID check scheduler', 'N', 'P', 'C',
        'NEW', 100, TO_TIMESTAMP('2026-08-14 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- --- AD_Scheduler_Para (MaxChecksPerRun default 500, per DESIGN.md §5) ---
INSERT INTO AD_Scheduler_Para (AD_Client_ID, AD_Org_ID, AD_Process_Para_ID, AD_Scheduler_ID, AD_Scheduler_Para_ID,
                                Created, CreatedBy, IsActive, ParameterDefault, Updated, UpdatedBy)
VALUES (0, 0, 543273, 550129, 540056 /*From ID Server*/,
        TO_TIMESTAMP('2026-08-14 08:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Y', '500', TO_TIMESTAMP('2026-08-14 08:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100);
