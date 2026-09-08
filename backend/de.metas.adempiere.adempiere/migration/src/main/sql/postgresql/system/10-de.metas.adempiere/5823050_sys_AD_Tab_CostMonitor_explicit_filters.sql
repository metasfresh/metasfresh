-- Switch the "Kostenüberwachung Fertigung" tab (AD_Window 542175, AD_Tab 549352) to an explicit
-- filter set, so that filters can be added to this window -- and ONLY to this window. No filter
-- added here may appear on the "Produktionsauftrag" window.
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
-- DateFinishSchedule are exactly the ones the tab has today via Auto (the PP_Order columns with
-- IsSelectionColumn='Y'): AD_Org_ID, DocStatus, DocumentNo, IsActive, M_Product_ID, M_Warehouse_ID.
-- DateFinishSchedule is added because the tab is sorted by it (AD_Field.SortNo=-1).
--
-- CostDifference is deliberately NOT among them. It is a decimal amount and its AD_Column carries no
-- FilterOperator, which the loader defaults to EQUALS -- an exact-match box on a computed decimal.
-- 'B' (Between) would fix that but lives on AD_Column, i.e. it would change Produktionsauftrag too.
-- The cost-difference filter is instead the dedicated virtual Yes/No column
-- PP_Order.HasCostDifference, added by 5823070 and placed on this tab by 5823080.
--
-- Consequence accepted knowingly: this tab's filter set is now hand-maintained. A PP_Order column
-- that later becomes a selection column will NOT show up here automatically.
--
-- No new AD_Element / AD_Field / AD_UI_Element rows -- nothing to translate or propagate.

UPDATE AD_Tab SET IncludeFiltersStrategy='E', -- Explicit
                  Updated=TO_TIMESTAMP('2026-09-08 09:20:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Tab_ID=549352;

-- the tab is sorted by this column, so it gets a filter too
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781758; -- DateFinishSchedule
-- the six filters the tab already has today via Auto -- re-declared so Explicit does not drop them
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781752; -- M_Product_ID
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781760; -- DocStatus
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781750; -- DocumentNo
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781761; -- AD_Org_ID
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781759; -- M_Warehouse_ID
UPDATE AD_Field SET IsFilterField='Y', Updated=TO_TIMESTAMP('2026-09-08 09:20:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=781749; -- IsActive
