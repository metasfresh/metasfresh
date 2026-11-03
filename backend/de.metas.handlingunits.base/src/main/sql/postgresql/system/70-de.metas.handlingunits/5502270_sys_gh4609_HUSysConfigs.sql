-- 2018-09-24T18:17:04.268
-- #298 changing anz. stellen
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541238,'S',TO_TIMESTAMP('2018-09-24 18:17:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','de.metas.ui.web.handlingunits.field.M_HU_PI_Item_Product_ID.IsDisplayed',TO_TIMESTAMP('2018-09-24 18:17:04','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2018-09-24T18:48:37.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2018-09-24 18:48:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541238
;

-- 2018-09-26T13:57:46.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541239,'S',TO_TIMESTAMP('2018-09-26 13:57:46','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.ui.web.handlingunits.field.M_Locator_ID.IsDisplayed',TO_TIMESTAMP('2018-09-26 13:57:46','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2018-09-26T13:59:36.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2018-09-26 13:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541239
;

-- 2018-09-27T11:20:36.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541240,'S',TO_TIMESTAMP('2018-09-27 11:20:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','de.metas.ui.web.handlingunits.field.C_UOM_ID.IsDisplayed',TO_TIMESTAMP('2018-09-27 11:20:35','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2018-09-27T11:21:04.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541241,'S',TO_TIMESTAMP('2018-09-27 11:21:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','de.metas.ui.web.handlingunits.HUEditorViewFactory.AlwaysUseSameLayout',TO_TIMESTAMP('2018-09-27 11:21:04','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2018-09-28T12:27:23.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='',Updated=TO_TIMESTAMP('2018-09-28 12:27:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541241
;

-- 2018-09-28T13:10:20.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2018-09-28 13:10:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541241
;

-- 2018-09-28T13:10:49.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2018-09-28 13:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541239
;

-- 2018-09-28T13:13:56.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2018-09-28 13:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541238
;

