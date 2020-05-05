
-- 2018-09-17T12:03:58.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Value,Description,AD_Org_ID,Name,EntityType) VALUES (0,TO_TIMESTAMP('2018-09-17 12:03:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','S',TO_TIMESTAMP('2018-09-17 12:03:58','YYYY-MM-DD HH24:MI:SS'),100,541236,'name-of-a-spring-profile','This is a template for a sysconfig entry that activates a particular spring profile for the webui-api.
To activate a profile via AD_SysConfig, just 
* copy this template, ractivate it and replace the N with something meaningful
* set as value the profile name that shall be active',0,'de.metas.ui.web.spring.profiles.activeN','D')
;

-- 2018-09-17T12:04:58.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Value,Description,AD_Org_ID,Name,EntityType) VALUES (0,TO_TIMESTAMP('2018-09-17 12:04:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','S',TO_TIMESTAMP('2018-09-17 12:04:58','YYYY-MM-DD HH24:MI:SS'),100,541237,'name-of-a-spring-profile','This is a template for a sysconfig entry that activates a particular spring profile for the app server.
To activate a profile via AD_SysConfig, just 
* copy this template, reactivate it and replace the N with something meaningful
* set as value the profile name that shall be active',0,'de.metas.spring.profiles.activeN','D')
;

-- 2018-09-17T12:05:04.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2018-09-17 12:05:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541236
;

-- 2018-09-17T12:05:07.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This is a template for a sysconfig entry that activates a particular spring profile for the app server. To activate a profile via AD_SysConfig, just  * copy this template, reactivate it and replace the N with something meaningful * set as value the profile name that shall be active',Updated=TO_TIMESTAMP('2018-09-17 12:05:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541237
;

-- 2018-09-17T12:05:13.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This is a template for a sysconfig entry that activates a particular spring profile for the webui-api.
To activate a profile via AD_SysConfig, just 
* copy this template, reactivate it and replace the N with something meaningful
* set as value the profile name that shall be active',Updated=TO_TIMESTAMP('2018-09-17 12:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541236
;

-- 2018-09-17T12:06:09.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This is a template for a sysconfig entry that activates a particular spring profile for the webui-api.
To activate a profile via AD_SysConfig, just 
* first copy this template, reactivate it and replace the N with something meaningful (there can be multiple sysconfigs with the prefix de.metas.ui.web.spring.profiles.active)
* then set as value the profile name that shall be active
* finally, restart',Updated=TO_TIMESTAMP('2018-09-17 12:06:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541236
;

-- 2018-09-17T12:06:33.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This is a template for a sysconfig entry that activates a particular spring profile for the webui-api.
To activate a profile via AD_SysConfig, just 
* first copy this template, reactivate it and replace the N with something meaningful (there can be multiple sysconfigs with the prefix de.metas.spring.profiles.active)
* then set as value the profile name that shall be active
* finally, restart',Updated=TO_TIMESTAMP('2018-09-17 12:06:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541237
;

-- 2018-09-17T12:09:12.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET IsActive='N',Updated=TO_TIMESTAMP('2018-09-17 12:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541237
;

-- 2018-09-17T12:09:16.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET IsActive='N',Updated=TO_TIMESTAMP('2018-09-17 12:09:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541236
;

