-- Revert 5815690: the domain model was corrected AGAIN. FactoringContractNo +
-- FactoringClientAccountId belong to the FACTORER BPartner (IsFactorer='Y', unique per org),
-- not to the factoring customer. The two config fields are per-org tenant configuration used
-- in the header row of the export. Flip the AD_Field DisplayLogic back to '@IsFactorer@=''Y'''.
--
-- FINAL AUTHORITATIVE DECISION (settled 2026-07-23): the visibility of both fields is
-- @IsFactorer@='Y'. Migration history for this branch flipped twice (5815550 → 5815690 →
-- 5815770) while the domain model was clarified; the sequence is preserved on the branch as
-- audit trail. Any future change to visibility should REPLACE these values on a fresh
-- migration script; do NOT re-flip via yet another follow-up.
--
-- AD_Field IDs from 5815550:
--   781768 → FactoringContractNo
--   781769 → FactoringClientAccountId

UPDATE AD_Field
SET DisplayLogic = '@IsFactorer@=''Y''',
    Updated = TO_TIMESTAMP('2026-07-23 14:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID = 781768;

UPDATE AD_Field
SET DisplayLogic = '@IsFactorer@=''Y''',
    Updated = TO_TIMESTAMP('2026-07-23 14:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID = 781769;
