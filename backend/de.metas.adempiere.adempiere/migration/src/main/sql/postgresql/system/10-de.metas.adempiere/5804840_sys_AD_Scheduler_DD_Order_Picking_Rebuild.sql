-- DD_Order picking reconcile — hourly AD_Scheduler for DD_Order_Picking_Rebuild.

-- 2026-05-27T12:00:00Z
INSERT INTO AD_Scheduler (
	AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Role_ID, AD_Scheduler_ID,
	Created, CreatedBy, CronPattern, EntityType,
	Frequency, IsActive, IsIgnoreProcessingTime,
	KeepLogDays, Name, Processing,
	SchedulerProcessType, ScheduleType, Status,
	Supervisor_ID, Updated, UpdatedBy
) VALUES (
	0, 0, 585623, 0, 550124 /*From ID Server*/,
	TO_TIMESTAMP('2026-05-27 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	'0 * * * *', 'D',
	0, 'Y', 'N',
	7, 'DD_Order Picking Rebuild (hourly drift watchdog)', 'N',
	'P', 'C', 'NEW',
	100, TO_TIMESTAMP('2026-05-27 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100
);
-- NOTE: AD_Scheduler has no _Trl table in this metasfresh version — no translation rows.
