-- VAT-ID online check: make VATaxIDStatus an actual grid column on the two core Business Partner
-- windows. Corrects 5818690 (window 123) and 5818700 (window 540354), which intended exactly this
-- but wrote the flag the WebUI does not read.
--
-- WHICH FLAG THE WEBUI READS. The WebUI builds a tab's grid layout from AD_UI_Element: it takes the
-- tab's AD_UI_Element rows that carry IsDisplayedGrid='Y' and orders the resulting columns by
-- AD_UI_Element.SeqNoGrid. AD_Field.IsDisplayedGrid and AD_Field.SeqNoGrid have no WebUI consumer at
-- all -- they are read by the generator process that seeds AD_UI_Element rows from AD_Field rows, and
-- by the legacy Swing client. So AD_Field.IsDisplayedGrid='Y' with AD_UI_Element.IsDisplayedGrid='N'
-- means: not in the grid.
--
-- The premise 5818690/5818700 argued from is therefore false, and is corrected in those files by a
-- comment-only edit (they are already applied, so the correction below is what actually changes
-- behaviour). Both scripts stated that core's own VATaxID "demonstrably does appear in the grid" on
-- these tabs and concluded that AD_Field.IsDisplayedGrid must be the effective flag. VATaxID carries
-- AD_Field.IsDisplayedGrid='Y' / AD_UI_Element.IsDisplayedGrid='N' on tabs 220, 222 and 540843, and
-- it does NOT appear in the rendered grid on any of them -- checked in the UI, not inferred. The
-- inconsistency between the two tables is pre-existing core state; VATaxID's own grid visibility is
-- NOT changed here, that is core behaviour outside the scope of this change.
--
-- SCOPE. Only VATaxIDStatus becomes a grid column. VATaxIDCheckedAt and VATaxID_CheckLog_ID stay out
-- of the grid on every tab (they keep AD_UI_Element.IsDisplayedGrid='N'): a timestamp and a zoom
-- reference cost grid width and earn nothing at a glance.
--
-- Tab 548422 "Einmaladresse" of window 123 is deliberately NOT included. The whole VAT-ID block is
-- hidden on that tab -- VATaxID itself has AD_Field.IsDisplayed='N' there, and 5818690 created the
-- three status fields hidden to match. A grid column for the status of a VAT-ID the tab does not show
-- would be inconsistent; if that tab is ever revealed, its grid flag is the same one-line change.
--
-- SeqNoGrid choice. AD_UI_Element.SeqNoGrid was 0 on all three rows, which would have placed the
-- column first. Rule applied: the status goes last among the business columns, ahead of the trailing
-- technical AD_Org_ID column where the tab has one. All slots below were confirmed free by query
-- against the grid columns each tab actually renders today:
--   tab 220    (window 123, C_BPartner)          -- ..., AD_Language 80, AD_Org_ID 90   -> 85
--   tab 222    (window 123, C_BPartner_Location) -- ..., VisitorsAddress 110, AD_Org_ID 120 -> 115
--   tab 540843 (window 540354, C_BPartner)       -- ..., URL 120, no AD_Org_ID column   -> 130
-- RESOLVED for tab 540843 -- the UPDATE below was a NO-OP and is REVERTED by 5819250.
-- This window keeps its whole VAT-ID block behind the advanced-edit toggle
-- (AD_UI_Element.IsAdvancedField='Y'), and an advanced field can NEVER become a grid column in the
-- WebUI: on the primary path that builds a grid layout from the tab's IsDisplayedGrid rows there is no
-- filter on IsAdvancedField (the zero-grid-columns fallback path does have one, but it never engages on
-- a tab that already has grid columns) -- yet the layer that serialises a view layout for the client
-- drops every advanced element, and the list-view layout has no way to ask for them. Measured against a
-- running WebUI, not inferred: with this script applied, the rendered grid of window 540354 still shows
-- its 12 pre-existing columns and no VATaxIDStatus, while windows/tabs whose element is
-- IsAdvancedField='N' (tabs 220 and 222 below) do show it. Three unrelated core windows behave the same
-- way -- 134/tab 249 (Help), 108/tab 118 (Description), 232/tab 402 (Priority, DueType, Kostenstelle)
-- each carry at least one element with IsAdvancedField='Y' AND IsDisplayedGrid='Y', and none of those
-- columns renders either. 73 such elements exist in core, so the combination is common and has always
-- been inert.
-- Resolving it required a choice outside the "make the status a grid column" decision:
--   (a) also set IsAdvancedField='N' on element 652921 -- the column then renders, but the status
--       additionally appears on this window's normal single-row form, in a section that today shows
--       nothing outside advanced edit, and it stops mirroring VATaxID's own placement here; or
--   (b) accept that this window has no status grid column while its VAT-ID lives in advanced edit,
--       and set IsDisplayedGrid back to 'N' so the metadata stops claiming a column that is not shown.
-- (b) WAS CHOSEN and is carried out by 5819250, which reverts the tab-540843 UPDATE below to
-- IsDisplayedGrid='N' / SeqNoGrid=0. Reason: this is the B2C Business Partner window, whose VAT-ID is
-- behind advanced edit precisely because private-consumer partners usually have no VAT-ID at all, so
-- (a) would spend normal-form space to surface a read-only status that is empty on most of this
-- window's records. The tab-220 and tab-222 UPDATEs below are unaffected and do render.
--
-- The one VATaxIDStatus placement that was already correct is left untouched: on the VAT-ID check-log
-- window the AD_UI_Element row was created with IsDisplayedGrid='Y' / SeqNoGrid=40 from the start.
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5819230 (this file's prefix)
-- No new AD rows are created; the AD_UI_Element_IDs updated below were allocated by 5818690/5818700.

-- tab 220 "Geschäftspartner" of window 123 -- between AD_Language (80) and AD_Org_ID (90)
UPDATE AD_UI_Element
SET IsDisplayedGrid = 'Y', SeqNoGrid = 85,
    Updated = TO_TIMESTAMP('2026-08-14 19:30:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652912;

-- tab 222 "Adresse" of window 123 -- between VisitorsAddress (110) and AD_Org_ID (120)
UPDATE AD_UI_Element
SET IsDisplayedGrid = 'Y', SeqNoGrid = 115,
    Updated = TO_TIMESTAMP('2026-08-14 19:30:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652915;

-- tab 540843 "Geschäftspartner" of window 540354 -- appended after URL (120).
-- This UPDATE is a NO-OP (see "RESOLVED for tab 540843" above) and is reverted by 5819250.
UPDATE AD_UI_Element
SET IsDisplayedGrid = 'Y', SeqNoGrid = 130,
    Updated = TO_TIMESTAMP('2026-08-14 19:30:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652921;
