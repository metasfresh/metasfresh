-- Make the M_ShipmentSchedule "Lieferweg-Services" labels field read-only. On the shipment schedule the
-- carrier services are owned by the carrier-advise code and are not user-edited on this window. AD_Field
-- 781773 is the labels selector on the hidden tab over M_ShipmentSchedule_Carrier_Service, shown on the
-- Lieferdisposition window (500221); its position (left column, below the goods-type / carrier-product
-- fields) stays unchanged.
UPDATE AD_Field
   SET IsReadOnly='Y',
       Updated=TO_TIMESTAMP('2026-08-13 10:02:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_Field_ID=781773
;
