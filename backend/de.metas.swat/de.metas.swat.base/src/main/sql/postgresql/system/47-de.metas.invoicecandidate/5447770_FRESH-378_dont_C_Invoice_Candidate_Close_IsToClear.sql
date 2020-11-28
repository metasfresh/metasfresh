
-- Jul 5, 2016 8:47 AM
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='By setting this configuration you can switch on and off the automatical closing of invoice candidates when they are set to be cleared. 
Closing invoice candidates means setting their Process_Override to "Yes".
If this behaviour is needed, set the value on Y otherwise on N.
!! Setting to N, because currently, shipment lines can''t be reset with the isToClear-IC is already processed !!', Value='N',Updated=TO_TIMESTAMP('2016-07-05 08:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540996
;

