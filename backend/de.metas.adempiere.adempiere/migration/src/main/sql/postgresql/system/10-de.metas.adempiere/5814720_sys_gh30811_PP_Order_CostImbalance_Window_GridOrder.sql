-- CostDifference is the column the cost-imbalance monitor exists for, so it belongs near the front of
-- the grid: move it from SeqNoGrid=100 to 35, between M_Product_ID (30) and QtyOrdered (40). Form order
-- and the other columns are unchanged.
UPDATE AD_UI_Element SET SeqNoGrid=35,
       Updated=TO_TIMESTAMP('2026-07-20 23:49:54','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID=652684
;
