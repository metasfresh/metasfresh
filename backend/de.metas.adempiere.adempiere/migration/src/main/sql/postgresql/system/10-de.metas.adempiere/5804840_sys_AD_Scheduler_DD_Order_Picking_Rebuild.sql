-- DD_Order picking reconcile — hourly AD_Scheduler for DD_Order_Picking_Rebuild
-- me03 #29966

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

-- 2026-05-27T12:00:00Z
INSERT INTO AD_Scheduler_Trl (
	AD_Language, AD_Scheduler_ID, Description, Help, Name,
	IsTranslated, AD_Client_ID, AD_Org_ID,
	Created, Createdby, Updated, UpdatedBy
) SELECT
	l.AD_Language, t.AD_Scheduler_ID, t.Description, t.Help, t.Name,
	'N', t.AD_Client_ID, t.AD_Org_ID,
	t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Scheduler t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
	AND t.AD_Scheduler_ID=550124
	AND NOT EXISTS (
		SELECT 1 FROM AD_Scheduler_Trl tt
		WHERE tt.AD_Language=l.AD_Language AND tt.AD_Scheduler_ID=t.AD_Scheduler_ID
	);
