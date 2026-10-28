-- #############################################################################
-- STEP-0 endpoint-model drift-fix (folded into the R1 dir-rename effort, per
-- SESSION_HANDOFF.md "DEFERRED (human)": I_/X_ExternalSystem_Endpoint
-- TransportType/SftpAuthType AD_Reference_ID + AuthType/Type mandatory flips).
--
-- Both parts were re-verified against the faithful DB before writing this
-- script (this issue has produced false-positive migration findings before --
-- never assert an ID missing/orphaned without a DB query):
--
-- (a) TransportType/SftpAuthType AD_Reference_ID + AuthType/Type mandatory
--     flips -- VERIFIED NO DRIFT. The live AD_Column rows for TransportType/
--     SftpAuthType/AuthType/Type (AD_Table_ID 542551) already match the
--     checked-in I_/X_ExternalSystem_Endpoint.java exactly: same
--     AD_Reference_ID(17)/AD_Reference_Value_ID (542077/542078/542017/542016)
--     and the same IsMandatory <-> PG NOT NULL state on every one of the four
--     columns (TransportType mandatory+NOT NULL; the other three nullable).
--     Nothing to fix -- this part of the deferred finding is a false positive.
--
-- (b) SshPrivateKey (AD_Column_ID 592247) IsEncrypted='Y' -- GENUINE DRIFT,
--     found while regenerating I_/X_ExternalSystem_Endpoint for the R1 dir
--     rename (migration 5815250): GenerateModel's ModelClassGenerator emits
--     set_ValueE(...)/set_ValueNoCheckE(...) for any IsEncrypted='Y' column,
--     but org.compiere.model.PO never declared those methods (confirmed via
--     `git log -S"set_ValueE" -- .../org/compiere/model/PO.java` -- zero
--     history), so an honest regeneration of this table fails to compile
--     ("cannot find symbol: method set_ValueE"). The committed generated file
--     compiled only because a prior session had hand-edited it back to
--     set_Value (a forbidden I_*/X_* hand-edit -- see the STEP-0 PR-review
--     note about a "hand-corrected set_ValueE" claim). Every sibling
--     secret-like column on the SAME table -- Password, ClientSecret,
--     AuthToken -- is IsEncrypted='N' and correctly generates set_Value(...);
--     SshPrivateKey is the ONLY column in the entire workspace with
--     IsEncrypted='Y' hitting this code path (grep across all java-gen/
--     confirms zero other files reference set_ValueE/set_ValueNoCheckE). The
--     physical column is plain `text` (no pgcrypto/bytea backing) -- the flag
--     has no working functional effect today, only a broken generator branch.
--     Root-cause fix: align SshPrivateKey with its sibling secret columns
--     (IsEncrypted='N'), instead of hand-editing the generated file (forbidden)
--     or changing the shared ModelClassGenerator.java (out of this task's
--     scope; would affect every other IsEncrypted column in the dictionary).
-- #############################################################################

UPDATE AD_Column
SET IsEncrypted = 'N',
    Updated = TO_TIMESTAMP('2026-07-21 20:05:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 592247;
