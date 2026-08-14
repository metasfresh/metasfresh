-- Nightly AD_Scheduler for the existing C_BPartner_VATaxID_Check process (AD_Process_ID 585650,
-- migration 5818940). No new AD_Process: this wires the SAME process a user runs by hand to a daily
-- schedule, the same way the manual run works, just with different selection parameters. Invoked with
-- no selection at all -- the process branches on that (see C_BPartner_VATaxID_Check#doIt) to cover every
-- due VAT-ID for this client instead of a user's selection.
--
-- Client-scoped (AD_Client_ID=1000000), not System (0): VATaxID_Config and every VAT-ID-bearing
-- C_BPartner/C_BPartner_Location live at this client, so the scheduler that checks them belongs to it
-- too, matching the precedent set by other per-client AD_Scheduler rows (e.g. migration 5586040). The
-- AD_Process definition itself stays System-level (like most AD_Process rows), shared across clients.
--
-- AD_Role_ID=540024 (WebUI, client 1000000): same precedent migration 5586040 pins a real role rather
-- than leaving it 0, and the cucumber test that exercises this exact "no selection" branch resolves the
-- same WebUI role to simulate the scheduled run -- the shipped scheduler and the test now agree on which
-- role actually runs the process.

-- IDs allocated from idserver.metas.de:
--   AD_Scheduler       550129
--   AD_Scheduler_Para  540056

-- --- AD_Scheduler (daily, 03:00) ---
INSERT INTO AD_Scheduler (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Role_ID, AD_Scheduler_ID, Created, CreatedBy,
                           CronPattern, EntityType, Frequency, FrequencyType, IsActive, IsIgnoreProcessingTime,
                           KeepLogDays, ManageScheduler, Name, Processing, SchedulerProcessType, ScheduleType,
                           Status, Supervisor_ID, Updated, UpdatedBy)
VALUES (1000000, 0, 585650, 540024, 550129 /*From ID Server*/,
        TO_TIMESTAMP('2026-08-14 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        '0 3 * * *', 'D', 0, 'D', 'Y', 'N',
        7, 'N', 'VAT-ID check scheduler', 'N', 'P', 'C',
        'NEW', 100, TO_TIMESTAMP('2026-08-14 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- --- AD_Scheduler_Para (MaxChecksPerRun default 500) ---
INSERT INTO AD_Scheduler_Para (AD_Client_ID, AD_Org_ID, AD_Process_Para_ID, AD_Scheduler_ID, AD_Scheduler_Para_ID,
                                Created, CreatedBy, IsActive, ParameterDefault, Updated, UpdatedBy)
VALUES (1000000, 0, 543273, 550129, 540056 /*From ID Server*/,
        TO_TIMESTAMP('2026-08-14 08:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Y', '500', TO_TIMESTAMP('2026-08-14 08:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100);
