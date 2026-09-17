-- Make the Product Costs product-selector tab (AD_Window 344 / AD_Tab 700) editable, but keep every
-- pre-existing field on it read-only, so ONLY the co-product cost-distribution field
-- (AD_Field 784982, AD_Column M_Product.CoProductCostDistributionPercent) becomes user-editable.
--
-- AD_Tab 700 "Produkt auswaehlen" is the read-only product selector of window 344 "Produktkosten":
-- it exists to pick a product and view its costing master data, not to edit the product itself, so
-- it was created with IsReadOnly='Y', which disables the ENTIRE tab in the WebUI -- including
-- field 784982, the one field the customer is meant to maintain here (the co-product cost-distribution
-- percentage). Just flipping the tab to IsReadOnly='N' would make every field on it editable, which is
-- not wanted. So this script instead:
--   1. sets AD_Tab 700 IsReadOnly='N' (the tab becomes editable), and
--   2. sets IsReadOnly='Y' on every OTHER AD_Field on tab 700 (all fields except 784982), leaving them
--      displayed but read-only,
-- so that field 784982 -- which keeps its IsReadOnly='N' from 5824760 -- is the ONLY editable field on
-- this otherwise read-only cost-selector tab.
--
-- Idempotent-safe: the two framework-managed fields on tab 700 (AD_Client_ID, AD_Org_ID) are already
-- IsReadOnly='Y'; the broad UPDATE below re-affirms them as a no-op.

UPDATE AD_Tab SET IsReadOnly='N', Updated=TO_TIMESTAMP('2026-09-17 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Tab_ID=700
;

UPDATE AD_Field SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2026-09-17 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Tab_ID=700 AND AD_Field_ID<>784982
;
