-- Every allocation-retirement path (remove-from-instruction, move, close, void/cancel)
-- DEACTIVATES M_Delivery_Planning_Alloc and its M_ShippingPackage instead of deleting them, so a
-- retired allocation now PERSISTS with IsActive='N' -- by design, so a re-booking history can show
-- what was once planned.
--
-- All three FKs on M_Delivery_Planning_Alloc were declared plain NO ACTION (see 5820400), which
-- assumed the row would always be deleted before its parent ever could be. That assumption no
-- longer holds: a physically deleted M_Delivery_Planning (receipt/shipment-schedule delete
-- interceptors, or the planning's own WebUI delete), or a physically deleted M_ShipperTransportation
-- (its beforeDelete deletes ALL M_ShippingPackage lines, active or retired), now leaves a retired
-- M_Delivery_Planning_Alloc row pointing at a row that no longer exists -- NO ACTION refuses the
-- parent delete outright with a raw FK-violation instead of the operation succeeding.
--
-- ON DELETE CASCADE on all three FKs. A retired allocation's only reason to exist is to record
-- history for a planning / instruction / package that still exists; once any one of those three is
-- itself physically deleted, there is nothing left for the allocation row to be a history OF, so
-- cascading it away costs the re-booking history nothing it was built to keep (void/close/remove/
-- move never delete the parent row, so this path is never taken for the history's own scenarios).
-- An ACTIVE allocation is a different matter - deleting its planning must still be refused rather
-- than silently cascaded away, which this migration alone cannot do (a plain FK cascade cannot tell
-- IsActive='Y' apart from 'N'). That refusal is a separate, unconditional application-level guard on
-- every M_Delivery_Planning delete, not part of this schema change.
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
