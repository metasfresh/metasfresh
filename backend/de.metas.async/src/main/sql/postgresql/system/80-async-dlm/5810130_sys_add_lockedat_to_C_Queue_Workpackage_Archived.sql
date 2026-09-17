-- Add the missing 'lockedat' column to dlm.C_Queue_Workpackage_Archived.
-- The polling-lock column LockedAt was added to C_Queue_Workpackage but never to its
-- archive twin, so dlm.archive_c_queue_data's "INSERT INTO ...Archived SELECT * FROM
-- (deleted C_Queue_Workpackage rows)" had one more source column than target columns and
-- aborted every run with: "INSERT has more expressions than target columns".
-- Result: async workpackage data was never archived and the C_Queue_* tables grew unbounded.
-- Mirrors the type/nullability of C_Queue_Workpackage.LockedAt (timestamptz, nullable).

ALTER TABLE IF EXISTS dlm.C_Queue_Workpackage_Archived
    ADD COLUMN IF NOT EXISTS lockedat timestamptz;
