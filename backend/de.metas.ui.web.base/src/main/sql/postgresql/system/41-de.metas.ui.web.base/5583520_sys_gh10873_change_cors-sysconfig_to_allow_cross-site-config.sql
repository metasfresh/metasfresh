
--
-- Rename AD_SysConfig 'webui.frontend.cors.enabled' to 'webui.frontend.allow-cross-site-usage'
--
-- 2021-03-23T16:06:18.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='By default (value=N), the Access-Control-Allow-Origin response header will be set from the "webui.frontend.url" sysconfig and the session-cookie SameSite directive will be set to Lax.
However, if you are a frontend developer and this is a dev-instance, then you can set this to Y so that your locally running frontend-code may connect to this dev-instance.', Name='webui.frontend.allow-cross-site-usage', Value='N',Updated=TO_TIMESTAMP('2021-03-23 17:06:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541231
;

