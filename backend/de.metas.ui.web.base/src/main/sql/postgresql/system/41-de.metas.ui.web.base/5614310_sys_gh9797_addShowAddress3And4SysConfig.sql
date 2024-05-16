-- 2021-11-18T13:22:51.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541426,'S',TO_TIMESTAMP('2021-11-18 14:22:50','YYYY-MM-DD HH24:MI:SS'),100,'Define if 2pack handle translations or not','D','Y','de.metas.ui.web.address.ShowAddress4',TO_TIMESTAMP('2021-11-18 14:22:50','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2021-11-18T13:23:25.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Define if Address 4 will be shown in the address layout',Updated=TO_TIMESTAMP('2021-11-18 14:23:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541426
;

-- 2021-11-18T13:23:43.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541427,'S',TO_TIMESTAMP('2021-11-18 14:23:43','YYYY-MM-DD HH24:MI:SS'),100,'Define if Address 3 will be shown in the address layout','D','Y','de.metas.ui.web.address.ShowAddress3',TO_TIMESTAMP('2021-11-18 14:23:43','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

