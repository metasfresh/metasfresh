-- 2021-08-17T08:14:51.334520100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541384,'O',TO_TIMESTAMP('2021-08-17 11:14:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.session.UserSession.IsAlwaysDisplayNewBPartner',TO_TIMESTAMP('2021-08-17 11:14:51','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2021-08-17T08:14:57.783435600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2021-08-17 11:14:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541384
;

