--
-- name things, mostly to have meaningfull reference internalNames
--
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
-- name and trl for reserver and unreserve process
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

--
-- fix errors regarding missing PricesBase context value; also trl
--
-- 2019-01-11T07:23:13.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Preissystem',Updated=TO_TIMESTAMP('2019-01-11 07:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541611
;

-- 2019-01-11T07:23:20.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preissystem' WHERE AD_Ref_List_ID=541611 AND AD_Language='de_CH'
;

-- 2019-01-11T07:23:25.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541611 AND AD_Language='en_US'
;

-- 2019-01-11T07:23:32.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preissystem' WHERE AD_Ref_List_ID=541611 AND AD_Language='de_DE'
;

-- 2019-01-11T07:23:35.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:35','YYYY-MM-DD HH24:MI:SS'),Name='Pricing system' WHERE AD_Ref_List_ID=541611 AND AD_Language='en_US'
;

-- 2019-01-11T07:23:42.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:42','YYYY-MM-DD HH24:MI:SS'),Name='Preissystem' WHERE AD_Ref_List_ID=541611 AND AD_Language='nl_NL'
;

-- 2019-01-11T07:24:19.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Festpreis',Updated=TO_TIMESTAMP('2019-01-11 07:24:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541612
;

-- 2019-01-11T07:24:22.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:24:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Festpreis' WHERE AD_Ref_List_ID=541612 AND AD_Language='de_CH'
;

-- 2019-01-11T07:24:25.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:24:25','YYYY-MM-DD HH24:MI:SS'),Name='Festpreis' WHERE AD_Ref_List_ID=541612 AND AD_Language='nl_NL'
;

-- 2019-01-11T07:24:28.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:24:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541612 AND AD_Language='en_US'
;

-- 2019-01-11T07:24:32.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:24:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Festpreis' WHERE AD_Ref_List_ID=541612 AND AD_Language='de_DE'
;

-- 2019-01-11T07:24:51.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='P', IsMandatory='Y',Updated=TO_TIMESTAMP('2019-01-11 07:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559657
;

-- 2019-01-11T07:25:53.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceBase/P@ = F',Updated=TO_TIMESTAMP('2019-01-11 07:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559659
;

-- 2019-01-11T07:25:56.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceBase/P@ = F',Updated=TO_TIMESTAMP('2019-01-11 07:25:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560678
;

-- 2019-01-11T07:26:03.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceBase/P@ = P',Updated=TO_TIMESTAMP('2019-01-11 07:26:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559655
;

-- 2019-01-11T07:26:06.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@M_Product_ID/0@!0',Updated=TO_TIMESTAMP('2019-01-11 07:26:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6610
;

-- 2019-01-11T07:24:59.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_DiscountSchemaBreak SET PriceBase='P' WHERE PriceBase IS NULL
;

