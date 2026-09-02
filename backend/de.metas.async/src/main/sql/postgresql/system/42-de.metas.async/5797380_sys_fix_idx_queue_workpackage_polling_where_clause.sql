-- Fix: The original migration script 5777120 created idx_queue_workpackage_polling
-- with WHERE Processed = 'Y' instead of WHERE Processed = 'N'.
-- On databases where the original script already ran, the wrong index exists.
-- This script drops and recreates the index with the correct WHERE clause.

DROP INDEX IF EXISTS idx_queue_workpackage_polling;

CREATE INDEX idx_queue_workpackage_polling
    ON C_Queue_WorkPackage (
                            C_Queue_PackageProcessor_ID,
                            IsReadyForProcessing,
                            IsError,
                            LockedAt,
                            Priority,
                            C_Queue_WorkPackage_ID
        )
    WHERE Processed = 'N';

COMMENT ON INDEX idx_queue_workpackage_polling IS
    'Optimized index for workpackage polling with FOR UPDATE SKIP LOCKED. Partial index on unprocessed records (Processed=N). Column order: C_Queue_PackageProcessor_ID first (most selective), then ready/error/locked filters, then Priority/ID for sorting.';
