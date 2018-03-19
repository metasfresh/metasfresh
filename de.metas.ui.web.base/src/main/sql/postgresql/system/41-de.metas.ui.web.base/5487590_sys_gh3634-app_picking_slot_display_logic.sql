
--
-- hide the picking slot parameter unless a shipment schedule is selected
--

-- 2018-03-07T20:26:50.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@M_ShipmentSchedule_ID/0@ ! 0',Updated=TO_TIMESTAMP('2018-03-07 20:26:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541226
;

