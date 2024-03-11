
-- 2021-11-18T10:36:28.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='By setting this configuration you can switch on and off the automatical closing of invoice candidates if they were partially invoiced and have one of the following invoice rules:  AfterDelivery, AfterOrderDelivered, CustomerScheduleAfterDelivery, OrderCompletelyDelivered. 
Closing invoice candidates means setting their Process_Override to "Yes". 
Note that the linked shipment/receipt schedules are also closed. If this behaviour is needed, set the value on "Y" otherwise on "N".',Updated=TO_TIMESTAMP('2021-11-18 12:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540997
;

