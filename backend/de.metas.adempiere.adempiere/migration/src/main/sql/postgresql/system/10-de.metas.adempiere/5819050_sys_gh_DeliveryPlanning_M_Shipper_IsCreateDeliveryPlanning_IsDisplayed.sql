-- 2026-08-14 Show M_Shipper.IsCreateDeliveryPlanning field on the shipper window
-- GH #30630, PR 25479
UPDATE AD_Field SET IsDisplayed='Y', Updated=TO_TIMESTAMP('2026-08-14 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782286;
