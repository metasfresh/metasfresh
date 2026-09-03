-- Task Q14 fix round (delivery planning quantities): the two new AD_Field rows on tab 546736
-- ("Versandpaket") must follow the tab's own convention and carry NO AD_Field ordering.
--
-- 5822250 inserted them with SeqNo=10 on BOTH rows and SeqNoGrid=45/55, while all 23 pre-existing fields
-- of that tab carry NULL for both (verified on the local stack): ordering on this tab lives entirely in
-- AD_UI_Element, where 5822250 already placed the two figures correctly
-- (...Batch(40), PlannedLoadedQuantity(45), ActualLoadQty(50), PlannedDischargeQuantity(55),
-- ActualDischargeQuantity(60), C_UOM_ID(70)).
--
-- Left as-is it is a metadata split-brain: any consumer that orders a tab's fields by AD_Field.SeqNo
-- (the legacy GridTabVO field loading, an export of the tab definition) sees two fields TIE at 10 ahead
-- of 23 NULLs and orders them arbitrarily between runs, while the WebUI shows the AD_UI_Element order.
-- Clearing both columns removes the second, contradictory ordering rather than trying to keep the two in
-- step. The WebUI order is unaffected - it never reads these two columns for this tab.

UPDATE AD_Field
SET SeqNo     = NULL,
    SeqNoGrid = NULL,
    Updated   = TO_TIMESTAMP('2026-09-03 11:25:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID IN (784915, 784916);
