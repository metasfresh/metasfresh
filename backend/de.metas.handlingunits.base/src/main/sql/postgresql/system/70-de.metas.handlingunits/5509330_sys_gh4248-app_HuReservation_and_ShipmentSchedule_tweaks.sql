--
-- name things, mostly to have meaningfull reference internalNames
--

-- 2019-01-09T14:27:50.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='M_ReceiptSchedule',Updated=TO_TIMESTAMP('2019-01-09 14:27:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540196
;

-- 2019-01-10T17:12:59.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='M_ShipmentSchedule',Updated=TO_TIMESTAMP('2019-01-10 17:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=500221
;

-- 2019-01-10T17:13:07.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='M_ShipmentSchedule',Updated=TO_TIMESTAMP('2019-01-10 17:13:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=500221
;

-- 2019-01-10T17:13:57.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='MD_Cockpit_DocumentDetail',Updated=TO_TIMESTAMP('2019-01-10 17:13:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540395
;

UPDATE AD_RelationType SET InternalName='C_Order_C_Invoice_Candidate' WHERE AD_RelationType_ID=540193; -- C_Order -> C_Invoice_Candidate

--
-- name and trl for reserve and unreserve process
--
-- 2019-01-10T17:38:27.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Für Auftragszeile reservieren',Updated=TO_TIMESTAMP('2019-01-10 17:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540983
;

-- 2019-01-10T17:38:32.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-10 17:38:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Für Auftragszeile reservieren' WHERE AD_Process_ID=540983 AND AD_Language='de_CH'
;

-- 2019-01-10T17:38:37.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-10 17:38:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Für Auftragszeile reservieren' WHERE AD_Process_ID=540983 AND AD_Language='de_DE'
;

-- 2019-01-10T17:38:43.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-10 17:38:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reserve for order line' WHERE AD_Process_ID=540983 AND AD_Language='en_US'
;

-- 2019-01-10T17:38:47.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-10 17:38:47','YYYY-MM-DD HH24:MI:SS'),Name='Für Auftragszeile reservieren' WHERE AD_Process_ID=540983 AND AD_Language='nl_NL'
;

-- 2019-01-10T17:39:09.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-10 17:39:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540985 AND AD_Language='de_CH'
;

-- 2019-01-10T17:39:11.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-10 17:39:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540985 AND AD_Language='de_DE'
;

-- 2019-01-10T17:39:25.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-10 17:39:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Delete reservation' WHERE AD_Process_ID=540985 AND AD_Language='en_US'
;
