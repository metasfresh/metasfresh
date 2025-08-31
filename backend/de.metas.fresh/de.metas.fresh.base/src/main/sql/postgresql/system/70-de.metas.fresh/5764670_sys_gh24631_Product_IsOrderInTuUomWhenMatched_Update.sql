-- Run mode: SWING_CLIENT

-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- 2025-08-31T10:02:41.258Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2025-08-31 10:02:41.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589626
;

-- 2025-08-31T10:02:57.304Z
INSERT INTO t_alter_column values('m_hu_pi_item_product','IsOrderInTuUomWhenMatched','CHAR(1)',null,'Y')
;

-- 2025-08-31T10:02:57.991Z
UPDATE M_HU_PI_Item_Product SET IsOrderInTuUomWhenMatched='Y' WHERE IsOrderInTuUomWhenMatched IS NULL
;

