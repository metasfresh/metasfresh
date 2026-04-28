-- 2017-05-01T14:00:52.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541143,'S',TO_TIMESTAMP('2017-05-01 14:00:52','YYYY-MM-DD HH24:MI:SS'),100,'Detemines if the runtime shall care about records'' DLM_Levels and the like when connecting to the database.
Can be userridden per-user via de.metas.dlm.ConnectionCustomizer.enabled.AD_User_ID_<particular-AD_User_ID>
This setting only makes a difference if DLM "main model interceptor is activated.
Also see https://github.com/metasfresh/metasfresh/issues/1411
','de.metas.dlm','Y','de.metas.dlm.ConnectionCustomizer.enabled.general',TO_TIMESTAMP('2017-05-01 14:00:52','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2017-05-01T14:04:08.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541144,'S',TO_TIMESTAMP('2017-05-01 14:04:08','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.dlm','Y','de.metas.dlm.ConnectionCustomizer.enabled.AD_User_ID_0',TO_TIMESTAMP('2017-05-01 14:04:08','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2017-05-01T14:04:20.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='See de.metas.dlm.ConnectionCustomizer.enabled.general',Updated=TO_TIMESTAMP('2017-05-01 14:04:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541144
;

