-- Follow-up to 5815550: flip the DisplayLogic on the two Factoring-config AD_Field rows.
--
-- Original placement had the fields near IsFactorer with DisplayLogic '@IsFactorer@=''Y''';
-- the domain model was corrected so FactoringContractNo + FactoringClientAccountId belong to
-- the factoring customer (IsFactoring='Y'), not the factorer. Update DisplayLogic accordingly
-- so the fields are visible on IsFactoring='Y' BPs, not IsFactorer='Y'.
--
-- AD_Field IDs from 5815550:
--   781768 → FactoringContractNo
--   781769 → FactoringClientAccountId

UPDATE AD_Field
SET DisplayLogic = '@IsFactoring@=''Y''',
    Updated = TO_TIMESTAMP('2026-07-23 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID = 781768;

UPDATE AD_Field
SET DisplayLogic = '@IsFactoring@=''Y''',
    Updated = TO_TIMESTAMP('2026-07-23 12:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID = 781769;
