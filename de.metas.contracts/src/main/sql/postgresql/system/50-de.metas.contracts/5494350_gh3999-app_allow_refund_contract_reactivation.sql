--
-- unrelated typo fix
--
-- 2018-05-23T17:01:28.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Specifies that C_Flatrate_Terms with Type_Conditions = Procuremnt ("Liefervereinbarung"). May be reactivated in the system.
Note that the spelling of "Procuremnt" is not a typo but the internal value for those contracts.

See https://github.com/metasfresh/metasfresh/issues/2917 for background.',Updated=TO_TIMESTAMP('2018-05-23 17:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541171
;

--
-- allow refund contract reactivation
--
-- 2018-05-23T17:05:03.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541213,'O',TO_TIMESTAMP('2018-05-23 17:05:03','YYYY-MM-DD HH24:MI:SS'),100,'Specifies that C_Flatrate_Terms with Type_Conditions = Refund ("Rückvergütung") may be reactivated in the system.
The system will attempt to delete the corresponsing invoice candidates.','de.metas.contracts','Y','de.metas.contracts.C_Flatrate_Term.allow_reactivate_Refund',TO_TIMESTAMP('2018-05-23 17:05:03','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

