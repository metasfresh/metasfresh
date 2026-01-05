-- Open ALL accounting periods for cucumber tests
-- This handles both 'N' (never opened) and 'C' (closed) periods
-- Must be idempotent: only updates periods that aren't already 'O' (open)

UPDATE c_periodcontrol
SET periodstatus = 'O',
    periodaction = 'N',
    updated = now(),
    updatedby = 100
WHERE periodstatus <> 'O';
