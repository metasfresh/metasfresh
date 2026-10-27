-- Column: M_ShipmentSchedule.C_Project_ID
-- The project is owned by the order line and propagated to the shipment schedule, so it must not be edited in any shipment schedule window.
-- ReadOnlyLogic (not IsUpdateable='N') because the propagation from the order line has to keep updating the column.
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2026-10-26 09:16:07.000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591698
;
