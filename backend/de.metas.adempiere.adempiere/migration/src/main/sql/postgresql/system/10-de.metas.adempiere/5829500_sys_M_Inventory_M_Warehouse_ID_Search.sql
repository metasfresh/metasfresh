-- M_Inventory.M_Warehouse_ID: show the warehouse as a search field (type to find) instead of a dropdown,
-- in every window showing it (Inventur, Eigenverbrauch, Materialentnahme, their LEGACY windows and customer override windows)

-- Column: M_Inventory.M_Warehouse_ID
-- 2026-10-15T10:00:00
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2026-10-15 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3815
;
