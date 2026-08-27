-- Task C6 (9b0eaf45c3f) made every allocation-retirement path (remove-from-instruction, move,
-- close, void/cancel) DEACTIVATE M_Delivery_Planning_Alloc and its M_ShippingPackage instead of
-- deleting them, so a retired allocation now PERSISTS with IsActive='N' -- by design, so the
-- re-booking audit trail (C6+E5) can show what was once planned.
--
-- All three FKs on M_Delivery_Planning_Alloc were declared plain NO ACTION (see 5820400), which
-- assumed the row would always be deleted before its parent ever could be. That assumption no
-- longer holds: a physically deleted M_Delivery_Planning (receipt/shipment-schedule delete
-- interceptors, or the planning's own WebUI delete), or a physically deleted M_ShipperTransportation
-- (its beforeDelete deletes ALL M_ShippingPackage lines, active or retired), now leaves a retired
-- M_Delivery_Planning_Alloc row pointing at a row that no longer exists -- NO ACTION refuses the
-- parent delete outright with a raw FK-violation instead of the operation succeeding.
--
-- Fix: ON DELETE CASCADE on all three FKs. A retired allocation's only reason to exist is to record
-- history for a planning / instruction / package that still exists; once any one of those three is
-- itself physically deleted, there is nothing left for the allocation row to be a history OF, so
-- cascading it away costs the audit trail nothing it was built to keep (void/close/remove/move never
-- delete the parent row, so this never fires on the paths C6+E5 actually care about).
ALTER TABLE M_Delivery_Planning_Alloc DROP CONSTRAINT IF EXISTS MDeliveryPlanning_MDeliveryPlanningAlloc;
ALTER TABLE M_Delivery_Planning_Alloc
    ADD CONSTRAINT MDeliveryPlanning_MDeliveryPlanningAlloc
        FOREIGN KEY (M_Delivery_Planning_ID) REFERENCES public.M_Delivery_Planning
            ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Delivery_Planning_Alloc DROP CONSTRAINT IF EXISTS MShipperTransportation_MDeliveryPlanningAlloc;
ALTER TABLE M_Delivery_Planning_Alloc
    ADD CONSTRAINT MShipperTransportation_MDeliveryPlanningAlloc
        FOREIGN KEY (M_ShipperTransportation_ID) REFERENCES public.M_ShipperTransportation
            ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Delivery_Planning_Alloc DROP CONSTRAINT IF EXISTS MShippingPackage_MDeliveryPlanningAlloc;
ALTER TABLE M_Delivery_Planning_Alloc
    ADD CONSTRAINT MShippingPackage_MDeliveryPlanningAlloc
        FOREIGN KEY (M_ShippingPackage_ID) REFERENCES public.M_ShippingPackage
            ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;
