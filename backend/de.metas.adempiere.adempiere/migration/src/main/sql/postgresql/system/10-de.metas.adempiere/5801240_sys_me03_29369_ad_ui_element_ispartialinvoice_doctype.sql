-- AC14.fix: pair AD_Field 777445 (IsPartialInvoice on C_DocType / tab 167) with the missing AD_UI_Element.
-- Per https://github.com/metasfresh/me03/issues/29369: migration 5800320 inserted the AD_Field row
-- (777445, "Teilrechnung", IsDisplayed=Y) but forgot the corresponding AD_UI_Element row, so the
-- field never rendered in the WebUI. This migration inserts the AD_UI_Element row pairing the field
-- with the "flags" element group (540407) on tab 167 (Belegart / C_DocType window).
--
-- UI hierarchy:  tab 167 → section 540180 → column 540253 → group 540407 (flags) → this element
-- SeqNo 160 — follows the last entry in the flags group (IsDefaultCounterDoc at 150)

-- UI Element: Belegart(167,D) -> main -> 20 -> flags.Teilrechnung
-- Column: C_DocType.IsPartialInvoice
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 777445, 0, 167, 540407 /*flags group*/, 650520 /*From ID Server*/,
        'F',
        '2026-05-06 12:00', 100, 'Y', 'N',
        'Y', 'N', 'N',
        'Teilrechnung', 160, 0, 0,
        '2026-05-06 12:00', 100);
