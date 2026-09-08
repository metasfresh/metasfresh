-- Switch the "Kostenüberwachung Fertigung" tab (AD_Window 542175, AD_Tab 549352) to an explicit
-- filter set, so that filters can be added to this window -- and ONLY to this window. No filter
-- added here may appear on the "Produktionsauftrag" window.
--
-- Only AD_Tab.IncludeFiltersStrategy and AD_Field.IsFilterField are per tab, hence safe to set here.
-- The AD_Column-level filter attributes (IsSelectionColumn, SelectionColumnSeqNo, FilterOperator,
-- FilterDefaultValue, IsFacetFilter, IsShowFilterInline) are shared by every window showing PP_Order;
-- this script touches none of them, and must keep it that way.
--
-- Under 'E' (Explicit) the tab's filter set is exactly the AD_Fields flagged IsFilterField, so every
-- filter this tab is to keep must be enumerated below or it is silently lost -- and a PP_Order column
-- that later becomes a selection column will no longer show up here on its own.

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
