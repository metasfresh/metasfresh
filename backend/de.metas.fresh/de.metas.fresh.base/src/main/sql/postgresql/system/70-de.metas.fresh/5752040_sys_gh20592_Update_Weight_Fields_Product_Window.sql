-- Column: M_Product.GrossWeight_UOM_ID
-- Column: M_Product.GrossWeight_UOM_ID
-- 2025-04-15T08:18:49.930Z
UPDATE AD_Column SET DefaultValue='540017',Updated=TO_TIMESTAMP('2025-04-15 08:18:49.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=578971
;

UPDATE m_product
SET GrossWeight_UOM_ID = 540017
WHERE GrossWeight_UOM_ID IS NULL
;