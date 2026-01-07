-- Column: M_Product.EAN13_ProductCode
-- 2025-05-07T17:19:46.146Z
UPDATE AD_Column SET FieldLength=9,Updated=TO_TIMESTAMP('2025-05-07 17:19:46.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589771
;

-- 2025-05-07T17:20:11.690Z
INSERT INTO t_alter_column values('m_product','EAN13_ProductCode','VARCHAR(9)',null,null)
;
