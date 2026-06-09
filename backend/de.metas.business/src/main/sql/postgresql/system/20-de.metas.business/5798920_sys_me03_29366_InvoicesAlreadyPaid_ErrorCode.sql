-- 2026-04-22 me03#29366 — review fix
-- AD_Message 'Invoices_already_paid' (ID 545236) has MsgType='E' (error).
-- Per metasfresh-db rules, error messages must carry an ErrorCode for programmatic handling.
-- This UPDATE must run AFTER script 5748270 (which adds AD_Message.ErrorCode column),
-- hence the separate migration script rather than appending to the original 5676790.

UPDATE AD_Message
SET ErrorCode = 'INVOICES_ALREADY_PAID',
    Updated = TO_TIMESTAMP('2026-04-22 08:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 0
WHERE AD_Message_ID = 545236
  AND ErrorCode IS NULL
;
