-- Revert 5820950: M_Delivery_Planning_Alloc's three FKs go back to plain NO ACTION.
--
-- 5820950 reached for ON DELETE CASCADE to stop a RETIRED (IsActive='N') allocation from refusing the
-- delete of the planning, instruction or package it records history for. It works, but it decides too
-- much: a cascade sees one undifferentiated set of child rows, so it erases a LIVE booking exactly as
-- readily as retired history - silently, and with nowhere to put a check. Deleting the parent is the
-- one moment where that distinction matters most, and it is the one place the database cannot make it.
--
-- The distinction is application logic, so it now lives in application code, where it is visible and
-- testable:
--
--   * M_Delivery_Planning (the leg the cascade existed for - a shipment/receipt-schedule delete
--     legitimately deletes its plannings): interceptor/M_Delivery_Planning.onDelete refuses while any
--     allocation is live (assertNotCurrentlyAllocated), then removes the remaining retired rows itself
--     via DeliveryPlanningService.deleteAllocationsFor. So the FK never sees an orphan and never has to
--     guess.
--
--   * M_ShipperTransportation and M_ShippingPackage: interceptor/M_ShippingPackage.onDelete refuses the
--     delete outright while any allocation points at the package, active or retired (message 545814,
--     added by 5820960) - a delivery instruction is cancelled or closed, never deleted. Guarding the
--     package covers the instruction too, because PO.delete0() runs beforeDelete() before it fires
--     TYPE_BEFORE_DELETE and MMShipperTransportation.beforeDelete() force-deletes all of its package
--     lines. Nothing reaches these two FKs with children still attached, so NO ACTION never fires.
--
-- Net effect: the FK goes back to being a last-resort integrity backstop that should never trigger,
-- instead of a silent destructor that triggers on every parent delete.

ALTER TABLE M_Delivery_Planning_Alloc DROP CONSTRAINT IF EXISTS MDeliveryPlanning_MDeliveryPlanningAlloc;
ALTER TABLE M_Delivery_Planning_Alloc
    ADD CONSTRAINT MDeliveryPlanning_MDeliveryPlanningAlloc
        FOREIGN KEY (M_Delivery_Planning_ID) REFERENCES public.M_Delivery_Planning
            DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Delivery_Planning_Alloc DROP CONSTRAINT IF EXISTS MShipperTransportation_MDeliveryPlanningAlloc;
ALTER TABLE M_Delivery_Planning_Alloc
    ADD CONSTRAINT MShipperTransportation_MDeliveryPlanningAlloc
        FOREIGN KEY (M_ShipperTransportation_ID) REFERENCES public.M_ShipperTransportation
            DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Delivery_Planning_Alloc DROP CONSTRAINT IF EXISTS MShippingPackage_MDeliveryPlanningAlloc;
ALTER TABLE M_Delivery_Planning_Alloc
    ADD CONSTRAINT MShippingPackage_MDeliveryPlanningAlloc
        FOREIGN KEY (M_ShippingPackage_ID) REFERENCES public.M_ShippingPackage
            DEFERRABLE INITIALLY DEFERRED;
