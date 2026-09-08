-- Give PP_Order.HasCostDifference (AD_Column 593510) an explicit position in the default filter box.
--
-- StandardDocumentFilterDescriptorsProviderFactory sorts that box by AD_Column.SelectionColumnSeqNo.
-- Left unset the column reads as 0 and ties with the other unset fields of the tab (IsActive,
-- M_Warehouse_ID, DateFinishSchedule); the stable sort then breaks the tie by field load order
-- (ORDER BY IsDisplayed DESC, SeqNo, AD_Field_ID). Because this field is IsDisplayed='N' it lands
-- after those three yet ahead of every field that DOES carry a SeqNo (M_Product_ID 30, DocStatus 60,
-- DocumentNo 70, AD_Org_ID 100) -- i.e. in the middle of the box, in an order nobody chose.
--
-- 10 makes the position CHOSEN rather than incidental, and puts it ahead of every field that carries
-- an explicit SeqNo. It does NOT lead the box: the three unset fields above still read as 0 and sort
-- first. Leading would need either a negative SeqNo -- of which the dictionary holds no example
-- (0 rows with SelectionColumnSeqNo < 0) -- or explicit SeqNos on those three, and each of them is
-- an AD_Column shared with a second window, i.e. the leak this whole design avoids. Not worth
-- inventing a precedent for filter-box ordering.
--
-- SelectionColumnSeqNo is an AD_Column-level (shared) attribute, but tab 549352 holds the only
-- AD_Field for column 593510 in the whole dictionary, so it cannot surface anywhere else -- the same
-- reasoning that makes FilterOperator/FilterDefaultValue safe on this column in 5823070.
-- Under the Auto strategy a column is only offered as a filter when IsSelectionColumn='Y', and this
-- column keeps IsSelectionColumn='N', so Produktionsauftrag (AD_Window 53009 / AD_Tab 53054) is
-- untouched either way.

-- 2026-09-08T00:00:00.000Z
UPDATE AD_Column SET SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2026-09-08 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593510
;
