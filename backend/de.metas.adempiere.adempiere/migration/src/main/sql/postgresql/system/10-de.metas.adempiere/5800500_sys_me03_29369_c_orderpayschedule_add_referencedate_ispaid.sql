-- 2026-04-30 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: ReferenceDate + IsPaid columns on C_OrderPaySchedule.
--         ReferenceDate = the date used to compute DueDate (DueDate = ReferenceDate + OffsetDays).
--         NULL while the line is Pending (Status='PR'); non-null once Status transitions out of Pending.
--         IsPaid = 'Y' iff Status = 'P' (Paid). System-managed; never user-edited.
--         Status code mapping (verified against AD_Ref_List 541993): 'P'=Paid, 'PR'=Pending, 'WP'=Awaiting_Pay.
--         Both columns are derived aliases of (Status, DueDate, OffsetDays) — written exclusively
--         by OrderPayScheduleLine.applyAndProcess (the sole Status writer). Provided as separate
--         columns for SQL filtering and reporting.

/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE C_OrderPaySchedule ADD COLUMN ReferenceDate date DEFAULT NULL');
-- Add IsPaid as NOT NULL DEFAULT 'N' directly (PostgreSQL 11+ handles constant defaults efficiently).
-- Using NOT NULL + DEFAULT at ADD COLUMN time avoids the "pending trigger events" error
-- that occurs when SET NOT NULL is called after DML in the same single-transaction psql run.
/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE C_OrderPaySchedule ADD COLUMN IsPaid char(1) NOT NULL DEFAULT ''N''');
/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE C_OrderPaySchedule ADD CONSTRAINT IsPaid_check CHECK (IsPaid IN (''Y'',''N''))');

SELECT backup_table('c_orderpayschedule', '_iter3_referencedate_ispaid_bkp');

-- Backfill ReferenceDate: for non-Pending rows, ReferenceDate = DueDate - OffsetDays.
-- Pending rows (Status='PR') leave ReferenceDate NULL.
UPDATE C_OrderPaySchedule
   SET ReferenceDate = DueDate - (OffsetDays * INTERVAL '1 day')
 WHERE Status <> 'PR';

-- Backfill IsPaid: 'Y' iff Status='P' (Paid). (All rows already have 'N' from DEFAULT;
-- only Paid rows need updating to 'Y'.)
UPDATE C_OrderPaySchedule
   SET IsPaid = 'Y'
 WHERE Status = 'P';
