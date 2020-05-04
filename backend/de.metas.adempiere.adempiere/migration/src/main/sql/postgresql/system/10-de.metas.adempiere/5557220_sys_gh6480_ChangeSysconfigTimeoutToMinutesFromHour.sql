-- 2020-04-15T09:09:19.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.ui.web.view.ViewExpirationTimeoutInMinutes', Description='How many minutes a view is cached. A view is automatically invalidated after this many minutes pass without it being accessed.
Default is 60.
On modification, restart the instance to load the new settings.', Value='60',Updated=TO_TIMESTAMP('2020-04-15 12:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541319
;
