-- Detach the carrier-service validation rule from the read-only M_ShipmentSchedule "Lieferweg-Services"
-- labels field. Column M_ShipmentSchedule_Carrier_Service.Carrier_Service_ID carried AD_Val_Rule 540757
-- (Carrier_Service_ID_for_M_Shipper_ID), which keys on @Carrier_Product_ID@ / @M_Shipper_ID@. That labels
-- selector lives on the junction tab, whose lookup context only exposes the host link column
-- (@M_ShipmentSchedule_ID@), so the rule can never resolve there. The field is read-only (the advise code
-- owns it), so no product filter is needed on it. AD_Val_Rule 540757 itself is left intact -- it is still
-- referenced by, and resolves correctly for, the M_ShipmentSchedule_Advise_Manual process parameters.
UPDATE AD_Column
   SET AD_Val_Rule_ID=NULL,
       Updated=TO_TIMESTAMP('2026-08-13 10:01:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_Column_ID=591336
   AND AD_Val_Rule_ID=540757
;
