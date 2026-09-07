-- Idempotent convergence for the carrier-window scripts (5807950/5807960/5807970).
-- Ensures the Carrier_Product child tabs bind Parent_Column_ID to the PARENT table's PK
-- (Carrier_Product.Carrier_Product_ID = 591348), not the child-table FK, and that the
-- three header tabs default-sort by Name ascending (master-data convention).
-- On a fresh DB the originals already set these values, so every statement is a no-op there.

-- Child tab parent binding: Parent_Column_ID = parent PK (591348), AD_Column_ID stays the child FK
UPDATE AD_Tab SET Parent_Column_ID=591348 /*Carrier_Product.Carrier_Product_ID PK*/,
     Updated=TO_TIMESTAMP('2026-06-15 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Tab_ID IN (549315 /*Warenarten*/, 549316 /*Services*/)
  AND Parent_Column_ID <> 591348;

-- Default grid sort by Name ascending on the three header tabs
UPDATE AD_Field SET SortNo=1,
     Updated=TO_TIMESTAMP('2026-06-15 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID IN (781128 /*Carrier_Product Name*/, 781136 /*Carrier_Goods_Type Name*/, 781142 /*Carrier_Service Name*/)
  AND COALESCE(SortNo,0) <> 1;
