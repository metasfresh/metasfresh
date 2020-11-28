-- 2020-04-14T10:08:07.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Name,AD_Org_ID,Description,Value,EntityType) VALUES (0,TO_TIMESTAMP('2020-04-14 13:08:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','S',TO_TIMESTAMP('2020-04-14 13:08:06','YYYY-MM-DD HH24:MI:SS'),100,541319,'de.metas.ui.web.view.ViewExpirationTimeInHours',0,'How many hours a view is cached. A view is automatically invalidated after this many hours pass without it being accessed.','1','D')
;

-- 2020-04-14T10:14:07.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.ui.web.view.ViewExpirationTimeoutInHours',Updated=TO_TIMESTAMP('2020-04-14 13:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541319
;

