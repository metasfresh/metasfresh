-- Business Partner window (AD_Window 123), tab "Kunde" (AD_Tab 223) is a grid tab: the WebUI shows only the AD_UI_Element rows with
-- IsDisplayedGrid='Y' as columns. The order type element created by 5832570 (AD_UI_Element 655461, C_BPartner.SO_DocTypeTarget_ID)
-- was placed for the form view only, so the field could not be reached in the WebUI. Show it as a grid column, between
-- "Zahlungsweise" (SeqNoGrid 20) and "Zahlungsbedingung" (SeqNoGrid 30).
--
-- Impact: AD_UI_Element.IsDisplayedGrid N -> Y and AD_UI_Element.SeqNoGrid 0 -> 25 on AD_UI_Element 655461 only.
-- NOT affected: all other AD_UI_Element rows of tab 223, AD_Field 542107 (the legacy AD_Field.IsDisplayedGrid / SeqNoGrid are not used by
--   the WebUI of a section-backed tab and stay as they are).
UPDATE AD_UI_Element
SET IsDisplayedGrid = 'Y', SeqNoGrid = 25, Updated = TO_TIMESTAMP('2026-11-02 19:20:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 655461
;
