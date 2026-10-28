-- AD_Element 577279 (PickupTimeTo) is used by 4 AD_Column rows. Its en_US AD_Element_Trl row carried
-- the German text verbatim while flagged IsTranslated='Y'.
UPDATE AD_Element_Trl
   SET Name='Pickup Time To', Description='Pickup Time To', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 15:15:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=577279 AND AD_Language='en_US'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577279, 'en_US');

-- fr_CH per the convention stated once in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql: the en_US text,
-- IsTranslated='N'. English text for this element exists as of the statement above, so the fr_CH row
-- no longer falls into the "no English text at all" case that keeps the German base text.
UPDATE AD_Element_Trl
   SET Name='Pickup Time To', Description='Pickup Time To', IsTranslated='N',
       Updated=TO_TIMESTAMP('2026-08-27 15:15:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=577279 AND AD_Language='fr_CH'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577279, 'fr_CH');
