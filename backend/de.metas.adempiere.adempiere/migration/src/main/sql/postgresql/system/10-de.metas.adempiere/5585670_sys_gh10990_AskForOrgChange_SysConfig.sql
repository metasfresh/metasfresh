-- 2021-04-15T12:56:01.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) 
VALUES (0,0,541364,'S',TO_TIMESTAMP('2021-04-15 15:56:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AskForOrgChangeOnRegionChange',
TO_TIMESTAMP('2021-04-15 15:56:01','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2021-04-20T20:37:03.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='When set on ''Y'', this config will trigger the process "Organisation Change", which allows moving a partner, together with the locations, users and bankaccounts, to be moved to another organization. This process will be triggered when a user changes the Region of a location.',Updated=TO_TIMESTAMP('2021-04-20 23:37:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541364
;

-- 2021-04-21T07:38:37.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='When set on ''Y'', this configurtion will trigger the process "Organisation Change", which allows moving a partner, together with the locations, users and bankaccounts to be moved to another organization. This process will be triggered when a user changes the Region of a location.',Updated=TO_TIMESTAMP('2021-04-21 10:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541364
;

