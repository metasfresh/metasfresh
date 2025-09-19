-- Run mode: SWING_CLIENT

-- Column: M_Product.Value
-- 2025-09-19T08:12:10.421Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-09-19 08:12:10.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2011
;

-- 2025-09-19T08:12:11.928Z
INSERT INTO t_alter_column values('m_product','Value','VARCHAR(255)',null,null)
;

