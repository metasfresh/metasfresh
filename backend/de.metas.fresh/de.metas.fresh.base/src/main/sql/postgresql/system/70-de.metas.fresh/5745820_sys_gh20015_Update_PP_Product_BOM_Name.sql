-- Run mode: SWING_CLIENT

-- Column: PP_Product_BOM.Name
-- 2025-02-05T16:43:49.775Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-02-05 16:43:49.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53322
;

-- 2025-02-05T16:44:49.346Z
INSERT INTO t_alter_column values('pp_product_bom','Name','VARCHAR(255)',null,null)
;

-- Column: PP_Product_BOM_Trl.Name
-- 2025-02-05T17:32:45.172Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-02-05 17:32:45.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=57223
;

-- 2025-02-05T17:33:16.611Z
INSERT INTO t_alter_column values('pp_product_bom_trl','Name','VARCHAR(255)',null,null)
;

