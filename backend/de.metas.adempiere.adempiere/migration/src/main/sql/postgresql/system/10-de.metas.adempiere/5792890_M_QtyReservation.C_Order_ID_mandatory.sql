-- Run mode: SWING_CLIENT

-- Column: M_QtyReservation.C_Order_ID
-- 2026-03-07T22:59:56.529Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-03-07 22:59:56.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592205
;

-- 2026-03-07T22:59:57.267Z
INSERT INTO t_alter_column values('m_qtyreservation','C_Order_ID','NUMERIC(10)',null,null)
;

-- 2026-03-07T22:59:57.272Z
INSERT INTO t_alter_column values('m_qtyreservation','C_Order_ID',null,'NOT NULL',null)
;

