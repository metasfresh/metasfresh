-- Column: Carrier_ShipmentOrder.PickupTimeFrom
-- 2025-10-19T10:02:13.361Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-10-19 10:02:13.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591385
;

-- 2025-10-19T10:02:15.097Z
INSERT INTO t_alter_column values('carrier_shipmentorder','PickupTimeFrom','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2025-10-19T10:02:15.099Z
INSERT INTO t_alter_column values('carrier_shipmentorder','PickupTimeFrom',null,'NOT NULL',null)
;

-- Column: Carrier_ShipmentOrder.PickupTimeTo
-- 2025-10-19T10:02:18.655Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-10-19 10:02:18.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591386
;

-- 2025-10-19T10:02:20.203Z
INSERT INTO t_alter_column values('carrier_shipmentorder','PickupTimeTo','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2025-10-19T10:02:20.206Z
INSERT INTO t_alter_column values('carrier_shipmentorder','PickupTimeTo',null,'NOT NULL',null)
;

