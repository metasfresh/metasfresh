-- 2026-04-28 Make DateAcct (M_CostDetail.DateAcct) a date-range filter in the Cost-Detail
-- window's grid view.
--
-- Migration 5799730 surfaced DateAcct in the grid (AD_UI_Element.IsDisplayedGrid='Y'), but
-- AD_Column.IsSelectionColumn was still 'N' so the column did not appear in the filter panel.
-- For a Date column (AD_Reference_ID = 15) we also want IsRangeFilter='Y' so the WebUI
-- renders the standard from/to date-range widget.

UPDATE AD_Column
SET    IsSelectionColumn    = 'Y',
       SelectionColumnSeqNo = 15,             -- between M_Product_ID (10) and M_CostElement_ID (20)
       IsRangeFilter        = 'Y',
       Updated              = TO_TIMESTAMP('2026-04-28 06:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy            = 0
WHERE  AD_Column_ID = 584019                 -- DateAcct on M_CostDetail (AD_Table_ID = 808)
;
