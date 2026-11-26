-- Run mode: SWING_CLIENT

-- Column: M_ProductPrice.C_TaxCategory_ID
-- 2026-04-03T14:44:55.611Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-04-03 14:44:55.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=505149
;

-- 2026-04-03T14:45:13.617Z
INSERT INTO t_alter_column values('m_productprice','C_TaxCategory_ID','NUMERIC(10)',null,null)
;

-- 2026-04-03T14:45:13.678Z
INSERT INTO t_alter_column values('m_productprice','C_TaxCategory_ID',null,'NOT NULL',null)
;

