-- 2020-12-02T09:53:51.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541347,'O',TO_TIMESTAMP('2020-12-02 11:53:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','de.metas.marketing.SyncUserNewsletterFlagToMarketingContact',TO_TIMESTAMP('2020-12-02 11:53:51','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2020-12-02T11:19:33.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This sys config specifies whether a user should be added to or removed from the default "newsletter" marketing campaign when setting the user''s ''IsNewsletter'' flag to Y or N.',Updated=TO_TIMESTAMP('2020-12-02 13:19:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541347
;
