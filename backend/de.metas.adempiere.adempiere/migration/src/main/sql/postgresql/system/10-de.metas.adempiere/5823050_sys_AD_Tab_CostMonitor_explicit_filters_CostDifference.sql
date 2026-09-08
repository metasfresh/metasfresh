-- Make CostDifference filterable on the "Kostenüberwachung Fertigung" window (AD_Window 542175,
-- AD_Tab 549352) -- and ONLY there. The filter must not appear on the "Produktionsauftrag" window.
--
-- Why this shape: filter configuration is split over two levels.
--   * AD_Tab.IncludeFiltersStrategy and AD_Field.IsFilterField are PER TAB -- safe to change here.
--   * AD_Column.IsSelectionColumn / SelectionColumnSeqNo / FilterOperator / FilterDefaultValue /
--     IsFacetFilter / IsShowFilterInline are on AD_Column and therefore SHARED by every window
--     showing PP_Order. Setting any of them would leak the filter into AD_Tab 53054
--     (Produktionsauftrag) and AD_Tab 540816. This script touches none of them.
--
-- A tab with IncludeFiltersStrategy unset defaults to 'A' (Auto), under which the filter set is
-- read from AD_Column.IsSelectionColumn. Switching this tab to 'E' (Explicit) makes the loader
-- ignore IsSelectionColumn and read AD_Field.IsFilterField instead -- so EVERY filter this tab is
-- to keep must be enumerated below, or it is silently lost. The six enumerated besides
-- CostDifference and DateFinishSchedule are exactly the ones the tab has today via Auto
-- (the PP_Order columns with IsSelectionColumn='Y'): AD_Org_ID, DocStatus, DocumentNo, IsActive,
-- M_Product_ID, M_Warehouse_ID. DateFinishSchedule is added because the tab is sorted by it
-- (AD_Field.SortNo=-1).
--
-- No filter default value is set: FilterDefaultValue lives on AD_Column and cannot be scoped to a
-- single window. The window keeps opening on every completed, not-yet-closed order. (IsActive
-- continues to carry its shared AD_Column default 'Y' -- unchanged from today.)
--
-- Consequence accepted knowingly: this tab's filter set is now hand-maintained. A PP_Order column
-- that later becomes a selection column will NOT show up here automatically.
--
-- No new AD_Element / AD_Field / AD_UI_Element rows -- nothing to translate or propagate.

UPDATE AD_Tab SET IncludeFiltersStrategy='E', -- Explicit
                  Updated=TO_TIMESTAMP('2026-09-08 09:20:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Tab_ID=549352;

-- the new filter this change is about
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781753; -- CostDifference
-- the tab is sorted by this column, so it gets a filter too
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781758; -- DateFinishSchedule
-- the six filters the tab already has today via Auto -- re-declared so Explicit does not drop them
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781752; -- M_Product_ID
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781760; -- DocStatus
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781750; -- DocumentNo
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781761; -- AD_Org_ID
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781759; -- M_Warehouse_ID
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781749; -- IsActive
