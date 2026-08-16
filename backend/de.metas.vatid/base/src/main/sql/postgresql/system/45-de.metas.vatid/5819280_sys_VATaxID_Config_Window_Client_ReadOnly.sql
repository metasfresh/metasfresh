-- VAT-ID online check: make AD_Client_ID read-only on the "USt-IdNr.-Konfiguration" window
-- (AD_Window 542182, tab 549363, table VATaxID_Config).
--
-- THE CONVENTION. Across this DB, AD_Client_ID fields are read-only: 2016 active AD_Field rows for
-- ColumnName='AD_Client_ID' carry IsReadOnly='Y' against 22 that carry 'N'. The 22 exceptions are
-- overwhelmingly recent windows that simply never set the flag (542105, 542157, 542161, 542162, 542163,
-- 542178 and this one, 542182). The client of a record is decided when the record is created, by the
-- session's tenant -- it is context, not an editable business value. This script moves 542182 onto the
-- convention. AD_UI_Element has no IsReadOnly column, so AD_Field.IsReadOnly is the flag to set.
--
-- THIS IS A REAL BEHAVIOUR FIX, NOT A COSMETIC ONE. AD_Column.IsUpdateable is already 'N' for
-- AD_Client_ID, which looks like it should be enough -- it is not. The WebUI deliberately IGNORES
-- IsUpdateable='N': GridTabVOBasedDocumentEntityDescriptorFactory.extractReadOnlyLogic (~L692) returns
-- ConstantLogicExpression.FALSE -- i.e. NOT read-only -- for a field that is !isUpdateable() and not a
-- parent link, with the comment that in Swing the flag meant "read-write until saved" and that the
-- concept does not survive the WebUI's auto-save. The only earlier branch that could make this field
-- read-only is gridFieldVO.isReadOnly() (~L679), which reads exactly the AD_Field.IsReadOnly this script
-- sets. Without it the field is editable on the rendered window.
--
-- REST OF THE WINDOW-DESIGN CHECKLIST -- verified, no change needed:
--   * "Client must NOT appear in grid views" -- AD_UI_Element 652825 already has IsDisplayedGrid='N'.
--   * "Organisation must be the last field in grid view" -- AD_Org_ID has the highest SeqNoGrid (60),
--     ahead of OnServiceUnavailable (50), RequesterNumber (47), RequesterMemberStateCode (45),
--     RecheckAfterDays (40), IsVIESCheckEnabled (30), IsFormatCheckEnabled (20), IsActive (10).
--   * "Exactly one UIStyle='primary' group per tab, in the left column" -- group 555538 in column 549617.
--   * "First group of the right column is named 'flags' and its first element is IsActive" -- group 555539
--     in column 549618, IsActive at SeqNo 10, then IsFormatCheckEnabled 20, IsVIESCheckEnabled 30.
--   * "Last group of the right column is the org/client group, AD_Org_ID first then AD_Client_ID" --
--     group 555540, AD_Org_ID SeqNo 10, AD_Client_ID SeqNo 20.
-- The window therefore already satisfies the layout cornerstones; the read-only flag was the one gap.
--
-- NOT DONE HERE, DELIBERATELY -- conditional field logic. Five fields on this tab are meaningful only
-- while IsVIESCheckEnabled='Y': RestApiBaseURL, RequesterMemberStateCode, RequesterNumber,
-- RecheckAfterDays and OnServiceUnavailable (the repository itself documents recheckAfterDays and
-- onServiceUnavailable as "unreachable while viesCheckEnabled is false"). Today all ten fields on the tab
-- carry empty DisplayLogic / ReadOnlyLogic / MandatoryLogic, so those five are shown unconditionally.
-- The window-design rules would model that with a DisplayLogic of @IsVIESCheckEnabled@='Y' on the five.
-- That is a deliberate UX change beyond "make AD_Client_ID read-only" and is left for the feature owner
-- to decide rather than folded in silently.
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5819280 (this file's prefix)
-- No new AD rows are created; AD_Field 781912 was allocated when the window was built.

UPDATE AD_Field
SET IsReadOnly = 'Y',
    Updated = TO_TIMESTAMP('2026-08-15 14:10:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781912;
