-- Product Life Cycle Status (BBS-Status): stop showing the field in the vanilla Product window.
-- 5816400 put AD_Field 781848 on AD_Tab 180 / AD_Window 140 ("Produkt_OLD").
--
-- It is redundant there: the window already has an "Auslaufprodukt" (M_Product.Discontinued) checkbox,
-- and users read the two as the same control. They are not -- Discontinued blocks nothing, it only
-- filters the order-line quick-input picker (ProductLookupDescriptor.appendFilterByDiscontinued, set
-- only by OrderLineQuickInputDescriptorFactory) and feeds the price-deactivation process, whereas
-- ProductLifeCycleStatus is a hard backend block. The enforcement is wanted by one customer today, and
-- that customer's repo already adds the field to the window it actually opens.
--
-- Hides a UI element only -- the column, its ref-list and every backend guard stay in core untouched.
-- (the customer product window overrides 140 with IsOverrideInMenu='Y', so the menu does not reach 140
-- anyway; this makes "not part of the vanilla Product UI" explicit rather than accidental.)

UPDATE AD_Field
SET IsDisplayed='N',
    IsDisplayedGrid='N',
    Updated=TO_TIMESTAMP('2026-08-26 08:30:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Field_ID=781848 /* M_Product.ProductLifeCycleStatus on AD_Tab 180 (AD_Window 140, Produkt_OLD) */
;

UPDATE AD_UI_Element
SET IsDisplayed='N',
    IsDisplayedGrid='N',
    Updated=TO_TIMESTAMP('2026-08-26 08:30:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID=652772 /* the AD_UI_Element rendering AD_Field 781848 */
;
