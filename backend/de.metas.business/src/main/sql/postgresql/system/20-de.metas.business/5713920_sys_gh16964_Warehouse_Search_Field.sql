-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.M_Warehouse_ID
-- 2023-12-18T12:28:25.696Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-12-18 14:28:25.695','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=540358
;

-- Run mode: SWING_CLIENT

-- 2023-12-18T12:28:44.592Z
INSERT INTO t_alter_column values('m_shipmentschedule','M_Warehouse_ID','NUMERIC(10)',null,null)
;

-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.M_Warehouse_Override_ID
-- 2023-12-18T14:44:12.139Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-12-18 16:44:12.139','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=65576
;

-- 2023-12-18T14:44:37.505Z
INSERT INTO t_alter_column values('m_shipmentschedule','M_Warehouse_Override_ID','NUMERIC(10)',null,null)
;
