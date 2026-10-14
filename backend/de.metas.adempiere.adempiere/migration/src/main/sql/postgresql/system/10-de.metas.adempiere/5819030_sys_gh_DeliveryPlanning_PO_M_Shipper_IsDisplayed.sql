-- C4: AC-13b — Display Shipper on Purchase Order window 181
-- AD_Field 3447 (C_Order.M_Shipper_ID, Tab 294 "Bestellung", Window 181)
-- Flip IsDisplayed N→Y; DisplayLogic was NULL so nothing to preserve.

UPDATE AD_Field
SET    IsDisplayed = 'Y',
       Updated     = TO_TIMESTAMP('2026-08-13', 'YYYY-MM-DD'),
       UpdatedBy   = 99
WHERE  AD_Field_ID = 3447 -- existing row, no ID-server call needed
;
