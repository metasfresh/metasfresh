-- Surface CostDifference prominently in the cost-imbalance monitor grid.
-- The window exists to let a controller scan completed-not-closed orders for their WIP cost
-- imbalance, so the CostDifference column must sit near the front of the grid — not after the
-- quantity/date/warehouse columns. Move its grid position from SeqNoGrid=100 to 35, right after
-- M_Product_ID (30) and before QtyOrdered (40), matching its form position (4th). Form order
-- (SeqNo) and the other columns are unchanged. AD_UI_Element has no _Trl and no propagation
-- dependency, so a direct UPDATE is correct.
UPDATE AD_UI_Element SET SeqNoGrid=35,
       Updated=TO_TIMESTAMP('2026-07-20 23:49:54','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID=652684
;
