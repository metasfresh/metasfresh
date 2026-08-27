-- Product Life Cycle Status (BBS-Status): stop offering the field as a filter in the vanilla Product window.
-- 5820370 hid the field itself on AD_Tab 180 / AD_Window 140 ("Produkt_OLD"), but the filter bar kept
-- listing it, so the window offered a filter for a field it no longer displays.
--
-- The filter entry comes from AD_Column.IsSelectionColumn (5819940), which is table-level and must stay
-- 'Y': the customer's own Product window relies on it for its quick filter, and clearing it there would
-- remove the filter from every M_Product tab. AD_Field.IsFilterField is the window-scoped switch, so
-- setting it on this one AD_Field removes the entry from window 140 alone. AD_Field 781859 -- the same
-- column on the customer override window -- is a separate row and is untouched by construction.
--
-- Verified on a local stack carrying every migration of this change: with IsFilterField='N' the window's
-- layout no longer lists ProductLifeCycleStatus among its filter parameters, the grid stays clean, and
-- the column, its ref-list and every backend guard remain untouched.

UPDATE AD_Field
SET IsFilterField='N',
    Updated=TO_TIMESTAMP('2026-08-27 09:15:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Field_ID=781848 /* M_Product.ProductLifeCycleStatus on AD_Tab 180 (AD_Window 140, Produkt_OLD) */
;
