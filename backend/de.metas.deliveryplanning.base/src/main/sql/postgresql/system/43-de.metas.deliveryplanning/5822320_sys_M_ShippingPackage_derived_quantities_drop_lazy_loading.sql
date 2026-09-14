-- Drop IsLazyLoading on the four M_ShippingPackage virtual quantity columns that read the parent
-- M_Delivery_Planning through M_Delivery_Planning_Alloc: planned load, planned discharge, actual
-- load and actual discharge quantity.
--
-- Lazy loading exists to defer an EXPENSIVE expression. The reason these columns stay virtual
-- (ColumnSQL) rests on the lookup being cheap: a single-row lookup backed by the unique partial
-- index M_Delivery_Planning_Alloc_Package_UQ. So lazy defers nothing worth deferring here, while
-- costing three things:
--  1) A correctness trap that already caused a defect in this issue: PO#load(ResultSet) SKIPS
--     lazy columns and never clears m_valueLoaded, so InterfaceWrapperHelper.refresh can never
--     re-read them -- a cucumber step-def consequently read stale figures.
--  2) N+1 queries on the two tabs that carry all four columns (Lieferanweisungen and Transport
--     Auftrag), so lazy guarantees extra per-column round-trips and defers nothing.
--  3) The generated getters come out @Deprecated -- the generator's own signal that lazy columns
--     should not be read that way.
--
-- The eight AD_SQLColumn_SourceTableColumn rows for these columns (cache-invalidation dependency
-- tracking) are untouched -- they are still needed regardless of IsLazyLoading.
--
-- IDs referenced (pre-existing AD_Column rows, unchanged names/expressions):
--   AD_Column 585497 (ActualLoadQty)
--   AD_Column 585498 (ActualDischargeQuantity)
--   AD_Column 593470 (PlannedLoadedQuantity)
--   AD_Column 593471 (PlannedDischargeQuantity)

UPDATE AD_Column
SET IsLazyLoading='N', Updated=TO_TIMESTAMP('2026-09-03 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID IN (585497, 585498, 593470, 593471)
;
