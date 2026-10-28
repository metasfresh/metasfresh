-- Product Life Cycle Status (BBS-Status): remove the leftover filter entry from the vanilla Product
-- window. 5820370 hid the field on AD_Window 140, but the filter bar kept offering it.
--
-- AD_Field.IsFilterField is window-scoped, so this touches window 140 only. The filter's source,
-- AD_Column.IsSelectionColumn (5819940), is table-level and stays 'Y' -- clearing it there would also
-- strip the quick filter from the customer's own Product window.

UPDATE AD_Field
SET IsFilterField='N',
    Updated=TO_TIMESTAMP('2026-08-27 09:15:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Field_ID=781848 /* M_Product.ProductLifeCycleStatus on AD_Tab 180 (AD_Window 140, Produkt_OLD) */
;
