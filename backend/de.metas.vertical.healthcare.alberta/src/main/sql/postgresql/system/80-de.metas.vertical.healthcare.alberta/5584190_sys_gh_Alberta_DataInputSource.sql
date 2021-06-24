-- 2021-03-28T22:29:28.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_InputDataSource (AD_Client_ID,AD_InputDataSource_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDestination,IsEdiEnabled,Name,Updated,UpdatedBy,Value) VALUES (1000000,540216,1000000,TO_TIMESTAMP('2021-03-29 01:29:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','Alberta',TO_TIMESTAMP('2021-03-29 01:29:28','YYYY-MM-DD HH24:MI:SS'),100,'Alberta')
;

update ad_inputdatasource set internalname = 'Alberta' where ad_inputdatasource_id = 540216
;

