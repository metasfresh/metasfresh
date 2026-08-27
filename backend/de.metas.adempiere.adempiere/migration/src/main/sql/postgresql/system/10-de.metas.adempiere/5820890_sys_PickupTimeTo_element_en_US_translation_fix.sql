-- AD_Element 577279 (PickupTimeTo), used by 4 AD_Column rows (M_Shipper, M_ShipperTransportation,
-- DPD_StoreOrder, Carrier_ShipmentOrder -- verified via impact query, no other usage). The en_US
-- AD_Element_Trl row carried the German text "Abholung Uhrzeit bis" verbatim while flagged
-- IsTranslated='Y' -- i.e. marked translated without actually being translated. fr_CH carries the
-- same untranslated German text but is correctly flagged IsTranslated='N' (honestly-marked
-- fallback, not a defect) and is left untouched here.
UPDATE AD_Element_Trl
   SET Name='Pickup Time To', Description='Pickup Time To', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 15:15:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=577279 AND AD_Language='en_US'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577279, 'en_US');
