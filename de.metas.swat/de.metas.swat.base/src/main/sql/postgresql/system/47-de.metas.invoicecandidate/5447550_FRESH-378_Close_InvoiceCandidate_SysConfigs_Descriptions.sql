-- 27.06.2016 15:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='By setting this configuration you can switch on and off the automatical closing of invoice candidates when they are set to be cleared. Closing invoice candidates means setting their Process_Override to "Yes".
If this behaviour is needed, set the value on "Y" otherwise on "N".',Updated=TO_TIMESTAMP('2016-06-27 15:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540996
;

-- 27.06.2016 15:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='By setting this configuration you can switch on and off the automatical closing of invoice candidates if they were partially invoiced. Closing invoice candidates means setting their Process_Override to "Yes"> Note that the linked shipment/receipt schedules are also closed.
If this behaviour is needed, set the value on "Y" otherwise on "N".', Value='Y',Updated=TO_TIMESTAMP('2016-06-27 15:38:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540997
;
