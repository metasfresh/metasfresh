-- VAT-ID online check: create the id sequences the two feature tables need.
--
-- WHY THIS EXISTS AT ALL. metasfresh creates a <Table>_SEQ LAZILY, at application runtime, the first time
-- a row is inserted through the model layer (PO's native-sequence path emits nextval('<table>_seq')
-- inline). So the sequence is present on any database an application has already run against, and ABSENT
-- on a freshly migrated one. That is exactly the difference between a developer's long-lived database and
-- CI's, and it is why validating the seed INSERT locally could not catch it: db-apply-migrations failed
-- with 'relation "vataxid_config_seq" does not exist' while the same statement succeeded on a dev box.
--
-- BOTH tables get their sequence here, not just the one that failed. VATaxID_CheckLog is inserted into at
-- RUNTIME by VATaxIDCheckRepository#writeRequestSent through the identical native-sequence path, so it
-- carries the same latent defect; no migration inserts into it, which is precisely why
-- db-apply-migrations cannot surface it and why it is worth closing pre-emptively rather than waiting for
-- a first real insert on a fresh installation to find it.
--
-- Everything is idempotent: IF NOT EXISTS leaves an existing sequence completely untouched (Postgres
-- no-ops the statement; it does not restart or alter), and the AD_Sequence rows are guarded by NOT EXISTS.
-- Rows an application already created drew from this same sequence, so START 1000000 cannot collide with
-- them retroactively.
--
-- Pure DDL by design: docs/coding-rules (via the metasfresh-db skill) requires schema DDL and data DML to
-- ship as separate scripts. The data seed that consumes these sequences is 5819340, which therefore runs
-- after this one.
--
-- Parameters and the paired AD_Sequence row follow the shape table-creating scripts use elsewhere and
-- match DB.createTableSequence's own defaults.
--
-- IDs from idserver.metas.de: AD_MigrationScript 5819290, AD_Sequence 556651 and 556652.

CREATE SEQUENCE IF NOT EXISTS VATaxID_Config_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

CREATE SEQUENCE IF NOT EXISTS VATaxID_CheckLog_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

INSERT INTO AD_Sequence (AD_Client_ID, AD_Org_ID, AD_Sequence_ID, Created, CreatedBy, CurrentNext,
                         CurrentNextSys, Description, IncrementNo, IsActive, IsAudited, IsAutoSequence,
                         IsTableID, Name, StartNo, Updated, UpdatedBy)
SELECT 0, 0, 556651 /*From ID Server*/,
       TO_TIMESTAMP('2026-08-15 19:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 1000000,
       50000, 'Table VATaxID_Config', 1, 'Y', 'N', 'Y',
       'Y', 'VATaxID_Config', 1000000,
       TO_TIMESTAMP('2026-08-15 19:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100
WHERE NOT EXISTS (SELECT 1 FROM AD_Sequence WHERE Name = 'VATaxID_Config')
;

-- CheckLog gets its AD_Sequence row too, not just the physical sequence. Under the default
-- native-sequence configuration DB.getNextID uses the Postgres sequence directly and this row is never
-- read — but MSequence.getNextID, the fallback whenever native sequences are switched off, looks the name
-- up in AD_Sequence and fails outright when no row matches. Shipping one without the other would leave
-- exactly the same shape of latent gap this script exists to close.
INSERT INTO AD_Sequence (AD_Client_ID, AD_Org_ID, AD_Sequence_ID, Created, CreatedBy, CurrentNext,
                         CurrentNextSys, Description, IncrementNo, IsActive, IsAudited, IsAutoSequence,
                         IsTableID, Name, StartNo, Updated, UpdatedBy)
SELECT 0, 0, 556652 /*From ID Server*/,
       TO_TIMESTAMP('2026-08-15 19:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 1000000,
       50000, 'Table VATaxID_CheckLog', 1, 'Y', 'N', 'Y',
       'Y', 'VATaxID_CheckLog', 1000000,
       TO_TIMESTAMP('2026-08-15 19:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100
WHERE NOT EXISTS (SELECT 1 FROM AD_Sequence WHERE Name = 'VATaxID_CheckLog')
;
