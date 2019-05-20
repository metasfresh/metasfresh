
--
-- unrelated: allow our existing de.metas.async.*.SizeBasedPrio Sysconfigs to be overridden on org level (the code seems to allow it too)
-- 2019-05-15T14:16:06.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540902
;

-- 2019-05-15T14:16:08.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540901
;

-- 2019-05-15T14:16:10.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540900
;

-- 2019-05-15T14:16:12.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540899
;

-- 2019-05-15T14:16:14.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540898
;

-- 2019-05-15T14:16:17.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540893
;

-- 2019-05-15T14:16:19.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540894
;

-- 2019-05-15T14:16:22.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540895
;

-- 2019-05-15T14:16:23.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540896
;

-- 2019-05-15T14:16:28.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-05-15 14:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540897
;


-- 2019-05-15T15:22:06.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Value,AD_Org_ID,Name,EntityType,Description) VALUES (0,TO_TIMESTAMP('2019-05-15 15:22:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','O',TO_TIMESTAMP('2019-05-15 15:22:06','YYYY-MM-DD HH24:MI:SS'),100,541279,'Y',0,'de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly','de.metas.handlingunits','If set to yes, and the shipment run is supposed to create shipment lines even for quantities that were not picked, then the system will still allocate existing available HUs on the fly.
This can include both allocating complete HUs and splitting HUs to allocate matching storage quantities.
')
;

-- 2019-05-15T15:24:10.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If set to yes, and the shipment run is supposed to create shipment lines even for quantities that were not picked, then the system will still allocate existing available HUs on the fly.
This can include both allocating complete HUs and splitting HUs to allocate matching storage quantities.
Hardcoded default in case this sysconfig record is missing: true!
',Updated=TO_TIMESTAMP('2019-05-15 15:24:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541279
;

-- 2019-05-16T15:04:14.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Der Lieferbeleg enthält die Liefermenge der jeweiligen Lieferdispo-Datensätze, auch wenn kein ausreichender Bestand vorhanden ist.', Name='Liefermenge',Updated=TO_TIMESTAMP('2019-05-16 15:04:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541682
;

-- 2019-05-16T15:05:08.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='The shipment document contains the shipment schedule''s quantity to deliever, even without sufficient stock.',Updated=TO_TIMESTAMP('2019-05-16 15:05:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541682
;

-- 2019-05-16T15:05:27.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der Lieferbeleg enthält die Liefermenge der jeweiligen Lieferdispo-Datensätze, auch wenn kein ausreichender Bestand vorhanden ist.',Updated=TO_TIMESTAMP('2019-05-16 15:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541682
;

-- 2019-05-16T15:05:33.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Liefermenge',Updated=TO_TIMESTAMP('2019-05-16 15:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541682
;

-- 2019-05-16T15:05:40.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Liefermenge',Updated=TO_TIMESTAMP('2019-05-16 15:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541682
;

-- 2019-05-16T15:05:46.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der Lieferbeleg enthält die Liefermenge der jeweiligen Lieferdispo-Datensätze, auch wenn kein ausreichender Bestand vorhanden ist.',Updated=TO_TIMESTAMP('2019-05-16 15:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541682
;

-- 2019-05-16T15:06:01.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-05-16 15:06:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541681
;

-- 2019-05-16T15:06:11.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Picked quantity',Updated=TO_TIMESTAMP('2019-05-16 15:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541681
;

-- 2019-05-16T15:06:34.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-05-16 15:06:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540458
;

-- 2019-05-16T15:06:37.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-05-16 15:06:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540458
;

-- 2019-05-16T15:06:40.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Trl WHERE AD_Language='en_GB' AND AD_Process_ID=540458
;

-- 2019-05-16T15:06:48.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Trl WHERE AD_Language='fr_CH' AND AD_Process_ID=540458
;

-- 2019-05-16T15:06:51.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Trl WHERE AD_Language='it_CH' AND AD_Process_ID=540458
;

-- 2019-05-16T15:06:53.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Trl WHERE AD_Language='nl_NL' AND AD_Process_ID=540458
;

-- 2019-05-16T15:07:00.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Generate shipments',Updated=TO_TIMESTAMP('2019-05-16 15:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540458
;

