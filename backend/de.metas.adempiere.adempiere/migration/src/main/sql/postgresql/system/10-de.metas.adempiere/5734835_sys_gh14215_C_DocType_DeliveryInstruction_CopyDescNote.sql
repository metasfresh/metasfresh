-- Fixes failure of: 5671440_sys_gh14215_C_DocType_DeliveryInstruction.sql
-- Sets the Delivery Instruction C_DocType (541085) copy-description flag.
--
-- Deferred here from 5671440 so the DP port applies on BOTH substrates:
--   * CICD fresh in-order build: at 5671440 only the OLD column IsCopyDescriptionToDocument exists
--     (core transform 5734830/5734831/5734832 runs later); 5671440 omits the copy-desc column, so the
--     old column takes its default 'Y', which 5734831 converts to CopyDescriptionAndDocumentNote='CD'.
--   * Instance rollout (transform already applied): at 5671440 the old column is already dropped and only
--     the (nullable) new column exists; 5671440's omit is safe, and this script sets the final value.
--
-- Placed after 5734832 (sub-slot 5) because that script DROPs IsCopyDescriptionToDocument on both
-- substrates -- so the WHERE keys on C_DocType_ID, not the now-nonexistent old column.
select backup_table('c_doctype');
UPDATE C_DocType SET CopyDescriptionAndDocumentNote='CD',Updated=TO_TIMESTAMP('2026-08-10 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541085
;
