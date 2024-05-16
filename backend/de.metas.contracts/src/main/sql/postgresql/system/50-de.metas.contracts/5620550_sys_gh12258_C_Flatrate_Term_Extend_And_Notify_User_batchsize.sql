-- 2022-01-04T19:51:43.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541441,'S',TO_TIMESTAMP('2022-01-04 20:51:43','YYYY-MM-DD HH24:MI:SS'),100,'When the process C_Flatrate_Term_Extend_And_Notify_User is run to extend *all* eligible terms, it will process up to ''chunkSize'' terms per transaction.
The goal of this is to avoid a large number of CreateMissingInvoiceCandidatesWorkpackageProcessor workpackages, each with just one element.','de.metas.contracts','Y','de.metas.contracts.flatrate.process.C_Flatrate_Term_Extend_And_Notify_User.chunkSize',TO_TIMESTAMP('2022-01-04 20:51:43','YYYY-MM-DD HH24:MI:SS'),100,'1000')
;

