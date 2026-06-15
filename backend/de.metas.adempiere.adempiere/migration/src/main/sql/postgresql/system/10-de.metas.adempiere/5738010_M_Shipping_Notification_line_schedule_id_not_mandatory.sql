-- Run mode: SWING_CLIENT

-- Column: M_Shipping_NotificationLine.M_ShipmentSchedule_ID
-- 2024-10-30T07:10:51.441Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-10-30 08:10:51.441','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587418
;

-- 2024-10-30T07:10:54.684Z
INSERT INTO t_alter_column values('m_shipping_notificationline','M_ShipmentSchedule_ID','NUMERIC(10)',null,null)
;

-- 2024-10-30T07:10:54.688Z
INSERT INTO t_alter_column values('m_shipping_notificationline','M_ShipmentSchedule_ID',null,'NULL',null)
;

