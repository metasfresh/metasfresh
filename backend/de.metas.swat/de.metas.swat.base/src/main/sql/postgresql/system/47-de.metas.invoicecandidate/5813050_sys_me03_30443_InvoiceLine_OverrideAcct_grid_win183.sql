-- me03 30443 (F01010.4 Invoice Accounting Overrides) — UAT-demo follow-up
-- Show the override-account field in the GRID of the purchase-invoice line tab.
-- Window 183 "Eingangsrechnung" (the live purchase-invoice window, C_Invoice.PO_Window_ID=183),
-- tab 291 "Rechnungsposition" (C_InvoiceLine). The override field
-- C_InvoiceLine.C_ElementValue_Override_ID (AD_Field 781215 / AD_UI_Element 652327) was
-- form-only; place it in the grid at SeqNoGrid 60, between C_Activity_ID (50) and Description (70).
-- (The former C_InvoiceLine.Account_ID grid field no longer exists — dropped as core cleanup.)
-- Grid flags are set on both the AD_Field and the AD_UI_Element layer, matching sibling grid fields.

UPDATE AD_Field
   SET IsDisplayedGrid = 'Y',
       SeqNoGrid       = 60,
       Updated         = TO_TIMESTAMP('2026-07-09 18:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',
       UpdatedBy       = 100
 WHERE AD_Field_ID = 781215
;

UPDATE AD_UI_Element
   SET IsDisplayedGrid = 'Y',
       SeqNoGrid       = 60,
       Updated         = TO_TIMESTAMP('2026-07-09 18:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',
       UpdatedBy       = 100
 WHERE AD_UI_Element_ID = 652327
;
