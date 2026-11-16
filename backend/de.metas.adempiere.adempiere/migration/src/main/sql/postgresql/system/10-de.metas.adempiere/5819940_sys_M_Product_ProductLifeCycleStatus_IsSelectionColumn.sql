-- Add M_Product.ProductLifeCycleStatus to the quick-filter bar.
--
-- Filter-bar inclusion is driven by AD_Column.IsSelectionColumn (loaded by GridFieldVO into
-- isDefaultFilterColumn), NOT by AD_UI_Element.IsAllowFiltering: that flag is only honoured for the
-- Labels widget (AD_UI_ElementType='L'), so the IsAllowFiltering='Y' set on this field's UI element in
-- 5816400_sys_M_Product_ProductLifeCycleStatus.sql is inert for a plain field ('F') and does not enable
-- filtering on its own.
--
-- IsSelectionColumn lives on AD_Column, i.e. it is table-level: the filter becomes available on every
-- window/tab that exposes this column. SeqNo 130 keeps it next to the other life-cycle filters
-- (Discontinued=110, DiscontinuedFrom=120).

UPDATE AD_Column
SET IsSelectionColumn='Y',
    SelectionColumnSeqNo=130,
    Updated=TO_TIMESTAMP('2026-08-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID=593038 /* M_Product.ProductLifeCycleStatus */
;
