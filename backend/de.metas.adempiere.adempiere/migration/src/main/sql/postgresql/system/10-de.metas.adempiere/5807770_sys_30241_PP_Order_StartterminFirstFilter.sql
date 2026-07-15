-- 30241: Produktionsauftrag — make "Geplanter Starttermin" the first date filter
--
-- Target: PP_Order grid/table filter bar (window 53009 / tab 53054 and any PP_Order grid).
--
-- Mechanism (AD_Column level, per the proven convention in 5539030 / 5539060):
--   * A column appears in the quick-filter bar when AD_Column.IsSelectionColumn = 'Y'.
--   * The ORDER of the filters follows AD_Column.SelectionColumnSeqNo (ascending; lower = earlier).
--   * Date filters render a from/to range when AD_Column.IsRangeFilter = 'Y'.
--
-- Approach: make DateStartSchedule a range selection column and give it a
-- SelectionColumnSeqNo strictly below every other date selection column on PP_Order,
-- so it is the FIRST date filter.
--
-- VERIFY ON DB before relying on this (no live DB was read while writing it):
--   * DateStartSchedule = "Geplanter Starttermin" (vs DateStart = actual "Starttermin").
--   * IsSelectionColumn / SelectionColumnSeqNo are table-level (AD_Column) → affect every
--     PP_Order grid, not only window 53009.
--   * The computed SelectionColumnSeqNo may be ≤ 0 if other date filters sit at 0; that still
--     sorts first. Replace with an explicit positive value after checking the live values if preferred.

UPDATE AD_Column
SET IsSelectionColumn    = 'Y',
    IsRangeFilter        = 'Y',
    SelectionColumnSeqNo = COALESCE((
        SELECT MIN(c2.SelectionColumnSeqNo) - 10
        FROM AD_Column c2
        WHERE c2.AD_Table_ID       = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'PP_Order')
          AND c2.IsActive          = 'Y'
          AND c2.IsSelectionColumn = 'Y'
          AND c2.AD_Reference_ID IN (15, 16)
          AND c2.ColumnName     <> 'DateStartSchedule'
    ), 10),
    Updated              = TO_TIMESTAMP('2026-06-15 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy            = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'PP_Order')
  AND ColumnName  = 'DateStartSchedule';
