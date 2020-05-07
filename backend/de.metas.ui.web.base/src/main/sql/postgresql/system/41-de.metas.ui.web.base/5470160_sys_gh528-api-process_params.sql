--
-- adjust the process parameters' display logic
--

-- 2017-08-29T11:33:36.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='1', DisplayLogic='@M_HU_PI_Item_ID/-1@ > 0',Updated=TO_TIMESTAMP('2017-08-29 11:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541174
;

-- 2017-08-29T11:34:59.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@M_HU_PI_Item_Product_ID/-1@ > 0 & @M_HU_PI_Item_Product_ID@ <> 101', IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2017-08-29 11:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541174
;

-- 2017-08-29T11:35:13.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-29 11:35:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='TU quantity',Help='This parameter is shown if a non-virtual TU packing instruction is selected' WHERE AD_Process_Para_ID=541174 AND AD_Language='en_US'
;

-- 2017-08-29T11:35:53.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Help='Dieser Parameter wird angezeigt, wenn eine physikalische TU packvorschrift ausgewählt ist',Updated=TO_TIMESTAMP('2017-08-29 11:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541174
;

-- 2017-08-29T12:15:32.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@M_HU_PI_Item_Product_ID/-1@>0 & @M_HU_PI_Item_Product_ID/-1@!101',Updated=TO_TIMESTAMP('2017-08-29 12:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541174
;

-- 2017-08-29T15:13:06.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@M_HU_PI_Item_ID/-1@ > 0',Updated=TO_TIMESTAMP('2017-08-29 15:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541172
;

-- 2017-08-29T15:13:31.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@M_HU_PI_Item_Product_ID/-1@!101',Updated=TO_TIMESTAMP('2017-08-29 15:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541172
;

