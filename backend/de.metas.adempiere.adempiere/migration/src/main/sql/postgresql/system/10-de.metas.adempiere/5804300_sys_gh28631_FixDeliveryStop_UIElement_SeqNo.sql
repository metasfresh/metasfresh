-- gh#28631: Fix the SeqNo of the two new BPartner-window AD_UI_Element rows created by 5793400.
--
-- The original 5793400 placed Liefer-/Auftragssperre (648530) at SeqNo=100 and
-- Lieferstopp Grund (648531) at SeqNo=110 inside AD_UI_ElementGroup 540671
-- ("advanced edit") on AD_Tab 220 of AD_Window 123. Pre-existing UI elements
-- `freight cost id` (560118) and `shipper` (560119) already occupy those same
-- SeqNos. Render order with ties is undefined, so the rendered "Erweiterte Erfassung"
-- panel interleaves the new fields with unrelated freight/shipper fields
-- (Frachtkostenpauschale → Liefer-/Auftragssperre → Lieferweg → Lieferstopp Grund).
--
-- Fix: move both new fields just BEFORE `Frachtkostenpauschale` (SeqNo=100), into
-- the gap above it. Final order in the group: Sonstiges (90) → Liefer-/Auftragssperre
-- (95) → Lieferstopp Grund (96) → Frachtkostenpauschale (100) → Lieferweg (110) → …

UPDATE AD_UI_Element
SET SeqNo = 95,
    Updated = TO_TIMESTAMP('2026-05-24 11:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 648530;

UPDATE AD_UI_Element
SET SeqNo = 96,
    Updated = TO_TIMESTAMP('2026-05-24 11:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 648531;
