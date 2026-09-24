-- Closes the round-trip hole the default alone (5826110) does not close: AD_Column.DefaultValue only
-- fires on record CREATION, never on an existing row, so an endpoint that goes
-- LOCAL_FILE -> (another transport, which clears Frequency to 0) -> LOCAL_FILE lands on 0, not 60000.
-- Gating Frequency as mandatory while TransportType=LOCAL_FILE makes that 0 unsaveable through the
-- window's normal save path (a loud validation error at enable time), mirroring LocalRootLocation's
-- own MandatoryLogic (AD_Column 593641, script 5826070).
--
-- IDs allocated from idserver.metas.de on 2026-09-24:
--   AD_MigrationScript 5826120 (this script)

UPDATE AD_Column
SET MandatoryLogic = '@TransportType/X@=''LOCAL_FILE''',
    Updated         = TO_TIMESTAMP('2026-09-24 09:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy       = 100
WHERE AD_Column_ID = 593642;
