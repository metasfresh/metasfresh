-- 2019-01-21T15:22:42.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2019-01-21 15:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540949
;

-- 2019-01-21T15:51:27.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541258,'S',TO_TIMESTAMP('2019-01-21 15:51:27','YYYY-MM-DD HH24:MI:SS'),100,'Max number of MSV3StockAvailabilityUpdatedEvents that are published in one batch.','de.metas.vertical.pharma.msv3.server','Y','de.metas.vertical.pharma.msv3.server.peer.metasfresh.services.MSV3StockAvailabilityService.publishAll_StockAvailability.BatchSize',TO_TIMESTAMP('2019-01-21 15:51:27','YYYY-MM-DD HH24:MI:SS'),100,'500')
;

-- 2019-01-21T16:23:54.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Max number of MSV3StockAvailabilityUpdatedEvents that are published in one batch.
Default=500',Updated=TO_TIMESTAMP('2019-01-21 16:23:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541258
;


-- 2019-01-21T16:16:41.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 16:16:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Alle MSV3-Produkte übertragen',Description='Löscht alle MSV3-Produkte auf dem MSV3-Server und überträgt sie erneut' WHERE AD_Process_ID=540949 AND AD_Language='de_CH'
;

-- 2019-01-21T16:16:47.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 16:16:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Löscht alle MSV3-Produkte auf dem MSV3-Server und überträgt sie erneut' WHERE AD_Process_ID=540949 AND AD_Language='de_DE'
;

-- 2019-01-21T16:16:52.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 16:16:52','YYYY-MM-DD HH24:MI:SS'),Name='Alle MSV3-Produkte übertragen' WHERE AD_Process_ID=540949 AND AD_Language='de_DE'
;

-- 2019-01-21T16:18:27.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 16:18:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Deletes all MSV3 products on the MSV3 server and transmits them from metasfresh.
The transmission will take place in batches. During the transmission, additional changes such as new MSV3-Products will be trasmitted in parallel.' WHERE AD_Process_ID=540949 AND AD_Language='en_US'
;

-- 2019-01-21T16:20:11.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 16:20:11','YYYY-MM-DD HH24:MI:SS'),Name='Alle MSV3-Produkte übermitteln',Description='Löscht alle MSV3-Produkte auf dem MSV3-Server und übermittelt sie erneut aus metasfresh.
Die Übermittlung erfolgt Paketweise. Andere Änderungen wie zum Beispiel neu hinzukommende MSV3-Produkte werden paralell zu diesem Prozess ebenfalls übermittelt.
' WHERE AD_Process_ID=540949 AND AD_Language='de_DE'
;

-- 2019-01-21T16:20:21.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 16:20:21','YYYY-MM-DD HH24:MI:SS'),Name='Alle MSV3-Produkte übermitteln',Description='Löscht alle MSV3-Produkte auf dem MSV3-Server und übermittelt sie erneut aus metasfresh.
Die Übermittlung erfolgt Paketweise. Andere Änderungen wie zum Beispiel neu hinzukommende MSV3-Produkte werden paralell zu diesem Prozess ebenfalls übermittelt.' WHERE AD_Process_ID=540949 AND AD_Language='de_CH'
;

-- 2019-01-21T16:20:45.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='3',Updated=TO_TIMESTAMP('2019-01-21 16:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540949
;

-- 2019-01-21T16:21:08.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Alle MSV3-Produkte übermitteln',Updated=TO_TIMESTAMP('2019-01-21 16:21:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540949
;

-- 2019-01-21T16:21:33.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='3', Name='Alle MSV3-Kunden übermitteln',Updated=TO_TIMESTAMP('2019-01-21 16:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540939
;

-- 2019-01-21T16:21:41.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 16:21:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Alle MSV3-Kunden übermitteln' WHERE AD_Process_ID=540939 AND AD_Language='de_DE'
;

-- 2019-01-21T16:21:55.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 16:21:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Publish all MSV3 customers' WHERE AD_Process_ID=540939 AND AD_Language='en_US'
;

-- 2019-01-21T16:22:03.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 16:22:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Alle MSV3-Kunden übermitteln' WHERE AD_Process_ID=540939 AND AD_Language='de_CH'
;

