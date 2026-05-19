-- 2026-05-20 https://github.com/metasfresh/me03/issues/29369
-- Iter-3 follow-up: pair the 3 iter-3 AD_Field rows on the Order Payment Schedule
-- tab (AD_Tab_ID=548450 "Zahlungsplan" on AD_Window 541889 "Bestellung") with
-- AD_UI_Element rows so the WebUI grid actually renders them.
--
-- Background — discovered during UAT 2026-05-19/20 (manual_test_steps_documented.md
-- Step 5/6 verification): after a partial-invoice run, the BL row was correctly
-- updated in C_OrderPaySchedule (M_InOut_ID + C_Invoice_ID populated, DueAmt_Actual
-- per Class 1 fix iter-2), but the user saw "no change" in the WebUI because the
-- columns that changed are not rendered. Root cause: the iter-3 migrations
-- (5800180 + 5800500 etc.) added AD_Column + AD_Field rows for BaseAmt,
-- DueAmt_Actual, M_InOut_ID, C_Invoice_ID, ReferenceDate, IsPaid on the OPS tab
-- — but did not add the AD_UI_Element pairs. metasfresh-application-dictionary
-- skill: "every renderable AD_Field needs an AD_UI_Element row in the same
-- script. WebUI silently ignores unpaired fields."
--
-- This script ships UI elements for the 3 fields the customer explicitly needs
-- visible in the grid for the partial-invoice workflow:
--   - DueAmt_Actual (AD_Field 777250) — proves Class 1 fix iter-2 holds on BL row
--   - M_InOut_ID    (AD_Field 777442) — the linked receipt
--   - C_Invoice_ID  (AD_Field 777444) — the linked partial/final invoice
--
-- BaseAmt (777440), ReferenceDate (777477), IsPaid (777479) are intentionally
-- omitted from this script — they have AD_Field rows with IsDisplayed='Y' so
-- they show in the form view; in the grid, the legacy ReferenceDateType / DueDate
-- / IsActive columns already convey equivalent info. If the customer asks for
-- them later, a follow-up script adds the AD_UI_Element pairs analogously.
--
-- Placement: AD_UI_ElementGroup 553584 ("main" — the grid-column group).
-- SeqNoGrid chosen to slot between existing legacy columns without disrupting
-- the established layout:
--   - DueAmt_Actual at SeqNoGrid=65 → between DueAmt (60) and DueDate (70)
--   - M_InOut_ID    at SeqNoGrid=85 → between Status (80) and IsActive (90)
--   - C_Invoice_ID  at SeqNoGrid=87 → right after M_InOut_ID

INSERT INTO AD_UI_Element (
    AD_UI_Element_ID,
    AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_ElementGroup_ID, AD_Tab_ID, AD_Field_ID,
    Name, SeqNo, SeqNoGrid,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, SeqNo_SideList,
    IsAdvancedField, AD_UI_ElementType,
    IsAllowFiltering, IsMultiline
) VALUES
-- DueAmt_Actual
(651704 /*From ID Server*/,
 0, 0, 'Y',
 TO_TIMESTAMP('2026-05-20 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
 TO_TIMESTAMP('2026-05-20 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
 553584 /*main grid*/, 548450 /*Zahlungsplan*/, 777250 /*AD_Field DueAmt_Actual*/,
 'Tatsächlich fälliger Betrag', 65, 65,
 'N', 'Y', 'N', 0,
 'N', 'F',
 'N', 'N'),
-- M_InOut_ID
(651705 /*From ID Server*/,
 0, 0, 'Y',
 TO_TIMESTAMP('2026-05-20 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
 TO_TIMESTAMP('2026-05-20 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
 553584 /*main grid*/, 548450 /*Zahlungsplan*/, 777442 /*AD_Field M_InOut_ID*/,
 'Wareneingang', 85, 85,
 'N', 'Y', 'N', 0,
 'N', 'F',
 'N', 'N'),
-- C_Invoice_ID
(651706 /*From ID Server*/,
 0, 0, 'Y',
 TO_TIMESTAMP('2026-05-20 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
 TO_TIMESTAMP('2026-05-20 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
 553584 /*main grid*/, 548450 /*Zahlungsplan*/, 777444 /*AD_Field C_Invoice_ID*/,
 'Rechnung', 87, 87,
 'N', 'Y', 'N', 0,
 'N', 'F',
 'N', 'N');
