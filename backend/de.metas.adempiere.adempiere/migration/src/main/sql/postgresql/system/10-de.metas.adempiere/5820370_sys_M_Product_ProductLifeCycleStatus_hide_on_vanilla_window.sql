-- Product Life Cycle Status (BBS-Status): stop showing the field in the vanilla Product window.
--
-- 5816400 put AD_Field 781848 on AD_Tab 180 / AD_Window 140 ("Produkt_OLD"). Two reasons to take it
-- back off the vanilla window:
--
-- 1) It is redundant there. The core Product window carries an "Auslaufprodukt" (M_Product.Discontinued)
--    checkbox in its right-hand flags group, and users read the two as the same thing. They are not:
--    Discontinued blocks nothing -- it only filters the order-line quick-input product picker
--    (ProductLookupDescriptor.appendFilterByDiscontinued, switched on solely by
--    OrderLineQuickInputDescriptorFactory.hideDiscontinued(true)) and feeds
--    M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process. ProductLifeCycleStatus, by
--    contrast, is a hard block enforced in the backend on ordering, picking, manufacturing and shipping.
--    Offering both, unexplained, side by side invites the wrong one to be set.
--
-- 2) The enforcement it drives is currently wanted by one customer only, and that customer already adds
--    its own AD_Field for this column to the Product window it actually opens. Nothing else in core
--    depends on the field being rendered here.
--
-- The COLUMN, its reference list and every backend guard are untouched and stay in core -- this hides a
-- UI element, it does not remove a capability. Any instance that wants the field back flips these two
-- flags again.
--
-- Note window 140 is "Produkt_OLD": AD_Window 541885 ("Produkt") carries Overrides_Window_ID=140 and
-- IsOverrideInMenu='Y', so the Product menu entry already opens 541885 rather than 140. Hiding here is
-- therefore a cleanup of a field that the menu does not reach anyway -- it makes "not part of the vanilla
-- Product UI" explicit instead of accidental.

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
