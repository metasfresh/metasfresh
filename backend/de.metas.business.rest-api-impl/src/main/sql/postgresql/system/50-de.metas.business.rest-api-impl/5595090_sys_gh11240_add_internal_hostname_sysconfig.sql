-- 2021-06-25T16:09:24.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (ConfigurationLevel,AD_Client_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_SysConfig_ID,Name,Description,AD_Org_ID,Value,EntityType) VALUES ('S',0,TO_TIMESTAMP('2021-06-25 18:09:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-06-25 18:09:24','YYYY-MM-DD HH24:MI:SS'),100,541379,'de.metas.util.web.audit.AppServerInternalHostName','Hostname which the API Audit''s internal http-client can use to invoke the app server''s REST endpoints.
Should be reachable for both app itself and webapi.',0,'app','D')
;

-- 2021-06-25T16:13:41.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Hostname which the API Audit''s internal http-client can use to invoke the app server''s REST endpoints.
Should be reachable for both app itself and webapi.

NOTE: to ease the developers'' lifes, if the API Audit framework receives a call to http://localhost, it will ignore this sysconfig and keep going with localhost.',Updated=TO_TIMESTAMP('2021-06-25 18:13:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541379
;
