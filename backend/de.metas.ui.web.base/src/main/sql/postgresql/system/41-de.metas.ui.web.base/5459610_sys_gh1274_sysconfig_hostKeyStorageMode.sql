-- 2017-04-04T19:52:48.640
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541126,'O',TO_TIMESTAMP('2017-04-04 19:52:48','YYYY-MM-DD HH24:MI:SS'),100,'Controls how to store the pritning host key in webui mode. allowed values are "cookie" and "session.remote_host" (both case insensitive).
"cookie" means that the webui will create a cookie with a random UUID and store it on the client''s machine to identify it. 
"session.remote_host" means that the webui will use the respective AD_session.Remote_Addr value as the host key.
Also see https://github.com/metasfresh/metasfresh/issues/1274','de.metas.ui.web','Y','de.metas.printing.webui.HostKeyStorageMode',TO_TIMESTAMP('2017-04-04 19:52:48','YYYY-MM-DD HH24:MI:SS'),100,'cookie')
;

-- 2017-04-04T19:56:38.983
-- URL zum Konzept
UPDATE AD_SysConfig SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-04-04 19:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541126
;

-- Setting AD_SysConfig de.metas.printing.webui.HostKeyStorageMode to session.remote_host makes a lot of sense when you log in from hosts with fixed IPs
-- UPDATE AD_SysConfig SET Value='session.remote_host' WHERE AD_SysConfig_ID=541126;