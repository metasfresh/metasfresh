-- 2020-11-27T06:12:31.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541346,'O',TO_TIMESTAMP('2020-11-27 08:12:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','de.metas.marketing.EnforceUserHasMarketingChannels',TO_TIMESTAMP('2020-11-27 08:12:31','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2020-11-27T10:52:31.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This only affects non-system users. System users can always have no marketing channels.
The rule is only enforced in the user window, when this is set to ''Y''.',Updated=TO_TIMESTAMP('2020-11-27 12:52:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541346
;

