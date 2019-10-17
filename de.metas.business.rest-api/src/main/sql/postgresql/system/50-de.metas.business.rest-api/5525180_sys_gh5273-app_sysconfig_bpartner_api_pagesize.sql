-- 2019-06-19T13:50:39.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,Name,Processing,Updated,UpdatedBy) VALUES (0,540252,0,TO_TIMESTAMP('2019-06-19 13:50:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.rest_api','Y','Y','de.metas.rest_api','N',TO_TIMESTAMP('2019-06-19 13:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-19T14:23:22.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541280,'O',TO_TIMESTAMP('2019-06-19 14:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Max mumber of items per page returned by the paginated GET  requests','de.metas.rest_api','Y','de.metas.rest_api.bpartner.SincePageSize',TO_TIMESTAMP('2019-06-19 14:23:22','YYYY-MM-DD HH24:MI:SS'),100,'50')
;

-- 2019-06-19T14:24:32.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.rest_api.bpartner.PageSize',Updated=TO_TIMESTAMP('2019-06-19 14:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541280
;

