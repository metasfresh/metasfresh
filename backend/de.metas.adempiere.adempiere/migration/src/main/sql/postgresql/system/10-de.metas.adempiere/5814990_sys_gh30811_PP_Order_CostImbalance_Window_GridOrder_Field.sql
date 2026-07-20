-- Mirror the CostDifference grid-column position onto the AD_Field layer.
--
-- The cost-imbalance monitor tab (AD_Tab 549352) has a persisted AD_UI_Section, so both grid
-- orderings are live: the WebUI reads AD_UI_Element.SeqNoGrid, the legacy Swing client reads
-- AD_Field.SeqNoGrid. An earlier fix moved the CostDifference AD_UI_Element (652684) to
-- SeqNoGrid=35 (right after M_Product_ID) for the WebUI, but left the sibling AD_Field (781753)
-- at 100, so the Swing client still rendered CostDifference last. Set AD_Field.SeqNoGrid=35 to
-- match (slot 35 is free on the AD_Field layer: siblings are 30=M_Product_ID, 40=QtyOrdered).

UPDATE AD_Field SET SeqNoGrid=35,
       Updated=TO_TIMESTAMP('2026-07-21 00:40:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781753
;
