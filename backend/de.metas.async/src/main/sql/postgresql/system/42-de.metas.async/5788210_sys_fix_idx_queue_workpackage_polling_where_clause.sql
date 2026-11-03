-- Fix: The partial index idx_queue_workpackage_polling was created with WHERE Processed = 'Y'
-- by migration 5777120, but it should be WHERE Processed = 'N' (unprocessed records only).
-- This script drops and recreates the index with the correct WHERE clause.
-- See also: 5777120 migration which originally created the index.

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
    'Optimized index for workpackage polling with FOR UPDATE SKIP LOCKED. Column order: C_Queue_PackageProcessor_ID first (most selective), then ready/error/locked filters, then Priority/ID for sorting. Partial index on unprocessed records only = smaller, faster.';
