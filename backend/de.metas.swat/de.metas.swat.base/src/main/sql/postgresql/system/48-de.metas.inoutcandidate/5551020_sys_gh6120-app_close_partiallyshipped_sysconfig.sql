-- 2020-02-04T06:40:09.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541308,'O',TO_TIMESTAMP('2020-02-04 07:40:09','YYYY-MM-DD HH24:MI:SS'),100,'By setting this configuration you can switch on and off the automatical closing of shipment schedules candidates if they were partially shipped. 
If this behaviour needed, set the value on "Y" otherwise on "N".','de.metas.inoutcandidate','Y','M_ShipmentSchedule_Close_PartiallyShipped',TO_TIMESTAMP('2020-02-04 07:40:09','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2020-02-04T06:42:42.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='By setting this configuration you can switch on and off the automatical closing of invoice candidates if they were partially invoiced. Closing invoice candidates means setting their Process_Override to "Yes"> Note that the linked shipment/receipt schedules are also closed. If this behaviour is needed, set the value on "Y" otherwise on "N".',Updated=TO_TIMESTAMP('2020-02-04 07:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540997
;

-- also make the invoice candidate pendant org-configurable
-- 2020-02-04T06:53:43.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2020-02-04 07:53:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540997
;

