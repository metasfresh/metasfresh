-- ProcessedDirectory (AD_UI_Element 652677) and ErrorDirectory (AD_UI_Element 652678) were
-- relabeled/redescribed as transport-agnostic (used by BOTH SFTP and REST scripted import) by
-- 5815250, which also removed their SFTP-only AD_Field.DisplayLogic so both fields render
-- regardless of TransportType. That migration left the two AD_UI_Element rows in place inside
-- AD_UI_ElementGroup 554996 ("SFTP", under the "Transport" section's SFTP-transport column),
-- whose sibling elements are all DisplayLogic-gated to TransportType='SFTP'. Since these two
-- fields now show unconditionally, an HTTP-only admin still sees them rendered under the "SFTP"
-- group header -- misleading grouping, even though the fields themselves are correctly visible.
--
-- Fix: relocate both AD_UI_Element rows into AD_UI_ElementGroup 553738 ("main"), the tab's
-- transport-agnostic group that already hosts the ungated TransportType field (AD_UI_Element
-- 648566) alongside Value/Type/HttpEndPoint/OutboundHttpMethod/AuthType/Password. Appended at
-- the end of that group's SeqNo range (existing max = 100 for Password).
--
-- No new AD_Element/AD_Column/AD_Field/AD_UI_Element IDs needed -- pure relocation of the two
-- existing rows. Plain UPDATE (not an upsert), per the "re-placement is a plain UPDATE" pattern.
--
--   AD_MigrationScript 5815290 (from idserver.metas.de, 2026-07-21)

UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 553738,
    SeqNo = 110,
    Updated = TO_TIMESTAMP('2026-07-21 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652677;

UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 553738,
    SeqNo = 120,
    Updated = TO_TIMESTAMP('2026-07-21 21:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652678;
